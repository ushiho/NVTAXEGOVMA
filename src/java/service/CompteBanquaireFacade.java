/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CompteBanquaire;
import bean.Societe;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ushiho
 */
@Stateless
public class CompteBanquaireFacade extends AbstractFacade<CompteBanquaire> {

    @PersistenceContext(unitName = "TaxeGOVMAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompteBanquaireFacade() {
        super(CompteBanquaire.class);
    }

    public List<CompteBanquaire> findBySociete(Societe societe) {

        return em.createQuery("SELECT c FROM CompteBanquaire c WHERE c.societe.id = '" + societe.getIdFiscal() + "'").getResultList();
    }

    public CompteBanquaire findByDGI() {
        String req = "SELECT c FROM CompteBanquaire c WHERE c.societe IS NULL";
        return getUniqueResult(req);
    }

    public int save(CompteBanquaire compteBanquaire) {
        if (compteBanquaire == null) {
            return -1;
        }
        create(compteBanquaire);
        return 1;
    }

    public int crediter(CompteBanquaire compteBanquaire, Float montant) {
        if (compteBanquaire == null) {
            return -1;
        } else if (compteBanquaire.getSolde() < montant) {
            return -2;
        } else {
            compteBanquaire.setSolde(compteBanquaire.getSolde() - montant);
            edit(compteBanquaire);
            return 1;
        }
    }

    public int debiter(CompteBanquaire compteBanquaire, Float montant) {
        if (compteBanquaire == null) {
            return -1;
        } else {
            compteBanquaire.setSolde(compteBanquaire.getSolde() + montant);
            edit(compteBanquaire);
            return 1;
        }
    }

}
