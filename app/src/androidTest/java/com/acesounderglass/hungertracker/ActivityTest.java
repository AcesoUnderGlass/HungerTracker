package com.acesounderglass.hungertracker;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import static org.easymock.EasyMock.*;



/**
 * Created by elvan on 3/14/2015.
 */
public class ActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity activity;
    EditText inputText;
    ListView historyList;
    HungerTrackerWriter mockWriter;

    public ActivityTest() {
        super(MainActivity.class);
    }

    //TODO figure out how to do one time set up

    public void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        activity = getActivity();
        assertNotNull(activity);
        getInstrumentation().getTargetContext().getCacheDir();
        System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());


        // Verify initial state before tests can alter it
        inputText = (EditText) activity.findViewById(R.id.input_text);
        assertNotNull(inputText);
        assertEquals("0", inputText.getText().toString());

        historyList = (ListView) activity.findViewById(R.id.history_list);

        testViewExists(R.id.store_button, "Store button");
        testViewExists(R.id.clear_button, "Clear button");
        testViewExists(R.id.retrieve_button, "Retrieve button");
        testViewExists(R.id.history_list, "History list");
        testViewExists(R.id.alarm_button, "Alarm Button");
    }

    public void testCanInput() {
        sendKeystrokesToHungerInputBox(new int[]{
                KeyEvent.KEYCODE_FORWARD_DEL,
                KeyEvent.KEYCODE_6});
        assertEquals("6", inputText.getText().toString());
    }

    public void testStoreButton() {
        setMockWriter();
        mockWriter.writeToFileWithDate("0");
        replay(mockWriter);
        clickButton(R.id.store_button);
        verify(mockWriter);
    }

    public void testStoreButtonEmptyInput() {
        setMockWriter();
        replay(mockWriter);
        sendKeystrokesToHungerInputBox(new int[]{
                KeyEvent.KEYCODE_FORWARD_DEL});
        clickButton(R.id.store_button);
        verify(mockWriter);

    }

    public void testClearButton() {
        setMockWriter();
        mockWriter.clearFile();
        replay(mockWriter);
        clickButton(R.id.clear_button);
        verify(mockWriter);
    }

    public void testNumericInputOnly() {
        sendKeystrokesToHungerInputBox(new int[]{
                KeyEvent.KEYCODE_FORWARD_DEL,
                KeyEvent.KEYCODE_A});
        assertEquals("", inputText.getText().toString());
    }

    public void testSingleDigitInputOnly() {
        sendKeystrokesToHungerInputBox(new int[]{
                KeyEvent.KEYCODE_FORWARD_DEL,
                KeyEvent.KEYCODE_6,
                KeyEvent.KEYCODE_5});
        assertEquals("6", inputText.getText().toString());
    }

    public void testRetrieveButton() {
        setMockWriter();

        String[] data = {"1", "2", "3", "bat", "car"};
        ArrayList<String> expectedList = new ArrayList<String>(Arrays.asList(data));
        expect(mockWriter.retrieveAllData()).andReturn(expectedList);
        replay(mockWriter);

        clickButton(R.id.retrieve_button);

        verify(mockWriter);
        ListAdapter adapter = historyList.getAdapter();
        assertEquals(data.length, adapter.getCount());
        for(int i =0; i<data.length; i++) {
            assertEquals(expectedList.get(i), adapter.getItem(i));
        }
    }

    public void testAlarmButtonNoData() {
        // clear alarm time input box, just in case
        sendKeystrokeToTimeInputBox(new int[]{
                KeyEvent.KEYCODE_FORWARD_DEL,
                KeyEvent.KEYCODE_FORWARD_DEL});
        clickButton(R.id.alarm_button);
    }

    public void testAlarmButtonWithData() {
        sendKeystrokeToTimeInputBox(new int[]{
                KeyEvent.KEYCODE_FORWARD_DEL,
                KeyEvent.KEYCODE_FORWARD_DEL,
                KeyEvent.KEYCODE_1,
                KeyEvent.KEYCODE_2});
        clickButton(R.id.alarm_button);
        sendKeystrokeToTimeInputBox(new int[]{
                KeyEvent.KEYCODE_FORWARD_DEL,
                KeyEvent.KEYCODE_FORWARD_DEL});
    }

    private void testViewExists(int id, String name) {
        View view = activity.findViewById(id);
        assertNotNull("View " + name + " does not exist", view);
    }

    private void sendKeystrokeToTimeInputBox(int[] keycodes) {
        sendKeystrokesToInputBox(
                keycodes,
                (EditText) activity.findViewById(R.id.alarm_time));
    }

    private void sendKeystrokesToHungerInputBox(int[] keycodes) {
        sendKeystrokesToInputBox(keycodes, inputText);
    }

    private void sendKeystrokesToInputBox(int[] keycodes, final EditText textbox) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                textbox.requestFocus();
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
            mockWriter = createMock(HungerTrackerWriter.class);
            activity.setWriter(mockWriter);
        }
    }
}
