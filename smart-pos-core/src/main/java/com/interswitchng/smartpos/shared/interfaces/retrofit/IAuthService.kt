package com.interswitchng.smartpos.shared.interfaces.retrofit

import com.interswitchng.smartpos.shared.Constants.AUTH_END_POINT
import com.interswitchng.smartpos.shared.models.core.AuthToken
import com.interswitchng.smartpos.simplecalladapter.Simple
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface IAuthService {

    @FormUrlEncoded
    @POST(AUTH_END_POINT)
    fun getToken(
            @Field("grant_type") grantType: String,
            @Field("scope") scope: String): Simple<AuthToken?>
}