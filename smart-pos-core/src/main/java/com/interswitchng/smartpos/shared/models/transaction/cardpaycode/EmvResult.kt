package com.interswitchng.smartpos.shared.models.transaction.cardpaycode

/**
 * This class represents the status results from the
 * CardReader's communication with the Card Chip
 */
enum class EmvResult(val code: Int) {
    OFFLINE_DENIED(0), // AC_AAC = 0
    OFFLINE_APPROVED(1), // AC_TC = 1
    ONLINE_REQUIRED(2), // AC_ARQC = 2
    CANCELLED(-2)
}