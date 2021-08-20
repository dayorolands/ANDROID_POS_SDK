package com.interswitchng.interswitchpossdkdemo;


import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.interswitchng.interswitchpossdkdemo.activities.KeypadActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class KeypadActivityTest {

    private ActivityTestRule<KeypadActivity> activityTestRule = new ActivityTestRule<>(KeypadActivity.class);


    @Test
    public void launch_demo() throws InterruptedException {
        activityTestRule.launchActivity(new Intent());

        onView(withId(R.id.amount)).perform(typeText("5000"));

        Thread.sleep(1000L);

        onView(withId(R.id.btnSubmitAmount)).perform(click());

        onView(withId(R.id.ussdPayment)).perform(click());
        Thread.sleep(4000);


        Thread.sleep(500);
        onView(withId(R.id.banksSpinner)).perform(click());

        Thread.sleep(1500);
        onView(withText("GUARANTY TRUST BANK")).perform(click());

        Thread.sleep(500);
        onView(withId(R.id.btnGetCode)).perform(click());

        Thread.sleep(2000);
        onView(withText("Transaction in progress")).check(matches(isDisplayed()));

        Thread.sleep(40000);
    }

    @Test
    public void launch_demo_for_qr() throws InterruptedException {
        activityTestRule.launchActivity(new Intent());

        onView(withId(R.id.amount)).perform(typeText("5000"));

        Thread.sleep(1000L);

        onView(withId(R.id.btnSubmitAmount)).perform(click());

        onView(withId(R.id.qrPayment)).perform(click());

        Thread.sleep(1000);
        onView(withText("Transaction in progress")).check(matches(isDisplayed()));

        Thread.sleep(40000);
    }
}
