package com.interswitchng.smartpos.modules.menu.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.interswitchng.smartpos.shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.interfaces.library.TransactionLogService
import com.interswitchng.smartpos.shared.models.core.*
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.posconfig.PrintStringConfiguration
import com.interswitchng.smartpos.shared.models.printer.info.PrintStatus
import com.interswitchng.smartpos.shared.models.transaction.EodSummary
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.TransactionLog
import com.interswitchng.smartpos.shared.models.transaction.currencyType
import com.interswitchng.smartpos.shared.services.utils.DateUtils
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import com.interswitchng.smartpos.shared.utilities.DisplayUtils
import com.interswitchng.smartpos.shared.viewmodel.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

internal class ReportViewModel(
        private val posDevice: POSDevice,
        private val transactionLogService: TransactionLogService,
        private val store: KeyValueStore

) : BaseViewModel() {

    private val terminalInfo: TerminalInfo by lazy { TerminalInfo.get(store)!! }

    private val _printButton = MutableLiveData<Boolean>()
    val printButton: LiveData<Boolean> get() = _printButton

    private val _printerMessage = MutableLiveData<String>()
    val printerMessage: LiveData<String> get() = _printerMessage

    private val _endOfDayTransactions = MutableLiveData<List<TransactionLog>>()
    val endOfDayTransactions: LiveData<List<TransactionLog>> = _endOfDayTransactions

    private val _endOfDaySummary = MutableLiveData<EodSummary>()
    val endOfDaySummary: LiveData<EodSummary> = _endOfDaySummary


    // setup paged list config
    private val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .build()

    private fun getReport(day: Date): LiveData<PagedList<TransactionLog>> {
        return transactionLogService.getTransactionFor(day, config)
    }

    private fun getReport(from: Date, to: Date): LiveData<PagedList<TransactionLog>> {
        return transactionLogService.getTransactionFor(from, to, config)
    }

    fun getReport(day: Date, transactionType: TransactionType?): LiveData<PagedList<TransactionLog>> {
        if(transactionType == null)
            return getReport(day)
        return transactionLogService.getTransactionFor(day, transactionType, config)
    }

    fun getReport(from: Date, to: Date, transactionType: TransactionType?): LiveData<PagedList<TransactionLog>> {
        if(transactionType == null)
            return getReport(from, to)
        return transactionLogService.getTransactionFor(from, to, transactionType, config)
    }

    fun printEndOfDay(date: Date, transactions: List<TransactionLog>, transactionType: TransactionType) {

        uiScope.launch {
            // get printer status on IO thread
            val printStatus = withContext(ioScope) {
                posDevice.printer.canPrint()
            }

            when (printStatus) {
                is PrintStatus.Error -> {
                    _printerMessage.value = printStatus.message
                }
                else -> {
                    // print transaction for current type
                    printTransactions(transactionType, transactions, date, date)
                }
            }
        }
    }

    fun printEndOfDay(from: Date, to: Date, transactions: List<TransactionLog>, transactionType: TransactionType) {

        uiScope.launch {
            // get printer status on IO thread
            val printStatus = withContext(ioScope) {
                posDevice.printer.canPrint()
            }

            when (printStatus) {
                is PrintStatus.Error -> {
                    _printerMessage.value = printStatus.message
                    _printButton.value = true
                }
                else -> {
                    // print transaction for current type
                    //change this date to date range
                    printTransactions(transactionType, transactions, from, to)
                }
            }
        }
    }


    fun printAll(from: Date, to: Date) {
        // get printer status on IO thread
        uiScope.launch {
            var counter = 1
            for (transactionType in TransactionType.values()) {

                // get transactions for current type
                val transactions = withContext(ioScope) {
                    transactionLogService.getTransactionListFor(from, to, transactionType)
                }

                // get print status before printing
                val printStatus = withContext(ioScope) {
                    posDevice.printer.canPrint()
                }
                when (printStatus) {
                    is PrintStatus.Error -> {
                        _printerMessage.value = printStatus.message
                        _printButton.value = true
                    }
                    else -> {
                        // print transaction for current type
                        printTransactions(transactionType, transactions, from, to)

                        if(counter != TransactionType.values().size){
                            _printButton.value = false
                        }
                        // delay for 2 secs before printing again
                        delay(2000)
                    }
                }
                counter++
            }
        }
    }

    private suspend fun printTransactions(transactionType: TransactionType, transactions: List<TransactionLog>, from: Date, to: Date) {
        if (transactions.isEmpty()) {
            _printButton.value = true
            return
        }

        // get name of transaction type
        val typeName = transactionType.name
        // get slip for current date
        val slipItems = transactions.toSlipItems(from, to, typeName)

        val user = UserType.Merchant
        // get user label print out
        val userCopy = PrintObject.Data(
            "*** $user copy ***".toUpperCase(),
            PrintStringConfiguration(displayCenter = true)
        )

        // add user label print out to top of slip
        slipItems.add(0, userCopy)
        // add user label print out to bottom of slip
        slipItems.add(userCopy)

        // print code in IO thread
        val status = withContext(ioScope) {
            posDevice.printer.printSlip(slipItems)
        }

        // publish print message
        _printerMessage.value = status.message
        val printed = status !is PrintStatus.Error
        // enable print button
        _printButton.value = printed
    }


    fun getEndOfDay(date: Date): LiveData<List<TransactionLog>> {
        // disable print button
        _printButton.value = false

        return transactionLogService.getTransactionFor(date)
    }


    fun getEndOfDay(date: Date, transactionType: TransactionType): LiveData<List<TransactionLog>> {
        // disable print button
        _printButton.value = false

        return transactionLogService.getTransactionFor(date, transactionType)
    }

    private fun getEndOfDay(from: Date, to: Date): LiveData<List<TransactionLog>> {
        // disable print button
        _printButton.value = false

        return transactionLogService.getTransactionFor(from, to)
    }

    fun getEndOfDay(from: Date, to: Date, transactionType: TransactionType?): LiveData<List<TransactionLog>> {
        if(transactionType == null)
            return getEndOfDay(from, to)

        // disable print buttond
        _printButton.value = false

        return transactionLogService.getTransactionFor(from, to, transactionType)
    }

    fun getSummary(transactions: List<TransactionLog>?) {
        var transactionApproved = 0
        var transactionApprovedAmountInNaira = 0.0
        var transactionFailedAmountInNaira = 0.0
        var transactionApprovedAmtInDollar = 0.0
        var transactionFailedAmountInDollar = 0.0
        if(transactions == null){
            _endOfDaySummary.postValue(null)
            return
        }
        transactions.forEach {
            if (it.responseCode == IsoUtils.OK && currencyType == IswPaymentInfo.CurrencyType.NAIRA) {
                transactionApproved += 1
                transactionApprovedAmountInNaira += DisplayUtils.getAmountString(it.amount)
            }else if(it.responseCode == IsoUtils.OK && currencyType == IswPaymentInfo.CurrencyType.DOLLAR){
                transactionApproved += 1
                transactionApprovedAmtInDollar += DisplayUtils.getAmountString(it.amount)
            }
            else if(it.responseCode != IsoUtils.OK && currencyType == IswPaymentInfo.CurrencyType.DOLLAR){
                transactionFailedAmountInDollar += DisplayUtils.getAmountString(it.amount)
            }
            else  if (it.responseCode != IsoUtils.OK && currencyType == IswPaymentInfo.CurrencyType.NAIRA){
                transactionFailedAmountInNaira += DisplayUtils.getAmountString(it.amount)
            }
        }

        val transactionSum = transactions.size
        val transactionFailed = transactionSum - transactionApproved
        val totalAmount = DisplayUtils.getAmountString((transactionApprovedAmountInNaira + transactionFailedAmountInNaira).toMajor())
        val eodSummary = EodSummary(
                transactionSum,
                transactionApproved,
                transactionFailed,
                totalAmount,
                DisplayUtils.getAmountString(transactionApprovedAmountInNaira.toMajor()),
                DisplayUtils.getAmountString(transactionFailedAmountInNaira.toMajor()),
                DisplayUtils.getAmountString(transactionApprovedAmtInDollar.toMajor()),
                DisplayUtils.getAmountString(transactionFailedAmountInDollar.toMajor())
        )
        _printButton.value = true
        _endOfDaySummary.postValue(eodSummary)
    }

    fun togglePrintButton(status: Boolean){
        _printButton.value = status
    }

    private fun List<TransactionLog>.toSlipItems(from: Date, to: Date, transactionType: String): MutableList<PrintObject> {
        val headerLineItems = mutableListOf<PrintObject>()
        // create the title for printout
        val title = PrintObject.Data(transactionType, PrintStringConfiguration(isTitle = true, displayCenter = true))

        // initialize list with the title and a line under
        val newLine = PrintObject.Data("\n")
        //val list = mutableListOf(title, newLine, PrintObject.Line)
        headerLineItems.addAll( mutableListOf(title, newLine, PrintObject.Line))

        //create the addressTitle for printout
        val addressTitle = PrintObject.Data("\nAddress\n", PrintStringConfiguration(isBold = true))
        //add addressTitle
        headerLineItems.add(addressTitle)
        //create the address for printout
        val address = PrintObject.Data("${terminalInfo.merchantNameAndLocation}\n")
        // add address
        headerLineItems.add(address)


        //add line
        headerLineItems.add(PrintObject.Line)


        //add terminalIdTitle
        val terminalIdTitle = PrintObject.Data("TerminalId\n", PrintStringConfiguration(isBold = true))
        //add terminalIdTitle
        headerLineItems.add(terminalIdTitle)
        //create the terminalId for printout
        val terminalId = PrintObject.Data("${terminalInfo.terminalId}\n")
        // add terminalId
        headerLineItems.add(terminalId)


        //add line
        headerLineItems.add(PrintObject.Line)

        // add date
        val startDate = DateUtils.shortDateFormat.format(from)
        val endDate = DateUtils.shortDateFormat.format(to)
        val dateTitle = PrintObject.Data("Date: $startDate - \n $endDate\n", PrintStringConfiguration(isBold = true))
        //add dateTitle
        headerLineItems.add(dateTitle)


        // add line
        headerLineItems.add(PrintObject.Line)

        val lineItems = mutableListOf<PrintObject>()

        // table title
        val timeTitle = "Time".padEnd(6, ' ')
        val amountTitle = "Amt".padEnd(12, ' ')
        val cardTitle = "Card".padEnd(5, ' ')
        val statusTitle = "Status".padEnd(6, ' ')
        val tableTitle = PrintObject.Data("$timeTitle $amountTitle $cardTitle $statusTitle\n")
        lineItems.addAll(mutableListOf(tableTitle, PrintObject.Line))

        var transactionApproved = 0
        var transactionApprovedAmountInNaira = 0.0
        var transactionApprovedAmountInDollar = 0.0
        var transactionFailedAmountInNaira = 0.0
        var transactionFailedAmountInDollar = 0.0

        // add each item into the end of day list
        this.forEach {
            val slipItem = it.toSlipItem()
            lineItems.add(slipItem)

            //add successful transactions
            if (it.responseCode == IsoUtils.OK && currencyType == IswPaymentInfo.CurrencyType.NAIRA) {
                transactionApproved += 1
                transactionApprovedAmountInNaira += DisplayUtils.getAmountString(it.amount)
            }else if(it.responseCode == IsoUtils.OK && currencyType == IswPaymentInfo.CurrencyType.DOLLAR){
                transactionApproved += 1
                transactionApprovedAmountInDollar += DisplayUtils.getAmountString(it.amount)
            }
            else if (it.responseCode != IsoUtils.OK && currencyType == IswPaymentInfo.CurrencyType.NAIRA){
                transactionFailedAmountInNaira += DisplayUtils.getAmountString(it.amount)
            }
            else if (it.responseCode != IsoUtils.OK && currencyType == IswPaymentInfo.CurrencyType.DOLLAR){
                transactionFailedAmountInDollar += DisplayUtils.getAmountString(it.amount)
            }
        }
        //add line after transaction line items
        lineItems.add(PrintObject.Line)

        val list = mutableListOf<PrintObject>()

        //create summary title
        val summary = PrintObject.Data("Summary\n", PrintStringConfiguration(isBold = true))
        //add summaryTitle
        list.add(summary)

        // total number of transactions
        val transactionSum = this.size
        val transactionFailed = transactionSum - transactionApproved

        val totalAmountString = DisplayUtils.getAmountString((transactionApprovedAmountInNaira + transactionFailedAmountInNaira).toMajor())
        val totalApprovedAmtStringInNaira = DisplayUtils.getAmountString(transactionApprovedAmountInNaira.toMajor())
        val totalFailedAmtStringInNaira = DisplayUtils.getAmountString(transactionFailedAmountInNaira.toMajor())
        val totalApprovedAmtStringInDollar = DisplayUtils.getAmountString(transactionApprovedAmountInDollar.toMajor())
        val totalFailedAmtStringInDollar = DisplayUtils.getAmountString(transactionFailedAmountInDollar.toMajor())

        list.add(PrintObject.Data("Total Transactions: $transactionSum\n", PrintStringConfiguration(isBold = true)))
        list.add(PrintObject.Data("Total Passed Transaction: $transactionApproved\n", PrintStringConfiguration(isBold = true)))
        list.add(PrintObject.Data("Total Failed Transaction: $transactionFailed\n", PrintStringConfiguration(isBold = true)))
        list.add(PrintObject.Data("Total Appvd Amt in Naira: $totalApprovedAmtStringInNaira\n", PrintStringConfiguration(isBold = true)))
        list.add(PrintObject.Data("Total Appvd Amt in Dollar: $totalApprovedAmtStringInDollar\n", PrintStringConfiguration(isBold = true)))
        list.add(PrintObject.Data("Total Failed Amt in Naira: $totalFailedAmtStringInNaira\n", PrintStringConfiguration(isBold = true)))
        list.add(PrintObject.Data("Total Failed Amt in Dollar: $totalFailedAmtStringInDollar\n", PrintStringConfiguration(isBold = true)))
        list.add(PrintObject.Data("Total Amount: $totalAmountString\n", PrintStringConfiguration(isBold = true)))
        list.add(PrintObject.Line)

        //return lineItems.plus(list).toMutableList()
        return headerLineItems.plus(list).plus(lineItems).toMutableList()
    }

    private fun TransactionLog.toSlipItem(): PrintObject {
        val date = Date(this.time)
        val dateStr = DateUtils.hourMinuteFormat.format(date).padEnd(6, ' ').take(6)
        val amount = DisplayUtils.getAmountString(this.amount.toInt()).padEnd(12, ' ').take(12)
        val code = this.responseCode
        val card = this.cardPan.takeLast(4).padEnd(5, ' ').take(5)
        val status = (if (code == IsoUtils.OK) "PASS" else "FAIL").padEnd(6, ' ').take(6)


        return PrintObject.Data("$dateStr $amount $card $status\n")
    }

    private fun formatAmount(amount: String): String {
        val spaceCount = 10 - amount.length
        val padding = " ".repeat(spaceCount)
        return amount + padding
    }

    private fun Double.toMajor(): Double {
      return  this / 100.0
    }
}