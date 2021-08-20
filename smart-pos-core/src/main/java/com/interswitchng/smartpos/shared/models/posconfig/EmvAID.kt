package com.interswitchng.smartpos.shared.models.posconfig

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


/**
 * This file represents model for
 * EMV Application IDs and keys
 */
@Root(name = "emv", strict = false)
class EmvAIDs {
    @field:ElementList(name = "emvcards")
    var cards: ArrayList<EmvCard> = arrayListOf()
}

@Root(name = "emvcard", strict = false)
class EmvCard {
    @field:Element(name = "name")
    var name: String = ""
    @field:Element(name = "aid")
    var aid: String = ""
    @field:Element(name = "partialmatch")
    var partialMatch: Boolean = false
    @field:ElementList(name = "keys")
    var keys: ArrayList<EmvCapk> = arrayListOf()
}

class EmvCapk {
    @field:Element(name = "keyidx")
    var id: String = ""
    @field:Element(name = "expdate")
    var expiry: String = ""
    @field:Element(name = "modulus")
    var modulus: String = ""
    @field:Element(name = "exponent")
    var exponent: String = ""
    @field:Element(name = "checksum")
    var checksum: String = ""
}

