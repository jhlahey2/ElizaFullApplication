package com.lahey;
import java.io.*;

/**
 * @author jack lahey
 */
public class ElizaLogger {

    private static final String fileName = "ElizaLogFile.txt";

    private static FileWriter fileWriter = null;
    private static BufferedWriter bufferedWriter = null;
    private static FileReader fileReader = null;
    private static BufferedReader bufferedReader = null;


    //*************************************************************************
    //* default constructor
    //*************************************************************************
    public ElizaLogger(){

        openFile();
    }

    //*************************************************************************
    //* open file & truncate if exists (do not append)
    //*************************************************************************
    private void openFile() {

        boolean bAppend = false;

        try{
             // Assume default encoding.
             fileWriter = new FileWriter(fileName, bAppend);

             // Always wrap FileWriter in BufferedWriter.
             bufferedWriter = new BufferedWriter(fileWriter);

             // FileReader reads text files in the default encoding.
             fileReader = new FileReader(fileName);

             // Always wrap FileReader in BufferedReader.
             bufferedReader = new BufferedReader(fileReader);

        }
        catch(IOException e) {

            System.out.println("Error opening file '" + fileName + "'");
            e.printStackTrace();;
        }

    }//end private void openFile()

    //*************************************************************************
    //* write to file
    //*************************************************************************
    public void writeToFile(String sElizaOut){

        try{

            bufferedWriter.write(sElizaOut);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch(IOException e) {

            System.out.println("Error writing to file '" + fileName + "'");
            e.printStackTrace();
        }
    }//end public void writeToFile(String sElizaOut)


    //*************************************************************************
    //*
    //*************************************************************************
    public void writeLogReport() {
        String sOutput = "";

            System.out.println("\n\tLOG REPORT\n");

        try {
                while((sOutput = bufferedReader.readLine()) != null) {

                    System.out.println(sOutput);
            }

        } catch (IOException e) {

            System.out.println("Error writing to file '" + fileName + "'");
            e.printStackTrace();
        }

    }//end public String readFile()

    //*************************************************************************
    //*close file
    //*************************************************************************
    public void closeFile(){

        try{

            bufferedWriter.close();

        }
        catch(IOException e) {

            System.out.println("Error writing to file '" + fileName + "'");
            e.printStackTrace();
        }

    }//end public void closeFile(


}//end public class ElizaLogger
