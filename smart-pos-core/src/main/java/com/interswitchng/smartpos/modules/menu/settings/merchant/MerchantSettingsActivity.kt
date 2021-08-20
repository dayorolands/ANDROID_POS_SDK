package com.interswitchng.smartpos.modules.menu.settings.merchant

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.modules.menu.settings.SettingsViewModel
import com.interswitchng.smartpos.shared.activities.BaseMenuActivity
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import com.interswitchng.smartpos.shared.models.core.IswLocal
import com.interswitchng.smartpos.shared.models.core.PurchaseConfig
import com.interswitchng.smartpos.shared.utilities.DisplayUtils
import com.interswitchng.smartpos.shared.utilities.toast
import kotlinx.android.synthetic.main.isw_activity_merchant_settings.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MerchantSettingsActivity : BaseMenuActivity(), AdapterView.OnItemSelectedListener {

    private val iswPos: IswPos by inject()
    private val store: KeyValueStore by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.isw_activity_merchant_settings)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // set the text values
        setupTexts()

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
        } else if(item?.itemId == R.id.saveConfig) {
            saveConfig()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupTexts() {

        // setup sdk config texts
        // --- set min amount
        val config = iswPos.config.purchaseConfig
        val minAmount = DisplayUtils.getAmountString(config.minimumAmount.toDouble())
        etMinAmount.setText(minAmount)
        // --- set email
        etMerchantEmail.setText(config.vendorEmail)

        // --- set merchant local spinner adapter and values
        val currencies = IswLocal.values().map { it.name }
        val merchantLocalAdapter = ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, currencies)
        spLocal.adapter = merchantLocalAdapter
        spLocal.onItemSelectedListener = this
        // set the individual item view
        merchantLocalAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);


        // --- set selected value
        val selectedCurrency = currencies.indexOf(config.local.name)
        spLocal.setSelection(selectedCurrency)

    }

    private fun saveConfig() {
        // extract amount or toast error
        val minAmount = etMinAmount.text.toString().toDoubleOrNull()
                ?: return toast("Invalid minimum amount")


        // extract local
        val localName = spLocal.selectedItem as String
        val local = when (localName) {
            IswLocal.GHANA.name -> IswLocal.GHANA
            IswLocal.NIGERIA.name -> IswLocal.NIGERIA
            else -> return toast("Invalid merchant local")
        }


        // extract email or toast error
        val email = etMerchantEmail.text.trim().toString()
        // validate email
        val isValidEmail = !TextUtils.isEmpty(email)
                && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (!isValidEmail) toast("Invalid merchant email")


        // create new purchase config
        val newConfig = PurchaseConfig(minimumAmount = minAmount.toInt(),
                vendorEmail = email, local = local)

        // toast no config changes were applied
        if (newConfig == iswPos.config.purchaseConfig)
            toast("Config hasn't changed")


        // set and save the new config
        iswPos.config
                .withPurchaseConfig(newConfig)
                .saveConfig(store)

        toast("Config saved successfully!")
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) { }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, index: Int, l: Long) {
        // extract local
        val localName = spLocal.selectedItem as String
        val local = when (localName) {
            IswLocal.GHANA.name -> IswLocal.GHANA
            IswLocal.NIGERIA.name -> IswLocal.NIGERIA
            else -> return toast("Invalid merchant local")
        }
    }

}


