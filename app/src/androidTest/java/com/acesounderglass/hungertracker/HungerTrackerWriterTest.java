package com.acesounderglass.hungertracker;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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

        hungerTrackerWriter.clearFile();
    }

    public void tearDown() {
        getInstrumentation().getTargetContext().deleteFile(fileName);
    }

    public void testWriteToFileWithDate() {
        hungerTrackerWriter.writeToFileWithDate("9");
        hungerTrackerWriter.writeToFileWithDate("2");
        String[] retrievedText = hungerTrackerWriter.retrieveAllData().toArray(
                new String[hungerTrackerWriter.retrieveAllData().size()]);
        System.out.println(retrievedText);
        assertEquals(2, retrievedText.length);
    }
}
