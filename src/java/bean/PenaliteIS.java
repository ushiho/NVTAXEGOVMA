/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author ushiho
 */
@Entity
public class PenaliteIS implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Societe societe;
    private BigDecimal mtPenalite = new BigDecimal(0);
    private BigDecimal mtBase = new BigDecimal(0);
    @OneToOne
    private ConditionPenaliteIS conditionPenalite;
    @OneToOne
    private DeclarationIs declarationIs;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateAppl;

    public PenaliteIS() {
    }

    public PenaliteIS(Long id) {
        this.id = id;
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

    public BigDecimal getMtPenalite() {
        return mtPenalite;
    }

    public void setMtPenalite(BigDecimal mtPenalite) {
        this.mtPenalite = mtPenalite;
    }

    public BigDecimal getMtBase() {
        return mtBase;
    }

    public void setMtBase(BigDecimal mtBase) {
        this.mtBase = mtBase;
    }

    public ConditionPenaliteIS getConditionPenalite() {
        if (conditionPenalite == null) {
            conditionPenalite = new ConditionPenaliteIS();
        }
        return conditionPenalite;
    }

    public void setConditionPenalite(ConditionPenaliteIS conditionPenalite) {
        this.conditionPenalite = conditionPenalite;
    }

    public DeclarationIs getDeclarationIs() {
        if (declarationIs == null) {
            declarationIs = new DeclarationIs();
        }
        return declarationIs;
    }

    public void setDeclarationIs(DeclarationIs declarationIs) {
        this.declarationIs = declarationIs;
    }

    public Date getDateAppl() {
        return dateAppl;
    }

    public void setDateAppl(Date dateAppl) {
        this.dateAppl = dateAppl;
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
        if (!(object instanceof PenaliteIS)) {
            return false;
        }
        PenaliteIS other = (PenaliteIS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Penalite[ id=" + id + " ]";
    }

}
