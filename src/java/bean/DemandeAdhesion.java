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
public class DemandeAdhesion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int etat;//1:accepter ; 2:reffuser ; 3:en attent
    @OneToOne
    private Employe contribuable;
    @OneToOne
    private Societe societe;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateEnvoyer;

    public DemandeAdhesion(Long id) {
        this.id = id;
    }

    public DemandeAdhesion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employe getContribuable() {
        if (contribuable == null) {
            contribuable = new Employe();
        }
        return contribuable;
    }

    public void setContribuable(Employe contribuable) {
        this.contribuable = contribuable;
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

    public Date getDateEnvoyer() {
        return dateEnvoyer;
    }

    public void setDateEnvoyer(Date dateEnvoyer) {
        this.dateEnvoyer = dateEnvoyer;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
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
        if (!(object instanceof DemandeAdhesion)) {
            return false;
        }
        DemandeAdhesion other = (DemandeAdhesion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Adhesion[ id=" + id + " ]";
    }

}
