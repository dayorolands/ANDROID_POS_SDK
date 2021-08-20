package com.interswitchng.smartpos

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.interswitchng.smartpos.base.BaseTestActivity
import com.interswitchng.smartpos.old_modules.home.HomeActivity
import com.interswitchng.smartpos.utils.WaitUtils
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityWorkFlows: BaseTestActivity() {

    @Rule @JvmField
    val activityRule = ActivityTestRule(HomeActivity::class.java, true, false)

    @Before
    fun setup() {
        activityRule.launchActivity(intent)
    }

    @Test
    fun show_qr_work_flow() {
        WaitUtils.waitTime(500)

        onView(withId(R.id.qrPayment)).perform(click())

        onView(withText(R.string.isw_title_generating_code)).check(matches(isDisplayed()))

        WaitUtils.waitTime(25000)
        onView(withId(R.id.qrCodeImage)).check(matches(isDisplayed()))

        WaitUtils.waitTime(25000)
        onView(withId(R.id.qrCodeImage)).check(matches(isDisplayed()))

        WaitUtils.waitTime(25000)
        onView(withId(R.id.qrCodeImage)).check(matches(isDisplayed()))

        WaitUtils.waitTime(25000)
        onView(withId(R.id.qrCodeImage)).check(matches(isDisplayed()))

        WaitUtils.cleanupWaitTime()
    }


    @Test
    fun show_ussd_work_flow() {
        WaitUtils.waitTime(1500)

        onView(withId(R.id.ussdPayment)).perform(click())

        WaitUtils.waitTime(3500)
        onView(withId(R.id.banksSpinner)).perform(click())

        WaitUtils.waitTime(3500)
        onView(withText("GUARANTY TRUST BANK")).perform(click())

        WaitUtils.waitTime(10000)
        onView(withId(R.id.ussdText)).check(matches(isDisplayed()))

        WaitUtils.cleanupWaitTime()
    }
}