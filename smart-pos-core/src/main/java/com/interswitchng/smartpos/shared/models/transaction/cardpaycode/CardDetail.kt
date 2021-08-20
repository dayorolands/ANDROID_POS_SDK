package com.interswitchng.smartpos.shared.models.transaction.cardpaycode


/**
 * This class captures the information for a given EMV card
 */
data class CardDetail(val pan: String, val expiry: String, val type: CardType)