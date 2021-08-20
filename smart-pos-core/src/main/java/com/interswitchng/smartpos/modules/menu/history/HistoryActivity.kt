package com.interswitchng.smartpos.modules.menu.history

import android.os.Bundle
import android.view.View
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.activities.BaseMenuActivity
import com.interswitchng.smartpos.shared.adapters.TransactionLogAdapter
import com.interswitchng.smartpos.shared.models.transaction.TransactionLog
import kotlinx.android.synthetic.main.isw_activity_history.*
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryActivity : BaseMenuActivity() {

    private val historyViewModel: HistoryViewModel by viewModel()
    private lateinit var adapter: TransactionLogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.isw_activity_history)


        // setup toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // create paging adapter
        adapter = TransactionLogAdapter()


        // observe view model
        with(historyViewModel) {

            val owner = { lifecycle }

            // observe list updates
            pagedList.observe(owner, ::submitList)

        }

        // setup recycler view
        rvTransactions.adapter = adapter
        rvTransactions.layoutManager = LinearLayoutManager(this)
        rvTransactions.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

    }

    private fun submitList(list: PagedList<TransactionLog>?){
        // hide progress bar if visible
        if (initialProgress.visibility == View.VISIBLE)
            initialProgress.visibility = View.GONE

        if (list != null && list.isEmpty() && adapter.itemCount == 0) {
            rvTransactions.visibility = View.GONE
            tvNoHistory.visibility = View.VISIBLE
        } else {
            adapter.submitList(list)
            rvTransactions.visibility = View.VISIBLE
            tvNoHistory.visibility = View.GONE
        }
    }
}
