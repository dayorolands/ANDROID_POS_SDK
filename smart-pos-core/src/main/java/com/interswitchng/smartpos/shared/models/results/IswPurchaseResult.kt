package com.interswitchng.smartpos.shared.models.results

import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpos.shared.models.transaction.TransactionResultData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.CardType
import com.interswitchng.smartpos.shared.services.utils.IsoUtils


/**
 * A data model representing the final result status of an initiated purchase transaction
 */
class IswPurchaseResult : IswTransactionResult {
    val purchaseMethod: PaymentType

    constructor(
        // parent properties
        responseCode: String,
        responseMessage: String,
        isSuccessful: Boolean,
        amount: Long,
        transactionReference: String,
        cardType: CardType,
        transactionType: TransactionType,

        // local properties
        purchaseMethod: PaymentType
    ) : super(responseCode, responseMessage, isSuccessful, transactionReference, amount, cardType, transactionType) {

        // initialize variables
        this.purchaseMethod = purchaseMethod
    }

    internal constructor(txnResult: TransactionResultData) : this(
        // parent properties
        responseCode = txnResult.responseCode,
        responseMessage = txnResult.responseMessage,
        isSuccessful = txnResult.responseCode == IsoUtils.OK,
        amount = txnResult.amount.toLong(),
        transactionReference = txnResult.stan,
        cardType = txnResult.cardType,
        transactionType = txnResult.type,

        // local properties
        purchaseMethod = txnResult.paymentType
    )
}