package com.interswitchng.smartpos.shared.utilities


object KeysUtils {

    fun productionKSN() = "0000000002DDDDE00001" //FFFF000002DDDDE0
    fun testKSN() = "0000000006DDDDE00000"
    fun productionIPEK() = "3F2216D8297BCE9C"
    fun testIPEK() = "9F8011E7E71E483B"

    fun productionCMS(isEpms: Boolean) =
        if (isEpms) EPMS.productionCMS()
        else CTMS.productionCMS()

    fun testCMS(isEpms: Boolean) =
        if (isEpms) EPMS.testCMS()
        else CTMS.testCMS()


    private object EPMS {
        fun productionCMS() = "A050F63AFF366A4B0588D818D23C6C77"
        fun testCMS() = "DBEECACCB4210977ACE73A1D873CA59F"
    }

    private object CTMS {
        fun productionCMS() = "3CDDE1CC6FDD225C9A8BC3EB065509A6"//"A050F63AFF366A4B0588D818D23C6C77"
        fun testCMS() = "DBEECACCB4210977ACE73A1D873CA59F" //EPMS=DBEECACCB4210977ACE73A1D873CA59F
    }
}
