package com.interswitchng.smartpos.mockservices

import com.interswitchng.smartpos.old_shared.interfaces.library.KeyValueStore

class MockKeyValueStore: KeyValueStore {
    override fun saveString(key: String, value: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getString(key: String, default: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveNumber(key: String, value: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getNumber(key: String, default: Long): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}