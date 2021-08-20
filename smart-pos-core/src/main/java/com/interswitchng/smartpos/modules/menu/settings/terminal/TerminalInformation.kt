package com.interswitchng.smartpos.modules.menu.settings.terminal

import com.interswitchng.smartpos.shared.models.core.TerminalInfo
import com.interswitchng.smartpos.shared.utilities.InputValidator
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

/**
 * This class is used to load terminal configuration
 * from the file system
 */

@Root(name = "terminalInformation", strict = false)
internal class TerminalInformation {
    @field:Element(name = "terminalId", required = false)
    var terminalId: String = ""
    @field:Element(name = "terminalId2", required = false)
    var terminalId2: String = ""
    @field:Element(name = "merchantId", required = false)
    var merchantId: String = ""
    @field:Element(name = "merchantId2", required = false)
    var merchantId2: String = ""
    @field:Element(name = "merchantNameAndLocation", required = false)
    var merchantNameAndLocation: String = ""
    @field:Element(name = "merchantAddress1", required = false)
    var merchantAddress1: String = ""
    @field:Element(name = "merchantAddress2", required = false)
    var merchantAddress2: String = ""
    @field:Element(name = "merchantCategoryCode", required = false)
    var merchantCategoryCode: String = ""
    @field:Element(name = "countryCode", required = false)
    var countryCode: String = ""
    @field:Element(name = "currencyCode", required = false)
    var currencyCode: String = ""
    @field:Element(name = "currencyCode2", required = false)
    var currencyCode2: String = ""
    @field:Element(name = "callHomeTimeInMin", required = false)
    var callHomeTimeInMin: String = ""
    @field:Element(name = "serverTimeoutInSec", required = false)
    var serverTimeoutInSec: String = ""
    @field:Element(name = "serverIp", required = false)
    var serverIp: String = ""
    @field:Element(name = "kimono", required = false)
    var isKimono: Boolean = false
    @field:Element(name = "capabilities", required = false)
    var capabilities: String? = null
    @field:Element(name = "serverPort", required = false)
    var serverPort: String = ""
    @field:Element(name = "serverUrl", required = false)
    var serverUrl: String = ""
    @field:Element(name = "merchantAlias", required = false)
    var merchantAlias: String = ""
    @field:Element(name = "merchantCode", required = false)
    var merchantCode: String = ""


    lateinit var error: TerminalInformation


    val isValid: Boolean
        get() {
            validateInfo()

            val properties = listOf(error.terminalId, error.merchantId, error.currencyCode2,error.serverIp, error.capabilities,
                    error.merchantNameAndLocation, error.merchantCategoryCode, error.serverPort, error.serverUrl,
                    error.countryCode, error.currencyCode, error.terminalId2, error.merchantId2,error.callHomeTimeInMin, error.serverTimeoutInSec)


            // validate that all error properties are empty
            return properties.all { it?.isEmpty() ?: true }
        }

    val toTerminalInfo: TerminalInfo
        get() {
            return TerminalInfo(
                    terminalId = terminalId,
                    merchantId = merchantId,
                    terminalId2 = terminalId2,
                    merchantId2 = merchantId2,
                    merchantNameAndLocation = merchantNameAndLocation,
                    merchantAddress = "$merchantAddress1 $merchantAddress2",
                    merchantCategoryCode = merchantCategoryCode,
                    countryCode = countryCode,
                    currencyCode = currencyCode,
                    currencyCode2 = currencyCode2,
                    callHomeTimeInMin = callHomeTimeInMin.toIntOrNull() ?: -1,
                    serverTimeoutInSec = serverTimeoutInSec.toIntOrNull() ?: -1,
                    merchantAlias = merchantAlias,
                    merchantCode = merchantCode,
                    isKimono = isKimono,
                    capabilities = capabilities,
                    serverIp = serverIp,
                    serverUrl = serverUrl,
                    serverPort = serverPort.toIntOrNull() ?: -1)
        }

    private fun validateInfo() {
        error =
            TerminalInformation()

        // validate terminalId
        val terminalIdValidator = InputValidator(terminalId)
                .isNotEmpty().isAlphaNumeric().isExactLength(8)
        // assign error message for field
        if (terminalIdValidator.hasError) error.terminalId = terminalIdValidator.message


        // validate merchant id
        val merchantId = InputValidator(merchantId)
                .isNotEmpty().isAlphaNumeric().isExactLength(15)
        // assign error message for field
        if (merchantId.hasError) error.merchantId = merchantId.message


        // validate merchant name and location
        val merchantNameAndLocation = InputValidator(merchantNameAndLocation).isNotEmpty().isExactLength(40)
        // assign error message for field
        if (merchantNameAndLocation.hasError) error.merchantNameAndLocation = merchantNameAndLocation.message


        // validate merchant code
        val merchantCode = InputValidator(merchantCategoryCode)
                .isNotEmpty().isNumber().isExactLength(4)
        // assign error message for field
        if (merchantCode.hasError) error.merchantCategoryCode = merchantCode.message


        // validate country code
        val countryCode = InputValidator(countryCode)
                .isNotEmpty().isNumber().hasMinLength(3).hasMaxLength(4)

        // assign error message for field
        if (countryCode.hasError) error.countryCode = countryCode.message


        // validate country code
        val currencyCode = InputValidator(currencyCode)
                .isNotEmpty().isNumber().isExactLength(3)
        // assign error message for field
        if (currencyCode.hasError) error.currencyCode = currencyCode.message


        val callHomeTime = InputValidator(callHomeTimeInMin)
                .isNotEmpty().isNumber().isNumberBetween(0, 120)
        // assign error message for field
        if (callHomeTime.hasError) error.callHomeTimeInMin = callHomeTime.message


        // validate server timeout
        val serverTimeout = InputValidator(serverTimeoutInSec)
                .isNotEmpty().isNumber().isNumberBetween(0, 120)
        // assign error message for field
        if (serverTimeout.hasError) error.serverTimeoutInSec = serverTimeout.message


        // validate server server url
        val serverUrl = InputValidator(serverUrl)
                .isNotEmpty()
        // assign error message for field if is kimono
        if (isKimono && serverUrl.hasError) error.serverUrl = serverUrl.message


        // validate terminal capabilities value
        capabilities?.apply {
            val capabilitiesValidator = InputValidator(this)
                    .isNotEmpty().isExactLength(6).isAlphaNumeric()

            if (capabilitiesValidator.hasError) error.capabilities = capabilitiesValidator.message
        }

        // validate server IP
        val serverIp = InputValidator(serverIp)
                .isNotEmpty().isValidIp()
        // assign error message for field if not kimono
        if (serverIp.hasError && !isKimono) error.serverIp = serverIp.message


        // validate server server url
        val serverPort = InputValidator(serverPort)
                .isNotEmpty().isNumber()
                .hasMaxLength(5)
                .isNumberBetween(0, 65535)

        // assign error message for field if not kimono
        if (serverPort.hasError && !isKimono) error.serverPort = serverPort.message
    }
}
