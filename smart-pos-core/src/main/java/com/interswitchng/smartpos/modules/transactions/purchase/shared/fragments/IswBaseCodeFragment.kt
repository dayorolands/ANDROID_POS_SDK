package com.interswitchng.smartpos.modules.transactions.purchase.shared.fragments

import androidx.appcompat.app.AlertDialog
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.PaymentStatus
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.Transaction

internal abstract class IswBaseCodeFragment : IswBasePaymentFragment() {

    // function to hide loader indicator
    abstract fun hideLoader()

    // function to stop checking status
    abstract fun onCheckStopped()

    // function to get transaction result
    abstract fun getTransactionResult(transaction: Transaction): TransactionResultData


    fun handlePaymentStatus(status: PaymentStatus) {
        // clear current loading indicator
        if (status !is PaymentStatus.Pending) hideLoader()

        // else handle status
        when (status) {

            is PaymentStatus.Complete -> {
                // get the transaction result
                val result = getTransactionResult(status.transaction)
                // set and complete payment
                showTransactionResult(result)
            }

            // do nothing for ongoing poll, show loading indicator
            is PaymentStatus.OngoingTimeout -> {}

            is PaymentStatus.Timeout -> {
                val title = "Payment not Confirmed"
                val message = "Unable to confirm payment at the moment, please try again in 1 minute."

                // stop polling
                onCheckStopped()

                // show alert dialog
                AlertDialog.Builder(requireContext())
                    .setTitle(title)
                    .setMessage(message)
                    .create().show()
            }

            is PaymentStatus.Error -> {
                // get alert title
                val title =
                    if (status.errorMsg != null) "Network Error"
                    else getString(R.string.isw_title_transaction_error)

                // getResult error message
                val message = status.errorMsg
                    ?: status.transaction?.responseDescription
                    ?: "An error occurred, please try again"

                // stop polling
                onCheckStopped()

                // show alert dialog
                AlertDialog.Builder(requireContext())
                    .setTitle(title)
                    .setMessage(message)
                    .create().show()
            }
        }
    }

}