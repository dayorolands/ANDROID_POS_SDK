package com.interswitchng.smartpos.shared.services.kimono.models

import com.google.gson.Gson
import com.interswitchng.smartpos.shared.interfaces.library.KeyValueStore
import org.simpleframework.xml.*

@Root(name = "allTerminalInfo", strict = false)
internal data class AllTerminalInfo (
        @field:Element(name = "responseCode", required = false)
        var responseCode: String = "",

        @field:Element(name = "responseMessage", required = false)
        var responseMessage: String = "",

        @field:ElementList(name = "terminalAllowedTxTypes", inline=true, required = false)
        var terminalAllowedTxTypes: List<TerminalAllowedTxTypes>? = null,

        @field:Element(name = "terminalInfoBySerials", required = false)
        var terminalInfoBySerials: TerminalInfoBySerials? = null
)

@Root(name = "terminalInfoBySerials", strict = false)
internal class TerminalInfoBySerials(

        @field:Element(name = "terminalCode", required = false)
        var terminalCode: String = "",

        @field:Element(name = "cardAcceptorId", required = false)
        var cardAcceptorId: String = "",

        @field:Element(name = "merchantId", required = false)
        var merchantId: String = "",

        @field:Element(name = "merchantName", required = false)
        var merchantName: String = "",

        @field:Element(name = "merchantAddress1", required = false)
        var merchantAddress1: String = "",

        @field:Element(name = "merchantAddress2", required = false)
        var merchantAddress2: String = "",

        @field:Element(name = "merchantPhoneNumber", required = false)
        var merchantPhoneNumber: String = "",

        @field:Element(name = "merchantEmail", required = false)
        var merchantEmail: String = "",

        @field:Element(name = "merchantState", required = false)
        var merchantState: String = "",

        @field:Element(name = "merchantCity", required = false)
        var merchantCity: String = "",

        @field:Element(name = "cardAcceptorNameLocation", required = false)
        var cardAcceptorNameLocation: String = "",

        @field:Element(name = "merchantCategoryCode", required = false)
        var merchantCategoryCode: String = "")

@Root(name = "terminalAllowedTxTypes", strict = false)
internal class TerminalAllowedTxTypes(
        @field:Element(name = "applicationDescription", required = false)
        var applicationDescription: String = ""
)
