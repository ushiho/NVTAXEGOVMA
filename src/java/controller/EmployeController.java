package controller;

import bean.Employe;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import java.io.IOException;
import service.EmployeFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import util.SessionUtil;
import util.VerifyRecaptchaUtil;

@Named("employeController")
@SessionScoped
public class EmployeController implements Serializable {

    @EJB
    private EmployeFacade ejbFacade;
    private List<Employe> items = null;
    private Employe selected;
    private boolean show;
    private String path;

    public EmployeController() {
    }

    public EmployeFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(EmployeFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public List<Employe> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
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

    public void hideDetail() {
        setShow(false);
    }

    public void showDetail() {
        setShow(true);
    }

    public Employe getConnected() {
        return ejbFacade.getConnectedUser();
    }

    public void deconnecter() {
        ejbFacade.logout();
        SessionUtil.redirectToPage("login.xhtml");
    }

    public void resetPass() {
        ejbFacade.resetPassword(selected);
    }
}
