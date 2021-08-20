package com.interswitchng.smartpos.shared.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.transactions.completion.fragments.IswCompletionFragment
import com.interswitchng.smartpos.modules.transactions.payments.billpayment.IswBillPaymentFragment
import com.interswitchng.smartpos.modules.transactions.payments.cashout.IswCashOutFragment
import com.interswitchng.smartpos.modules.transactions.preauth.IswPreAuthFragment
import com.interswitchng.smartpos.modules.transactions.purchase.IswPurchaseFragment
import com.interswitchng.smartpos.modules.transactions.purchase.cashback.IswCashBackFragment
import com.interswitchng.smartpos.modules.transactions.refund.IswRefundFragment
import com.interswitchng.smartpos.shared.IswConstants.KEY_BILL_INFO
import com.interswitchng.smartpos.shared.IswConstants.KEY_PAYMENT_INFO
import com.interswitchng.smartpos.shared.models.core.Transaction
import com.interswitchng.smartpos.shared.models.results.IswTransactionResult
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import com.interswitchng.smartpos.shared.models.transaction.payments.billpayment.IswBillPaymentInfo


internal abstract class IswBottomSheetFragment : Fragment() {

    private data class ColorState(val red: Int, val blue: Int, val green: Int)

    lateinit var iswPaymentInfo: IswPaymentInfo
    lateinit var iswBillInfo: IswBillPaymentInfo

    // layout views
    private lateinit var container: CoordinatorLayout

    // flag to toggle draggable state
    private var isDraggable = true
    // flag to set dismissed state
    private var hasDismissed = false

    // loading fragment to display loaders
    private var loader: IswLoaderFragment? = null


    val isLocked: Boolean get() = !isDraggable
    val isDismissed: Boolean get() = hasDismissed


    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        val arguments = arguments ?: savedInstance

        if (arguments != null) {
            iswPaymentInfo = arguments.getParcelable(KEY_PAYMENT_INFO) ?: return
            iswBillInfo = arguments.getParcelable(KEY_BILL_INFO) ?: return
        }
    }


    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstance: Bundle?): View? {
        return inflater.inflate(R.layout.isw_fragment_bottomsheet, parent, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstance: Bundle?) {

        // get views from layout
        container = view.findViewById(R.id.bottomSheetLayoutContainer)
    }


    // toggle bottom sheet lock (lock or unlock bottomSheet)
    protected fun setSheetDraggable(isDraggable: Boolean) {
        if (this.isDraggable == isDraggable) return
        this.isDraggable = isDraggable
    }

    // show loader screen with a loading message
    fun showLoader(message: String) {
        // get loader state
        val loadingState = IswLoaderFragment
            .LoadingState.Loading(message = message)

        // update loader state and,
        // return if loader is not null
        loader?.let {
            it.setState(state = loadingState)
            return
        }

        // else create the loader and set state
        val loader =
            IswLoaderFragment()
        loader.setState(loadingState)

        // add fragment to view
        addLoader(loader)
    }

    // show a loader screen with an error message
    fun showError(errorMsg: String, retryAction: () -> Unit) {
        // get loader state
        val loadingState = IswLoaderFragment.LoadingState.ErrorLoading(
            errorMessage = errorMsg,
            cancelAction = { cancelPayment() },
            retryAction = retryAction
        )

        // update loader state and,
        // return if loader is not null
        loader?.let {
            it.setState(state = loadingState)
            return
        }

        // else create the loader and set state
        val loader =
            IswLoaderFragment()
        loader.setState(loadingState)

        // add loader view
        addLoader(loader)
    }

    // remove loader from view
    fun closeLoader(animate: Boolean = true) {
        // remove the loader from view hierarchy
        loader?.let {
            childFragmentManager.beginTransaction()
                .let {
                    if (animate) it.setCustomAnimations(
                        R.anim.isw_fade_in,
                        R.anim.isw_fade_out,
                        R.anim.isw_fade_in,
                        R.anim.isw_fade_out
                    )
                    else it
                }
                .remove(it)
                .commit()

            // reset loader
            loader = null
        }
    }

    // attach loader as a child to the view
    private fun addLoader(loader: IswLoaderFragment) {
        // add fragment to view
        childFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.isw_fade_in,
                R.anim.isw_fade_out,
                R.anim.isw_fade_in,
                R.anim.isw_fade_out
            )
            .add(R.id.mainPage, loader)
            .commit()

        // set loader
        this.loader = loader
    }


    // close bottom sheet
    fun dismiss() {
        // send result to calling app
        val callback = activity as? IswPos.IswPaymentCallback

        callback?.apply {
            if (!hasPaymentResult()) onUserCancel()
            else onPaymentCompleted(getResult())
        }

        hasDismissed = true
    }

    // function to determine if a payment
    // result is present before dismissing bottomSheet
    abstract fun hasPaymentResult(): Boolean

    // function to dismiss bottomSheet
    abstract fun cancelPayment()

    // result gotten from a processed payment
    abstract fun getResult(): IswTransactionResult

    // show the result of a transaction
    abstract fun showResult(result: TransactionResultData)

    companion object {
        fun newInstance(transaction: Transaction,
                        paymentInfo: IswPaymentInfo,
                        billInfo: IswBillPaymentInfo? = null
        ): IswBottomSheetFragment {
            return when(transaction) {
                is Transaction.Purchase -> IswPurchaseFragment.newInstance(transaction, paymentInfo)
                is Transaction.PreAuth -> IswPreAuthFragment.newInstance(transaction, paymentInfo)
                is Transaction.Completion -> IswCompletionFragment.newInstance(transaction, paymentInfo)
                is Transaction.Refund -> IswRefundFragment.newInstance(transaction, paymentInfo)
                is Transaction.IFIS -> IswCashOutFragment.newInstance(transaction, paymentInfo)
                is Transaction.Payments -> IswBillPaymentFragment.newInstance(transaction, paymentInfo, billInfo)
                is Transaction.CashBack -> IswCashBackFragment.newInstance(transaction, paymentInfo)
                else -> throw Exception("Transaction Not Implemented")
            }
        }
    }

}