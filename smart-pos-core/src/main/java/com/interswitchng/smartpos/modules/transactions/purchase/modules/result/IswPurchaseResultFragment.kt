package com.interswitchng.smartpos.modules.transactions.purchase.modules.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.transactions.purchase.IswPurchaseFragment
import com.interswitchng.smartpos.modules.transactions.purchase.IswPurchaseViewModel
import com.interswitchng.smartpos.modules.transactions.purchase.shared.fragments.IswBasePaymentFragment
import com.interswitchng.smartpos.shared.IswConstants
//import com.interswitchng.smartpos.shared.models.core.CURRENCYTYPE
import com.interswitchng.smartpos.shared.models.core.IswLocal
import com.interswitchng.smartpos.shared.models.core.UserType
import com.interswitchng.smartpos.shared.models.printer.slips.TransactionSlip
import com.interswitchng.smartpos.shared.models.results.IswPurchaseResult
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import com.interswitchng.smartpos.shared.models.transaction.currencyType
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import com.interswitchng.smartpos.shared.utilities.DialogUtils
import com.interswitchng.smartpos.shared.utils.DisplayUtils.toast
import com.interswitchng.smartpos.shared.viewmodel.TransactionResultViewModel
import kotlinx.android.synthetic.main.isw_fragment_payment_result.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception


internal class IswPurchaseResultFragment : IswBasePaymentFragment(), View.OnClickListener {

    private lateinit var result: TransactionResultData


    private lateinit var printSlip: TransactionSlip
    private var hasPrintedMerchantCopy = false
    private var hasPrintedCustomerCopy = false
    private var hasSentEmail = false

    private val parentViewModel: IswPurchaseViewModel by sharedViewModel()
    private val resultViewModel: TransactionResultViewModel by viewModel()



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
        return inflater.inflate(R.layout.isw_fragment_payment_result, container, false)
    }


    override fun onViewCreated(view: View, bundle: Bundle?) {
        // observe viewModel
        observeViewModel()

        // print user's copy slip
        printSlip.let {
            if (result.responseCode != IsoUtils.TIMEOUT_CODE && result.responseCode != IsoUtils.OK) {
                resultViewModel.printSlip(UserType.Customer, it)
            }
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
                printBtn.isEnabled = isEnabled ?: false
                printBtn.isClickable = isEnabled ?: false
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
        var amount: String
        // set subtitle
        if (currencyType == IswPaymentInfo.CurrencyType.DOLLAR){
            amount = getString(R.string.isw_dollar_title_amount, paymentInfo.amountString)
        }else{
            amount = getString(R.string.isw_title_amount, paymentInfo.amountString)
        }
        resultSubtitle.text = "your payment of $amount has been completed."


        printBtn.setOnClickListener { printOutSlip() }

        closeBtn.setOnClickListener {

            val hasNotPrinted = !hasPrintedCustomerCopy && !hasPrintedCustomerCopy
            if (hasNotPrinted && !hasSentEmail) {
                DialogUtils.getAlertDialog(requireContext())
                    .setTitle("Close without printing?")
                    .setMessage("Are you sure you want to close without printing")
                    .setNegativeButton(android.R.string.no) { dialog, _ -> dialog.dismiss() }
                    .setPositiveButton(android.R.string.yes) { dialog, _ -> dialog.dismiss(); parent.dismiss();  }
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
        val allButtons = listOf(
            btnCard, btnPayCode, btnUssd,
            btnQr, btnCancel, btnRetry, btnCash
        )

        // set click listener
        allButtons.forEach {
            it.setOnClickListener(this)
        }


        // hide current payment's button
        val buttonToHide = when (result.paymentType) {
            PaymentType.Card -> btnCard
            PaymentType.QR -> btnQr
            PaymentType.USSD ->  btnUssd
            PaymentType.PayCode -> btnPayCode
            PaymentType.Cash -> btnCash
        }

        buttonToHide.visibility = View.GONE

        // add click listener to print button
        errorPrintBtn.setOnClickListener { printOutSlip() }
    }

    override fun onClick(btn: View) {
        val page = when(btn) {
            // return a selected purchase option
            btnCard -> IswPurchaseFragment.PurchasePage.Card
            btnPayCode -> IswPurchaseFragment.PurchasePage.PayCode
            btnUssd -> IswPurchaseFragment.PurchasePage.USSD
            btnQr -> IswPurchaseFragment.PurchasePage.QR
            btnCash -> IswPurchaseFragment.PurchasePage.Cash
            btnRetry -> when(result.paymentType) {
                PaymentType.PayCode -> IswPurchaseFragment.PurchasePage.PayCode
                PaymentType.Card -> IswPurchaseFragment.PurchasePage.Card
                PaymentType.USSD -> IswPurchaseFragment.PurchasePage.USSD
                PaymentType.QR -> IswPurchaseFragment.PurchasePage.QR
                PaymentType.Cash -> IswPurchaseFragment.PurchasePage.Cash
            }

            // cancel payment
            btnCancel -> return cancelPayment()

            // else invalid argument
            else -> throw Exception("Unsupported payment type selected")
        }

        // show page
        parentViewModel.setCurrentPage(page)
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

        fun newInstance(paymentInfo: IswPaymentInfo, txnResult: TransactionResultData): IswPurchaseResultFragment {
            // create fragment and arguments
            val fragment = IswPurchaseResultFragment()
            val arguments = Bundle()

            // pass arguments
            arguments.putParcelable(IswConstants.KEY_PAYMENT_INFO, paymentInfo)
            arguments.putParcelable(KEY_TRANSACTION_RESULT, txnResult)

            fragment.arguments = arguments
            return fragment
        }
    }
}