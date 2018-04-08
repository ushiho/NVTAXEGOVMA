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
import javax.persistence.Temporal;

/**
 *
 * @author ushiho
 */
//c est la partie admin
//////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
@Entity
public class DernierDelaiIS implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int mois;
    private int jour;
    private int accompteAverse; // 1 , 2 ,3 ,4
    private int type;//1:date de dernier delai de declaration / 2 de paiement
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateAppDeCetLoi;

    public DernierDelaiIS() {
    }

    public DernierDelaiIS(Long id) {
        this.id = id;
    }

    public DernierDelaiIS(int mois, int jour, int accompteAverse) {
        this.mois = mois;
        this.jour = jour;
        this.accompteAverse = accompteAverse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

    public int getAccompteAverse() {
        return accompteAverse;
    }

    public void setAccompteAverse(int accompteAverse) {
        this.accompteAverse = accompteAverse;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDateAppDeCetLoi() {
        return dateAppDeCetLoi;
    }

    public void setDateAppDeCetLoi(Date dateAppDeCetLoi) {
        this.dateAppDeCetLoi = dateAppDeCetLoi;
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
        if (!(object instanceof DernierDelaiIS)) {
            return false;
        }
        DernierDelaiIS other = (DernierDelaiIS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Date[ id=" + id + " ]";
    }

}
