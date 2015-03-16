package com.acesounderglass.hungertracker;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    private int foo = 5;

    protected void setUp() throws Exception {
        super.setUp();
        foo = 6;
        createApplication();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testFooEqualsSix() {
        assertEquals(6, foo);
        Log.e("ApplicationTest", "RAN A TEST");
    }

    public void testFooNotFive() {
        assertFalse(5 == foo);
    }

    public void testCanCreateActivity() {
        //MainActivity mainActivity =
    }
}