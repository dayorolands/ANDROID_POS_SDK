package com.interswitchng.smartpos.shared.interfaces.library

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.transaction.TransactionLog
import java.util.*

interface TransactionLogService {


    /**
     * This function is responsible for saving transaction
     * logs to the proposed storage implementation
     *
     * @param result  The transaction result to be logged
     */
    fun logTransactionResult(result: TransactionLog)


    /**
     * This function is responsible for retrieving all logged
     * transaction, essentially providing transaction history
     *
     * @param  pagedListConfig  a configuration object for the returned paged list
     * @return  a paged list of all the transactions that have been stored
     */
    fun getTransactions(pagedListConfig: PagedList.Config): LiveData<PagedList<TransactionLog>>


    /** This function is responsible for retrieving a paged list of transaction for a
     * specific day, with the specified range of start to end of day
     *
     * @param date  the day for transactions to be retrieved
     * @return  a paged list of transactions for that day
     */
    fun getTransactionFor(date: Date, pagedListConfig: PagedList.Config): LiveData<PagedList<TransactionLog>>

    /** This function is responsible for retrieving a paged list of transaction for a
     * specific day, with the specified range of start to end of day
     *
     * @param from  the start day for transactions to be retrieved
     * @param to  the end day for transactions to be retrieved
     * @return  a paged list of transactions for that day
     */
    fun getTransactionFor(from: Date, to: Date, pagedListConfig: PagedList.Config): LiveData<PagedList<TransactionLog>>

    /** This function is responsible for retrieving a paged list of transaction for a
     * specific day and a specific type, with the specified range of start to end of day
     *
     * @param date  the day for transactions to be retrieved
     * @param transactionType  the type of the transactions to be retrieved
     * @return  a paged list of transactions for that day
     */
    fun getTransactionFor(date: Date, transactionType: TransactionType, pagedListConfig: PagedList.Config): LiveData<PagedList<TransactionLog>>

    /** This function is responsible for retrieving a paged list of transaction for a
     * specific day and a specific type, with the specified range of start to end of day
     *
     * @param from  the start day for transactions to be retrieved
     * @param to  the end day for transactions to be retrieved
     * @param transactionType  the type of the transactions to be retrieved
     * @return  a paged list of transactions for that day
     */
    fun getTransactionFor(from: Date, to: Date, transactionType: TransactionType, pagedListConfig: PagedList.Config): LiveData<PagedList<TransactionLog>>

    /** This function is responsible for retrieving a list transaction for a
     * specific day, with the specified range of start to end of day
     *
     * @param date  the day for transactions to be retrieved
     * @return  a list of transactions for that day
     */
    fun getTransactionFor(date: Date): LiveData<List<TransactionLog>>


    /** This function is responsible for retrieving a list transaction for a
     * specific day and a specific type, with the specified range of start to end of day
     *
     * @param from the start day for transactions to be retrieved
     * @param to  the end day for transactions to be retrieved
     * @param transactionType  the type of the transactions to be retrieved
     * @return  a paged list of transactions for that day
     */
    fun getTransactionFor(from: Date, to: Date, transactionType: TransactionType): LiveData<List<TransactionLog>>

    /** This function is responsible for retrieving a list transaction for a
     * specific day, with the specified range of start to end of day
     *
     * @param from the start day for transactions to be retrieved
     * @param to the end day for transactions to be retrieved
     * @return  a list of transactions for that day
     */
    fun getTransactionFor(from: Date, to: Date): LiveData<List<TransactionLog>>


    /** This function is responsible for retrieving a list transaction for a
     * specific day and a specific type, with the specified range of start to end of day
     *
     * @param date  the day for transactions to be retrieved
     * @param transactionType  the type of the transactions to be retrieved
     * @return  a paged list of transactions for that day
     */
    fun getTransactionFor(date: Date, transactionType: TransactionType): LiveData<List<TransactionLog>>

    /** This function is responsible for synchronously retrieving a list transaction for a
     * specific day and a specific type, with the specified range of start to end of day
     *
     * @param date  the day for transactions to be retrieved
     * @param transactionType  the type of the transactions to be retrieved
     * @return  a paged list of transactions for that day
     */
    fun getTransactionListFor(date: Date, transactionType: TransactionType): List<TransactionLog>

    /** This function is responsible for synchronously retrieving a list transaction for a
     * specific day and a specific type, with the specified range of start to end of day
     *
     * @param from the start day for transactions to be retrieved
     * @param to the end day for transactions to be retrieved
     * @param transactionType  the type of the transactions to be retrieved
     * @return  a paged list of transactions for that day
     */
    fun getTransactionListFor(from: Date, to: Date, transactionType: TransactionType): List<TransactionLog>

}