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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author ushiho
 */
@Entity
public class PaiementTVA implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDernierPaiement;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datePaiment;
    private Float mtBase = new Float(0);
    private Float mtTotal = new Float(0);
    private Float mtRetartd = new Float(0);
    @OneToOne
    private DeclarationTva declarationTva;

    public PaiementTVA() {
    }

    public PaiementTVA(Long id, Date dateDernierPaiement, Date datePaiment) {
        this.id = id;
        this.dateDernierPaiement = dateDernierPaiement;
        this.datePaiment = datePaiment;
    }

    public PaiementTVA(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateDernierPaiement() {
        return dateDernierPaiement;
    }

    public void setDateDernierPaiement(Date dateDernierPaiement) {
        this.dateDernierPaiement = dateDernierPaiement;
    }

    public Date getDatePaiment() {
        return datePaiment;
    }

    public void setDatePaiment(Date datePaiment) {
        this.datePaiment = datePaiment;
    }

    public Float getMtBase() {
        return mtBase;
    }

    public void setMtBase(Float mtBase) {
        this.mtBase = mtBase;
    }

    public Float getMtTotal() {
        return mtTotal;
    }

    public void setMtTotal(Float mtTotal) {
        this.mtTotal = mtTotal;
    }

    public Float getMtRetartd() {
        return mtRetartd;
    }

    public void setMtRetartd(Float mtRetartd) {
        this.mtRetartd = mtRetartd;
    }

    public DeclarationTva getDeclarationTva() {
        if (declarationTva == null) {
            declarationTva = new DeclarationTva();
        }
        return declarationTva;
    }

    public void setDeclarationTva(DeclarationTva declarationTva) {
        this.declarationTva = declarationTva;
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
        if (!(object instanceof PaiementTVA)) {
            return false;
        }
        PaiementTVA other = (PaiementTVA) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.PaiementTVA[ id=" + id + " ]";
    }

}
