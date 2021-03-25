package com.github.lamba92.kotlingram

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This object represents an error in the Telegram Passport element which was submitted that should be
 * resolved by the user.
 */
@Serializable
data class PassportElementError(
    val source: PassportElementErrorSource,
    val type: String,
    val message: String,
    @SerialName("field_name") val fieldName: String? = null,
    @SerialName("data_hash") val dataHash: String? = null,
    @SerialName("file_hash") val fileHash: String? = null,
    @SerialName("element_hash") val elementHash: String? = null,
    @SerialName("file_hashes") val fileHashes: List<String> = emptyList(),
)

