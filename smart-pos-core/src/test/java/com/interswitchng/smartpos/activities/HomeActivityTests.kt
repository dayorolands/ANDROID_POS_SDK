package com.interswitchng.smartpos.activities

import android.app.Application
import org.junit.Test
import android.content.Intent
import android.widget.LinearLayout
import androidx.test.core.app.ApplicationProvider
import com.interswitchng.smartpos.old_modules.home.HomeActivity
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.old_modules.ussdqr.activities.QrCodeActivity
import com.interswitchng.smartpos.old_modules.ussdqr.activities.UssdActivity
import com.interswitchng.smartpos.old_shared.Constants.KEY_PAYMENT_INFO
import com.interswitchng.smartpos.old_shared.models.transaction.PaymentInfo
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf

@RunWith(RobolectricTestRunner::class)
class HomeActivityTests {

    private lateinit var activity: HomeActivity

    @Before
    fun setup() {
        val parcel = PaymentInfo(2000, "")
        val mainIntent = Intent().apply {
            putExtra(KEY_PAYMENT_INFO, parcel)
        }
        activity = Robolectric.buildActivity(HomeActivity::class.java, mainIntent).create().get()
    }


    @Test
    fun `should start qr-code activity when qr-button is clicked`() {

        activity.findViewById<LinearLayout>(R.id.qrPayment).performClick()

        val expectedIntent = Intent(activity, QrCodeActivity::class.java)
        val app: Application =  ApplicationProvider.getApplicationContext()
        val actual = shadowOf(app).nextStartedActivity

        assertEquals(expectedIntent.component, actual.component)
    }

    @Test
    fun `should start ussd activity when ussd button is clicked`() {

        activity.findViewById<LinearLayout>(R.id.ussdPayment).performClick()

        val expectedIntent = Intent(activity, UssdActivity::class.java)
        val app: Application =  ApplicationProvider.getApplicationContext()
        val actual = shadowOf(app).nextStartedActivity

        assertEquals(expectedIntent.component, actual.component)
    }
}