package com.acesounderglass.hungertracker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends ActionBarActivity {
    String FILENAME = "hunger_tracker";
    EditText inputText;
    HungerTrackerWriter writer;
    ListView historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = (EditText) findViewById(R.id.input_text);
        writer = new HungerTrackerWriter(FILENAME, this.getBaseContext());
        historyList = (ListView) findViewById(R.id.history_list);

        // set edit text to editable
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(inputText, InputMethodManager.SHOW_IMPLICIT);

        //Store data in edit field when store button is clicked
        Button storeButton = (Button) findViewById(R.id.store_button);
        storeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writer.writeToFileWithDate(getInput());
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
                displayHistory();
            }
        });

        Button alarmButton = (Button) findViewById(R.id.alarm_button);
        // TODO: change to timed notification
        alarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createNotification();
            }
        });
    }

//    private void setAlarm() {
//        Date futureDate = new Date(new Date().getTime() + 200);
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(this,6767, intent, 0);
//        AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
//        am.set(AlarmManager.RTC_WAKEUP, futureDate.getTime(), pi);
//    }

    protected void createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Hunger Tracker")
                .setContentText("Time to record your fullness level");
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);

        Notification notification = builder.build();
        notificationManager.notify(2, notification);
    }


    private void displayHistory() {
        ArrayList<String> data = writer.retrieveAllData();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                data);
        historyList.setAdapter(adapter);
    }

    public void setWriter(HungerTrackerWriter writer) {
        this.writer = writer;
    }

    private String getInput () {

        String newText = ((EditText) findViewById(R.id.input_text)).getText().toString();
        return newText;
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
