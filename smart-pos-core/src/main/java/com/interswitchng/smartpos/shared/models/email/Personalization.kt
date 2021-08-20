package com.interswitchng.smartpos.shared.models.email

import com.google.gson.annotations.SerializedName


data class Personalization(
        val to: List<Email>,
        @SerializedName("dynamic_template_data")
        val templateData: CustomArguments
)