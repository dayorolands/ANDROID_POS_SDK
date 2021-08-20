package com.interswitchng.smartpos.shared.viewmodel

import androidx.lifecycle.ViewModel
import com.interswitchng.smartpos.shared.utilities.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

internal abstract class BaseViewModel(tag: String? = null) : ViewModel() {

    private val job = Job()
    protected val uiScope = CoroutineScope(Dispatchers.Main + job)
    protected val ioScope = uiScope.coroutineContext + Dispatchers.IO

    protected val logger by lazy {
        val classTag = tag ?: this::class.java.simpleName
        Logger.with(classTag)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}