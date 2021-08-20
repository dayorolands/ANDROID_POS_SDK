package com.interswitchng.smartpos.shared.models.printer.info


import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.PaymentType

/**
 * This class captures information to be
 * printed out for a purchase transaction
 */
data class TransactionInfo(
    val paymentType: PaymentType,
    val stan: String,
    val dateTime: String,
    val amount: String,
    val type: TransactionType,
    val cardPan: String,
    val cardType: String,
    val cardExpiry: String,
    val authorizationCode: String,
    val pinStatus: String,
    val responseCode: String,
    val transactionId: String,
    val cardHolderName: String,
    val remoteResponseCode: String,
    val biller: String? = "",
    val customerDescription: String? = "",
    val surcharge: String? = "", //this should be removed in the future
    val additionalInfo: String? = "",
    val additionalAmounts: String? = "",
    val currencyType: IswPaymentInfo.CurrencyType
)