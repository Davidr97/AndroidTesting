package com.example.david.demoapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;

import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Random;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ImplicitActivityIntentSentInstrumentationTest {


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
    private int position;
    private List<ResolveInfo> apps;

    @Rule
    public IntentsTestRule<ImplicitActivity> mActivityRule = new IntentsTestRule<>(
            ImplicitActivity.class);

    @Before
    public void setUp() throws Exception {
        Random random = new Random();
        apps = mActivityRule.getActivity().getActivities();
        position = random.nextInt(apps.size());
    }

    @Test
    public void testShareIntentSent() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.activity_implicit_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));

        // Verify that an intent to the clicked Activity was sent with the correct package name.
        // Think of Intents intended API as the equivalent to Mockito's verify.
        ResolveInfo resolveInfo = apps.get(position);
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        String packageName = activityInfo.applicationInfo.packageName;
        intended(toPackage(packageName));
    }
}
