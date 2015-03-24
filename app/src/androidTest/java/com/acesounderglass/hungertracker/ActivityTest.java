package com.acesounderglass.hungertracker;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;

/**
 * Created by elvan on 3/14/2015.
 */
public class ActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Activity activity;

    public ActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        activity = getActivity();
    }

    public void testOne() {
        assertNotNull(activity);
    }

    public void testOutputBox() {
        testButtonExists(R.id.result_text, "output text box");
        TextView outputText = (TextView) activity.findViewById(R.id.result_text);


        String text = outputText.getText().toString();
        assertEquals("Start", text);
    }

    public void testInputBox() {
        testButtonExists(R.id.input_text, "Input text box");
        TextView inputText = (TextView) activity.findViewById(R.id.input_text);

        String text = inputText.getText().toString();
        assertEquals("0", text);
    }

    public void testStoreButtonExists() {
        testButtonExists(R.id.store_button, "Store button");
    }

    public void testClearButtonExists() {
        testButtonExists(R.id.clear_button, "Clear button");
    }

    public void testRetrieveButtonExists() {
        testButtonExists(R.id.retrieve_button, "Retrieve button");
    }

    private void testButtonExists(int id, String name) {
        View view = activity.findViewById(id);
        assertNotNull("View " + name + " does not exist", view);
    }
}
