package com.interswitchng.smartpos.base

import android.content.Intent
import com.interswitchng.smartpos.old_shared.Constants
import com.interswitchng.smartpos.old_shared.models.transaction.PaymentInfo

abstract class BaseTestActivity {
    private val paymentInfo = PaymentInfo(2000, "005609")
    val intent = Intent().apply {
        putExtra(Constants.KEY_PAYMENT_INFO, paymentInfo)
    }
}