package com.interswitchng.smartpos.shared.models.core

import android.content.Intent
import com.interswitchng.smartpos.shared.models.transaction.PaymentType

sealed class Transaction {

    object PreAuth : Transaction()
    object Completion : Transaction()
    object Refund : Transaction()
    object Payments : Transaction()
    object IFIS : Transaction()
    object CashBack : Transaction()


    data class Purchase(internal val type: PaymentType?) : Transaction() {
        override fun bundleUp(bundle: Intent): Intent {
            super.bundleUp(bundle)
            return bundle.putExtra(KEY_PAYMENT_TYPE, type?.name)
        }

        constructor(bundle: Intent) : this(
            type = bundle
                .getStringExtra(KEY_PAYMENT_TYPE)
                ?.let(PaymentType::valueOf)
        )

        companion object {
            const val KEY_PAYMENT_TYPE = "PAYMENT_TYPE_KEY"
        }
    }

    internal open fun bundleUp(bundle: Intent): Intent = bundle.putExtra(
        KEY_TRANSACTION_TYPE,
        this::class.java.simpleName
    )


    companion object {
        private const val KEY_TRANSACTION_TYPE = "transaction_type_key"

        internal fun readFromBundle(bundle: Intent): Transaction {
            // get the class type for the transaction
            val classType = bundle.getStringExtra(KEY_TRANSACTION_TYPE)
            // get the transaction type
            return when (classType) {
                Completion::class.java.simpleName -> Completion
                Refund::class.java.simpleName -> Refund
                PreAuth::class.java.simpleName -> PreAuth
                Purchase::class.java.simpleName -> Purchase(bundle)
                Payments::class.java.simpleName -> Payments
                IFIS::class.java.simpleName -> IFIS
                CashBack::class.java.simpleName -> CashBack
                else -> throw Exception("Payment type not supported")
            }
        }
    }

}

typealias Purchase = Transaction.Purchase