package com.interswitchng.smartpos.emv.pax.old.models

data class POSConfiguration(
            internal val alias: String,
            internal val terminalId: String,
            internal val merchantId: String,
            internal val terminalType: String,
            internal val uniqueId: String,
            internal val merchantLocation: String,
            internal val merchantCode: String,
            internal val merchantName: String = ""
) {


    companion object {

        internal fun default(): POSConfiguration {
            val empty = ""
            return POSConfiguration(empty, empty, empty, empty, empty, empty, empty)
        }
    }
}
