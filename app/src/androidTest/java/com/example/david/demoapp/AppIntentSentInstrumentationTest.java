package com.example.david.demoapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.AllOf.allOf;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AppIntentSentInstrumentationTest {


    /**
     * A JUnit {@link Rule @Rule} to init and release Espresso Intents before and after each
     * test run.
     * <p>
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link Before @Before} method.
     * <p>
     * This rule is based on {@link ActivityTestRule} and will create and launch the activity
     * for you and also expose the activity under test.
     */
    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(
            MainActivity.class);


    @Test
    public void testShareIntentSent() {
        // Click the button to open the Implicit Activity for sharing
        onView(withId(R.id.main_activity_button_activities)).perform(click());

        // Verify that an intent to the ImplicitActivity was sent with the correct action and package.
        // Think of Intents intended API as the equivalent to Mockito's verify.
        final String ACTION = "mk.ukim.finki.mpip.IMPLICIT_ACTION";
        final String PACKAGE = "mpip.finki.ukim.mk.lab01";
        intended(allOf(
                hasAction(equalTo(ACTION)),
                toPackage(PACKAGE)
        ));
    }
}
