package com.interswitchng.smartpos.emv.pax.emv

interface PinCallback {

    suspend fun showInsertCard()

    fun getPinResult(panBlock: String): Int

    suspend fun enterPin(isOnline: Boolean, triesCount: Int, offlineTriesLeft: Int, panBlock: String)

    suspend fun showPinOk()
}