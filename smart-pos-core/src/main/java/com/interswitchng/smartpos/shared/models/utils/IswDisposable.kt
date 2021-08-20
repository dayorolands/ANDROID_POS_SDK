package com.interswitchng.smartpos.shared.models.utils

class IswDisposable {

    var isDisposed = false

    private val disposeListeners = ArrayList<Runnable>()

    fun onDispose(runnable: Runnable) {
        disposeListeners.add(runnable)
    }

    fun onDispose(runnable: () -> Unit) {
        disposeListeners.add(Runnable { runnable() })
    }

    fun dispose(): Boolean {
        disposeListeners.forEach { it.run() }
        isDisposed = true
        return isDisposed
    }
}