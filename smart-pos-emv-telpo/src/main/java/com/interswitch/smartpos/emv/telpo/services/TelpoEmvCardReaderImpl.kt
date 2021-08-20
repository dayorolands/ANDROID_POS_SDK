package com.interswitch.smartpos.emv.telpo.services

import android.content.Context
import com.interswitch.smartpos.emv.telpo.emv.ICCData
import com.interswitch.smartpos.emv.telpo.emv.TelpoEmvImplementation
import com.interswitch.smartpos.emv.telpo.emv.TelpoPinCallback
import com.interswitch.smartpos.emv.telpo.services.TelpoPOSDeviceImpl.Companion.INDEX_TPK
import com.interswitchng.smartpos.shared.interfaces.device.EmvCardReader
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvMessage
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvResult
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.EmvData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.response.TransactionResponse
import com.interswitchng.smartpos.shared.utilities.Logger
import com.telpo.emv.EmvPinData
import com.telpo.emv.EmvService
import com.telpo.pinpad.PinParam
import com.telpo.pinpad.PinpadService
import com.telpo.tps550.api.util.StringUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.sendBlocking

internal class TelpoEmvCardReaderImpl(private val context: Context) : EmvCardReader,
    TelpoPinCallback {

    private val emvImpl by lazy { TelpoEmvImplementation(context, this) }

    private val logger = Logger.with("Telpo EMV Card Reader Implementation")
    private lateinit var channel: Channel<EmvMessage>
    private lateinit var channelScope: CoroutineScope

    private var amount: Int = 0

    private var isCancelled = false
    private var pinData: String? = null
    private var ksnData: String? = null
    private var isKimono: Boolean = false

    private var cardPinResult: Int = EmvService.EMV_TRUE

    // private var pinData: String? = null
    private var hasEnteredPin: Boolean = false


    // flag to trigger pin entry
    private var canStartTransaction = false
    private lateinit var transactionResult: EmvResult


    //----------------------------------------------------------
    //     Implementation for ISW EmvCardReader interface
    //----------------------------------------------------------
    override suspend fun setupTransaction(
        amount: Int,
        terminalInfo: TerminalInfo,
        channel: Channel<EmvMessage>,
        scope: CoroutineScope
    ) {
        this.amount = amount
        emvImpl.setAmount(amount)
        this.channel = channel
        this.channelScope = scope
        this.isKimono = terminalInfo.isKimono


        // wrap native call in try catch
        try {
            val result = emvImpl.setupContactEmvTransaction(terminalInfo)
            logger.logErr("Result: $result")

            val resultMsg = when (result) {
                EmvService.ERR_ICCCMD, EmvService.ERR_NOAPP,
                EmvService.ERR_NOPIN, EmvService.ERR_TIMEOUT, EmvService.ERR_NODATA -> 1
                else -> null
            }

            if (resultMsg != null) callTransactionCancelled(resultMsg, "Unable to read Card")
            else {
                // start the transaction, in order to read card info
                val txnResult = emvImpl.startContactEmvTransaction()

                logger.logErr("start txn result code: $txnResult")

                hasEnteredPin = true

                // flag to determine if error occurred
                val noTxnError =
                    txnResult == EmvService.EMV_TRUE && cardPinResult == EmvService.EMV_TRUE

                // get result of triggered transaction
                transactionResult = if (!isCancelled) when {
                    noTxnError -> logger.log("Offline approved").let { EmvResult.ONLINE_REQUIRED }
                    else -> logger.log("Offline declined").let { EmvResult.OFFLINE_DENIED }
                }
                else logger.log("Transaction was cancelled").let { EmvResult.CANCELLED }
            }
        } catch (ex: Exception) {
            val msg = ex.message ?: "Unknown Exception"
            callTransactionCancelled(-1, msg)
        }
    }


    override fun startTransaction(): EmvResult = runBlocking {
        canStartTransaction = true

        // wait till transaction result has been processed
        while (channelScope.isActive && !::transactionResult.isInitialized) {
            delay(1000)
            continue
        }

        // return transaction result if initialized, else cancelled
        if (::transactionResult.isInitialized) transactionResult
        else EmvResult.CANCELLED
    }

    override fun completeTransaction(response: TransactionResponse): EmvResult {
        emvImpl.completeTransaction(response)
        return EmvResult.OFFLINE_APPROVED
    }

    override fun cancelTransaction() {
        if (!isCancelled) {
            isCancelled = true
        }
    }

    override fun getTransactionInfo(): EmvData? {

        return emvImpl.getTrack2()?.let {
            // get pinData (only for online PIN)
            val cardPin = pinData ?: ""
            val pinKsn = ksnData ?: ""

            // get track 2 string
            val track2data = StringUtil.toHexString(it)

            // extract pan and expiry
            val strTrack2 = track2data.split("F")[0]
            val pan = strTrack2.split("D")[0]
            val expiry = strTrack2.split("D")[1].substring(0, 4)
            val src = strTrack2.split("D")[1].substring(4, 7)

            val iccFull = emvImpl.getIccFullData()

            val aid = emvImpl.getTLVString(0x9F06)!!
            // get the card sequence number
            val csnStr = emvImpl.getTLVString(ICCData.APP_PAN_SEQUENCE_NUMBER.tag)!!
            val csn = "0$csnStr"

            EmvData(
                cardPAN = pan, cardExpiry = expiry, cardPIN = cardPin, cardTrack2 = track2data,
                icc = iccFull, AID = aid, src = src, csn = csn, pinKsn = pinKsn
            )
        }
    }


    //----------------------------------------------------------
    //      Implementation for PinCallback interface
    //----------------------------------------------------------
    override suspend fun showInsertCard() {
        // prompts the user to insert card
        channel.send(EmvMessage.InsertCard)
        //Opens the device card reader
        EmvService.IccOpenReader()

        while (channelScope.isActive && !isCancelled) {
            if (EmvService.IccCheckCard(300) == 0) {
                break
            }
            delay(300)
        }

        val ret = EmvService.IccCard_Poweron()
        logger.log("EmvService Power On: RET = $ret")

        channel.send(EmvMessage.CardDetected)

        channelScope.launch(Dispatchers.IO) {
            startWatchingCard()
        }
    }

    private suspend fun startWatchingCard() {
        // try and detect card
        while (channelScope.isActive) {
            // check if card cannot be detected
            if (EmvService.IccCheckCard(10) != 0) {
                // notify callback of card removal
                channel.send(EmvMessage.CardRemoved)
                break
            }

            delay(500)
        }
    }

    override suspend fun showPinOk() = channel.send(EmvMessage.PinOk)

    override val pinResult: Int = cardPinResult

    override suspend fun enterPin(
        isOnline: Boolean,
        isRetry: Boolean,
        offlineTriesLeft: Int,
        panBlock: String,
        emvPinData: EmvPinData?
    ): Int {


        // return if coroutine is non-active or cancelled
        if (!channelScope.isActive || isCancelled) {
            return EmvService.EMV_FALSE
        }

        // send information of read card
        channel.sendBlocking(
            EmvMessage.CardRead(
                cardType = emvImpl.getCardType(),
                cardPan = emvImpl.getPan() ?: ""
            )
        )

        // wait for transaction to be started
        while (channelScope.isActive && !isCancelled) {
            // pause execution till transaction is started
            if (!canStartTransaction) {
                delay(500)
                continue
            } else break
        }

        // return if coroutine is non-active or cancelled
        if (!channelScope.isActive || isCancelled) {
            return EmvService.EMV_FALSE
        }

        // else  notify callback to show pin
        channel.sendBlocking(EmvMessage.EnterPin)


        // show pin input error
        if (isRetry) {
            channel.sendBlocking(EmvMessage.PinError(offlineTriesLeft))
        } else {

            // cancel pin input after specified Timeout
            channelScope.launch(Dispatchers.IO) {
                // expected timeout
                val timeout = 39000L
                // delay till timeout
                delay(timeout)

                // cancel pin input if user has not
                // already entered his pin
                if (!hasEnteredPin) {
                    // cancel pin input
                    PinpadService.TP_PinpadGetPinExit()
                    // publish pin input timeout
                    callTransactionCancelled(PinpadService.PIN_ERROR_TIMEOUT, "Pin Input Timeout")
                }
            }

            val pinParameter = PinParam(context)
            pinParameter.apply {
                PinBlockFormat = 0
                KeyIndex = INDEX_TPK
                WaitSec = 100
                MaxPinLen = 6
                MinPinLen = 4
                IsShowCardNo = 0
                Amount = (amount / 100.0).toString()
                CardNo = emvImpl.getPan()
            }

            PinpadService.Open(context)

            // trigger pin input based flag
            if (isOnline) getOnlinePin(pinParameter)
            else getOfflinePin(pinParameter, emvPinData)
        }

        return cardPinResult
    }

    private fun getOfflinePin(pinParameter: PinParam, emvPinData: EmvPinData?) {

        cardPinResult = when (PinpadService.TP_PinpadGetPlainPin(pinParameter, 0, 0, 0)) {
            PinpadService.PIN_ERROR_CANCEL -> EmvService.ERR_USERCANCEL
            PinpadService.PIN_ERROR_TIMEOUT -> EmvService.ERR_TIMEOUT
            PinpadService.PIN_ERROR_PINLEN -> EmvService.ERR_NOPIN
            PinpadService.PIN_OK -> {
                emvPinData?.Pin = pinParameter.Pin_Block
                runBlocking { showPinOk() }
                EmvService.EMV_TRUE
            }
            else -> EmvService.EMV_FALSE
        }
    }

    private fun getOnlinePin(pinParameter: PinParam) {
        if (isKimono) {

            PinpadService.TP_PinpadDukptSessionStart(0)

            val result = PinpadService.TP_PinpadDukptGetPin(pinParameter)
            cardPinResult = when (result) {
                PinpadService.PIN_ERROR_CANCEL -> EmvService.ERR_USERCANCEL
                PinpadService.PIN_ERROR_TIMEOUT -> EmvService.ERR_TIMEOUT
                PinpadService.PIN_OK -> {
                    pinData = StringUtil.toHexString(pinParameter.Pin_Block)
                    ksnData = StringUtil.toHexString(pinParameter.Curr_KSN)
                    if (pinData!!.contains("00000000")) {
                        EmvService.ERR_NOPIN
                    } else EmvService.EMV_TRUE
                }
                else -> EmvService.EMV_FALSE
            }

            PinpadService.TP_PinpadDukptSessionEnd()

        } else {
            cardPinResult = when (PinpadService.TP_PinpadGetPin(pinParameter)) {
                PinpadService.PIN_ERROR_CANCEL -> EmvService.ERR_USERCANCEL
                PinpadService.PIN_ERROR_TIMEOUT -> EmvService.ERR_TIMEOUT
                PinpadService.PIN_OK -> {
                    pinData = StringUtil.toHexString(pinParameter.Pin_Block)
                    if (pinData!!.contains("00000000")) {
                        EmvService.ERR_NOPIN
                    } else EmvService.EMV_TRUE
                }
                else -> EmvService.EMV_FALSE
            }
        }
    }

    private suspend fun callTransactionCancelled(code: Int, reason: String) {
        if (!isCancelled) {
            channel.send(EmvMessage.TransactionCancelled(code, reason))
            cancelTransaction()
        }
    }
}