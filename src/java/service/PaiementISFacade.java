/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.DernierDelaiIS;
import bean.DeclarationIs;
import bean.PaiementIS;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class PaiementISFacade extends AbstractFacade<PaiementIS> {

    @PersistenceContext(unitName = "TaxeGOVMAPU")
    private EntityManager em;

    @EJB
    private DernierDelaiISFacade dateDernierDelaiFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PaiementISFacade() {
        super(PaiementIS.class);
    }

    public int save(List<PaiementIS> paiementISs) {
        if (paiementISs == null) {
            return -1;
        }
        for (int i = 0; i < paiementISs.size(); i++) {
            PaiementIS paiementIS = paiementISs.get(i);
            create(paiementIS);
        }
        return 1;
    }

    public int deleteByDeclarationIS(DeclarationIs declarationIs) {
        return em.createQuery("DELETE FROM PaiementIS p WHERE p.declarationIs.id = '" + declarationIs.getId() + "'").executeUpdate();
    }

    public List<PaiementIS> save(DeclarationIs declarationIs) {
        List<PaiementIS> paiementISs = new ArrayList();
        for (int i = 1; i <= 4; i++) {
            PaiementIS paiementIS = paiementISs.get(i);
            DernierDelaiIS dateDernierDelai = dateDernierDelaiFacade.findDatePaiementByAccompte(i,2);
            String date = dateDernierDelai.getJour() + "/" + dateDernierDelai.getMois() + "/" + DateUtil.addYearToDate(1, declarationIs.getDateDeclaration());
            rempliParams(paiementIS, declarationIs, i, DateUtil.parse(date));
        }
        return paiementISs;
    }

    private void rempliParams(PaiementIS paiementIS, DeclarationIs declarationIs, int accompte, Date dateDernierDelai) {
        paiementIS.setDateDernierDelai(dateDernierDelai);
        paiementIS.setAccompteVerse(accompte);
        paiementIS.setId(generate("PaiementIS", "id"));
        paiementIS.setDeclarationIs(declarationIs);
        paiementIS.setMtBase((declarationIs.getMontantIs() / 4) % .3f);
        paiementIS.setDatePaiement(null);
        paiementIS.setMtTotal(paiementIS.getMtBase());
    }
}
