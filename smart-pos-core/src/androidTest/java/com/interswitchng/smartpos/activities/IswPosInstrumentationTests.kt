package com.interswitchng.smartpos.activities

import android.app.Instrumentation
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.old_modules.home.HomeActivity
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IswPosInstrumentationTests {

    private val monitor = Instrumentation.ActivityMonitor(HomeActivity::class.java.simpleName, null, true)

    @Before
    fun setup() {
        InstrumentationRegistry.getInstrumentation().addMonitor(monitor)
    }


    @Test
    fun should_start_main_activity_when_payment_is_initiated() {

        IswPos.getInstance().initiatePayment(monitor.lastActivity, 200000, null)

        val mainActivity = monitor.waitForActivityWithTimeout(10 * 1000) // wait for 10 seconds

        assertNotNull("activity is null", mainActivity)
        assertTrue("activity created is not MainActivity", mainActivity is HomeActivity)
    }

}