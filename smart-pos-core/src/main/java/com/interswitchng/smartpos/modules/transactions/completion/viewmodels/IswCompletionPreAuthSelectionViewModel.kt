package com.interswitchng.smartpos.modules.transactions.completion.viewmodels

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.interswitchng.smartpos.shared.interfaces.library.TransactionLogService
import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.transaction.TransactionLog
import com.interswitchng.smartpos.shared.viewmodel.BaseViewModel
import java.util.*

internal class IswCompletionPreAuthSelectionViewModel(
    private val transactionLogService: TransactionLogService
) : BaseViewModel() {


    // setup paged list config
    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(10)
        .setPageSize(10)
        .build()


    fun getPreAuthTransactions(day: Date): LiveData<PagedList<TransactionLog>> {
        val transactionType = TransactionType.PreAuth
        return transactionLogService.getTransactionFor(day, transactionType, config)
    }
}