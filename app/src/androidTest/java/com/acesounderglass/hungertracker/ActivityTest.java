package com.acesounderglass.hungertracker;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityTestCase;
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
        TextView outputText = (TextView) activity.findViewById(R.id.result_text);
        assertNotNull(outputText);

        String text = outputText.getText().toString();
        assertEquals("Start", text);
    }

    public void testInputBox() {
        TextView inputText = (TextView) activity.findViewById(R.id.extractEditText);
        assertNotNull(inputText);

        String text = inputText.getText().toString();
        assertEquals("0", text);
    }
}
