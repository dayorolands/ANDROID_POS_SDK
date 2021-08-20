package com.interswitchng.smartpos.shared.models.printer.info


/**
 * This class captures the response status of a
 * purchase transaction and represents it for printout
 */
data class TransactionStatus (
        val responseMessage: String,
        val responseCode: String,
        val AID: String,
        val telephone: String
)