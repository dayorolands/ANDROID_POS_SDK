package com.interswitchng.smartpos.modules.transactions.purchase.modules.paycode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.shared.interfaces.library.IsoService
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import com.interswitchng.smartpos.shared.utilities.DisplayUtils
import com.interswitchng.smartpos.shared.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

internal class PayCodeViewModel(private val isoService: IsoService, private val iswPos: IswPos) :
    BaseViewModel() {


    private val _transactionResult = MutableLiveData<Optional<TransactionResultData>>()
    val transactionResult: LiveData<Optional<TransactionResultData>> get() = _transactionResult


    fun processOnline(terminalInfo: TerminalInfo, code: String, iswPaymentInfo: IswPaymentInfo) {
        uiScope.launch {

            val result = withContext(ioScope) {
                val (card, response) =
                    isoService.initiatePaycodePurchase(terminalInfo, code, iswPaymentInfo)

                return@withContext when (response) {
                    null -> None
                    else -> {

                        val now = Date()
                        val responseMsg = IsoUtils.getIsoResultMsg(response.responseCode)
                            ?: "Unknown Error"

                        // extract result
                        Some(
                            TransactionResultData(
                                paymentType = PaymentType.PayCode,
                                dateTime = DisplayUtils.getIsoString(now),
                                amount = iswPaymentInfo.amount.toString(),
                                type = TransactionType.Purchase,
                                authorizationCode = response.authCode,
                                responseMessage = responseMsg,
                                responseCode = response.responseCode,
                                stan = response.stan, pinStatus = "", AID = "", code = "",
                                cardPan = card.pan, cardExpiry = card.expiry, cardType = card.type,
                                telephone = iswPos.config.merchantTelephone,
                                cardHolderName = "",
                                txnDate = response.date
                            )
                        )
                    }
                }
            }


            _transactionResult.value = result
        }
    }

}