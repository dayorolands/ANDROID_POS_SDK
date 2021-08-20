package com.interswitchng.smartpos.shared.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gojuno.koptional.Optional
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvResult
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.AccountType
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.EmvData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.response.TransactionResponse
import com.interswitchng.smartpos.shared.utilities.toast
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal abstract class BaseCardViewModel: BaseViewModel() {

    protected val _onlineResult = MutableLiveData<OnlineProcessResult>()
    val onlineResult: LiveData<OnlineProcessResult> get() = _onlineResult

    private val _transactionResponse =
        MutableLiveData<Optional<Pair<TransactionResponse, EmvData>>>()
    val transactionResponse: LiveData<Optional<Pair<TransactionResponse, EmvData>>> get() = _transactionResponse


    fun processResult(
        context: Context,
        result: EmvResult,
        paymentInfo: IswPaymentInfo,
        accountType: AccountType,
        terminalInfo: TerminalInfo,
        emvData: EmvData?,
        completeTransaction: (TransactionResponse) -> EmvResult
    ) {
        uiScope.launch {

            when (result) {
                EmvResult.ONLINE_REQUIRED -> {
                    // trigger online transaction process in IO thread
                    val response = withContext(ioScope) {
                        processOnline(
                            paymentInfo,
                            accountType,
                            terminalInfo,
                            emvData,
                            completeTransaction
                        )
                    }

                    // publish transaction response
                    _transactionResponse.value = response
                }
                EmvResult.CANCELLED -> {
                    // transaction has already been cancelled
                    context.toast("Transaction was cancelled")
                }
                else -> {
                    context.toast("Error processing card transaction")
                }
            }
        }
    }


    abstract fun processOnline(
        paymentInfo: IswPaymentInfo,
        accountType: AccountType,
        terminalInfo: TerminalInfo,
        emvData: EmvData?,
        completeTransaction: (TransactionResponse) -> EmvResult
    ): Optional<Pair<TransactionResponse, EmvData>>


    enum class OnlineProcessResult {
        NO_EMV,
        NO_RESPONSE,
        ONLINE_DENIED,
        ONLINE_APPROVED
    }
}