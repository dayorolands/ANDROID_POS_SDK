package com.interswitchng.smartpos.shared.models.transaction

import com.google.gson.Gson
import com.interswitchng.smartpos.shared.models.core.IswLocal
import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.CardType
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmModule
import java.util.*
import kotlin.collections.HashMap

/**
 * This class is responsible for capturing
 * transaction results, to be logged to DB
 */
open class TransactionLog(
    @PrimaryKey var id: Int = 0,
    var paymentType: Int = 0,
    var stan: String = "",
    var dateTime: String = "",
    var amount: String = "",
    var transactionType: Int = TransactionType.Purchase.ordinal,
    var cardPan: String = "",
    var cardType: Int = CardType.None.ordinal,
    var cardExpiry: String = "",
    var cardHolderName: String = "",
    var transactionId: String = "",
    var authorizationCode: String = "",
    var pinStatus: String = "",
    var responseMessage: String = "",
    var responseCode: String = "",
    var AID: String = "",
    var code: String = "",
    var telephone: String = "",
    var time: Long = Date().time,
    var additionalInfo: String = "" //use this to serialize other misc fields
) : RealmObject() {


    /**
     * This function transforms this instance of transaction
     * log to it corresponding [TransactionResultData]
     *
     * @return  the transaction result that was captured by the log
     * @see [TransactionResultData]
     */
    internal fun toResult(): TransactionResultData {
        var cashBackAmount = ""
        var surcharge = ""

        val paymentType = when (this.paymentType) {
            PaymentType.PayCode.ordinal -> PaymentType.PayCode
            PaymentType.USSD.ordinal -> PaymentType.USSD
            PaymentType.QR.ordinal -> PaymentType.QR
            PaymentType.Cash.ordinal -> PaymentType.Cash
            else -> PaymentType.Card
        }

        val cardType = when (this.cardType) {
            CardType.VERVE.ordinal -> CardType.VERVE
            CardType.MASTER.ordinal -> CardType.MASTER
            CardType.VISA.ordinal -> CardType.VISA
            CardType.AMERICANEXPRESS.ordinal -> CardType.AMERICANEXPRESS
            CardType.CHINAUNIONPAY.ordinal -> CardType.CHINAUNIONPAY
            else -> CardType.None
        }

        val type = when(transactionType) {
            TransactionType.PreAuth.ordinal -> TransactionType.PreAuth
            TransactionType.Refund.ordinal -> TransactionType.Refund
            TransactionType.Completion.ordinal -> TransactionType.Completion
            TransactionType.IFIS.ordinal -> TransactionType.IFIS
            TransactionType.Payments.ordinal -> TransactionType.Payments
            TransactionType.CashBack.ordinal -> TransactionType.CashBack
            else -> TransactionType.Purchase
        }

        if(!additionalInfo.isBlank() && additionalInfo != "null"){
            val additionalInfoObj: AdditionalInfo = Gson().fromJson(additionalInfo, AdditionalInfo::class.java)
            if(!additionalInfoObj.additionalAmounts.isNullOrEmpty()) {
                cashBackAmount = additionalInfoObj.additionalAmounts
            }
            if(!additionalInfoObj.surcharge.isNullOrEmpty()){
                surcharge = additionalInfoObj.surcharge
            }
        }


        return TransactionResultData(
            paymentType,
            stan,
            dateTime,
            amount,
            type,
            cardPan,
            cardType,
            cardExpiry,
            authorizationCode,
            pinStatus,
            responseMessage,
            responseCode,
            AID,
            code,
            telephone,
            time,
            // added fields
            cardHolderName = cardHolderName,
            transactionId = transactionId,
            //additionalInfo = additionalInfo,
            additionalAmounts = cashBackAmount,
            surcharge = surcharge
        )
    }

    companion object {

        /**
         * This function transforms a [TransactionResultData] to
         * a TransactionLog
         *
         * @param result  the transaction result to be logged
         * @return   the transformed transaction log containing the result
         */
        internal fun fromResult(result: TransactionResultData) = TransactionLog(
            paymentType = result.paymentType.ordinal,
            stan = result.stan,
            dateTime = result.dateTime,
            amount = result.amount,
            transactionType = result.type.ordinal,
            cardType = result.cardType.ordinal,
            cardExpiry = result.cardExpiry,
            cardPan = result.cardPan,
            authorizationCode = result.authorizationCode,
            pinStatus = result.pinStatus,
            responseMessage = result.responseMessage,
            responseCode = result.responseCode,
            AID = result.AID,
            code = result.code,
            telephone = result.telephone,
            time = result.txnDate,
            cardHolderName = result.cardHolderName,
            transactionId = result.transactionId,
            //additionalInfo = result.additionalInfo.toString()
            additionalInfo = Gson().toJson(AdditionalInfo(result.surcharge, result.additionalAmounts))
            //currencyType
        )
    }
}

@RealmModule(library = true, classes = [TransactionLog::class])
class IswTransactionModule

data class EodSummary(val totalVolume: Int, val successVolume: Int, val failedVolume: Int, val totalValue: String, val successValue: String, val failedValue: String, val successValDollar: String, val failedValDollar: String)

data class AdditionalInfo(val surcharge: String?, val additionalAmounts: String?){

    companion object{
        internal fun toHashMap(additionalInfo: AdditionalInfo): HashMap<String, String?> {
            val keyValue: HashMap<String, String?> = HashMap()
            keyValue["surcharge"] = additionalInfo.surcharge
            keyValue["additionalAmounts"] = additionalInfo.additionalAmounts

            return  keyValue;
        }
    }
}