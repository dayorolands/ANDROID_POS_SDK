package com.interswitchng.smartpos.modules.menu.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.interswitchng.smartpos.BuildConfig
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.menu.settings.merchant.MerchantSettingsActivity
import com.interswitchng.smartpos.modules.menu.settings.supervisor.SupervisorSettingsActivity
import com.interswitchng.smartpos.modules.menu.settings.terminal.TerminalSettingsActivity
import com.interswitchng.smartpos.shared.Constants
import com.interswitchng.smartpos.shared.activities.BaseMenuActivity
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.utilities.SecurityUtils
import com.interswitchng.smartpos.shared.utilities.toast
import kotlinx.android.synthetic.main.isw_activity_settings_options.*
import org.koin.android.ext.android.inject

internal class SettingsOptionsActivity : BaseMenuActivity(), View.OnClickListener {

    private lateinit var pinDialog: PinDialogFragment
    private val store: KeyValueStore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.isw_activity_settings_options)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // attach click listeners
        arrayOf(
            btnMerchantSettings,
            btnSupervisorSettings,
            btnTerminalSettings
        ).forEach { it.setOnClickListener(this) }

    }

    override fun onClick(btn: View) {

        if (!::pinDialog.isInitialized)
            pinDialog = PinDialogFragment()

        // set successful pin input listener
        pinDialog.onSuccess {

            // get require activity
            val clazz = when (btn) {
                btnTerminalSettings -> TerminalSettingsActivity::class.java
                btnSupervisorSettings -> SupervisorSettingsActivity::class.java
                else -> MerchantSettingsActivity::class.java
            }

            // create intent and start activity
            val intent = Intent(this, clazz)
            startActivity(intent)
        }


        pinDialog.onValidate { pin ->
            // get admin configured pin
            var existingPin = store.getString(Constants.KEY_ADMIN_PIN, "")
            if (existingPin.isEmpty()) existingPin = BuildConfig.ISW_DEFAULT_PIN

            // hash provided pin
            var providedPin = SecurityUtils.getSecurePassword(pin)
            val isValid = providedPin == existingPin

            if (isValid) toast("PIN OK")
            else toast("Invalid PIN")

            isValid
        }

        // show pin dialog
        pinDialog.show(supportFragmentManager, pinDialog.tag)
    }
}