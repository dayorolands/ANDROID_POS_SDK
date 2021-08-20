package com.interswitchng.smartpos.shared.models.transaction.cardpaycode

/**
 * This enum class represents the different cards
 * supported for EMV Card Chip transactions
 *
 * Note: The [code] parameter must match the AID's name tag in the xml config
 */
enum class CardType( val code: String) {
    MASTER("Master"),
    VISA("Visa"),
    VERVE("Verve"),
    AMERICANEXPRESS("AMEX"),
    CHINAUNIONPAY("CUP"),
    None("None"),
}