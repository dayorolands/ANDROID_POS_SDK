package com.interswitchng.smartpos.activities

import android.content.Intent
import android.widget.Button
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.old_shared.Constants
import com.interswitchng.smartpos.old_shared.activities.TransactionResultActivity
import com.interswitchng.smartpos.old_shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.old_shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.old_shared.models.core.PurchaseResult
import com.interswitchng.smartpos.old_shared.models.transaction.PaymentInfo
import com.interswitchng.smartpos.old_shared.models.printer.info.TransactionType
import com.interswitchng.smartpos.old_shared.models.transaction.PaymentType
import com.interswitchng.smartpos.old_shared.models.transaction.TransactionResult
import com.interswitchng.smartpos.old_shared.models.transaction.cardpaycode.CardType
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class TransactionResultActivityTests {

    private lateinit var activity: TransactionResultActivity
    private lateinit var activityController: ActivityController<TransactionResultActivity>

    @Before
    fun setup() {
        val posDevice: POSDevice = mock()
        val store: KeyValueStore = mock { whenever(mock.getString(any(), any())) doReturn "" }

        loadKoinModules(module(override = true) {
            single { posDevice }
            single { store }
        })

        val paymentInfo = PaymentInfo(2000, "005609")
//        val str = ""
//        val result = TransactionResult(PaymentType.Card,
//                str, str, str, TransactionType.Purchase, str,
//                CardType.None, str, str, str, str, str, str, str, str)

        val intent = Intent().apply {
            putExtra(Constants.KEY_PAYMENT_INFO, paymentInfo)
//            putExtra(TransactionResultActivity.KEY_TRANSACTION_RESULT, result)
        }

        activityController = Robolectric.buildActivity(TransactionResultActivity::class.java, intent).create()
        activity = activityController.get()
    }


    @Test
    fun should_return_correct_purchase_result_when_close_button_is_clicked() {
        val expextedResult = PurchaseResult("", "", PaymentType.Card, CardType.None, "")
        activityController.start()

        Thread.sleep(1000)
        // click the close button
        activity.findViewById<Button>(R.id.closeBtn).performClick()

        val activityShadow = shadowOf(activity)
        val resultIntent = activityShadow.resultIntent

        // get result of activity
//        val actual = IswPos.getResult(resultIntent)
        // check assertions
//        assertEquals(expextedResult, actual)
    }

    @Test
    fun should_return_correct_purchase_result_when_activity_is_destroyed() {
        val expextedResult = PurchaseResult("", "", PaymentType.Card, CardType.None, "")
        activityController.start()

        Thread.sleep(1000)
        activityController.destroy()

        val activityShadow = shadowOf(activity)
        val resultIntent = activityShadow.resultIntent

        // get result of activity
//        val actual = IswPos.getResult(resultIntent)
        // check assertions
//        assertEquals(expextedResult, actual)
    }

}