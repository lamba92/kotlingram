package com.github.lamba92.telegram.api

import com.github.lamba92.telegram.api.generated.ResponseParameters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TelegramResponse<T>(
    val result: T,
    val ok: Boolean,
    val description: String? = null,
    @SerialName("error_code") val errorCode: Int? = null,
    val parameters: ResponseParameters? = null,
)
