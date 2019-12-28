/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package envoiMail;

import appliLocation.Alertes;
import genererPDF.Constante;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author ESDRAS
 */
public class Mailer extends Thread {

    public static String ADDRMAIL = "nsoboyz@gmail.com";
    public static String MOTDEPASSE = "nsoboyzexpress";
    private String mail;
    private String nomPDF;

    public Mailer(String mail, String nomPDF) {
        this.mail = mail;
        this.nomPDF = nomPDF;
    }

    public static void main(String[] args) {
        System.out.println(envoiMail("fandioemma@gmail.com"));
    }

    @Override
    public void run() {
        if (envoiMail(mail, Constante.CHEMIN+nomPDF)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alertes.information("Successful Operation", "The mail has been correctly sent");
                }
            });
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alertes.alerte("Operation failed", "The operation failed");
                }
            });
        }
    }

    public static boolean envoiMail(String mail, String nomPDF) {
        return send(ADDRMAIL, MOTDEPASSE, mail, "Report from Nso Boyz", "Here is the report", nomPDF);
    }

    public static boolean envoiMail(String mail) {
        return send(ADDRMAIL, MOTDEPASSE, mail, "Report from Nso Boyz", "Here is the report");
    }

    public static boolean send(String from, String password, String to, String sub, String msg, String nomPDF) {
        //Get properties object    
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session   
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        //compose message
        MimeBodyPart mbp2 = new MimeBodyPart();
        // attach the file to the message
        FileDataSource fds = new FileDataSource(nomPDF);
        try {
            mbp2.setDataHandler(new DataHandler(fds));
            mbp2.setFileName(fds.getName());
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp2);
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            message.setContent(mp);
            //send message  
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            System.out.print(e);
            return false;
        }
    }

    public static boolean send(String from, String password, String to, String sub, String msg) {
        //Get properties object    
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session   
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        //compose message    
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message  
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            System.out.print(e);
            return false;
        }
    }
}
