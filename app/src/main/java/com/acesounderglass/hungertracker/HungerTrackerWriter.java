package com.acesounderglass.hungertracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

    private static final String tableName = "fullnessTable";
    private Context mBase;
    private SQLiteDatabase db;
    Cursor cursor;

    public HungerTrackerWriter() {

    }

    public HungerTrackerWriter(String filename, Context mBase) {
        this.mBase = mBase;

        createOrOpenDb();
    }

    private void createOrOpenDb() {
        HungerTrackerSqlHelper dbHelper = new HungerTrackerSqlHelper(mBase);
        db = dbHelper.getWritableDatabase();
        db.execSQL("create table if not exists " + tableName + " (timestamp DATETIME, fullness INT)");
    }

    public void writeToFileWithDate(String val) {
        db.execSQL("insert into " + tableName + "(timestamp, fullness) VALUES('" + getTimeStamp() +
        "', " + val + ")");
    }

    private String getTimeStamp() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;
    }


    public void clearFile() {
        db.delete(tableName, null, null);
    }

    public ArrayList<String> retrieveAllData() {
        ArrayList<String> list = new ArrayList<>();
        cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        cursor.moveToFirst();

        String nextLine  = getNextLine();

        while (nextLine != null) {
            list.add(nextLine);
            nextLine = getNextLine();
        }

        return list;
    }

    public String getNextLine() {

        if (cursor.isAfterLast())
            return null;

        String result = cursor.getString(0) + "\t\t" + cursor.getString(1);
        cursor.moveToNext();
        return result;
    }

    private String bytesToString(byte[] bytes) {
        return new String(bytes);
    }

    private class HungerTrackerSqlHelper extends SQLiteOpenHelper {

        public HungerTrackerSqlHelper(Context context) {
            super(context, "htDB", null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}

