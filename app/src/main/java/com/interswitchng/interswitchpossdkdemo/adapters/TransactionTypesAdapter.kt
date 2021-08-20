package com.interswitchng.interswitchpossdkdemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.interswitchng.interswitchpossdkdemo.R
import com.interswitchng.interswitchpossdkdemo.models.TransactionTypes

class TransactionTypesAdapter(
    private val types: List<TransactionTypes>,
    private val onSelect: (TransactionTypes) -> Unit
) : RecyclerView.Adapter<TransactionTypesAdapter.TypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_transaction,
            parent,
            false
        )

        return TypeViewHolder(view)
    }

    override fun getItemCount(): Int = types.size

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        // bind holder to transaction type
        holder.bind(types[position])
    }



    inner class TypeViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val transactionIcon = view.findViewById<ImageView>(R.id.transactionIcon)
        private val transactionTitle = view.findViewById<TextView>(R.id.transactionTitle)
        private val transactionSubtitle = view.findViewById<TextView>(R.id.transactionSubtitle)


        fun bind(type: TransactionTypes) {
            transactionIcon.setImageResource(type.icon)
            transactionTitle.text = type.title
            transactionSubtitle.text = type.subtitle

            // set the click listener
            itemView.setOnClickListener {
                onSelect(type)
            }
        }
    }
}
