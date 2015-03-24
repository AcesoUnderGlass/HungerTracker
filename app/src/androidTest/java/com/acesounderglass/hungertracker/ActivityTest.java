package com.acesounderglass.hungertracker;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by elvan on 3/14/2015.
 */
public class ActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Activity activity;
    EditText inputText;
    TextView outputText;

    public ActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        activity = getActivity();
        assertNotNull(activity);

        // Verify initial state before tests can alter it
        inputText = (EditText) activity.findViewById(R.id.input_text);
        assertNotNull(inputText);
        assertEquals("0", inputText.getText().toString());

        outputText = (TextView) activity.findViewById(R.id.result_text);
        assertNotNull(inputText);
        assertEquals("Start", outputText.getText().toString());

        testButtonExists(R.id.store_button, "Store button");
        testButtonExists(R.id.clear_button, "Clear button");
        testButtonExists(R.id.retrieve_button, "Retrieve button");
    }

    public void testVerifyInput() {
        sendKeystrokesToInputBox(new int[] {KeyEvent.KEYCODE_FORWARD_DEL, KeyEvent.KEYCODE_6});
        assertEquals("6", inputText.getText().toString());
    }

    // TODO Mock out HunterTrackerWriter, add tests for it

    private void testButtonExists(int id, String name) {
        View view = activity.findViewById(id);
        assertNotNull("View " + name + " does not exist", view);
    }

    private void sendKeystrokesToInputBox(int[] keycodes) {

        final EditText inputText = (EditText) activity.findViewById(R.id.input_text);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                inputText.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        for(int keycode : keycodes) {
            this.sendKeys(keycode);
        }
    }
}
