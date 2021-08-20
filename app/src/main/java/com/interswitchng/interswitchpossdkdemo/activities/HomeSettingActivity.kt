package com.interswitchng.interswitchpossdkdemo.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.interswitchng.interswitchpossdkdemo.R
import com.interswitchng.interswitchpossdkdemo.adapters.TransactionTypesConfigAdapter
import com.interswitchng.interswitchpossdkdemo.models.TransactionTypes
import com.interswitchng.smartpos.shared.utilities.toast
import kotlinx.android.synthetic.main.activity_home_settings.*
import java.util.*

class HomeSettingActivity: AppCompatActivity() {

    private lateinit var defaultAdapter: TransactionTypesConfigAdapter
    private lateinit var othersAdapter: TransactionTypesConfigAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_settings)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // get list of default and other txn types
        val defaultList = TransactionTypes.getDefault(this).filter { it != TransactionTypes.More }
        val otherList = TransactionTypes.getMore(this)

        // set the adapter for each transaction type list
        defaultAdapter = TransactionTypesConfigAdapter(defaultList)
        othersAdapter = TransactionTypesConfigAdapter(otherList)

        // set the transaction types for each adapter
        rvDefaultTransactionTypes.adapter = defaultAdapter
        rvOtherTransactionTypes.adapter = othersAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.demo_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.save -> {
                saveTransactionConfig()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun saveTransactionConfig() {
        // get default options
        val defaultOptions =
            defaultAdapter.types.filter { it.isDefault } + othersAdapter.types.filter { it.isDefault }

        val otherOptions =
            defaultAdapter.types.filter { !it.isDefault } + othersAdapter.types.filter { !it.isDefault }

        // check if default options is valid
        val isDefaultValid = defaultOptions.size == 3

        if (!isDefaultValid) toast("Default transaction must be only 3!")
        else {
            TransactionTypes.saveDefault(this, defaultOptions)
            TransactionTypes.saveMore(this, otherOptions)
            finish()
        }
    }
}