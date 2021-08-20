package com.interswitchng.smartpos.shared.models.results

import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.CardType
import com.interswitchng.smartpos.shared.services.utils.IsoUtils

open class IswTransactionResult internal constructor(
    val responseCode: String,
    val responseMessage: String,
    val isSuccessful: Boolean,
    val transactionReference: String,
    val amount: Long,
    val cardType: CardType,
    val transactionType: TransactionType
) {

    internal constructor(txnResult: TransactionResultData) : this(
        // parent properties
        responseCode = txnResult.responseCode,
        responseMessage = txnResult.responseMessage,
        isSuccessful = txnResult.responseCode == IsoUtils.OK,
        amount = txnResult.amount.toLong(),
        transactionReference = txnResult.stan,
        cardType = txnResult.cardType,
        transactionType = txnResult.type
    )
}