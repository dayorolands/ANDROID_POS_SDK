package com.interswitchng.smartpos.utils

import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.IdlingResource

object WaitUtils {

    private const val defaultWaitTime = 3000L

    private var idlingResource: IdlingResource? = null

    private val idlingRegistry = IdlingRegistry.getInstance()

    @JvmOverloads
    fun waitTime(waitingTime: Long = defaultWaitTime) {
        cleanupWaitTime()

        idlingResource = ElapsedTimeIdlingResource(waitingTime)
        idlingResource?.let { idlingRegistry.register(it) }
    }

    fun cleanupWaitTime() {
        idlingResource?.let { idlingRegistry.unregister(it) }
        idlingResource = null
    }
}