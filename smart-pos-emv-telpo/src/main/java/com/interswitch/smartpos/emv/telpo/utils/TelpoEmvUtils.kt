package com.interswitch.smartpos.emv.telpo.utils

import com.interswitch.smartpos.emv.telpo.emv.ICCData
import com.interswitch.smartpos.emv.telpo.models.toAPPListItem
import com.interswitchng.smartpos.shared.models.posconfig.EmvAIDs
import com.interswitchng.smartpos.shared.models.posconfig.EmvCard
import com.interswitchng.smartpos.shared.models.posconfig.TerminalConfig
import com.telpo.emv.EmvApp
import com.telpo.tps550.api.util.StringUtil

internal object TelpoEmvUtils {

    fun createAppList(terminalConfig: TerminalConfig, aiDs: EmvAIDs): List<EmvApp> {
        val creator = { card: EmvCard -> card.toAPPListItem(terminalConfig) }
        return aiDs.cards.map(creator).toList()
    }

    @JvmStatic
    fun buildIccString(tagValues: List<Pair<ICCData, ByteArray?>>): String {
        var hex = ""

        for (tagValue in tagValues) {
            tagValue.second?.apply {
                val tag = Integer.toHexString(tagValue.first.tag).toUpperCase()
                var value = StringUtil.toHexString(this)
                var length = Integer.toHexString(size)

                // truncate tag value if it exceeds max length
                if (size > tagValue.first.max) {
                    val expectedLength = 2 * tagValue.first.max // hex is double the length
                    value = value.substring(0 until expectedLength)
                    length = Integer.toHexString(tagValue.first.max)
                }

                // prepend 0 based on value length
                val lengthStr = (if (length.length > 1) length else "0$length").toUpperCase()
                hex = "$hex$tag$lengthStr$value"
            }
        }

        return hex
    }
}