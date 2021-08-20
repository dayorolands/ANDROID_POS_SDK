package com.interswitchng.smartpos.modules.menu.settings.supervisor.enrollcard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.fragments.IswBottomSheetFragment
import com.interswitchng.smartpos.shared.models.results.IswTransactionResult
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData

internal class EnrollCardBottomSheet : IswBottomSheetFragment() {

    private lateinit var enrollAction: EnrolledCardFragment.EnrollAction

    override fun onViewCreated(view: View, savedInstance: Bundle?) {
        super.onViewCreated(view, savedInstance)

        // show enroll card fragment
        showEnrollCardFlow()
    }

    private fun showEnrollCardFlow() {
        // create pre-auth content
        val fragment = EnrolledCardFragment.newInstance()

        // set enroll action
        fragment.setAction(enrollAction)

        // show content
        show(fragment)
    }

    fun setEnrollAction(action: EnrolledCardFragment.EnrollAction) {
        enrollAction = action
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
    override fun hasPaymentResult(): Boolean = false

    override fun cancelPayment() = dismiss()

    // method is not used, so result can be anything
    override fun getResult(): IswTransactionResult = Unit as Any as IswTransactionResult

    override fun showResult(result: TransactionResultData) = Unit


    companion object {
        fun newInstance() = EnrollCardBottomSheet()
    }
}