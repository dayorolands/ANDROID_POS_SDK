package com.interswitchng.smartpos.modules.transactions.purchase.modules.ussdqr.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.interswitchng.smartpos.shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.shared.interfaces.library.HttpService
import com.interswitchng.smartpos.shared.models.core.UserType
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.posconfig.PrintStringConfiguration
import com.interswitchng.smartpos.shared.models.printer.info.PrintStatus
import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.request.TransactionStatus
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.PaymentStatus
import com.interswitchng.smartpos.shared.utilities.toast
import com.interswitchng.smartpos.shared.viewmodel.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal abstract class BaseCodeViewModel(protected val paymentService: HttpService) : BaseViewModel() {

    private val _paymentStatus = MutableLiveData<PaymentStatus>()
    val paymentStatus: LiveData<PaymentStatus> = _paymentStatus

    private val _printButton = MutableLiveData<Boolean>()
    val printButton: LiveData<Boolean> get() = _printButton

    val showProgress = MutableLiveData<Boolean>()
    private var pollingJob: Job? = null


    fun pollTransactionStatus(status: TransactionStatus) {
        // cancel current polling job before starting new one
        pollingJob?.cancel()

        // poll for status on IO thread
        pollingJob = uiScope.launch(ioScope) {
            // delay for 15 seconds before polling
            delay(15_000)
            // continuously repeat polling process
            repeat(10) {

                // start polling for payment status
                poll(status, PaymentStatus.OngoingTimeout)

                // delay for 5 seconds before next polling process
                // if its not the last iteration
                if (it < 9) delay(5_000)
                else _paymentStatus.postValue(PaymentStatus.Timeout)
            }

            pollingJob?.cancel()
        }
    }

    fun checkTransactionStatus(status: TransactionStatus) {
        // cancel current polling job before starting new one
        pollingJob?.cancel()

        // start new job
        pollingJob = uiScope.launch (ioScope) {
            poll(status, PaymentStatus.Timeout)
        }
    }

    private suspend fun poll(status: TransactionStatus, timeoutMessage: PaymentStatus) {

        // get the type of payment
        val type = when (this) {
            is QrViewModel -> PaymentType.QR
            else -> PaymentType.USSD
        }

        // show progress indicator
        showProgress.postValue(true)

        // poll 3 times before timeout
        repeat(3) {

            // get payment status
            val paymentStatus = paymentService.checkPayment(type, status)

            // publish status
            _paymentStatus.postValue(paymentStatus)

            // wait for 3 seconds
            delay(3_000)

            // trigger timeout if its third iteration starting from 0
            if (it == 2) _paymentStatus.postValue(timeoutMessage)
        }

        // hide progress indicator
        showProgress.postValue(false)
    }


    fun printCode(context: Context, posDevice: POSDevice, user: UserType, slip: MutableList<PrintObject>) {
        uiScope.launch {
            // get printer status on IO thread
            val printStatus = withContext(ioScope) { posDevice.printer.canPrint() }

            when (printStatus) {
                is PrintStatus.Error -> context.toast(printStatus.message)
                else -> {
                    // disable print button
                    _printButton.value = false

                    // get user label print out
                    val userCopy = PrintObject.Data(
                        "*** $user copy ***".toUpperCase(),
                        PrintStringConfiguration(displayCenter = true)
                    )

                    // add user label print out to top of slip
                    slip.add(0, userCopy)
                    // add user label print out to bottom of slip
                    slip.add(userCopy)

                    // print code in IO thread
                    val status = withContext(ioScope) { posDevice.printer.printSlip(slip) }
                    // toast print message
                    context.toast(status.message)
                    // enable print button
                    _printButton.value = true
                }
            }
        }
    }

    fun cancelPoll() {
        pollingJob?.cancel()
    }

    fun reset() {
        _paymentStatus.postValue(null)
        _printButton.postValue(null)
        showProgress.postValue(false)
    }
}