package com.interswitchng.smartpos.shared.models.email


data class CustomArguments(
        val merchantName: String,
        val terminalId: String,
        val paymentType: String,
        val stan: String,
        val date: String,
        val amount: String,
        val aid: String,
        val responseCode: String,
        val responseMessage: String,
        val telephone: String,
        val senderName: String = "Interswitch",
        val senderCity: String = "Victoria Island",
        val senderAddress: String = "Address: 1648C Oko Awo St, Victoria Island, Lagos"
)
