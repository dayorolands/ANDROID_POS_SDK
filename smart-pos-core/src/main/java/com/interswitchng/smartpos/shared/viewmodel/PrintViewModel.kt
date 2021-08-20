package com.interswitchng.smartpos.shared.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.printer.info.PrintStatus
import com.interswitchng.smartpos.shared.models.results.IswPrintResult
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

internal class PrintViewModel(private val posDevice: POSDevice) : BaseViewModel(), IswPos.IswPrinterCallback {

//    private val _printResponse = MutableLiveData<PrintStatus>()
//    val printResponse: LiveData<PrintStatus> get() = _printResponse

    override fun onError(result: IswPrintResult) {
        IswPos.getInstance().setPrintResult(result)
    }

    override fun onPrintCompleted(result: IswPrintResult) {
        IswPos.getInstance().setPrintResult(result)
    }

    fun print(printObject: List<PrintObject>) {
        uiScope.launch {
            // get printer status on IO thread
            withContext(ioScope) {
                val printerStatus = posDevice.printer.canPrint()

                when (printerStatus) {
                    is PrintStatus.Error -> onError(IswPrintResult("01", printerStatus.message))
                    else -> doPrint(printObject)
                }
            }
        }
    }

//    override fun onCleared() {
//        super.onCleared()
//        uiScope.cancel()
//    }

    fun canPrint(): PrintStatus {
        return posDevice.printer.canPrint()
    }

    fun printerStatus() {
        when (val status = canPrint()) {
            is PrintStatus.Ok -> onPrintCompleted(IswPrintResult("00", status.message))
            is PrintStatus.Error -> onError(IswPrintResult("01", status.message))
        }
    }

    private fun doPrint(printObject: List<PrintObject>) {
        when (val status = posDevice.printer.printSlip(printObject)) {
            is PrintStatus.Ok -> onPrintCompleted(IswPrintResult("00", status.message))
            else -> onError(IswPrintResult("01", status.message))
        }

    }
}