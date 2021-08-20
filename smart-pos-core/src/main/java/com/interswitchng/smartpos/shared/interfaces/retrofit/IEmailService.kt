package com.interswitchng.smartpos.shared.interfaces.retrofit

import com.interswitchng.smartpos.shared.Constants.EMAIL_END_POINT
import com.interswitchng.smartpos.shared.models.email.EmailMessage
import retrofit2.Call

import retrofit2.http.Body
import retrofit2.http.POST

interface IEmailService {

    @POST(EMAIL_END_POINT)
    fun sendMail(@Body data: EmailMessage): Call<Void>
}