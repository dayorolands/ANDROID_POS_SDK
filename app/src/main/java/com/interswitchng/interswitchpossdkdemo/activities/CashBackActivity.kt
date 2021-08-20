package com.interswitchng.interswitchpossdkdemo.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.interswitchng.interswitchpossdkdemo.R
import com.interswitchng.interswitchpossdkdemo.fragments.DefaultTransactionsFragment
import com.interswitchng.interswitchpossdkdemo.utils.Keyboard
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.shared.errors.NotConfiguredException
import com.interswitchng.smartpos.shared.models.core.Transaction
import com.interswitchng.smartpos.shared.models.results.IswTransactionResult
import com.interswitchng.smartpos.shared.utilities.toast
import kotlinx.android.synthetic.main.activity_cash_back.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_keypad.*
import kotlinx.android.synthetic.main.activity_keypad.amount
import java.math.BigDecimal
import java.text.NumberFormat

class CashBackActivity : AppCompatActivity(), IswPos.IswPaymentCallback {

    private val defaultAmount = "0.00"
    private var current = ""
    private var currentAmount = 0

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_back)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
               // tvSample.setText("Text in EditText : "+s)
                /*if (s != current) {
                    val text = if (s.isEmpty()) defaultAmount else s
                    val cleanString = text.replace("[$,.]".toRegex(), "")
                    val parsed = cleanString.toDouble()
                    val numberFormat = NumberFormat.getInstance()
                    numberFormat.minimumFractionDigits = 2
                    numberFormat.maximumFractionDigits = 2
                    val formatted = numberFormat.format(parsed / 100)
                    currentAmount = Integer.valueOf(cleanString)
                    amount.text = cleanString//formatted
                    //current = cleanString
                    //keyboard!!.setText(cleanString)
                }*/
            }
        })

    }

    private fun setupUI() {
        amount.text = "0.00"
        //additionalAmount.text = "0.00"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onUserCancel() {
        toast("User cancelled payment")
    }

    override fun onPaymentCompleted(result: IswTransactionResult) {
        Log.d("Demo", "" + result)

        // reset the amount back to default
        //if (result.isSuccessful) onTextChange(defaultAmount)
        val message =
                if (result.responseCode == "00") "Payment completed successfully" else "Payment cancelled"
        toast(message)
    }

    fun doPay (view: View) {
        val enteredAmount = amount.text.toString()
        val enteredAdditionalAmount = additionalAmount.text.toString()
        if(enteredAmount.isBlank()) {
            toast("Amount value is required")
        }
        else if(enteredAdditionalAmount.isBlank()) {
            toast("Additional amount is required")
        }
        else {
            try {
                val formattedMinorAmount = (BigDecimal(enteredAmount) * BigDecimal(100)).toInt()
                val formattedAdditionalMinorAmount = (BigDecimal(enteredAdditionalAmount) * BigDecimal(100)).toInt()
                IswPos.getInstance().pay(formattedMinorAmount, this, Transaction.CashBack, 5, formattedAdditionalMinorAmount)
            }catch (e: NotConfiguredException) {
                toast("Pos has not been configured")
                Log.d("DEMO", e.message)
            }
        }
    }





}