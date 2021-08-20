package com.interswitchng.smartpos.modules.menu.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.interswitchng.smartpos.shared.interfaces.library.TransactionLogService
import com.interswitchng.smartpos.shared.models.transaction.TransactionLog

class HistoryViewModel(transactionLogService: TransactionLogService): ViewModel() {

    // history paged list
    val pagedList: LiveData<PagedList<TransactionLog>>


    init {
        // setup thread executor and paged list config
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .build()

        // setup paged list liveData
        pagedList = transactionLogService.getTransactions(config)
    }
}