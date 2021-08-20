package com.interswitchng.smartpos.shared.models.printer.slips

import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.posconfig.PrintStringConfiguration
import com.interswitchng.smartpos.shared.models.printer.info.TransactionInfo
import com.interswitchng.smartpos.shared.models.printer.info.TransactionStatus
import com.interswitchng.smartpos.shared.utilities.DisplayUtils


/**
 * @inherit
 *
 * This class is responsible for  generating a print slip
 * for card and paycode transactions
 *
 * @param info the information concerning the current transaction
 */
internal class CardSlip(terminal: TerminalInfo, status: TransactionStatus, private val info: TransactionInfo) : TransactionSlip(terminal, status) {


    /**
     * @inherit
     */
    override fun getTransactionInfo(rePrint: Boolean): List<PrintObject> {


        val typeConfig = PrintStringConfiguration(isTitle = true, isBold = true, displayCenter = true)
        val quickTellerConfig = PrintStringConfiguration(isBold = true, displayCenter = true)
        var quickTellerText = pairString("", "")


        val transactionLabel = if(info.type == TransactionType.CashBack)  "Purchase with cashback" else  info.type.toString()
        val txnType = pairString("", transactionLabel, stringConfig = typeConfig)
        val paymentType = pairString("channel", info.paymentType.toString())
        val stan = pairString("stan", info.stan.padStart(6, '0'))
        val date = pairString("date", info.dateTime.take(10))
        val time = pairString("time", info.dateTime.substring(11, 19))

        val list = mutableListOf(quickTellerText, txnType, paymentType, date, time)

        when(info.type){
            TransactionType.IFIS -> {
                list.add(pairString("switch", info.remoteResponseCode))
                list.add(pairString("ref", info.transactionId))
            }
            TransactionType.Payments -> {
                list.addAll(listOf(line, pairString("ref", info.transactionId), line))
                if(!info.biller.isNullOrEmpty()) list.add(pairString("biller", info.biller))
                if(!info.customerDescription.isNullOrEmpty()) list.add(pairString("Customer", info.customerDescription))
//                if(!info.surcharge.isNullOrEmpty()) list.add(pairString("surcharge", DisplayUtils.getAmountWithCurrency(info.surcharge)))
            }
            TransactionType.CashBack -> {
                if(!info.additionalAmounts.isNullOrEmpty()){
                    list.add(pairString("cashback amount", DisplayUtils.getAmountWithCurrency(
                        info.additionalAmounts
                    )))
                    list.add(pairString("purchase amount", DisplayUtils.getAmountWithCurrency(
                        (info.amount.toInt() - info.additionalAmounts.toInt()).toString()
                    )))
                }
            }
        }

        val amount = pairString("amount", DisplayUtils.getAmtWithCurrency(info.amount, info.currencyType))

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

        // check if its card transaction
        if (info.cardPan.isNotEmpty()) {
            val pan = run {
                val length = info.cardPan.length
                if (length < 10) return@run ""
                val firstSix = info.cardPan.substring(0..5)
                val middle = "*".repeat(length - 8)
                val lastFour = info.cardPan.substring(length - 4 until length)
                return@run "$firstSix$middle$lastFour"
            }
            val panConfig = PrintStringConfiguration(isBold = true)
            val cardType = pairString("card type", info.cardType + "card")
            val cardPan = pairString("card pan", pan, stringConfig = panConfig)
            val cardExpiry = pairString("expiry date", info.cardExpiry)
            val cardHolderName = pairString("name", info.cardHolderName)
            val pinStatus = pairString("", info.pinStatus)
            val rrn =  pairString("rrn", info.stan.padStart(12, '0'))

            list.addAll(listOf(cardType, cardPan, cardExpiry, cardHolderName, stan, rrn))
            if(info.authorizationCode.isNotEmpty()){
                list.add(pairString("authorization code", info.authorizationCode))
            }
            list.addAll(listOf(pinStatus, line))
        }

        // return transaction info of slip
        return list
    }

    override fun getFootNote(): List<PrintObject> {
        val footNoteText = when(info.type){
            TransactionType.IFIS -> "POWERED BY QUICKTELLER PAYPOINT"
            else -> "Powered by Interswitch"
        }

        //val yphoneNumber = PrintObject.Data("Tel: ".plus(Resources.getSystem().getString(R.string.isw_value_support_tel)), PrintStringConfiguration(displayCenter = true))
        val phoneNumber = PrintObject.Data("Tel: 016283888", PrintStringConfiguration(displayCenter = true))
        val email = PrintObject.Data("Email: support@interswitchng.com", PrintStringConfiguration(displayCenter = true))

        //val yemail = PrintObject.Data("Email: ".plus(Resources.getSystem().getString(R.string.isw_value_support_email)) , PrintStringConfiguration(displayCenter = true))

        return mutableListOf(pairString("", footNoteText, stringConfig = PrintStringConfiguration(displayCenter = true)),phoneNumber,email)
    }

}