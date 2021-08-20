package com.interswitchng.smartpos.shared.interfaces.library

import com.gojuno.koptional.Optional
import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.request.CodeRequest
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.request.TransactionStatus
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.Bank
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.CodeResponse
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.PaymentStatus


internal interface HttpService {

    suspend fun initiateQrPayment(request: CodeRequest): Optional<CodeResponse>

    suspend fun initiateUssdPayment(request: CodeRequest): Optional<CodeResponse>

    suspend fun getBanks(): Optional<List<Bank>>

    suspend fun checkPayment(type: PaymentType, status: TransactionStatus): PaymentStatus
}