package com.interswitchng.smartpos.shared.services.kimono.models

import org.simpleframework.xml.*


@Root(name = "kxml", strict = false)
@NamespaceList(
        Namespace(prefix = "ns2", reference = "http://interswitchng.com"),
        Namespace(prefix = "ns3", reference = "http://tempuri.org/ns.xsd")
)
internal data class KimonoKeyResponse(
        @field:Element(name = "card", required = false)
        var card: CardScript? = null,
        @field:ElementList(inline = true, entry = "var", required = false)
        var responses: List<ValueResponse>? = null
) {

    // assign all the fields
    var responseCode: String = ""
    var responseMessage: String = ""

    fun setResponses() {
        responseCode = responses?.firstOrNull { it.name == "responsecode" }?.text ?: ""
        responseMessage = responses?.firstOrNull { it.name == "responsemessage" }?.text ?: ""
    }
}

internal data class CardScript(
        @field:Element(name = "script", required = false)
        var script: ValueResponse? = null,
        @field:Attribute(name = "name", required = false)
        var name: String = "",
        @field:Attribute(name = "type", required = false)
        var type: String = ""
)


internal data class ValueResponse(
        @field:Attribute(name = "name", required = false)
        var name: String = "",
        @field:Text
        var text: String = "")