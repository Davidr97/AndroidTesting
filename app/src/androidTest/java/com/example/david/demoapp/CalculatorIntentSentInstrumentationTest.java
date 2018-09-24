package com.example.david.demoapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.matcher.ComponentNameMatchers;
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
public class CalculatorIntentSentInstrumentationTest {

    private static final String VALID_NUMBER = "10";


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

    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }


    @Test
    public void testCalculatorIntentSent() {
        // Stub all Intents to ExplicitActivity to return VALID_NUMBER. Note that the Activity
        // is never launched and result is stubbed.
        intending(hasComponent(ComponentNameMatchers.hasShortClassName(".ExplicitActivity")))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK,
                        ExplicitActivity.createResultData(VALID_NUMBER)));

        // Click the button.
        onView(withId(R.id.main_activity_button_text)).perform(click());

        // Check that the number is displayed in the UI.
        onView(withId(R.id.main_activity_text_view))
                .check(ViewAssertions.matches(withText(VALID_NUMBER)));
    }
}
