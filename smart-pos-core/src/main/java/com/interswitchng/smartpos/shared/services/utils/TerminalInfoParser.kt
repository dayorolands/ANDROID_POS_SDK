package com.interswitchng.smartpos.shared.services.utils

import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import java.util.ArrayList

internal object TerminalInfoParser {

    internal data class TerminalData(
            internal val tag: String,
            internal val len: Int,
            internal val value: String)


    fun parse(terminalId: String, ip: String, port: Int, rawData: String, store: KeyValueStore): TerminalInfo? {

        val paramatersLists = mutableListOf<MutableList<TerminalData>>()
        var terminalParameters: MutableList<TerminalData> = ArrayList()
        try {

            var tmp = rawData
            while (tmp.isNotEmpty()) {

                val tag = tmp.substring(0, 2)
                tmp = tmp.substring(2)

                val len = Integer.parseInt(tmp.substring(0, 3))
                tmp = tmp.substring(3)
                val value = tmp.substring(0, len)

                tmp = tmp.substring(len)
                val tlv = TerminalData(tag, len, value)
                terminalParameters.add(tlv)

                val tmpLen = tmp.length
                val delim = if (tmpLen > 0) tmp.substring(0, 1) else ""
                if (delim.equals("~", ignoreCase = true) || tmpLen == 0) {
                    paramatersLists.add(terminalParameters)
                    tmp = if (tmpLen > 0) tmp.substring(1) else tmp
                    terminalParameters = ArrayList()
                }
            }

            if (paramatersLists.size > 0) {
                terminalParameters = paramatersLists[0]

                val map = mutableMapOf<String, Any>()

                for (tlv in terminalParameters) {
                    when {
                        "03" == tlv.tag -> map["03"] = tlv.value
                        "04" == tlv.tag -> map["04"] = Integer.parseInt(tlv.value)
                        "05" == tlv.tag -> map["05"] = "0" + tlv.value
                        "06" == tlv.tag -> map["06"] = "0" + tlv.value
                        "07" == tlv.tag -> map["07"] = Integer.parseInt(tlv.value) * 60
                        "08" == tlv.tag -> map["08"] = tlv.value
                        "52" == tlv.tag -> map["52"] = tlv.value
                    }
                }

                var terminalInfo = TerminalInfo(
                        terminalId = terminalId,
                        terminalId2 = "",
                        merchantId2 = "",
                        merchantId = map["03"] as String,
                        currencyCode = map["05"] as String,
                        countryCode = map["06"] as String,
                        currencyCode2 = "",
                        serverTimeoutInSec = map["04"] as Int,
                        callHomeTimeInMin = map["07"] as Int,
                        merchantCategoryCode = map["08"] as String,
                        merchantNameAndLocation = map["52"] as String,
                        serverIp = ip,
                        serverPort = port,
                        capabilities = TerminalInfo.get(store)?.capabilities

                )

                if (terminalInfo.countryCode.length >= 4) {
                    terminalInfo = terminalInfo.copy(countryCode = terminalInfo.countryCode.substring(1, terminalInfo.countryCode.length))
                }
                if (terminalInfo.currencyCode.length >= 4) {
                    terminalInfo = terminalInfo.copy(currencyCode = terminalInfo.currencyCode.substring(1, terminalInfo.currencyCode.length))
                }

                return terminalInfo
            }

            return null

        } catch (ex: Exception) {
            return null
        }
    }
}