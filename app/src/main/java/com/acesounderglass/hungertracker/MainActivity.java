package com.acesounderglass.hungertracker;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity {
    String FILENAME = "hunger_tracker";
    TextView outputText;
    EditText inputText;
    HungerTrackerWriter writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputText = (TextView) findViewById(R.id.result_text);
        inputText = (EditText) findViewById(R.id.input_text);
        writer = new HungerTrackerWriter(FILENAME, this.getBaseContext());


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
                writer.clearFile();
                }
            }
        );

                Button retrieveButton = (Button) findViewById(R.id.retrieve_button);
        retrieveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                setOutputText(writer.retrieveData());
            }
        });
    }

    public void setWriter(HungerTrackerWriter writer) {
        this.writer = writer;
    }

    private String getDate() {

        String newText = ((EditText) findViewById(R.id.input_text)).getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String currentDateandTime = sdf.format(new Date());
        String string = currentDateandTime + "   " + newText + "\n";

        return string;
    }

    private void writeToFile(String string, int mode) {
        writer.writeToFile(string);
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
