package com.interswitchng.smartpos.shared.models.core

import com.google.gson.Gson
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.interfaces.library.UsbConnector


/**
 * A data model representing the the POS configuration for the current acquirer
 */
data class POSConfig(
        internal val alias: String,
        internal val clientId: String,
        internal val clientSecret: String,
        internal val merchantCode: String,
        internal val merchantTelephone: String = "",
        internal val environment: Environment = Environment.Test) {

    internal var usbConnector: UsbConnector? = null
    internal var purchaseConfig = PurchaseConfig(10, "tech@isw.dev.ng", IswLocal.NIGERIA)

    fun with(usb: UsbConnector): POSConfig {
        usbConnector = usb
        return this
    }

    fun withPurchaseConfig(config: PurchaseConfig): POSConfig {
        purchaseConfig = config
        return this
    }

    /**
     * Load config saved in local disk
     */
    internal fun loadConfig(keyStore: KeyValueStore) {
        val gson = Gson()
        val configString = keyStore.getString(KEY_PURCHASE_CONFIG, "")

        // ensure values were retrieved
        if (configString.isNotEmpty()) {
            val savedConfig = gson.fromJson(configString, PurchaseConfig::class.java)
            purchaseConfig = savedConfig
        }
    }


    /**
     * Save config to local disk
     */
    internal fun saveConfig(keyStore: KeyValueStore) {
        val gson = Gson()
        val configToSave = gson.toJson(purchaseConfig)
        keyStore.saveString(KEY_PURCHASE_CONFIG, configToSave)
    }

    companion object {
        const val KEY_PURCHASE_CONFIG = "key_purchase_config"
    }




}