package com.github.lamba92.telegram.api.data

import com.github.lamba92.telegram.api.generated.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure

object ReplyMarkupSerializer : KSerializer<ReplyMarkup> {
    override val descriptor =
        buildClassSerialDescriptor("ReplyMarkup") {
            element<Boolean>("force_reply", isOptional = true)
            element<Boolean>("selective", isOptional = true)
            element<List<List<InlineKeyboardButton>>>("inline_keyboard", isOptional = true)
            element<List<List<KeyboardButton>>>("keyboard", isOptional = true)
            element<Boolean>("resize_keyboard", isOptional = true)
            element<Boolean>("one_time_keyboard", isOptional = true)
            element<Boolean>("remove_keyboard", isOptional = true)
        }

    override fun deserialize(decoder: Decoder): ReplyMarkup =
        decoder.decodeStructure(descriptor) {
            var forceReply: Boolean? = null
            var selective: Boolean? = null
            var inlineKeyboard: List<List<InlineKeyboardButton>> = emptyList()
            var keyboard: List<List<KeyboardButton>> = emptyList()
            var resizeKeyboard: Boolean? = null
            var onetimeKeyboard: Boolean? = null
            var removeKeyboard: Boolean? = null
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> forceReply = decodeBooleanElement(descriptor, 0)
                    1 -> selective = decodeBooleanElement(descriptor, 1)
                    2 -> inlineKeyboard = decodeSerializableElement(
                        descriptor,
                        2,
                        ListSerializer(ListSerializer(InlineKeyboardButton.serializer()))
                    )
                    3 -> keyboard = decodeSerializableElement(
                        descriptor,
                        3,
                        ListSerializer(ListSerializer(KeyboardButton.serializer()))
                    )
                    4 -> resizeKeyboard = decodeBooleanElement(descriptor, 4)
                    5 -> onetimeKeyboard = decodeBooleanElement(descriptor, 5)
                    6 -> removeKeyboard = decodeBooleanElement(descriptor, 6)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            when {
                forceReply != null -> ForceReply(forceReply, selective)
                inlineKeyboard.isNotEmpty() -> InlineKeyboardMarkup(inlineKeyboard)
                keyboard.isNotEmpty() -> ReplyKeyboardMarkup(keyboard, resizeKeyboard, onetimeKeyboard, selective)
                removeKeyboard != null -> ReplyKeyboardRemove(removeKeyboard, selective)
                else -> error("No suitable child found for ReplyMarkup")
            }
        }

    override fun serialize(encoder: Encoder, value: ReplyMarkup) {
        when (value) {
            is ReplyKeyboardMarkup -> encoder.encodeSerializableValue(ReplyKeyboardMarkup.serializer(), value)
            is ReplyKeyboardRemove -> encoder.encodeSerializableValue(ReplyKeyboardRemove.serializer(), value)
            is InlineKeyboardMarkup -> encoder.encodeSerializableValue(InlineKeyboardMarkup.serializer(), value)
            is ForceReply -> encoder.encodeSerializableValue(ForceReply.serializer(), value)
        }
    }
}
