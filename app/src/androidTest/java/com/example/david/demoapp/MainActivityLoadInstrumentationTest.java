package com.example.david.demoapp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * JUnit4 Ui Tests for {@link MainActivity} using the {@link AndroidJUnitRunner}.
 * This class uses the JUnit4 syntax for tests.
 * <p>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityLoadInstrumentationTest {

    /**
     * A JUnit {@link Rule @Rule} to launch your activity under test. This is a replacement
     * for {@link ActivityInstrumentationTestCase2}.
     * <p>
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link Before @Before} method.
     * <p>
     * {@link ActivityTestRule} will create and launch of the activity for you and also expose
     * the activity under test. To get a reference to the activity you can use
     * the {@link ActivityTestRule#getActivity()} method.
     */

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void testIsTextViewDisplayed(){
        performOperation(R.id.main_activity_text_view);
    }

    @Test
    public void testIsCalculatorButtonDisplayed(){
        performOperation(R.id.main_activity_button_text);
    }

    @Test
    public void testIsAppsButtonDisplayed(){
        performOperation(R.id.main_activity_button_activities);
    }

    @Test
    public void testIsShareButtonDisplayed(){
        performOperation(R.id.main_activity_button_share);
    }

    @Test
    public void testIsPickButtonDisplayed(){
        performOperation(R.id.main_activity_button_pick);
    }

    public void performOperation(int resId){
        onView(withId(resId)).check(matches(isDisplayed()));
    }
}