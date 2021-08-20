package com.interswitchng.smartpos.modules.transactions.purchase.modules.card

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.interswitchng.smartpos.shared.interfaces.library.IsoService
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvResult
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.AccountType
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.EmvData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.PurchaseType
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.TransactionInfo
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.response.TransactionResponse
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import com.interswitchng.smartpos.shared.viewmodel.BaseCardViewModel

internal class CardViewModel(private val isoService: IsoService) :  BaseCardViewModel() {


    override fun processOnline(
        paymentInfo: IswPaymentInfo,
        accountType: AccountType,
        terminalInfo: TerminalInfo,
        emvData: EmvData?,
        completeTransaction: (TransactionResponse) -> EmvResult
    ): Optional<Pair<TransactionResponse, EmvData>> {

        // return response based on data
        if (emvData != null) {
            // create transaction info and issue online purchase request
            val txnInfo =
                TransactionInfo.fromEmv(emvData, paymentInfo, PurchaseType.Card, accountType)
            val response = isoService.initiateCardPurchase(terminalInfo, txnInfo)


            when (response) {
                null -> {
                    _onlineResult.postValue(OnlineProcessResult.NO_RESPONSE)
                    return None
                }
                else -> {
                    // complete transaction by applying scripts
                    // only when responseCode is 'OK'
                    if (response.responseCode == IsoUtils.OK) {
                        // get result code of applying server response
                        val completionResult = completeTransaction(response)

                        // react to result code
                        when (completionResult) {
                            EmvResult.OFFLINE_APPROVED -> _onlineResult.postValue(
                                OnlineProcessResult.ONLINE_APPROVED
                            )
                            else -> _onlineResult.postValue(OnlineProcessResult.ONLINE_DENIED)
                        }
                    }

                    return Some(Pair(response, emvData))
                }
            }
        } else {
            _onlineResult.postValue(OnlineProcessResult.NO_EMV)
            return None
        }
    }
}