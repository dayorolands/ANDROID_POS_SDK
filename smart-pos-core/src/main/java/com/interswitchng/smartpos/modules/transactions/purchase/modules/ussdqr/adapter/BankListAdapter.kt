package com.interswitchng.smartpos.modules.transactions.purchase.modules.ussdqr.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.Constants
import com.interswitchng.smartpos.shared.IswConstants
import com.interswitchng.smartpos.shared.models.transaction.ussdqr.response.Bank
import com.squareup.picasso.Picasso


internal class BankListAdapter(
    private var banks: List<Bank>,
    private var tapListener: (Bank) -> Unit
) : BaseAdapter(), SpinnerAdapter {


    fun getTitle(position: Int) = when (position) {
        0 -> "Select a bank"
        else -> getItem(position - 1).name
    }

    override fun getCount() = banks.size + 1

    override fun getItem(position: Int): Bank {
        return banks[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

        // get or inflate view
        val view = convertView ?: LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.isw_list_item_bank,
                parent,
                false
            )

        // get the title at position
        val bankName = getTitle(position)

        // get the text view
        val textView = view.findViewById<TextView>(R.id.bankName)
        textView.text = bankName
        return textView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val context = parent.context

        // get the view
        val view = LayoutInflater
            .from(context)
            .inflate(
                R.layout.isw_list_item_bank,
                parent,
                false
            )

        val textView = view.findViewById<TextView>(R.id.bankName)
        val bankImageView = view.findViewById<ImageView>(R.id.bankImageView)

        if (position == 0) {
            // get the bank at position
            val bankName = getTitle(position)
            // set the bank name
            textView.text = bankName

            // hide image for default option
            bankImageView.visibility = View.GONE
        } else {
            // get the bank
            val bank = getItem(position - 1)
            textView.text = bank.name

            // load the bank image
            val baseUrl = Constants.ISW_IMAGE_BASE_URL
            val imageUrl = "${baseUrl}banks/${bank.code}.png"

            // load image
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.isw_bank_placeholder)
                .error(R.drawable.isw_bank_placeholder)
                .fit()
                .into(bankImageView)

            // show image for default option
            bankImageView.visibility = View.VISIBLE
        }

        return view
    }
}