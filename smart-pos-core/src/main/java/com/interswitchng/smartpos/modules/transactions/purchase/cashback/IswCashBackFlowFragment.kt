package com.interswitchng.smartpos.modules.transactions.purchase.cashback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.fragments.IswCardFlowFragment
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvResult
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.AccountType
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.EmvData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.PurchaseType
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.TransactionInfo
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.response.TransactionResponse
import com.interswitchng.smartpos.shared.models.transaction.currencyType
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import com.interswitchng.smartpos.shared.utilities.DialogUtils
import com.interswitchng.smartpos.shared.utilities.DisplayUtils
import com.interswitchng.smartpos.shared.utilities.Logger
import com.interswitchng.smartpos.shared.utils.DisplayUtils.runWithInternet
import com.interswitchng.smartpos.shared.utils.DisplayUtils.toast
import com.interswitchng.smartpos.shared.viewmodel.BaseCardViewModel
import com.interswitchng.smartpos.shared.viewmodel.TransactionCardFlowViewModel
import kotlinx.android.synthetic.main.isw_cancel_button.*
import kotlinx.android.synthetic.main.isw_content_transaction_card_flow.*
import kotlinx.android.synthetic.main.isw_content_transaction_card_flow.cardFlowTitle
import kotlinx.android.synthetic.main.isw_content_transaction_card_flow.tvAmount
import kotlinx.android.synthetic.main.isw_fragment_cashback.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

internal class IswCashBackFlowFragment : Fragment(), IswCardFlowFragment.CardFlowListener {

    // sdk instance
    val iswPos: IswPos by inject()

    // key-value store
    val store: KeyValueStore by inject()

    // terminal info
    val terminalInfo by lazy { TerminalInfo.get(store)!! }

    private val logger by lazy { Logger.with("IswCashBackFlowFragment") }
    private val parent: IswCashBackFragment
        get() =
            parentFragment as IswCashBackFragment


    private val cashBackViewModel: TransactionCardFlowViewModel by viewModel()
    private lateinit var cardFlowFragment: IswCardFlowFragment

    private val cancelDialog by lazy {
        DialogUtils.getAlertDialog(requireContext())
                .setMessage("Would you like to try again?")
                .setCancelable(false)
                .setNeutralButton(R.string.isw_action_cancel) { dialog, _ ->
                    dialog.dismiss()
                    // close dialog
                    parent.dismiss()
                }
                .setPositiveButton(R.string.isw_title_try_again) { dialog, _ ->
                    dialog.dismiss()
                    resetTransaction()
                }.create()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.isw_fragment_cashback, container, false)
    }

    override fun onViewCreated(view: View, savedInstance: Bundle?) {
        super.onViewCreated(view, savedInstance)

        // set card flow title
        cardFlowTitle.text = "Cashback"


        // set title amount
        var amountString = getString(
                R.string.isw_title_amount,
                parent.iswPaymentInfo.amountString
        )
        if (currencyType == IswPaymentInfo.CurrencyType.DOLLAR){
            amountString = tvAmount.context.getString(
                R.string.isw_dollar_currency_amount,
                parent.iswPaymentInfo.amountString)
        }
        tvAmount.text = amountString

        var cashBackAmt = getString(
                R.string.isw_title_amount,
                parent.iswPaymentInfo.additionalAmountsString
        )
        if (currencyType == IswPaymentInfo.CurrencyType.DOLLAR){
            cashBackAmt = tvAmount.context.getString(
                R.string.isw_dollar_title_amount,
                parent.iswPaymentInfo.amountString)
        }
        tvCashBackAmount.text = cashBackAmt

                // observe view model
        observeViewModel()

        // reset card state by creating
        // fragment for card-emv flow
        resetTransaction()

        // set cancel button click listener
        closeBtn.setOnClickListener {
            // dismiss bottom sheet
            parent.dismiss()
        }
    }


    private fun observeViewModel() {
        with(this.cashBackViewModel) {

            // set the transaction type
            setTransactionType(TransactionType.CashBack)

            // get view life cycle owner
            val owner = { viewLifecycleOwner.lifecycle }

            // observe transaction response
            transactionResponse.observe(owner) {
                it?.let(::processResponse)
            }

            // observe online process results
            onlineResult.observe(owner) {
                it?.let { result ->
                    when (result) {
                        BaseCardViewModel.OnlineProcessResult.NO_EMV -> {
                            toast("Unable to getResult icc")
                        }
                        BaseCardViewModel.OnlineProcessResult.NO_RESPONSE -> {
                            toast("Unable to process Transaction")
                        }
                        BaseCardViewModel.OnlineProcessResult.ONLINE_DENIED -> {
                            toast("Transaction Declined")
                        }
                        BaseCardViewModel.OnlineProcessResult.ONLINE_APPROVED -> {
                            toast("Transaction Approved")
                        }
                    }
                }
            }
        }
    }


    private fun processResponse(transactionResponse: Optional<Pair<TransactionResponse, EmvData>>) {

        when (transactionResponse) {
            is None -> {
                val errorMsg = "Unable to complete transaction"
                // show error to retry card transaction
                parent.showError(errorMsg, this::resetTransaction)
            }
            is Some -> {
                // extract info
                val response = transactionResponse.value.first
                val emvData = transactionResponse.value.second
                val txnInfo = TransactionInfo.fromEmv(
                        emvData,
                        parent.iswPaymentInfo,
                        PurchaseType.Card,
                        cardFlowFragment.accountType
                )

                val responseMsg = if (response.responseDescription.isNullOrEmpty()) {
                    IsoUtils.getIsoResultMsg(response.responseCode)
                            ?: "Unknown Error"
                } else response.responseDescription

                val pinStatus = when {
                    cardFlowFragment.pinOk || response.responseCode == IsoUtils.OK -> "PIN Verified"
                    else -> "PIN Unverified"
                }

                val now = Date()
                val transactionResult = TransactionResultData(
                        paymentType = PaymentType.Card,
                        dateTime = DisplayUtils.getIsoString(now),
                        amount = (parent.iswPaymentInfo.amount + (response.additionalInfo?.additionalAmounts?.toInt()
                                ?: 0)).toString(),
                        type = TransactionType.CashBack,
                        authorizationCode = response.authCode,
                        responseMessage = responseMsg,
                        responseCode = response.responseCode,
                        cardPan = txnInfo.cardPAN,
                        cardExpiry = txnInfo.cardExpiry,
                        cardType = cardFlowFragment.cardType,
                        stan = response.stan,
                        pinStatus = pinStatus,
                        AID = emvData.AID,
                        code = "",
                        telephone = iswPos.config.merchantTelephone,
                        txnDate = response.date,
                        cardHolderName = emvData.icc.CARD_HOLDER_NAME,
                        transactionId = response.transactionId ?: "",
                        //additionalInfo = Gson().toJson(response.additionalInfo)
                        surcharge = response.additionalInfo?.surcharge,
                        additionalAmounts = response.additionalInfo?.additionalAmounts,
                        currencyType = IswPaymentInfo.CurrencyType.values()[currencyType.ordinal]
                )

                // close loader
                closeLoader()

                // show transaction result screen
                parent.showResult(transactionResult)
            }
        }
    }


    ///// CardFlowListener overrides
    override fun closeLoader() = parent.closeLoader(true)

    override fun getPaymentInfo(): IswPaymentInfo = parent.iswPaymentInfo

    override fun showLoadingMessage(message: String) = parent.showLoader(message)

    override fun showCancelDialog(reason: String) {
        // set reason and show cancel dialog
        cancelDialog.setTitle(reason)
        if (!cancelDialog.isShowing) cancelDialog.show()
    }

    override fun processEmvResult(
            emvResult: EmvResult,
            emvData: EmvData?,
            accountType: AccountType
    ) {
        // ensure internet connection
        // before starting transaction
        runWithInternet {
            // try starting transaction
            this.cashBackViewModel.processResult(
                    requireContext(),
                    emvResult,
                    parent.iswPaymentInfo,
                    accountType,
                    terminalInfo,
                    emvData,
                    cardFlowFragment::completeTransaction
            )
        }
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
                .replace(R.id.cardFlowContainer, fragment, fragment.tag)
                // add home page to back navigation
                .addToBackStack(fragment.tag)
                .commit()

        childFragmentManager.executePendingTransactions()
    }

    private fun resetTransaction() {
        // create a new card Flow fragment
        val fragment = IswCardFlowFragment()

        // replace old cardFlow with new one
        show(fragment)

        // set the cardFlow instance
        cardFlowFragment = fragment
    }

}