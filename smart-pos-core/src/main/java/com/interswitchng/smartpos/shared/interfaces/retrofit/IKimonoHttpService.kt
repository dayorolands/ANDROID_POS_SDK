package com.interswitchng.smartpos.shared.interfaces.retrofit

import com.interswitchng.smartpos.shared.Constants
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.Transaction
import com.interswitchng.smartpos.shared.services.kimono.models.*
import com.interswitchng.smartpos.shared.services.kimono.models.CompletionRequest
import com.interswitchng.smartpos.shared.services.kimono.models.KimonoKeyRequest
import com.interswitchng.smartpos.shared.services.kimono.models.PurchaseRequest
import com.interswitchng.smartpos.shared.services.kimono.models.PurchaseResponse
import com.interswitchng.smartpos.shared.services.kimono.models.ReversalRequest
import com.interswitchng.smartpos.shared.Constants.KIMONO_MERCHANT_DETAILS_END_POINT
import com.interswitchng.smartpos.simplecalladapter.Simple
import okhttp3.ResponseBody
import retrofit2.http.*

internal interface IKimonoHttpService {


    @Headers("Content-Type: application/xml")
    @GET(Constants.KIMONO_KEY_END_POINT)
    fun getKimonoKey(@Query("cmd") cmd: String,
                     @Query("terminal_id") terminalId: String,
                     @Query("pkmod") pkmod: String,
                     @Query("pkex") pkex: String,
                     @Query("pkv") pkv: String,
                     @Query("keyset_id") keyset_id: String,
                     @Query("der_en") der_en: String): Simple<ResponseBody>





    @Headers("Content-Type: text/xml", "Accept: application/xml", "Accept-Charset: utf-8")
    @POST
    fun makePurchase(@Url url: String, @Body purchaseRequest: PurchaseRequest): Simple<PurchaseResponse>


    @Headers("Content-Type: text/xml", "Accept: application/xml", "Accept-Charset: utf-8")
    @POST
    fun completion(@Url url: String, @Body completionRequest: CompletionRequest): Simple<PurchaseResponse>


    @Headers("Content-Type: text/xml", "Accept: application/xml", "Accept-Charset: utf-8")
    @POST
    fun preAuthorization(@Url url: String, @Body preAuthRequest: PreAuthRequest): Simple<PurchaseResponse>


    @Headers("Content-Type: text/xml", "Accept: application/xml", "Accept-Charset: utf-8")
    @POST
    fun refund(@Url url: String, @Body refundRequest: RefundRequest): Simple<PurchaseResponse>


    @Headers("Content-Type: text/xml", "Accept: application/xml", "Accept-Charset: utf-8")
    @POST
    fun reversePurchase(@Url url: String, @Body reverseRequest: ReversalRequest): Simple<PurchaseResponse>


    @Headers("Content-Type: text/xml", "Accept: application/xml", "Accept-Charset: utf-8")
    @POST("")
    fun getPinKey(
        @Url endpoint: String,
        @Body request: KimonoKeyRequest): Simple<ResponseBody>

    @Headers("Content-Type: text/xml", "Accept: application/xml", "Accept-Charset: utf-8")
    @POST
    fun doCashOut(@Url url: String, @Body cashOutRequest: CashOutRequest): Simple<PurchaseResponse>

    @Headers("Content-Type: text/xml", "Accept: application/xml", "Accept-Charset: utf-8")
    @POST
    fun billPayment(@Url url: String, @Body billPaymentRequest: BillPaymentRequest): Simple<BillPaymentResponse>

    @Headers("Content-Type: application/xml")
    @GET(KIMONO_MERCHANT_DETAILS_END_POINT)
    fun getMerchantDetails(@Path("terminalSerialNo") terminalSerialNo: String): Simple<AllTerminalInfo>
}