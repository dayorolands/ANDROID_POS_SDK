package com.interswitchng.smartpos.shared.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.interswitchng.smartpos.shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.shared.models.core.UserType
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.posconfig.PrintStringConfiguration
import com.interswitchng.smartpos.shared.models.printer.info.PrintStatus
import com.interswitchng.smartpos.shared.models.printer.slips.TransactionSlip
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class TransactionDetailViewModel(private val posDevice: POSDevice) : BaseViewModel() {


    private val _printButton = MutableLiveData<Boolean>()
    val printButton: LiveData<Boolean> get() = _printButton

    private val _printerMessage = MutableLiveData<String>()
    val printerMessage: LiveData<String> get() = _printerMessage


    fun printSlip(user: UserType, slip: TransactionSlip) {
        // re-print flag
        val reprint = true

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
                    // disable print button
                    _printButton.value = false

                    // get items to print
                    val slipItems = slip.getSlipItems(reprint)

                    // get user label print out
                    val userCopy = PrintObject.Data(
                        "*** $user copy ***".toUpperCase(),
                        PrintStringConfiguration(displayCenter = true)
                    )

                    // add user label print out to top of slip
                    slipItems.add(0, userCopy)
                    // add user label print out to bottom of slip
                    //slipItems.add(userCopy)


                    // print code in IO thread
                    val status =
                        withContext(ioScope) { posDevice.printer.printSlip(slipItems) }
                    // publish print message
                    _printerMessage.value = status.message
                    // enable print button
                    _printButton.value = true
                }
            }
        }
    }



}