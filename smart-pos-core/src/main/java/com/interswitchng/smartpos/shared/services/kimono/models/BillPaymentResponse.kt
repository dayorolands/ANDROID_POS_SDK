package com.interswitchng.smartpos.shared.services.kimono.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.NamespaceList
import org.simpleframework.xml.Root


@Root(name = "billPaymentResponse", strict = false)
@NamespaceList(
        Namespace( prefix = "ns2", reference = "http://interswitchng.com"),
        Namespace( prefix = "ns3", reference = "http://tempuri.org/ns.xsd")
)
internal data class BillPaymentResponse(

        @field:Element(name = "description", required = false)
        var description: String = "",

        @field:Element(name = "field39", required = false)
        var responseCode: String = "",

        @field:Element(name = "narration", required = false)
        var narration: String = "",

        @field:Element(name = "approvedAmount", required = false)
        var approvedAmount: String = "",

        @field:Element(name = "stan", required = false)
        var stan: String = "",

        @field:Element(name = "collectionsAccountNumber", required = false)
        var collectionsAccountNumber: String = "",

        @field:Element(name = "wasReceive", required = false)
        var wasReceive: Boolean = false,

        @field:Element(name = "wasSend", required = false)
        var wasSend: Boolean = false,

        @field:Element(name = "responseMessage", required = false)
        var responseMessage: String = "",

        @field:Element(name = "transactionRef", required = false)
        var transactionRef: String = "",

        @field:Element(name = "collectionsAccountType", required = false)
        var collectionsAccountType: String = "",

        @field:Element(name = "uuid", required = false)
        var uuid: String = "",

        @field:Element(name = "isAmountFixed", required = false)
        var isAmountFixed: String = "",

        @field:Element(name = "surcharge", required = false)
        var surcharge: String = "",

        @field:Element(name = "biller", required = false)
        var biller: String = "",

        @field:Element(name = "itemDescription", required = false)
        var itemDescription: String = "",

        @field:Element(name = "customerDescription", required = false)
        var customerDescription: String = "")
