package com.interswitchng.smartpos.shared.services.storage

import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.utilities.Logger

internal class KeyValueStoreImpl(private val prefManager: SharePreferenceManager): KeyValueStore {

    val logger = Logger.with("KeyValueStoreImpl")

    override fun saveString(key: String, value: String) {
        val result = prefManager.saveString(key, value)
        logger.log("saveString: $result")
    }

    override fun getString(key: String, default: String): String {
        return prefManager.getString(key, default) ?: ""
    }

    override fun saveNumber(key: String, value: Long) {
        prefManager.saveNumber(key, value)
    }

    override fun getNumber(key: String, default: Long): Long {
        return prefManager.getNumber(key, default)
    }


    override fun saveBoolean(key: String, value: Boolean) {
        prefManager.saveBoolean(key, value)
    }

    override fun getBoolean(key: String): Boolean {
        return prefManager.getBoolean(key)
    }
}