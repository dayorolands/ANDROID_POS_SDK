package com.interswitchng.smartpos.shared.models.transaction

import android.os.Parcel
import android.os.Parcelable
import java.util.*


/**
 * This class represents the purchase request
 * triggered by external source that depends the SDK
 */
internal data class IswPaymentInfo(
    val amount: Int,
    val currentStan: String,
    val surcharge: Int = 0,
    val additionalAmounts: Int = 0,
    val currencyType: CurrencyType? = CurrencyType.NAIRA
) : Parcelable {


    val amountString: String get() = String.format(Locale.getDefault(), "%,.2f",
        amount.toDouble() / 100.toDouble())

    val additionalAmountsString: String get() = String.format(Locale.getDefault(), "%,.2f",
            additionalAmounts.toDouble() / 100.toDouble())

    val surchargeString: String get() = String.format(Locale.getDefault(), "%,.2f",
            surcharge.toDouble() / 100.toDouble())

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    )

    enum class CurrencyType{
        NAIRA, DOLLAR
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(amount)
        parcel.writeString(currentStan)
        parcel.writeInt(surcharge)
        parcel.writeInt(additionalAmounts)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IswPaymentInfo> {
        override fun createFromParcel(parcel: Parcel): IswPaymentInfo {
            return IswPaymentInfo(parcel)
        }

        override fun newArray(size: Int): Array<IswPaymentInfo?> {
            return arrayOfNulls(size)
        }
    }
}