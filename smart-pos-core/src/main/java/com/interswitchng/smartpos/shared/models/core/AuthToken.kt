package com.interswitchng.smartpos.shared.models.core

import com.google.gson.annotations.SerializedName

/**
 * A data model representing the user's access token retrieved from the server
 */
internal data class AuthToken(@SerializedName("access_token") val token: String)