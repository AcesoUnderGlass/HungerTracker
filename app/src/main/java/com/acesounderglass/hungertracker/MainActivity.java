package com.acesounderglass.hungertracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    String FILENAME = "hello_file";
    FileInputStream fis = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button storeButton = (Button) findViewById(R.id.store_button);
        try {
            fis = openFileInput(FILENAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        storeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String newText = ((EditText) findViewById(R.id.extractEditText)).getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                String currentDateandTime = sdf.format(new Date());
                String string = currentDateandTime + "   " + newText + "\n";


                try {
                    FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_APPEND);
                    fos.write(string.getBytes());
                    fos.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                        fos.write("".getBytes());
                        fos.close();
                    }catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        );

                Button retrieveButton = (Button) findViewById(R.id.retrieve_button);
        retrieveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // for each line in file: add to list
                // last item: clear list button

                TextView txtView = (TextView) findViewById(R.id.result_text);
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

                txtView.setText(string);

               /* ArrayAdapter<String> list_adaptor = new ArrayAdapter<>(
                        getActivity(), R.layout.history,
                        R.id.history, forecasts
                );

                ListView listView = (ListView) rootView.findViewById(R.id.history);
                listView.setAdapter(list_adaptor);*/

            }
        });


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
