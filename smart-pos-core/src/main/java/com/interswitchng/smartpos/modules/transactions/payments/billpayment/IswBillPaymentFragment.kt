package com.interswitchng.smartpos.modules.transactions.payments.billpayment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.IswConstants
import com.interswitchng.smartpos.shared.fragments.IswBottomSheetFragment
import com.interswitchng.smartpos.shared.fragments.IswTransactionResultFragment
import com.interswitchng.smartpos.shared.models.core.Transaction
import com.interswitchng.smartpos.shared.models.results.IswTransactionResult
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import com.interswitchng.smartpos.shared.models.transaction.payments.billpayment.IswBillPaymentInfo

internal class IswBillPaymentFragment : IswBottomSheetFragment() {

    private lateinit var transactionResult: TransactionResultData
    private lateinit var paymentResult: IswTransactionResult

    override fun onViewCreated(view: View, savedInstance: Bundle?) {
        super.onViewCreated(view, savedInstance)

        // show content fragment
        showCardFlow()
    }

    private fun showCardFlow() {
        // create bill-payment content
        val content = IswBillPaymentCardFlowFragment()

        // show content
        show(content)
    }

    fun retryCardTransaction() {
        // create stan for new transaction
        iswPaymentInfo = iswPaymentInfo.copy(currentStan = IswPos.getNextStan())

        // show the card flow
        showCardFlow()
    }

    private fun show(fragment: Fragment) {
        // load card fragment
        childFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.isw_fade_in,
                        R.anim.isw_fade_out,
                        R.anim.isw_fade_in,
                        R.anim.isw_fade_out
                )
                .replace(R.id.currentPage, fragment, fragment.tag)
                // add home page to back navigation
                .addToBackStack(fragment.tag)
                .commit()

        childFragmentManager.executePendingTransactions()

        // toggle bottom sheet lock
        setSheetDraggable(isDraggable = false)
    }

    override fun hasPaymentResult(): Boolean = this::transactionResult.isInitialized

    override fun cancelPayment() = dismiss()

    override fun getResult(): IswTransactionResult = paymentResult

    override fun showResult(result: TransactionResultData) {
        // assign result
        paymentResult = IswTransactionResult(result)

        // assign result
        transactionResult = result

        // create result fragment
        val fragment = IswTransactionResultFragment.newInstance(
                iswPaymentInfo,
                paymentResult,
                result
        )

        // show result fragment
        show(fragment)
    }

    companion object {
        fun newInstance(
                transaction: Transaction.Payments,
                paymentInfo: IswPaymentInfo,
                billInfo: IswBillPaymentInfo?
        ): IswBillPaymentFragment {

            val fragment = IswBillPaymentFragment()
            // create arguments for fragment
            val bundle = Bundle()
            bundle.putParcelable(IswConstants.KEY_PAYMENT_INFO, paymentInfo)
            bundle.putParcelable(IswConstants.KEY_BILL_INFO, billInfo)

            // set arguments for fragment
            fragment.arguments = bundle
            return fragment
        }
    }
}