package com.interswitchng.smartpos.emv.pax.services

import android.content.Context
import android.os.SystemClock
import com.interswitchng.smartpos.emv.pax.emv.*
import com.interswitchng.smartpos.emv.pax.services.POSDeviceImpl.Companion.INDEX_TIK
import com.interswitchng.smartpos.emv.pax.services.POSDeviceImpl.Companion.INDEX_TPK
import com.interswitchng.smartpos.emv.pax.utilities.EmvUtils
import com.interswitchng.smartpos.shared.interfaces.device.EmvCardReader
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvMessage
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.EmvData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.response.TransactionResponse
import com.interswitchng.smartpos.shared.utilities.Logger
import com.pax.dal.IPed
import com.pax.dal.entity.EDUKPTPinMode
import com.pax.dal.entity.EKeyCode
import com.pax.dal.entity.EPedType
import com.pax.dal.entity.EPinBlockMode
import com.pax.dal.exceptions.EPedDevException
import com.pax.dal.exceptions.PedDevException
import com.pax.jemv.clcommon.ACType
import com.pax.jemv.clcommon.RetCode
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.sendBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvResult as CoreEmvResult

/**
 * This is a the card reader implementation of the [EmvCardReader]
 */
class EmvCardReaderImpl(context: Context) : EmvCardReader, PinCallback, IPed.IPedInputPinListener {

    private val logger by lazy { Logger.with("EmvCardReaderImpl") }
    private val ped by lazy { POSDeviceImpl.dal.getPed(EPedType.INTERNAL) }
    private var isCancelled = false

    private var text = ""

    private val emvImpl by lazy { EmvImplementation(context, this) }
    private lateinit var channel: Channel<EmvMessage>
    private lateinit var channelScope: CoroutineScope


    private var pinResult: Int = RetCode.EMV_OK
    private var pinData: String? = null
    private var ksnData: String? = null
    private var hasEnteredPin: Boolean = false
    private var isKimono: Boolean = false


    //----------------------------------------------------------
    //     Implementation for ISW EmvCardReader interface
    //----------------------------------------------------------

    override suspend fun setupTransaction(amount: Int, terminalInfo: TerminalInfo, channel: Channel<EmvMessage>, scope: CoroutineScope) {
        // set amount and channel scope
        emvImpl.setAmount(amount)
        this.channel = channel
        this.channelScope = scope
        this.isKimono = terminalInfo.isKimono

        // setup emv transaction
        val setupResult = emvImpl.setupContactEmvTransaction(terminalInfo)

        val resultMsg = when (setupResult) {
            EmvResult.EMV_ERR_ICC_CMD.errCode -> EmvResult.EMV_ERR_ICC_CMD
            EmvResult.EMV_ERR_NO_APP.errCode -> EmvResult.EMV_ERR_NO_APP
            EmvResult.EMV_ERR_NO_PASSWORD.errCode -> EmvResult.EMV_ERR_NO_PASSWORD
            EmvResult.EMV_ERR_TIME_OUT.errCode -> EmvResult.EMV_ERR_TIME_OUT
            EmvResult.EMV_ERR_NO_DATA.errCode -> EmvResult.EMV_ERR_NO_DATA
            else -> null
        }

        if (resultMsg != null) callTransactionCancelled(resultMsg.errCode, "Unable to read Card")
        else {
            // try reading the pan
            val pan = emvImpl.getPan()

            // show error if unable to get pan
            if (pan == null) callTransactionCancelled(EmvResult.EMV_ERR_NO_DATA.errCode, "Unable to read Card")
            // else notify card has been read
            else channel.send(EmvMessage.CardRead(emvImpl.getCardType(), pan))
        }
    }


    override fun startTransaction(): CoreEmvResult {
        val result = emvImpl.startContactEmvTransaction()

        hasEnteredPin = true

        return if (!isCancelled) when (result) {
            ACType.AC_TC -> logger.log("offline process approved").let { CoreEmvResult.OFFLINE_APPROVED }
            ACType.AC_ARQC -> logger.log("online should be processed").let { CoreEmvResult.ONLINE_REQUIRED }
            else -> logger.log("offline declined").let { CoreEmvResult.OFFLINE_DENIED }
        } else logger.log("Transaction was cancelled").let { CoreEmvResult.CANCELLED }
    }

    override fun completeTransaction(response: TransactionResponse): CoreEmvResult {
        val result = emvImpl.completeContactEmvTransaction(response)


        return when (result) {
            ACType.AC_TC -> logger.log("online response approved").let { CoreEmvResult.OFFLINE_APPROVED }
            else -> logger.log("online response declined").let { CoreEmvResult.OFFLINE_DENIED }
        }
    }

    override fun cancelTransaction() {
        if (!isCancelled) {
            isCancelled = true
            ped.setInputPinListener(null) // remove the pin pad
        }
    }

    override fun getTransactionInfo(): EmvData? {
        // get track2 data
        return emvImpl.getTrack2()?.let {
            // get pinData (only for online PIN)
            val carPin = pinData ?: ""
            // get the ksn for dukpt pin
            val pinKsn = ksnData ?: ""


            // get track 2 string
            val track2data = EmvUtils.bcd2Str(it)

            // extract pan and expiry
            val strTrack2 = track2data.split("F")[0]
            val pan = strTrack2.split("D")[0]
            val expiry = strTrack2.split("D")[1].substring(0, 4)
            val src = strTrack2.split("D")[1].substring(4, 7)


            val icc = emvImpl.getIccData()
            val aid = EmvUtils.bcd2Str(emvImpl.getTlv(0x9F06)!!)
            // get the card sequence number
            val csnStr = EmvUtils.bcd2Str(emvImpl.getTlv(ICCData.APP_PAN_SEQUENCE_NUMBER.tag)!!)
            val csn = "0$csnStr"
            EmvData(cardPAN = pan, cardExpiry = expiry, cardPIN = carPin, cardTrack2 = track2data, icc = icc, AID = aid, src = src, csn = csn, pinKsn = pinKsn)
        }
    }


    //----------------------------------------------------------
    //      Implementation for PAX PinCallback interface
    //----------------------------------------------------------
    override suspend fun showInsertCard() {
        // notify callback
        channel.send(EmvMessage.InsertCard)

        // try and detect card
        while (coroutineContext.isActive && !isCancelled) {
            if (POSDeviceImpl.dal.icc.detect(0x00)) break
        }

        // notify callback of card detected
        channel.send(EmvMessage.CardDetected)

        // watch for card removal on io thread
        channelScope.launch(Dispatchers.IO) {
            startWatchingCard()
        }
    }

    private suspend fun startWatchingCard() {
        // try and detect card
        while (channelScope.isActive) {
            // check if card cannot be detected
            if (!POSDeviceImpl.dal.icc.detect(0x00)) {
                // notify callback of card removal
                channel.send(EmvMessage.CardRemoved)
                break
            }

            delay(500)
        }
    }

    override fun getPinResult(panBlock: String) = pinResult


    override suspend fun enterPin(isOnline: Boolean, triesCount: Int, offlineTriesLeft: Int, panBlock: String) {

        try {
            // reset text
            text = ""
            // notify callback to show pin
            channel.sendBlocking(EmvMessage.EnterPin)

            // set check interval
            ped.setIntervalTime(1, 1)
            // set input listener
            ped.setInputPinListener(this)

            // show pin input error
            if (triesCount > 0) {
                channel.sendBlocking(EmvMessage.PinError(offlineTriesLeft))
            } else {

                // cancel pin input after specified Timeout
                channelScope.launch(Dispatchers.IO) {
                    // expected timeout
                    val timeout = emvImpl.timeout - 1000
                    // delay till timeout
                    delay(timeout)

                    // cancel pin input if user has not
                    // already entered his pin
                    if (!hasEnteredPin) {
                        // cancel pin input
                        ped.cancelInput()
                        ped.clearScreen()
                        // publish pin input timeout
                        callTransactionCancelled(RetCode.EMV_TIME_OUT, "Pin Input Timeout")
                    }
                }

                // trigger pin input based flag
                if (isOnline) getOnlinePin(panBlock)
                else getOfflinePin()
            }
        } catch (e: PedDevException) {
            logger.logErr("Error occurred verifying pin: isOnline - $isOnline, code - ${e.errCode}, msg - ${e.errMsg}")
            setPinResult(e.errCode) // set the pin result
        }
    }

    override suspend fun showPinOk() {
        channel.send(EmvMessage.PinOk)
    }

    private fun getOnlinePin(panBlock: String) {
        // get user encrypted pin based on kimono flag
        if (isKimono) {
            // get pin block from the terminal using DUKPT
            val pinBlock = ped.getDUKPTPin(INDEX_TIK, "4,6", panBlock.toByteArray(), EDUKPTPinMode.ISO9564_0_INC, emvImpl.timeout.toInt())

            // extract pin result
            if (pinBlock == null) pinResult = RetCode.EMV_NO_PASSWORD
            else {
                pinResult = RetCode.EMV_OK
                pinData = EmvUtils.bcd2Str(pinBlock.result)
                ksnData = EmvUtils.bcd2Str(pinBlock.ksn)
            }

        } else {
            val end = panBlock.length - 2
            val start = end - 11
            // get the 12 right shift of th pan (excluding the check digit)
            val panShifted = panBlock.substring(start..end)
            // add padding to get new pan
            val newPan = "0000$panShifted"
            // get pin block from the terminal using the terminal pin key
            val pinBlock = ped.getPinBlock(INDEX_TPK, "4,6", newPan.toByteArray(), EPinBlockMode.ISO9564_0, emvImpl.timeout.toInt())

            // extract pin result
            if (pinBlock == null) pinResult = RetCode.EMV_NO_PASSWORD
            else {
                pinResult = RetCode.EMV_OK
                pinData = EmvUtils.bcd2Str(pinBlock)
            }
        }
    }

    private fun getOfflinePin() {
        // make system sleep so that PED screen will show up
        SystemClock.sleep(300)
    }


    //----------------------------------------------------------
    //      Implementation for PAX PedInputListener interface
    //----------------------------------------------------------

    override fun onKeyEvent(key: EKeyCode?) {
        // react to key press
        text = when (key) {
            EKeyCode.KEY_CLEAR -> ""
            EKeyCode.KEY_ENTER,
            EKeyCode.KEY_CANCEL -> {
                // remove input listener
                ped.setInputPinListener(null)
                "" + text
            }
            else -> "*$text"
        }


        channelScope.launch(Dispatchers.IO) {
            // check if the user cancelled pin input
            if (key == EKeyCode.KEY_CANCEL) callTransactionCancelled(RetCode.EMV_USER_CANCEL, "User cancelled PIN input")
            else if (key == EKeyCode.KEY_ENTER && text.length < 4) channel.send(EmvMessage.IncompletePin)
            else channel.send(EmvMessage.PinText(text))
        }
    }

    private fun setPinResult(errCode: Int) {
        pinResult = when (errCode) {
            EPedDevException.PED_ERR_INPUT_CANCEL.errCodeFromBasement -> RetCode.EMV_USER_CANCEL
            EPedDevException.PED_ERR_INPUT_TIMEOUT.errCodeFromBasement -> RetCode.EMV_TIME_OUT
            EPedDevException.PED_ERR_PIN_BYPASS_BYFUNKEY.errCodeFromBasement -> RetCode.EMV_NO_PASSWORD
            EPedDevException.PED_ERR_NO_PIN_INPUT.errCodeFromBasement -> RetCode.EMV_NO_PASSWORD
            else -> RetCode.EMV_NO_PINPAD
        }
    }

    private suspend fun callTransactionCancelled(code: Int, reason: String) {
        if (!isCancelled) {
            channel.send(EmvMessage.TransactionCancelled(code, reason))
            cancelTransaction()
        }
    }
}