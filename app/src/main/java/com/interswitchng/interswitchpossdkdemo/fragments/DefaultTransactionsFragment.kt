package com.interswitchng.interswitchpossdkdemo.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.interswitchng.interswitchpossdkdemo.POSApplication
import com.interswitchng.interswitchpossdkdemo.R
import com.interswitchng.interswitchpossdkdemo.activities.CashBackActivity
import com.interswitchng.interswitchpossdkdemo.activities.HomeActivity
import com.interswitchng.interswitchpossdkdemo.activities.KeypadActivity
import com.interswitchng.interswitchpossdkdemo.adapters.TransactionTypesAdapter
import com.interswitchng.interswitchpossdkdemo.models.TransactionTypes
//import com.interswitchng.interswitchpossdkdemo.utils.DateUtil
import com.interswitchng.smartpos.IswPos
import com.interswitchng.smartpos.shared.models.posconfig.PrintObject
import com.interswitchng.smartpos.shared.models.posconfig.PrintStringConfiguration
import com.interswitchng.smartpos.shared.models.results.IswPrintResult
import kotlinx.android.synthetic.main.fragment_default_transactions.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class DefaultTransactionsFragment : Fragment(), IswPos.IswPrinterCallback {

    private val parentActivity: HomeActivity get() = activity as HomeActivity

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_default_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get list of default transactions
        val defaultTransactions = TransactionTypes.getDefault(view.context)

        // set adapter for recycler view
        rvTransactionTypes.adapter = TransactionTypesAdapter(defaultTransactions) {

            when (it) {
                TransactionTypes.More -> {
                    parentActivity.showMore()
                }
                TransactionTypes.CashBack -> {
                    val intent = Intent(requireContext(), CashBackActivity::class.java)
                            .putExtra("TRANSACTION_TYPE", it.toTransactionType.name)

                    startActivity(intent)
                }
                TransactionTypes.Payments, TransactionTypes.Transfer -> Toast.makeText(parentActivity, "Coming soon!!!", Toast.LENGTH_SHORT).show()
                else -> {
                    // create intent with transaction type
                    val intent = Intent(requireContext(), KeypadActivity::class.java)
                            .putExtra(KeypadActivity.KEY_TYPE, it.toTransactionType.name)

                    // start demo activity
                    startActivity(intent)
                }
            }
        }

        testPrintBMP.setOnClickListener {
            //progressBar.visibility = View.VISIBLE
            val logo = ContextCompat.getDrawable(parentActivity, R.drawable.isw_logo_gtb)
            val bm = POSApplication.drawableToBitmap(logo)

            val printObjects = mutableListOf<PrintObject>()
            printObjects.add(PrintObject.Line)
            printObjects.add(PrintObject.BitMap(bm))
            printObjects.add(PrintObject.Data("Test Center PrintValue", PrintStringConfiguration(displayCenter = true)))
            printObjects.add(PrintObject.Data("Test Title PrintValue", PrintStringConfiguration(isTitle = true)))
            printObjects.add(PrintObject.Data("Test Bold PrintValue", PrintStringConfiguration(isBold = true)))

            val callback = this


            IswPos.getInstance().print(printObjects, callback)


        }

        testCanPrint.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            IswPos.getInstance().canPrint(this)
            Toast.makeText(context, "canPrintResponse", Toast.LENGTH_SHORT).show()
        }

        val formatter = SimpleDateFormat("EEE, MMM d", Locale.ROOT)
        tvDate.text = formatter.format(Date())

        //tvCompanyName.text = DateUtil.getGreeting()
    }

    override fun onError(result: IswPrintResult) {
       // progressBar.visibility = View.GONE
        Log.e("::PRINT", "App response, onError ${result.message}")
        Toast.makeText(context, "App response, onError ${result.message}", Toast.LENGTH_LONG).show()
    }

    override fun onPrintCompleted(result: IswPrintResult) {

        //progressBar.visibility = View.GONE

        Log.e("::PRINT", "App response, onPrintCompleted ${result.message}")
        //Toast.makeText(context, "App response, onPrintCompleted ${result.message}", Toast.LENGTH_LONG).show()
    }
}