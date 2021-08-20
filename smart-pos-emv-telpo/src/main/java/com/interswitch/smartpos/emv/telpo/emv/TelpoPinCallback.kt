package com.interswitch.smartpos.emv.telpo.emv

import com.telpo.emv.EmvPinData

interface TelpoPinCallback {

    suspend fun showInsertCard()

    val pinResult: Int

    suspend fun enterPin(isOnline: Boolean, isRetry: Boolean, offlineTriesLeft: Int, panBlock: String, emvPinData: EmvPinData?): Int

    suspend fun showPinOk()
}