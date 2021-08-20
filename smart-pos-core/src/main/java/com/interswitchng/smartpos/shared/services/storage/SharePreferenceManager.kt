package com.interswitchng.smartpos.shared.services.storage

import android.content.Context

internal class SharePreferenceManager(private val context: Context) {

    private fun getSharedPreference() = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    private fun getEditor() = getSharedPreference().edit()

    fun saveString(key: String, value: String) = getEditor().putString(key, value).commit()

    fun getString(key: String, default: String? = null): String? = getSharedPreference().getString(key, default)

    fun saveBoolean(key: String, value: Boolean) = getEditor().putBoolean(key, value).commit()

    fun getBoolean(key: String): Boolean = getSharedPreference().getBoolean(key,false)

    fun saveNumber(key: String, default: Long) = getEditor().putLong(key, default).commit()

    fun getNumber(key: String, default: Long): Long = getSharedPreference().getLong(key, default)

    fun removeValue(key: String) = getEditor().remove(key).commit()

    companion object {
        private const val SHARED_PREF_NAME = "ng.insterswitch.shared+preference+name"
    }
}