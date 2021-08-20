package com.interswitchng.smartpos.shared.models.transaction.payments.billpayment

import com.interswitchng.smartpos.shared.services.kimono.models.BillPaymentRequest
import com.interswitchng.smartpos.shared.services.kimono.models.BillPaymentResponse


/**
 * This class captures the transaction response from EPMS
 * for a given purchase request
 */
data class InquiryResponse(// response code
        val narration: String?,
        val uuid: String,
        val approvedAmount: String,
        val transactionRef: String,
        val paymentCode: String,
        val customerId: String,
        val customerPhone: String,
        val customerEmail: String?,
        val collectionsAccountType: String,
        val surcharge: String,
        val itemDescription: String? = "",
        val biller: String? = "",
        val customerDescription: String? = "",
        val isAmountFixed: String? = "",
        val collectionsAccountNumber: String = ""
){
    companion object {
        internal fun fromBillPaymentData(
                billResponse: BillPaymentResponse,
                billRequest: BillPaymentRequest) = InquiryResponse(
                narration = billResponse.responseMessage,
                uuid = billResponse.uuid,
                approvedAmount = billResponse.approvedAmount,
                transactionRef = billResponse.transactionRef,
                paymentCode = billRequest.paymentCode,
                customerId = billRequest.customerId,
                customerPhone = billRequest.customerMobile,
                customerEmail = billRequest.customerEmail,
                collectionsAccountType = billResponse.collectionsAccountType,
                surcharge = billResponse.surcharge,
                itemDescription = billResponse.itemDescription,
                biller = billResponse.biller,
                customerDescription = billResponse.customerDescription,
                isAmountFixed = billResponse.isAmountFixed,
                collectionsAccountNumber = billResponse.collectionsAccountNumber
        )
    }
}