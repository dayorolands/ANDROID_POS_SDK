package com.interswitchng.smartpos.emv.pax

import android.support.test.runner.AndroidJUnit4
import com.kulik.android.jaxb.library.Annotations.XmlElement
import com.kulik.android.jaxb.library.Annotations.XmlRootElement
import com.kulik.android.jaxb.library.parser.ParserImpl
import com.kulik.android.jaxb.library.parser.UnMarshalerTypes
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith


@XmlRootElement(name = "Simple")
data class SimpleClass(
        @XmlElement(name = "x")
        var xAttribute: Int = 0,
        @XmlElement(name = "y")
        var yAttribute: Int = 0)


@RunWith(AndroidJUnit4::class)
class XmlParseText {

    @Test
    fun shouldParseXMLCorrectly() {
        val parser =  ParserImpl(UnMarshalerTypes.JSONAdapter);
        val se = parser.parse(SimpleClass::class.java, "");
    }


}