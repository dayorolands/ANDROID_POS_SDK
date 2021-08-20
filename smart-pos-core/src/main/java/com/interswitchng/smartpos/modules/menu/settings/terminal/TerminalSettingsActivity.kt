package com.interswitchng.smartpos.modules.menu.settings.terminal

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.google.gson.Gson
import com.interswitchng.smartpos.BuildConfig
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.menu.settings.SettingsViewModel
import com.interswitchng.smartpos.modules.menu.settings.merchant.MerchantSettingsActivity
import com.interswitchng.smartpos.shared.Constants
import com.interswitchng.smartpos.shared.activities.BaseMenuActivity
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.services.kimono.models.AllTerminalInfo
import com.interswitchng.smartpos.shared.services.kimono.models.TerminalInfoBySerials
import com.interswitchng.smartpos.shared.services.utils.DateUtils
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import com.interswitchng.smartpos.shared.utilities.DialogUtils
import com.interswitchng.smartpos.shared.utilities.DisplayUtils
import com.interswitchng.smartpos.shared.utilities.InputValidator
import com.interswitchng.smartpos.shared.utilities.toast
import kotlinx.android.synthetic.main.isw_activity_terminal_settings.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class TerminalSettingsActivity : BaseMenuActivity() {

    private val store: KeyValueStore by inject()
    private val settingsViewModel: SettingsViewModel by viewModel()

    private val alert by lazy {
        DialogUtils.getAlertDialog(this)
                .setTitle("Invalid Configuration")
                .setMessage("The configuration contains invalid parameters, please fix the errors and try saving again")
                .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.isw_activity_terminal_settings)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        // setup button listeners
        setupButtons()
        // set the text values
        setupTexts()

        // observe view model
        with(settingsViewModel) {
            val owner = { lifecycle }

            // observe key download status
            keysDownloadSuccess.observe(owner) {
                it?.apply(::keysDownloaded)
            }

            // observe config download status
            configDownloadSuccess.observe(owner) {
                it?.apply(::terminalConfigDownloaded)
            }

            merchantDetailsDownloadSuccess.observe(owner) {
                it?.apply(::merchantDetailsDownloaded)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.isw_menu_settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // set back click to go back
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        } else if (item?.itemId == R.id.saveConfig) {
            saveConfig()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupButtons() {
        // function validate nibbs request config
        val isValidNibbsRequest: (String, String, String) -> Boolean = { terminalId, ip, port ->

            // check validity of all fields
            val invalidTerminalId = InputValidator(terminalId)
                    .isNotEmpty().isAlphaNumeric()
                    .isExactLength(8)

            val invalidServerIp = InputValidator(ip).isNotEmpty().isValidIp()

            val invalidServerPort = InputValidator(port).isNotEmpty()
                    .isNumber().hasMaxLength(5)
                    .isNumberBetween(0, 65535)


            // show all error on the page
            if (invalidTerminalId.hasError) etTerminalId.error = invalidTerminalId.message
            if (invalidServerIp.hasError) etServerIP.error = invalidServerIp.message
            if (invalidServerPort.hasError) etServerPort.error = invalidServerPort.message


            // ensure no parameter is invalid
            !(invalidTerminalId.hasError
                    && invalidServerIp.hasError
                    && invalidServerPort.hasError)
        }

        // set up buttons
        btnDownloadKeys.setOnClickListener {

            // get fields
            val terminalID: String = etTerminalId.text.toString()
            val serverIp: String = etServerIP.text.toString()
            val serverPort: String = etServerPort.text.toString()

            // check validity
            val isValid = isValidNibbsRequest(terminalID, serverIp, serverPort)

            if (isValid) {
                // disable and hide button
                btnDownloadKeys.isEnabled = false
                btnDownloadKeys.visibility = View.GONE

                // set the text of keys
                tvKeys.text = getString(R.string.isw_title_downloading_keys)
                // show progress bar
                progressKeyDownload.visibility = View.VISIBLE
                // hide download date
                tvKeyDate.visibility = View.GONE

                // trigger download keys
                settingsViewModel.downloadKeys(terminalID, serverIp, serverPort.toInt(), switchKimono.isChecked)
            }
        }

        btnDownloadTerminalConfig.setOnClickListener {

            // get fields
            val terminalID: String = etTerminalId.text.toString()
            val serverIp: String = etServerIP.text.toString()
            val serverPort: String = etServerPort.text.toString()

            // check validity
            val isValid = isValidNibbsRequest(terminalID, serverIp, serverPort)

            // validate terminal id
            if (isValid) {
                // disable and hide button
                btnDownloadTerminalConfig.isEnabled = false
                btnDownloadTerminalConfig.visibility = View.GONE

                // set the text of terminal config
                tvTerminalInfo.text = getString(R.string.isw_title_downloading_terminal_config)
                // show progress bar
                progressTerminalDownload.visibility = View.VISIBLE
                // hide download date
                tvTerminalInfoDate.visibility = View.GONE

                // trigger download terminal config
                settingsViewModel.downloadTerminalConfig(terminalID, serverIp, serverPort.toInt(), switchKimono.isChecked)
            }
        }

        btnUploadConfig.setOnClickListener {
            // create intent to choose file
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "text/xml"
            }

            // choose config file
            startActivityForResult(intent,
                    RC_FILE_READ
            )
        }

        switchKimono.setOnCheckedChangeListener { button, _ ->

            // hide error messages for non required fields
            if (button.isChecked) {
                // ip and port not required if kimono
                etServerPort.error = null
                etServerIP.error = null

                // hide server and port fields
                etServerPort.isEnabled = false
                etServerIP.isEnabled = false

                etTerminalId2.isEnabled = true
                etMerchantId2.isEnabled = true
                etCurrencyCode2.isEnabled = true

                // show server url field
                etServerUrl.isEnabled = true
            } else {
                // server not required if not kimono
                etServerUrl.error = null
                // hide server url field
                etServerUrl.isEnabled = false

                etTerminalId2.isEnabled = false
                etMerchantId2.isEnabled = false
                etCurrencyCode2.isEnabled = false

                etTerminalId2.error = null
                etMerchantId2.error = null
                etCurrencyCode2.error = null

                // show server and port fields
                etServerPort.isEnabled = true
                etServerIP.isEnabled = true
            }

            // set the terminal-info download container based on kimono flag
            terminalInfoDownloadContainer.visibility = if (button.isChecked) View.GONE else View.VISIBLE
            terminalId2Container.visibility =
                if (button.isChecked) View.VISIBLE else View.GONE
            merchantId2Container.visibility =
                if (button.isChecked) View.VISIBLE else View.GONE
            currencyCode2Container.visibility =
                if (button.isChecked) View.VISIBLE else View.GONE
        }

        btnDownloadMerchantDetails.setOnClickListener {

            btnDownloadMerchantDetails.isEnabled = false
            btnDownloadMerchantDetails.visibility = View.GONE

            tvMerchantDetails.text = getString(R.string.isw_title_downloading_merchant_details)

            progressMerchantDetailsDownload.visibility = View.VISIBLE

            tvMerchantDetailsDate.visibility = View.GONE

            settingsViewModel.downloadMerchantDetails()
        }
    }

    private fun setupTexts(terminalInfo: TerminalInfo? = TerminalInfo.get(store)) {
        val terminalDate = store.getNumber(KEY_DATE_TERMINAL, -1)
        val keysDate = store.getNumber(KEY_DATE_KEYS, -1)
        val merchantDetailsDate = store.getNumber(KEY_DATE_MERCHANT_DETAILS, -1)

        if (terminalDate != -1L) {
            val date = Date(terminalDate)
            val dateStr = DateUtils.timeOfDateFormat.format(date)
            tvTerminalInfoDate.text = getString(R.string.isw_title_date, dateStr)
            tvTerminalInfo.text = getString(R.string.isw_title_terminal_config_downloaded)
        } else {
            val message = "No terminal configuration"
            tvTerminalInfoDate.text = getString(R.string.isw_title_date, message)
            tvTerminalInfo.text = getString(R.string.isw_title_download_terminal_configuration)
        }

        if (keysDate != -1L) {
            val date = Date(terminalDate)
            val dateStr = DateUtils.timeOfDateFormat.format(date)
            tvKeyDate.text = getString(R.string.isw_title_date, dateStr)
            tvKeys.text = getString(R.string.isw_title_keys_downloaded)
        } else {
            val message = "No keys"
            tvKeyDate.text = getString(R.string.isw_title_date, message)
            tvKeys.text = getString(R.string.isw_title_download_keys)
        }

        if (merchantDetailsDate != -1L) {
            val date = Date(merchantDetailsDate)
            val dateStr = DateUtils.timeOfDateFormat.format(date)
            tvMerchantDetailsDate.text = getString(R.string.isw_title_date, dateStr)
            tvMerchantDetails.text = getString(R.string.isw_title_merchant_details_downloaded)
        } else {
            val message = "No merchant details"
            tvMerchantDetailsDate.text = getString(R.string.isw_title_date, message)
            tvMerchantDetails.text = getString(R.string.isw_title_download_merchant_details)
        }

        // set up field texts with keyTerminalInfo
        terminalInfo?.apply {
            // terminal config
            etTerminalId.setText(terminalId)
            etTerminalId2.setText(terminalId2)
            etMerchantId.setText(merchantId)
            etMerchantId2.setText(merchantId2)
            etMerchantCategoryCode.setText(merchantCategoryCode)
            etMerchantNameAndLocation.setText(merchantNameAndLocation)
            etCountryCode.setText(countryCode)
            etCurrencyCode.setText(currencyCode)
            etCurrencyCode2.setText(currencyCode2)
            etCallHomeTime.setText(callHomeTimeInMin.toString())
            etServerTimeout.setText(serverTimeoutInSec.toString())
            etCapabilities.setText(capabilities)
            etMerchantAlias.setText(merchantAlias)
            etMerchantCode.setText(merchantCode)
            switchKimono.isChecked = isKimono
        }
        val serverIp = terminalInfo?.serverIp ?: Constants.ISW_TERMINAL_IP
        val serverPort = terminalInfo?.serverPort ?: BuildConfig.ISW_TERMINAL_PORT
        val serverUrl = terminalInfo?.serverUrl ?: Constants.ISW_KIMONO_URL


        // server config
        etServerIP.setText(serverIp)
        etServerPort.setText(serverPort.toString())
        etServerUrl.setText(serverUrl)
    }


    private fun keysDownloaded(isSuccessful: Boolean) {
        // enable and show button
        btnDownloadKeys.isEnabled = true
        btnDownloadKeys.visibility = View.VISIBLE
        // hide progress bar
        progressKeyDownload.visibility = View.GONE
        // show download date
        tvKeyDate.visibility = View.VISIBLE


        if (isSuccessful) {
            // get and store date
            val date = Date()
            store.saveNumber(KEY_DATE_KEYS, date.time)

            // set the texts
            val dateStr = DisplayUtils.getIsoString(date)
            tvKeyDate.text = getString(R.string.isw_title_date, dateStr)
            tvKeys.text = getString(R.string.isw_title_keys_downloaded)

            // set the drawable and color
            btnDownloadKeys.setImageResource(R.drawable.isw_ic_check)
            val color = ContextCompat.getColor(this, R.color.iswColorSuccess)
            ImageViewCompat.setImageTintList(btnDownloadKeys, ColorStateList.valueOf(color))

            // save config if its kimono
            if (switchKimono.isChecked) saveConfig()
        } else {
            val message = "No keys downloaded"
            tvKeyDate.text = getString(R.string.isw_title_date, message)
            tvKeys.text = getString(R.string.isw_title_error_downloading_keys)

            // set the drawable and color
            btnDownloadKeys.setImageResource(R.drawable.isw_ic_error)
            val color = ContextCompat.getColor(this, R.color.iswColorError)
            ImageViewCompat.setImageTintList(btnDownloadKeys, ColorStateList.valueOf(color))
        }

    }

    private fun terminalConfigDownloaded(isSuccessful: Boolean) {
        // enable and show button
        btnDownloadTerminalConfig.isEnabled = true
        btnDownloadTerminalConfig.visibility = View.VISIBLE
        // hide progress bar
        progressTerminalDownload.visibility = View.GONE
        // show download date
        tvTerminalInfoDate.visibility = View.VISIBLE


        if (isSuccessful) {
            // get and store date
            val date = Date()
            store.saveNumber(KEY_DATE_TERMINAL, date.time)

            // setup text
            setupTexts()

            // set the drawable and color
            btnDownloadTerminalConfig.setImageResource(R.drawable.isw_ic_check)
            val color = ContextCompat.getColor(this, R.color.iswColorSuccess)
            ImageViewCompat.setImageTintList(btnDownloadTerminalConfig, ColorStateList.valueOf(color))
        } else {
            val message = "No terminal configuration"
            tvTerminalInfoDate.text = getString(R.string.isw_title_date, message)
            tvTerminalInfo.text = getString(R.string.isw_title_error_downloading_terminal_config)

            // set the drawable and color
            btnDownloadTerminalConfig.setImageResource(R.drawable.isw_ic_error)
            val color = ContextCompat.getColor(this, R.color.iswColorError)
            ImageViewCompat.setImageTintList(btnDownloadTerminalConfig, ColorStateList.valueOf(color))
        }
    }

    // function to extract terminal info form input elements
    private fun getTerminalInfo(): TerminalInformation {
        return TerminalInformation()
                .apply {
                    terminalId = etTerminalId.getString()
                    terminalId2 = etTerminalId2.getString()
                    merchantId = etMerchantId.getString()
                    merchantId2 = etMerchantId2.getString()
                    merchantCategoryCode = etMerchantCategoryCode.getString()
                    merchantNameAndLocation = etMerchantNameAndLocation.getString()
                    countryCode = etCountryCode.getString()
                    currencyCode = etCurrencyCode.getString()
                    callHomeTimeInMin = etCallHomeTime.getString()
                    serverTimeoutInSec = etServerTimeout.getString()
                    serverIp = etServerIP.getString()
                    serverPort = etServerPort.getString()
                    serverUrl = etServerUrl.getString()
                    merchantAlias = etMerchantAlias.getString()
                    merchantCode = etMerchantCode.getString()
                    isKimono = switchKimono.isChecked
                    if (isKimono.apply {
                            currencyCode2 = etCurrencyCode2.getString()
                            //ensure that currency code 2 is not empty
                            if (currencyCode2.isEmpty()){
                                toast("Currency Code 2 cannot be empty")
                            }
                        })
                    // only set capabilities if it was provided
                    etCapabilities.getString().apply {
                        capabilities = if (this.isNotEmpty()) this else null
                    }

                }
    }

    private fun setTerminalInfoError(errorInfo: TerminalInformation) {
        errorInfo.apply {
            if (terminalId.isNotEmpty()) tiTerminalId.error = terminalId
            if (terminalId2.isNotEmpty()) tiTerminalId2.error = terminalId2
            if (merchantId.isNotEmpty()) tiMerchantId.error = merchantId
            if (merchantId2.isNotEmpty()) tiMerchantId2.error = merchantId2
            if (merchantCategoryCode.isNotEmpty()) tiMerchantCategoryCode.error = merchantCategoryCode
            if (merchantNameAndLocation.isNotEmpty()) tiMerchantNameAndLocation.error = merchantNameAndLocation
            if (countryCode.isNotEmpty()) tiCountryCode.error = countryCode
            if (currencyCode.isNotEmpty()) tiCurrencyCode.error = currencyCode
            if (currencyCode2.isNotEmpty()) tiCurrencyCode2.error = currencyCode2
            if (callHomeTimeInMin.isNotEmpty()) tiCallHomeTime.error = callHomeTimeInMin
            if (serverTimeoutInSec.isNotEmpty()) tiServerTimeout.error = serverTimeoutInSec
            if (serverIp.isNotEmpty()) tiServerIP.error = serverIp
            if (capabilities?.isNotEmpty() == true) tiCapabilities.error = capabilities
            if (serverPort.isNotEmpty()) tiServerPort.error = serverPort
            if (serverUrl.isNotEmpty()) tiServerUrl.error = serverUrl
        }

        alert.show()
    }

    private fun saveConfig(terminalInfo: TerminalInformation = getTerminalInfo()) {
        // ensure extracted information is valid
        if (terminalInfo.isValid) {
            // check if config parameters changed
            val isNew = terminalInfo.toTerminalInfo.persist(store)

            // toast based on status
            if (isNew) toast("Config saved successfully!")
            else toast("Config has not changed!")
        } else {
            setTerminalInfoError(terminalInfo.error)
            toast("Error: Invalid configuration loaded into terminal")
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == RC_FILE_READ) {

            data?.data?.also { uri ->
                contentResolver.openInputStream(uri)?.use {
                    // extract and save terminal info
                    val terminalInfo = settingsViewModel.getTerminalInformation(it)
                    toast("Config file loaded successfully")
                    // set text for loaded information
                    setupTexts(terminalInfo.toTerminalInfo)
                    // save current config
                    saveConfig(terminalInfo)
                }
            }

        } else super.onActivityResult(requestCode, resultCode, data)
    }

    private fun EditText.getString(): String {
        return this.text.toString()
    }

    private fun merchantDetailsDownloaded(response: AllTerminalInfo) {
        // enable and show button
        btnDownloadMerchantDetails.isEnabled = true
        btnDownloadMerchantDetails.visibility = View.VISIBLE
        // hide progress bar
        progressMerchantDetailsDownload.visibility = View.GONE
        // show download date
        tvMerchantDetailsDate.visibility = View.VISIBLE


        if (response.responseCode == IsoUtils.OK && response.terminalInfoBySerials != null) {
            // get and store date
            val date = Date()
            store.saveNumber(KEY_DATE_MERCHANT_DETAILS, date.time)

            // set the texts
            val dateStr = DisplayUtils.getIsoString(date)
            tvMerchantDetailsDate.text = getString(R.string.isw_title_date, dateStr)
            tvMerchantDetails.text = getString(R.string.isw_title_merchant_details_downloaded)

            // set the drawable and color
            btnDownloadMerchantDetails.setImageResource(R.drawable.isw_ic_check)
            val color = ContextCompat.getColor(this, R.color.iswColorSuccess)
            ImageViewCompat.setImageTintList(btnDownloadMerchantDetails, ColorStateList.valueOf(color))

            //save merchant details
            saveMerchantDetails(store, response)

            //get terminalInfo from merchant details
            setupTexts(getTerminalInfoFromMerchantDetails(response.terminalInfoBySerials!!).toTerminalInfo)
            // save config
            saveConfig()
        } else {
            val message = "Merchant details not downloaded"
            tvMerchantDetailsDate.text = getString(R.string.isw_title_date, message)
            tvMerchantDetails.text = getString(R.string.isw_title_error_downloading_keys)

            // set the drawable and color
            btnDownloadMerchantDetails.setImageResource(R.drawable.isw_ic_error)
            val color = ContextCompat.getColor(this, R.color.iswColorError)
            ImageViewCompat.setImageTintList(btnDownloadMerchantDetails, ColorStateList.valueOf(color))
        }

    }

    private fun getTerminalInfoFromMerchantDetails(data: TerminalInfoBySerials): TerminalInformation {
        return TerminalInformation().apply {
            terminalId = data.terminalCode
            merchantId = data.merchantId
            merchantCategoryCode = data.merchantCategoryCode
            merchantNameAndLocation = data.cardAcceptorNameLocation
            countryCode = Constants.COUNTRY_CODE
            currencyCode = Constants.CURRENCY_CODE
            callHomeTimeInMin = Constants.CALL_HOME_TIME_IN_MIN
            serverTimeoutInSec = Constants.SERVER_TIMEOUT_IN_SEC
            serverIp = Constants.ISW_TERMINAL_IP
            serverPort = Constants.ISW_TERMINAL_PORT.toString()
            serverUrl = Constants.ISW_KIMONO_URL
            isKimono = switchKimono.isChecked
            capabilities = Constants.TERMINAL_CAPABILITIES
        }
    }

    companion object {
        const val KEY_DATE_TERMINAL = "key_download_terminal_date"
        const val KEY_DATE_KEYS = "key_download_key_date"
        const val RC_FILE_READ = 49239
        private const val KEY_DATE_MERCHANT_DETAILS = "key_download_merchant_details_date"
        private const val KEY_MERCHANT_DETAILS = "key_download_merchant_details"

        private fun saveMerchantDetails(store: KeyValueStore, allTerminalInfo: AllTerminalInfo) {
            val jsonString = Gson().toJson(allTerminalInfo)
            store.saveString(KEY_MERCHANT_DETAILS, jsonString)

        }
    }
}