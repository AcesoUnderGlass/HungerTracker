package com.acesounderglass.hungertracker;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by elvan on 3/21/2015.
 */
public class HungerTrackerWriter {

    String filename;
    Context mBase;

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

    public String retrieveData() {
        byte[] data = new byte[20];
        String string = "";
        try {
            FileInputStream fis = mBase.openFileInput(filename);

            //ArrayList<String> forecasts = new ArrayList<>();
            //TODO: how to tell I've reached end of file?
            for(int i = 0; i < 10; i++) {
                try {
                    fis.read(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                string = string + bytesToString(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // for each line in file: add to list
        // last item: clear list button

        return string;
    }

    private String bytesToString(byte[] bytes) {
        return new String(bytes);
    }
}

