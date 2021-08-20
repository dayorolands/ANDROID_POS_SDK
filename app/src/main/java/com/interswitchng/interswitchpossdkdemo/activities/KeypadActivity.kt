package com.interswitchng.interswitchpossdkdemo.activities

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.interswitchng.interswitchpossdkdemo.utils.Keyboard
import com.interswitchng.interswitchpossdkdemo.R
import com.interswitchng.smartpos.IswPos.Companion.getInstance
import com.interswitchng.smartpos.IswPos.IswPaymentCallback
import com.interswitchng.smartpos.shared.errors.NotConfiguredException
import com.interswitchng.smartpos.shared.models.core.Transaction
import com.interswitchng.smartpos.shared.models.core.Transaction.Purchase
import com.interswitchng.smartpos.shared.models.core.TransactionType
import com.interswitchng.smartpos.shared.models.results.IswTransactionResult
import kotlinx.android.synthetic.main.activity_keypad.*
import java.text.NumberFormat

class KeypadActivity : AppCompatActivity(),
    Keyboard.KeyBoardListener, IswPaymentCallback {
    private val defaultAmount = "0.00"
    private var current = ""
    private var currentAmount = 0
    private var keyboard: Keyboard? = null
    private var transactionType: Transaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keypad)
        setSupportActionBar(findViewById(R.id.homeToolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        // set up selected transaction type
        val typeName = intent.extras?.getString(KEY_TYPE)!!
        val transactionTypeName = TransactionType.valueOf(typeName)

        transactionType = when {
            transactionTypeName === TransactionType.PreAuth -> Transaction.PreAuth
            transactionTypeName === TransactionType.Completion -> Transaction.Completion
            transactionTypeName === TransactionType.Refund -> Transaction.Refund
            transactionTypeName === TransactionType.Payments -> Transaction.Payments
            transactionTypeName === TransactionType.IFIS -> Transaction.IFIS
            transactionTypeName === TransactionType.CashBack -> Transaction.CashBack
            else -> Purchase(null)
        }

        setupUI()
    }

    private fun setupUI() {
        amount.text = "0.00"
        keyboard =
            Keyboard(this, this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toast(msg: String) {
        runOnUiThread { Toast.makeText(this@KeypadActivity, msg, Toast.LENGTH_LONG).show() }
    }

    override fun onTextChange(text: String) {
        if (text != current) {
            val text = if (text.isEmpty()) defaultAmount else text
            val cleanString = text.replace("[$,.]".toRegex(), "")
            val parsed = cleanString.toDouble()
            val numberFormat = NumberFormat.getInstance()
            numberFormat.minimumFractionDigits = 2
            numberFormat.maximumFractionDigits = 2
            val formatted = numberFormat.format(parsed / 100)
            currentAmount = Integer.valueOf(cleanString)
            amount.text = formatted
            current = cleanString
            keyboard!!.setText(cleanString)
        }
    }

    override fun onSubmit(text: String) {
        val enteredAmount = amount.text.toString()
        if (enteredAmount.isEmpty() || enteredAmount == defaultAmount) {
            toast("Amount value is required")
        } else {
            makePayment(currentAmount)
        }
    }

    private fun makePayment(amount: Int) {
        try {
            // trigger payment
            when(transactionType){
                Transaction.Payments-> getInstance().payBill(amount, this, "04385222", "1004124549","2348124888436", "okekechidalu17@gmail.com")
                else -> getInstance().pay(amount, this, transactionType!!)
            }
        } catch (e: NotConfiguredException) {
            toast("Pos has not been configured")
            Log.d("DEMO", e.message)
        }
    }

    override fun onUserCancel() {
        toast("User cancelled payment")
    }

    override fun onPaymentCompleted(result: IswTransactionResult) {
        Log.d("Demo", "" + result)

        // reset the amount back to default
        if (result.isSuccessful) onTextChange(defaultAmount)
        val message =
            if (result.responseCode == "00") "Payment completed successfully" else "Payment cancelled"
        toast(message)
    }

    companion object {
        private const val KEY_ENABLE_USB = "key_enable_usb"
        const val KEY_TYPE = "key_transaction_type"
        fun drawableToBitmap(drawable: Drawable): Bitmap {
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }
    }
}