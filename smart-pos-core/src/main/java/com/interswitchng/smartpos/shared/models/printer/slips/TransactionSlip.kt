package com.interswitchng.smartpos.shared.models.printer.slips

import com.interswitchng.smartpos.shared.models.core.CURRENCYTYPE
import com.interswitchng.smartpos.shared.models.core.IswLocal
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.posconfig.PrintStringConfiguration
import com.interswitchng.smartpos.shared.models.printer.info.TransactionStatus
import com.interswitchng.smartpos.shared.services.utils.IsoUtils


/**
 * This class is serves as the super type for Transaction slips
 * It is represents base functionality for extracting transaction
 * information to be printed out
 *
 * @param terminal  the terminal information used to configure the current terminal
 * @param status  the response status for the current transaction
 */
internal abstract class TransactionSlip(private val terminal: TerminalInfo, private val status: TransactionStatus) {
    protected val line = PrintObject.Line


    /**
     * This method is formatting the pair of strings representing a title-value format
     *
     * @param title  a string representing the title text of the pair of strings
     * @param value  a string representing the value text of the pair of strings
     * @param hasNewLine  a boolean indicating if the value should be printed on a new line
     * @param isUpperCase  a boolean indicating if the value should be all in uppercase
     * @param stringConfig  a configuration for the value text of the pair of strings
     * @return   printable object that has been formatted for printing
     */
    protected fun pairString(title: String, value: String, hasNewLine: Boolean = false, isUpperCase: Boolean = true, stringConfig: PrintStringConfiguration = PrintStringConfiguration()): PrintObject {
        // get title formatted
        val titleCopy = when (title.isEmpty()) {
            true -> ""
            else -> "$title: "
        }

        // get formatted pair
        var result = when (hasNewLine) {
            true -> "$titleCopy\n$value"
            else -> "$titleCopy$value"
        }

        // append new line if not display center
        result = when (stringConfig.displayCenter) {
            true -> result
            else -> "$result\n"
        }


        result = when (isUpperCase) {
            true -> result.toUpperCase()
            else -> result
        }

        return PrintObject.Data(result, stringConfig)
    }


    /**
     * This method extracts the terminal info and returns it as
     * a list of printable objects
     *
     * @return  a list of configured printable objects representing the terminal info
     */
    internal fun getTerminalInfo(): List<PrintObject> {
        val merchantName = pairString("merchant", terminal.merchantNameAndLocation)
        var terminalId = pairString("Terminal Id", terminal.terminalId)
        var merchantId = pairString("Merchant Id", terminal.merchantId)
        if (CURRENCYTYPE == IswLocal.USA.currency){
            terminalId = pairString("Terminal Id", terminal.terminalId2)
            merchantId = pairString("Merchant Id", terminal.merchantId2)
        }

        return listOf(merchantName, terminalId, merchantId, line)
    }


    /**
     * This method extracts the transaction response info and returns
     * it as a list of configured printable objects
     *
     * @return   a list of printable objects representing the transaction response
     */
    internal fun getTransactionStatus(): List<PrintObject> {
        val responseMsg = pairString("", status.responseMessage, stringConfig = PrintStringConfiguration(isTitle = true, displayCenter = true))
        val printList = mutableListOf(responseMsg)
        val emptyLine = pairString("", "")

        //fix for overlap of responseMessage
        printList.add(emptyLine)

        if (status.responseCode.isNotEmpty() && status.responseCode != IsoUtils.TIMEOUT_CODE) {
            val responseCode = pairString("response code", status.responseCode)
            printList.add(responseCode)
        }

        if (status.AID.isNotEmpty()) {
            val aid = pairString("AID", status.AID)
            printList.add(aid)
        }

        val tel = pairString("TEL", status.telephone)
        val retainReceiptMsg = pairString("", "Please retain your receipt", stringConfig = PrintStringConfiguration(displayCenter = true))
        val appNameAndVersion = pairString("", "Kimono v3.0", stringConfig = PrintStringConfiguration(displayCenter = true))
        return printList + listOf(line, retainReceiptMsg, line, appNameAndVersion)
    }


    /**
     * This method is to be implemented by a subclass to return
     * information about the current transaction
     */
    internal abstract fun getTransactionInfo(rePrint: Boolean): List<PrintObject>

    open fun getFootNote(): List<PrintObject> {

        val phoneNumber = PrintObject.Data("Tel: 016283888", PrintStringConfiguration(displayCenter = true))
        val email = PrintObject.Data("Email: support@interswitchng.com", PrintStringConfiguration(displayCenter = true))

        return mutableListOf(pairString("", "Powered by Interswitch", stringConfig = PrintStringConfiguration(displayCenter = true)), phoneNumber, email)
    }

    /**
     * This method generates a combined list of all
     * printable information for this transactions
     */
    internal fun getSlipItems(rePrint: Boolean): MutableList<PrintObject> =
            (getTerminalInfo() + getTransactionInfo(rePrint) + getTransactionStatus() + getFootNote()).toMutableList()
}