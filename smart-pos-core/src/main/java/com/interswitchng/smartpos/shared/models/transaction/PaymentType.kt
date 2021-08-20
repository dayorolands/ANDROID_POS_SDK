package com.interswitchng.smartpos.shared.models.transaction


/**
 * This enum class represents the different
 * payment methods the SDK supports
 */
enum class PaymentType {
    PayCode,
    Card,
    QR,
    USSD,
    Cash;

    override fun toString(): String {
        val string = when (QR) {
            this -> "QR Code"
            else -> super.toString()
        }

        return string.toUpperCase()
    }
}