/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author User
 */
@Entity
public class DeclarationTva implements Serializable {

    //le contrib doit choisi un seul regime par annee non modifiable 
    //selon son chiffre d affaire
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeclaration;
    private Float tvaDue = new Float(0);//la tva due = rab7 dyalo*
    private Float tvaDeductible = new Float(0);//bach chra
    private Float montantAverser = new Float(0);//la tvaDue - tvaDeduct
    private Float creditAreporter = new Float(0);
    private int etat;// 0 : declarer ;1:valider ; 2 : payer

    @ManyToOne
    private Societe societe;
    @OneToOne(mappedBy = "declarationTva")
    private ExerciceTVA exerciceTVAVente;
    @OneToOne
    private ExerciceTVA exerciceTVAAchat;
    @OneToOne(mappedBy = "declarationTva")
    private PaiementTVA paiementTVA;

    public DeclarationTva() {
    }

    public DeclarationTva(Long id) {
        this.id = id;
    }

    public DeclarationTva(Date dateDeclaration, int etat) {
        this.dateDeclaration = dateDeclaration;
        this.etat = etat;
    }

    

    public Date getDateDeclaration() {
        return dateDeclaration;
    }

    public void setDateDeclaration(Date dateDeclaration) {
        this.dateDeclaration = dateDeclaration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public ExerciceTVA getExerciceTVAVente() {
        if (exerciceTVAVente == null) {
            exerciceTVAVente = new ExerciceTVA();
        }
        return exerciceTVAVente;
    }

    public void setExerciceTVAVente(ExerciceTVA exerciceTVAVente) {
        this.exerciceTVAVente = exerciceTVAVente;
    }

    public Float getTvaDue() {
        return tvaDue;
    }

    public void setTvaDue(Float tvaDue) {
        this.tvaDue = tvaDue;
    }

    public Float getTvaDeductible() {
        return tvaDeductible;
    }

    public void setTvaDeductible(Float tvaDeductible) {
        this.tvaDeductible = tvaDeductible;
    }

    public Float getMontantAverser() {
        return montantAverser;
    }

    public void setMontantAverser(Float montantAverser) {
        this.montantAverser = montantAverser;
    }

    public Float getCreditAreporter() {
        return creditAreporter;
    }

    public void setCreditAreporter(Float creditAreporter) {
        this.creditAreporter = creditAreporter;
    }

    public ExerciceTVA getExerciceTVAAchat() {
        if (exerciceTVAAchat == null) {
            exerciceTVAAchat = new ExerciceTVA();
        }
        return exerciceTVAAchat;
    }

    public void setExerciceTVAAchat(ExerciceTVA exerciceTVAAchat) {
        this.exerciceTVAAchat = exerciceTVAAchat;
    }

    public PaiementTVA getPaiementTVA() {
        if (paiementTVA == null) {
            paiementTVA = new PaiementTVA();
        }
        return paiementTVA;
    }

    public void setPaiementTVA(PaiementTVA paiementTVA) {
        this.paiementTVA = paiementTVA;
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
        if (!(object instanceof DeclarationTva)) {
            return false;
        }
        DeclarationTva other = (DeclarationTva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DeclarationTva{" + "id=" + id + ", dateCreation=" + dateDeclaration + ", taxeDue=" + tvaDue + ", taxeDeductible=" + tvaDeductible + ", taxeAverser=" + montantAverser + ", taxeAreporter=" + creditAreporter + '}';
    }

}
