package com.interswitchng.smartpos.emv.pax.emv

import android.content.Context
import com.interswitchng.smartpos.emv.pax.models.getCapks
import com.interswitchng.smartpos.emv.pax.utilities.EmvUtils
import com.interswitchng.smartpos.emv.pax.utilities.EmvUtils.bcd2Str
import com.interswitchng.smartpos.emv.pax.utilities.EmvUtils.bytes2String
import com.interswitchng.smartpos.emv.pax.utilities.EmvUtils.str2Bcd
//import com.interswitchng.smartpos.shared.models.core.CURRENCYTYPE
import com.interswitchng.smartpos.shared.models.core.IswLocal
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.posconfig.EmvAIDs
import com.interswitchng.smartpos.shared.models.posconfig.TerminalConfig
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.CardType
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.IccData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.response.TransactionResponse
import com.interswitchng.smartpos.shared.models.transaction.currencyType
import com.interswitchng.smartpos.shared.utilities.FileUtils
import com.interswitchng.smartpos.shared.utilities.Logger
import com.pax.jemv.clcommon.*
import com.pax.jemv.device.DeviceManager
import com.pax.jemv.emv.api.EMVCallback
import com.pax.jemv.emv.model.EmvEXTMParam
import com.pax.jemv.emv.model.EmvMCKParam
import com.pax.jemv.emv.model.EmvParam
import kotlinx.coroutines.runBlocking
import java.util.*


/**
 * This class interacts with the card reader for the pax terminal, serving as an intermediary
 * between the pax terminal and the IswPos SDK
 */
internal class EmvImplementation(private val context: Context, private val pinCallback: PinCallback) {

    val timeout = 30 * 1000L
    private val emvParameters = EmvParam()
    private val mckParameters = EmvMCKParam().also { it.extmParam = EmvEXTMParam() }
    private val emvCallback = EMVCallback.getInstance().also { it.setCallbackListener(Listener()) }
    private val logger = Logger.with("Pax EMV Implementation")
    private lateinit var selectedRID: String

    // get terminal config and EMV apps
    private lateinit var config: Pair<TerminalConfig, EmvAIDs>
    private lateinit var terminalInfo: TerminalInfo


    private var amount: Int = 0

    fun setAmount(amount: Int) {
        this.amount = amount
    }


    private fun addCAPKIntoEmvLib(capks: List<EMV_CAPK>): Int {

        var ret: Int
        val dataList = ByteArray()

        ret = EMVCallback.EMVGetTLVData(0x4F.toShort(), dataList)

        if (ret != RetCode.EMV_OK) {
            ret = EMVCallback.EMVGetTLVData(0x84.toShort(), dataList)
        }

        if (ret == RetCode.EMV_OK) {
            val rid = ByteArray(5)
            System.arraycopy(dataList.data, 0, rid, 0, 5)

            ret = EMVCallback.EMVGetTLVData(0x8F.toShort(), dataList)
            if (ret == RetCode.EMV_OK) {
                val keyId = dataList.data[0]
                logger.log("keyID=$keyId")

                for (capk in capks) {
                    if (bytes2String(capk.rID) == String(rid)) {
                        if (keyId.toInt() == -1 || capk.keyID == keyId) {

                            selectedRID = bcd2Str(capk.rID)
                            // log certified authority public key
                            logger.log("EMVGetTLVData rid=$selectedRID")
                            logger.log("EMVGetTLVData keyID=" + capk.keyID)
                            logger.log("EMVGetTLVData exponentLen=" + capk.exponentLen)
                            logger.log("EMVGetTLVData hashInd=" + capk.hashInd)
                            logger.log("EMVGetTLVData arithInd=" + capk.arithInd)
                            logger.log("EMVGetTLVData modulLen=" + capk.modulLen)
                            logger.log("EMVGetTLVData checkSum=" + bcd2Str(capk.checkSum))


                            // add mutual CAPK
                            ret = EMVCallback.EMVAddCAPK(capk)
                            logger.log("EMVAddCAPK ret=$ret")
                        }
                    }
                }
            }
        }
        return ret
    }

    suspend fun setupContactEmvTransaction(terminalInfo: TerminalInfo): Int {
        // initialize config if not initialized
        if (!::config.isInitialized)
            config =  FileUtils.getConfigurations(context, terminalInfo)

        // set terminal info
        this.terminalInfo = terminalInfo


        // initialize emv library data elements
        val initResult = EMVCallback.EMVCoreInit()
        // ensure initialization succeeded
        if (initResult != RetCode.EMV_OK) return initResult

        // TODO figure out what this does
        EMVCallback.EMVSetCallback()


        // getResult default parameters
        EMVCallback.EMVGetParameter(emvParameters)

        val terminalConfig = config.first
        // configure other parameters
        emvParameters.apply {
            capability = str2Bcd(terminalConfig.terminalcapability)
            countryCode = str2Bcd(terminalInfo.countryCode)
            transCurrCode = str2Bcd(terminalInfo.currencyCode)
            if (currencyType == IswPaymentInfo.CurrencyType.DOLLAR){
                transCurrCode = str2Bcd(terminalInfo.currencyCode2)
            }
            merchName = terminalInfo.merchantNameAndLocation.toByteArray()
            termId = terminalInfo.terminalId.toByteArray()
            merchId = terminalInfo.merchantId.toByteArray()
            merchCateCode = terminalInfo.merchantCategoryCode.toByteArray()
            forceOnline = 1
            terminalType = terminalConfig.terminaltype.toByte() //34
            exCapability = str2Bcd(terminalConfig.extendedterminalcapability)
        }

        // set configured parameters
        EMVCallback.EMVSetParameter(emvParameters)

        // getResult kernel MCK default parameters
        EMVCallback.EMVGetMCKParam(mckParameters)
        // configure MCK parameters
        mckParameters.apply {
            ucBypassPin = 1
            ucBatchCapture = 1
            mckParameters.extmParam.apply {
                aucTermAIP = str2Bcd("0800")
                ucUseTermAIPFlg = 1
                ucBypassAllFlg = 1
            }
        }

        // set configured MCK parameters
        EMVCallback.EMVSetMCKParam(mckParameters)

        // set PCI mode to allow offline pin
        EMVCallback.EMVSetPCIModeParam(1, "4,5".toByteArray(), timeout)

        // remove all applications from terminal app list
        EMVCallback.EMVDelAllApp()


        // use terminal config and emv AID to configure EMV Kernel
        val appList = EmvUtils.createAppList(config.first, config.second)
        for (app in appList) {
            val addResult = EMVCallback.EMVAddApp(app)

            if (addResult == RetCode.EMV_OK) logger.log("AddApp succeeded: aid - ${bcd2Str(app.aid)}")
            else return addResult.also { logger.logErr("AddApp failed: code - $addResult: aid - ${bcd2Str(app.aid)}") }
        }


        // verify that all apps were added
        val test = EMV_APPLIST()
        for (i in 0 until appList.size) {
            val ret = EMVCallback.EMVGetApp(i, test)
            logger.log("EmvApiGetApp " + bcd2Str(test.aid))
            if (ret != RetCode.EMV_OK) {
                logger.log("EMVGetApp err= $ret")
                return ret
            }
        }


        // show Input card info
        pinCallback.showInsertCard()


        // trigger app selection by specifying card slot and transactionCount
        val appSelectResult = EMVCallback.EMVAppSelect(0x00, 100)
        if (appSelectResult != RetCode.EMV_OK)
            return appSelectResult.also { logger.logErr("AppSelection Error: code - $appSelectResult") }


        // read app data from chip
        val readResult = EMVCallback.EMVReadAppData()
        if (readResult != RetCode.EMV_OK)
            return readResult.also { logger.logErr("ReadAppData Error: code - $readResult") }
        else extractTags()


        // get capks for AIDs
        val capks = config.second.getCapks()

        // TODO verify that DELAllCAPK is available
        // remove CAPKs
        for (capk in capks) capk.apply {
            EMVCallback.EMVDelCAPK(keyID, rID)
        }


        // add all CAPKs
        addCAPKIntoEmvLib(capks)


        // authenticate card
        val cardAuthResult = EMVCallback.EMVCardAuth()
        if (cardAuthResult != RetCode.EMV_OK)
            return cardAuthResult.also { logger.log("EMVCardAuth Error: code - $cardAuthResult") }
        else extractTags()


        // getResult authentication results
        val errorCode = ByteArray(10)
        val debugInfoResult = EMVCallback.EMVGetDebugInfo(0, errorCode)

        if (debugInfoResult == RetCode.EMV_OK) {
            logger.log("EMVDebugInfo succeeded")
            logger.log("DebugResult ${bcd2Str(errorCode)}")
        } else logger.log("EMVDebugInfo Error: code - $debugInfoResult")


        return cardAuthResult
    }

    fun startContactEmvTransaction(): Int {
        // app cryptogram
        val ac = ACType()
        logger.log("Before EMVStartTransaction")
        logger.log("AC type - ${ac.type}")

        // start the transaction
        val startTransactionResult = EMVCallback.EMVStartTrans(amount.toLong(), 0, ac)

        logger.log("After EMVStartTransaction")
        logger.log("AC type - ${ac.type}")

        if (startTransactionResult != RetCode.EMV_OK)
            logger.logErr("StartTransaction error: code $startTransactionResult")
        else extractTags()


        return when (ac.type) {
            ACType.AC_TC,
            ACType.AC_AAC,
            ACType.AC_ARQC -> ac.type
            else -> startTransactionResult
        }
    }

    fun completeContactEmvTransaction(response: TransactionResponse): Int {
        val authCode = response.authCode.toByteArray()
        val responseCode = response.responseCode.toByteArray()

        EMVCallback.EMVSetTLVData(0x89, authCode, 6)
        EMVCallback.EMVSetTLVData(0x8A, responseCode, 2)

        val script = str2Bcd(response.scripts)
        val responseResult = OnlineResult.ONLINE_APPROVE
        val ac = ACType()


        val completionResult = EMVCallback.EMVCompleteTrans(responseResult, script, script.size, ac)
        if (completionResult != RetCode.EMV_OK) return completionResult.also { logger.logErr("Complete Transaction Error: code: $completionResult") }
        logger.log("AC_Type = ${ac.type}")


        val datalist = com.pax.jemv.clcommon.ByteArray(5)
        EMVCallback.EMVGetTLVData(0x95, datalist)
        logger.log("TLV - TVR 0x95: ${bcd2Str(datalist.data)}")

        EMVCallback.EMVGetTLVData(0x9B, datalist)
        logger.log("TLV - TVR 0x9B: ${bcd2Str(datalist.data, 2)}")



        return when (ac.type) {
            ACType.AC_TC,
            ACType.AC_AAC,
            ACType.AC_ARQC -> ac.type
            else -> completionResult
        }
    }

    suspend fun enterPin(isOnline: Boolean, triesCount: Int, offlineTriesLeft: Int, pan: String) {
        logger.log("offline tries left: $offlineTriesLeft")
        pinCallback.enterPin(isOnline, triesCount, offlineTriesLeft, pan)
    }

    fun getCardType(): CardType {
        val aids = config.second
        val selected = aids.cards.firstOrNull { ::selectedRID.isInitialized && it.aid.startsWith(selectedRID) }
        val isCard = { type: CardType -> selected?.name?.startsWith(type.code, true) ?: false }

        var cardType = CardType.None
        // find matching card
        for (type in CardType.values()) {
            if (isCard(type)) cardType = type
        }

        // return type
        return cardType
    }

    inner class Listener : EMVCallback.EmvCallbackListener {

        override fun emvInputAmount(amountInfo: LongArray) {
            amountInfo[0] = amount.toLong()
            emvCallback.setCallBackResult(RetCode.EMV_OK)

            val bytes = str2Bcd(Integer.toHexString(amount))
            val resultAmt = EMVCallback.EMVSetTLVData(ICCData.TRANSACTION_AMOUNT.tag.toShort(), bytes, bytes.size)
            if (resultAmt != RetCode.EMV_OK) logger.log("Error setting amount TLV: code - $resultAmt")
        }

        override fun emvVerifyPINfailed(pinInfo: ByteArray): Int {
            logger.log("Verify pin failed")

            // return 1 to cancel or 0 to retry
            return 1
        }


        override fun emvWaitAppSel(retryCount: Int, appList: Array<out EMV_APPLIST>, appNumber: Int) {
            emvCallback.setCallBackResult(RetCode.EMV_OK)
        }

        override fun emvSetParam(): Int {
            logger.log("certVerify: For PBOC, so not needed")
            return RetCode.EMV_OK
        }

        override fun emvGetHolderPwd(tryFlag: Int, remainCount: Int, pin: ByteArray?) {
            if (pin == null) {
                logger.log("emvGetHolderPwd pin is null, tryFlag=$tryFlag remainCnt:$remainCount")
            } else {
                logger.log("emvGetHolderPwd pin is not null, tryFlag=$tryFlag remainCnt:$remainCount")
            }

            var ret: Int

            if (pin != null && pin[0].toInt() != 0) {
                ret = pin[0].toInt()
                logger.log("emvGetHolderPwd ret=$ret")
            } else {

                val isOnline = pin == null
                val pan: String = getPan()!!.let {
                    if (terminalInfo.isKimono && isOnline) {
                        // pan manipulation required for kimono
                        var modifiedPan = "0".repeat(16)
                        val startIndex = it.length - 13
                        val endIndex = startIndex + 12
                        val subPan = it.substring(startIndex, endIndex)

                        modifiedPan = modifiedPan.replaceRange(4 until modifiedPan.length, subPan)
                        modifiedPan += "0"
                        return@let modifiedPan
                    } else return@let it
                }

                // trigger enterPin
                runBlocking {
                    enterPin(isOnline, tryFlag, remainCount, pan)
                }

                logger.log("emvGetHolderPwd enterPin finish")

                ret = pinCallback.getPinResult(pan)
                logger.log("GetPinEmv GetPinResult = $ret")
            }

            emvCallback.setCallBackResult(ret)
        }

        override fun emvVerifyPINOK() = runBlocking<Unit> {
            pinCallback.showPinOk()
            logger.log("certVerify: For PBOC, so not needed")
        }


        override fun emvUnknowTLVData(tag: Short, data: com.pax.jemv.clcommon.ByteArray): Int {
            // copyFieldsFrom value into data reference, based on specified value
            when (tag.toInt()) {
                0x9A -> {
                    val date = ByteArray(7)
                    DeviceManager.getInstance().getTime(date)
                    System.arraycopy(date, 1, data.data, 0, 3)
                }
                0x9F1E -> {
                    val sn = ByteArray(10)
                    DeviceManager.getInstance().readSN(sn)
                    System.arraycopy(sn, 0, data.data, 0, Math.min(data.data.size, sn.size))
                }
                0x9F21 -> {
                    val time = ByteArray(7)
                    DeviceManager.getInstance().getTime(time)
                    System.arraycopy(time, 4, data.data, 0, 3)
                }
                0x9F37 -> {
                    val random = ByteArray(4)
                    DeviceManager.getInstance().getRand(random, 4)
                    System.arraycopy(random, 0, data.data, 0, data.data.size)
                }
                0xFF01 -> Arrays.fill(data.data, 0x00.toByte())
                else -> return RetCode.EMV_PARAM_ERR
            }

            data.length = data.data.size

            return RetCode.EMV_OK
        }

        override fun certVerify() {
            logger.log("certVerify: For PBOC, so not needed")
            emvCallback.setCallBackResult(RetCode.EMV_OK)
        }

        override fun emvAdviceProc() {
            logger.log("certVerify: For PBOC, so not needed")
        }


        override fun cRFU2(): Int = RetCode.EMV_OK
    }

    fun getTlv(tag: Int): ByteArray? {
        val byteArray = ByteArray()
        if (EMVCallback.EMVGetTLVData(tag.toShort(), byteArray) == RetCode.EMV_OK) {
            val data = Arrays.copyOfRange(byteArray.data, 0, byteArray.length)
            logger.log("asd" + Integer.toHexString(tag) + ":" + data.size)
            return data
        }
        return null
    }

    fun getTrack2() = getTlv(0x57)

    fun getPan(): String? {
        return getTrack2()?.let {
            val strTrack2 = bcd2Str(it).split("F")[0]
            return@let strTrack2.split("D")[0]
        }
    }

    private fun extractTags() {
        logger.log("---------------------------------------------")
        for (tag in REQUEST_TAGS) {
            val tlv = getTlv(tag.tag)
            val str = tlv?.let { bcd2Str(it) }
            logger.log("tag: ${tag.name}, hex: $str")
        }
        logger.log("---------------------------------------------")
    }

    internal fun getIccData(): IccData {
        // set icc data using specified icc tags
        return IccData(
                TRANSACTION_AMOUNT = ICCData.TRANSACTION_AMOUNT.getTlv() ?: "",
                ANOTHER_AMOUNT = ICCData.ANOTHER_AMOUNT.getTlv() ?: "000000000000",
                APPLICATION_INTERCHANGE_PROFILE = ICCData.APPLICATION_INTERCHANGE_PROFILE.getTlv() ?: "",
                APPLICATION_TRANSACTION_COUNTER = ICCData.APPLICATION_TRANSACTION_COUNTER.getTlv() ?: "",
                CRYPTOGRAM_INFO_DATA = ICCData.CRYPTOGRAM_INFO_DATA.getTlv() ?: "",
                AUTHORIZATION_REQUEST = ICCData.AUTHORIZATION_REQUEST.getTlv() ?: "",
                CARD_HOLDER_VERIFICATION_RESULT = ICCData.CARD_HOLDER_VERIFICATION_RESULT.getTlv() ?: "",
                ISSUER_APP_DATA = ICCData.ISSUER_APP_DATA.getTlv() ?: "",
                TERMINAL_VERIFICATION_RESULT = ICCData.TERMINAL_VERIFICATION_RESULT.getTlv() ?: "",
                // remove leading zero in currency and country codes
                TRANSACTION_CURRENCY_CODE = ICCData.TRANSACTION_CURRENCY_CODE.getTlv()?.substring(1) ?: "",
                TERMINAL_COUNTRY_CODE = ICCData.TERMINAL_COUNTRY_CODE.getTlv()?.substring(1) ?: "",

                TERMINAL_TYPE = ICCData.TERMINAL_TYPE.getTlv() ?: "",
                TERMINAL_CAPABILITIES = ICCData.TERMINAL_CAPABILITIES.getTlv() ?: "",
                TRANSACTION_DATE = ICCData.TRANSACTION_DATE.getTlv() ?: "",
                TRANSACTION_TYPE = ICCData.TRANSACTION_TYPE.getTlv() ?: "",
                UNPREDICTABLE_NUMBER = ICCData.UNPREDICTABLE_NUMBER.getTlv() ?: "",
                DEDICATED_FILE_NAME = ICCData.DEDICATED_FILE_NAME.getTlv() ?: "").apply {


            val tagValues: MutableList<Pair<ICCData, ByteArray?>> = mutableListOf()

            for (tag in REQUEST_TAGS) {
                val tlv = getTlv(tag.tag)
                tagValues.add(Pair(tag, tlv))
            }

            iccAsString = EmvUtils.buildIccString(tagValues)
            INTERFACE_DEVICE_SERIAL_NUMBER = ICCData.INTERFACE_DEVICE_SERIAL_NUMBER.getTlv() ?: ""
            APP_VERSION_NUMBER = ICCData.APP_VERSION_NUMBER.getTlv() ?: ""
            CARD_HOLDER_NAME = EmvUtils.asciiStringToString(ICCData.CARD_HOLDER_NAME.getTlv() ?: "")
        }


    }

    private fun ICCData.getTlv(): String? = getTlv(tag)?.let(::bcd2Str)

}