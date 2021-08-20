package com.interswitchng.smartpos.modules.transactions.completion.fragments

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


internal class IswCompletionFragment: IswBottomSheetFragment() {


    private lateinit var transactionResult: TransactionResultData
    private lateinit var paymentResult: IswTransactionResult


    override fun onViewCreated(view: View, savedInstance: Bundle?) {
        super.onViewCreated(view, savedInstance)

        // show completion flow
        showCompletionFlow()
    }

    fun retryCardTransaction() {
        // create stan for new transaction
        iswPaymentInfo = iswPaymentInfo.copy(currentStan = IswPos.getNextStan())

        // show the card flow
        showCompletionFlow()
    }

    private fun showCompletionFlow() {

        // create completion flow fragment
        val content =
            IswCompletionFlowFragment()

        // show content
        show(content)
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


    /// Base Fragment Overrides
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
        // const val KEY_PURCHASE_TYPE = "key_purchase_type"

        fun newInstance(
            transaction: Transaction.Completion,
            paymentInfo: IswPaymentInfo
        ): IswCompletionFragment {
            val fragment =
                IswCompletionFragment()
            // create arguments for fragment
            val bundle = Bundle()
            bundle.putParcelable(IswConstants.KEY_PAYMENT_INFO, paymentInfo)
            //bundle.putString(KEY_PURCHASE_TYPE, transaction.type?.name)

            // set arguments for fragment
            fragment.arguments = bundle
            return fragment
        }
    }
}