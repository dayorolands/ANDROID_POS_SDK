package com.interswitchng.smartpos.shared.services.kimono.models

import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.services.utils.DateUtils
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.util.*

@Root(name = "kmsg", strict = false)
internal class KimonoKeyRequest(
        @field:Element(name = "scheme", required = false)
        val scheme: String,
        @field:Element(name = "app", required = false)
        val app: String,
        @field:Element(name = "terminfo", required = false)
        val keyTerminalInfo: KeyTerminalInfo,
        @field:Element(name = "request", required = false)
        val request: KeyRequest) {

    companion object {
        fun create(terminalInfo: TerminalInfo, deviceName: String, stan: String, keyModulus: String, keyExponent: String) = KimonoKeyRequest(
                scheme = "standard",
                app = "keydownload",
                keyTerminalInfo = KeyTerminalInfo.create(terminalInfo, stan, deviceName),
                request = KeyRequest.create(stan, keyModulus, keyExponent))
    }
}


internal class KeyTerminalInfo(
        @field:Element(name = "mid", required = false)
        val merchantId: String,
        @field:Element(name = "ttype", required = false)
        val terminalType: String,
        @field:Element(name = "tid", required = false)
        val terminalId: String,
        @field:Element(name = "uid", required = false)
        val uniqueId: String,
        @field:Element(name = "mloc", required = false)
        val merchantLocation: String,
        @field:Element(name = "batt", required = false)
        val battery: String,
        @field:Element(name = "tim", required = false)
        val time: String,
        @field:Element(name = "currencycode", required = false)
        val currencyCode: String,
        @field:Element(name = "pstat", required = false)
        val pstat: Int = 1,
        @field:Element(name = "lang", required = false)
        val language: String = "EN",
        @field:Element(name = "posgeocode", required = false)
        val posGeoCode: String,
        @field:Element(name = "poscondcode", required = false)
        val posConditionCode: String = "00") {

    companion object {
        fun create(terminalInfo: TerminalInfo, stan: String, posDeviceName: String) = KeyTerminalInfo(
                terminalId = terminalInfo.terminalId,
                terminalType = posDeviceName,
                merchantId = terminalInfo.merchantId,
                uniqueId = stan,
                merchantLocation = terminalInfo.merchantNameAndLocation,
                battery = "-1",
                time = DateUtils.universalDateFormat.format(Date()),
                posGeoCode = "",
                currencyCode = terminalInfo.currencyCode)
    }
}


data class KeyRequest(
        @field:Element(name = "ttid", required = false)
        val transactionId: String,
        @field:Element(name = "type", required = false)
        val type: String,
        @field:Element(name = "keysetid", required = false)
        val keySetId: String,
        @field:Element(name = "addinfo", required = false)
        val additionalInfo: KeyAdditionalInfo) {

    companion object {
        fun create(stan: String, keyModulus: String, keyExponent: String) = KeyRequest(
                transactionId = stan,
                type = "trans",
                keySetId = "000002",
                additionalInfo = KeyAdditionalInfo.create(keyModulus, keyExponent))
    }
}

data class KeyAdditionalInfo(
        @field:Element(name = "keyinfo", required = false)
        val keyInfo: KeyInfo) {

    companion object {
        fun create(keyModulus: String, keyExponent: String) = KeyAdditionalInfo(keyInfo = KeyInfo.create(keyModulus, keyExponent))
    }
}

data class KeyInfo(
        @field:Element(name = "pkmod", required = false)
        val publicKeyModulus: String,
        @field:Element(name = "pkex", required = false)
        val publicKeyExponent: String,
        @field:Element(name = "der", required = false)
        val der: String) {

    companion object {
        fun create(publicKeyModulus: String, publicKeyExponent: String) = KeyInfo(
                publicKeyModulus = publicKeyModulus,
                publicKeyExponent = publicKeyExponent,
                der = "1")
    }
}