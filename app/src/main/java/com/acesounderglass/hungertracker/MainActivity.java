package com.acesounderglass.hungertracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity {
    String FILENAME = "hunger_tracker";
    FileInputStream fis = null;
    TextView outputText;
    EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputText = (TextView) findViewById(R.id.result_text);
        inputText = (EditText) findViewById(R.id.extractEditText);


        // set edit text to editable
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(inputText, InputMethodManager.SHOW_IMPLICIT);

        //Store data in edit field when store button is clicked
        Button storeButton = (Button) findViewById(R.id.store_button);
        storeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writeToFile(getDate(), Context.MODE_APPEND);
        }});

        //Clear all text
        Button clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    writeToFile("", Context.MODE_PRIVATE);
                }
            }
        );

                Button retrieveButton = (Button) findViewById(R.id.retrieve_button);
        retrieveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    fis = openFileInput(FILENAME);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // for each line in file: add to list
                // last item: clear list button

                //ArrayList<String> forecasts = new ArrayList<>();
                byte[] data = new byte[20];
                String string = "";
                //TODO: how to tell I've reached end of file?
                for(int i = 0; i < 10; i++) {
                    try {
                        fis.read(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    string = string + bytesToString(data);
                }

                setOutputText(string);
            }
        });
    }

    private String getDate() {

        String newText = ((EditText) findViewById(R.id.extractEditText)).getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String currentDateandTime = sdf.format(new Date());
        String string = currentDateandTime + "   " + newText + "\n";

        return string;
    }

    private void writeToFile(String string, int mode) {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, mode);
            fos.write(string.getBytes());
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private void setOutputText(String string) {
        outputText.setText(string);
    }

    private String bytesToString(byte[] bytes) {

        return new String(bytes);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
