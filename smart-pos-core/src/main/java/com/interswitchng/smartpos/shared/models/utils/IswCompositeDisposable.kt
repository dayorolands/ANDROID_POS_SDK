package com.interswitchng.smartpos.shared.models.utils

import java.util.*

class IswCompositeDisposable {

    private val disposables = Stack<IswDisposable>()

    fun add(disposable: IswDisposable) {
        disposables.push(disposable)
    }

    fun dispose() {
        for (i in 0 until disposables.size) {
            val disposable = disposables.pop()
            disposable.dispose()
        }
    }
}