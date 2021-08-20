package com.interswitchng.smartpos.emv.pax.old.models

import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.posconfig.PrintStringConfiguration

internal abstract class TransactionSlip(private val posConfig: POSConfiguration, private val status: TransactionStatus) {

    protected fun pairString(title: String, value: String, hasNewLine: Boolean = false, isUpperCase: Boolean = true, stringConfig: PrintStringConfiguration = PrintStringConfiguration()): PrintObject {
        // get title formatted
        val titleCopy = when(title.isEmpty()) {
            true -> ""
            else -> "$title: "
        }

        // get formatted pair
        var result = when(hasNewLine) {
            true -> "$titleCopy\n$value"
            else -> "$titleCopy$value"
        }

        // append new line if not display center
        result = when(stringConfig.displayCenter) {
            true -> result
            else -> "$result\n"
        }


        result = when(isUpperCase) {
            true -> result.toUpperCase()
            else -> result
        }

        return PrintObject.Data(result, stringConfig)
    }

    internal fun getTerminalInfo(): List<PrintObject> {
        val merchantName = pairString("merchant name", posConfig.merchantName, true)
        val merchantLocation = pairString("merchant location", posConfig.merchantLocation, true)
        val terminalId = pairString("Terminal Id", posConfig.terminalId)

        return listOf(merchantName, merchantLocation, terminalId, PrintObject.Line)
    }


    internal fun getTransactionStatus(): List<PrintObject> {
        val responseMsg = pairString("", status.responseMessage, stringConfig = PrintStringConfiguration(isTitle = true, displayCenter = true))
        val responseCode = pairString("response code", status.responseCode)
        val aid = pairString("AID", status.AID)
        val tel = pairString("TEL", status.telephone)
        val line = PrintObject.Line

        return listOf(responseMsg, responseCode, aid, tel,  line)
    }


    internal abstract fun getTransactionInfo(): List<PrintObject>
}