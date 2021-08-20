package com.interswitchng.smartpos.emv.pax.old.models

import com.interswitchng.smartpos.old_shared.models.posconfig.PrintObject

internal class UssdQrSlip(config: POSConfiguration, status: TransactionStatus, private val info: TransactionInfo): TransactionSlip(config, status) {


    override fun getTransactionInfo(): List<PrintObject> {

        val stan = pairString("stan", info.stan)
        val date = pairString("date", info.dateTime)
        val amount = pairString("amount", info.amount)
        val authCode = pairString("authorization code", info.authorizationCode)
        val pinStatus = PrintObject.Data(info.pinStatus)

        return listOf(stan, date, amount, authCode, pinStatus,  PrintObject.Line)
    }

}
