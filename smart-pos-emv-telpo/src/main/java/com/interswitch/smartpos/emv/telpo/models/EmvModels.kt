package com.interswitch.smartpos.emv.telpo.models

import com.interswitchng.smartpos.shared.models.posconfig.EmvAIDs
import com.interswitchng.smartpos.shared.models.posconfig.EmvCapk
import com.interswitchng.smartpos.shared.models.posconfig.EmvCard
import com.interswitchng.smartpos.shared.models.posconfig.TerminalConfig
import com.telpo.emv.EmvApp
import com.telpo.emv.EmvCAPK
import com.telpo.tps550.api.util.StringUtil

fun EmvAIDs.getAllCapks(): List<EmvCAPK> {
    return cards.flatMap { card ->
        // use aid to get rid for keys
        val rid = card.aid.substring(0..9)
        return@flatMap card.keys.map { it.toEmvCAPK(rid) }
    }
}

fun EmvCapk.toEmvCAPK(rid: String): EmvCAPK {
    // get the key  index
    val keyId = Integer.parseInt(id, 16)

    return EmvCAPK().also {
        it.apply {
            RID = StringUtil.toBytes(rid)
            KeyID = keyId.toByte()
            HashInd = 0x01
            ArithInd = 0x01
            Modul = StringUtil.toBytes(modulus)
            Exponent = StringUtil.toBytes(exponent)
            ExpDate = StringUtil.toBytes(expiry)
            CheckSum = StringUtil.toBytes(checksum)
        }
    }
}

fun EmvCard.toAPPListItem(config: TerminalConfig): EmvApp {
    val acquirerId = "000000123456"
    val partialMatch = if (partialMatch) PART_MATCH else FULL_MATCH


    return EmvApp().also {
        it.apply {
            AppName = name.toByteArray()
            AID = StringUtil.toBytes(aid)
            SelFlag = partialMatch.toByte()
            FloorLimit = byteArrayOf(config.floorlimit.toByte())
            FloorLimitCheck = if (config.floorlimitcheck) 1 else 0
            TACDenial = StringUtil.toBytes(config.tacdenial)
            TACOnline = StringUtil.toBytes(config.taconline)
            TACDefault = StringUtil.toBytes(config.tacdefault)
            AcquierId = StringUtil.toBytes(acquirerId)
            DDOL = StringUtil.toBytes(config.ddol)
            Version = StringUtil.toBytes(config.version)
            TDOL = StringUtil.toBytes(config.tdol)

            // -----------------------------
            // unknown attributes config
            // -----------------------------
            Priority = 0
            Threshold = byteArrayOf(1.toByte())
            TargetPer = 0
            MaxTargetPer = 0
            RandTransSel = 1
            VelocityCheck = 1
        }
    }
}

private val PART_MATCH = 0
private val FULL_MATCH = 1