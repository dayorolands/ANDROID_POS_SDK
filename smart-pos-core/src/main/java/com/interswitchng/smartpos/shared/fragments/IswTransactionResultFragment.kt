package com.interswitchng.smartpos.shared.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.transactions.completion.fragments.IswCompletionFragment
import com.interswitchng.smartpos.modules.transactions.payments.billpayment.IswBillPaymentFragment
import com.interswitchng.smartpos.modules.transactions.payments.cashout.IswCashOutFragment
import com.interswitchng.smartpos.modules.transactions.preauth.IswPreAuthFragment
import com.interswitchng.smartpos.modules.transactions.purchase.cashback.IswCashBackFragment
import com.interswitchng.smartpos.modules.transactions.refund.IswRefundFragment
import com.interswitchng.smartpos.shared.IswConstants
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.core.CURRENCYTYPE
import com.interswitchng.smartpos.shared.models.core.IswLocal
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.core.UserType
import com.interswitchng.smartpos.shared.models.printer.slips.TransactionSlip
import com.interswitchng.smartpos.shared.models.results.IswTransactionResult
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import com.interswitchng.smartpos.shared.utilities.DialogUtils
import com.interswitchng.smartpos.shared.utils.DisplayUtils.toast
import com.interswitchng.smartpos.shared.viewmodel.TransactionResultViewModel
import kotlinx.android.synthetic.main.isw_fragment_transaction_result.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

internal class IswTransactionResultFragment: Fragment(), View.OnClickListener {

    private lateinit var result: TransactionResultData

    private lateinit var printSlip: TransactionSlip
    private var hasPrintedMerchantCopy = false
    private var hasPrintedCustomerCopy = false
    private var hasSentEmail = false

    // key-value store
    private val store: KeyValueStore by inject()
    // terminal info
    private val terminalInfo by lazy { TerminalInfo.get(store)!! }

    private val resultViewModel: TransactionResultViewModel by viewModel()

    // get the parent fragment
    private val parent: IswBottomSheetFragment
    get() = (parentFragment as IswBottomSheetFragment)

    private val emailSendingDialog by lazy {
        val dialog = DialogUtils.getLoadingDialog(requireContext())
        dialog.setTitle("Sending Email")
        dialog.setMessage("Sending...")
        return@lazy dialog
    }

    private val emailInputDialog by lazy {
        DialogUtils.getEmailInputDialog(requireContext()) { email ->
            // handle user interaction here
            when (email) {
                null -> toast("Email cancelled") // user cancelled dialog
                else -> {
                    // process email
                    terminalInfo.let {
                        resultViewModel.sendMail(email, result, it)
                        // prevent warning dialog from showing
                        hasPrintedCustomerCopy = true
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        result = arguments?.getParcelable(KEY_TRANSACTION_RESULT)
            ?: throw NullPointerException("No transaction result argument was passed")

        // get print slip from result
        printSlip = terminalInfo.let { result.getSlip(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.isw_fragment_transaction_result, container, false)
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        // observe viewModel
        observeViewModel()

        // print user's copy slip
        if (result.responseCode != IsoUtils.TIMEOUT_CODE && result.responseCode != IsoUtils.OK) {
            resultViewModel.printSlip(UserType.Customer, printSlip)
        }

        // log transaction result
        resultViewModel.logTransaction(result)

        // get the title text view
        val tvTitle: TextView = view.findViewById(R.id.resultTitle)

        // get the title of txn
        val title =
            if (result.isSuccessful) R.string.isw_title_transaction_successful
            else R.string.isw_title_transaction_error

        // set the title
        tvTitle.text = getString(title)

        // get the icon
        val resultIcon: ImageView = view.findViewById(R.id.resultIcon)
        val resultImageResource =
            if (result.isSuccessful) R.drawable.isw_success_tick
            else R.drawable.isw_ic_warning

        // set the image icon
        resultIcon.setImageResource(resultImageResource)

        // get text color based on the result
        val textColor =
            if (result.isSuccessful) R.color.iswColorSuccess
            else R.color.iswColorError
        val color = ContextCompat.getColor(requireContext(), textColor)

        // set the error color
        resultSubtitle.setTextColor(color)

        // handle result status
        if (result.isSuccessful) showSuccessfulResult()
        else showFailureResult()
    }


    private fun observeViewModel() {

        with(resultViewModel) {
            val owner = { viewLifecycleOwner.lifecycle }

            // observe printer message
            printerMessage.observe(owner) {
                it?.let { msg -> toast(msg) }
            }

            // observe customer print
            printedCustomerCopy.observe(owner) {
                hasPrintedCustomerCopy = it ?: false
            }

            // observe merchant print
            printedMerchantCopy.observe(owner) {
                hasPrintedMerchantCopy = it ?: false
            }

            // observe print button function
            printButton.observe(owner) { isEnabled ->
                // get print button based on result status
                val button =
                    if (result.isSuccessful) printBtn
                    else errorPrintBtn

                // enable or disable button
                button.isEnabled = isEnabled ?: false
                button.isClickable = isEnabled ?: false
            }

            // observe email sent result
            emailStatus.observe(owner) {
                it?.apply {
                    hasSentEmail = this
                    val msg =
                        if (this) "Email has been sent Successfully"
                        else "An error occurred sending Email"

                    toast(msg)
                }
            }

            // observe email dialog
            emailDialog.observe(owner) {
                it?.let { show ->
                    if (show) emailSendingDialog.show()
                    else emailSendingDialog.hide()
                }
            }
        }
    }

    private fun showSuccessfulResult() {

        // show success buttons
        successButtons.visibility = View.VISIBLE

        // get formatted amount
        var amount = getString(R.string.isw_title_amount, parent.iswPaymentInfo.amountString)
        // set subtitle
        if (CURRENCYTYPE == IswLocal.USA.currency){
            amount = getString(R.string.isw_dollar_title_amount, parent.iswPaymentInfo.amountString)
        }
        resultSubtitle.text = "your payment of $amount has been completed."


        printBtn.setOnClickListener {
            // print slip
            printOutSlip()
        }

        closeBtn.setOnClickListener {
            val hasNotPrinted = !hasPrintedCustomerCopy && !hasPrintedCustomerCopy
            if (hasNotPrinted && !hasSentEmail) {
                DialogUtils.getAlertDialog(requireContext())
                    .setTitle("Close without printing?")
                    .setMessage("Are you sure you want to close without printing")
                    .setNegativeButton(android.R.string.no) { dialog, _ -> dialog.dismiss() }
                    .setPositiveButton(android.R.string.yes) { dialog, _ -> dialog.dismiss();  parent.dismiss() }
                    .show()
            } else {
                parent.dismiss()
            }
        }

        btnEReceipt.setOnClickListener {
            emailInputDialog.show()
        }
    }

    private fun showFailureResult() {
        // set subtitle
        resultSubtitle.text = result.responseMessage

        // show error buttons
        failureButtons.visibility = View.VISIBLE

        // set all the buttons
        val allButtons = listOf(btnCancel, btnRetry, errorPrintBtn)

        // set click listener
        allButtons.forEach {
            it.setOnClickListener(this)
        }
    }

    override fun onClick(btn: View) {
        val retryPayment = when (parent) {
            is IswPreAuthFragment -> (parent as IswPreAuthFragment)::retryCardTransaction
            is IswCompletionFragment -> (parent as IswCompletionFragment)::retryCardTransaction
            is IswRefundFragment -> (parent as IswRefundFragment)::retryCardTransaction
            is IswCashOutFragment -> (parent as IswCashOutFragment)::retryCardTransaction
            is IswBillPaymentFragment -> (parent as IswBillPaymentFragment)::retryCardTransaction
            is IswCashBackFragment -> (parent as IswCashBackFragment)::retryCardTransaction
            else -> throw Exception("Unsupported Transaction type selected")
        }

        when(btn) {
            // retry payment
            btnRetry -> retryPayment()

            // cancel payment
            btnCancel -> return parent.cancelPayment()

            // print transaction error receipt
            errorPrintBtn -> printOutSlip()

            // else invalid argument
            else -> throw Exception("Unsupported payment type selected")
        }
    }

    private fun printOutSlip() {
        // print slip
        printSlip.let {
            // print customer copy if has not yet
            if (!hasPrintedCustomerCopy) resultViewModel.printSlip(UserType.Customer, it)
            // display message for no further printing
            else if (hasPrintedMerchantCopy) toast("Printing Done!!!")
            else {
                // if has not printed merchant copy
                // print merchant copy
                resultViewModel.printSlip(UserType.Merchant, it)
            }
        }
    }

    companion object {
        const val KEY_TRANSACTION_RESULT = "transaction_result_key"
        fun newInstance(paymentInfo: IswPaymentInfo, result: IswTransactionResult, txnResult: TransactionResultData): IswTransactionResultFragment {
            // create fragment and arguments
            val fragment = IswTransactionResultFragment()
            val arguments = Bundle()

            // pass arguments
            arguments.putParcelable(IswConstants.KEY_PAYMENT_INFO, paymentInfo)
            arguments.putParcelable(KEY_TRANSACTION_RESULT, txnResult)

            fragment.arguments = arguments
            return fragment
        }
    }

}