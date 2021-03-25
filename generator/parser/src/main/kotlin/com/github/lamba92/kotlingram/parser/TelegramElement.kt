package com.github.lamba92.kotlingram.parser

sealed class TelegramElement {
    abstract val name: String
    abstract val description: String
    abstract val fields: List<Field>

    data class Method(
        override val name: String,
        override val description: String,
        override val fields: List<Field>
    ) : TelegramElement()

    data class Object(
        override val name: String,
        override val description: String,
        override val fields: List<Field>
    ) : TelegramElement()

    data class Field(
        val name: String,
        val type: String,
        val required: Boolean,
        val description: String
    )

}
