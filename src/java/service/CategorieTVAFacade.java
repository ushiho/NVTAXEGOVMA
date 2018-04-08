/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CategorieTVA;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ushiho
 */
@Stateless
public class CategorieTVAFacade extends AbstractFacade<CategorieTVA> {

    @PersistenceContext(unitName = "TaxeGOVMAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategorieTVAFacade() {
        super(CategorieTVA.class);
    }
    
    public CategorieTVA findByNum(int num){
        return getUniqueResult("SELECT c FROM CategorieTVA c WHERE c.num='"+num+"'");
        }
}
