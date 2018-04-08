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
@Entity
public class DernierDelaiTVA implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int jour;
    private int type;//declarartion ou paiemen
    private int regime;//1:mensuel ; 2:trimes
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateAppDeCetLoi;

    public DernierDelaiTVA(Long id) {
        this.id = id;
    }

    public DernierDelaiTVA() {
    }

    public DernierDelaiTVA(int jour, int type, Date dateAppDeCetLoi) {
        this.jour = jour;
        this.type = type;
        this.dateAppDeCetLoi = dateAppDeCetLoi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
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

    public int getRegime() {
        return regime;
    }

    public void setRegime(int regime) {
        this.regime = regime;
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
        if (!(object instanceof DernierDelaiTVA)) {
            return false;
        }
        DernierDelaiTVA other = (DernierDelaiTVA) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.DernierDelaiTVA[ id=" + id + " ]";
    }

}
