package com.interswitchng.smartpos.shared.models.transaction.ussdqr.response

import com.google.gson.annotations.SerializedName


/**
 * This class captures information for enlisted Banks
 */
data class Bank(
        val id: Int,
        @SerializedName("issuerName") val name: String,
        @SerializedName("issuer") val code: String)
