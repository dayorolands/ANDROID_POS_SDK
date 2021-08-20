package com.interswitchng.smartpos.shared.errors

class DeviceError(val errorMessage: String, val errorCode: Int) {

    companion object {
        const val ERR_INVALID_PIN = 1
        const val ERR_CARD_ERROR = 2
    }
}