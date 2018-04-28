package controller;

import bean.ExerciceIS;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import service.ExerciceISFacade;

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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import service.DeclarationIsFacade;
import service.EmployeFacade;
import util.DateUtil;
import util.MessageUtil;

@Named("exerciceISController")
@SessionScoped
public class ExerciceISController implements Serializable {

    @EJB
    private service.ExerciceISFacade ejbFacade;
    @EJB
    private DeclarationIsFacade declarationIsFacade;
    @EJB
    private EmployeFacade employeFacade;
    EmployeController employeController = new EmployeController();
    private List<ExerciceIS> items;
    private ExerciceIS selected;
    private String dateDebut;
    private String dateFin;
    private boolean showForm;
    private boolean showTable;
    private float totalCharges = 0;
    private float totalProduits = 0;
    private float totalDeductibles = 0;
    private float totalNonDeductibles = 0;
    private boolean resTraitement;

    /**
     *
     */
    public ExerciceISController() {
    }

    public ExerciceIS getSelected() {
        if (selected == null) {
            selected = new ExerciceIS();
        }
        return selected;
    }

    public void setSelected(ExerciceIS selected) {
        this.selected = selected;
    }

    public ExerciceISFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(ExerciceISFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<ExerciceIS> getItems() {
        if (items == null) {
            items = new ArrayList();
        }
        return items;
    }

    public void setItems(List<ExerciceIS> items) {
        this.items = items;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isShowForm() {
        return showForm;
    }

    public void setShowForm(boolean showForm) {
        System.out.println("rah dkhal l setter !! ");
        this.showForm = showForm;
    }

    public boolean isShowTable() {
        return showTable;
    }

    public void setShowTable(boolean showTable) {
        this.showTable = showTable;
    }

    public float getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(float totalCharges) {
        this.totalCharges = totalCharges;
    }

    public float getTotalProduits() {
        return totalProduits;
    }

    public void setTotalProduits(float totalProduits) {
        this.totalProduits = totalProduits;
    }

    public float getTotalDeductibles() {
        return totalDeductibles;
    }

    public void setTotalDeductibles(float totalDeductibles) {
        this.totalDeductibles = totalDeductibles;
    }

    public float getTotalNonDeductibles() {
        return totalNonDeductibles;
    }

    public void setTotalNonDeductibles(float totalNonDeductibles) {
        this.totalNonDeductibles = totalNonDeductibles;
    }

    public boolean isResTraitement() {
        return resTraitement;
    }

    public void setResTraitement(boolean resTraitement) {
        this.resTraitement = resTraitement;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ExerciceISFacade getFacade() {
        return ejbFacade;
    }

    public ExerciceIS prepareCreate() {
        selected = new ExerciceIS();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ExerciceISCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ExerciceISUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ExerciceISDeleted"));
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
                    ejbFacade.create(selected);
                } else if (persistAction != PersistAction.DELETE) {
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

    public ExerciceIS getExerciceIS(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<ExerciceIS> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ExerciceIS> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ExerciceIS.class)
    public static class ExerciceISControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ExerciceISController controller = (ExerciceISController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "exerciceISController");
            return controller.getExerciceIS(getKey(value));
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
            if (object instanceof ExerciceIS) {
                ExerciceIS o = (ExerciceIS) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ExerciceIS.class.getName()});
                return null;
            }
        }

    }

    public void addToList() {
        selected.setDateDebut(DateUtil.getSqlDateToSaveInDB(dateDebut));
        selected.setDateFin(DateUtil.getSqlDateToSaveInDB(dateFin));
        selected.setSociete(null);//societe d'apres societe !!!
        getItems().add(ejbFacade.clone(selected));
        setShowForm(false);
        setShowTable(true);
        calculTotalMontants(1, selected);
        selected = null;
        setDates("", "");
    }

    public void modifyFromList() {
        System.out.println("dkhl l modify : ");
        if (selected == null) {
            MessageUtil.showMsgSelectToModify();
        } else {
            getItems().remove(selected);
            setDates(DateUtil.formateDate("dd/MM/YYYY", selected.getDateDebut()),
                    DateUtil.formateDate("dd/MM/YYYY", selected.getDateFin()));
            setShowForm(true);
        }
    }

    public void removeFromList() {
        if (selected == null) {
            MessageUtil.showMsgSelectToRemove();
        } else {
            ejbFacade.removeSelectedFromList(getItems(), selected);
            setShowForm(false);
            System.out.println("ha size : " + getItems().size());
            System.out.println("ha liste => : " + items);
            calculTotalMontants(2, selected);
            selected = null;
        }
    }

    private void calculTotalMontants(int operator, ExerciceIS selected) {
        switch (operator) {
            case 1:
                setTotalCharges(getTotalCharges() + selected.getCharges());
                setTotalProduits(getTotalProduits() + selected.getProduits());
                setTotalDeductibles(getTotalDeductibles() + selected.getDeductibles());
                setTotalNonDeductibles(getTotalNonDeductibles() + selected.getNonDeductibles());
                break;
            case 2:
                setTotalCharges(getTotalCharges() - selected.getCharges());
                setTotalProduits(getTotalProduits() - selected.getProduits());
                setTotalDeductibles(getTotalDeductibles() - selected.getDeductibles());
                setTotalNonDeductibles(getTotalNonDeductibles() - selected.getNonDeductibles());
                break;
            default:
                break;
        }

    }

    private void setDates(String dateDebut, String dateFin) {
        setDateDebut(dateDebut);
        setDateFin(dateFin);
    }

    public void validerDeclarationIS() {
        if (!items.isEmpty()) {
            ejbFacade.setSocieteToList(items);
            if (declarationIsFacade.save(getItems()) > 0) {
                setResTraitement(true);
                setShowForm(false);
                setShowTable(false);
            }
        } else {
            MessageUtil.showMsg("Rien à sauvegarder !");
        }
    }
}
