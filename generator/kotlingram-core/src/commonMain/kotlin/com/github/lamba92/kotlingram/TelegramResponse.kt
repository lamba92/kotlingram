package com.github.lamba92.kotlingram

import com.github.lamba92.kotlingram.api.generated.ResponseParameters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TelegramResponse<T>(
    val result: T? = null,
    val ok: Boolean,
    val description: String? = null,
    @SerialName("error_code") val errorCode: Int? = null,
    val parameters: ResponseParameters? = null
)
