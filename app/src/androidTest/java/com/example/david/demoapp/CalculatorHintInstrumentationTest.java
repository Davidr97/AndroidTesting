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
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * JUnit4 Ui Tests for {@link ExplicitActivity} using the {@link AndroidJUnitRunner}.
 * This class uses the JUnit4 syntax for tests.
 * <p>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CalculatorHintInstrumentationTest {

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
    public ActivityTestRule<ExplicitActivity> mActivityRule = new ActivityTestRule<>(
            ExplicitActivity.class);


    @Test
    public void testEditText_OperandOneHint() {
        performOperation(R.string.type_operand_one_hint, R.id.operand_one_edit_text, "first test");
    }

    @Test
    public void testEditText_OperandTwoHint() {
        performOperation(R.string.type_operant_two_hint, R.id.operand_two_edit_text, "second test");
    }

    private void performOperation(int editTextHintResId, int editTextResId, String writtenText){
        // Type the text in the EditText field
        onView(withId(editTextResId)).perform(typeText(writtenText), closeSoftKeyboard());

        // Check if the text is displayed in the UI
        String operandHint = mActivityRule.getActivity().getString(editTextHintResId);
        onView(withId(editTextResId)).check(matches(withHint(operandHint)));
    }
}