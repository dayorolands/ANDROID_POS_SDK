package com.interswitchng.smartpos.shared.models.posconfig

import android.graphics.Bitmap

/**
 * A sum type capturing printable information
 * - Line class represents a dotted line on a print slip
 * - Data class represents the textual information to be printed
 * - BitMap class represents any image to be printed
 */
sealed class PrintObject {
    object Line: PrintObject()
    class Data(val value: String, val config: PrintStringConfiguration = PrintStringConfiguration()): PrintObject()
    class BitMap(val bitmap: Bitmap): PrintObject()
}
