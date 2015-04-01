package com.acesounderglass.hungertracker;

import android.content.Context;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by elvan on 3/21/2015.
 */
public class HungerTrackerWriter {

    private String filename;
    private Context mBase;
    private Scanner scanner;

    public HungerTrackerWriter() {

    }

    public HungerTrackerWriter(String filename, Context mBase) {
        this.filename = filename;
        this.mBase = mBase;
    }

    public void writeToFile(String toWrite) {

        try {
            FileOutputStream fos = mBase.openFileOutput(filename, Context.MODE_APPEND);
            fos.write(toWrite.getBytes());
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFileWithDate(String val) {
        String toWrite = getTimeStamp() + "\t" + val + "\n";
        try {
            FileOutputStream fos = mBase.openFileOutput(filename, Context.MODE_APPEND);
            fos.write(toWrite.getBytes());
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private String getTimeStamp() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;
    }


    public void clearFile() {
        try {
            FileOutputStream fos = mBase.openFileOutput(filename, Context.MODE_PRIVATE);
            String empty = "";
            fos.write(empty.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> retrieveAllData() {
        ArrayList<String> list = new ArrayList<>();
        try {
            scanner = new Scanner(mBase.openFileInput(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner.useDelimiter("\n");
        String nextLine  = getNextLine();
        while (nextLine!=null) {
            list.add(nextLine);
            nextLine = getNextLine();
        }

        return list;
    }

    public String getNextLine() {
        if(scanner.hasNext()) {
            return scanner.next();
        }
        return null;
    }

    private String bytesToString(byte[] bytes) {
        return new String(bytes);
    }
}

