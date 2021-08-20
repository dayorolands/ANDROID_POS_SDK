package com.interswitchng.smartpos.shared.models.transaction.cardpaycode.response

import com.interswitchng.smartpos.shared.models.transaction.AdditionalInfo
import com.interswitchng.smartpos.shared.models.transaction.payments.billpayment.InquiryResponse


/**
 * This class captures the transaction response from EPMS
 * for a given purchase request
 */
data class TransactionResponse(
        val responseCode: String, // response code
        val authCode: String, // authorization code
        val stan: String,
        val date: Long,
        val scripts: String,
        val responseDescription: String? = null,
        var transactionId: String? = null,
        val remoteResponseCode: String? = null,
        val inquiryResponse: InquiryResponse? = null,
        val additionalInfo: AdditionalInfo? = null
)
