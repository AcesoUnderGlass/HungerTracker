package com.acesounderglass.hungertracker;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityTestCase;

/**
 * Created by elvan on 3/14/2015.
 */
public class ActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public ActivityTest() {
        super(MainActivity.class);
    }

    public void testOne() {
        assertEquals(4, 4);
        Activity activity = getActivity();
        assertNotNull(activity);
    }
}
