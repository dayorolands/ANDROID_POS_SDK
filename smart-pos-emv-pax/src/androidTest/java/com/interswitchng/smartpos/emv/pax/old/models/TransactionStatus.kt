package com.interswitchng.smartpos.emv.pax.old.models

data class TransactionStatus(
        val responseMessage: String,
        val responseCode: String,
        val AID: String,
        val telephone: String
)