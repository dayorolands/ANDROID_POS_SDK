package com.interswitchng.smartpos.shared.interfaces.device

import android.content.Context
import com.interswitchng.smartpos.shared.models.fingerprint.Fingerprint
import kotlinx.coroutines.channels.Channel

interface POSFingerprint {

    suspend fun setup(
        context: Context,
        phoneNumber: String,
        channel: Channel<Fingerprint>
    )

    suspend fun createFinger()

    fun removeFinger()

    suspend fun authorizeFingerprint()

    fun hasFingerprint(context: Context, phoneNumber: String): Boolean

    fun close()
}