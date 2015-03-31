package com.acesounderglass.hungertracker;


import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import static org.easymock.EasyMock.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by elvan on 3/30/2015.
 */
public class HungerTrackerWriterTest extends ActivityInstrumentationTestCase2 {

    String fileName = "test-file";
    HungerTrackerWriter hungerTrackerWriter;
    Context mBase;
    Scanner scanner;

    public HungerTrackerWriterTest() {
        super(MainActivity.class);
    }
    public void setUp() {
        mBase = getInstrumentation().getTargetContext();
        hungerTrackerWriter = new HungerTrackerWriter(
            fileName, mBase);

        // Delete all ambient files
        for (String file : mBase.fileList()) {
            mBase.deleteFile(file);
        }

    }

    public void tearDown() {
        getInstrumentation().getTargetContext().deleteFile(fileName);
    }

    public void testCanWriteSingleDataPointAndReadToFile() {
        String testText = "foo";
        assertEquals(0, mBase.fileList().length);
        hungerTrackerWriter.writeToFile(testText);
        String[] fileList = mBase.fileList();
        assertEquals(1, fileList.length);
        assertEquals(fileName, fileList[0]);

        String retrievedText = hungerTrackerWriter.retrieveData();
        assertEquals(testText + "\n", retrievedText);
    }

    public void testCanWriteMultipleDataPointsAndReadToFile() {
        String[] data = {"foo", "bar", "car", "large"};
        for(String d : data) {
            hungerTrackerWriter.writeToFile(d + "\n");
        }

        String[] retrievedText = hungerTrackerWriter.retrieveDataAsList().toArray(
                new String[hungerTrackerWriter.retrieveDataAsList().size()]);

        for(int i=0; i<data.length; i++) {
            assertEquals(data[i], retrievedText[i]);
        }

    }
}
