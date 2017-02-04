package org.fasttrackit.dev.lesson1.numgenerator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by fasttrackit on 2/4/2017.
 */
public class sendMail implements Runnable{

    private static final String SMTP_HOST_NAME = "revo.space";
    private static final String SMTP_AUTH_USER = "fasttrackit@revo.space";
    private static final String SMTP_AUTH_PWD  = "0G[VD$AnS(3K";

    private String subiect;
    double totalTime;
    int numGuess;

    public sendMail(String subiect, double totalTime, int numGuess) {
        this.subiect=subiect;
        this.totalTime=totalTime;
        this.numGuess=numGuess;
    }

    public void run() {



        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");

        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        // uncomment for debugging infos to stdout
        // mailSession.setDebug(true);
        try {
            Transport transport = mailSession.getTransport();

            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(subiect);
            message.setContent("You guessed in "+totalTime+"sec the number "+ numGuess, "text/plain");
            message.setFrom(new InternetAddress("fasttrackit@revo.space"));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("adrian.boldor@gmail.com"));

            transport.connect();
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.TO));
            System.out.println("mail sent");
            transport.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            String username = SMTP_AUTH_USER;
            String password = SMTP_AUTH_PWD;
            return new PasswordAuthentication(username, password);
        }
    }
}
