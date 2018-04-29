package controller;

import bean.DeclarationIs;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import service.DeclarationIsFacade;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.primefaces.context.RequestContext;
import util.DateUtil;

@Named("declarationIsController")
@SessionScoped
public class DeclarationIsController implements Serializable {

    @EJB
    private service.DeclarationIsFacade ejbFacade;
    private List<DeclarationIs> items;
    private DeclarationIs selected;
    private Integer etat;
    private String dateCreationMin;
    private String dateCreationMax;
    private String text;
    private String pageDialod;

    public DeclarationIsController() {
    }

    public DeclarationIs getSelected() {
        if (selected == null) {
            selected = new DeclarationIs();
        }
        return selected;
    }

    public void setSelected(DeclarationIs selected) {
        this.selected = selected;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public String getDateCreationMin() {
        return dateCreationMin;
    }

    public void setDateCreationMin(String dateCreationMin) {
        this.dateCreationMin = dateCreationMin;
    }

    public String getDateCreationMax() {
        return dateCreationMax;
    }

    public void setDateCreationMax(String dateCreationMax) {
        this.dateCreationMax = dateCreationMax;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPageDialod() {
        return pageDialod;
    }

    public void setPageDialod(String pageDialod) {
        this.pageDialod = pageDialod;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DeclarationIsFacade getFacade() {
        return ejbFacade;
    }

    public DeclarationIs prepareCreate() {
        selected = new DeclarationIs();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DeclarationIsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DeclarationIsUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DeclarationIsDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DeclarationIs> getItems() {
        if (items == null) {
            items = new ArrayList();
        }
        return items;
    }

    public void setItems(List<DeclarationIs> items) {
        this.items = items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
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

    public DeclarationIs getDeclarationIs(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<DeclarationIs> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DeclarationIs> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DeclarationIs.class)
    public static class DeclarationIsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DeclarationIsController controller = (DeclarationIsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "declarationIsController");
            return controller.getDeclarationIs(getKey(value));
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
            if (object instanceof DeclarationIs) {
                DeclarationIs o = (DeclarationIs) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DeclarationIs.class.getName()});
                return null;
            }
        }

    }

    public void findByCriteria() {
        setItems(ejbFacade.findByCriteria(etat, DateUtil.getSqlDateToSaveInDB(dateCreationMin),
                DateUtil.getSqlDateToSaveInDB(dateCreationMax)));
        setText("");
        if (getItems() == null || getItems().isEmpty()) {
            setText("Pas de déclarations pour votre recherche actuellement !");
        }
        initParamsSearch();
    }

    private void initParamsSearch() {
        setEtat(null);
        setDateCreationMax("");
        setDateCreationMin("");
    }

    public String etatFromIntToString(int etat) {
        switch (etat) {
            case 0:
                return "Brouillon";
            case 1:
                return "Validée";
            case 2:
                return "Payée";
            default:
                return null;
        }
    }

    public String formatDateToString(Date dateToFormat) {
        System.out.println("ha date to conevrt : " + dateToFormat);
        return DateUtil.formateDate("dd/MM/yyyy", dateToFormat);
    }

    public void showDeclarationIs() {
        System.out.println("ha sele : " + selected);
}

public Map<String, Object> getDialogOptions() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        options.put("height", 400);
        options.put("contentHeight", "100%");
        return options;
        
//        Map<String, Object> options = new HashMap<String, Object>();
//        options.put("modal", true);
//        options.put("width", 640);
//        options.put("height", 340);
//        options.put("contentWidth", "100%");
//        options.put("contentHeight", "100%");
//        options.put("headerElement", "customheader");
    }

    public String getTime() {
        return LocalTime.now().toString();
    }
}
