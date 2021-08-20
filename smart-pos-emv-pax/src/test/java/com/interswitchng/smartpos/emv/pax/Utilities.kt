package com.interswitchng.smartpos.emv.pax

import java.io.File
import java.io.InputStream

object Utilities {

    fun getFile(path: String): File {
        val loader = javaClass.classLoader
        val uri = loader?.getResource(path)
        return File(uri?.path)
    }

    fun getStream(path: String): InputStream = getFile(path).inputStream()

    fun getString(path: String): String = String(getBytes(path))

    fun getBytes(path: String): ByteArray = getFile(path).readBytes()

}
