package com.interswitchng.smartpos.shared.models.core

import com.google.gson.Gson
import com.interswitchng.smartpos.BuildConfig
import com.interswitchng.smartpos.shared.Constants
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.utilities.Logger


/**
 * A data model representing the terminal information downloaded from the server, which
 * is then used to configure the any given POS terminal
 *
 * - terminalId: the specific id of a terminal in a merchant's store
 * - merchantId: the merchant's id
 * - countryCode: a code identifying the country that the terminal is processing transactions in
 * - currencyCode: a code indicating the currency of the country
 * - callHomeTimeInMin: an integer indicating the time (in minutes) required to continuously call home
 * - serverTimeoutInSec: an integer indicating the time (in seconds) to specify as a connection timeout
 */
data class TerminalInfo(
    var terminalId: String,
    var merchantId: String,
    val terminalId2: String,
    val merchantId2: String,
    val currencyCode2: String,
    val merchantNameAndLocation: String,
    val merchantCategoryCode: String,
    val merchantAddress:String = "",
    val countryCode: String,
    var currencyCode: String,
    val callHomeTimeInMin: Int,
    val serverTimeoutInSec: Int,
    var isKimono: Boolean = false,
    val capabilities: String? = null,
    val merchantAlias: String = "",
    val merchantCode: String = "",
    var serverUrl: String = Constants.ISW_KIMONO_URL,
    var serverIp: String = Constants.ISW_TERMINAL_IP,
    var serverPort: Int = BuildConfig.ISW_TERMINAL_PORT) {


    internal fun  persist(store: KeyValueStore): Boolean {
        // set default values
        if (serverUrl.isNullOrEmpty()) serverUrl = Constants.ISW_KIMONO_URL
        if (serverIp.isNullOrEmpty()) serverIp = Constants.ISW_TERMINAL_IP
        if (serverPort == 0) serverPort = BuildConfig.ISW_TERMINAL_PORT

        // get previous terminal info
        val prevInfo = get(store)

        // save only when config changed
        if (prevInfo != this) {
            val jsonString = Gson().toJson(this)
            store.saveString(PERSIST_KEY, jsonString)
            return true
        }

        return false
    }


    override fun toString(): String {
        return """
            | terminalId: $terminalId
            | merchantId: $merchantId
            | merchantNameAndLocation: $merchantNameAndLocation
            | merchantCategory: $merchantCategoryCode
            | currencyCode: $currencyCode
            | currencyCode2: $currencyCode2
            | callHomeTimeInMin: $callHomeTimeInMin
            | serverTimeoutInSec: $serverTimeoutInSec
        """.trimIndent()
    }


    companion object {
        private const val PERSIST_KEY = "terminal_data"

        internal fun get(store: KeyValueStore): TerminalInfo? {
            val jsonString = store.getString(PERSIST_KEY, "")
            return when (jsonString) {
                "" -> null
                else -> Gson().fromJson(jsonString, TerminalInfo::class.java)
            }
        }
    }

}

