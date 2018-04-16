package controller;

import bean.Employe;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import java.io.IOException;
import service.EmployeFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.context.RequestContext;
import util.FieldValidatorUtil;
import util.SessionUtil;
import util.VerifyRecaptchaUtil;

@Named("employeController")
@SessionScoped
public class EmployeController implements Serializable {

    @EJB
    private EmployeFacade ejbFacade;
    private List<Employe> items;
    private Employe selected;
    private boolean showForm;
    private String codeSent;
    private String confirmer;
    private boolean showTable;
    private int[] droits;

    public EmployeController() {
    }

    public EmployeFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(EmployeFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<Employe> getItems() {
        if (items == null) {
            items = new ArrayList();
        }
        return items;
    }

    public void setItems(List<Employe> items) {
        this.items = items;
    }

    public Employe getSelected() {
        if (selected == null) {
            selected = new Employe();
        }
        return selected;
    }

    public void setSelected(Employe selected) {
        this.selected = selected;
    }

    public String getCodeSent() {
        return codeSent;
    }

    public void setCodeSent(String codeSent) {
        this.codeSent = codeSent;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }

    public boolean isShowForm() {
        return showForm;
    }

    public void setShowForm(boolean showForm) {
        this.showForm = showForm;
    }

    public boolean isShowTable() {
        return showTable;
    }

    public void setShowTable(boolean showTable) {
        this.showTable = showTable;
    }

    public int[] getDroits() {
        return droits;
    }

    public void setDroits(int[] droits) {
        this.droits = droits;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EmployeFacade getFacade() {
        return ejbFacade;
    }

    public Employe prepareCreate() {
        selected = new Employe();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EmployeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EmployeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EmployeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.CREATE) {
                    getFacade().save(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Employe getEmploye(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Employe> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Employe> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Employe.class)
    public static class EmployeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmployeController controller = (EmployeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "employeController");
            return controller.getEmploye(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Employe) {
                Employe o = (Employe) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Employe.class.getName()});
                return null;
            }
        }

    }

    public void seConnecter() throws IOException {
        int res = getFacade().seConnecter(selected);
        boolean recaptcha = VerifyRecaptchaUtil.getRecaptcha();
        System.out.println(res + "ha recaptcha : " + recaptcha);
        if (res > 0) {
            SessionUtil.redirectToPage("profile.xhtml");
        }
    }

    public void hideForm() {
        setShowForm(false);
    }

    public void showDetailForm() {
        setShowForm(true);
    }

    public void hideTable() {
        setShowTable(false);
    }

    public void showDetailTable() {
        setShowTable(true);
    }

    public Employe connectedUser() {
        return ejbFacade.getConnectedUser("user");
    }

    public void userData() {
        selected = ejbFacade.getConnectedUser("data");
    }

    public void addedUsersData() {
        selected = ejbFacade.getConnectedUser("usersAdded");
    }

    public void deconnecter() {
        ejbFacade.logout();
        SessionUtil.redirectToPage("login.xhtml");
    }

    public void resetPass() {
        ejbFacade.resetPassword(selected);
    }

    public void isEmail() {
        System.out.println("inside the methode");
        System.out.println(selected.getEmail());
        System.out.println(confirmer);
        System.out.println(selected.getNom());
        if (!FieldValidatorUtil.isEmail("mmmm")) {
            showMsg("Entrer un email valid");
        }

    }

    public void showMsg(String msg) {
        RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreur", "" + msg + ""));
    }

    public void sendCodeToVirefyEmail() {
        if (ejbFacade.sendCodeToVirefyEmail(selected) < 0) {
            showMsg("email est incorrect !");
        } else {
            SessionUtil.redirectToPage("adhesionE2");
        }
    }

    public void verifyCodeSent() {
        if (!selected.getMotDePasse().equals(codeSent)) {
            showMsg("Code est incorrect !");
        } else {
            SessionUtil.redirectToPage("adhesionE3");
        }
    }

    public void testTwoPassword() {
        if (!selected.getMotDePasse().equals(confirmer)) {
            showMsg("les deux mots de passe sont pas egaux !");
        } else {
            SessionUtil.redirectToPage("adhesionE4");
        }
    }

    public void saveListInSession() {
        SessionUtil.setAttribute("listEmploye", items);
    }

    public void listData() {
        items = (List<Employe>) SessionUtil.getAttribute("listEmploye");
    }

    public void addToList() {
        if (ejbFacade.existeInList(getItems(), selected)) {
            showMsg("CET UILISATEUR EST DÉJA AJOUTÉ ");
        } else {
            selected.setDroitFiscale(ejbFacade.numberTodroit(ejbFacade.checkboxDroitsToNum(droits)));
            items.add(ejbFacade.clone(selected));
        }
        selected = null;
        confirmer = "";
    }

    public void modifyFromList() {
        if (selected != null) {
            items.remove(selected);
            showDetailForm();
        } else {
            showMsg("SÉLÉCTIONNER UN UTILISATEUR POUR MODIFIER !");
        }
    }

    public void removeFromList() {
        if (selected != null) {
            if (items.size() == 1 || selected == items.get(0)) {
                items.remove(0);
                selected = null;
                return;
            }
            items.remove(items.indexOf(selected) + 1);
            selected = null;
        } else {
            showMsg("SÉLÉCTIONNER UN UTILISATEUR !");
        }
    }

    public void saveUsersAdedByContribuable() {
        Employe data = (Employe) SessionUtil.getAttribute("data");
        if (data != null && items != null) {
            for (Employe employe : getItems()) {
                employe.setDroitFiscale(ejbFacade.droitToNumberToSave(employe.getDroitFiscale()));
                employe.setSociete(data.getSociete());
                int res = ejbFacade.addUtilisateur(employe, data);
                if (res < 0) {
                    System.out.println(res);
                    return;
                }
                SessionUtil.setAttribute("usersAdded", items);
            }
        }
    }

    public void test() {
        System.out.println("in the method :");
    }
}
