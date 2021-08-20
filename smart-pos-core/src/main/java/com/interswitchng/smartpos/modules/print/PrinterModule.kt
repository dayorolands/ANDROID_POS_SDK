package com.interswitchng.smartpos.modules.print

import android.util.Log
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.printer.info.PrintStatus
import com.interswitchng.smartpos.shared.models.results.IswPrintResult
import com.interswitchng.smartpos.shared.viewmodel.PrintViewModel
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class PrinterModule : KoinComponent {

    private val printViewModel: PrintViewModel by inject()

    fun canPrint() {
        printViewModel.printerStatus()
    }

    fun print(printObject: List<PrintObject>) {
        when (val printStatus = printViewModel.canPrint()) {
            is PrintStatus.Ok -> printViewModel.print(printObject)
            is PrintStatus.Error -> IswPrintResult("01", printStatus.message)
        }
    }

}