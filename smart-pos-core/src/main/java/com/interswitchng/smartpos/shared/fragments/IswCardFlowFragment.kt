package com.interswitchng.smartpos.shared.fragments

import android.content.ContentValues.TAG
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.core.CURRENCYTYPE
import com.interswitchng.smartpos.shared.models.core.IswLocal
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.CardType
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvMessage
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvResult
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.AccountType
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.EmvData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.response.TransactionResponse
import com.interswitchng.smartpos.shared.utilities.CurrencyDialog
import com.interswitchng.smartpos.shared.utilities.DialogUtils
import com.interswitchng.smartpos.shared.utilities.Logger
import com.interswitchng.smartpos.shared.utils.DisplayUtils.toast
import com.interswitchng.smartpos.shared.viewmodel.CardFlowViewModel
import kotlinx.android.synthetic.main.isw_fragment_card_flow.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


internal class IswCardFlowFragment : Fragment() {

    interface CardFlowListener {
        fun closeLoader()
        fun getPaymentInfo(): IswPaymentInfo
        fun showCancelDialog(reason: String)
        fun showLoadingMessage(message: String)
        fun processEmvResult(emvResult: EmvResult, emvData: EmvData?, accountType: AccountType)
        fun onCardRead(pan: String){}
    }

    enum class CardTransactionState {
        InsertCard,
        SelectAccountType,
        EnterPin
    }

    // key-value store
    val store: KeyValueStore by inject()
    // terminal info
    val terminalInfo by lazy { TerminalInfo.get(store)!! }

    // view model for card flow
    val cardViewModel: CardFlowViewModel by viewModel()

    // current payment info
    val paymentInfo: IswPaymentInfo by lazy { parent.getPaymentInfo() }

    // get the parent fragment
    private val parent: CardFlowListener
        get() = (parentFragment as CardFlowListener)


    private val logger by lazy { Logger.with("IswCardFragmentFlow") }

    private val alert by lazy { DialogUtils.getAlertDialog(requireContext()).create() }
    private var isCancelled = false


    var accountType = AccountType.Default
        private set
    var cardType = CardType.None
        private set
    var pinOk = false
        private set


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.isw_fragment_card_flow, container, false)
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        showCurrencyDialog()
        // observe view model
        //observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        isCancelled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        isCancelled = true
    }

    private fun chooseAccount() {

        // attach click listeners for each account type button
        arrayOf(btnDefault, btnSavings, btnCurrent, btnCredit).forEach { btn ->

            btn.setOnClickListener {
                // set the selected account
                accountType = when (it) {
                    btnSavings -> AccountType.Savings
                    btnCurrent -> AccountType.Current
                    btnCredit -> AccountType.Credit
                    else -> AccountType.Default
                }

                accountTypeText.text = accountType.name

                // try starting transaction
                cardViewModel.startTransaction { emvResult, emvData ->
                    parent.processEmvResult(emvResult, emvData, accountType)
                }
            }
        }

        // show account selection
        showContainer(CardTransactionState.SelectAccountType)

        // dismiss loader
        parent.closeLoader()
    }

    private fun observeViewModel() {
        with(cardViewModel) {

            val owner = { viewLifecycleOwner.lifecycle }

            // observe emv messages
            emvMessage.observe(owner) {
                it?.let(::processMessage)
            }
        }
    }

    fun completeTransaction(response: TransactionResponse) = cardViewModel.completeEmvTransaction(response)


    private fun processMessage(message: EmvMessage) {

        //showCurrencyDialog()
        // assigns value to ensure the when expression is exhausted
        val ignore = when (message) {

            // when card is detected
            is EmvMessage.CardDetected -> {
                parent.showLoadingMessage("Reading Card")
            }

            // when card should be inserted
            is EmvMessage.InsertCard -> {
                showContainer(CardTransactionState.InsertCard)
            }

            // when card has been read
            is EmvMessage.CardRead -> {

                cardType = message.cardType
                val cardIcon = when (cardType) {
                    CardType.MASTER -> R.drawable.isw_ic_card_mc
                    CardType.VISA -> R.drawable.isw_visa
                    CardType.VERVE -> R.drawable.isw_ic_card_verve
                    CardType.AMERICANEXPRESS -> R.drawable.isw_ic_card_american_express
                    CardType.CHINAUNIONPAY -> R.drawable.isw_ic_card_unionpay
                    else -> R.drawable.isw_ic_card
                }

                // set the card icon
                cardTypeIcon.setImageResource(cardIcon)

                parent.onCardRead(message.cardPan)

                // show account type selection
                chooseAccount()
            }

            // when card gets removed
            is EmvMessage.CardRemoved -> {
                cancelTransaction("Transaction Cancelled: Card was removed")
            }

            // when user should enter pin
            is EmvMessage.EnterPin -> {
                parent.closeLoader()


                // set title amount
                var amountString = getString(R.string.isw_title_amount, paymentInfo.amountString)
                if (CURRENCYTYPE == IswLocal.USA.currency){
                    amountString = getString(R.string.isw_dollar_title_amount, paymentInfo.amountString)
                }
                val pinHintString = getString(R.string.isw_title_pin_amount, amountString)
                val spannableHint = SpannableString(pinHintString)


                // increase font size and change font color of amount
                val startIndex = 68
                val endIndex = startIndex + amountString.length + 1
                val codeColor = ContextCompat.getColor(requireContext(), R.color.iswColorPrimary)
                spannableHint.setSpan(AbsoluteSizeSpan(21, true), startIndex, endIndex, 0)
                spannableHint.setSpan(StyleSpan(Typeface.BOLD), startIndex, endIndex, 0)
                spannableHint.setSpan(
                    ForegroundColorSpan(codeColor),
                    startIndex,
                    endIndex,
                    0
                )

                // set the hint for pin
                pinHint.text = spannableHint


                // show PIN container
                showContainer(CardTransactionState.EnterPin)
                cardPin.setText("")
            }

            // when user types in pin
            is EmvMessage.PinText -> {
                cardPin.setText(message.text)
            }

            // when pin has been validated
            is EmvMessage.PinOk -> {
                pinOk = true
                toast("Pin OK")
            }

            // when the user enters an incomplete pin
            is EmvMessage.IncompletePin -> {

                alert.setTitle("Invalid Pin")
                alert.setMessage("Please press the CANCEL (X) button and try again")
                alert.show()
            }

            // when pin is incorrect
            is EmvMessage.PinError -> {
                alert.setTitle("Invalid Pin")
                alert.setMessage("Please ensure you put the right pin.")
                alert.show()

                // dismiss alert in 3 seconds
                Handler().postDelayed({ alert.dismiss() }, 3000).run {
                    println("posted: $this")
                }
            }

            // when user cancels transaction
            is EmvMessage.TransactionCancelled -> {
                cancelTransaction(message.reason)
            }

            // when transaction is processing
            is EmvMessage.ProcessingTransaction -> {
                // show transaction progress alert
                parent.showLoadingMessage("Processing Transaction...")
            }
        }
    }

    private fun showContainer(state: CardTransactionState) {

        // card flow containers
        val containers = arrayOf(insertPinContainer,
            chooseAccountContainer,
            insertCardContainer
        )

        // get current state container to be visible
        val container = when (state) {
            CardTransactionState.InsertCard -> insertCardContainer
            CardTransactionState.SelectAccountType -> chooseAccountContainer
            CardTransactionState.EnterPin -> insertPinContainer
        }

        containers.forEach {
            // hide other containers
            if (it != container) it.visibility = View.GONE
            // show selected container
            else container.apply {
                visibility = View.VISIBLE
                bringToFront()
            }
        }

    }

    private fun showCurrencyDialog(){
        val currencyDialog = CurrencyDialog{
            when(it){
                0 -> {
                    CURRENCYTYPE = IswLocal.NIGERIA.currency
                    //cancelDialog.show()
                    DialogUtils.getAlertDialog(context!!)
                        .setTitle("Continue")
                        .setMessage("Naira Transaction, do you want to continue?")
                        .setCancelable(false)
                        .setNegativeButton(R.string.isw_no) { dialog, _ ->
                            showCurrencyDialog()
                            dialog.dismiss()
                        }
                        .setPositiveButton(R.string.isw_yes) { dialog, _ ->
                            observeViewModel()
                            // setup transaction
                            cardViewModel.setupTransaction(paymentInfo.amount, terminalInfo)
                        }
                        .create()
                        .show()
                }

                1 -> {
                    CURRENCYTYPE = IswLocal.USA.currency
                    //cancelDialog.show()
                    DialogUtils.getAlertDialog(context!!)
                        .setTitle("Continue")
                        .setMessage("Dollar Transaction, do you want to continue?")
                        .setCancelable(false)
                        .setNegativeButton(R.string.isw_no) { dialog, _ ->
                            showCurrencyDialog()
                            dialog.dismiss()
                        }
                        .setPositiveButton(R.string.isw_yes) { dialog, _ ->
                            observeViewModel()
                            // setup transaction
                            cardViewModel.setupTransaction(paymentInfo.amount, terminalInfo)
                        }.create()
                        .show()
                }
            }
        }
        currencyDialog.show(childFragmentManager, TAG)
    }

//    private val cancelDialog by lazy {
//        if (CURRENCYTYPE == IswLocal.USA.currency){
//            DialogUtils.getAlertDialog(context!!)
//                .setTitle("Continue")
//                .setMessage("Dollar Transaction, do you want to continue?")
//                .setCancelable(false)
//                .setNegativeButton(R.string.isw_no) { dialog, _ ->
//                    showCurrencyDialog()
//                    dialog.dismiss()
//                }
//                .setPositiveButton(R.string.isw_yes) { dialog, _ ->
//                    observeViewModel()
//
//                    // setup transaction
//                    cardViewModel.setupTransaction(paymentInfo.amount, terminalInfo)
//                }
//                .create()
//        }
//        else {
//            DialogUtils.getAlertDialog(context!!)
//                .setTitle("Continue")
//                .setMessage("Naira Transaction, do you want to continue?")
//                .setCancelable(false)
//                .setNegativeButton(R.string.isw_no) { dialog, _ ->
//                    showCurrencyDialog()
//                    dialog.dismiss()
//                }
//                .setPositiveButton(R.string.isw_yes) { dialog, _ ->
//                    observeViewModel()
//
//                    // setup transaction
//                    cardViewModel.setupTransaction(paymentInfo.amount, terminalInfo)
//                }
//                .create()
//        }
//    }

    private fun cancelTransaction(reason: String) {
        // return early if already cancelled
        if (isCancelled) return

        // remove dialogs
        if (alert.isShowing) alert.dismiss()
        // set flag
        isCancelled = true

        // close loader
        parent.closeLoader()

        // set reason and show cancel dialog
        parent.showCancelDialog(reason)
    }

    companion object {
        fun newInstance(): IswCardFlowFragment = IswCardFlowFragment()
    }
}