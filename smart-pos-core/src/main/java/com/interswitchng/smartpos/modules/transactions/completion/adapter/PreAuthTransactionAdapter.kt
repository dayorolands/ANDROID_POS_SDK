package com.interswitchng.smartpos.modules.transactions.completion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.models.transaction.IswPaymentInfo
import com.interswitchng.smartpos.shared.models.transaction.TransactionLog
import com.interswitchng.smartpos.shared.models.transaction.currencyType
import com.interswitchng.smartpos.shared.services.utils.DateUtils
import com.interswitchng.smartpos.shared.services.utils.IsoUtils
import com.interswitchng.smartpos.shared.utilities.DisplayUtils
import java.util.*

internal class PreAuthTransactionAdapter(private val onSelect: (TransactionLog) -> Unit) : PagedListAdapter<TransactionLog, RecyclerView.ViewHolder>(
    diffCallback
) {

    private var loading: Boolean? = null

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        // inflate view based on type
        val view = if (type == TYPE_LOADING) inflater.inflate(R.layout.isw_list_item_loading, parent, false)
        else inflater.inflate(R.layout.isw_list_item_transaction, parent, false)

        // return view holder based on type
        return if (type == TYPE_LOADING) LoadingViewHolder(view)
        else TransactionViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is LoadingViewHolder -> viewHolder.bind(loading ?: false)
            is TransactionViewHolder -> viewHolder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        // has extra row if its loading
        val hasExtraRow = loading != false
        // is loading if hasExtra row and position is at last item
        val isLoading = hasExtraRow && position == itemCount - 1

        // return view type based on loading status
        return if (isLoading) TYPE_LOADING
        else TYPE_ITEM
    }

    override fun onCurrentListChanged(currentList: PagedList<TransactionLog>?) {
        super.onCurrentListChanged(currentList)

        // cancel loading progress bar
        setLoadingStatus(false)
    }


    private fun setLoadingStatus(newStatus: Boolean?) {
        // get previous loading state
        val prevLoadingStatus = loading
        val wasLoading = prevLoadingStatus == true

        // check next loading state
        loading = newStatus
        val isLoading = loading == true

        // react to extra data flag changing
        // between loading updates
        if (wasLoading != isLoading) {
            // if it previously was loading data
            // and now has loaded data, notify [loader] item removed
            if (wasLoading) notifyItemRemoved(itemCount)
            // else if it previously wasn't loading data
            // and now it has more data, notify [loader] item added
            else notifyItemInserted(itemCount)
        } else if (isLoading && prevLoadingStatus != loading) {
            notifyItemChanged(itemCount)
        }
    }


    inner class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvAmount: TextView = view.findViewById(R.id.tvAmount)
        private val tvTxnType: TextView = view.findViewById(R.id.tvTransactionType)
        private val tvPaymentType: TextView = view.findViewById(R.id.tvPaymentType)
        private val tvDate: TextView = view.findViewById(R.id.tvDate)
        private val statusContainer: CardView = view.findViewById(R.id.statusContainer)
        private val statusText: TextView = view.findViewById(R.id.tvStatus)


        fun bind(txn: TransactionLog?) {
            txn?.toResult()?.apply {
                if (this.currencyType == IswPaymentInfo.CurrencyType.DOLLAR) {
                    tvAmount.text = tvAmount.context.getString(
                        R.string.isw_dollar_currency_amount,
                        DisplayUtils.getAmountString(amount.toInt())
                    )
                }else{
                    tvAmount.text = tvAmount.context.getString(R.string.isw_currency_amount, DisplayUtils.getAmountString(amount.toInt()))
                }
                tvTxnType.text = type.name
                tvPaymentType.text = paymentType.name

                val date = Date(txn.time)
                tvDate.text = DateUtils.timeOfDateFormat.format(date)

                val isSuccess = responseCode == IsoUtils.OK
                val status = if (isSuccess) "Success" else "Failed"
                statusText.text = status


                // get color for status text
                val textColorInt =
                    if (isSuccess) R.color.iswColorSuccess
                    else R.color.iswColorError

                // get color for background
                val backgroundColorInt =
                    if (isSuccess) R.color.iswColorSuccessHighlight
                    else R.color.iswColorErrorHighlight

                // transform int values to color values
                val textColor = ContextCompat.getColor(tvAmount.context, textColorInt)
                val backgroundColor = ContextCompat.getColor(tvAmount.context, backgroundColorInt)

                // set status color values
                statusContainer.setCardBackgroundColor(backgroundColor)
                statusText.setTextColor(textColor)

                itemView.setOnClickListener {
                    onSelect(txn)
                }
            }
        }
    }


    inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val progressBar: ProgressBar = view.findViewById(R.id.pbTransaction)


        fun bind(loadingStatus: Boolean) {
            val visibility =
                if (loadingStatus) View.VISIBLE
                else View.GONE
            progressBar.visibility = visibility

        }
    }

    companion object {

        private const val TYPE_LOADING = -1
        private const val TYPE_ITEM = 1

        private val diffCallback = object : DiffUtil.ItemCallback<TransactionLog>() {

            override fun areItemsTheSame(firstLog: TransactionLog, secondLog: TransactionLog): Boolean {
                return firstLog.id == secondLog.id
            }

            override fun areContentsTheSame(firstLog: TransactionLog, secondLog: TransactionLog): Boolean {
                return firstLog.equals(secondLog)
            }

        }
    }
}