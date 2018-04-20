/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author User
 */
@Entity
public class Employe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String preNom;
    private String CIN;
    private String numTele;
    private String numPortable;
    private String email;
    private String profession;

    @ManyToOne
    private Societe societe;
    //pour un utili
    private String login;
    private String motDePasse;
    private String droitFiscale;//"[1, 2]" = redac + valid ; "[2, 3]" = valida + paiem
//    "[1, 2, 3]" = tt les droits
    private String reponse;
    private int questionChoisi;
    @OneToMany(mappedBy = "emeteur")
    private List<Notification> notifications;

    public Employe() {
    }

    public Employe(Long id) {
        this.id = id;
    }

    public Employe(Long id, String nom, String preNom, String CIN, String numTele, String email, String profession, String login, String motDePasse, String droitFiscale) {
        this.id = id;
        this.nom = nom;
        this.preNom = preNom;
        this.CIN = CIN;
        this.numTele = numTele;
        this.email = email;
        this.profession = profession;
        this.login = login;
        this.motDePasse = motDePasse;
        this.droitFiscale = droitFiscale;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPreNom() {
        return preNom;
    }

    public void setPreNom(String preNom) {
        this.preNom = preNom;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public String getNumTele() {
        return numTele;
    }

    public void setNumTele(String numTele) {
        this.numTele = numTele;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getDroitFiscale() {
        return droitFiscale;
    }

    public void setDroitFiscale(String droitFiscale) {
        this.droitFiscale = droitFiscale;
    }

    public List<Notification> getNotifications() {
        if (notifications == null) {
            notifications = new ArrayList();
        }
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public int getQuestionChoisi() {
        return questionChoisi;
    }

    public void setQuestionChoisi(int questionChoisi) {
        this.questionChoisi = questionChoisi;
    }

    public String getNumPortable() {
        return numPortable;
    }

    public void setNumPortable(String numPortable) {
        this.numPortable = numPortable;
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
        if (!(object instanceof Employe)) {
            return false;
        }
        Employe other = (Employe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Employe{" + "id=" + id + ", nom=" + nom + ", preNom=" + preNom + ", CIN=" + CIN + ", numTele=" + numTele + ", numPortable=" + numPortable + ", email=" + email + ", profession=" + profession + ", login=" + login + ", motDePasse=" + motDePasse + ", droitFiscale=" + droitFiscale + ", reponse=" + reponse + ", questionChoisi=" + questionChoisi + '}';
    }
    

}
