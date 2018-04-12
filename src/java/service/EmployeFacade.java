/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Email;
import util.EmailUtil;
import bean.Societe;
import bean.Employe;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import util.PassUtil;
import util.HashageUtil;
import util.SessionUtil;

/**
 *
 * @author ushiho
 */
@Stateless
public class EmployeFacade extends AbstractFacade<Employe> {


    private final EmailUtil emailUtil = new EmailUtil();
    @PersistenceContext(unitName = "TaxeGOVMAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @EJB
    private SocieteFacade societeFacade;
    @EJB
    private EmployeFacade utilisateurFacade;
    @EJB
    private EmailFacade emailFacade;

    public EmployeFacade() {
        super(Employe.class);
    }

    public int sAdherer(Employe contribuable) {
        if ((contribuable.getLogin()).length() == 12) {
            return testIDFiscalEtPass(contribuable);
        }
        return -3;
    }

    private int testIDFiscalEtPass(Employe contribuable) {
        Societe societe = societeFacade.find(contribuable.getLogin());
        if (societe == null) {
            return -1;
        } else if (!PassUtil.testTwoPasswords(contribuable.getMotDePasse(), societe.getPassword())) {
            return -2;
        }
        return 1;
    }

    public int seConnecter(Employe utilisateur) {
        if (testUtilisateur(utilisateur)) {
            System.out.println("-3");
            return -3;
        }
        Employe existe = findByLogin(utilisateur.getLogin());
        if (existe == null) {
            System.out.println("-1");
            return -1;
        } else if (!PassUtil.testTwoPasswords(utilisateur.getMotDePasse(), existe.getMotDePasse())) {
            System.out.println("-2");
            return -2;
        } else {
            System.out.println("1");
            SessionUtil.registerUser(clone(existe));
            return 1;
        }
    }

    private boolean testUtilisateur(Employe utilisateur) {
        return utilisateur == null;
    }

    public int save(Employe utilisateur) {
        if (utilisateur == null) {
            return -1;
        }
        System.out.println(utilisateur);
        utilisateur.setMotDePasse(HashageUtil.sha256(utilisateur.getMotDePasse()));
        create(utilisateur);
        return 1;
    }

    public int addUtilisateur(Employe utilisateur, Employe contribuable) {
        if (testParams(utilisateur, contribuable)) {
            return -1;
        }
        setPassAndLogin(utilisateur);
        Email email = emailFacade.creerMsgGenererPass(utilisateur.getLogin(), utilisateur.getMotDePasse(), 1);
        if (emailUtil.sendEmail(email, utilisateur) < 0) {
            return -2;
        }
        create(utilisateur);
        return 1;

    }

    private void setPassAndLogin(Employe utilisateur) {
        String login = PassUtil.generate(12, 1);
        String pass = PassUtil.generatePass(6, 4);
        while (findByLogin(login) != null) {
            login = PassUtil.generatePass(12, 1);
        }
        utilisateur.setMotDePasse(pass);
        utilisateur.setLogin(login);
    }

    private boolean testParams(Employe utilisateur, Employe contribuable) {
        return utilisateur == null || contribuable == null || contribuable.getDroitFiscale() != 0;
    }

    public int modify(Employe nvUtilisateur, Employe anUtilisateur) {
        if (testUtilisateur(anUtilisateur) || testUtilisateur(find(anUtilisateur.getId()))) {
            return -1;
        }
        setParamToModify(nvUtilisateur, anUtilisateur);
        edit(anUtilisateur);
        return 1;
    }

    public void setParamToModify(Employe nvUtilisateur, Employe anUtilisateur) {
        anUtilisateur.setCIN(nvUtilisateur.getCIN());
        anUtilisateur.setDroitFiscale(nvUtilisateur.getDroitFiscale());
        anUtilisateur.setEmail(nvUtilisateur.getEmail());
        anUtilisateur.setLogin(nvUtilisateur.getLogin());
        anUtilisateur.setMotDePasse(nvUtilisateur.getMotDePasse());
        anUtilisateur.setNom(nvUtilisateur.getNom());
        anUtilisateur.setNumTele(nvUtilisateur.getNumTele());
        anUtilisateur.setPreNom(nvUtilisateur.getPreNom());
        anUtilisateur.setProfession(nvUtilisateur.getProfession());
        anUtilisateur.setSociete(nvUtilisateur.getSociete());

    }

    public int resetPassword(Employe utilisateur) {
        if (testUtilisateur(utilisateur) || findByLogin(utilisateur.getLogin()) == null) {
            return -1;
        }
        String psswd = PassUtil.generatePass(6, 4) ;
        System.out.println(psswd);
        System.out.println(utilisateur.getEmail());
        utilisateur.setMotDePasse(HashageUtil.sha256(psswd));
        Email email = emailFacade.creerMsgGenererPass(utilisateur.getLogin(), psswd, 3);
        if (emailUtil.sendEmail(email, utilisateur) < 0) {
            return -2;
        }
        return 1;
    }

    public int deleteFromSimplSer(Employe contribuable, Employe utilisateur) {
        if (testParams(find(utilisateur.getId()), contribuable)) {
            return -1; //c'est pas un contri , 
        }
        remove(utilisateur);
        return 1;
    }

    public String droit(int num) {
        String droit;
        switch (num) {
            case 0:
                droit = "Redacteur";
                break;
            case 1:
                droit = "Responsable de Validation";
                break;
            case 2:
                droit = "Responsable de Paiement";
                break;
            default:
                droit = null;
                break;
        }
        return droit;
    }

    public Employe findByLogin(String login) {
        return getUniqueResult("SELECT u FROM Employe u WHERE u.login like '" + login + "'");
    }

    public int deleteBySociete(Societe societe) {
        if (societe != null) {
            return em.createQuery("DELETE FROM Employe u WHERE u.societe.id ='" + societe.getId() + "'").executeUpdate();
        }
        return -1;
    }

    //clone the user
    public Employe clone(Employe user) {
        Employe clone = new Employe(user.getId(), user.getNom(), user.getPreNom(), user.getCIN(),
                user.getNumTele(), user.getEmail(), user.getProfession(), user.getLogin(), null, user.getDroitFiscale());
        clone.setSociete(user.getSociete());
        return clone;
    }

    public Employe getConnectedUser() {
        Employe connected = (Employe) SessionUtil.getAttribute("user");
        return connected;
    }

    public void logout() {
        HttpSession session = SessionUtil.getSession();
        session.invalidate();
    }
    
    
}
