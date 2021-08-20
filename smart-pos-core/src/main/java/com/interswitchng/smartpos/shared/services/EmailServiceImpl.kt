package com.interswitchng.smartpos.shared.services

import com.interswitchng.smartpos.shared.interfaces.library.EmailService
import com.interswitchng.smartpos.shared.interfaces.retrofit.IEmailService
import com.interswitchng.smartpos.shared.models.email.EmailMessage

class EmailServiceImpl(private val emailHttpService: IEmailService): EmailService {

    override suspend fun send(email: EmailMessage): Boolean {
        val request = emailHttpService.sendMail(email)

        // get response
        val response = request.execute()
        return response.isSuccessful
    }

}