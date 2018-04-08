/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CategorieIS;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.SearchUtil;

/**
 *
 * @author ushiho
 */
@Stateless
public class CategorieISFacade extends AbstractFacade<CategorieIS> {

    @PersistenceContext(unitName = "TaxeGOVMAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategorieISFacade() {
        super(CategorieIS.class);
    }

    public Float findByMontant(Float mt) {
        String req = "SELECT C FROM CategorieIS C WHERE 1=1 ";
        req += SearchUtil.addConstraint("C", "benificeMin", "<=", mt);
        req += SearchUtil.addConstraint("C", "benificeMax", ">=", mt);
       // req+= " or c.benificeMin > "+mt;
        

        return getUniqueResult(req).getTaux();
    }

    public int save(CategorieIS categorieIS) {
        if (categorieIS == null) {
            return -1;
        } else if (categorieIS.getBenificeMax() < 0 || categorieIS.getBenificeMin() < 0 || categorieIS.getTaux() < 0
                || categorieIS.getBenificeMax() < categorieIS.getBenificeMin()) {
            return -2;
        }
        categorieIS.setDateApp(new Date());
        create(categorieIS);
        return 1;
    }
}
