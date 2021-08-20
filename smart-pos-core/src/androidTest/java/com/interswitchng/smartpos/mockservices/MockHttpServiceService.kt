package com.interswitchng.smartpos.mockservices

import android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.interswitchng.smartpos.old_shared.interfaces.library.HttpService
import com.interswitchng.smartpos.old_shared.models.core.Callback
import com.interswitchng.smartpos.old_shared.models.transaction.PaymentType
import com.interswitchng.smartpos.old_shared.models.transaction.ussdqr.request.CodeRequest
import com.interswitchng.smartpos.old_shared.models.transaction.ussdqr.request.TransactionStatus
import com.interswitchng.smartpos.old_shared.models.transaction.ussdqr.response.Bank
import com.interswitchng.smartpos.old_shared.models.transaction.ussdqr.response.CodeResponse
import com.interswitchng.smartpos.old_shared.models.transaction.ussdqr.response.Transaction
import com.interswitchng.smartpos.old_shared.models.utils.IswDisposable
import com.interswitchng.smartpos.old_shared.utilities.ThreadUtils

internal class MockHttpService private constructor(
        private val qr: ((Callback<CodeResponse?>) -> Unit)?,
        private val ussd: ((Callback<CodeResponse?>) -> Unit)?,
        private val banks: ((Callback<List<Bank>?>) -> Unit)?,
        private val paymentStatus: ((TransactionRequeryCallback) -> Unit)?) : HttpService {



    private val defaultTime = 2500L
    private val code = "00"
    private val desc = "Successful"
    private val date = "2018-11-23T16:21:22"
    private val ref = "46524"
    private val bankShortCode = "*737*6*46524#"
    private val defaultShortCode = "*322*419*051691201*46524#"
    private val terminalId = "2069018M"
    private val transactionReference = "TRSuiKz3mpqoXmDsW7bqiiZsZ"
    private val qrCodeData = "000201021340771312156775204599953035665406100.005802NG5911Refund Test6005Lagos62290525ptu3UzobDrKJ9IE0kgcvxHITl6304432C"
    private val response = CodeResponse(code, desc, date, ref, bankShortCode, defaultShortCode, terminalId, transactionReference, qrCodeData)

    private val banksResponse = listOf(Bank(107, "ACCESS BANK PLC", "ABP"),
            Bank(108, "ECOBANK NIGERIA", "ECO"),
            Bank(103, "GUARANTY TRUST BANK", "isw_logo_gtb"),
            Bank(114, "Skye Bank", "SKYE"))

    override fun initiateUssdPayment(request: CodeRequest, callback: Callback<CodeResponse?>) {

        Thread(Runnable {

            Thread.sleep(defaultTime)

            runOnUiThread {
                if (ussd != null) ussd.invoke(callback)
                else callback(response, null)
            }

        }).start()
    }

    override fun initiateQrPayment(request: CodeRequest, callback: Callback<CodeResponse?>) {

        Thread(Runnable {

            Thread.sleep(defaultTime)

            runOnUiThread {
                if (qr != null) qr.invoke(callback)
                else callback(response, null)
            }

        }).start()
    }

    override fun getBanks(callback: Callback<List<Bank>?>) {
        Thread(Runnable {
            Thread.sleep(defaultTime)

            runOnUiThread {
                if (banks != null) banks.invoke(callback)
                else callback(banksResponse, null)
            }
        }).start()
    }

    override fun checkPayment(type: PaymentType, status: TransactionStatus, timeout: Long, callback: TransactionRequeryCallback): IswDisposable {
        return ThreadUtils.createExecutor {
            Thread.sleep(defaultTime)
            val response = Transaction(3380867, 5000, "XeAAoeCX8dklyjbBMDg5wysiA", "00", "566", false, "011", 50, null)

            runOnUiThread {
                if (paymentStatus != null) paymentStatus.invoke(callback)
                else callback.onTransactionCompleted(response)
            }
        }
    }

    class Builder {
        private var qr: ((Callback<CodeResponse?>) -> Unit)? = null
        private var ussd: ((Callback<CodeResponse?>) -> Unit)? = null
        private var banks: ((Callback<List<Bank>?>) -> Unit)? = null
        private var paymentStatus: ((TransactionRequeryCallback) -> Unit)? = null

        fun setQrCall(call: (Callback<CodeResponse?>) -> Unit): Builder {
            qr = call
            return this
        }

        fun setUssdCall(call: (Callback<CodeResponse?>) -> Unit): Builder {
            ussd = call
            return this
        }

        fun setBanksCall(call: (Callback<List<Bank>?>) -> Unit): Builder {
            banks = call
            return this
        }

        fun setPaymentStatusCall(call: (TransactionRequeryCallback) -> Unit): Builder {
            paymentStatus = call
            return this
        }

        fun build() = MockHttpService(qr, ussd, banks, paymentStatus)

    }
}