package com.interswitchng.smartpos.modules.transactions.purchase.shared.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.transactions.purchase.IswPurchaseFragment
import com.interswitchng.smartpos.shared.Constants
import com.interswitchng.smartpos.shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import org.koin.android.ext.android.inject

internal abstract class IswBasePaymentFragment: Fragment() {
    // terminal pos device
    val posDevice: POSDevice by inject()
    // sdk instance
    val iswPos: IswPos by inject()

    // key-value store
    val store: KeyValueStore by inject()
    // terminal info
    val terminalInfo by lazy { TerminalInfo.get(store)!! }


    // current payment info
    internal lateinit var paymentInfo: IswPaymentInfo

    // get the tag of this fragment
    internal val idTag get() = this::class.java.simpleName

    // get the parent fragment
    protected val parent: IswPurchaseFragment
        get() = (parentFragment as IswPurchaseFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paymentInfo = arguments?.getParcelable(Constants.KEY_PAYMENT_INFO) ?: return
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        val changePayment = view.findViewById<View?>(R.id.chooseOptionsBtn)

        // add click listener to change payment button
        changePayment?.setOnClickListener {
            parent.goHome()
        }
    }

    // show processing error
    fun showError(error: String, retryAction: () -> Unit) = parent.showError(error, retryAction)

    // show loader using bottom sheet container
    fun showLoader(message: String) = parent.showLoader(message)

    // remove loading fragment
    fun closeLoader() = parent.closeLoader()

    // cancel payment
    fun cancelPayment() = parent.cancelPayment()

    // change options
    fun goHome() = parent.goHome()

    internal fun showTransactionResult(result: TransactionResultData) = parent.showResult(result)
}