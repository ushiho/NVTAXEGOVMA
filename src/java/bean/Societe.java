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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author User
 */
@Entity
public class Societe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long idFiscal;//generer par dgi
    private String password;//genere par dgi
    private String raisonSociale;
    private String numTele;
    private String numFix;
    private String email;
    private Float deficitIS;//resultat fiscal < 0
    private Float deficitTVA;//resultat tva < 0
    private int regimeTVA;//1:mensuelle ; 3: trimest

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCreation;//1,2,...5

    @OneToMany(mappedBy = "societe")
    private List<CompteBanquaire> compteBanquaires;
    @OneToMany(mappedBy = "societe")
    private List<Employe> employes;
    @OneToMany(mappedBy = "societe")
    private List<DeclarationIs> declarationIss;

    public Societe(long id, long idFiscal, String password, String raisonSociale,
            String numTele, String numFix, String email, Float deficitIS, Float deficitTVA, int regimeTVA, Date dateCreation) {
        this.id = id;
        this.idFiscal = idFiscal;
        this.password = password;
        this.raisonSociale = raisonSociale;
        this.numTele = numTele;
        this.numFix = numFix;
        this.email = email;
        this.deficitIS = deficitIS;
        this.deficitTVA = deficitTVA;
        this.regimeTVA = regimeTVA;
        this.dateCreation = dateCreation;
    }

    public Societe(long idFiscal, String password) {
        this.idFiscal = idFiscal;
        this.password = password;
    }

    public Societe(long id) {
        this.idFiscal = id;
    }

    public Societe() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdFiscal() {
        return idFiscal;
    }

    public void setIdFiscal(long idFiscal) {
        this.idFiscal = idFiscal;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getNumTele() {
        return numTele;
    }

    public void setNumTele(String numTele) {
        this.numTele = numTele;
    }

    public String getNumFix() {
        return numFix;
    }

    public void setNumFix(String numFix) {
        this.numFix = numFix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CompteBanquaire> getCompteBanquaires() {
        if (compteBanquaires == null) {
            compteBanquaires = new ArrayList();
        }
        return compteBanquaires;
    }

    public void setCompteBanquaires(List<CompteBanquaire> compteBanquaires) {
        this.compteBanquaires = compteBanquaires;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Float getDeficitIS() {
        if (deficitIS == null) {
            deficitIS = 0f;
        }
        return deficitIS;
    }

    public void setDeficitIS(Float deficitIS) {
        this.deficitIS = deficitIS;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Employe> getEmployes() {
        if (employes == null) {
            employes = new ArrayList();
        }
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public Float getDeficitTVA() {
        return deficitTVA;
    }

    public void setDeficitTVA(Float deficitTVA) {
        this.deficitTVA = deficitTVA;
    }

    public int getRegimeTVA() {
        return regimeTVA;
    }

    public void setRegimeTVA(int regimeTVA) {
        this.regimeTVA = regimeTVA;
    }

    public List<DeclarationIs> getDeclarationIss() {
        if (declarationIss == null) {
            declarationIss = new ArrayList();
        }
        return declarationIss;
    }

    public void setDeclarationIss(List<DeclarationIs> declarationIss) {
        this.declarationIss = declarationIss;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idFiscal;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the idFiscal fields are not set
        if (!(object instanceof Societe)) {
            return false;
        }
        Societe other = (Societe) object;
        if (this.idFiscal != other.idFiscal) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "raisonSociale=" + raisonSociale;
    }

}
