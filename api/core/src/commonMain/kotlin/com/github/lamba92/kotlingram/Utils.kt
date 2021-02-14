package com.github.lamba92.kotlingram

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.utils.io.core.*

fun FormBuilder.appendFile(key: String, fileName: String, data: ByteArray) {
    appendInput(
        key,
        Headers.build { append(HttpHeaders.ContentDisposition, "filename=$fileName") },
        data.size.toLong()
    ) {
        buildPacket { writeFully(data) }
    }
}

fun buildMultiPartFormData(function: FormBuilder.() -> Unit) =
    MultiPartFormDataContent(formData(function))

