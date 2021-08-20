package com.interswitchng.smartpos.shared.models.printer.slips

import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.posconfig.PrintStringConfiguration
import com.interswitchng.smartpos.shared.models.printer.info.TransactionInfo
import com.interswitchng.smartpos.shared.models.printer.info.TransactionStatus
import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpos.shared.utilities.DisplayUtils

/**
 * @inherit
 *
 * This class is responsible for  generating a print slip
 * for cash transactions
 *
 * @param info the information concerning the current transaction
 */
internal class CashSlip(terminal: TerminalInfo, status: TransactionStatus, private val info: TransactionInfo) : TransactionSlip(terminal, status) {


    /**
     * @inherit
     */
    override fun getTransactionInfo(rePrint: Boolean): List<PrintObject> {


        val typeConfig = PrintStringConfiguration(isTitle = true, isBold = true, displayCenter = true)
        val quickTellerConfig = PrintStringConfiguration(isBold = true, displayCenter = true)
        var quickTellerText = pairString("", "")

//        if (info.type == TransactionType.CashOut) {
//            quickTellerText = pairString("", "Quickteller Paypoint", stringConfig = quickTellerConfig)
//        }

        val txnType = pairString("", info.type.toString(), stringConfig = typeConfig)
        val paymentType = pairString("channel", info.paymentType.toString())
        val stan = pairString("stan", info.stan.padStart(6, '0'))
        val date = pairString("date", info.dateTime.take(10))
        val time = pairString("time", info.dateTime.substring(11, 19))

        var dateTime = pairString("", "")
        var transactionId = pairString("", "")

        if (info.paymentType == PaymentType.Cash) {
            transactionId = pairString("Receipt No", info.transactionId)
        }
        val list = mutableListOf(quickTellerText, txnType, paymentType, date, time, dateTime, transactionId)


        val amount = pairString("amount", DisplayUtils.getAmountWithCurrency(info.amount))
        // if reprinting, add reprint flags below and above amount
        if (rePrint) {
            val rePrintFlag = PrintObject.Data(
                "*** Re-Print ***",
                PrintStringConfiguration(displayCenter = true, isBold = true)
            )
            list.addAll(listOf(line, rePrintFlag, line, amount, line, rePrintFlag, line))
        } else {
            list.addAll(listOf(line, amount, line))
        }

        val authCode = pairString("authorization code", info.authorizationCode)
        list.addAll(listOf(stan, authCode, line))

        // return transaction info of slip
        return list
    }

}