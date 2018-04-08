/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.DeclarationIs;
import bean.PenaliteIS;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ushiho
 */
@Stateless
public class PenaliteISFacade extends AbstractFacade<PenaliteIS> {

    @PersistenceContext(unitName = "TaxeGOVMAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PenaliteISFacade() {
        super(PenaliteIS.class);
    }
    
    public void saveForDeclarationIS(DeclarationIs declarationIs){
    }
}
