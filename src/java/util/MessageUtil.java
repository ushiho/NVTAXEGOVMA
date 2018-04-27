/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.faces.application.FacesMessage;
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
        RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreur", "" + msg + ""));
    }
}
