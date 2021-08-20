package com.interswitchng.smartpos.mockservices

import com.interswitchng.smartpos.old_shared.interfaces.device.EmvCardReader
import com.interswitchng.smartpos.old_shared.interfaces.library.EmvCallback
import com.interswitchng.smartpos.old_shared.models.transaction.cardpaycode.CardDetail
import com.interswitchng.smartpos.old_shared.models.core.TerminalInfo
import com.interswitchng.smartpos.old_shared.models.transaction.cardpaycode.EmvResult
import com.interswitchng.smartpos.old_shared.models.transaction.cardpaycode.request.EmvData
import com.interswitchng.smartpos.old_shared.models.transaction.cardpaycode.response.TransactionResponse

class MockEmvCardReaderImpl: EmvCardReader {
    override fun completeTransaction(response: TransactionResponse): EmvResult {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransactionInfo(): EmvData? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setEmvCallback(callback: EmvCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeEmvCallback(callback: EmvCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setupTransaction(amount: Int, terminalInfo: TerminalInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startTransaction(): EmvResult {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCardDetail(): CardDetail {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelTransaction() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}