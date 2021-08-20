package com.interswitchng.interswitchpossdkdemo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.interswitchng.interswitchpossdkdemo.R
import com.interswitchng.interswitchpossdkdemo.activities.CashBackActivity
import com.interswitchng.interswitchpossdkdemo.activities.HomeActivity
import com.interswitchng.interswitchpossdkdemo.activities.KeypadActivity
import com.interswitchng.interswitchpossdkdemo.adapters.TransactionTypesAdapter
import com.interswitchng.interswitchpossdkdemo.models.TransactionTypes
import kotlinx.android.synthetic.main.fragment_default_transactions.*

class MoreTransactionsFragment : Fragment() {

    private val parentActivity: HomeActivity get() = activity as HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more_transactions, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get list of default transactions
        val moreTransactions = TransactionTypes.getMore(view.context)

        // set adapter for recycler view
        rvTransactionTypes.adapter = TransactionTypesAdapter(moreTransactions) {

            if (it == TransactionTypes.More) {
                parentActivity.showMore()
            }
            else if(it == TransactionTypes.CashBack){
                val intent = Intent(requireContext(), CashBackActivity::class.java)
                        .putExtra("TRANSACTION_TYPE", it.toTransactionType.name)

                startActivity(intent)
            }
            else if( it == TransactionTypes.Payments || it ==  TransactionTypes.Transfer) {
                Toast.makeText(parentActivity, "Coming soon!!!", Toast.LENGTH_SHORT).show()
            } else {

                // create intent with transaction type

                val intent = Intent(requireContext(), KeypadActivity::class.java)
                        .putExtra(KeypadActivity.KEY_TYPE, it.toTransactionType.name)

                // start demo activity
                startActivity(intent)
            }
        }

    }
}