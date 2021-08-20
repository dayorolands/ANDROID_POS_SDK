package com.interswitchng.smartpos.shared.models.email


import com.interswitchng.smartpos.shared.Constants.EMAIL_TEMPLATE_ID


data class EmailMessage (
        val from: Email,
        val personalizations: List<Personalization>,
        val template_id: String = EMAIL_TEMPLATE_ID
)