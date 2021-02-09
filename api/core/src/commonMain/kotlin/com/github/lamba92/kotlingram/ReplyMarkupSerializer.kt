package com.github.lamba92.kotlingram

import com.github.lamba92.kotlingram.api.generated.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

object ReplyMarkupSerializer : JsonContentPolymorphicSerializer<ReplyMarkup>(ReplyMarkup::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out ReplyMarkup> =
        when {
            "force_reply" in element.jsonObject -> ForceReply.serializer()
            "inline_keyboard" in element.jsonObject -> InlineKeyboardMarkup.serializer()
            "keyboard" in element.jsonObject -> ReplyKeyboardMarkup.serializer()
            "remove_keyboard" in element.jsonObject -> ReplyKeyboardRemove.serializer()
            else -> error("Cannot serialize/deserialize object as InlineQueryResult:\n\t$element")
        }
}

