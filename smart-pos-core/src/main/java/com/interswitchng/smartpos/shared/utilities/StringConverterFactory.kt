package com.interswitchng.smartpos.shared.utilities

import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.reflect.Type

internal class ToStringConverterFactory : Converter.Factory() {

    fun fromResponseBody(type: Type, annotations: Array<Annotation>): Converter<ResponseBody, *>? {
        return if (String::class.java == type) {
            Converter<ResponseBody, String> { value -> value.string() }
        } else null
    }

    fun toRequestBody(type: Type, annotations: Array<Annotation>): Converter<*, RequestBody>? {
        return if (String::class.java == type) {
            Converter<String, RequestBody> { value -> RequestBody.create(MEDIA_TYPE, value) }
        } else null
    }

    companion object {
        private val MEDIA_TYPE = MediaType.parse("text/plain")
    }
}