/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author User
 */
@Entity
public class DeclarationIs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Societe societe;
    @OneToMany(mappedBy = "declarationIs")
    private List<ExerciceIS> exerciceISs;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeclaration;
    private int etat = 0;//0;broul 1: valider 2:payer
    private int nbAccomptePaye = 0;//1 ,2 ,3 ,4
    private Float montantIs = new Float(0);
    private float tauxIs = 0;
    private Float resultatComptable = new Float(0);
    private Float chiffreAffaire = new Float(0);
    private Float resultatFiscal = new Float(0);
    private Float deficit = new Float(0);
    @OneToMany(mappedBy = "declarationIs")
    private List<PaiementIS> paiementISs;
    @OneToOne(mappedBy = "declarationIs")
    private PenaliteIS penaliteIS;

    public DeclarationIs() {
    }

    public DeclarationIs(Long id) {
        this.id = id;
    }

    public DeclarationIs(Float montantIs, Float resultatComptable, Float chiffreAffaire, Float resultatFiscal) {
        this.montantIs = montantIs;
        this.resultatComptable = resultatComptable;
        this.chiffreAffaire = chiffreAffaire;
        this.resultatFiscal = resultatFiscal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateDeclaration() {
        return dateDeclaration;
    }

    public void setDateDeclaration(Date dateDeclaration) {
        this.dateDeclaration = dateDeclaration;
    }

    public Float getMontantIs() {
        return montantIs;
    }

    public void setMontantIs(Float montantIs) {
        this.montantIs = montantIs;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Societe getSociete() {
        if (societe == null) {
            societe = new Societe();
        }
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public List<ExerciceIS> getExerciceISs() {
        if (exerciceISs == null) {
            exerciceISs = new ArrayList();
        }
        return exerciceISs;
    }

    public void setExerciceISs(List<ExerciceIS> exerciceISs) {
        this.exerciceISs = exerciceISs;
    }

    public float getTauxIs() {
        return tauxIs;
    }

    public void setTauxIs(float tauxIs) {
        this.tauxIs = tauxIs;
    }

    public Float getResultatComptable() {
        return resultatComptable;
    }

    public void setResultatComptable(Float resultatComptable) {
        this.resultatComptable = resultatComptable;
    }

    public List<PaiementIS> getPaiementISs() {
        if (paiementISs == null) {
            paiementISs = new ArrayList();
        }
        return paiementISs;
    }

    public void setPaiementISs(List<PaiementIS> paiementISs) {
        this.paiementISs = paiementISs;
    }

    public Float getResultatFiscal() {
        return resultatFiscal;
    }

    public void setResultatFiscal(Float resultatFiscal) {
        this.resultatFiscal = resultatFiscal;
    }

    public Float getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(Float chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }

    public int getNbAccomptePaye() {
        return nbAccomptePaye;
    }

    public void setNbAccomptePaye(int nbAccomptePaye) {
        this.nbAccomptePaye = nbAccomptePaye;
    }

    public Float getDeficit() {
        return deficit;
    }

    public void setDeficit(Float deficit) {
        this.deficit = deficit;
    }

    public PenaliteIS getPenaliteIS() {
        if (penaliteIS == null) {
            penaliteIS = new PenaliteIS();
        }
        return penaliteIS;
    }

    public void setPenaliteIS(PenaliteIS penaliteIS) {
        this.penaliteIS = penaliteIS;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeclarationIs)) {
            return false;
        }
        DeclarationIs other = (DeclarationIs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DeclarationIs{" + "id=" + id + ", societe=" + societe + ", exerciceISs=" + exerciceISs + ", dateDeclaration=" + dateDeclaration + ", etat=" + etat + ", nbAccomptePaye=" + nbAccomptePaye + ", montantIs=" + montantIs + ", tauxIs=" + tauxIs + ", resultatComptable=" + resultatComptable + ", chiffreAffaire=" + chiffreAffaire + ", resultatFiscal=" + resultatFiscal + ", deficit=" + deficit + ", paiementISs=" + paiementISs + ", penaliteIS=" + penaliteIS + '}';
    }

}
