package com.interswitchng.smartpos.shared.models.transaction

import android.os.Parcel
import android.os.Parcelable
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.printer.info.TransactionInfo
import com.interswitchng.smartpos.shared.models.printer.info.TransactionStatus
import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.printer.slips.CardSlip
import com.interswitchng.smartpos.shared.models.printer.slips.CashSlip
import com.interswitchng.smartpos.shared.models.printer.slips.TransactionSlip
import com.interswitchng.smartpos.shared.models.printer.slips.UssdQrSlip
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.CardType
import com.interswitchng.smartpos.shared.services.utils.IsoUtils


/**
 * This class represents the final result
 * of the triggered purchase transaction.
 * This is what captures the transaction's result
 */
internal data class TransactionResultData(
    val paymentType: PaymentType,
    val stan: String,
    val dateTime: String,
    val amount: String,
    val type: TransactionType,
    val cardPan: String,
    val cardType: CardType,
    val cardExpiry: String,
    val authorizationCode: String,
    val pinStatus: String,
    val responseMessage: String,
    val responseCode: String,
    val AID: String,
    val code: String,
    val telephone: String,
    val txnDate: Long,
    val transactionId: String = "",
    val cardHolderName: String,
    val remoteResponseCode: String = "",
    val biller: String? = "",
    val customerDescription: String? = "",
    val surcharge: String? = "",
    val additionalAmounts: String? = "",
    val currencyType: IswPaymentInfo.CurrencyType
) : Parcelable {


    constructor(parcel: Parcel) : this(
        getPaymentType(parcel.readInt()),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        getTransactionType(parcel.readInt()),
        parcel.readString()!!,
        getCardType(parcel.readInt()),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        getCurrencyType(parcel.readInt())
    )

    val isSuccessful: Boolean get() = responseCode == IsoUtils.OK

    fun getSlip(terminal: TerminalInfo): TransactionSlip {
        return when (paymentType) {
            PaymentType.USSD, PaymentType.QR -> UssdQrSlip(
                terminal,
                getTransactionStatus(),
                getTransactionInfo()
            )
            PaymentType.Card, PaymentType.PayCode -> CardSlip(
                terminal,
                getTransactionStatus(),
                getTransactionInfo()
            )
            PaymentType.Cash -> CashSlip(
                    terminal,
                    getTransactionStatus(),
                    getTransactionInfo()
            )
        }
    }

    /// function to extract
    /// print slip transaction info
    fun getTransactionInfo() =
        TransactionInfo(
            paymentType = paymentType,
            stan = stan,
            dateTime = dateTime,
            amount = amount,
            type = type,
            cardPan = cardPan,
            cardType = cardType.name,
            cardExpiry = cardExpiry,
            authorizationCode = authorizationCode,
            pinStatus = pinStatus,
            responseCode = responseCode,
            transactionId = transactionId,
            cardHolderName = cardHolderName,
            remoteResponseCode = remoteResponseCode,
            biller = biller,
            customerDescription = customerDescription,
            surcharge = surcharge,
            additionalAmounts = additionalAmounts,
            currencyType = currencyType
        )


    /// function to extract
    /// print slip transaction status
    fun getTransactionStatus() =
        TransactionStatus(
            responseMessage = responseMessage,
            responseCode = responseCode,
            AID = AID,
            telephone = telephone
        )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(paymentType.ordinal)
        parcel.writeString(stan)
        parcel.writeString(dateTime)
        parcel.writeString(amount)
        parcel.writeInt(type.ordinal)
        parcel.writeString(cardPan)
        parcel.writeInt(cardType.ordinal)
        parcel.writeString(cardExpiry)
        parcel.writeString(authorizationCode)
        parcel.writeString(pinStatus)
        parcel.writeString(responseMessage)
        parcel.writeString(responseCode)
        parcel.writeString(AID)
        parcel.writeString(code)
        parcel.writeString(telephone)
        parcel.writeLong(txnDate)
        parcel.writeString(transactionId)
        parcel.writeString(cardHolderName)
        parcel.writeString(remoteResponseCode)
        parcel.writeString(biller)
        parcel.writeString(customerDescription)
        parcel.writeString(surcharge)
        parcel.writeString(additionalAmounts)
        parcel.writeInt(currencyType.ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionResultData> {
        override fun createFromParcel(parcel: Parcel): TransactionResultData {
            return TransactionResultData(parcel)
        }

        override fun newArray(size: Int): Array<TransactionResultData?> {
            return arrayOfNulls(size)
        }

        private fun getPaymentType(ordinal: Int): PaymentType {
            return when (ordinal) {
                PaymentType.Card.ordinal -> PaymentType.Card
                PaymentType.QR.ordinal -> PaymentType.QR
                PaymentType.USSD.ordinal -> PaymentType.USSD
                PaymentType.Cash.ordinal -> PaymentType.Cash
                else -> PaymentType.PayCode
            }
        }

        private fun getTransactionType(ordinal: Int): TransactionType = when (ordinal) {
            TransactionType.PreAuth.ordinal -> TransactionType.PreAuth
            TransactionType.Completion.ordinal -> TransactionType.Completion
            TransactionType.Refund.ordinal -> TransactionType.Refund
            TransactionType.IFIS.ordinal -> TransactionType.IFIS
            TransactionType.Payments.ordinal -> TransactionType.Payments
            TransactionType.CashBack.ordinal -> TransactionType.CashBack
            else -> TransactionType.Purchase
        }

        private fun getCurrencyType(ordinal: Int): IswPaymentInfo.CurrencyType {
            return when (ordinal) {
                IswPaymentInfo.CurrencyType.NAIRA.ordinal -> IswPaymentInfo.CurrencyType.NAIRA
                else -> IswPaymentInfo.CurrencyType.DOLLAR
            }
        }

        private fun getCardType(ordinal: Int) = when (ordinal) {
            CardType.VISA.ordinal -> CardType.VISA
            CardType.VERVE.ordinal -> CardType.VERVE
            CardType.MASTER.ordinal -> CardType.MASTER
            CardType.AMERICANEXPRESS.ordinal -> CardType.AMERICANEXPRESS
            else -> CardType.None
        }
    }

}