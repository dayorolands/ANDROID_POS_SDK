package com.interswitchng.smartpos.shared.models.core

data class PurchaseConfig (
        internal val minimumAmount: Int,
        internal val vendorEmail: String,
        internal val local: IswLocal
)