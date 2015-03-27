package com.acesounderglass.hungertracker;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.*;


/**
 * Created by elvan on 3/14/2015.
 */
public class ActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity activity;
    EditText inputText;
    TextView outputText;
    HungerTrackerWriterMock mockWriter;
    HungerTrackerWriter mockitoWriter;

    public ActivityTest() {

        super(MainActivity.class);
    }

    //TODO figure out how to do one time set up
    //TODO mock once, in set up, not in text (ew)
    //TODO use mockito, not a hand mock

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

    public void testCanCreateMockito() {
       // mockitoWriter = mock(HungerTrackerWriter.class);
        List mockedList = Mockito.mock(List.class);
    }

    public void testCanInput() {
        sendKeystrokesToInputBox(new int[] {
                KeyEvent.KEYCODE_FORWARD_DEL,
                KeyEvent.KEYCODE_6});
        assertEquals("6", inputText.getText().toString());
    }

    public void testStoreButton() {
        setMockWriter();
        int startWriteToFileCount = mockWriter.getWriteToFileCount();
        clickButton(R.id.store_button);
        assertEquals(startWriteToFileCount+1, mockWriter.getWriteToFileCount());
    }

    public void testClearButton() {
        setMockWriter();
        int startClearCount = mockWriter.getClearFileCount();
        clickButton(R.id.clear_button);
        assertEquals(startClearCount+1, mockWriter.getClearFileCount());

    }

    public void testRetrieveButton() {
        setMockWriter();
        int startRetrieveCount = mockWriter.getRetrieveDataCount();
        clickButton(R.id.retrieve_button);
        assertEquals(startRetrieveCount+1, mockWriter.getRetrieveDataCount());
    }

    private void testButtonExists(int id, String name) {
        View view = activity.findViewById(id);
        assertNotNull("View " + name + " does not exist", view);
    }

    private void sendKeystrokesToInputBox(int[] keycodes) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                inputText.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        for(int keycode : keycodes) {
            this.sendKeys(keycode);
        }
        getInstrumentation().waitForIdleSync();
    }

    private void clickButton(int buttonId) {
        final Button button = (Button) activity.findViewById(buttonId);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                button.requestFocus();
                button.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
    }

    // Necessary because setUp() is run before each test.  Will go away when that is fixed
    private void setMockWriter() {
        if (mockWriter==null) {
            mockWriter = new HungerTrackerWriterMock("", activity.getBaseContext());
            activity.setWriter(mockWriter);
        }
    }
}
