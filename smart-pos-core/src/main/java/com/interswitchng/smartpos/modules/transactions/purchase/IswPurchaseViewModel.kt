package com.interswitchng.smartpos.modules.transactions.purchase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.interswitchng.smartpos.modules.transactions.purchase.IswPurchaseFragment.PurchasePage as PurchasePage


internal class IswPurchaseViewModel : ViewModel() {

    private val _currentPage = MutableLiveData<PurchasePage>()


    val currentPage: LiveData<PurchasePage> get() = _currentPage

    fun setCurrentPage(page: PurchasePage) = _currentPage.postValue(page)

}