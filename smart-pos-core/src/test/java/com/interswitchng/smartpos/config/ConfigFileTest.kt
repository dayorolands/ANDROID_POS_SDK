package com.interswitchng.smartpos.config

import com.interswitchng.smartpos.Utilities
import com.interswitchng.smartpos.old_shared.models.posconfig.TerminalConfig
import com.interswitchng.smartpos.old_shared.services.iso8583.utils.FileUtils
import org.junit.Assert
import org.junit.Test
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import org.simpleframework.xml.core.Persister

class ConfigFileTest {

        @Root(name = "sloths", strict = false)
        class SimpleClass {
            @field:Element(name = "x")
            var xAttribute: Int = 0
            @field:Element(name = "y")
            var yAttribute: Int = 0
        }

    @Test
    fun shouldParseXMLcorrectly() {

        val expected = SimpleClass().apply { xAttribute = 1; yAttribute = 2}
        val actual = Persister().read(SimpleClass::class.java, "<Simple><x>1</x><y>2</y></Simple>")
        Assert.assertEquals(expected.xAttribute, actual.xAttribute)
        Assert.assertEquals(expected.yAttribute, actual.yAttribute)
    }

    @Test
    fun shouldReadEmvXmlFromFileCorrectly() {
        val xmlFile = Utilities.getStream("sample_aids.xml")
        val actual = FileUtils.getAids(xmlFile)

        Assert.assertEquals(2, actual.cards.size)
        Assert.assertEquals(2, actual.cards[0].keys.size)
        Assert.assertEquals(3, actual.cards[1].keys.size)
    }

    @Test
    fun shouldReadTerminalConfigXmlFileCorrectly() {

        val terminalXml = Utilities.getStream("sample_terminal.xml")
        val expectedTerminal = TerminalConfig().apply {
            supportpse = true
            floorlimitcheck = true
            floorlimit = 100
            tacdenial = "0010000000"
            taconline = "FCF8E4F880"
            tacdefault = "FCF0E40800"
            ddol = "9F3704"
            tdol = "N/A"
            version = "0020"
            riskdata = ""
            terminalcountrycode = "0566"
            terminaltype = "34"
            terminalcapability = "E0F8C8"
            extendedterminalcapability = "E000F0A001"
            referercurrencycode = "0566"
            merchantcatcode = ""
        }


        val actualTerminalConfig = FileUtils.getTerminalConfig(terminalXml)

        Assert.assertEquals(expectedTerminal.ddol, actualTerminalConfig.ddol)
        Assert.assertEquals(expectedTerminal.tdol, actualTerminalConfig.tdol)
        Assert.assertEquals(expectedTerminal.floorlimit, actualTerminalConfig.floorlimit)
        Assert.assertEquals(expectedTerminal.merchantcatcode, actualTerminalConfig.merchantcatcode)
        Assert.assertEquals(expectedTerminal.referercurrencycode, actualTerminalConfig.referercurrencycode)
        Assert.assertEquals(expectedTerminal.terminalcapability, actualTerminalConfig.terminalcapability)
        Assert.assertEquals(expectedTerminal.terminalcountrycode, actualTerminalConfig.terminalcountrycode)

    }
}