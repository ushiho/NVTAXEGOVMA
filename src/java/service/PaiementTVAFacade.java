/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.DeclarationTva;
import bean.DernierDelaiTVA;
import bean.PaiementTVA;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.DateUtil;

/**
 *
 * @author ushiho
 */
@Stateless
public class PaiementTVAFacade extends AbstractFacade<PaiementTVA> {

    @PersistenceContext(unitName = "TaxeGOVMAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @EJB
    private DernierDelaiTVAFacade dernierDelaiTVAFacade;

    public PaiementTVAFacade() {
        super(PaiementTVA.class);
    }

    public PaiementTVA save(DeclarationTva declarationTva) {
        if (declarationTva == null) {
            return null;
        }
        int regime = declarationTva.getSociete().getRegimeTVA();
        DernierDelaiTVA dernierDelaiTVA = dernierDelaiTVAFacade.findDatePaiementByTypeAndRegime(2, regime);
        String date = dernierDelaiTVA.getJour() + "/" + DateUtil.addMonthToDate(regime, declarationTva.getDateDeclaration())
                + "/" + declarationTva.getDateDeclaration().getYear();
        PaiementTVA paiementTVA = rempliParams(declarationTva, DateUtil.parse(date));
        create(paiementTVA);
        return paiementTVA;
    }

    private PaiementTVA rempliParams(DeclarationTva declarationTva, Date dateDernierDelai) {
        PaiementTVA paiementTVA = new PaiementTVA();
        paiementTVA.setMtBase(declarationTva.getMontantAverser());
        paiementTVA.setId(generate("PaiementTVA", "id"));
        paiementTVA.setDatePaiment(null);
        paiementTVA.setMtTotal(paiementTVA.getMtBase());//en attent d'ajouter penalite
        paiementTVA.setDateDernierPaiement(dateDernierDelai);
        paiementTVA.setDeclarationTva(declarationTva);
        return paiementTVA;
    }
}
