package com.interswitchng.smartpos.usb.interfaces

import com.interswitchng.smartpos.shared.models.transaction.PaymentType

interface MessageListener {

    fun onMessageReceived(paymentType: PaymentType, amount: Int)
}