package com.interswitchng.smartpos.shared.services.storage

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.interswitchng.smartpos.shared.interfaces.library.TransactionLogService
import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.transaction.TransactionLog
import com.zhuinden.monarchy.Monarchy
import io.realm.Sort
import io.realm.kotlin.where
import java.util.*


internal data class DayTime(val morning: Long, val midnight: Long)

class TransactionLogServiceImpl(private val monarchy: Monarchy) : TransactionLogService {

    override fun logTransactionResult(result: TransactionLog) = monarchy.writeAsync { realm ->
        // retrieve the latest id
        val currentId: Number = realm.where(TransactionLog::class.java).max("id") ?: 0
        // set the next id
        result.id = currentId.toInt() + 1
        // save result to realm db
        realm.copyToRealm(result)
    }

    override fun getTransactions(pagedListConfig: PagedList.Config): LiveData<PagedList<TransactionLog>> {

        // query for stream of transaction logs by creating dataSource factory
        val logDataSourceFactory = monarchy.createDataSourceFactory { realm ->
            realm.where<TransactionLog>().sort("time", Sort.DESCENDING)
        }

        // create paged list builder for datasource factory
        val livePagedBuilder = LivePagedListBuilder(logDataSourceFactory, pagedListConfig)

        // generate and return liveData of pagedList transactionLog
        return monarchy.findAllPagedWithChanges(logDataSourceFactory, livePagedBuilder)
    }

    override fun getTransactionFor(date: Date, pagedListConfig: PagedList.Config): LiveData<PagedList<TransactionLog>> {
        // get the date range for current date as morning and midnight
        val (morning, midnight) = getDateRange(date)

        // query for stream of transaction logs for specified day by creating dataSource factory
        val logDataSourceFactory = monarchy.createDataSourceFactory { realm ->
            realm.where<TransactionLog>()
                .greaterThan("time", morning)
                .lessThan("time", midnight)
                .sort("time", Sort.DESCENDING)
        }

        // create paged list builder for datasource factory
        val livePagedBuilder = LivePagedListBuilder<Int, TransactionLog>(logDataSourceFactory, pagedListConfig)

        // generate and return liveData of pagedList transactionLog
        return monarchy.findAllPagedWithChanges(logDataSourceFactory, livePagedBuilder)

    }

    override fun getTransactionFor(from: Date, to: Date, pagedListConfig: PagedList.Config): LiveData<PagedList<TransactionLog>> {
        // get the start and end date in milliseconds
        val start = getMorningTimeInMillis(from)
        val end = getNightTimeInMillis(to)

        // query for stream of transaction logs for specified day by creating dataSource factory
        val logDataSourceFactory = monarchy.createDataSourceFactory { realm ->
            realm.where<TransactionLog>()
                    .greaterThan("time", start)
                    .lessThan("time", end)
                    .sort("time", Sort.DESCENDING)
        }

        // create paged list builder for datasource factory
        val livePagedBuilder = LivePagedListBuilder<Int, TransactionLog>(logDataSourceFactory, pagedListConfig)

        // generate and return liveData of pagedList transactionLog
        return monarchy.findAllPagedWithChanges(logDataSourceFactory, livePagedBuilder)
    }

    override fun getTransactionFor(date: Date, transactionType: TransactionType, pagedListConfig: PagedList.Config): LiveData<PagedList<TransactionLog>> {
        // get the date range for current date as morning and midnight
        val (morning, midnight) = getDateRange(date)

        // query for stream of transaction logs for specified day by creating dataSource factory
        val logDataSourceFactory = monarchy.createDataSourceFactory { realm ->
            realm.where<TransactionLog>()
                .equalTo("transactionType", transactionType.ordinal)
                .greaterThan("time", morning)
                .lessThan("time", midnight)
                .sort("time", Sort.DESCENDING)
        }

        // create paged list builder for datasource factory
        val livePagedBuilder = LivePagedListBuilder(logDataSourceFactory, pagedListConfig)

        // query for stream of transaction logs for specified day by retrieving livedata list
        return monarchy.findAllPagedWithChanges(logDataSourceFactory, livePagedBuilder)
    }

    override fun getTransactionFor(from: Date, to: Date, transactionType: TransactionType, pagedListConfig: PagedList.Config): LiveData<PagedList<TransactionLog>> {
        // get the start and end date in milliseconds
        val start = getMorningTimeInMillis(from)
        val end = getNightTimeInMillis(to)

        // query for stream of transaction logs for specified day by creating dataSource factory
        val logDataSourceFactory = monarchy.createDataSourceFactory { realm ->
            realm.where<TransactionLog>()
                    .equalTo("transactionType", transactionType.ordinal)
                    .greaterThan("time", start)
                    .lessThan("time", end)
                    .sort("time", Sort.DESCENDING)
        }

        // create paged list builder for datasource factory
        val livePagedBuilder = LivePagedListBuilder(logDataSourceFactory, pagedListConfig)

        // query for stream of transaction logs for specified day by retrieving livedata list
        return monarchy.findAllPagedWithChanges(logDataSourceFactory, livePagedBuilder)
    }

    override fun getTransactionFor(date: Date): LiveData<List<TransactionLog>> {

        // get the date range for current date as morning and midnight
        val (morning, midnight) = getDateRange(date)

        // query for stream of transaction logs for specified day by retrieving livedata list
        return monarchy.findAllCopiedWithChanges { realm ->
            realm.where<TransactionLog>()
                .greaterThan("time", morning)
                .lessThan("time", midnight)
                .sort("time", Sort.DESCENDING)
        }
    }

    override fun getTransactionFor(from: Date, to: Date, transactionType: TransactionType): LiveData<List<TransactionLog>> {
        // get the start and end date in milliseconds
        val start = getMorningTimeInMillis(from)
        val end = getNightTimeInMillis(to)

        // query for stream of transaction logs for specified day by retrieving livedata list
        return monarchy.findAllCopiedWithChanges { realm ->
            realm.where<TransactionLog>()
                    .equalTo("transactionType", transactionType.ordinal)
                    .greaterThan("time", start)
                    .lessThan("time", end)
                    .sort("time", Sort.DESCENDING)
        }
    }

    override fun getTransactionFor(from: Date, to: Date): LiveData<List<TransactionLog>> {
        // get the start and end date in milliseconds
        val start = getMorningTimeInMillis(from)
        val end = getNightTimeInMillis(to)

        // query for stream of transaction logs for specified day by retrieving livedata list
        return monarchy.findAllCopiedWithChanges { realm ->
            realm.where<TransactionLog>()
                    .greaterThan("time", start)
                    .lessThan("time", end)
                    .sort("time", Sort.DESCENDING)
        }
    }

    override fun getTransactionFor(date: Date, transactionType: TransactionType): LiveData<List<TransactionLog>> {
        // get the date range for current date as morning and midnight
        val (morning, midnight) = getDateRange(date)

        // query for stream of transaction logs for specified day by retrieving livedata list
        return monarchy.findAllCopiedWithChanges { realm ->
            realm.where<TransactionLog>()
                .equalTo("transactionType", transactionType.ordinal)
                .greaterThan("time", morning)
                .lessThan("time", midnight)
                .sort("time", Sort.DESCENDING)
        }
    }

    override fun getTransactionListFor(date: Date, transactionType: TransactionType): List<TransactionLog> {
        // get the date range for current date as morning and midnight
        val (morning, midnight) = getDateRange(date)

        // query for stream of transaction logs for specified day by retrieving livedata list
        return monarchy.fetchAllCopiedSync { realm ->
            realm.where<TransactionLog>()
                .equalTo("transactionType", transactionType.ordinal)
                .greaterThan("time", morning)
                .lessThan("time", midnight)
                .sort("time", Sort.DESCENDING)
        }
    }

    override fun getTransactionListFor(from: Date, to: Date, transactionType: TransactionType): List<TransactionLog> {
        // get the start and end date in milliseconds
        val start = getMorningTimeInMillis(from)
        val end = getNightTimeInMillis(to)

        // query for stream of transaction logs for specified day by retrieving livedata list
        return monarchy.fetchAllCopiedSync { realm ->
            realm.where<TransactionLog>()
                    .equalTo("transactionType", transactionType.ordinal)
                    .greaterThan("time", start)
                    .lessThan("time", end)
                    .sort("time", Sort.DESCENDING)
        }
    }

    private fun getDateRange(date: Date): DayTime {
        // calendar to generate first hour of day
        val morningCalendar = Calendar.getInstance()
        // get 12AM (00:00:00 AM) in the morning
        val morning = morningCalendar.apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // calendar to generate last hour of day
        val nightCalendar = Calendar.getInstance()
        // get midnight 11:59PM (23:59:60 PM) in the midnight
        val midnight = nightCalendar.apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        return DayTime(morning.timeInMillis, midnight.timeInMillis)
    }

    private fun getMorningTimeInMillis(date: Date): Long {
        // calendar to generate first hour of day
        val calendar = Calendar.getInstance()
        // get 12AM (00:00:00 AM) in the morning
        val time = calendar.apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        return time.timeInMillis
    }

    private fun getNightTimeInMillis(date: Date): Long {
        // calendar to generate last hour of day
        val calendar = Calendar.getInstance()
        // get midnight 11:59PM (23:59:00 AM) in the midnight
        val time = calendar.apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        return time.timeInMillis
    }

}