package com.interswitchng.smartpos.shared.models.transaction.ussdqr.request

import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.core.TerminalInfo

/**
 * This class represents transaction information required
 * to issue out uSSD and QR code Purchase Transactions
 */
internal data class TransactionInfo(
        val currencyCode: String,
        val merchantId: String,
        val merchantLocation: String,
        val posGeoCode: String,
        val terminalType: String,
        val uniqueId: String
) {

    companion object {

        internal fun from(terminalInfo: TerminalInfo, paymentInfo: IswPaymentInfo) = TransactionInfo (
                currencyCode = terminalInfo.currencyCode,
                merchantId = terminalInfo.merchantId,
                merchantLocation = terminalInfo.merchantNameAndLocation,
                posGeoCode = terminalInfo.countryCode,
                terminalType = "Android",
                uniqueId =  paymentInfo.currentStan
        )
    }
}