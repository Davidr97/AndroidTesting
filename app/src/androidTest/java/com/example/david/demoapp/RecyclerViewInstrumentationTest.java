package com.example.david.demoapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import java.util.List;

public class RecyclerViewInstrumentationTest {

    @Rule
    public ActivityTestRule<ImplicitActivity> mActivityRule = new ActivityTestRule<>(
            ImplicitActivity.class);

    @Test
    public void testRecyclerViewSize(){
        List<String> apps = mActivityRule.getActivity().getApps();
        onView(withId(R.id.activity_implicit_recycler_view)).check(new RecyclerViewItemCountAssertion(apps.size()));
    }

    @Test
    public void testRecyclerViewItems(){
        List<String> apps = mActivityRule.getActivity().getApps();
        onView(withId(R.id.activity_implicit_recycler_view)).check(new RecyclerViewItemsAssertion(apps));
    }


    public class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assert adapter != null;
            assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }

    public class RecyclerViewItemsAssertion implements ViewAssertion {
        private final List<String> expectedItems;

        RecyclerViewItemsAssertion(List<String> expectedItems) {
            this.expectedItems = expectedItems;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            ImplicitActivity.ActivityAdapter adapter = (ImplicitActivity.ActivityAdapter)recyclerView.getAdapter();
            assert adapter != null;
            assertThat(adapter.getItems(),equalTo(expectedItems));
        }
    }
}
