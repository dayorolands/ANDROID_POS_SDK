package com.interswitchng.smartpos.shared.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.google.gson.Gson
import com.interswitchng.smartpos.shared.interfaces.library.IsoService
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.TransactionLog
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvResult
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.AccountType
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.EmvData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.PurchaseType
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.TransactionInfo
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.response.TransactionResponse
import com.interswitchng.smartpos.shared.models.transaction.payments.billpayment.InquiryResponse
import com.interswitchng.smartpos.shared.models.transaction.payments.billpayment.IswBillPaymentInfo
import com.interswitchng.smartpos.shared.services.kimono.KimonoIsoServiceImpl
import com.interswitchng.smartpos.shared.services.kimono.models.AllTerminalInfo
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class KimonoTransactionCardFlowViewModel(
        private val kimonoIsoService: KimonoIsoServiceImpl,
        private val isoService: IsoService,
        private val store: KeyValueStore
) :
        BaseCardViewModel() {

    private val _inquiryResponse =
            MutableLiveData<TransactionResponse>()
    val inquiryResponse: LiveData<TransactionResponse> get() = _inquiryResponse

    private lateinit var transactionType: TransactionType

    fun setTransactionType(type: TransactionType) {
        transactionType = type
    }

    override fun processOnline(
            paymentInfo: IswPaymentInfo,
            accountType: AccountType,
            terminalInfo: TerminalInfo,
            emvData: EmvData?,
            completeTransaction: (TransactionResponse) -> EmvResult
    ): Optional<Pair<TransactionResponse, EmvData>> {

        // return response based on data
        if (emvData != null) {
            // create transaction info and issue online purchase request
            val txnInfo =
                    TransactionInfo.fromEmv(emvData, paymentInfo, PurchaseType.Card, accountType)

            val response = when (transactionType) {
                TransactionType.IFIS -> initiateCashOutTransaction(terminalInfo, txnInfo)
                TransactionType.Payments -> initiateBillPaymentTransaction(terminalInfo, txnInfo)
                else -> null
            }

            when (response) {
                null -> {
                    _onlineResult.postValue(OnlineProcessResult.NO_RESPONSE)
                    return None
                }
                else -> {
                    // complete transaction by applying scripts
                    // only when responseCode is 'OK'
                    if (response.responseCode == IsoUtils.OK) {
                        // get result code of applying server response
                        val completionResult = completeTransaction(response)

                        // react to result code
                        when (completionResult) {
                            EmvResult.OFFLINE_APPROVED -> _onlineResult.postValue(
                                    OnlineProcessResult.ONLINE_APPROVED
                            )
                            else -> _onlineResult.postValue(OnlineProcessResult.ONLINE_DENIED)
                        }
                    }

                    return Some(Pair(response, emvData))
                }
            }
        } else {
            _onlineResult.postValue(OnlineProcessResult.NO_EMV)
            return None
        }
    }

    fun doInquiry(
            paymentInfo: IswPaymentInfo,
            terminalInfo: TerminalInfo,
            billInfo: IswBillPaymentInfo,
            cardPan: String
    ) {

        uiScope.launch {
            val response =
                    withContext(ioScope) { kimonoIsoService.doInquiry(paymentInfo, terminalInfo, billInfo, cardPan) }

            _inquiryResponse.postValue(response)

        }
    }

    private fun initiateCashOutTransaction(
            terminalInfo: TerminalInfo,
            txnInfo: TransactionInfo
    ): TransactionResponse? {

        val agentDetails = getAgentDetails(store)
        if (agentDetails !=  null){
            return kimonoIsoService.initiateCashOut(terminalInfo, txnInfo, agentDetails)
        }
        return null
    }

    private fun initiateBillPaymentTransaction(
            terminalInfo: TerminalInfo,
            txnInfo: TransactionInfo
    ): TransactionResponse? {
        val data = _inquiryResponse.value

        data?.apply {
            if(inquiryResponse != null){
                val response = isoService.initiateBillPayment(terminalInfo, txnInfo, this.inquiryResponse)
                if (response?.responseCode == IsoUtils.OK && !terminalInfo.isKimono){
                    sendAdvice(terminalInfo, txnInfo, inquiryResponse)
                }

                //use inquiry trans ref for NIBSS txns
                if(!terminalInfo.isKimono)
                    response?.transactionId = data.transactionId

                return response
            }
        }
        return null
    }

    private fun sendAdvice(
            terminalInfo: TerminalInfo,
            txnInfo: TransactionInfo,
            inquiryResponse: InquiryResponse
    ){
        kimonoIsoService.initiatePaymentAdvice(terminalInfo, txnInfo, inquiryResponse)
    }

    companion object {
        private const val KEY_MERCHANT_DETAILS = "key_download_merchant_details"

        internal fun getAgentDetails(store: KeyValueStore): AllTerminalInfo? {
            val jsonString = store.getString(KEY_MERCHANT_DETAILS, "")
            return when (jsonString) {
                "" -> null
                else -> Gson().fromJson(jsonString, AllTerminalInfo::class.java)
            }
        }

    }
}