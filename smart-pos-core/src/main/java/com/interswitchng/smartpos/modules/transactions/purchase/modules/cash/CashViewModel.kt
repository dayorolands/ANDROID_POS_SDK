package com.interswitchng.smartpos.modules.transactions.purchase.modules.cash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.CardType
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.*
import com.interswitchng.smartpos.shared.models.transaction.currencyType
import com.interswitchng.smartpos.shared.services.kimono.KimonoIsoServiceImpl
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import com.interswitchng.smartpos.shared.utilities.DisplayUtils
import com.interswitchng.smartpos.shared.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

internal class CashViewModel(private val isoService: KimonoIsoServiceImpl) : BaseViewModel() {

    private val _transactionResult = MutableLiveData<Optional<TransactionResultData>>()
    val transactionResult: LiveData<Optional<TransactionResultData>> get() = _transactionResult
    private val _fakeCardTrack2 = "5060990580000367864D2702601018444995"
    fun processOnline(paymentInfo: IswPaymentInfo, terminalInfo: TerminalInfo) {

        val iccData = IccData()
        val emvData = EmvData("", "", "", _fakeCardTrack2, "", "", "", iccData, "")

        uiScope.launch {

            val result = withContext(ioScope) {
                // create transaction info and issue online purchase request
                val txnInfo = TransactionInfo.fromEmv(
                    emvData,
                    paymentInfo,
                    PurchaseType.Cash,
                    AccountType.Default
                )

                val response = isoService.initiateCashPurchase(terminalInfo, txnInfo)

                val now = Date()
                val responseMsg = response.responseDescription
                    ?: IsoUtils.getIsoResultMsg(response.responseCode)
                    ?: "Unknown Error"

                // extract result
                return@withContext Some(
                    TransactionResultData(
                        paymentType = PaymentType.Cash,
                        dateTime = DisplayUtils.getIsoString(now),
                        amount = paymentInfo.amount.toString(),
                        type = TransactionType.Purchase,
                        authorizationCode = response.authCode,
                        responseMessage = responseMsg,
                        responseCode = response.responseCode,
                        cardHolderName = emvData.icc.CARD_HOLDER_NAME,
                        stan = response.stan, pinStatus = "", AID = "", code = "",
                        cardPan = "", cardExpiry = "", cardType = CardType.None,
                        telephone = "",
                        txnDate = response.date,
                        transactionId =  response.transactionId.toString(),
                        currencyType = IswPaymentInfo.CurrencyType.values()[currencyType.ordinal]
                    )
                )
            }
            _transactionResult.value = result
        }
    }
}