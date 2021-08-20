package com.interswitchng.smartpos.shared.models.posconfig

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

/**
 * This class represents all necessary information
 * to be used to configure a POS terminal
 */
@Root(name = "terminal", strict = false)
class TerminalConfig {

    @field:Element(name = "supportpse", required = false)
    var supportpse: Boolean = false
    @field:Element(name = "floorlimitcheck", required = false)
    var floorlimitcheck: Boolean = false
    @field:Element(name = "floorlimit", required = false)
    var floorlimit: Int = 0
    @field:Element(name = "tacdenial", required = false)
    var tacdenial: String = ""
    @field:Element(name = "taconline", required = false)
    var taconline: String = ""
    @field:Element(name = "tacdefault", required = false)
    var tacdefault: String = ""
    @field:Element(name = "ddol", required = false)
    var ddol: String = ""
    @field:Element(name = "tdol", required = false)
    var tdol: String = ""
    @field:Element(name = "version", required = false)
    var version: String = ""
    @field:Element(name = "riskdata", required = false)
    var riskdata: String = ""
    @field:Element(name = "terminalcountrycode", required = false)
    var terminalcountrycode: String = ""
    @field:Element(name = "terminaltype", required = false)
    var terminaltype: String = ""
    @field:Element(name = "terminalcapability", required = false)
    var terminalcapability: String = ""
    @field:Element(name = "extendedterminalcapability", required = false)
    var extendedterminalcapability: String = ""
    @field:Element(name = "referercurrencycode", required = false)
    var referercurrencycode: String = ""
    @field:Element(name = "merchantcatcode", required = false)
    var merchantcatcode: String = ""
}