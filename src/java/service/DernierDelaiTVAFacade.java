/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.DernierDelaiIS;
import bean.DernierDelaiTVA;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ushiho
 */
@Stateless
public class DernierDelaiTVAFacade extends AbstractFacade<DernierDelaiTVA> {

    @PersistenceContext(unitName = "TaxeGOVMAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DernierDelaiTVAFacade() {
        super(DernierDelaiTVA.class);
    }

    public DernierDelaiTVA findDatePaiementByTypeAndRegime(int type, int regime) {
        String req = "SELECT d FROM DernierDelaiTVA d WHERE d.type='" + type + "' AND d.regime ='" + regime + "'";
        return getUniqueResult(req);
    }
}
