package com.interswitchng.smartpos.modules.menu.report

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.activities.BaseMenuActivity
import com.interswitchng.smartpos.shared.adapters.TransactionLogAdapter
import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.transaction.TransactionLog
import com.interswitchng.smartpos.shared.services.utils.DateUtils
import com.interswitchng.smartpos.shared.utilities.DialogUtils
import com.interswitchng.smartpos.shared.utilities.hide
import com.interswitchng.smartpos.shared.utilities.show
import com.interswitchng.smartpos.shared.utilities.toast
import kotlinx.android.synthetic.main.isw_activity_report.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class ReportActivity : BaseMenuActivity(), DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    private val reportViewModel: ReportViewModel by viewModel()

    private lateinit var adapter: TransactionLogAdapter

    private lateinit var reportLiveData: LiveData<PagedList<TransactionLog>>

    private lateinit var transactionOptions: Array<String>

    private var eodSummaryIsLoaded = false;

    // initialize date as today
    private var selectedStartDate = Date()
    private var selectedEndDate = Date()

    private var selectType = SelectType.START_DATE
    private var transactionType: TransactionType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.isw_activity_report)

        // setup toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //get transaction options array
        transactionOptions = resources.getStringArray(R.array.isw_transaction_types)

        // create paging adapter
        adapter = TransactionLogAdapter()

        // setup recycler view
        rvTransactions.adapter = adapter
        rvTransactions.layoutManager = LinearLayoutManager(this)
        rvTransactions.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


        setButtonClickListeners()

        // select today's reports
        showReportFor(selectedStartDate, selectedEndDate)

        showSpinner()

        //create and observe for eod
        observe()
    }

    private fun observe() {
        // observe view model
        with(reportViewModel) {

            printButton.observe(this@ReportActivity, Observer {
                it ?: return@Observer
                // toggle button's clickable state
                isw_button_eod.isEnabled = it
                isw_button_eod.isClickable = it
                if(it) isw_button_eod.text = getString(R.string.isw_print_eod_label) else  isw_button_eod.text = getString(R.string.isw_title_processing)
            })

            printerMessage.observe(this@ReportActivity, Observer {
                it ?: return@Observer
                toast(it)
            })

            endOfDaySummary.observe(this@ReportActivity, Observer {

                tvTotalVolume.text = it.totalVolume.toString()
                tvTotalValue.text = it.totalValue
                tvTotalSuccessVolume.text = it.successVolume.toString()
                tvTotalSuccessValue.text = it.successValue
                tvTotalFailedVolume.text = it.failedVolume.toString()
                tvTotalFailedValue.text = it.failedValue

                if (it.totalVolume > 0) {
                    eodSummaryIsLoaded = true
                    eodSummaryDetails.show()
                    eodSummaryProgress.hide()
                    collapseIcon.setImageResource(R.drawable.isw_ic_chevron_up)
                }
            })
        }
    }

    private fun showReportFor(from: Date, to: Date, transactionType: TransactionType? = null) {
        // set selected date
        tvDate.text = DateUtils.shortDateFormat.format(selectedStartDate)
        tvEndDate.text = DateUtils.shortDateFormat.format(selectedEndDate)

        // show loader and hide recycler view
        initialProgress.show()
        rvTransactions.hide()

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
        reportLiveData = reportViewModel.getReport(from, to, transactionType)

        reportLiveData.observe(owner) { submitList(it, from, to) }

        processEodSummary(transactionType)
    }

    private fun submitList(list: PagedList<TransactionLog>?, from: Date, to: Date) {

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
            val startDate = DateUtils.shortDateFormat.format(from)
            val endDate = DateUtils.shortDateFormat.format(to)
            tvResultHint.text = getString(R.string.isw_no_report, startDate, endDate)
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

        when (selectType) {
            SelectType.START_DATE -> selectedStartDate = calendar.time
            SelectType.END_DATE -> selectedEndDate = calendar.time
        }

        // show report for selected date
        showReportFor(selectedStartDate, selectedEndDate)
    }

    private fun showSpinner() {

        val optionsAdapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, transactionOptions) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view: View = super.getDropDownView(position, convertView, parent)
                val tv = view as TextView
                if (position == 0) tv.setTextColor(Color.GRAY)
                return view
            }
        }
        optionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(spinnerTransactionTypes) {
            adapter = optionsAdapter
            setSelection(0, false)
            onItemSelectedListener = this@ReportActivity
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        toast("Nothing Selected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (transactionOptions[position] == "All") {
            showReportFor(selectedStartDate, selectedEndDate)
        } else {
            val filteredTransactionOptions = transactionOptions.drop(2)
            transactionType = TransactionType.valueOf(filteredTransactionOptions[position - 2])
            showReportFor(selectedStartDate, selectedEndDate, transactionType)
        }

    }

    private fun setButtonClickListeners() {
        // set click listener date selector
        btnSelectStartDate.setOnClickListener {
            val dialog = DialogUtils.createDateDialog(this, this, selectedStartDate)
            dialog.datePicker.maxDate = System.currentTimeMillis()
            dialog.show()
            selectType = SelectType.START_DATE
        }

        btnSelectEndDate.setOnClickListener {
            val dialog = DialogUtils.createDateDialog(this, this, selectedEndDate)
            dialog.datePicker.maxDate = System.currentTimeMillis()
            dialog.show()
            selectType = SelectType.END_DATE
        }

        isw_button_eod.setOnClickListener {
            val transactions = reportViewModel.getEndOfDay(selectedStartDate, selectedEndDate, transactionType)

            // observer to print transactions
            lateinit var printObserver: Observer<List<TransactionLog>>

            printObserver = Observer {
                it ?: return@Observer

                if (it.isEmpty()) {
                    toast("Nothing to print")
                    reportViewModel.togglePrintButton(true)
                    return@Observer
                }

                // print transaction based on transactionType else print all
                when (val transactionType = transactionType) {
                    null -> reportViewModel.printAll(selectedStartDate, selectedEndDate)
                    else -> reportViewModel.printEndOfDay(selectedStartDate, selectedEndDate, it, transactionType)
                }
                //reportViewModel.togglePrintButton(true)
                // remove observer once print has been triggered
                transactions.removeObserver(printObserver)
            }

            transactions.observe(this, printObserver);

        }

        eodSummaryContainer.setOnClickListener {

            if (eodSummaryIsLoaded) {
                if (eodSummaryDetails.visibility == View.VISIBLE) {
                    eodSummaryDetails.hide()
                    collapseIcon.setImageResource(R.drawable.isw_ic_chevron_down)
                } else {
                    eodSummaryDetails.show()
                    collapseIcon.setImageResource(R.drawable.isw_ic_chevron_up)
                }
            } else {
                if (eodSummaryProgress.visibility == View.GONE) {
                    eodSummaryProgress.show()
                    collapseIcon.setImageResource(R.drawable.isw_ic_chevron_up)
                } else {
                    eodSummaryProgress.hide()
                    collapseIcon.setImageResource(R.drawable.isw_ic_chevron_down)
                }
            }
        }
    }

    private fun processEodSummary(transactionType: TransactionType?) {
        val transactions = reportViewModel.getEndOfDay(selectedStartDate, selectedEndDate, transactionType)

        lateinit var eodSummaryObserver: Observer<List<TransactionLog>>

        eodSummaryObserver = Observer {

            reportViewModel.getSummary(it)

            // remove observer once eod has been triggered
            transactions.removeObserver(eodSummaryObserver)
        }
        transactions.observe(this, eodSummaryObserver);
    }

    companion object {
        private enum class SelectType {
            START_DATE, END_DATE
        }
    }
}
