package com.interswitchng.smartpos.shared.models.fingerprint

import android.graphics.Bitmap

sealed class Fingerprint {
    data class Detected(val bitmap: Bitmap): Fingerprint()
    object SetupComplete: Fingerprint()
    object Success : Fingerprint()
    object Authorized : Fingerprint()
    object Timeout: Fingerprint()
    object WriteSuccessful: Fingerprint()
    data class Failed(val message: String): Fingerprint()
}