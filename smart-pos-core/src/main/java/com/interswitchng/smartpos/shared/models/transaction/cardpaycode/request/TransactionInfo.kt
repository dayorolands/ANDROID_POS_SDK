package com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request

import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo


/**
 * This class captures transaction request information to be issued
 * out to the EPMS for [PurchaseType] Transactions
 */
internal data class TransactionInfo(
        val cardExpiry: String,
        val cardPIN: String,
        val cardPAN: String,
        val cardTrack2: String,
        val iccString: String,
        val iccData: IccData,
        val src: String, // service restriction code
        val csn: String, // card sequence number
        val amount: Int,
        val stan: String,
        val purchaseType: PurchaseType,
        val accountType: AccountType,
        val pinKsn: String,
        val additionalAmounts: Int = 0,
        val surcharge: Int = 0) {


    companion object {
        fun fromEmv(emv: EmvData, paymentInfo: IswPaymentInfo, purchaseType: PurchaseType, accountType: AccountType) = TransactionInfo (
                cardExpiry =  emv.cardExpiry,
                cardPAN = emv.cardPAN,
                cardPIN =  emv.cardPIN,
                cardTrack2 =  emv.cardTrack2,
                iccString = emv.icc.iccAsString,
                iccData = emv.icc,
                src = emv.src,
                csn = emv.csn,
                amount = paymentInfo.amount,
                additionalAmounts = paymentInfo.additionalAmounts,
                stan = paymentInfo.currentStan,
                purchaseType = purchaseType,
                accountType = accountType,
                pinKsn = emv.pinKsn,
                surcharge = paymentInfo.surcharge)
    }
}