package com.interswitchng.smartpos.emv.pax.old.models



data class TransactionInfo(
        val stan: String,
        val dateTime: String,
        val amount: String,
        val type: TransactionType,
        val cardPan: String,
        val cardType: String,
        val cardExpiry: String,
        val authorizationCode: String,
        val pinStatus: String
)