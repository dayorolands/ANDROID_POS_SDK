package com.interswitchng.smartpos.modules.transactions.purchase.modules.ussdqr.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gojuno.koptional.None
import com.gojuno.koptional.Some
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.transactions.purchase.modules.ussdqr.viewModels.QrViewModel
import com.interswitchng.smartpos.modules.transactions.purchase.shared.fragments.IswBaseCodeFragment
import com.interswitchng.smartpos.shared.IswConstants
import com.interswitchng.smartpos.shared.models.core.UserType
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.CardType
import com.interswitchng.smartpos.shared.models.transaction.currencyType
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.request.CodeRequest
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.request.CodeRequest.Companion.QR_FORMAT_RAW
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.request.CodeRequest.Companion.TRANSACTION_QR
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.request.TransactionStatus
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.CodeResponse
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.PaymentStatus
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.Transaction
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import com.interswitchng.smartpos.shared.utilities.DisplayUtils
import com.interswitchng.smartpos.shared.utilities.Logger
import com.interswitchng.smartpos.shared.utils.DisplayUtils.runWithInternet
import kotlinx.android.synthetic.main.isw_fragment_qr.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.*

internal class IswQrFragment : IswBaseCodeFragment() {

    // make viewModel shared to preserve viewModel state
    // when changing payment options (changing fragments)
    private val qrViewModel: QrViewModel by sharedViewModel()
    private lateinit var qrData: String
    private var printSlip = mutableListOf<PrintObject>()
    private val logger by lazy { Logger.with("IswQrFragment") }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.isw_fragment_qr, container, false)
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        // func returns lifecycle
        val owner = { viewLifecycleOwner.lifecycle }

        // observe view model
        with(qrViewModel) {

            // observe print button
            printButton.observe(owner) {
                val isEnabled = it ?: false
                printCodeButton.isEnabled = isEnabled
                printCodeButton.isClickable = isEnabled
            }

            // observe qr code
            qrCode.observe(owner) {
                it?.let { code ->
                    // close loading fragment
                    closeLoader()
                    // handle qr response
                    when (code) {
                        is Some -> handleResponse(code.value)
                        is None -> handleError()
                    }
                }
            }

            // observe payment status
            paymentStatus.observe(owner) {
                // handle updates of payment status
                it?.let { status ->
                    // setup payment slip
                    if (it is PaymentStatus.Timeout) {

                        // create transaction info
                        val transaction = Transaction(
                            -1, paymentInfo.amount,
                            "",
                            "0X0X",
                            terminalInfo.currencyCode,
                            true,
                            null,
                            0,
                            "Pending"
                        )

                        // generate transaction result using transaction info
                        val result = getTransactionResult(transaction)
                        // create print slip using the transaction result
                        printSlip = result.getSlip(terminalInfo).getSlipItems(false)
                    }

                    // handle QR payment status
                    handlePaymentStatus(status)
                }
            }

            showProgress.observe(owner) {
                val isLoading = it == true
                // toggle visibility of loader
                val visibility =
                    if (isLoading) View.VISIBLE
                    else View.GONE
                loader.visibility = visibility

                // toggle confirm button
                btnConfirmPayment.isEnabled = !isLoading
                btnConfirmPayment.isClickable = !isLoading
            }
        }


        // get qr code
        getQrCode()
    }


    // ensure device is connected to internet
    private fun getQrCode() = runWithInternet {

        // check if qrData is present
        if (qrViewModel.qrCode.value != null) return@runWithInternet

        // show loading dialog
        showLoader("Generating QR Code...")

        // create and request code
        val request = CodeRequest.from(
            //iswPos.config.alias,
            terminalInfo,
            paymentInfo,
            TRANSACTION_QR,
            QR_FORMAT_RAW
        )

        qrViewModel.getQrCode(request, requireContext())
    }

    private fun handleResponse(response: CodeResponse) {
        when (response.responseCode) {
            CodeResponse.OK -> runWithInternet {
                qrData = response.qrCodeData!!
                val bitmap = response.qrCodeImage!!
                printSlip.add(PrintObject.BitMap(bitmap))

                qrCodeImage.setImageBitmap(response.qrCodeImage)
                enableButtons(response)

                // check transaction status
                val status =
                    TransactionStatus(response.transactionReference!!, terminalInfo.merchantCode)

                // ensure internet connection before
                // polling for transaction result
                qrViewModel.pollTransactionStatus(status)
            }
            else -> {
                val errorMessage = "An error occurred: ${response.responseDescription}"
                logger.log(errorMessage)
                handleError()
            }
        }
    }

    private fun handleError() = showError("Unable to generate code", this::getQrCode)

    private fun enableButtons(response: CodeResponse) {
        btnConfirmPayment.isEnabled = true
        btnConfirmPayment.setOnClickListener {
            runWithInternet {
                btnConfirmPayment.isEnabled = false
                btnConfirmPayment.isClickable = false

                // check transaction status
                val status =
                    TransactionStatus(response.transactionReference!!, terminalInfo.merchantCode)
                qrViewModel.checkTransactionStatus(status)
            }
        }

        printCodeButton.isEnabled = true
        printCodeButton.isClickable = true
        printCodeButton.setOnClickListener {
            qrViewModel.printCode(requireContext(), posDevice, UserType.Customer, printSlip)
        }

    }


    override fun getTransactionResult(transaction: Transaction): TransactionResultData {
        val now = Date()
        val responseMsg = IsoUtils.getIsoResultMsg(transaction.responseCode)
            ?: transaction.responseDescription
            ?: "Error"

        return TransactionResultData(
            paymentType = PaymentType.QR,
            dateTime = DisplayUtils.getIsoString(now),
            amount = paymentInfo.amount.toString(),
            type = TransactionType.Purchase,
            authorizationCode = transaction.responseCode,
            responseMessage = responseMsg,
            responseCode = transaction.responseCode,
            cardPan = "",
            cardExpiry = "",
            cardType = CardType.None,
            cardHolderName = "",
            stan = paymentInfo.currentStan,
            pinStatus = "",
            AID = "",
            code = qrData,
            telephone = iswPos.config.merchantTelephone,
            txnDate = now.time,
            currencyType = IswPaymentInfo.CurrencyType.values()[currencyType.ordinal]
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        // cancel all polling
        qrViewModel.cancelPoll()
        // reset view model
        qrViewModel.reset()
    }

    override fun hideLoader() {
        loader.visibility = View.GONE

        // enable confirm button
        btnConfirmPayment.isEnabled = true
        btnConfirmPayment.isClickable = true
    }

    override fun onCheckStopped() = qrViewModel.cancelPoll()

    companion object {
        fun newInstance(paymentInfo: IswPaymentInfo): IswQrFragment {
            // create fragment and arguments
            val fragment = IswQrFragment()
            val bundle = Bundle().apply {
                putParcelable(IswConstants.KEY_PAYMENT_INFO, paymentInfo)
            }

            // set fragment arguments
            fragment.arguments = bundle
            return fragment
        }
    }

}