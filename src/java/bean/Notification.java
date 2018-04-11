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
import javax.persistence.Temporal;

/**
 *
 * @author ushiho
 */
@Entity
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateRecu;
    @ManyToOne
    private Employe emeteur;
    @ManyToOne
    private Employe recepteur;
    private int cas;//2 : urgent ; 1 : normal
    private boolean vue;
    private int declaration;// 1 : ir ; 2: is ; 3: tva

    public Notification() {
    }

    public Notification(Long id, Date dateRecu, int cas, boolean vue, int declaration) {
        this.id = id;
        this.dateRecu = dateRecu;
        this.cas = cas;
        this.vue = vue;
        this.declaration = declaration;
    }

    public Notification(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateRecu() {
        return dateRecu;
    }

    public void setDateRecu(Date dateRecu) {
        this.dateRecu = dateRecu;
    }

    public Employe getEmeteur() {
        if (emeteur == null) {
            emeteur = new Employe();
        }
        return emeteur;
    }

    public void setEmeteur(Employe emeteur) {
        this.emeteur = emeteur;
    }

    public Employe getRecepteur() {
        return recepteur;
    }

    public void setRecepteur(Employe recepteur) {
        if (recepteur == null) {
            recepteur = new Employe();
        }
        this.recepteur = recepteur;
    }

    public int getCas() {
        return cas;
    }

    public void setCas(int cas) {
        this.cas = cas;
    }

    public boolean isVue() {
        return vue;
    }

    public void setVue(boolean vue) {
        this.vue = vue;
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
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Notification[ id=" + id + " ]";
    }

}
