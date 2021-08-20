package com.interswitchng.smartpos.shared.models.transaction.ussdqr.response


/**
 * This class captures the possible responses that
 * indicate the result of a pending payment status
 */
internal sealed class PaymentStatus {

    class Complete(val transaction: Transaction): PaymentStatus()
    class Pending(val transaction: Transaction): PaymentStatus()
    class Error(val transaction: Transaction?, val errorMsg: String?): PaymentStatus()
    object OngoingTimeout: PaymentStatus()
    object Timeout: PaymentStatus()
}