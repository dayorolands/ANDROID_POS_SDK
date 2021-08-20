package com.interswitchng.smartpos.activities

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.google.gson.Gson
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.base.BaseTestActivity
import com.interswitchng.smartpos.mockservices.MockHttpService
import com.interswitchng.smartpos.old_modules.ussdqr.activities.QrCodeActivity
import com.interswitchng.smartpos.old_shared.interfaces.library.HttpService
import com.interswitchng.smartpos.old_shared.models.transaction.ussdqr.response.Transaction
import com.interswitchng.smartpos.utils.Utilities
import com.interswitchng.smartpos.utils.WaitUtils
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules

@RunWith(AndroidJUnit4::class)
class QrCodeInstrumentationTests: BaseTestActivity() {

    @Rule @JvmField
    val activityRule = ActivityTestRule(QrCodeActivity::class.java, true, false)


    @Test
    fun should_show_qr_code_image() {
        activityRule.launchActivity(intent)

        onView(withText(R.string.isw_title_generating_code)).check(matches(isDisplayed()))

        WaitUtils.waitTime(2000)
        onView(withId(R.id.qrCodeImage)).check(matches(isDisplayed()))

        WaitUtils.cleanupWaitTime()
    }

    @Test
    fun should_show_success_message_when_transaction_is_successful() {

        val service: HttpService = MockHttpService.Builder()
                .setPaymentStatusCall {
                    val successString = Utilities.getJson(activityRule.activity, "success-transaction-response.json")
                    val transaction: Transaction = Gson().fromJson(successString, Transaction::class.java)
                    it.onTransactionCompleted(transaction)
                }
                .build()

        loadKoinModules(module(override = true) {
            single { service }
        })

        activityRule.launchActivity(intent)

        WaitUtils.waitTime(3000)
        onView(withText(R.string.isw_sub_title_checking_transaction_status)).check { view, noViewFoundException ->

            Assert.assertNull(noViewFoundException)

            Assert.assertTrue(view.visibility == View.VISIBLE)
        }
        WaitUtils.cleanupWaitTime()


        WaitUtils.waitTime(1000)
        WaitUtils.cleanupWaitTime()
        onView(withText(R.string.isw_title_transaction_completed_successfully)).check { view, noViewFoundException ->

            Assert.assertNull(noViewFoundException)

            Assert.assertTrue(view.visibility == View.VISIBLE)
        }

    }

    @Test
    fun should_show_error_msg_when_transaction_fails() {

        var errorMsg = "error"
        val service: HttpService = MockHttpService.Builder()
                .setPaymentStatusCall {
                    val successString = Utilities.getJson(activityRule.activity,"failed-transaction-response.json")
                    val transaction: Transaction = Gson().fromJson(successString, Transaction::class.java)
                    errorMsg = transaction.responseDescription ?: "error"
                    it.onTransactionError(transaction, null)
                }
                .build()

        loadKoinModules(module(override = true) {
            single { service }
        })


        activityRule.launchActivity(intent)

        WaitUtils.waitTime( 500)
        onView(withText(R.string.isw_sub_title_checking_transaction_status)).check { view, noViewFoundException ->

            Assert.assertNull(noViewFoundException)

            Assert.assertTrue(view.visibility == View.VISIBLE)
        }

        WaitUtils.waitTime(1000)
        onView(withText(errorMsg)).check { view, noViewFoundException ->

            Assert.assertNull(noViewFoundException)

            Assert.assertTrue(view.visibility == View.VISIBLE)
        }
    }

    @Test
    fun should_show_error_msg_when_transaction_times_out() {

        val service: HttpService = MockHttpService.Builder()
                .setPaymentStatusCall { it.onTransactionTimeOut() }
                .build()

        loadKoinModules(module(override = true) {
            single { service }
        })


        activityRule.launchActivity(intent)

        WaitUtils.waitTime( 500)
        onView(withText(R.string.isw_sub_title_checking_transaction_status)).check { view, noViewFoundException ->

            Assert.assertNull(noViewFoundException)

            Assert.assertTrue(view.visibility == View.VISIBLE)
        }

        WaitUtils.waitTime(1000)
        onView(withText(R.string.isw_content_transaction_in_progress_time_out)).check { view, noViewFoundException ->

            Assert.assertNull(noViewFoundException)

            Assert.assertTrue(view.visibility == View.VISIBLE)
        }

        WaitUtils.cleanupWaitTime()
    }

}