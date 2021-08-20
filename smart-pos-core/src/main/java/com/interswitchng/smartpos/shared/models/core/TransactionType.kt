package com.interswitchng.smartpos.shared.models.core

enum class TransactionType {
    // NOTE: Do not change the order of these values, because of DB queries

    Purchase, PreAuth, Completion, Refund, Transfer, Payments, IFIS, CashBack

}