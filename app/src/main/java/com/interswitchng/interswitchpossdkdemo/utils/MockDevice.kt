package com.interswitchng.interswitchpossdkdemo.utils

import android.graphics.Bitmap
import com.interswitchng.smartpos.shared.interfaces.device.DevicePrinter
import com.interswitchng.smartpos.shared.interfaces.device.EmvCardReader
import com.interswitchng.smartpos.shared.interfaces.device.POSDevice
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.models.core.UserType
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.printer.info.PrintStatus
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvMessage
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.EmvResult
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request.EmvData
import com.interswitchng.smartpos.shared.models.transaction.cardpaycode.response.TransactionResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel

internal class MockDevice : POSDevice {
    override val hasFingerPrintReader: Boolean
        get() = false

    override val name: String
        get() = "Mock Device"

    override fun loadInitialKey(initialKey: String, ksn: String) {}
    override fun loadPinKey(pinKey: String) {}
    override fun loadMasterKey(masterKey: String) {}

    override val printer: DevicePrinter
        get() = object : DevicePrinter {

            override fun printSlip(slip: List<PrintObject>, isReceipt: Boolean): PrintStatus {
                return PrintStatus.Error("No DevicePrinterImpl installed")
            }

            override fun canPrint(): PrintStatus {
                return PrintStatus.Error("No DevicePrinterImpl installed")
            }

            override fun printCompanyLogo() {
                TODO("Not yet implemented")
            }
        }

    override fun getEmvCardReader(): EmvCardReader {
        return object : EmvCardReader {
            override suspend fun setupTransaction(
                    amount: Int,
                    terminalInfo: TerminalInfo,
                    channel: Channel<EmvMessage>,
                    scope: CoroutineScope
            ) {
            }

            override fun completeTransaction(response: TransactionResponse): EmvResult {
                return EmvResult.OFFLINE_APPROVED
            }

            override fun startTransaction(): EmvResult {
                return EmvResult.OFFLINE_APPROVED
            }

            override fun cancelTransaction() {}
            override fun getTransactionInfo(): EmvData? {
                return null
            }
        }
    }

}