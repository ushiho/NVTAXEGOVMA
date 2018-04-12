package util;

import bean.Email;
import bean.Employe;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil extends Object {

    public static int sendEmail(Email email, Employe employe) {

//        try {
//
//            Properties props = setProps();
//            Session mailSession = setSession(props, email);
//            mailSession.setDebug(true);
//            Message msg = setMessage(mailSession, email, employe);
//            Transport.send(msg);
//            return 1;
//
//        } catch (MessagingException E) {
//            System.out.println("Oops something has gone pearshaped!");
//            System.out.println(E);
//
//            return -1;
//        }
        try {

            Properties props = setProps();

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("hlotfi.hamza.lotfi@gmail.com", "Lotfi1997.#");
                }
            });

            session.setDebug(true); // Enable the debug mode

            Message msg = new MimeMessage(session);

            //--[ Set the FROM, TO, DATE and SUBJECT fields
            msg.setFrom(new InternetAddress("hlotfi.hamza.lotfi@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("lootfi.hamza@gmail.com"));
            msg.setSentDate(new Date());
            msg.setSubject("Hello World!");

            //--[ Create the body of the mail
            msg.setText("Hello from my first e-mail sent with JavaMail");

            //--[ Ask the Transport class to send our mail message
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", 465, "hlotfi.hamza.lotfi@gmail.com", "Lotfi1997.#");
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            Transport.send(msg);
            return 1;

        } catch (MessagingException E) {
            System.out.println("Oops something has gone pearshaped!");
            System.out.println(E);
            return -1;
        }
    }

    private static Message setMessage(Session mailSession, Email email, Employe employe) throws MessagingException {
        Message msg = new MimeMessage(mailSession);
        //--[ Set the FROM, TO, DATE and SUBJECT fields
        msg.setFrom(new InternetAddress("hlotfi.hamza.lotfi@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(employe.getEmail()));
        msg.setSentDate(new Date());
        msg.setSubject(email.getSubject());
        //--[ Create the body of the mail
        msg.setText(email.getContenu());
        return msg;
    }

    private static Session setSession(Properties props, Email email) {
        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("hlotfi.hamza.lotfi@gmail.com", "Lotfi1997.#");
            }
        });
        return mailSession;
    }

    private static Properties setProps() {
        Properties props = new Properties();
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // for gmail use smtp.gmail.com
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        return props;
    }

    public static void main(String[] args) {

//        try {
//
//            Properties props = setProps();
//
//            Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
//
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication("hlotfi.hamza.lotfi@gmail.com", "Lotfi1997.#");
//                }
//            });
//
//            mailSession.setDebug(true); // Enable the debug mode
//
//            Message msg = new MimeMessage(mailSession);
//
//            //--[ Set the FROM, TO, DATE and SUBJECT fields
//            msg.setFrom(new InternetAddress("hlotfi.hamza.lotfi@gmail.com"));
//            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("lootfi.hamza@gmail.com"));
//            msg.setSentDate(new Date());
//            msg.setSubject("Hello World!");
//
//            //--[ Create the body of the mail
//            msg.setText("Hello from my first e-mail sent with JavaMail");
//
//            //--[ Ask the Transport class to send our mail message
//            Transport.send(msg);
//
//        } catch (MessagingException E) {
//            System.out.println("Oops something has gone pearshaped!");
//            System.out.println(E);
//        }
        Email email = new Email();
        Employe employe = new Employe();
        System.out.println(sendEmail(email, employe));
    }
}
