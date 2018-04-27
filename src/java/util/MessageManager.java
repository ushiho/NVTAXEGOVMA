///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package util;
//
//import util.MessageUtil;
//import util.JsfUtil;
//import javax.faces.application.FacesMessage;
//
///**
// *
// * @author moulaYounes
// */
//public class MessageManager {
//
//    MessageUtil message;
//
//    public static MessageUtil createInfoMessage(int resultat, String text) {
//        return new MessageUtil(resultat, text, FacesMessage.SEVERITY_INFO);
//    }
//
//    public static MessageUtil createErrorMessage(int resultat, String text) {
//        return new MessageUtil(resultat, text, FacesMessage.SEVERITY_ERROR);
//    }
//
//    public static MessageUtil createWarnMessage(int resultat, String text) {
//        return new MessageUtil(resultat, text, FacesMessage.SEVERITY_WARN);
//    }
//
//    public MessageManager() {
//    }
//
//    public static void showMessage(MessageUtil message) {
//        if (message != null) {
//            if (message.getSeverity() == FacesMessage.SEVERITY_ERROR) {
//                JsfUtil.addErrorMessage(message.getText());
//            } else if (message.getSeverity() == FacesMessage.SEVERITY_WARN) {
//                JsfUtil.addWrningMessage(message.getText());
//            } else if (message.getSeverity() == FacesMessage.SEVERITY_INFO) {
//                if (message.getText() != null && !message.getText().equals("")) {
//                    JsfUtil.addSuccessMessage(message.getText());
//                }
//            }
//        }
//    }
//
//}
