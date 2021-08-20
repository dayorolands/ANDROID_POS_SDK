package com.interswitchng.smartpos.services

import com.interswitchng.smartpos.old_shared.models.utils.IswCompositeDisposable
import com.interswitchng.smartpos.old_shared.utilities.ThreadUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class ThreadUitlsTest {

    @Test
    fun `should kill all threads when disposable is called`() {

        val composite = IswCompositeDisposable()

        val currentCount = Thread.activeCount()

        composite.add(ThreadUtils.createExecutor {
            val someCondition = true
            while (someCondition && !it.isDisposed) {
                Thread.sleep(300)
                println("Some message from the while loop: 1")
            }
        })

        composite.add(ThreadUtils.createExecutor {
            val someCondition = true
            while (someCondition && !it.isDisposed) {
                Thread.sleep(300)
                println("Some message from the while loop: 2")
            }
        })

        composite.add(ThreadUtils.createExecutor {
            val someCondition = true
            while (someCondition && !it.isDisposed) {
                Thread.sleep(300)
                println("Some message from the while loop: 3")
            }
        })

        assertEquals(currentCount + 3, Thread.activeCount())

        println("All threads setup")
        Thread.sleep(1000)

        composite.dispose()

        Thread.sleep(300)
        assertEquals(currentCount, Thread.activeCount())
    }

    @Test
    fun `should kill all threads even when threads are complete disposable is called`() {

        val composite = IswCompositeDisposable()

        val currentCount = Thread.activeCount()

        composite.add(ThreadUtils.createExecutor {
            Thread.sleep(500)
            println("Some message from the while loop: 1")
        })

        composite.add(ThreadUtils.createExecutor {
            val someCondition = true
            while (someCondition && !it.isDisposed) {
                Thread.sleep(300)
                println("Some message from the while loop: 2")
            }
        })

        composite.add(ThreadUtils.createExecutor {
            val someCondition = true
            while (someCondition && !it.isDisposed) {
                Thread.sleep(300)
                println("Some message from the while loop: 3")
            }
        })

        assertEquals(currentCount + 3, Thread.activeCount())

        println("All threads setup")
        Thread.sleep(1000)

        composite.dispose()

        Thread.sleep(300)
        assertEquals(currentCount, Thread.activeCount())
    }

    @Test
    fun testDate() {

        val date = Date()
        val calendar = Calendar.getInstance()
        // get 12AM (00:00:00 AM) in the morning
        val morning = calendar.apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // get midnight 11:59PM (23:59:60 PM) in the midnight
        val midnight = calendar.apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 69)
            set(Calendar.MILLISECOND, 0)
        }


        val df = SimpleDateFormat("hh:mm:ss aa")

        println(df.format(morning.time))
        println(df.format(midnight.time))

    }
}