package com.interswitchng.smartpos.shared.interfaces.retrofit

import com.interswitchng.smartpos.shared.Constants.BANKS_END_POINT
import com.interswitchng.smartpos.shared.Constants.CODE_END_POINT
import com.interswitchng.smartpos.shared.Constants.TRANSACTION_STATUS_QR
import com.interswitchng.smartpos.shared.Constants.TRANSACTION_STATUS_USSD
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.request.CodeRequest
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.Bank
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.CodeResponse
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.Transaction
import com.interswitchng.smartpos.simplecalladapter.Simple
import retrofit2.http.*

internal interface IHttpService {

    @POST(CODE_END_POINT)
    fun getQrCode(@Body request: CodeRequest): Simple<CodeResponse>

    @POST(CODE_END_POINT)
    fun getUssdCode(@Body request: CodeRequest): Simple<CodeResponse>

    @GET(TRANSACTION_STATUS_QR)
    fun getQrTransactionStatus(@Query("merchantCode") merchantCode: String,
                             @Query("transactionReference") transactionReference: String): Simple<Transaction?>


    @GET(TRANSACTION_STATUS_USSD)
    fun getUssdTransactionStatus(@Query("merchantCode") merchantCode: String,
                             @Query("transactionReference") transactionReference: String): Simple<Transaction?>

    @GET(BANKS_END_POINT)
    fun getBanks(): Simple<List<Bank>?>
}