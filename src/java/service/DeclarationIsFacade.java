/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CompteBanquaire;
import bean.DeclarationIs;
import bean.ExerciceIS;
import bean.PaiementIS;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ushiho
 */
@Stateless
public class DeclarationIsFacade extends AbstractFacade<DeclarationIs> {

    @PersistenceContext(unitName = "TaxeGOVMAPU")
    private EntityManager em;

    @EJB
    private CategorieISFacade categorieISFacade;
    @EJB
    private ExerciceISFacade exerciceISFacade;
    @EJB
    private PaiementISFacade paiementISFacade;
    @EJB
    private SocieteFacade societeFacade;
    @EJB
    private CompteBanquaireFacade compteBanquaireFacade;
    @EJB
    private PenaliteISFacade penaliteISFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DeclarationIsFacade() {
        super(DeclarationIs.class);
    }

    public int save(List<ExerciceIS> exerciceISs) {
        if (testParamForSave(exerciceISs)) {
            return -1;
        }
        DeclarationIs declarationIs = calcResultatFiscalAndComptable(exerciceISs);
        declarationIs.setExerciceISs(exerciceISs);
        initDecalarationIsParam(declarationIs);
        if (!testExoneration(declarationIs)) {
            //creation de penalite sur retard de declaration 
            //afficher la penalite ds le view
            penaliteISFacade.saveForDeclarationIS(declarationIs);// a faire !!

        }
        create(declarationIs);
        exerciceISFacade.save(exerciceISs);
        return 1;
    }

    private boolean testParamForSave(List<ExerciceIS> exerciceISs) {
        return exerciceISs == null || exerciceISs.get(0) == null || exerciceISs.isEmpty() || exerciceISFacade.testExercices(exerciceISs) < 0;
    }

    private void initDecalarationIsParam(DeclarationIs declarationIs) {
        declarationIs.setEtat(0);
        declarationIs.setDateDeclaration(new Date());
        declarationIs.setPaiementISs(null);
    }

    //test de l exoneration
    private boolean testExoneration(DeclarationIs declarationIs) {
        if (societeFacade.exonerer(declarationIs.getSociete())) {
            declarationIs.setMontantIs(0f);
            declarationIs.getSociete().setDeficitIS(0f);
            return true;
        } else if (calculerDeficit(declarationIs) > 0) {
            calcMontantIS(declarationIs);
        } else {
            declarationIs.setMontantIs(0f);
        }
        return false;
    }

    //le calcul de mtIS a payer 
    private void calcMontantIS(DeclarationIs declarationIs) {
        declarationIs.setTauxIs(categorieISFacade.findByMontant(declarationIs.getResultatFiscal()));
        float montant = ((declarationIs.getResultatFiscal() * declarationIs.getTauxIs()) / 100f) % .3f;
        float cm = ((declarationIs.getChiffreAffaire() * 0.5f) / 100f) % .3f;
        if (montant > cm) {
            declarationIs.setMontantIs(montant);
        } else if (cm > 1500f) {
            declarationIs.setMontantIs(cm);
        } else {
            declarationIs.setMontantIs(1500f);
        }
    }

    //ajout de deficit des annes derniers !!
    private int calculerDeficit(DeclarationIs declarationIs) {
        float rest = declarationIs.getResultatFiscal() + declarationIs.getSociete().getDeficitIS();
        if (rest >= 0) {
            declarationIs.getSociete().setDeficitIS(0f);//faire initialiser le deficit !
            declarationIs.setResultatFiscal(rest);
            societeFacade.edit(declarationIs.getSociete());
            return 1;
        } else {
            declarationIs.getSociete().setDeficitIS(rest);
            societeFacade.edit(declarationIs.getSociete());
            declarationIs.setDeficit(rest);
            return -1;
        }

    }

    private DeclarationIs calcResultatFiscalAndComptable(List<ExerciceIS> exerciceISs) {
        DeclarationIs declarationIs = new DeclarationIs();
        declarationIs.setSociete(exerciceISs.get(0).getSociete());//societe ghatjibha mn utili mn session !!!
        for (int i = 0; i < exerciceISs.size(); i++) {
            ExerciceIS exerciceIS = exerciceISs.get(i);
            declarationIs.setResultatComptable(declarationIs.getResultatComptable() + exerciceIS.getProduits() - exerciceIS.getCharges());
            declarationIs.setResultatFiscal(declarationIs.getResultatFiscal() + declarationIs.getResultatComptable()
                    - exerciceIS.getDeductibles() + exerciceIS.getNonDeductibles());
            declarationIs.setChiffreAffaire(exerciceIS.getProduits() + declarationIs.getChiffreAffaire());
        }
        return declarationIs;
    }

    ///validation de la declarartion
    public int valider(DeclarationIs declarationIs) {
        if (declarationIs == null) {
            return -1;
        }
        List<PaiementIS> paiementISs = paiementISFacade.save(declarationIs);
        declarationIs.setEtat(1);
        declarationIs.setPaiementISs(paiementISs);
        edit(declarationIs);
        paiementISFacade.save(paiementISs);
        return 1;
    }

    public int payer(DeclarationIs declarationIs, CompteBanquaire compteBanquaire) {
        if (testParamForPayer(declarationIs, compteBanquaire)) {
            return -1;
        }
        PaiementIS paiementIS = accomptApayer(declarationIs);
        if (paiementIS == null) {
            return -2;
        }
        appliquerPenalitePrPaiement(paiementIS);
        if (compteBanquaireFacade.crediter(compteBanquaire, paiementIS.getMtTotal()) < 0) {
            return -3;
        } else {
            compteBanquaireFacade.debiter(compteBanquaireFacade.findByDGI(), paiementIS.getMtTotal());
            paiementIS.setDatePaiement(new Date());
            declarationIs.setEtat(2);
            edit(declarationIs);
            paiementISFacade.edit(paiementIS);
            return 1;
        }

    }

    private PaiementIS accomptApayer(DeclarationIs declarationIs) {
        int accompte = declarationIs.getNbAccomptePaye();
        switch (accompte) {
            case 0:
                return declarationIs.getPaiementISs().get(1);
            case 1:
                return declarationIs.getPaiementISs().get(2);
            case 2:
                return declarationIs.getPaiementISs().get(3);
            case 3:
                return declarationIs.getPaiementISs().get(4);
        }
        return null;
    }

    private void appliquerPenalitePrPaiement(PaiementIS paiementIS) {
        if (new Date().compareTo(paiementIS.getDateDernierDelai()) > 0) {
            //chercher les anciens penalits + save de penalite+calcul de mt de retard
            paiementIS.setMtRetard(Float.NaN);

        }
        paiementIS.setMtTotal(paiementIS.getMtBase() + paiementIS.getMtRetard());
    }

    private boolean testParamForPayer(DeclarationIs declarationIs, CompteBanquaire compteBanquaire) {
        return declarationIs == null || compteBanquaire == null || !compteBanquaireFacade.findBySociete(declarationIs.getSociete()).contains(compteBanquaire);
    }

    public int modify(DeclarationIs declarationIs, List<ExerciceIS> nvExerciceISs) {
        if (declarationIs == null || nvExerciceISs == null || nvExerciceISs.isEmpty()) {
            return -1;
        } else if (declarationIs.getEtat() == 2) {
            return -2;
        }
        delete(declarationIs);
        return save(nvExerciceISs);

    }

    public void delete(DeclarationIs declarationIs) {
        if (declarationIs == null) {
            return;
        }
        List<ExerciceIS> anExerciceISs = declarationIs.getExerciceISs();
        for (int i = 0; i < anExerciceISs.size(); i++) {
            ExerciceIS anExerciceIS = anExerciceISs.get(i);
            exerciceISFacade.remove(anExerciceIS);
        }
        paiementISFacade.deleteByDeclarationIS(declarationIs);
        remove(declarationIs);
    }
}
