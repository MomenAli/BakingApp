package com.example.momenali.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.momenali.bakingapp.ui.DetailActivity;
import com.example.momenali.bakingapp.ui.MainActivity;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

/**
 * Created by Momen Ali on 12/24/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeSelectTest {

    public static final String RECIPE_NAME = "Brownies";
    public static final String STEP_DESCRIPTION = "1. Preheat the oven to 350�F. Butter the bottom and sides of a 9\"x13\" pan.";

    private static final int BROWNIES = 1;
    private static final int BROWNIES_STEP = 1;

    private static final int RECIPE_NUMBER = 4;

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mMainActivityTestRule.getActivity().getIdlingResource();

        Espresso.registerIdlingResources(mIdlingResource);

    }

    @Test
    public void clickRecycleViewItem_OpensDetailsActivity() {

        onView(ViewMatchers.withId(R.id.rvMain))
                .perform(RecyclerViewActions.actionOnItemAtPosition(BROWNIES, click()));

        onView(withText(RECIPE_NAME)).check(matches(isDisplayed()));


    }

    @Before
    public void registerFourthIdlingResource() {
        mIdlingResource = mMainActivityTestRule.getActivity().getIdlingResource();

        Espresso.registerIdlingResources(mIdlingResource);

    }

    @Test
    public void checkRecipeNumber() {

        onView(withId(R.id.rvMain)).check(new RecyclerViewItemCountAssertion(RECIPE_NUMBER ));

    }

    @Before
    public void registerSecondIdlingResource() {
        mIdlingResource = mMainActivityTestRule.getActivity().getIdlingResource();

        Espresso.registerIdlingResources(mIdlingResource);

    }

    @Test
    public void clickRecycleViewItem_TestTheActivityTitle() {

        onView(ViewMatchers.withId(R.id.rvMain))
                .perform(RecyclerViewActions.actionOnItemAtPosition(BROWNIES, click()));


        onView(
                allOf(
                        isAssignableFrom(TextView.class),
                        withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(RECIPE_NAME)));

    }

    @Before
    public void registerThirdIdlingResource() {
        mIdlingResource = mMainActivityTestRule.getActivity().getIdlingResource();

        Espresso.registerIdlingResources(mIdlingResource);

    }

    @Test
    public void clickRecycleViewItem_clickStepItem() {

        onView(ViewMatchers.withId(R.id.rvMain))
                .perform(RecyclerViewActions.actionOnItemAtPosition(BROWNIES, click()));

        onView(ViewMatchers.withId(R.id.rvSteps))
                .perform(ViewActions.scrollTo());
        onView(ViewMatchers.withId(R.id.rvSteps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(BROWNIES_STEP, click()));
        onView(withId(R.id.tvStepDescription))
                .check(matches(withText(STEP_DESCRIPTION)));

    }


    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}

class RecyclerViewItemCountAssertion implements ViewAssertion {
    private final int expectedCount;

    public RecyclerViewItemCountAssertion(int expectedCount) {
        this.expectedCount = expectedCount;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assertThat(adapter.getItemCount(), is(expectedCount));
    }
}