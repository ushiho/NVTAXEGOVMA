/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author moulaYounes
 */
public class MessageUtil {

    public static void showMsgSelectToModify() {
        showMsg("SÉLÉCTIONNER UN UTILISATEUR POUR MODIFIER !");
    }

    public static void showMsgSelectToRemove() {
        showMsg("SÉLÉCTIONNER UN UTILISATEUR !");
    }

    public static void showMsg(String msg) {
        RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreur",  msg));
    }

    public void info(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", msg));
    }

    public void warn(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention!", msg));
    }

    public void error(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", msg));
    }

    public void fatal(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", msg));
    }
}
