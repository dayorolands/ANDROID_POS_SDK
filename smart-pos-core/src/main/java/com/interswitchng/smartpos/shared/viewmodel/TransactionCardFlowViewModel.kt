package com.interswitchng.smartpos.shared.viewmodel

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.interswitchng.smartpos.shared.interfaces.library.IsoService
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.TransactionLog
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvResult
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.AccountType
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.EmvData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.PurchaseType
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.TransactionInfo
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.response.TransactionResponse
import com.interswitchng.smartpos.shared.services.utils.DateUtils
import com.interswitchng.smartpos.shared.services.utils.DateUtils.timeAndDateFormatter
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import java.util.*

internal class TransactionCardFlowViewModel(private val isoService: IsoService) :
        BaseCardViewModel() {

    private lateinit var transactionType: TransactionType
    private lateinit var preAuthTransaction: TransactionLog

    fun setTransactionType(type: TransactionType) {
        transactionType = type
    }

    fun setPreAuthTxn(txn: TransactionLog) {
        preAuthTransaction = txn
    }


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

            val response = initiateTransaction(transactionType, terminalInfo, txnInfo)
            //isoService.initiateCardPurchase(terminalInfo, txnInfo)

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


    private fun initiateTransaction(
            transactionType: TransactionType,
            terminalInfo: TerminalInfo,
            txnInfo: TransactionInfo
    ): TransactionResponse? {
        return when (transactionType) {
            TransactionType.Refund -> isoService.initiateRefund(terminalInfo, txnInfo)
            TransactionType.PreAuth -> isoService.initiatePreAuthorization(
                    terminalInfo,
                    txnInfo
            )
            TransactionType.Completion -> isoService.initiateCompletion(
                    terminalInfo,
                    txnInfo,
                    preAuthStan = preAuthTransaction.stan,
                    preAuthDateTime = timeAndDateFormatter.format(Date(preAuthTransaction.time)),
                    preAuthAuthId = preAuthTransaction.authorizationCode
            )
            TransactionType.CashBack -> isoService.initiatePurchaseWithCashBack(terminalInfo, txnInfo)
            else -> null
        }
    }
}