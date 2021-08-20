package com.interswitchng.smartpos.shared.interfaces.library

import com.interswitchng.smartpos.shared.models.email.EmailMessage


/**
 * This interface is provides functionality
 * for the sdk to send Emails to customers
 */
interface EmailService {

    suspend fun send(email: EmailMessage): Boolean
}