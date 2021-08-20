package com.interswitchng.smartpos.shared.services.kimono.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.NamespaceList
import org.simpleframework.xml.Root


@Root(name = "purchaseResponse", strict = false)
@NamespaceList(
    Namespace( prefix = "ns2", reference = "http://interswitchng.com"),
    Namespace( prefix = "ns3", reference = "http://tempuri.org/ns.xsd")
)
internal data class PurchaseResponse(

    @field:Element(name = "description", required = false)
    var description: String = "",

    @field:Element(name = "field39", required = false)
    var responseCode: String = "",

    @field:Element(name = "authId", required = false)
    var authCode: String = "",

    @field:Element(name = "referenceNumber", required = false)
    var referenceNumber: String = "",

    @field:Element(name = "stan", required = false)
    var stan: String = "",

    @field:Element(name = "transactionChannelName", required = false)
    var transactionChannelName: String = "",

    @field:Element(name = "wasReceive", required = false)
    var wasReceive: Boolean = false,

    @field:Element(name = "wasSend", required = false)
    var wasSend: Boolean = false,

    @field:Element(name = "responseMessage", required = false)
    var responseMessage: String = "",

    @field:Element(name = "transactionRef", required = false)
    var transactionRef: String = "",

    @field:Element(name = "remoteResponseCode", required = false)
    var remoteResponseCode: String = "")
