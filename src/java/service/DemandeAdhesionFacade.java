/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.DemandeAdhesion;
import bean.Email;
import bean.Employe;
import bean.Societe;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.PassUtil;
import util.EmailUtil;

/**
 *
 * @author ushiho
 */
@Stateless
public class DemandeAdhesionFacade extends AbstractFacade<DemandeAdhesion> {

    @PersistenceContext(unitName = "TaxeGOVMAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DemandeAdhesionFacade() {
        super(DemandeAdhesion.class);
    }

    private PassUtil passUtil;
    private EmailUtil emailUtil;

    @EJB
    private SocieteFacade societeFacade;
    @EJB
    private EmailFacade emailFacade;

    public int creerAdhesion(DemandeAdhesion demandeAdhesion) {
        if (demandeAdhesion == null) {
            return -1;
        }
        demandeAdhesion.setEtat(3);
        create(demandeAdhesion);
        return 1;
    }

    public int accepterAdhesion(DemandeAdhesion demandeAdhesion) {
        if (demandeAdhesion == null) {
            return -1;
        } else if (creerSocietAndSenEmail(demandeAdhesion) < 0) {
            return -2;
        }
        demandeAdhesion.setEtat(1);
        edit(demandeAdhesion);

        return 1;
    }

    private int creerSocietAndSenEmail(DemandeAdhesion demandeAdhesion) {
        Societe societe = setSocieteParams(demandeAdhesion);
        Employe contribuable = demandeAdhesion.getContribuable();
        Email email = emailFacade.creerMsgGenererPass("" + societe.getIdFiscal(), societe.getPassword(), 1);
        if (emailUtil.sendEmail(email, contribuable) > 1) {
            societeFacade.create(societe);
            return 1;
        }
        return -1;
    }

    private Societe setSocieteParams(DemandeAdhesion demandeAdhesion) throws NumberFormatException {
        Societe societe = demandeAdhesion.getSociete();
        societe.setIdFiscal(new Long(passUtil.generate(12, 1)));
        societe.setPassword(passUtil.generatePassAndHash(6, 4));
        return societe;
    }

    public List<DemandeAdhesion> voirTtDemande() {
        return findByEtat(3);
    }

    public int annulerDemande(DemandeAdhesion demandeAdhesion) {
        if (demandeAdhesion == null) {
            return -1;
        }
        demandeAdhesion.setEtat(2);
        edit(demandeAdhesion);
        return 1;
    }

    public int supprimerDemande(DemandeAdhesion demandeAdhesion) {
        if (demandeAdhesion != null) {
            remove(demandeAdhesion);
            return 1;
        }
        return -1;
    }

    public List<DemandeAdhesion> findByEtat(int etat) {
        return getMultipleResult("SELECT d FROM DemandeAdhesion d WHERE d.etat = '" + etat + "'");
    }
}
