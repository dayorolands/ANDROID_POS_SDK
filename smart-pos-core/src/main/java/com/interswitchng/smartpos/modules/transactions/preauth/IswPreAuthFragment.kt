package com.interswitchng.smartpos.modules.transactions.preauth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.interswitchng.smartpos.IswPos.Companion.getNextStan
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.IswConstants
import com.interswitchng.smartpos.shared.fragments.IswBottomSheetFragment
import com.interswitchng.smartpos.shared.fragments.IswTransactionResultFragment
import com.interswitchng.smartpos.shared.models.core.Transaction
import com.interswitchng.smartpos.shared.models.results.IswTransactionResult
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData

internal class IswPreAuthFragment : IswBottomSheetFragment() {

    private lateinit var transactionResult: TransactionResultData
    private lateinit var paymentResult: IswTransactionResult


    override fun onViewCreated(view: View, savedInstance: Bundle?) {
        super.onViewCreated(view, savedInstance)

        // show content fragment
        showCardFlow()
    }

    private fun showCardFlow() {
        // create pre-auth content
        val content = IswPreAuthCardFlowFragment()

        // show content
        show(content)
    }

    fun retryCardTransaction() {
        // create stan for new transaction
        iswPaymentInfo = iswPaymentInfo.copy(currentStan = getNextStan())

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

        fun newInstance(
            transaction: Transaction.PreAuth,
            paymentInfo: IswPaymentInfo
        ): IswPreAuthFragment {
            val fragment = IswPreAuthFragment()
            // create arguments for fragment
            val bundle = Bundle()
            bundle.putParcelable(IswConstants.KEY_PAYMENT_INFO, paymentInfo)

            // set arguments for fragment
            fragment.arguments = bundle
            return fragment
        }
    }
}