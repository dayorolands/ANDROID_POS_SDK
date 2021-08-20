package com.interswitchng.smartpos.shared.activities

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.core.UserType
import com.interswitchng.smartpos.shared.models.results.IswTransactionResult
import com.interswitchng.smartpos.shared.models.transaction.AdditionalInfo
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import com.interswitchng.smartpos.shared.models.transaction.currencyType
import com.interswitchng.smartpos.shared.services.utils.DateUtils
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import com.interswitchng.smartpos.shared.utilities.DisplayUtils
import com.interswitchng.smartpos.shared.utilities.toast
import com.interswitchng.smartpos.shared.viewmodel.TransactionDetailViewModel
import kotlinx.android.synthetic.main.isw_activity_transaction_detail.*
import kotlinx.android.synthetic.main.isw_activity_transaction_detail.tvAmount
import kotlinx.android.synthetic.main.isw_content_transaction_card_flow.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class TransactionDetailActivity : BaseMenuActivity(), IswPos.IswPaymentCallback {

    // key-value store
    private val store: KeyValueStore by inject()
    // terminal info
    private val terminalInfo by lazy { TerminalInfo.get(store)!! }
    // transaction details view model
    private val detailsViewModel: TransactionDetailViewModel by viewModel()

    // transaction result
    private lateinit var result: TransactionResultData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.isw_activity_transaction_detail)

        // setup toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // read transaction result
        result = intent.getParcelableExtra(KEY_TRANSACTION)

        // setup the ui
        setupUI(result)

        // observe view model
        observeViewModel()
    }

    private fun setupUI(result: TransactionResultData) {
        // set text views values
        var amountString: String

        if (currencyType == IswPaymentInfo.CurrencyType.DOLLAR){
            amountString = tvAmount.context.getString(
                R.string.isw_dollar_title_amount,
                DisplayUtils.getAmountString(result.amount.toInt()))
        }else{
            amountString = getString(
                    R.string.isw_currency_amount,
            DisplayUtils.getAmountString(result.amount.toInt())
            )
        }
        tvAmount.text = amountString

        tvStan.text = result.stan
        tvResponseCode.text = result.responseCode
        tvResponseMessage.text = result.responseMessage


        tvPaymentType.text = result.paymentType.name
        tvTransactionType.text = result.type.name
        tvAuthCode.text = result.authorizationCode

        val date = Date(result.txnDate)
        tvDate.text = DateUtils.timeOfDateFormat.format(date)


        // get the status of the transaction
        val isSuccessful = result.responseCode == IsoUtils.OK

        // set status based on txn success
        tvStatus.text = if (isSuccessful) "Success" else "Failed"
        val color = if (isSuccessful) R.color.iswColorSuccess else R.color.iswColorError
        statusContainer.setCardBackgroundColor(ContextCompat.getColor(this, color))

        //tvSurcharge.text = result.surcharge
        additionalInfoUI(result)

        printBtn.setOnClickListener {
            // get print-slip
            val printSlip = result.getSlip(terminalInfo)

            // print slip
            detailsViewModel.printSlip(UserType.Customer, printSlip)
        }
    }

    private fun observeViewModel() {

        with(detailsViewModel) {
            val owner = { lifecycle }

            // observe printer message
            printerMessage.observe(owner) {
                it?.let { msg -> toast(msg) }
            }

            // observe print button function
            printButton.observe(owner) { isEnabled ->
                printBtn.isEnabled = isEnabled ?: false
                printBtn.isClickable = isEnabled ?: false
            }
        }
    }

    private fun additionalInfoUI(result: TransactionResultData) {
        var cashBackAmount = 0
        if(!result.additionalAmounts.isNullOrEmpty()){

            val additionalAmount = result.additionalAmounts
            if(!additionalAmount.isNullOrEmpty()){
                tvCashBackAmount.text = getString(
                        R.string.isw_currency_amount,
                        DisplayUtils.getAmountString( additionalAmount.toInt() )
                )
                cashBackAmount = additionalAmount.toInt()
            }

            if(!result.surcharge.isNullOrEmpty()){
                var surcharge = result.surcharge
                if(surcharge[0] == 'C' || surcharge[0] == 'D') surcharge =  surcharge.drop(1)
                tvSurcharge.text = getString(
                        R.string.isw_currency_amount,
                        DisplayUtils.getAmountString( surcharge.toInt() )
                )
            }


            val purchaseAmount = result.amount.toInt() - cashBackAmount
            tvPurchaseAmount.text =  getString(
                    R.string.isw_currency_amount,
                    DisplayUtils.getAmountString(purchaseAmount))

            additionalInfoContainer.visibility = View.VISIBLE
        }


//        if(!additionalInfo.isNullOrEmpty()){
//            val additionalInfoObj: AdditionalInfo = Gson().fromJson(additionalInfo, AdditionalInfo::class.java)
//            //val additionInfoMap = AdditionalInfo.toHashMap(additionalInfoObj)
//
//            if(!additionalInfoObj.surcharge.isNullOrEmpty()){
//                var surcharge = additionalInfoObj.surcharge
//                if(surcharge[0] == 'C' || surcharge[0] == 'D') surcharge =  surcharge.drop(1)
//                tvSurcharge.text = getString(
//                        R.string.isw_currency_amount,
//                        DisplayUtils.getAmountString( surcharge.toInt() )
//                )
//            }
//
//            if(!additionalInfoObj.additionalAmounts.isNullOrEmpty()){
//                val additionalAmount = additionalInfoObj.additionalAmounts
//                if(!additionalAmount.isNullOrEmpty()){
//                    tvCashBackAmount.text = getString(
//                            R.string.isw_currency_amount,
//                            DisplayUtils.getAmountString( additionalAmount.toInt() )
//                    )
//                    cashBackAmount = additionalAmount.toInt()
//                }
//            }
//
////            if(additionInfoMap.containsKey(("additionalAmounts"))){
////                val additionalAmount = additionInfoMap["additionalAmounts"]
////                if(!additionalAmount.isNullOrEmpty()){
////                    tvCashBackAmount.text = getString(
////                            R.string.isw_currency_amount,
////                            DisplayUtils.getAmountString( additionalAmount.toInt() )
////                    )
////                    cashBackAmount = additionalAmount.toInt()
////                }
////            }
////
////            if(additionInfoMap.containsKey(("surcharge"))){
////                var surcharge = additionInfoMap["surcharge"]
////                if(!surcharge.isNullOrEmpty()){
////                    if(surcharge[0] == 'C' || surcharge[0] == 'D') surcharge =  surcharge.drop(1)
////                    tvSurcharge.text = getString(
////                            R.string.isw_currency_amount,
////                            DisplayUtils.getAmountString( surcharge.toInt() )
////                    )
////                }
////            }
//
//            val purchaseAmount = amount - cashBackAmount
//            tvPurchaseAmount.text =  getString(
//                    R.string.isw_currency_amount,
//                    DisplayUtils.getAmountString( purchaseAmount.toInt() ))
//
//            additionalInfoContainer.visibility = View.VISIBLE
//        }
    }

    companion object {
        const val KEY_TRANSACTION = "transaction_key"
    }

    /**
     * Called when user cancels the current payment transaction
     */
    override fun onUserCancel() {
        toast("Refund Cancelled")
    }

    /**
     * Called when the current payment transaction has completed
     */
    override fun onPaymentCompleted(result: IswTransactionResult) {
        val msg =
            if (result.isSuccessful) "Refund Completed Successfully"
            else "Error Processing Refund"

        toast(msg)
    }
}
