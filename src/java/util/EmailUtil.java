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

    public int sendEmail(Email email, Employe employe) {

        try {

            Properties props = setProps();
            Session mailSession = setSession(props, email);
            mailSession.setDebug(true);
            Message msg = setMessage(mailSession, email, employe);
            Transport.send(msg);
            return 1;

        } catch (MessagingException E) {
            System.out.println(E);
            return -1;
        }
    }

    private Message setMessage(Session mailSession, Email email, Employe employe) throws MessagingException {
        Message msg = new MimeMessage(mailSession);
        //--[ Set the FROM, TO, DATE and SUBJECT fields
        msg.setFrom(new InternetAddress(email.getEmail()));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(employe.getEmail()));
        msg.setSentDate(new Date());
        msg.setSubject(email.getSubject());
        //--[ Create the body of the mail
        msg.setText(email.getContenu());
        return msg;
    }

    private Session setSession(Properties props, Email email) {
        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email.getEmail(), email.getPassword());
            }
        });
        return mailSession;
    }

    private Properties setProps() {
        Properties props = new Properties();
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

}
