package com.interswitchng.smartpos.shared.models.transaction.payments.billpayment

import android.os.Parcel
import android.os.Parcelable
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo


/**
 * This class is for collecting data specific to bills payment
 * when triggered by external source that depends the SDK
 */
data class IswBillPaymentInfo(
    val paymentCode: String,
    val customerId: String,
    val customerPhone: String,
    val customerEmail: String?
) : Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(paymentCode)
        parcel.writeString(customerId)
        parcel.writeString(customerPhone)
        parcel.writeString(customerEmail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IswBillPaymentInfo> {
        override fun createFromParcel(parcel: Parcel): IswBillPaymentInfo {
            return IswBillPaymentInfo(parcel)
        }

        override fun newArray(size: Int): Array<IswBillPaymentInfo?> {
            return arrayOfNulls(size)
        }
    }
}