package com.acesounderglass.hungertracker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import java.util.ArrayList;
import java.util.Calendar;


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
                String input = getInput();
                if(input.length() > 0) {
                    writer.writeToFileWithDate(getInput());
                }
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
        alarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setAlarm();
            }
        });
    }

    private void setAlarm() {
        String alarmId = "intent_send_notification";

        AlarmManager alarmManager = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
        Intent reminderIntent = new Intent();
        reminderIntent.setAction(alarmId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, reminderIntent, 0);
        long timeToAlarm = getTime();

        if(timeToAlarm == -1) {
            return;
        }
        alarmManager.set(AlarmManager.RTC, getTime(), pendingIntent);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(alarmId);

        BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                createNotification();
            }
        };

        this.registerReceiver(alarmReceiver, intentFilter);
    }

    private long getTime() {
        String newText = ((EditText) findViewById(R.id.alarm_time)).getText().toString();

        if(newText.length() == 0) {
            return -1;
        }
        long convert = Integer.parseInt(newText)*60000;

        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(System.currentTimeMillis());
        return time.getTimeInMillis()+convert;
    }

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

    private String getInput() {

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
