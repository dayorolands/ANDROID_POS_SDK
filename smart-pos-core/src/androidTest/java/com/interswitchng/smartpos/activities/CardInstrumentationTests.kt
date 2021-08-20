package com.interswitchng.smartpos.activities

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.base.BaseTestActivity
import com.interswitchng.smartpos.old_modules.card.CardActivity
import com.interswitchng.smartpos.utils.WaitUtils
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CardInstrumentationTests: BaseTestActivity() {

    @Rule @JvmField
    val activity = ActivityTestRule(CardActivity::class.java, true, false)


    @Before
    fun setup() {
        activity.launchActivity(intent)
    }

    @Test
    fun loadCardActivity() {

        onView(withId(R.id.insertCardContainer)).check(matches(isDisplayed()))
        onView(withId(R.id.insertPinContainer)).check(matches(not(isDisplayed())))

        WaitUtils.waitTime(2000)
        onView(withId(R.id.insertCardContainer)).check(matches(not(isDisplayed())))
        onView(withId(R.id.insertPinContainer)).check(matches(isDisplayed()))

        onView(withId(R.id.cardPin)).perform(typeText("4444"))

        WaitUtils.waitTime(1500)
        WaitUtils.cleanupWaitTime()
    }
}