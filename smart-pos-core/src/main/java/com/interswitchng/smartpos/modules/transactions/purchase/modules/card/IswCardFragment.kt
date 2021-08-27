package com.interswitchng.smartpos.modules.transactions.purchase.modules.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.transactions.purchase.shared.fragments.IswBasePaymentFragment
import com.interswitchng.smartpos.shared.IswConstants
import com.interswitchng.smartpos.shared.fragments.IswCardFlowFragment
import com.interswitchng.smartpos.shared.fragments.IswCardFlowFragment.CardFlowListener
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
import com.interswitchng.smartpos.shared.viewmodel.BaseCardViewModel.OnlineProcessResult
import com.interswitchng.smartpos.shared.utils.DisplayUtils.runWithInternet
import com.interswitchng.smartpos.shared.utils.DisplayUtils.toast
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

internal class IswCardFragment : IswBasePaymentFragment(), CardFlowListener {

    private val cardViewModel: CardViewModel by viewModel()

    private val logger by lazy { Logger.with("IswCardFragment") }

    private lateinit var cardFlowFragment: IswCardFlowFragment

    private val cancelDialog by lazy {
        DialogUtils.getAlertDialog(requireContext())
            .setMessage("Would you like to change payment method, or try again?")
            .setCancelable(false)
            .setPositiveButton(R.string.isw_action_change) { dialog, _ ->
                dialog.dismiss()
                // show options
                goHome()
            }
            .setNeutralButton(R.string.isw_title_try_again) { dialog, _ ->
                dialog.dismiss()
                resetTransaction()
            }.create()
    }

    private lateinit var transactionResult: TransactionResultData


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.isw_fragment_card, container, false)
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        // observe view model
        observeViewModel()

        // reset card state by creating
        // fragment for card-emv flow
        resetTransaction()
    }


    private fun observeViewModel() {
        with(cardViewModel) {

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
                showError(errorMsg, this::resetTransaction)
            }
            is Some -> {
                // extract info
                val response = transactionResponse.value.first
                val emvData = transactionResponse.value.second
                val txnInfo = TransactionInfo.fromEmv(
                    emvData,
                    paymentInfo,
                    PurchaseType.Card,
                    cardFlowFragment.accountType
                )

                val responseMsg = IsoUtils.getIsoResultMsg(response.responseCode) ?: "Unknown Error"
                val pinStatus = when {
                    cardFlowFragment.pinOk || response.responseCode == IsoUtils.OK -> "PIN Verified"
                    else -> "PIN Unverified"
                }

                val now = Date()
                transactionResult = TransactionResultData(
                    paymentType = PaymentType.Card,
                    dateTime = DisplayUtils.getIsoString(now),
                    amount = paymentInfo.amount.toString(),
                    type = TransactionType.Purchase,
                    authorizationCode = response.authCode,
                    responseMessage = responseMsg,
                    responseCode = response.responseCode,
                    cardPan = txnInfo.cardPAN,
                    cardExpiry = txnInfo.cardExpiry,
                    cardType = cardFlowFragment.cardType,
                    stan = response.stan,
                    pinStatus = pinStatus,
                    AID = emvData.AID,
                    cardHolderName = emvData.icc.CARD_HOLDER_NAME,
                    code = "",
                    telephone = iswPos.config.merchantTelephone,
                    txnDate = response.date,
                    currencyType = IswPaymentInfo.CurrencyType.values()[currencyType.ordinal]
                )

                // close loader
                closeLoader()

                // show transaction result screen
                showTransactionResult(transactionResult)
            }
        }
    }


    ///// CardFlowListener overrides
    override fun getPaymentInfo(): IswPaymentInfo = paymentInfo
    override fun showLoadingMessage(message: String) = showLoader(message)

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
            cardViewModel.processResult(
                requireContext(),
                emvResult,
                paymentInfo,
                accountType,
                terminalInfo,
                emvData,
                cardFlowFragment::completeTransaction
            )
        }
    }

    override fun currencyChosen(cxch: String) {
        Logger.with("This is what is returned in this method: ").logErr(cxch)
    }

    private fun resetTransaction() {
        // create a new card Flow fragment
        val fragment = IswCardFlowFragment()

        // replace old cardFlow with new one
        childFragmentManager.beginTransaction()
            .replace(R.id.cardFlow, fragment)
            .commit()

        // set the cardFlow instance
        cardFlowFragment = fragment
    }

    companion object {
        fun newInstance(paymentInfo: IswPaymentInfo): IswCardFragment {

            // create fragment and arguments
            val fragment = IswCardFragment()
            val bundle = Bundle().apply {
                putParcelable(IswConstants.KEY_PAYMENT_INFO, paymentInfo)
            }

            // set fragment arguments
            fragment.arguments = bundle
            return fragment
        }
    }

}