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
            sendMail objMail = new sendMail("Grats",totalTime,generatedNumber);

            Thread s = new Thread(objMail);
            s.start();

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



}