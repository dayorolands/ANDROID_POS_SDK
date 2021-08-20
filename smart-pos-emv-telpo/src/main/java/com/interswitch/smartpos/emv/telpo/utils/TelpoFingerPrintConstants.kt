package com.interswitch.smartpos.emv.telpo.utils

internal object TelpoFingerPrintConstants {

    const val SUCCESS : Int = 0x00
    const val FAILED : Int = 0x01
    const val VERIFICATION_ERROR : Int = 0x11
    const val IDENTIFICATION_ERROR : Int = 0x12
    const val TEMPLATE_EMPTY : Int = 0x13
    const val TEMPLATE_NOT_EMPTY : Int = 0x14
    const val ID_DOESNT_EXIST : Int = 0x17
}