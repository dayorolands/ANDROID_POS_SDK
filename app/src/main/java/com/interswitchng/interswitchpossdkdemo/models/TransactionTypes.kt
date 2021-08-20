package com.interswitchng.interswitchpossdkdemo.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.interswitchng.interswitchpossdkdemo.R
import com.interswitchng.smartpos.shared.models.core.TransactionType


private class TransactionConfig(val name: String)
enum class TransactionTypes(
        val icon: Int,
        val title: String,
        val subtitle: String,
        var isDefault: Boolean
) {
    Purchase(
            icon = R.drawable.ic_transaction_purchase,
            title = "Purchase",
            subtitle = "Pay for items purchased",
            isDefault = true
    ),
    PreAuth(
            icon = R.drawable.ic_transaction_pre_auth,
            title = "Pre Authorization",
            subtitle = "Initiate a pre-authorized transaction",
            isDefault = true
    ),
    Completion(
            icon = R.drawable.ic_transaction_completion,
            title = "Completion",
            subtitle = "Complete a pre-authorized transaction",
            isDefault = true
    ),
    Refunds(
            icon = R.drawable.ic_transaction_refunds,
            title = "Refunds",
            subtitle = "Initiate a refund to a customer",
            isDefault = false
    ),
    Transfer(
            icon = R.drawable.ic_transaction_transfer,
            title = "Transfer",
            subtitle = "Initiate a bank transfer (coming soon)",
            isDefault = false

    ),
    Payments(
            icon = R.drawable.ic_transaction_bills,
            title = "BillPayments",
            subtitle = "Initiate a bill payment (coming soon)",
            isDefault = false
    ),
    IFIS(
            icon = R.drawable.ic_transaction_bills,
            title = "IFIS Cash-Out",
            subtitle = "Do an IFIS Cash-out",
            isDefault = false
    ),
    CashBack(
            icon = R.drawable.ic_transaction_purchase_wc,
            title = "Purchase with cashback",
            subtitle = "Receive payment and provide cashback service",
            isDefault = false
    ),
    More(
            icon = R.drawable.ic_transaction_more,
            title = "More",
            subtitle = "Some more transactions",
            isDefault = true
    );

    val toTransactionType: TransactionType
        get() = when (this) {
            Purchase -> TransactionType.Purchase
            PreAuth -> TransactionType.PreAuth
            Completion -> TransactionType.Completion
            Refunds -> TransactionType.Refund
            Transfer -> TransactionType.Transfer
            Payments -> TransactionType.Payments
            IFIS -> TransactionType.IFIS
            CashBack -> TransactionType.CashBack
            else -> throw Exception("Invalid Transaction type selected")
        }

    companion object {
        private const val KEY_DEFAULT = "default_transaction_types"
        private const val KEY_MORE = "more_transaction_types"


        // load default transaction types
        fun getDefault(context: Context): List<TransactionTypes> {
            val defaultTxns = getList(KEY_DEFAULT, context)
            val list = defaultTxns.map {
                // set config runtime value
                val type = valueOf(it.name)
                type.isDefault = true

                type
            }

            // return non-default transaction types
            return if (list.isNotEmpty()) list + More
            else values().filter { it.isDefault }
        }

        // load more transaction types
        fun getMore(context: Context): List<TransactionTypes> {
            val moreTxns = getList(KEY_MORE, context)
            val list = moreTxns.map {
                // set config runtime value
                val type = valueOf(it.name)
                type.isDefault = false

                type
            }

            // return non-default transaction types
            return if (list.isNotEmpty()) list
            else values().filter { !it.isDefault }
        }

        private fun getList(name: String, context: Context): List<TransactionConfig> {
            val sp = context.getSharedPreferences("TransactionTypes", Context.MODE_PRIVATE)
            val string = sp.getString(name, null) ?: return emptyList()

            // convert string from json to java map object
            val typeToken = object : TypeToken<List<TransactionConfig>>() {}.type
            return Gson().fromJson(string, typeToken)
        }


        fun saveDefault(context: Context, list: List<TransactionTypes>) {
            val config = list.map { TransactionConfig(name = it.name) }
            saveList(KEY_DEFAULT, context, config)
        }

        fun saveMore(context: Context, list: List<TransactionTypes>) {
            val config = list.map { TransactionConfig(name = it.name) }
            saveList(KEY_MORE, context, config)
        }

        private fun saveList(name: String, context: Context, list: List<TransactionConfig>) {
            val sp = context.getSharedPreferences("TransactionTypes", Context.MODE_PRIVATE)
            val editor = sp.edit()

            val json = Gson().toJson(list)

            editor.putString(name, json)
            editor.apply()
        }
    }
}
