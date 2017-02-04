package org.fasttrackit.dev.lesson1.numgenerator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by condor on 29/11/14.
 * FastTrackIT, 2015
 */


/* didactic purposes

Some items are intentionally not optimized or not coded in the right way

FastTrackIT 2015

*/

public class NumGeneratorBusinessLogic {



    private static final int MAX_NUMBER = 10;

    private boolean isFirstTime = true;
    private boolean successfulGuess;
    private int numberOfGuesses;
    private int generatedNumber;
    private String hint;
    private long startTime;
    private double totalTime;



    public double getTotalTime() {
        return totalTime;
    }

    public NumGeneratorBusinessLogic(){
        resetNumberGenerator();
    }

    public boolean getSuccessfulGuess(){
        return successfulGuess;
    }

    public String getHint(){
        return hint;
    }

    public int getNumGuesses(){
        return numberOfGuesses;
    }

    public boolean isFirstTime(){
        return isFirstTime;
    }

    public void resetNumberGenerator(){
        isFirstTime = true;
        numberOfGuesses = 0;
        hint = "";
    }

    public boolean determineGuess(int guessNumber){
        if (isFirstTime) {
            generatedNumber = NumGenerator.generate(MAX_NUMBER);
            System.out.println("gennr:"+generatedNumber);
            isFirstTime = false;
            startTime = System.currentTimeMillis();
            System.out.println("we started at"+startTime);
        }
        numberOfGuesses++;
        System.out.println("numarul generat" + generatedNumber);
        System.out.println("numarul ghicit" + guessNumber);

        if (guessNumber == generatedNumber) {
            hint="";
            successfulGuess = true;
            sendEmail();

            long stopTime = System.currentTimeMillis();
            System.out.println("we stoped at"+stopTime);

            totalTime = (stopTime - startTime) /1000.0;

            System.out.println("total time is " + totalTime);

        } else if (guessNumber < generatedNumber) {
            hint = "higher";
            successfulGuess = false;
            long stopTime = System.currentTimeMillis();
            System.out.println("we stoped at"+stopTime);

            totalTime = (stopTime - startTime) /1000.0;

            System.out.println("total time is " + totalTime);
        } else if (guessNumber > generatedNumber) {
            hint = "lower";
            successfulGuess = false;
            long stopTime = System.currentTimeMillis();
            System.out.println("we stoped at"+stopTime);

            totalTime = (stopTime - startTime) /1000.0;

            System.out.println("total time is " + totalTime);
        }
        return successfulGuess;
    }

    private static final String SMTP_HOST_NAME = "revo.space";
    private static final String SMTP_AUTH_USER = "fasttrackit@revo.space";
    private static final String SMTP_AUTH_PWD  = "0G[VD$AnS(3K";

    public void sendEmail() {
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
            message.setContent("This is a test", "text/plain");
            message.setFrom(new InternetAddress("fasttrackit@revo.space"));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("adrian.boldor@gmail.com"));

            transport.connect();
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.TO));
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