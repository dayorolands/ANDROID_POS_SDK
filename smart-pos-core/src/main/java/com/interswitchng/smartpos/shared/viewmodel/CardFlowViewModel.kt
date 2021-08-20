package com.interswitchng.smartpos.shared.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.interswitchng.smartpos.shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvMessage
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvResult
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.EmvData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.response.TransactionResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class CardFlowViewModel(private val posDevice: POSDevice) : BaseViewModel() {


    // communication channel with cardreader
    private val channel = Channel<EmvMessage>()

    // card removed flag
    private var cardRemoved = false

    private val _emvMessage = MutableLiveData<EmvMessage>()
    val emvMessage: LiveData<EmvMessage> = _emvMessage

    private val emv by lazy { posDevice.getEmvCardReader() }

    fun setupTransaction(amount: Int, terminalInfo: TerminalInfo) {

        with(uiScope) {
            // launch job in IO thread to listen for messages
            launch(ioScope) {
                // listen and  publish all received messages
                for (message in channel) {
                    cardRemoved = cardRemoved || message is EmvMessage.CardRemoved
                    _emvMessage.postValue(message)
                }
            }

            // trigger transaction setup in IO thread
            launch(ioScope) {
                // setup transaction
                emv.setupTransaction(amount, terminalInfo, channel, uiScope)
            }
        }
    }


    fun startTransaction(handleResult: (EmvResult, EmvData?) -> Unit) {
        uiScope.launch {
            //  start card transaction in IO thread
            val result = withContext(ioScope) { emv.startTransaction() }

            // publish processing transaction message
            if (result == EmvResult.ONLINE_REQUIRED) {
                // set message as transaction processing
                _emvMessage.value = EmvMessage.ProcessingTransaction
            }

            // else if txn was not a cancelled transaction, show cancelled transaction
            // if its not already triggered by card removal (i.e unable to process txn)
            else if (result != EmvResult.CANCELLED && !cardRemoved) {
                // trigger transaction cancel
                val reason = "Unable to process card transaction"
                _emvMessage.value = EmvMessage.TransactionCancelled(-1, reason)
            }

            // read txn info
            val emvData: EmvData? = emv.getTransactionInfo()

            // handle emv result
            handleResult.invoke(result, emvData)
        }
    }

    fun completeEmvTransaction(response: TransactionResponse) = emv.completeTransaction(response)


    override fun onCleared() {
        super.onCleared()
        channel.cancel()
    }
}