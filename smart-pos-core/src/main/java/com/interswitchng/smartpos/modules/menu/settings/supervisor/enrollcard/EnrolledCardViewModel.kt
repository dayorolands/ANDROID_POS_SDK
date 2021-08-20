package com.interswitchng.smartpos.modules.menu.settings.supervisor.enrollcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.interswitchng.smartpos.shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvMessage
import com.interswitchng.smartpos.shared.viewmodel.BaseViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

internal class EnrolledCardViewModel(private val posDevice: POSDevice) : BaseViewModel() {


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
                // setup transaction to read card details
                emv.setupTransaction(amount, terminalInfo, channel, uiScope)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }
}