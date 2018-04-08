/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CompteBanquaire;
import bean.DeclarationTva;
import bean.ExerciceTVA;
import bean.PaiementTVA;
import bean.Societe;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ushiho
 */
@Stateless
public class DeclarationTvaFacade extends AbstractFacade<DeclarationTva> {

    @EJB
    private ExerciceTVAFacade exerciceTVAFacade;
    @EJB
    private CategorieTVAFacade categorieTVAFacade;
    @EJB
    private SocieteFacade societeFacade;
    @EJB
    private PaiementTVAFacade paiementTVAFacade;
    @EJB
    private CompteBanquaireFacade compteBanquaireFacade;

    @PersistenceContext(unitName = "TaxeGOVMAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DeclarationTvaFacade() {
        super(DeclarationTva.class);
    }

    public int save(ExerciceTVA exerciceTVAAchat, ExerciceTVA exerciceTVAVente) {
        if (exerciceTVAFacade.testParams(exerciceTVAAchat) < 0 || exerciceTVAFacade.testParams(exerciceTVAVente) < 0) {
            return -1;
        }

        DeclarationTva declarationTva = setParams(exerciceTVAAchat, exerciceTVAVente);
        calculeTaxeTva(exerciceTVAAchat, declarationTva);
        declarationTva.setPaiementTVA(null);
        create(declarationTva);
        return 1;

    }

    private void calculeTaxeTva(ExerciceTVA exerciceTVAAchat, DeclarationTva declarationTva) {
        Societe societe = exerciceTVAAchat.getSociete();
        float rest = declarationTva.getTvaDue() - declarationTva.getTvaDeductible() - societe.getDeficitTVA();
        if (rest < 0) {
            setMontants(declarationTva, 0f, rest, societe);
        } else {
            setMontants(declarationTva, rest,0f, societe);
        }
        declarationTva.setSociete(societe);
        societeFacade.edit(societe);
    }

    private void setMontants(DeclarationTva declarationTva, float averser,float areporter, Societe societe) {
        declarationTva.setMontantAverser(averser);
        declarationTva.setCreditAreporter(areporter);
        societe.setDeficitTVA(areporter);
    }

    private DeclarationTva setParams(ExerciceTVA exerciceTVAAchat, ExerciceTVA exerciceTVAVente) {
        DeclarationTva declarationTva = new DeclarationTva(new Date(), 0);
        declarationTva.setTvaDeductible(exerciceTVAAchat.getMontant() * exerciceTVAAchat.getCategorieTVA().getTaux());
        declarationTva.setTvaDue(exerciceTVAVente.getMontant() * exerciceTVAVente.getCategorieTVA().getTaux());
        declarationTva.setExerciceTVAAchat(exerciceTVAAchat);
        declarationTva.setExerciceTVAVente(exerciceTVAVente);
        return declarationTva;
    }

    public int valider(DeclarationTva declarationTva) {
        if (declarationTva == null) {
            return -1;
        }
        declarationTva.setPaiementTVA(paiementTVAFacade.save(declarationTva));
        declarationTva.setEtat(1);
        edit(declarationTva);
        return 1;
    }

    public int payer(DeclarationTva declarationTva, CompteBanquaire compteBanquaire) {
        if (testParamForPayer(declarationTva, compteBanquaire)) {
            return -1;
        }
        PaiementTVA paiementTVA = declarationTva.getPaiementTVA();
        appliquerPenalitePaimenet(paiementTVA);
        if (compteBanquaireFacade.crediter(compteBanquaire, paiementTVA.getMtTotal()) < 0) {
            return -2;
        } else {
            compteBanquaireFacade.debiter(compteBanquaireFacade.findByDGI(), paiementTVA.getMtTotal());
            declarationTva.setEtat(2);
            paiementTVA.setDatePaiment(new Date());
            edit(declarationTva);
            paiementTVAFacade.edit(paiementTVA);
            return 1;
        }
    }

    private boolean testParamForPayer(DeclarationTva declarationTva, CompteBanquaire compteBanquaire) {
        return declarationTva == null || compteBanquaire == null || !compteBanquaireFacade.findBySociete(declarationTva.getSociete()).contains(compteBanquaire);
    }

    private void appliquerPenalitePaimenet(PaiementTVA paiementTVA) {
        if (new Date().compareTo(paiementTVA.getDateDernierPaiement()) > 0) {
            //service pr appliquer 
            // tva
            paiementTVA.setMtRetartd(Float.NaN);
        }
    }
}
