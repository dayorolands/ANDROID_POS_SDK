package com.interswitchng.smartpos.shared.services.utils

import com.solab.iso8583.IsoMessage
import java.io.PrintStream

class NibssIsoMessage(val message: IsoMessage)  {


    fun setValue(fieldId: Int, value: String): NibssIsoMessage {
        val field = message.getField<Any>(fieldId)
        message.setValue(fieldId, value, field.type, field.length)
        return this
    }

    fun dump(p: PrintStream, indent: String = "") {

        val type = Integer.toHexString(message.type)
        p.println("$indent<isomsg mti=\"$type\">")

        for (i in 0 until 129) {
            val field = message.getField<Any>(i)
            if (field != null) {
                field.toString()
                val value = "$indent\t\t <field id=\"$i\"  value=\"$field\" />"
                p.println(value)
            }
        }

        p.println( "$indent</isomsg>")

    }

    fun copyFieldsFrom(isoMessage: NibssIsoMessage): NibssIsoMessage {
        // copyFieldsFrom all none-null values
        for (i in 0 until 129) {
            // extract field from isoMessage
            val field = isoMessage.message.getField<Any>(i)

            if (field != null) {
                message.setValue(i, field.value, field.type, field.length)
            }
        }

        return this
    }
}