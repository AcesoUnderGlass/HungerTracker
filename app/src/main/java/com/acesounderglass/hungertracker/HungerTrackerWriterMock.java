package com.acesounderglass.hungertracker;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by elvan on 3/25/2015.
 */
public class HungerTrackerWriterMock extends HungerTrackerWriter {
    int writeToFileCount = 0;
    int clearFileCount = 0;
    int retrieveDataCount = 0;

    public HungerTrackerWriterMock(String filename, Context mBase) {
        super(filename, mBase);
    }

    public void writeToFile(String toWrite) {
        writeToFileCount++;
    }

    public void clearFile() {
        clearFileCount++;
    }

    public String retrieveData() {
        retrieveDataCount++;
        return "";
    }

    public int getWriteToFileCount() { return writeToFileCount; }
    public int getClearFileCount() {return clearFileCount;  }
    public int getRetrieveDataCount() {return retrieveDataCount; }
}
