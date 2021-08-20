package com.interswitchng.smartpos.shared.services

import com.interswitchng.smartpos.shared.interfaces.library.UserStore
import com.interswitchng.smartpos.shared.interfaces.retrofit.IAuthService
import com.interswitchng.smartpos.shared.models.core.AuthToken
import com.interswitchng.smartpos.shared.utilities.Logger
import retrofit2.HttpException
import java.io.IOException

internal class UserStoreImpl(private val authService: IAuthService): UserStore {

    private val logger by lazy { Logger.with("UserStoreImpl")}
    private lateinit var token: AuthToken

    override  fun <T> getToken (callback: (String) -> T): T {
        // define grant type and scope for auth request
        val grantType = "client_credentials"
        val scope = "profile"

        // process callback based on availability of access token
        if (::token.isInitialized) return callback(token.token)
        else authService.getToken(grantType, scope).run().let {
            val authToken =
                    if (it.isSuccessful) it.body()!!.also { token = it }
                    else AuthToken("") // empty token

            return callback(authToken.token)
        }
    }
}