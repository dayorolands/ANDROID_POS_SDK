package com.interswitchng.smartpos.emv.pax.old.models

import com.interswitchng.smartpos.old_shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.old_shared.models.posconfig.PrintStringConfiguration
import java.text.NumberFormat

internal class CardSlip(config: POSConfiguration, status: TransactionStatus, private val info: TransactionInfo): TransactionSlip(config, status) {

    override fun getTransactionInfo(): List<PrintObject> {
        val numberFormat = NumberFormat.getInstance()
        numberFormat.minimumFractionDigits = 2
        numberFormat.maximumFractionDigits = 2

        val txnAmount = numberFormat.format(info.amount.toDouble())

        val pan = run {
            val length = info.cardPan.length
            val firstFour = info.cardPan.substring(0..3)
            val middle = "*".repeat(length - 8)
            val lastFour = info.cardPan.substring(length - 4 until length)
            return@run  "$firstFour$middle$lastFour"
        }

        val typeConfig = PrintStringConfiguration(isTitle = true, isBold = true, displayCenter = true)
        val txnType = pairString("", info.type.toString(), stringConfig = typeConfig)
        val stan = pairString("stan", info.stan)
        val date = pairString("date", info.dateTime)
        val amount = pairString("amount", txnAmount)
        val panConfig = PrintStringConfiguration(isBold = true)
        val cardPan = pairString(info.cardType, pan, true, stringConfig = panConfig)
        val cardExpiry = pairString("expiry date", info.cardExpiry)
        val authCode = pairString("authorization code", info.authorizationCode)
        val pinStatus = pairString("", info.pinStatus)


        val line = PrintObject.Line
        // return transaction info of slip
        return listOf(txnType, stan, date, line, amount, line, cardPan, cardExpiry, authCode, pinStatus, line)
    }

}