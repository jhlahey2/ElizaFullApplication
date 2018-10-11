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


    public Eliza()
    {
        //display for testing
//        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        initReplacementMap();
        initPrependMap();
        initHedgeList();
        initQuestionList();
    }

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
        for(String s : hedgeList) {

            System.out.println(s);
        }
    }//end private void iinitHedgeList()


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
        for(String s : questionList) {

            System.out.println(s);
        }
    }//end private void iinitHedgeList()


    public String processResponse(String str) {

        Random rand = new Random();
        ArrayList<String> wordList = new ArrayList<String>();
        StringBuffer strBuff = new StringBuffer();

        //replace words in the original string
        for (String word : str.split(" ")) {

            wordList.add(word.toLowerCase());
        }

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

        //here the response randomly splits into 3, hedge,
       //generate a random number 1, 2, 3
        int randomNum = rand.nextInt(3) + 1;

        if(randomNum == 1){

            System.out.println("Qualifying Quesstion");
            return getQualifyingQuestion(rand, strBuff);

        }else if(randomNum == 2){

            System.out.println("Prepended Response");
            return getPrependedResponse(wordList, strBuff);

        }else {

            System.out.println("Hedge Response");
            return getHedgeResponse(rand);
        }

    }//end public String processRespnse(String str)


    private String getPrependedResponse(ArrayList<String> list, StringBuffer buff){

        String prependString = "I understand. You feel that";

        //find prepend from map
        for (String word : list) {

            if( prependMap.containsKey(word)) {
                prependString = prependMap.get(word);
            }

        }//end for (String word : wordList)

        buff.insert(0 , prependString + " ");

        buff.append("\b.\n");
        char chTemp = Character.toUpperCase(buff.charAt(0));
        buff.setCharAt(0,chTemp);

        return buff.toString();
    }


    private String getQualifyingQuestion(Random rand, StringBuffer buff){

        String qualifier = "";

        int iSelection = rand.nextInt(hedgeList.size() - 1 );

        qualifier = hedgeList.get(iSelection);

        buff.insert(0 , qualifier + " ");

        buff.append("\b?\n");
        char chTemp = Character.toUpperCase(buff.charAt(0));
        buff.setCharAt(0,chTemp);

        return buff.toString();

    }//end private String getQualifyingQuestion(Random rand, StringBuffer buff)


    private String getHedgeResponse(Random rand){

        int iSelection = rand.nextInt(hedgeList.size() - 1 );

        return hedgeList.get(iSelection);

    }//end     private String getHedgeResponse(Random rand)


}//end public class Eliza
