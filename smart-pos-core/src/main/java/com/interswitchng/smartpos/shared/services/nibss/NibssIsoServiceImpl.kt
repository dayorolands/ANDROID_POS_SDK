package com.interswitchng.smartpos.shared.services.nibss

import android.content.Context
import com.interswitchng.smartpos.IswPos.Companion.getNextStan
import com.interswitchng.smartpos.shared.Constants
import com.interswitchng.smartpos.shared.Constants.KEY_MASTER_KEY
import com.interswitchng.smartpos.shared.Constants.KEY_PIN_KEY
import com.interswitchng.smartpos.shared.Constants.KEY_SESSION_KEY
import com.interswitchng.smartpos.shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.shared.interfaces.library.IsoService
import com.interswitchng.smartpos.shared.interfaces.library.IsoSocket
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.transaction.AdditionalInfo
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.CardDetail
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.CardType
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.TransactionInfo
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.response.TransactionResponse
import com.interswitchng.smartpos.shared.models.transaction.payments.billpayment.InquiryResponse
import com.interswitchng.smartpos.shared.services.utils.DateUtils.dateFormatter
import com.interswitchng.smartpos.shared.services.utils.DateUtils.monthFormatter
import com.interswitchng.smartpos.shared.services.utils.DateUtils.timeAndDateFormatter
import com.interswitchng.smartpos.shared.services.utils.DateUtils.timeFormatter
import com.interswitchng.smartpos.shared.services.utils.DateUtils.yearAndMonthFormatter
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import com.interswitchng.smartpos.shared.services.utils.IsoUtils.TIMEOUT_CODE
import com.interswitchng.smartpos.shared.services.utils.IsoUtils.generatePan
import com.interswitchng.smartpos.shared.services.utils.NibssIsoMessage
import com.interswitchng.smartpos.shared.services.utils.TerminalInfoParser
import com.interswitchng.smartpos.shared.services.utils.TripleDES
import com.interswitchng.smartpos.shared.utilities.FileUtils
import com.interswitchng.smartpos.shared.utilities.Logger
import com.solab.iso8583.IsoType
import com.solab.iso8583.IsoValue
import com.solab.iso8583.parse.ConfigParser
import java.io.StringReader
import java.io.UnsupportedEncodingException
import java.text.ParseException
import java.util.*
import kotlin.math.log

internal class NibssIsoServiceImpl(
    private val context: Context,
    private val store: KeyValueStore,
    private val posDevice: POSDevice,
    private val socket: IsoSocket
) : IsoService {

    private val logger by lazy { Logger.with("IsoServiceImpl") }
    private val messageFactory by lazy {
        try {

            val data = FileUtils.getFromAssets(context)
            val string = String(data!!)
            val stringReader = StringReader(string)
            val messageFactory = ConfigParser.createFromReader(stringReader)
            messageFactory.isUseBinaryBitmap = false //NIBSS usebinarybitmap = false
            messageFactory.characterEncoding = "UTF-8"

            return@lazy messageFactory

        } catch (e: Exception) {
            logger.logErr(e.localizedMessage)
            e.printStackTrace()
            throw e
        }
    }

    private fun makeKeyCall(
        terminalId: String,
        ip: String,
        port: Int,
        code: String,
        key: String
    ): String? {
        try {

            val now = Date()
            val stan = getNextStan()

            val message = NibssIsoMessage(messageFactory.newMessage(0x800))
            message
                .setValue(3, code)
                .setValue(7, timeAndDateFormatter.format(now))
                .setValue(11, stan)
                .setValue(12, timeFormatter.format(now))
                .setValue(13, monthFormatter.format(now))
                .setValue(41, terminalId)

            // remove unset fields
            message.message.removeFields(62, 64)
            message.dump(System.out, "request -- ")

            // set server Ip and port
            socket.setIpAndPort(ip, port)

            // open to socket endpoint
            socket.open()

            // send request and process response
            val response = socket.sendReceive(message.message.writeData())
            // close connection
            socket.close()

            // read message
            val msg = NibssIsoMessage(messageFactory.parseMessage(response, 0))
            msg.dump(System.out, "response -- ")


            // extract encrypted key with clear key
            val encryptedKey = msg.message.getField<String>(SRCI)
            val decryptedKey = TripleDES.soften(key, encryptedKey.value)
            logger.log("Decrypted Key => $decryptedKey")

            return decryptedKey
        } catch (e: UnsupportedEncodingException) {
            logger.logErr(e.localizedMessage)
        } catch (e: ParseException) {
            logger.logErr(e.localizedMessage)
        } catch (e: java.lang.Exception) {
            logger.logErr(e.localizedMessage)
        }

        return null
    }

    override fun downloadKey(terminalId: String, ip: String, port: Int): Boolean {
        // check if its EPMS
        val isEPMS = port == Constants.ISW_TERMINAL_PORT
                && ip == Constants.ISW_TERMINAL_IP

        // getResult clear master key
        val cms = Constants.getCMS(isEPMS)

        // getResult master key & save
        val isDownloaded = makeKeyCall(terminalId, ip, port, "9A0000", cms)?.let { masterKey ->
            store.saveString(KEY_MASTER_KEY, masterKey)
            // load master key into pos
            posDevice.loadMasterKey(masterKey)

            // getResult pin key & save
            val isSessionSaved =
                makeKeyCall(terminalId, ip, port, "9B0000", masterKey)?.let { sessionKey ->
                    store.saveString(KEY_SESSION_KEY, sessionKey)
                    true
                }

            // getResult pin key & save
            val isPinSaved = makeKeyCall(terminalId, ip, port, "9G0000", masterKey)?.let { pinKey ->
                store.saveString(KEY_PIN_KEY, pinKey)

                // load pin key into pos device
                posDevice.loadPinKey(pinKey)
                true
            }

            isPinSaved == true && isSessionSaved == true
        }

        return isDownloaded == true
    }

    override fun downloadTerminalParameters(terminalId: String, ip: String, port: Int): Boolean {
        try {
            val code = "9C0000"
            val field62 = "01009280824266"

            val now = Date()
            val stan = getNextStan()

            val message = NibssIsoMessage(messageFactory.newMessage(0x800))
            message
                .setValue(3, code)
                .setValue(7, timeAndDateFormatter.format(now))
                .setValue(11, stan)
                .setValue(12, timeFormatter.format(now))
                .setValue(13, monthFormatter.format(now))
                .setValue(41, terminalId)
                .setValue(62, field62)


            val bytes = message.message.writeData()
            val length = bytes.size
            val temp = ByteArray(length - 64)
            if (length >= 64) {
                System.arraycopy(bytes, 0, temp, 0, length - 64)
            }


            // confirm that key was downloaded
            val key = store.getString(KEY_SESSION_KEY, "")
            if (key.isEmpty()) return false

            val hashValue = IsoUtils.getMac(key, temp) //SHA256
            message.setValue(64, hashValue)
            message.dump(System.out, "parameter request ---- ")

            // set server Ip and port
            socket.setIpAndPort(ip, port)

            // open socket connection
            socket.open()

            // send request and receive response
            val response = socket.sendReceive(message.message.writeData())
            // close connection
            socket.close()

            // read message
            val responseMessage = NibssIsoMessage(messageFactory.parseMessage(response, 0))
            responseMessage.dump(System.out, "parameter response ---- ")


            // getResult string formatted terminal info
            val terminalDataString = responseMessage.message.getField<String>(62).value
            logger.log("Terminal Data String => $terminalDataString")

            // parse and save terminal info
            val terminalData =
                TerminalInfoParser.parse(terminalId, ip, port, terminalDataString, store)
                    ?.also { it.persist(store) }
            logger.log("Terminal Data => $terminalData")

            return true
        } catch (e: Exception) {
            logger.log(e.localizedMessage)
            e.printStackTrace()
        }

        return false
    }

    override fun initiateCardPurchase(
        terminalInfo: TerminalInfo,
        transaction: TransactionInfo
    ): TransactionResponse {
        val now = Date()
        val message = NibssIsoMessage(messageFactory.newMessage(0x200))
        val processCode = "00" + transaction.accountType.value + "00"
        val hasPin = transaction.cardPIN.isNotEmpty()
        val stan = transaction.stan
        val randomReference = "000000$stan"

        message
            .setValue(2, transaction.cardPAN)
            .setValue(3, processCode)
            .setValue(4, String.format(Locale.getDefault(), "%012d", transaction.amount))
            .setValue(7, timeAndDateFormatter.format(now))
            .setValue(11, stan)
            .setValue(12, timeFormatter.format(now))
            .setValue(13, monthFormatter.format(now))
            .setValue(14, transaction.cardExpiry)
            .setValue(18, terminalInfo.merchantCategoryCode)
            .setValue(22, "051")
            .setValue(23, transaction.csn)
            .setValue(25, "00")
            .setValue(26, "06")
            .setValue(28, "C00000000")
            .setValue(35, transaction.cardTrack2)
            .setValue(37, randomReference)
            .setValue(40, transaction.src)
            .setValue(41, terminalInfo.terminalId)
            .setValue(42, terminalInfo.merchantId)
            .setValue(43, terminalInfo.merchantNameAndLocation)
            .setValue(49, terminalInfo.currencyCode)
            .setValue(55, transaction.iccString)

        if (hasPin) {
            message.setValue(52, transaction.cardPIN)
                .setValue(123, "510101511344101")
            // remove unset fields
            message.message.removeFields(32, 59)
        } else {
            message.setValue(123, "511101511344101")
            // remove unset fields
            message.message.removeFields(32, 52, 59)
        }

        // set message hash
        val bytes = message.message.writeData()
        val length = bytes.size
        val temp = ByteArray(length - 64)
        if (length >= 64) {
            System.arraycopy(bytes, 0, temp, 0, length - 64)
        }

        val sessionKey = store.getString(KEY_SESSION_KEY, "")
        val hashValue = IsoUtils.getMac(sessionKey, temp) //SHA256
        message.setValue(128, hashValue)
        message.dump(System.out, "request -- ")

        try {
            // open connection
            val isConnected = socket.open()
            if (!isConnected) return TransactionResponse(
                TIMEOUT_CODE,
                authCode = "",
                stan = "",
                scripts = "",
                date = now.time
            )


            val request = message.message.writeData()
            logger.log("Purchase Request HEX ---> ${IsoUtils.bytesToHex(request)}")

            val response = socket.sendReceive(request)
            // close connection
            socket.close()

            val responseMsg = NibssIsoMessage(messageFactory.parseMessage(response, 0))
            responseMsg.dump(System.out, "")


            // return response
            return responseMsg.message.let {
                val authCode = it.getObjectValue<String?>(38) ?: ""
                val code = it.getObjectValue<String>(39)
                val scripts = it.getObjectValue<String>(55)
                return@let TransactionResponse(
                    responseCode = code,
                    authCode = authCode,
                    stan = stan,
                    scripts = scripts,
                    date = now.time
                )
            }
        } catch (e: Exception) {
            // log error
            logger.log(e.localizedMessage)
            e.printStackTrace()
            // auto reverse txn purchase
            reversePurchase(message)
            // return response
            return TransactionResponse(
                TIMEOUT_CODE,
                authCode = "",
                stan = transaction.stan,
                scripts = "",
                date = now.time
            )
        }
    }

    override fun initiatePaycodePurchase(
        terminalInfo: TerminalInfo,
        code: String,
        iswPaymentInfo: IswPaymentInfo
    ): Pair<CardDetail, TransactionResponse?> {

        val pan = generatePan(code)
        val amount = String.format(Locale.getDefault(), "%012d", iswPaymentInfo.amount)
        val now = Date()
        val message = NibssIsoMessage(messageFactory.newMessage(0x200))
        val processCode = "001000"
        val stan = getNextStan()
        val randomReference = "000000$stan"
        val date = dateFormatter.format(now)
        val src = "501"

        val expiryDate = Calendar.getInstance().let {
            it.time = now
            val currentYear = it.get(Calendar.YEAR)
            it.set(Calendar.YEAR, currentYear + 1)
            it.time
        }

        // format track 2
        val expiry = yearAndMonthFormatter.format(expiryDate)
        val track2 = "${pan}D$expiry"

        // create card detail
        val card = CardDetail(
            pan = pan,
            expiry = expiry,
            type = CardType.VERVE
        )

        // format iccString data
        val authorizedAmountTLV = String.format("9F02%02d%s", amount.length / 2, amount)
        val transactionDateTLV = String.format("9A%02d%s", date.length / 2, date)
        val iccData =
            "9F260831BDCBC7CFF6253B9F2701809F10120110A50003020000000000000000000000FF9F3704F435D8A29F3602052795050880000000" +
                    "${transactionDateTLV}9C0100${authorizedAmountTLV}5F2A020566820238009F1A0205669F34034103029F3303E0F8C89F3501229F0306000000000000"

        message
            .setValue(2, pan)
            .setValue(3, processCode)
            .setValue(4, amount)
            .setValue(7, timeAndDateFormatter.format(now))
            .setValue(11, stan)
            .setValue(12, timeFormatter.format(now))
            .setValue(13, monthFormatter.format(now))
            .setValue(14, expiry)
            .setValue(18, terminalInfo.merchantCategoryCode)
            .setValue(22, "051")
            .setValue(23, "000")
            .setValue(25, "00")
            .setValue(26, "06")
            .setValue(28, "C00000000")
            .setValue(35, track2)
            .setValue(37, randomReference)
            .setValue(40, src)
            .setValue(41, terminalInfo.terminalId)
            .setValue(42, terminalInfo.merchantId)
            .setValue(43, terminalInfo.merchantNameAndLocation)
            .setValue(49, terminalInfo.currencyCode)
            .setValue(55, iccData)
            .setValue(59, "00") //""90")
            .setValue(123, "510101561344101")

        message.message.removeFields(32, 52)


        // set message hash
        val bytes = message.message.writeData()
        val length = bytes.size
        val temp = ByteArray(length - 64)
        if (length >= 64) {
            System.arraycopy(bytes, 0, temp, 0, length - 64)
        }

        val sessionKey = store.getString(KEY_SESSION_KEY, "")
        val hashValue = IsoUtils.getMac(sessionKey, temp) //SHA256
        message.setValue(128, hashValue)
        message.dump(System.out, "request -- ")

        try {

            // open connection
            val isConnected = socket.open()
            if (!isConnected) return Pair(card, TransactionResponse(
                TIMEOUT_CODE,
                authCode = "",
                stan = iswPaymentInfo.currentStan,
                scripts = "",
                date = now.time
            ))

            val request = message.message.writeData()
            val response = socket.sendReceive(request)
            // close connection
            socket.close()

            val responseMsg = NibssIsoMessage(messageFactory.parseMessage(response, 0))
            responseMsg.dump(System.out, "")


            // return response
            return responseMsg.message.let {
                val authCode = it.getObjectValue<String?>(38) ?: ""
                val scripts = it.getObjectValue<String>(55) ?: ""
                val responseCode = it.getObjectValue<String>(39)

                return@let Pair(card, TransactionResponse(
                    responseCode,
                    authCode = authCode,
                    stan = stan,
                    scripts = scripts,
                    date = now.time
                ))
            }

        } catch (e: Exception) {
            // error message
            logger.log(e.localizedMessage)
            e.printStackTrace()
            // auto reverse txn purchase
            reversePurchase(message)
            // return response
            return Pair(card, TransactionResponse(
                TIMEOUT_CODE,
                authCode = "",
                stan = iswPaymentInfo.currentStan,
                scripts = "",
                date = now.time
            ))
        }
    }

    /**
     * Initiates a pre-authorization transaction using the provided terminal and transaction info, and returns the
     * transaction response provided by EPMS
     *
     * @param terminalInfo  the necessary information that identifies the current POS terminal
     * @param transaction  the purchase information required to perform the transaction
     * @return   response status indicating transaction success or failure
     */
    override fun initiatePreAuthorization(
        terminalInfo: TerminalInfo,
        transaction: TransactionInfo
    ): TransactionResponse? {
        val now = Date()
        val transmissionDateTime = timeAndDateFormatter.format(now)
        try {
            val message = NibssIsoMessage(messageFactory.newMessage(0x100))
            val processCode = "60" + transaction.accountType.value + "00"
            val hasPin = transaction.cardPIN.isNotEmpty()
            val stan = transaction.stan
            val randomReference = "000000$stan"

            Logger.with("IsoServiceImpl").log(transaction.amount.toString())

            message
                .setValue(2, transaction.cardPAN)
                .setValue(3, processCode)
                .setValue(4, String.format(Locale.getDefault(), "%012d", transaction.amount))
                .setValue(7, transmissionDateTime)
                .setValue(11, stan)
                .setValue(12, timeFormatter.format(now))
                .setValue(13, monthFormatter.format(now))
                .setValue(14, transaction.cardExpiry)
                .setValue(18, terminalInfo.merchantCategoryCode)
                .setValue(22, "051")
                .setValue(23, transaction.csn)
                .setValue(25, "00")
                .setValue(26, "06")
                .setValue(28, "C00000000")
                .setValue(35, transaction.cardTrack2)
                .setValue(37, randomReference)
                .setValue(40, transaction.src)
                .setValue(41, terminalInfo.terminalId)
                .setValue(42, terminalInfo.merchantId)
                .setValue(43, terminalInfo.merchantNameAndLocation)
                .setValue(49, terminalInfo.currencyCode)
                .setValue(55, transaction.iccString)


            if (hasPin) {
                message.setValue(52, transaction.cardPIN)
                .setValue(123, "510101511344101")
                logger.log("transaction pin ${transaction.cardPIN}")
                // remove unset fields
                message.message.removeFields(32, 53, 54, 56, 59, 60, 62, 64, 124)
            } else {
                message.setValue(123, "511101511344101")
                // remove unset fields
                message.message.removeFields(32, 52, 53, 54, 56, 59, 60, 62, 64, 124)
            }

            // set message hash
            val bytes = message.message.writeData()
            val length = bytes.size
            val temp = ByteArray(length - 64)
            if (length >= 64) {
                System.arraycopy(bytes, 0, temp, 0, length - 64)
            }

            val sessionKey = store.getString(KEY_SESSION_KEY, "")
            val hashValue = IsoUtils.getMac(sessionKey, temp) //SHA256
            message.setValue(128, hashValue)
            message.dump(System.out, "preAuth request -- ")

            // open connection
            val isConnected = socket.open()
            if (!isConnected) return TransactionResponse(
                TIMEOUT_CODE,
                authCode = "",
                stan = transaction.stan,
                scripts = "",
                date = now.time
            )

            // send request and read response
            val request = message.message.writeData()
            val response = socket.sendReceive(request)

            // close connection
            socket.close()

            val responseMsg = NibssIsoMessage(messageFactory.parseMessage(response, 0))
            responseMsg.dump(System.out, "")

            logger.log("Message ---> Stan == $stan \n Timedate ==> $transmissionDateTime ")
            logger.log("Response code ==> ${responseMsg.message.getObjectValue<String>(39)}")

            // return response
            return responseMsg.message.let {
                val authCode = it.getObjectValue<String?>(38) ?: ""
                val code = it.getObjectValue<String>(39)
                val scripts = it.getObjectValue<String>(55)
                return@let TransactionResponse(
                    responseCode = code,
                    authCode = authCode,
                    stan = stan,
                    scripts = scripts,
                    date = now.time
                )
            }
        } catch (e: Exception) {
            logger.log(e.localizedMessage ?: "Exception occurred performing PreAuth")
            e.printStackTrace()
            return TransactionResponse(
                TIMEOUT_CODE,
                authCode = "",
                stan = transaction.stan,
                scripts = "",
                date = now.time
            )
        }
    }


    /**
     * Initiates a completion transaction using the provided terminal and transaction info, and returns the
     * transaction response provided by EPMS
     *
     * @param terminalInfo  the necessary information that identifies the current POS terminal
     * @param transaction  the information required to perform the transaction
     * @return   response status indicating transaction success or failure
     */
    override fun initiateCompletion(
        terminalInfo: TerminalInfo,
        transaction: TransactionInfo,
        preAuthStan: String,
        preAuthDateTime: String,
        preAuthAuthId: String
    ): TransactionResponse? {

        // time of transaction
        val now = Date()

        try {
            val message = NibssIsoMessage(messageFactory.newMessage(0x220))
            val processCode = "61" + transaction.accountType.value + "00"
            val hasPin = transaction.cardPIN.isNotEmpty()
            val stan = transaction.stan
            val acquiringInstitutionId = "00000111129"
            val forwardingInstitutionId = "00000111129"
            val randomReference = "000000$stan"
            val actualSettlementAmount = "000000000000"
            val actualSettlementFee = "C00000000"
            val actualTransactionFee = "C00000000"

            val originalDataElement =
                "0100$preAuthStan$preAuthDateTime$acquiringInstitutionId$forwardingInstitutionId"
            val replacementAmount = String.format(
                Locale.getDefault(),
                "%012d",
                transaction.amount
            ) + actualSettlementAmount + actualTransactionFee + actualSettlementFee

            message
                .setValue(2, transaction.cardPAN)
                .setValue(3, processCode)
                .setValue(4, String.format(Locale.getDefault(), "%012d", transaction.amount))
                .setValue(7, timeAndDateFormatter.format(now))
                .setValue(11, stan)
                .setValue(12, timeFormatter.format(now))
                .setValue(13, monthFormatter.format(now))
                .setValue(14, transaction.cardExpiry)
                .setValue(18, terminalInfo.merchantCategoryCode)
                .setValue(22, "051")
                .setValue(23, transaction.csn)
                .setValue(25, "00")
                .setValue(26, "06")
                .setValue(28, "C00000000")
                .setValue(35, transaction.cardTrack2)
                .setValue(37, randomReference)
                .setValue(40, transaction.src)
                .setValue(41, terminalInfo.terminalId)
                .setValue(42, terminalInfo.merchantId)
                .setValue(43, terminalInfo.merchantNameAndLocation)
                .setValue(49, terminalInfo.currencyCode)
                .setValue(55, transaction.iccString)
                .setValue(90, originalDataElement)
                .setValue(95, replacementAmount)
                .setValue(123, "510101511344101")



            if (hasPin) {
                message.setValue(52, transaction.cardPIN)
                logger.log("transaction pin ${transaction.cardPIN}")
                // remove unset fields
                message.message.removeFields(9, 29, 30, 31, 32, 33, 50, 53, 54, 56, 58, 59, 60, 62, 64, 67, 98, 100, 102, 103, 124)
            } else {
                // remove unset fields
                message.message.removeFields(9, 29, 30, 31, 32, 33, 50, 52, 53, 54, 56, 58, 59, 60, 62, 64, 67, 98, 100, 102, 103, 124)
            }


            // set message hash
            val bytes = message.message.writeData()
            val length = bytes.size
            val temp = ByteArray(length - 64)
            if (length >= 64) {
                System.arraycopy(bytes, 0, temp, 0, length - 64)
            }

            val sessionKey = store.getString(KEY_SESSION_KEY, "")
            val hashValue = IsoUtils.getMac(sessionKey, temp) //SHA256

            message.setValue(128, hashValue)
            // remove unset fields
            //message.message.removeFields(9, 29, 30, 31, 32, 33, 50, 52, 53, 54, 56, 58, 59, 60, 62, 64, 67, 98, 100, 102, 103, 124)
            message.dump(System.out, "request -- ")

            logger.log("Called ---->Completion message packed")


            // open connection
            val isConnected = socket.open()
            if (!isConnected) return TransactionResponse(
                TIMEOUT_CODE,
                authCode = preAuthAuthId,
                stan = transaction.stan,
                scripts = "",
                date = now.time
            )


            // send request and read response
            val request = message.message.writeData()
            val response = socket.sendReceive(request)

            // close connection
            socket.close()

            val responseMsg = NibssIsoMessage(messageFactory.parseMessage(response, 0))
            responseMsg.dump(System.out, "")


            // return response
            return responseMsg.message.let {
                val authCode = it.getObjectValue<String?>(38) ?: preAuthAuthId
                val code = it.getObjectValue<String>(39)
                val scripts = it.getObjectValue<String>(55) ?: ""

                return@let TransactionResponse(
                    responseCode = code,
                    authCode = authCode,
                    stan = stan,
                    scripts = scripts,
                    date = now.time
                )
            }
        } catch (e: Exception) {
            logger.log(e.localizedMessage ?: "Error occurred performing Completion")
            e.printStackTrace()
            return TransactionResponse(
                TIMEOUT_CODE,
                authCode = preAuthAuthId,
                stan = transaction.stan,
                scripts = "",
                date = now.time
            )
        }
    }


    /**
     * Initiates a refund transaction using the provided terminal and transaction info, and returns the
     * transaction response provided by EPMS
     *
     * @param terminalInfo  the necessary information that identifies the current POS terminal
     * @param transaction  the information required to perform the transaction
     * @return   response status indicating transaction success or failure
     */
    override fun initiateRefund(
        terminalInfo: TerminalInfo,
        transaction: TransactionInfo
    ): TransactionResponse? {

        // time of txn
        val now = Date()
        try {
            val message = NibssIsoMessage(messageFactory.newMessage(0x200))
            val processCode = "20" + transaction.accountType.value + "00"
            val hasPin = transaction.cardPIN.isNotEmpty()
            val stan = transaction.stan
            val randomReference = "000000$stan"
            val timeDateNow = timeAndDateFormatter.format(now)

            message
                .setValue(2, transaction.cardPAN)
                .setValue(3, processCode)
                .setValue(4, String.format(Locale.getDefault(), "%012d", transaction.amount))
                .setValue(7, timeDateNow)
                .setValue(11, stan)
                .setValue(12, timeFormatter.format(now))
                .setValue(13, monthFormatter.format(now))
                .setValue(14, transaction.cardExpiry)
                .setValue(18, terminalInfo.merchantCategoryCode)
                .setValue(22, "051")
                .setValue(23, transaction.csn)
                .setValue(25, "00")
                .setValue(26, "06")
                .setValue(28, "C00000000")
                .setValue(35, transaction.cardTrack2)
                .setValue(37, randomReference)
                .setValue(40, transaction.src)
                .setValue(41, terminalInfo.terminalId)
                .setValue(42, terminalInfo.merchantId)
                .setValue(43, terminalInfo.merchantNameAndLocation)
                .setValue(49, terminalInfo.currencyCode)
                .setValue(55, transaction.iccString)

            if (hasPin) {
                message.setValue(52, transaction.cardPIN)
                    .setValue(123, "510101511344101")

                // remove unset fields
                message.message.removeFields(32, 59)
            } else {
                message.setValue(123, "511101511344101")
                // remove unset fields
                message.message.removeFields(32, 52, 59)
            }

            // set message hash
            val bytes = message.message.writeData()
            val length = bytes.size
            val temp = ByteArray(length - 64)
            if (length >= 64) {
                System.arraycopy(bytes, 0, temp, 0, length - 64)
            }

            val sessionKey = store.getString(KEY_SESSION_KEY, "")
            val hashValue = IsoUtils.getMac(sessionKey, temp) //SHA256
            message.setValue(128, hashValue)
            message.dump(System.out, "request -- ")

            // open connection
            val isConnected = socket.open()
            if (!isConnected) return TransactionResponse(
                TIMEOUT_CODE,
                authCode = "",
                stan = transaction.stan,
                scripts = "",
                date = now.time
            )


            // send request and read response
            val request = message.message.writeData()
            val response = socket.sendReceive(request)

            // close connection
            socket.close()

            val responseMsg = NibssIsoMessage(messageFactory.parseMessage(response, 0))
            responseMsg.dump(System.out, "")

            // return response
            return responseMsg.message.let {
                val authCode = it.getObjectValue<String?>(38) ?: ""
                val code = it.getObjectValue<String>(39)
                val scripts = it.getObjectValue<String>(55)
                return@let TransactionResponse(
                    responseCode = code,
                    authCode = authCode,
                    stan = stan,
                    scripts = scripts,
                    date = now.time
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return TransactionResponse(TIMEOUT_CODE, authCode = "", stan = transaction.stan, scripts = "", date = now.time)
        }
    }

    /**
     * Initiates a reversal transaction using the provided terminal and transaction info, and returns the
     * transaction response provided by EPMS
     *
     * @param terminalInfo  the necessary information that identifies the current POS terminal
     * @param transaction  the information required to perform the transaction
     * @return   response status indicating transaction success or failure
     */
    override fun initiateReversal(
        terminalInfo: TerminalInfo,
        transaction: TransactionInfo
    ): TransactionResponse? {
        TODO("Not Implemented at the moment, not sure if this method is needed")
    }

    override fun initiateBillPayment(
            terminalInfo: TerminalInfo,
            transaction: TransactionInfo,
            inquiryResponse: InquiryResponse
    ): TransactionResponse? {
        val now = Date()
        val message = NibssIsoMessage(messageFactory.newMessage(0x200))
        val processCode = "00" + transaction.accountType.value + inquiryResponse.collectionsAccountType
        val hasPin = transaction.cardPIN.isNotEmpty()
        val stan = transaction.stan
        val randomReference = "000000$stan"

        val amount: Int = if(inquiryResponse.isAmountFixed == "1") inquiryResponse.approvedAmount.toInt() else transaction.amount
        message.message.setField(103, IsoValue(IsoType.LLVAR, 28))
        val field59 = "POSDIRECT::REF=${inquiryResponse.transactionRef}::PRD=${inquiryResponse.biller}::CUSTD=${inquiryResponse.customerDescription}::ITMD=${inquiryResponse.itemDescription}::MISD=${inquiryResponse.narration}"
        message
                .setValue(2, transaction.cardPAN)
                .setValue(3, processCode)
                .setValue(4, String.format(Locale.getDefault(), "%012d", amount))
                .setValue(7, timeAndDateFormatter.format(now))
                .setValue(11, stan)
                .setValue(12, timeFormatter.format(now))
                .setValue(13, monthFormatter.format(now))
                .setValue(14, transaction.cardExpiry)
                .setValue(18, terminalInfo.merchantCategoryCode)
                .setValue(22, "051")
                .setValue(23, transaction.csn)
                .setValue(25, "00")
                .setValue(26, "06")
                .setValue(28, "C00000000")
                .setValue(35, transaction.cardTrack2)
                .setValue(37, randomReference)
                .setValue(40, transaction.src)
                .setValue(41, terminalInfo.terminalId)
                .setValue(42, terminalInfo.merchantId)
                .setValue(43, terminalInfo.merchantNameAndLocation)
                .setValue(49, terminalInfo.currencyCode)
                .setValue(55, transaction.iccString)
                .setValue(59, field59)
                .setValue(103, inquiryResponse.collectionsAccountNumber)

        if (hasPin) {
            message.setValue(52, transaction.cardPIN)
                    .setValue(123, "510101511344101")
            // remove unset fields
            message.message.removeFields(32)
        } else {
            message.setValue(123, "511101511344101")
            // remove unset fields
            message.message.removeFields(32, 52)
        }

        // set message hash
        val bytes = message.message.writeData()
        val length = bytes.size
        val temp = ByteArray(length - 64)
        if (length >= 64) {
            System.arraycopy(bytes, 0, temp, 0, length - 64)
        }

        val sessionKey = store.getString(KEY_SESSION_KEY, "")
        val hashValue = IsoUtils.getMac(sessionKey, temp) //SHA256
        message.setValue(128, hashValue)
        message.dump(System.out, "request -- ")

        try {
            // open connection
            val isConnected = socket.open()
            if (!isConnected) return TransactionResponse(
                    TIMEOUT_CODE,
                    authCode = "",
                    stan = "",
                    scripts = "",
                    date = now.time
            )


            val request = message.message.writeData()
            val response = socket.sendReceive(request)
            // close connection
            socket.close()

            val responseMsg = NibssIsoMessage(messageFactory.parseMessage(response, 0))
            responseMsg.dump(System.out, "")


            // return response
            return responseMsg.message.let {
                val authCode = it.getObjectValue<String?>(38) ?: ""
                val code = it.getObjectValue<String>(39)
                val scripts = it.getObjectValue<String>(55)
                return@let TransactionResponse(
                        responseCode = code,
                        authCode = authCode,
                        stan = stan,
                        scripts = scripts,
                        date = now.time,
                        inquiryResponse = inquiryResponse
                )
            }
        } catch (e: Exception) {
            // log error
            logger.log(e.localizedMessage)
            e.printStackTrace()
            // auto reverse txn purchase
            reversePurchase(message)
            // return response
            return TransactionResponse(
                    TIMEOUT_CODE,
                    authCode = "",
                    stan = transaction.stan,
                    scripts = "",
                    date = now.time
            )
        }
    }


    override fun initiatePurchaseWithCashBack(
            terminalInfo: TerminalInfo,
            transaction: TransactionInfo
    ): TransactionResponse {
        val now = Date()
        val message = NibssIsoMessage(messageFactory.newMessage(0x200))
        message.message.setField(54, IsoValue(IsoType.LLLVAR, 120))
        val processCode = "09" + transaction.accountType.value + "00"
        val hasPin = transaction.cardPIN.isNotEmpty()
        val stan = transaction.stan
        val randomReference = "000000$stan"

        val additionalAmounts = transaction.additionalAmounts.toString();
        val field54 = transaction.accountType.value+"40"+terminalInfo.currencyCode+"D"+ additionalAmounts.padStart(12,'0')
        val formattedSurcharge = 'D'+ String.format(Locale.getDefault(), "%08d", transaction.surcharge)

        message
                .setValue(2, transaction.cardPAN)
                .setValue(3, processCode)
                .setValue(4, String.format(Locale.getDefault(), "%012d", transaction.amount))
                .setValue(7, timeAndDateFormatter.format(now))
                .setValue(11, stan)
                .setValue(12, timeFormatter.format(now))
                .setValue(13, monthFormatter.format(now))
                .setValue(14, transaction.cardExpiry)
                .setValue(18, terminalInfo.merchantCategoryCode)
                .setValue(22, "051")
                .setValue(23, transaction.csn)
                .setValue(25, "00")
                .setValue(26, "06")
                .setValue(28, formattedSurcharge)
                .setValue(35, transaction.cardTrack2)
                .setValue(37, randomReference)
                .setValue(40, transaction.src)
                .setValue(41, terminalInfo.terminalId)
                .setValue(42, terminalInfo.merchantId)
                .setValue(43, terminalInfo.merchantNameAndLocation)
                .setValue(49, terminalInfo.currencyCode)
                .setValue(54, field54)
                .setValue(55, transaction.iccString)

        if (hasPin) {
            message.setValue(52, transaction.cardPIN)
                    .setValue(123, "510101511344101")
            // remove unset fields
            message.message.removeFields(32, 59)
        } else {
            message.setValue(123, "511101511344101")
            // remove unset fields
            message.message.removeFields(32, 52, 59)
        }

        // set message hash
        val bytes = message.message.writeData()
        val length = bytes.size
        val temp = ByteArray(length - 64)
        if (length >= 64) {
            System.arraycopy(bytes, 0, temp, 0, length - 64)
        }

        val sessionKey = store.getString(KEY_SESSION_KEY, "")
        val hashValue = IsoUtils.getMac(sessionKey, temp) //SHA256
        message.setValue(128, hashValue)
        message.dump(System.out, "request -- ")

        try {
            // open connection
            val isConnected = socket.open()
            if (!isConnected) return TransactionResponse(
                    TIMEOUT_CODE,
                    authCode = "",
                    stan = "",
                    scripts = "",
                    date = now.time
            )

            val request = message.message.writeData()
            logger.log("Purchase with cashback Request HEX ---> ${IsoUtils.bytesToHex(request)}")

            val response = socket.sendReceive(request)
            // close connection
            socket.close()

            val responseMsg = NibssIsoMessage(messageFactory.parseMessage(response, 0))
            responseMsg.dump(System.out, "")


            // return response
            return responseMsg.message.let {
                val authCode = it.getObjectValue<String?>(38) ?: ""
                val code = it.getObjectValue<String>(39)
                val scripts = it.getObjectValue<String>(55)
                return@let TransactionResponse(
                        responseCode = code,
                        authCode = authCode,
                        stan = stan,
                        scripts = scripts,
                        date = now.time,
                        additionalInfo = AdditionalInfo(formattedSurcharge, additionalAmounts)
                )
            }
        } catch (e: Exception) {
            // log error
            logger.log(e.localizedMessage)
            e.printStackTrace()
            logger.log("D/IsoServiceImpl++++attempting to send reversal")
            // auto reverse txn purchase
            reversePurchase(message)
            // return response
            return TransactionResponse(
                    TIMEOUT_CODE,
                    authCode = "",
                    stan = transaction.stan,
                    scripts = "",
                    date = now.time
            )
        }
    }


    private fun reversePurchase(txnMessage: NibssIsoMessage): TransactionResponse {
        logger.log("D/IsoServiceImpl+++++ in reversal method")
        val now = Date()
        val stan = getNextStan()

        try {
            val message = NibssIsoMessage(messageFactory.newMessage(0x420))
            val randomReference = "000000$stan"

            val txnAmount = txnMessage.message.getField<Any>(4).toString()
            val txnStan = txnMessage.message.getField<Any>(11).toString()
            val txnDateTime = txnMessage.message.getField<Any>(7).toString()
            val pinData = txnMessage.message.getField<Any>(52)

            // txn acquirer and forwarding code
            val txnCodes = "0000011112900000111129"
            // settlement amt
            val settlement = "000000000000"
            val txnFee = "C00000000"
            val settlementFee = "C00000000"

            val originalDataElements = "0200$txnStan$txnDateTime$txnCodes"
            val replacementAmount = txnAmount + settlement + txnFee + settlementFee

            message
                .copyFieldsFrom(txnMessage)
//                    .setValue(32)
                .setValue(11, stan)
                .setValue(37, randomReference)
                .setValue(56, "4021") // timeout waiting for response
                .setValue(90, originalDataElements)
                .setValue(95, replacementAmount)
                // remove unused fields
                .message.removeFields(28, 32, 53, 55, 59, 62)

            // set or remove pin field
            if (pinData != null) message.setValue(52, pinData.toString())
            else message.message.removeFields(52)


            // set message hash
            val bytes = message.message.writeData()
            val length = bytes.size
            val temp = ByteArray(length - 64)
            if (length >= 64) {
                System.arraycopy(bytes, 0, temp, 0, length - 64)
            }

            val sessionKey = store.getString(KEY_SESSION_KEY, "")
            val hashValue = IsoUtils.getMac(sessionKey, temp) //SHA256
            message.setValue(128, hashValue)
            message.dump(System.out, "reversal request -- ")

            // open connection
            val isConnected = socket.open()
            if (!isConnected) return TransactionResponse(
                TIMEOUT_CODE,
                authCode = "",
                stan = stan,
                scripts = "",
                date = now.time
            )

            logger.log("D/IsoServiceImpl++++sending reversal")
            val request = message.message.writeData()
            val response = socket.sendReceive(request)
            // close connection
            socket.close()

            val responseMsg = NibssIsoMessage(messageFactory.parseMessage(response, 0))
            responseMsg.dump(System.out, "")


            // return response
            return responseMsg.message.let {
                val authCode = it.getObjectValue(38) ?: ""
                val code = it.getObjectValue<String>(39)
                val scripts = it.getObjectValue<String>(55)
                return@let TransactionResponse(
                    responseCode = code,
                    authCode = authCode,
                    stan = stan,
                    scripts = scripts,
                    date = now.time
                )
            }
        } catch (e: Exception) {
            logger.log(e.localizedMessage)
            e.printStackTrace()
            return TransactionResponse(
                TIMEOUT_CODE,
                authCode = "",
                stan = stan,
                scripts = "",
                date = now.time
            )
        }
    }

    companion object {
        private const val SRCI = 53
    }

}