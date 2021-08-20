package com.interswitchng.smartpos.modules.transactions.purchase.modules.ussdqr.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gojuno.koptional.Optional
import com.interswitchng.smartpos.shared.interfaces.library.HttpService
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.request.CodeRequest
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.Bank
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.CodeResponse
import kotlinx.coroutines.*

internal class UssdViewModel(paymentService: HttpService) : BaseCodeViewModel(paymentService) {

    var hasBanks = false
    private val _allBanks = MutableLiveData<Optional<List<Bank>>>()
    val allBanks: LiveData<Optional<List<Bank>>> get() = _allBanks

    private val _bankCode = MutableLiveData<Optional<CodeResponse>>()
    val bankCode: LiveData<Optional<CodeResponse>>  get() = _bankCode


    fun loadBanks() {
        uiScope.launch {
            // fetch banks on IO thread
            val banks = uiScope.async(Dispatchers.IO) { paymentService.getBanks() }
            // assign banks on main thread
            _allBanks.value = banks.await()
        }
    }

    fun getBankCode(codeRequest: CodeRequest) {
        uiScope.launch {
            // retrieve ussd code on IO thread
            val response = uiScope.async(Dispatchers.IO) { paymentService.initiateUssdPayment(codeRequest) }
            _bankCode.value = response.await()
        }
    }
}