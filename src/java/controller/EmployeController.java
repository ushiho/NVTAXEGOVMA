package controller;

import bean.Employe;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import java.io.IOException;
import service.EmployeFacade;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.mail.Message;
import org.primefaces.context.RequestContext;
import service.CompteBanquaireFacade;
import service.SocieteFacade;
import util.FieldValidatorUtil;
import util.MessageUtil;
import util.SessionUtil;
import util.VerifyRecaptchaUtil;

@Named("employeController")
@SessionScoped
public class EmployeController implements Serializable {

    @EJB
    private EmployeFacade ejbFacade;
    @EJB
    private CompteBanquaireFacade compteBanquaireFacade;
    @EJB
    private SocieteFacade societeFacade;
    private List<Employe> items;
    private Employe selected;
    private boolean showForm;
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

    public String getConfirmer() {
        if (confirmer == null) {
            confirmer = "";
        }
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
        System.out.println("ha l val table !!! hna f employe controller :" + showTable);
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

    public CompteBanquaireFacade getCompteBanquaireFacade() {
        return compteBanquaireFacade;
    }

    public void setCompteBanquaireFacade(CompteBanquaireFacade compteBanquaireFacade) {
        this.compteBanquaireFacade = compteBanquaireFacade;
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

    public Employe userData() {
        return ejbFacade.getConnectedUser("data");
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
            MessageUtil.showMsg("Entrer un email valid");
        }

    }

    

    public void sendCodeToVirefyEmail() {
        if (!userData().getEmail().equals(confirmer)) {
            MessageUtil.showMsg("LES DEUX ADRESSES EMAIL NE SONT PAS EGAUX !");
        } else if (ejbFacade.sendCodeToVirefyEmail(userData()) < 0) {
            MessageUtil.showMsg("ADRESSE EMAIL EST INCORRECT !");
        } else {
            initParams();
            SessionUtil.redirectToPage("adhesionE2");
        }
    }

    public void verifyCodeSent() {
        System.out.println("dans l' E2");
        if (!userData().getMotDePasse().equals(confirmer)) {
            MessageUtil.showMsg("CODE EST INCORRECT !");
        } else {
            System.out.println((Employe) SessionUtil.getAttribute("data"));
            System.out.println("ha  selected : " + selected);
            initParams();
            SessionUtil.redirectToPage("adhesionE3");
        }
    }

    private void initParams() {
        confirmer = "";
        selected = null;
    }

    public void testTwoPassword() {
        System.out.println("dans E3 : data est " + userData());
        System.out.println("avant selected est : " + selected);
        if (!userData().getMotDePasse().equals(confirmer)) {
            MessageUtil.showMsg("LES DEUX MOTS DE PASSE NE SONT PAS EGAUX !");
        } else if (userData().getMotDePasse().length() < 6) {
            MessageUtil.showMsg("LE MOT DE PASSE DOIT CONTENIR AU MOINS 6 CARACTÈRES !");
        } else {
            SessionUtil.redirectToPage("adhesionE4");
            initParams();
        }
        System.out.println("apres selected : " + userData());
    }

    public void saveListInSession() {
        System.out.println("ha list : employe.droit " + items.get(0).getDroitFiscale());
        SessionUtil.setAttribute("listEmploye", items);
        SessionUtil.redirectToPage("validerAdhesion");
        items = new ArrayList();
    }

    public List<Employe> listData() {
        return (List<Employe>) SessionUtil.getAttribute("listEmploye");
    }

    public void addToList() {
        System.out.println("dans E5 : l employe ajoute : " + selected);
        if (ejbFacade.existeInList(getItems(), selected)) {
            MessageUtil.showMsg("CET UILISATEUR EST DÉJA AJOUTÉ ");
        } else if (!selected.getEmail().equals(confirmer)) {
            MessageUtil.showMsg("LES DEUX ADRESSES NE SONT PAS EGAUX !");
        } else {
            selected.setDroitFiscale(ejbFacade.numberTodroit(ejbFacade.checkboxDroitsToNum(droits)));
            items.add(ejbFacade.clone(selected));
            showDetailTable();
            hideForm();
            System.out.println("droit employe est :" + selected.getDroitFiscale());
            initParams();
        }
    }

    public void modifyFromList() {
        if (selected != null) {
            items.remove(selected);
            showDetailForm();
        } else {
            MessageUtil.showMsgSelectToModify();
        }
    }

    public void removeFromList() {
        if (selected != null) {
            ejbFacade.removeSelectedFromList(items, selected);
            selected = null;
        } else {
            MessageUtil.showMsgSelectToRemove();
        }
    }

    public void validerAdhesion() {
        System.out.println("ha l cntribuable :" + userData());
        System.out.println("f valider adhesion : droit = " + listData().get(0).getDroitFiscale());
        if (userData() != null && listData() != null) {
            for (Employe employe : listData()) {
                employe.setDroitFiscale(ejbFacade.droitToNumberToSave(employe.getDroitFiscale()));
                employe.setSociete(userData().getSociete());
                System.out.println("ha user ajoute  : " + employe);
                System.out.println("ha droit d user ajoute  : " + employe.getDroitFiscale());
                if (ejbFacade.addUtilisateur(employe, userData()) < 0) {
                    System.out.println(ejbFacade.addUtilisateur(employe, userData()));
                    return;
                }
            }
            userData().setLogin(""+userData().getSociete().getIdFiscal());
            ejbFacade.save(userData());
            compteBanquaireFacade.saveListComptes(userData().getSociete().getCompteBanquaires());
            societeFacade.edit(userData().getSociete());
            ejbFacade.logout();
            initParams();
            SessionUtil.redirectToPage("seConnecter");
        }
    }

    public void sAdherer() throws IOException {
        int res = ejbFacade.sAdherer(selected);
        boolean recaptcha = VerifyRecaptchaUtil.getRecaptcha();
        System.out.println(res + "ha recaptcha : " + recaptcha);
        System.out.println("ha user : " + userData());
        if (res > 0 && recaptcha) {
            SessionUtil.redirectToPage("verifyIF");
            System.out.println("ha l raison sociale d soc : " + selected.getSociete().getRaisonSociale());
            initParams();
        }
        System.out.println("apres : " + selected);
    }

    public void test() {
        System.out.println("noon");
    }
}
