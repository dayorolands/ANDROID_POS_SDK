package com.interswitchng.smartpos.modules.transactions.refund

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
import com.interswitchng.smartpos.shared.viewmodel.TransactionCardFlowViewModel
import kotlinx.android.synthetic.main.isw_cancel_button.*
import kotlinx.android.synthetic.main.isw_content_transaction_card_flow.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import com.interswitchng.smartpos.shared.viewmodel.BaseCardViewModel.OnlineProcessResult

internal class IswRefundFlowFragment : Fragment(), IswCardFlowFragment.CardFlowListener {

    // sdk instance
    val iswPos: IswPos by inject()

    // key-value store
    val store: KeyValueStore by inject()

    // terminal info
    val terminalInfo by lazy { TerminalInfo.get(store)!! }

    private val logger by lazy { Logger.with("IswPreAuthContentFragment") }
    private val parent: IswRefundFragment
        get() = parentFragment as IswRefundFragment


    private val refundViewModel: TransactionCardFlowViewModel by viewModel()

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
                showCardFlow()
            }.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.isw_content_transaction_card_flow, container, false)
    }

    override fun onViewCreated(view: View, savedInstance: Bundle?) {
        super.onViewCreated(view, savedInstance)

        // set card flow title
        cardFlowTitle.text = "Refund"

        // observe view model
        observeViewModel()

        // set cancel button click listener
        closeBtn.setOnClickListener {
            // dismiss bottom sheet
            parent.dismiss()
        }

        // show card flow
        showCardFlow()
    }


    private fun observeViewModel() {
        with(this.refundViewModel) {

            // set the transaction type
            setTransactionType(TransactionType.Refund)

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
                        OnlineProcessResult.NO_EMV -> {
                            toast("Unable to getResult icc")
                        }
                        OnlineProcessResult.NO_RESPONSE -> {
                            toast("Unable to process Transaction")
                        }
                        OnlineProcessResult.ONLINE_DENIED -> {
                            toast("Transaction Declined")
                        }
                        OnlineProcessResult.ONLINE_APPROVED -> {
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
                logger.log(errorMsg)
                parent.showError(errorMsg, this::showCardFlow)
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

                val responseMsg = IsoUtils.getIsoResultMsg(response.responseCode) ?: "Unknown Error"
                val pinStatus = when {
                    cardFlowFragment.pinOk || response.responseCode == IsoUtils.OK -> "PIN Verified"
                    else -> "PIN Unverified"
                }

                val now = Date()
                val transactionResult = TransactionResultData(
                    paymentType = PaymentType.Card,
                    dateTime = DisplayUtils.getIsoString(now),
                    amount = parent.iswPaymentInfo.amount.toString(),
                    type = TransactionType.Refund,
                    authorizationCode = response.authCode,
                    responseMessage = responseMsg,
                    responseCode = response.responseCode,
                    cardPan = txnInfo.cardPAN,
                    cardExpiry = txnInfo.cardExpiry,
                    cardType = cardFlowFragment.cardType,
                    cardHolderName = emvData.icc.CARD_HOLDER_NAME,
                    stan = response.stan,
                    pinStatus = pinStatus,
                    AID = emvData.AID,
                    code = "",
                    telephone = iswPos.config.merchantTelephone,
                    txnDate = response.date,
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
            this.refundViewModel.processResult(
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

    override fun currencyChosen(cxch: String) {
        Logger.with("the value returned in this method is: ").logErr(cxch)
        // set title amount
        if (cxch == "1"){
            tvAmount.text = getString(
                R.string.isw_dollar_title_amount,
                parent.iswPaymentInfo.amountString)
        }
        else{
            tvAmount.text = getString(
                R.string.isw_title_amount,
                parent.iswPaymentInfo.amountString
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
            .commit()

        childFragmentManager.executePendingTransactions()
    }

    private fun showCardFlow() {
        // create a new card Flow fragment
        val fragment = IswCardFlowFragment()

        // replace old cardFlow with new one
        show(fragment)

        // set the cardFlow instance
        cardFlowFragment = fragment
    }
}