/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.ExerciceTVA;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.DateUtil;

/**
 *
 * @author ushiho
 */
@Stateless
public class ExerciceTVAFacade extends AbstractFacade<ExerciceTVA> {

    @PersistenceContext(unitName = "TaxeGOVMAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExerciceTVAFacade() {
        super(ExerciceTVA.class);
    }

    // ds le controller fait exe.setCat(catFacade.findByNum....
    public int save(ExerciceTVA exerciceTVA) {
        if (testParams(exerciceTVA) < 0) {
            return -1;
        }
        create(exerciceTVA);
        return 1;
    }

    public int testParams(ExerciceTVA exerciceTVA) {
        if (exerciceTVA == null) {
            return -1;
        } else if (exerciceTVA.getMontant() < 0
                || DateUtil.compareTwoDates(exerciceTVA.getDateDebut(), exerciceTVA.getDateFin()) < 0
                || exerciceTVA.getSociete() == null
                || exerciceTVA.getCategorieTVA() == null) {
            return -2;
        }
        return 1;
    }

    public int delete(ExerciceTVA exerciceTVA) {
        if (exerciceTVA == null) {
            return -1;
        }
        remove(exerciceTVA);
        return 1;
    }
}
