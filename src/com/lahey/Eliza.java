package com.lahey;
/**
 * @author jack lahey
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Eliza {

    private static final HashMap<String, String> prependMap = new HashMap<String, String>();
    private static final File prependFile = new File("ResponsePrependFile.txt");
    private static final HashMap<String, String> replaceWordMap = new HashMap<String, String>();
    private static final File wordFile = new File("WordReplacementsFile.txt");

    private static final ArrayList<String> hedgeList = new ArrayList<String>();
    private static final File hedgeFile = new File("HedgeResponseFile.txt");
    private static final ArrayList<String> questionList = new ArrayList<String>();
    private static final File questionFile = new File("QuestionPrependFile.txt");

    private ElizaLogger elizalogger = null;


    //*********************************************************************
    //* Default constructor calls methods to load Collections from files
    //*********************************************************************
    public Eliza()
    {
        initReplacementMap();
        initPrependMap();
        initHedgeList();
        initQuestionList();
        elizalogger = new ElizaLogger();
    }

    //*********************************************************************
    //* Clean up closes ElizaLogger File
    //*********************************************************************
    public void cleanup()
    {
        elizalogger.writeLogReport();
        elizalogger.closeFile();
    }

    //*********************************************************************
    // Ths method loads a file of keys and values from a file into  a
    // HashMap
    //*********************************************************************
    private void initReplacementMap(){
        String sKey = "";
        String sValue = "";

       try {

            Scanner scan  = new Scanner(wordFile);

            while (scan.hasNextLine()) {

                sKey = scan.next().toLowerCase();
                sValue = scan.next().toLowerCase();
                scan.nextLine();

                replaceWordMap.put(sKey, sValue);
            }
            scan.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }//end try catch

        //display for testing
//     Set set1 = replaceWordMap.entrySet();
//     Iterator iterator1 = set1.iterator();
//     while(iterator1.hasNext()) {
//         Map.Entry mentry1 = (Map.Entry) iterator1.next();
//         System.out.print("Key is: " + mentry1.getKey() + " & Value is: ");
//         System.out.println(mentry1.getValue());
//        }//end while(iterator1.hasNext())
//        System.out.println("\n");
//
    }//end private void initReplacementMap()


    //*********************************************************************
    // Ths method loads a file of keys and prepends from a file into
    // a HashMap
    //*********************************************************************
    private void initPrependMap() {
        String sKey = "";
        String sValue = "";

        try {
            Scanner scan  = new Scanner(prependFile);

            while (scan.hasNextLine()) {

                sKey = scan.next().toLowerCase();
                sValue = scan.nextLine().substring(1); //.toLowerCase();

                prependMap.put(sKey, sValue);
            }
            scan.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }//end try catch

        //display for testing
//        Set set1 = prependMap.entrySet();
//        Iterator iterator1 = set1.iterator();
//        while(iterator1.hasNext()) {
//            Map.Entry mentry1 = (Map.Entry) iterator1.next();
//            System.out.print("Key is: " + mentry1.getKey() + " & Value is: ");
//            System.out.println(mentry1.getValue());
//        }//end while(iterator1.hasNext())
//        System.out.println("\n");

    }//end private void initPrependMap()


    //*********************************************************************
    // Ths method loads hedges from a file into an ArrayList
    //*********************************************************************
    private void initHedgeList() {
        String sValue = "";

        try {
            Scanner scan  = new Scanner(hedgeFile);

            while (scan.hasNextLine()) {

                sValue = scan.nextLine();
                hedgeList.add(sValue);
            }
            scan.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }//end try catch

        //display for testing
//        for(String s : hedgeList) {
//
//            System.out.println(s);
//        }
    }//end private void iinitHedgeList()


    //*********************************************************************
    // Ths method loads question prepends into an ArrayList
    //*********************************************************************
    private void initQuestionList() {
        String sValue = "";

        try {
            Scanner scan  = new Scanner(questionFile);

            while (scan.hasNextLine()) {

                sValue = scan.nextLine();
                questionList.add(sValue);
            }
            scan.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }//end try catch

        //display for testing
//        for(String s : questionList) {
//
//            System.out.println(s);
//        }
    }//end private void iinitHedgeList()

    //*********************************************************************
    // Ths method processes the customer input based on a randome number
    //*********************************************************************
    public String processResponse(String customerInput) {
        Random rand = new Random();
        ArrayList<String> wordList = new ArrayList<String>();
        StringBuffer strBuff = new StringBuffer();
        String sElizaOutput = "";

        //log Customer input
        elizalogger.writeToFile("Customer: " + customerInput );


        //split customer input into individual words
        for (String word : customerInput.split(" ")) {

            wordList.add(word.toLowerCase());
        }

        replaceCustomerWords(wordList, strBuff);

        //*********************************************************************
        // here the response randomly splits into 3, based on a random number
        // the program will decide how to handle the customer input
        // 1 == customer input will be turned into a qualifying question
        // 2 == customer input will be turned into a prepended statement
        // 3 == a hedge will be returned & customer input ignored
        //*********************************************************************
        int randomNum = rand.nextInt(3) + 1;


        if(randomNum == 1){

//            System.out.println("Qualifying Question");
            sElizaOutput = getQualifyingQuestion(rand, strBuff);
            elizalogger.writeToFile(sElizaOutput);
            return sElizaOutput;

        }else if(randomNum == 2){

//            System.out.println("Prepended Response");
            sElizaOutput = getPrependedResponse(wordList, strBuff);
            elizalogger.writeToFile(sElizaOutput);
            return sElizaOutput;

        }else {

//            System.out.println("Hedge Response");
            sElizaOutput = getHedgeResponse(rand);
            elizalogger.writeToFile(sElizaOutput);
            return sElizaOutput;
        }

    }//end public String processRespnse(String str)

    //*********************************************************************
    // Ths method reverses prounouns and other words
    //*********************************************************************
    private void replaceCustomerWords(ArrayList<String> wordList, StringBuffer strBuff){

        //find replacement words from replacment word file
        for (String word : wordList) {

            if( replaceWordMap.containsKey(word))
            {
                word = replaceWordMap.get(word);
                if(word.equalsIgnoreCase("I")){
                    word = word.toUpperCase();
                }
            }
            strBuff.append(word);
            strBuff.append(" ");
        }//end for (String word : wordList)

    }//end private void replaceCustomerWords(String customerInput, StringBuffer strBuff)


    //*********************************************************************
    // Ths puts the customer input into an ArrayList and prepends a
    // statement response based on words in the customer input
    //*********************************************************************
    private String getPrependedResponse(ArrayList<String> wordList, StringBuffer buff){

        String prependString = "I understand. You feel that";

        //find prepend from map
        for (String word : wordList) {

            if( prependMap.containsKey(word)) {
                prependString = prependMap.get(word);
            }

        }//end for (String word : wordList)

        buff.insert(0 , prependString + " ");
        buff.insert(0 , "Eliza: ");

        buff.append("\b.\n");
        char chTemp = Character.toUpperCase(buff.charAt(0));
        buff.setCharAt(0,chTemp);

        return buff.toString();
    }

    //*********************************************************************
    // Ths method prepends a random question onto the reversed customer
    // input
    //*********************************************************************
    private String getQualifyingQuestion(Random rand, StringBuffer buff){

        String qualifier = "";

        int iSelection = rand.nextInt(questionList.size() - 1 );

        qualifier = questionList.get(iSelection);

        buff.insert(0 , qualifier + " ");
        buff.insert(0 , "Eliza: ");

        buff.append("\b?\n");
        char chTemp = Character.toUpperCase(buff.charAt(0));
        buff.setCharAt(0,chTemp);

        return buff.toString();

    }//end private String getQualifyingQuestion(Random rand, StringBuffer buff)


    //*********************************************************************
    // Ths method returns a hedge response from an ArrayList
    //*********************************************************************
    private String getHedgeResponse(Random rand){

        StringBuffer sbHedge = new StringBuffer();

        int iSelection = rand.nextInt(hedgeList.size() - 1 );

        sbHedge.append(hedgeList.get(iSelection));
        sbHedge.append("\n");
        sbHedge.insert(0 , "Eliza: ");

        return sbHedge.toString();

    }//end     private String getHedgeResponse(Random rand)


}//end public class Eliza
