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
import java.util.Arrays;
import java.util.List;
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

    public int testIDFiscalEtPass(Employe contribuable) {
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
            Employe clone = clone(existe);
            clone.setMotDePasse(null);
            SessionUtil.registerUser(clone);
            return 1;
        }
    }

    public boolean testUtilisateur(Employe utilisateur) {
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
        Email email = emailFacade.generatePassword(utilisateur.getLogin(), utilisateur.getMotDePasse(), 1);
        if (EmailUtil.sendEmail(email, utilisateur) < 0) {
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
        return utilisateur == null || contribuable == null || !contribuable.getDroitFiscale().equals("123");
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
        Employe existe = findByLogin(utilisateur.getLogin());
        if (testUtilisateur(utilisateur) || testUtilisateur(existe)) {
            return -1;
        } else if (verifyQuestion(existe, utilisateur.getQuestionChoisi(), utilisateur.getReponse()) < 0) {
            System.out.println("-2");
            return -2;
        }
        String psswd = PassUtil.generatePass(6, 4);
        if (EmailUtil.sendEmail(emailFacade.generatePassword(utilisateur.getLogin(), psswd, 3), utilisateur) < 0) {
            System.out.println("-3");
            return -3;
        }
        existe.setMotDePasse(HashageUtil.sha256(psswd));
        edit(existe);
        return 1;
    }

    private int verifyQuestion(Employe employe, int question, String reponse) {
        if (employe.getQuestionChoisi() != question) {
            return -1;
        } else if (!employe.getReponse().equals(reponse)) {
            return -2;
        } else {
            return 1;
        }
    }

    public int deleteFromSimplSer(Employe contribuable, Employe utilisateur) {
        if (testParams(find(utilisateur.getId()), contribuable)) {
            return -1; //c'est pas un contri , 
        }
        remove(utilisateur);
        return 1;
    }

    public String numberTodroit(String num) {
        if (num.length() == 3) {
            return "Rédaction, Validation, Paiment";
        }
        if (num.length() == 2) {
            return deuxNumToDroit(num);
        }
        if (num.length() == 1) {
            return unNumToDroit(num);
        }
        return null;
    }

    private String deuxNumToDroit(String num) {
        switch (num) {
            case "12":
                return "Rédaction, Validation";
            case "13":
                return "Rédaction, Paiement";
            case "23":
                return "Validation, Paiement";
            default:
                return null;
        }
    }

    private String unNumToDroit(String num) {
        switch (num) {
            case "1":
                return "Rédaction";
            case "2":
                return "Validation";
            case "3":
                return "Paiment";
            default:
                return null;
        }
    }

    public String droitToNumberToSave(String droit) {
        switch (droit) {
            case "Rédaction, Validation, Paiment":
                return "123";
            case "Rédaction, Validation":
                return "12";
            case "Rédaction, Paiment":
                return "13";
            case "Validation, Paiment":
                return "23";
            default:
                return null;
        }
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
                user.getNumTele(), user.getEmail(), user.getProfession(), user.getLogin(), user.getMotDePasse(), user.getDroitFiscale());
        clone.setSociete(user.getSociete());
        return clone;
    }

    public Employe getConnectedUser(String cle) {
        Employe connected = (Employe) SessionUtil.getAttribute(cle);
        return connected;
    }

    public void logout() {
        HttpSession session = SessionUtil.getSession();
        session.invalidate();
    }

    public int sendCodeToVirefyEmail(Employe employeToAdd) {
        String pass = PassUtil.generatePass(6, 1);
        employeToAdd.setMotDePasse(pass);
        if (EmailUtil.sendEmail(emailFacade.verifyEmail(pass, 4), employeToAdd) < 0) {
            System.out.println("-1");
            return -1;
        }
        SessionUtil.setAttribute("data", employeToAdd);
        return 1;
    }

    public boolean existeInList(List<Employe> employes, Employe employe) {
        for (Employe item : employes) {
            return (item.getCIN().equals(employe.getCIN()));
        }
        return false;
    }

    public String checkboxDroitsToNum(int[] droits) {
        char[] caracs = Arrays.toString(droits).toCharArray();
        if (caracs.length == 9) {
            //tous les droits; res exple : 123
            return caracs[1] + "" + caracs[4] + "" + caracs[7];
        }
        if (caracs.length == 6) {
            return caracs[1] + "" + caracs[4];
        }
        if (caracs.length == 3) {
            return caracs[1] + "";
        }
        return null;
    }
}
