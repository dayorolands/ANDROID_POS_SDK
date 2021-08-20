package com.interswitchng.smartpos.shared.models.transaction.cardpaycode.request

import org.simpleframework.xml.Element

class IccData(
        @field:Element(name = "AmountAuthorized")
        var TRANSACTION_AMOUNT: String = "",
        @field:Element(name = "AmountOther")
        var ANOTHER_AMOUNT: String = "000000000000",
        @field:Element(name = "ApplicationInterchangeProfile")
        var APPLICATION_INTERCHANGE_PROFILE: String = "",
        @field:Element(name = "atc")
        var APPLICATION_TRANSACTION_COUNTER: String = "",
        @field:Element(name = "Cryptogram")
        var AUTHORIZATION_REQUEST: String = "",
        @field:Element(name = "CryptogramInformationData")
        var CRYPTOGRAM_INFO_DATA: String = "",
        @field:Element(name = "CvmResults")
        var CARD_HOLDER_VERIFICATION_RESULT: String = "",
        @field:Element(name = "iad")
        var ISSUER_APP_DATA: String = "",
        @field:Element(name = "TransactionCurrencyCode")
        var TRANSACTION_CURRENCY_CODE: String = "",
        @field:Element(name = "TerminalVerificationResult")
        var TERMINAL_VERIFICATION_RESULT: String = "",
        @field:Element(name = "TerminalCountryCode")
        var TERMINAL_COUNTRY_CODE: String = "",
        @field:Element(name = "TerminalType")
        var TERMINAL_TYPE: String = "",
        @field:Element(name = "TerminalCapabilities")
        var TERMINAL_CAPABILITIES: String = "",
        @field:Element(name = "TransactionDate")
        var TRANSACTION_DATE: String = "",
        @field:Element(name = "TransactionType")
        var TRANSACTION_TYPE: String = "",
        @field:Element(name = "UnpredictableNumber")
        var UNPREDICTABLE_NUMBER: String = "",
        @field:Element(name = "DedicatedFileName")
        var DEDICATED_FILE_NAME: String = "") {


        var INTERFACE_DEVICE_SERIAL_NUMBER: String = ""
        var APP_VERSION_NUMBER: String = ""
        var APP_PAN_SEQUENCE_NUMBER: String = ""
        var CARD_HOLDER_NAME: String = ""

        var iccAsString: String = ""
}

