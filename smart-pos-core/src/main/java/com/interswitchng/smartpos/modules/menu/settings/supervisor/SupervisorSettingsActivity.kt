package com.interswitchng.smartpos.modules.menu.settings.supervisor

import android.os.Bundle
import com.interswitchng.smartpos.BuildConfig
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.menu.settings.supervisor.enrollcard.EnrollCardBottomSheet
import com.interswitchng.smartpos.modules.menu.settings.supervisor.enrollcard.EnrolledCardFragment
import com.interswitchng.smartpos.shared.Constants
import com.interswitchng.smartpos.shared.activities.BaseMenuActivity
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.results.IswTransactionResult
import com.interswitchng.smartpos.shared.utilities.InputValidator
import com.interswitchng.smartpos.shared.utilities.SecurityUtils
import com.interswitchng.smartpos.shared.utilities.toast
import kotlinx.android.synthetic.main.isw_activity_supervisor_settings.*
import org.koin.android.ext.android.inject

class SupervisorSettingsActivity : BaseMenuActivity(), IswPos.IswPaymentCallback {

    private val store: KeyValueStore by inject()
    private lateinit var enrollmentSheet: EnrollCardBottomSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.isw_activity_supervisor_settings)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        // setup ui
        setupUI()
    }

    private fun setupUI() {

        btnChangePassword.setOnClickListener {
            // validate user input
            validateUserPassword()
        }

        btnEnrollCard.setOnClickListener {
            // create enrollment dialog
            createDialog()
        }
    }

    private fun createDialog() {
        // create enrollment dialog
        enrollmentSheet = EnrollCardBottomSheet.newInstance()

        // set enrollment action as card enrollment
        enrollmentSheet.setEnrollAction(EnrolledCardFragment.EnrollAction.EnrollCard)

        supportFragmentManager.beginTransaction()
            .replace(R.id.enrollmentSheet, enrollmentSheet)
            .commit()
    }

    override fun onBackPressed() {
        // check if bottom sheet is locked
        val isSheetLocked = this::enrollmentSheet.isInitialized && enrollmentSheet.isLocked

        // dismiss bottom sheet if its not locked
        if (!isSheetLocked) enrollmentSheet.dismiss()
        // else if dismissed bottom sheet, invoke default back-pressed
        else if (enrollmentSheet.isDismissed) super.onBackPressed()
    }


    private fun validateUserPassword() {
        // get old password and check validation
        val oldPassword = etOldPassword.text.toString()
        val oldPasswordValidator = InputValidator(oldPassword)
            .isNotEmpty().isExactLength(6).isNumber()

        // get new password and check validation
        val newPassword = etNewPassword.text.toString()
        val newPasswordValidator = InputValidator(newPassword)
            .isNotEmpty().isExactLength(6).isNumber()

        // confirm password if no validation error
        if (!oldPasswordValidator.hasError) {
            // get admin configured pin
            var existingPin = store.getString(Constants.KEY_ADMIN_PIN, "")
            if (existingPin.isEmpty()) existingPin = BuildConfig.ISW_DEFAULT_PIN

            // hash provided pin
            val providedPin = SecurityUtils.getSecurePassword(oldPassword)
            val isValidPin = providedPin == existingPin

            if (!isValidPin) {
                oldPasswordValidator.hasError = true
                oldPasswordValidator.message = "invalid password"
            } else if (isValidPin && !newPasswordValidator.hasError) {
                // if new and old passwords are valid
                // hash the new password
                val newPasswordHash = SecurityUtils.getSecurePassword(newPassword)
                // save the new password
                store.saveString(Constants.KEY_ADMIN_PIN, newPasswordHash)
                toast("New Password Saved")
            }
        }

        // show any errors
        if (oldPasswordValidator.hasError) tiOldPassword.error = oldPasswordValidator.message
        if (newPasswordValidator.hasError) tiNewPassword.error = newPasswordValidator.message
    }

    override fun onUserCancel() = finish()

    override fun onPaymentCompleted(result: IswTransactionResult) = finish()
}