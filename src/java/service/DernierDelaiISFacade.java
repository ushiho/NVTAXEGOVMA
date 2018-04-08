/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.DernierDelaiIS;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ushiho
 */
@Stateless
public class DernierDelaiISFacade extends AbstractFacade<DernierDelaiIS> {

    @PersistenceContext(unitName = "TaxeGOVMAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DernierDelaiISFacade() {
        super(DernierDelaiIS.class);
    }

    public DernierDelaiIS findDatePaiementByAccompte(int accompte, int type) {
        String req = "SELECT d FROM DernierDelaiIS d WHERE d.type = '1' AND d.accompteAverse = '" + accompte + "'"
                + " AND d.type = '" + type + "'";
        return getUniqueResult(req);
    }
}
