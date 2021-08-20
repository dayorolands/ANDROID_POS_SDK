package com.interswitchng.smartpos.modules.transactions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.IswConstants.KEY_BILL_INFO
import com.interswitchng.smartpos.shared.IswConstants.KEY_PAYMENT_INFO
import com.interswitchng.smartpos.shared.fragments.IswBottomSheetFragment
import com.interswitchng.smartpos.shared.models.core.Transaction
import com.interswitchng.smartpos.shared.models.results.IswTransactionResult
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.payments.billpayment.IswBillPaymentInfo

class IswPaymentActivity : AppCompatActivity(), IswPos.IswPaymentCallback {

    private lateinit var bottomSheet: IswBottomSheetFragment
    private lateinit var transaction: Transaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.isw_activity_payment)

        // get transaction type
        transaction = Transaction.readFromBundle(intent)
        // extract payment info
        val info = intent.getParcelableExtra<IswPaymentInfo>(KEY_PAYMENT_INFO)
        val billInfo = intent.getParcelableExtra<IswBillPaymentInfo>(KEY_BILL_INFO)

        // create payment dialog
        createDialog(info, billInfo)
    }


    private fun createDialog(paymentInfo: IswPaymentInfo, billInfo: IswBillPaymentInfo?) {
        // create payment dialog
        bottomSheet = IswBottomSheetFragment.newInstance(transaction, paymentInfo, billInfo)

        supportFragmentManager.beginTransaction()
            .replace(R.id.bottomSheet, bottomSheet)
            .commit()
    }

    override fun onBackPressed() {
        // check if bottom sheet is locked
        val isSheetLocked = bottomSheet.isLocked

        // dismiss bottom sheet if its not locked
        if (!isSheetLocked) bottomSheet.dismiss()
    }

    override fun onUserCancel() {
        finish()
        IswPos.getInstance().cancelPayment()
    }

    override fun onPaymentCompleted(result: IswTransactionResult) {
        // set result and close activity
        finish()
        IswPos.getInstance().setResult(result)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.isw_fade_out)
    }

}
