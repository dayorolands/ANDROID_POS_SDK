package com.interswitchng.smartpos.shared.services.kimono.models

import com.interswitchng.smartpos.IswPos
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(name = "reversalRequestWithoutOriginalDate", strict = false)
internal data class ReversalRequest(
        @field:Element(name = "terminalInformation", required = false)
        var terminalInformation: TerminalInformation? = null,
        @field:Element(name = "cardData", required = false)
        var cardData: CardData?,
        @field:Element(name = "fromAccount", required = false)
        var fromAccount: String,
        @field:Element(name = "stan", required = false)
        var stan: String,
        @field:Element(name = "originalStan", required = false)
        var originalStan: String,
        @field:Element(name = "minorAmount", required = false)
        var minorAmount: String,
        @field:Element(name = "pinData", required = false)
        var pinData: PinData?,
        @field:Element(name = "originalAuthId", required = false)
        var originalAuthCode: String? = null,

        @field:Element(name = "toAccount", required = false)
        var toAccount: String = "Default",
        @field:Element(name = "reversalType", required = false)
        var reversalType: String = "Purchase",

        @field:Element(name = "keyLabel", required = false)
        var keyLabel: String) {


    companion object {
        fun create(purchaseRequest: TransactionRequest): ReversalRequest {

            return ReversalRequest(
                    terminalInformation = purchaseRequest.terminalInformation,
                    cardData = purchaseRequest.cardData,
                    fromAccount = purchaseRequest.fromAccount,
                    minorAmount = purchaseRequest.minorAmount,
                    originalStan = purchaseRequest.stan,
                    stan = IswPos.getNextStan(),
                    pinData = purchaseRequest.pinData,
                    keyLabel = purchaseRequest.keyLabel
            )
        }
    }
}