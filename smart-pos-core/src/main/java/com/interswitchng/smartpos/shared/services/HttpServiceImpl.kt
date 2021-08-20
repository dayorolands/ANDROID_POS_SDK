package com.interswitchng.smartpos.shared.services

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.interswitchng.smartpos.shared.interfaces.retrofit.IHttpService
import com.interswitchng.smartpos.shared.interfaces.library.HttpService
import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.request.CodeRequest
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.request.TransactionStatus
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.Bank
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.CodeResponse
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.PaymentStatus
import com.interswitchng.smartpos.shared.utilities.Logger
import com.interswitchng.smartpos.simplecalladapter.Simple
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class HttpServiceImpl(private val httpService: IHttpService) : HttpService {

    val logger by lazy { Logger.with(this.javaClass.name) }

    override suspend fun getBanks(): Optional<List<Bank>> {
        val banks = httpService.getBanks().await()
        val banksResponse = banks.first
        return when (banksResponse) {
            null -> None
            else -> Some(banksResponse)
        }
    }

    override suspend fun initiateQrPayment(request: CodeRequest): Optional<CodeResponse> {
        val code = httpService.getQrCode(request).await()
        val codeResponse = code.first
        return when (codeResponse) {
            null -> None
            else -> Some(codeResponse)
        }
    }

    override suspend fun initiateUssdPayment(request: CodeRequest): Optional<CodeResponse> {
        val response = httpService.getUssdCode(request).await()
        val codeResponse = response.first
        return when (codeResponse) {
            null -> None
            else -> Some(codeResponse)
        }
    }

    override suspend fun checkPayment(type: PaymentType, status: TransactionStatus): PaymentStatus {

        // check status based on the transaction type
        val transactionResponse = when (type) {
            PaymentType.USSD -> httpService.getUssdTransactionStatus(status.merchantCode, status.reference).await()
            else -> httpService.getQrTransactionStatus(status.merchantCode, status.reference).await()
        }

        val transaction = transactionResponse.first
        return when {
            transaction == null || transaction.isError() -> PaymentStatus.Error(transaction, transactionResponse.second)
            transaction.isCompleted() -> PaymentStatus.Complete(transaction)
            transaction.isPending() -> PaymentStatus.Pending(transaction)
            else -> {
                // some other error occurred
                // terminate loop and return
                PaymentStatus.Error(transaction, transactionResponse.second)
            }
        }
    }


    private suspend fun <T> Simple<T>.await(): Pair<T?, String?> {
        return suspendCoroutine { continuation ->
            process { response, t ->
                val message =  t?.message ?: t?.localizedMessage

                // log errors
                if (message != null) logger.log(message)
                // pair result and error
                val result = Pair(response, message)
                // return response
                continuation.resume(result)
            }
        }
    }
}