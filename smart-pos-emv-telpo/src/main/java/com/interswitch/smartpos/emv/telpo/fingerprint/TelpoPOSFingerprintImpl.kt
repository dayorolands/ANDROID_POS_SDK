package com.interswitch.smartpos.emv.telpo.fingerprint

import android.content.Context
import android.graphics.Bitmap
import com.interswitch.smartpos.emv.telpo.utils.RawToBitMap
import com.interswitchng.smartpos.shared.interfaces.device.POSFingerprint
import com.interswitchng.smartpos.shared.models.fingerprint.Fingerprint
import com.interswitchng.smartpos.shared.utilities.FileUtils
import com.telpo.usb.finger.Finger
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext

class TelpoPOSFingerprintImpl : POSFingerprint {

    private lateinit var channel: Channel<Fingerprint>
    private lateinit var context: Context

    private lateinit var fingerPrintData: ByteArray
    private lateinit var fingerPrintISO: ByteArray

    private lateinit var phoneNumber: String

    private lateinit var bitmap: Bitmap

    var ret = 0

    private var isCancelled = false

    init {
        val byte = ByteArray (250 * 360)
//        Finger.initialize(byte)
    }

    override suspend fun setup(
        context: Context,
        phoneNumber: String,
        channel: Channel<Fingerprint>
    ) {
        this.context = context
        this.channel = channel
        fingerPrintData = ByteArray(250 * 360)
        fingerPrintISO = ByteArray(890)
        this.phoneNumber = phoneNumber

        channel.send(Fingerprint.SetupComplete)
    }

    override suspend fun createFinger() {
        while (coroutineContext.isActive && !isCancelled) {
            val result = checkFingerprint()
            if (result) break
        }
        channel.send(Fingerprint.Detected(bitmap))

        //FileUtils.saveMerchantFingerPrint(channel, phoneNumber, fingerPrintISO)

        //This delay is for the UI to change before the dialog dismisses
        delay(1000)

        channel.send(Fingerprint.Success)
    }

    private fun checkFingerprint(): Boolean {
        fingerPrintData = ByteArray(250 * 360)
        fingerPrintISO = ByteArray(890)

        val length = IntArray(1)
        val quality = ByteArray(1)

        ret =  Finger.get_image(fingerPrintData)
        if (ret != 0) return false
        bitmap = RawToBitMap.convert8bit(fingerPrintData, 208, 288) ?: return false
        ret = Finger.get_ISO2005(fingerPrintISO, length, quality)

        return ret == 0
    }

    override suspend fun authorizeFingerprint() {
//        val savedFingerPrintISO = FileUtils.getFingerPrintFile(phoneNumber)
//        if (savedFingerPrintISO.isEmpty()) {
//            channel.send(Fingerprint.Failed("No fingerprint enrolled"))
//            return
//        }

//        while (coroutineContext.isActive && !isCancelled) {
//            val result = checkFingerprint()
//            if (result) break
//        }
//
//        val result = IntArray(1)
//        val isVerified = Finger.verify_ISO_tmpl(savedFingerPrintISO, savedFingerPrintISO, result) == 0
//        if (isVerified) channel.send(Fingerprint.Authorized)
//        else channel.send(Fingerprint.Failed("Authorization failed!!"))
    }

    override fun removeFinger() {}

    override fun hasFingerprint(context: Context, phoneNumber: String): Boolean {
        return false // FileUtils.getFingerPrintFile(phoneNumber).isNotEmpty()
    }

    override fun close() {
        isCancelled = true
        Finger.terminate()
    }
}