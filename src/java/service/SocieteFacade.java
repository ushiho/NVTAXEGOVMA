/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Societe;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ushiho
 */
@Stateless
public class SocieteFacade extends AbstractFacade<Societe> {

    @PersistenceContext(unitName = "TaxeGOVMAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SocieteFacade() {
        super(Societe.class);
    }

    public boolean exonerer(Societe societe) {
        if (findByIdFiscal(societe.getIdFiscal()) == null) {
            return false;
        }
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(societe.getDateCreation());
        gc.add(GregorianCalendar.MONTH, 60);
        return new Date().compareTo(gc.getTime()) <= 0;
    }

    public Societe findByIdFiscal(long idFiscal) {
        return getUniqueResult("SELECT s FROM Societe s WHERE s.idFiscal = '" + idFiscal + "'");
    }
}
