/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author User
 */
@Entity
public class TaxeIrEmploye implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private DeclarationIr declarationIr;
    @OneToOne
    private Employe employe;
    @OneToOne
    private CategorieIR categorieIR;
    private Float montantIr;

    public TaxeIrEmploye() {
    }

    public TaxeIrEmploye(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeclarationIr getDeclarationIr() {
        return declarationIr;
    }

    public void setDeclarationIr(DeclarationIr declarationIr) {
        this.declarationIr = declarationIr;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public CategorieIR getCategorieIR() {
        return categorieIR;
    }

    public void setCategorieIR(CategorieIR categorieIR) {
        this.categorieIR = categorieIR;
    }

    public Float getMontantIr() {
        return montantIr;
    }

    public void setMontantIr(Float montantIr) {
        this.montantIr = montantIr;
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
        if (!(object instanceof TaxeIrEmploye)) {
            return false;
        }
        TaxeIrEmploye other = (TaxeIrEmploye) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TaxeIrEmploye{" + "id=" + id + ", montantIr=" + montantIr + '}';
    }

}
