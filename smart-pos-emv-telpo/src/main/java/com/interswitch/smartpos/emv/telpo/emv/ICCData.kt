package com.interswitch.smartpos.emv.telpo.emv

enum class ICCData(val tagName: String, val tag: Int, val min: Int, val max: Int) {
    // emv request and sensitive data
    AUTHORIZATION_REQUEST("Authorization Request", 0x9F26, 8, 8),
    CRYPTOGRAM_INFO_DATA("Cryptogram Information Data", 0x9F27,  1, 1),
    ISSUER_APP_DATA("Issuer Application Data", 0x9F10, 0, 32),
    UNPREDICTABLE_NUMBER("Unpredictable Number", 0x9F37, 4, 4),
    APPLICATION_TRANSACTION_COUNTER("App Transaction Counter", 0x9F36, 2, 2),
    TERMINAL_VERIFICATION_RESULT("Terminal Verification Result", 0x95, 5, 5),
    TRANSACTION_DATE("Transaction Date", 0x9A, 3, 3),
    TRANSACTION_TYPE("Transaction Type", 0x9C, 1, 1),
    TRANSACTION_AMOUNT("Transaction Amount", 0x9F02, 6, 6),
    TRANSACTION_CURRENCY_CODE("Currency Code", 0x5F2A, 2, 2),
    APPLICATION_INTERCHANGE_PROFILE("App Interchange Profile", 0x82, 2, 2),
    TERMINAL_COUNTRY_CODE("Terminal Country Code", 0x9F1A, 2, 2),
    CARD_HOLDER_VERIFICATION_RESULT("CVM Results", 0x9F34, 3, 3),
    TERMINAL_CAPABILITIES("Terminal Capabilities", 0x9F33, 3, 3),
    TERMINAL_TYPE("Terminal Type", 0x9F35, 1, 1),
    INTERFACE_DEVICE_SERIAL_NUMBER("IDSN", 0X9F1E, 8, 8),
    DEDICATED_FILE_NAME("Dedicated File Name", 0x84, 5, 16),
    APP_VERSION_NUMBER("App Version Number", 0x9F09, 2, 2),
    ANOTHER_AMOUNT("Amount", 0x9F03, 6, 6),
    CARD_HOLDER_NAME("Cardholder Name", 0x5F20, 2, 26),
    APP_PAN_SEQUENCE_NUMBER("App PAN Sequence Number", 0x5F34, 1, 1),


    // Issuer response
    ISSUER_AUTHENTICATION("Issuer Authentication", 0x91, 0, 16),
    ISSUER_SCRIPT1("Issuer script 1", 0x71, 0, 128),
    ISSUER_SCRIPT2("Issuer script 1", 0x72, 0, 128)

}

internal val REQUEST_TAGS = listOf(ICCData.AUTHORIZATION_REQUEST,
        ICCData.CRYPTOGRAM_INFO_DATA,
        ICCData.ISSUER_APP_DATA,
        ICCData.UNPREDICTABLE_NUMBER,
        ICCData.APPLICATION_TRANSACTION_COUNTER,
        ICCData.TERMINAL_VERIFICATION_RESULT,
        ICCData.TRANSACTION_DATE,
        ICCData.TRANSACTION_TYPE,
        ICCData.TRANSACTION_AMOUNT,
        ICCData.TRANSACTION_CURRENCY_CODE,
        ICCData.APPLICATION_INTERCHANGE_PROFILE,
        ICCData.TERMINAL_COUNTRY_CODE,
        ICCData.CARD_HOLDER_VERIFICATION_RESULT,
        ICCData.TERMINAL_CAPABILITIES,
        ICCData.TERMINAL_TYPE,
        ICCData.INTERFACE_DEVICE_SERIAL_NUMBER,
        ICCData.DEDICATED_FILE_NAME,
        ICCData.APP_VERSION_NUMBER,
        ICCData.ANOTHER_AMOUNT,
        ICCData.APP_PAN_SEQUENCE_NUMBER)
