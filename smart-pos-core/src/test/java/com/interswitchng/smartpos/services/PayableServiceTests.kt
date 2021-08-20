/*
package com.interswitchng.smartpos.services

import com.igweze.ebi.simplecalladapter.Simple
import com.interswitchng.smartpos.shared.interfaces.retrofit.IHttpService
import com.interswitchng.smartpos.shared.models.core.Callback
import com.interswitchng.smartpos.shared.models.transaction.PaymentType
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.request.TransactionStatus
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.Bank
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.Transaction
import com.interswitchng.smartpos.shared.services.HttpServiceImpl
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test

class PayableServiceTests {


    @Test
    fun `should invoke 'onTransactionComplete' of callback when the transactionStatus is completed`() {

        val transactionStatusCallback: TransactionRequeryCallback = mock()
        val status = TransactionStatus("", "")


        val successTransaction: Transaction = mock {
            on(mock.isCompleted()) doReturn true
            on(mock.isPending()) doReturn false
        }

        val simpleResponse: Simple<Transaction?> = mock {
            on(mock.run(any<(Transaction?, Throwable?) -> Unit>())).doAnswer {
                val argumentCallback:  Callback<Transaction?> = it.getArgument(0)
                argumentCallback(successTransaction, null)
            }
        }

        val httpService: IHttpService = mock {
            on(mock.getQrTransactionStatus(any(), any())) doReturn simpleResponse
        }

        val threadCountBeforeServiceCall = Thread.activeCount()
        val service = HttpServiceImpl(httpService)
        service.checkPayment(PaymentType.QR, status, 3000, transactionStatusCallback)
        val threadCountDuringServiceCall = Thread.activeCount()
        assertEquals(threadCountBeforeServiceCall + 1, threadCountDuringServiceCall)


        Thread.sleep(3000)
        verify(transactionStatusCallback, times(1)).onTransactionCompleted(successTransaction)
        verify(transactionStatusCallback, times(0)).onTransactionStillPending(any())
        verify(transactionStatusCallback, times(0)).onTransactionError(anyOrNull(), anyOrNull())
        verify(transactionStatusCallback, times(0)).onTransactionTimeOut()
        assertTrue(threadCountDuringServiceCall > Thread.activeCount())
    }


    @Test
    fun `should invoke 'onTransactionStillPending' of callback when the transactionStatus is pending`() {
        val transactionStatusCallback: TransactionRequeryCallback = mock()
        val status = TransactionStatus("", "")


        val pendingTransaction: Transaction = mock {
            on(mock.isCompleted()) doReturn false
            on(mock.isPending()) doReturn true
        }

        val simpleResponse: Simple<Transaction?> = mock {
            on(mock.run(any<(Transaction?, Throwable?) -> Unit>())).doAnswer {
                val argumentCallback:  Callback<Transaction?> = it.getArgument(0)
                argumentCallback(pendingTransaction, null)
            }
        }

        val httpService: IHttpService = mock {
            on(mock.getQrTransactionStatus(any(), any())) doReturn simpleResponse
        }


        val service = HttpServiceImpl(httpService)
        val threadCountBeforeServiceCall = Thread.activeCount()
        service.checkPayment(PaymentType.QR, status, 3000, transactionStatusCallback)
        val threadCountDuringServiceCall = Thread.activeCount()
        assertEquals(threadCountBeforeServiceCall + 1, threadCountDuringServiceCall)


        Thread.sleep(2800)
        assertTrue(threadCountDuringServiceCall == Thread.activeCount())
        verify(transactionStatusCallback, times(0)).onTransactionCompleted(any())
        verify(transactionStatusCallback, times(1)).onTransactionStillPending(any())
        verify(transactionStatusCallback, times(0)).onTransactionError(anyOrNull(), anyOrNull())
        verify(transactionStatusCallback, times(0)).onTransactionTimeOut()
    }

    @Test
    fun `should invoke 'onTransactionTimeout' of callback when the transactionStatus is pending beyond specified timeout`() {
        val transactionStatusCallback: TransactionRequeryCallback = mock()
        val status = TransactionStatus("", "")


        val pendingTransaction: Transaction = mock {
            on(mock.isCompleted()) doReturn false
            on(mock.isPending()) doReturn true
        }

        val simpleResponse: Simple<Transaction?> = mock {
            on(mock.run(any<(Transaction?, Throwable?) -> Unit>())).doAnswer {
                val argumentCallback:  Callback<Transaction?> = it.getArgument(0)
                argumentCallback(pendingTransaction, null)
            }
        }

        val httpService: IHttpService = mock {
            on(mock.getUssdTransactionStatus(any(), any())) doReturn simpleResponse
        }

        val service = HttpServiceImpl(httpService)
        val threadCountBeforeServiceCall = Thread.activeCount()
        service.checkPayment(PaymentType.USSD, status, 6000, transactionStatusCallback)


        Thread.sleep(7500)
        verify(transactionStatusCallback, times(0)).onTransactionTimeOut()
        verify(transactionStatusCallback, times(2)).onTransactionStillPending(any())
        val threadCountDuringServiceCall = Thread.activeCount()
        assertEquals(threadCountBeforeServiceCall + 1, threadCountDuringServiceCall)


        Thread.sleep(1000)
        assertTrue(threadCountDuringServiceCall > Thread.activeCount())
        verify(transactionStatusCallback, times(0)).onTransactionCompleted(any())
        verify(transactionStatusCallback, times(0)).onTransactionError(anyOrNull(), anyOrNull())
        verify(transactionStatusCallback, times(1)).onTransactionTimeOut()
    }



    @Test
    fun `should invoke 'onTransactionError' of callback when the transactionStatus is not pending or completed`() {
        val transactionStatusCallback: TransactionRequeryCallback = mock()
        val status = TransactionStatus("", "")


        val pendingTransaction: Transaction = mock {
            on(mock.isCompleted()) doReturn false
            on(mock.isPending()) doReturn false
        }



        val simpleResponse: Simple<Transaction?> = mock {
            on(mock.run(any<(Transaction?, Throwable?) -> Unit>())).doAnswer {
                val argumentCallback:  Callback<Transaction?> = it.getArgument(0)
                argumentCallback(pendingTransaction, null)
            }
        }

        val httpService: IHttpService = mock {
            on(mock.getUssdTransactionStatus(any(), any())) doReturn simpleResponse
        }


        val service = HttpServiceImpl(httpService)
        val threadCountBeforeServiceCall = Thread.activeCount()
        service.checkPayment(PaymentType.USSD, status, 3000, transactionStatusCallback)
        val threadCountDuringServiceCall = Thread.activeCount()
        assertEquals(threadCountBeforeServiceCall + 1, threadCountDuringServiceCall)


        Thread.sleep(2000)
        assertTrue(threadCountDuringServiceCall > Thread.activeCount())
        verify(transactionStatusCallback, times(0)).onTransactionCompleted(any())
        verify(transactionStatusCallback, times(0)).onTransactionStillPending(any())
        verify(transactionStatusCallback, times(1)).onTransactionError(anyOrNull(), anyOrNull())
        verify(transactionStatusCallback, times(0)).onTransactionTimeOut()
    }


    @Test
    fun `should invoke callback function with response`() {
        val callback: Callback<List<Bank>?> = mock()

        val response: Simple<List<Bank>?> = mock()
        whenever(response.process(callback)).then {
            val argumentCallback:  Callback<List<Bank>?> = it.getArgument(0)
            argumentCallback(listOf(), null)
        }

        val httpService: IHttpService = mock()
        whenever(httpService.getBanks()).doReturn(response)

        val service = HttpServiceImpl(httpService)
        //service.getBanks(callback)


        verify(httpService, times(1)).getBanks()
        verify(callback, times(1)).invoke(any(), anyOrNull())
    }

}*/
