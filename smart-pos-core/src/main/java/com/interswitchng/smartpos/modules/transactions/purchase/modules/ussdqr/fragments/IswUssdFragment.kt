package com.interswitchng.smartpos.modules.transactions.purchase.modules.ussdqr.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import com.gojuno.koptional.None
import com.gojuno.koptional.Some
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.transactions.purchase.modules.ussdqr.adapter.BankListAdapter
import com.interswitchng.smartpos.modules.transactions.purchase.modules.ussdqr.viewModels.UssdViewModel
import com.interswitchng.smartpos.shared.utilities.Logger
import com.interswitchng.smartpos.shared.IswConstants
import com.interswitchng.smartpos.modules.transactions.purchase.shared.fragments.IswBaseCodeFragment
import com.interswitchng.smartpos.shared.models.core.UserType
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.posconfig.PrintStringConfiguration
import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.CardType
import com.interswitchng.smartpos.shared.models.transaction.currencyType
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.request.CodeRequest
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.request.CodeRequest.Companion.TRANSACTION_USSD
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.request.TransactionStatus
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.Bank
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.CodeResponse
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.PaymentStatus
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.Transaction
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import com.interswitchng.smartpos.shared.utilities.DisplayUtils
import com.interswitchng.smartpos.shared.utils.DisplayUtils.runWithInternet
import kotlinx.android.synthetic.main.isw_fragment_ussd.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.*

internal class IswUssdFragment : IswBaseCodeFragment(), AdapterView.OnItemSelectedListener {

    private val ussdViewModel: UssdViewModel by sharedViewModel()
    private var printSlip = mutableListOf<PrintObject>()

    private val logger by lazy { Logger.with("IswUssdFragment") }

    private lateinit var adapter: BankListAdapter
    private lateinit var ussdCode: String
    private lateinit var selectedBank: Bank
    private var selectedBankIndex: Int = -1

    // flag to prevent callback on initial setup of spinner
    private var justSetupSpinner = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.isw_fragment_ussd, container, false)
    }


    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        // observe viewModel
        observeViewModel()

        // load banks
        loadBanks()
    }

    override fun onDestroy() {
        super.onDestroy()

        // cancel polling
        ussdViewModel.cancelPoll()
        // reset view model
        ussdViewModel.reset()
    }


    private fun loadBanks() {
        // set the default banks selection
        etBanksSpinner.setText(getString(R.string.isw_select_bank))

        // load banks if not loaded
        if (!ussdViewModel.hasBanks) runWithInternet {
            showLoader("Loading Issuers...")
            ussdViewModel.loadBanks()
        }
    }


    private fun observeViewModel() {
        // func to return lifecycle
        val owner = { viewLifecycleOwner.lifecycle }

        // observe view model
        with(ussdViewModel) {

            // observe print button
            printButton.observe(owner) {
                val isEnabled = it ?: false
                printCodeButton.isEnabled = isEnabled
                printCodeButton.isClickable = isEnabled
            }

            // observe bank list
            allBanks.observe(owner) {
                it?.let { banks ->
                    when (banks) {
                        is Some -> {
                            hasBanks = banks.value.isNotEmpty()
                            setBanks(banks.value)
                            closeLoader()
                        }
                        is None -> {
                            hasBanks = false
                            // show warning to enable reload of banks
                            showError(
                                    "Unable to load bank issuers, please try again.",
                                    this::loadBanks
                            )
                        }
                    }
                }
            }

            // observe selected bank code
            bankCode.observe(owner) {
                // dismiss loading indicator
                closeLoader()

                // handle code response
                it?.let { code ->
                    when (code) {
                        is Some -> handleResponse(code.value)
                        is None -> handleError()
                    }
                }
            }

            // observe payment status
            paymentStatus.observe(owner) {
                it?.let { status ->

                    // setup payment slip
                    if (it is PaymentStatus.Timeout) {
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

                        val result = getTransactionResult(transaction)
                        printSlip = result.getSlip(terminalInfo).getSlipItems(false)
                    }

                    // handle ussd payment status
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

                // toggle of confirm button
                btnConfirmPayment.isEnabled = !isLoading
                btnConfirmPayment.isClickable = !isLoading
            }
        }
    }


    private fun showButtons(response: CodeResponse) {
        // show buttons and ussd containers
        btnsContainer.visibility = View.VISIBLE
        ussdContainer.visibility = View.VISIBLE


        // enable and attach click listeners
        btnConfirmPayment.isEnabled = true
        btnConfirmPayment.setOnClickListener {
            runWithInternet {
                btnConfirmPayment.isEnabled = false
                btnConfirmPayment.isClickable = false

                // create status request
                val status = TransactionStatus(
                        response.transactionReference!!,
                        terminalInfo.merchantCode
                )

                // check transaction status
                ussdViewModel.checkTransactionStatus(status)
            }
        }

        printCodeButton.isEnabled = true
        printCodeButton.isClickable = true
        printCodeButton.setOnClickListener {
            ussdViewModel.printCode(requireContext(), posDevice, UserType.Customer, printSlip)
        }
    }

    private fun hideUssdButtons() {
        arrayOf(btnsContainer, ussdContainer, tvUssdHint).forEach {
            it.visibility = View.GONE
        }
    }

    private fun setBanks(issuers: List<Bank>) {
        // create adapter with banks
        adapter = BankListAdapter(issuers, this::getBankCode)

        // set flag
        justSetupSpinner = true

        // setup the hidden spinner
        // create banks adapter
        banksSpinner.adapter = adapter
        // set default selection
        banksSpinner.setSelection(Adapter.NO_SELECTION)

        // set item select listener
        banksSpinner.onItemSelectedListener = this
        banksSpinner.isClickable = true

        // set click listener for banks
        etBanksSpinner.setOnClickListener {
            // trigger click on the hidden spinner
            banksSpinner.performClick()
        }
    }

    private fun getBankCode(selectedBank: Bank) {
        runWithInternet {
            // show loading indicator
            showLoader("Loading USSD Code...")

            // create request for ussd
            val request = CodeRequest.from(
                    //iswPos.config.alias,
                    terminalInfo,
                    paymentInfo,
                    TRANSACTION_USSD,
                    bankCode = selectedBank.code
            )

            // get ussd code
            ussdViewModel.getBankCode(request)
        }
    }


    private fun handleResponse(response: CodeResponse) {
        when (response.responseCode) {
            CodeResponse.OK -> {

                ussdCode = response.bankShortCode ?: response.defaultShortCode!!
                ussdCode.apply {
                    tvUssd.text = this
                    val code = substring(lastIndexOf("*") + 1 until lastIndexOf("#"))

                    // get the entire hint as spannable string
                    val hint = getString(R.string.isw_hint_enter_ussd_code, code)
                    val spannableHint = SpannableString(hint)


                    // increase font size and change font color
                    val startIndex = 16
                    val endIndex = startIndex + code.length + 1
                    val codeColor = ContextCompat.getColor(requireContext(), R.color.iswColorPrimary)
                    spannableHint.setSpan(AbsoluteSizeSpan(21, true), startIndex, endIndex, 0)
                    spannableHint.setSpan(StyleSpan(Typeface.BOLD), startIndex, endIndex, 0)
                    spannableHint.setSpan(
                            ForegroundColorSpan(codeColor),
                            startIndex,
                            endIndex,
                            0
                    )


                    // set the text value
                    tvUssdHint.text = spannableHint
                    tvUssdHint.visibility = View.VISIBLE

                    printSlip.add(
                            PrintObject.Data(
                                    "code - \n $this\n",
                                    PrintStringConfiguration(isBold = true, isTitle = true)
                            )
                    )
                }

                // show ussd text container
                showButtons(response)

                // ensure internet connection
                // before polling for transaction
                runWithInternet {
                    // check transaction status
                    val status = TransactionStatus(
                            response.transactionReference!!,
                            terminalInfo.merchantCode
                    )

                    ussdViewModel.pollTransactionStatus(status)
                }
            }
            else -> {
                val errorMessage = "An error occurred: ${response.responseDescription}"
                logger.log(errorMessage)
                handleError()
            }
        }
    }

    private fun handleError() {
        // show warning for user to try re-generating code
        showError("Unable to generate bank code") {
            // retry getting ussd code
            getBankCode(selectedBank)
        }

    }

    override fun getTransactionResult(transaction: Transaction): TransactionResultData {
        val now = Date()

        val responseMsg = IsoUtils.getIsoResult(transaction.responseCode)?.second
                ?: transaction.responseDescription
                ?: "Error"

        return TransactionResultData(
                paymentType = PaymentType.USSD,
                dateTime = DisplayUtils.getIsoString(now),
                amount = paymentInfo.amount.toString(),
                type = TransactionType.Purchase,
                authorizationCode = transaction.responseCode,
                responseMessage = responseMsg,
                responseCode = transaction.responseCode,
                cardPan = "",
                cardExpiry = "",
                cardHolderName = "",
                cardType = CardType.None,
                stan = paymentInfo.currentStan,
                pinStatus = "",
                AID = "",
                code = ussdCode,
                telephone = iswPos.config.merchantTelephone,
                txnDate = now.time,
                currencyType = IswPaymentInfo.CurrencyType.values()[currencyType.ordinal]
        )
    }

    override fun hideLoader() {
        loader.visibility = View.GONE

        // toggle of confirm button
        btnConfirmPayment.isEnabled = true
        btnConfirmPayment.isClickable = true
    }

    override fun onCheckStopped() = ussdViewModel.cancelPoll()


    override fun onItemSelected(adapterView: AdapterView<*>, view: View, itemPosition: Int, itemId: Long) {

        // get bank at adapter
        // check selected bank has changed
        if (selectedBankIndex == itemPosition) return
        else selectedBankIndex = itemPosition


        // set the name of selected bank
        etBanksSpinner.setText(adapter.getTitle(itemPosition))

        // prevent selection of default option
        if (itemPosition == 0) hideUssdButtons()
        else {
            selectedBank = adapter.getItem(itemPosition - 1)
            // generate bank code
            getBankCode(selectedBank)
        }
    }

    override fun onNothingSelected(adapterView: AdapterView<*>) {
        print("Nothing selected")
    }

    companion object {
        fun newInstance(paymentInfo: IswPaymentInfo): IswUssdFragment {
            // create fragment and arguments
            val fragment = IswUssdFragment()
            val bundle = Bundle().apply {
                putParcelable(IswConstants.KEY_PAYMENT_INFO, paymentInfo)
            }

            // set fragment arguments
            fragment.arguments = bundle
            return fragment
        }
    }
}