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

        try {

            Properties props = setProps();
            Session mailSession = setSession(props, email);
            mailSession.setDebug(true);
            Message msg = setMessage(mailSession, email, employe);
            Transport.send(msg);
            System.out.println("le contenu de email : "+email.getContenu());
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
        msg.setFrom(new InternetAddress(email.getDgi().getEmail()));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(employe.getEmail()));
        msg.setSentDate(new Date());
        msg.setSubject(email.getSubject());
        //--[ Create the body of the mail
        msg.setText(email.getContenu());
        return msg;
    }

    private static Session setSession(Properties props, Email email) {
        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email.getDgi().getEmail(), email.getDgi().getPasswordEmail());
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
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");

        return props;
    }

}
