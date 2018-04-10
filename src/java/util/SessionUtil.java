package util;

import bean.Employe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    private static final SessionUtil instance = new SessionUtil();
    private static List<Employe> users = new ArrayList();

    private SessionUtil() {
    }

    public static void registerUser(Employe user) {
        setAttribute("user", user);
        if(!isConnected(user)){
            users.add(user);
        } else {
        }
    }

//    public static Employe getConnectedUser(String cle) {
//        return (Employe) getAttribute(cle);
//    }

    private static boolean isConnected(Employe user){
        return users.stream().anyMatch((existe) -> (existe.getLogin().equals(user.getLogin())));
    }
    
    public static void setAttribute(String cle, Object valeur) {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc != null && fc.getExternalContext() != null) {
            getSession(fc).setAttribute(cle, valeur);
        }
    }

    private static HttpSession getSession(FacesContext fc) {
        return (HttpSession) fc.getExternalContext().getSession(false);
    }

    public static Object getAttribute(String cle) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Object res = null;
        if (isContextOk(fc)) {
            res = getSession(fc).getAttribute(cle);
        }
        return res;
    }

    private static boolean isContextOk(FacesContext fc) {
        boolean res = (fc != null
                && fc.getExternalContext() != null
                && fc.getExternalContext().getSession(false) != null);
        return res;
    }

    public static void redirect(String pagePath) throws IOException {
//        if (!pagePath.endsWith(".xhtml")) {
//            pagePath += ".xhtml";
//        }
        FacesContext.getCurrentInstance().getExternalContext().redirect(pagePath);

    }

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    // Ajoute des methodes par hamza lotfi
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.
                getCurrentInstance().
                getExternalContext().getRequest();
    }
//
//    public static String getUserName() {
//        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//        return session.getAttribute("username").toString();
//    }
//
//    public static String getUserId() {
//        HttpSession session = getSession();
//        if (session != null) {
//            return (String) session.getAttribute("userid");
//        } else {
//            return null;
//        }
//    }

}
