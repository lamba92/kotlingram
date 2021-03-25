package com.github.lamba92.kotlingram

import com.github.lamba92.kotlingram.api.generated.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

object InputMessageContentSerializer :
    JsonContentPolymorphicSerializer<InputMessageContent>(InputMessageContent::class) {

    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out InputMessageContent> =
        with(element) {
            when {
                "phone_number" in jsonObject && "first_name" in jsonObject ->
                    InputContactMessageContent.serializer()
                "messageText" in jsonObject -> InputTextMessageContent.serializer()
                "latitude" in jsonObject && "longitude" in jsonObject &&
                    "title" !in jsonObject && "address" !in jsonObject ->
                    InputLocationMessageContent.serializer()
                "latitude" in jsonObject && "longitude" in jsonObject &&
                    "title" in jsonObject && "address" in jsonObject ->
                    InputVenueMessageContent.serializer()
                else -> error("Cannot serialize/deserialize object as InputTextMessageContent:\n\t$this")
            }
        }

}
