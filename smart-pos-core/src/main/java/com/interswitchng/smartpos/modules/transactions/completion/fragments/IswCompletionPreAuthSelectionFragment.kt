package com.interswitchng.smartpos.modules.transactions.completion.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.transactions.completion.viewmodels.IswCompletionPreAuthSelectionViewModel
import com.interswitchng.smartpos.modules.transactions.completion.adapter.PreAuthTransactionAdapter
import com.interswitchng.smartpos.shared.models.transaction.TransactionLog
import com.interswitchng.smartpos.shared.services.utils.DateUtils
import com.interswitchng.smartpos.shared.utilities.DialogUtils
import kotlinx.android.synthetic.main.isw_fragment_completion_pre_auth_selection.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class IswCompletionPreAuthSelectionFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    // initialize date as today
    private var selectedDate = Date()

    private lateinit var adapter: PreAuthTransactionAdapter
    private lateinit var reportLiveData: LiveData<PagedList<TransactionLog>>

    private val preAuthSelectionViewModel: IswCompletionPreAuthSelectionViewModel by viewModel()

    private val parent by lazy { parentFragment as IswCompletionFlowFragment }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.isw_fragment_completion_pre_auth_selection,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // create paging adapter
        adapter =
            PreAuthTransactionAdapter(
                parent::setPreAuthTransaction
            )

        // setup recycler view
        val divider = DividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL
        )
        rvTransactions.adapter = adapter
        rvTransactions.addItemDecoration(divider)

        etTransactionDate.setOnClickListener {
            val dialog = DialogUtils.createDateDialog(requireContext(), this, selectedDate)
            val tomorrow =  System.currentTimeMillis() + (1000*60*60*24) // add 24 hours
            dialog.datePicker.maxDate = tomorrow
            dialog.show()
        }
    }


    private fun showTransactionsFor(day: Date) {
        // set selected date
        selectedDate = day

        // set the date string
        etTransactionDate.setText(DateUtils.shortDateFormat.format(day))

        // show loader and hide recycler view
        initialProgress.visibility = View.VISIBLE
        rvTransactions.visibility = View.GONE


        // clear current report
        if (adapter.itemCount > 0) {
            adapter.currentList?.dataSource?.invalidate()
            adapter.notifyDataSetChanged()
        }

        // lifecycle owner
        val owner = { lifecycle }

        // remove observers from current live data
        if (::reportLiveData.isInitialized)
            reportLiveData.removeObservers(owner)

        // get and observe new report
        reportLiveData = preAuthSelectionViewModel.getPreAuthTransactions(day)
        reportLiveData.observe(owner) { submitList(it, day) }
    }


    private fun submitList(list: PagedList<TransactionLog>?, day: Date) {

        // hide progress bar
        initialProgress.visibility = View.GONE

        // flag to determine if recycler view has no content
        val hasNoContent = list != null && list.isEmpty() && adapter.itemCount == 0

        // show recycler view based on results
        if (hasNoContent) {
            // hide recycler view and show no result
            rvTransactions.visibility = View.GONE
            tvResultHint.visibility = View.VISIBLE
            // format text for no result
            val date = DateUtils.shortDateFormat.format(day)
            tvResultHint.text = getString(R.string.isw_empty_result, date)
        } else {
            // submit paged list to adapter
            adapter.submitList(list)
            // hide no report hint, and show recycler view
            rvTransactions.visibility = View.VISIBLE
            tvResultHint.visibility = View.GONE
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        // extract the selected date
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        // show
        showTransactionsFor(calendar.time)
    }
}