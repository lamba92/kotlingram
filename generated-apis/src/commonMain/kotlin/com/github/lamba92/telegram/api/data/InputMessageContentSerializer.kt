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

object InputMessageContentSerializer : KSerializer<InputMessageContent> {
    override val descriptor =
        buildClassSerialDescriptor("InputMessageContent") {
            element<String>("phone_number", isOptional = true)
            element<String>("first_name", isOptional = true)
            element<String>("last_name", isOptional = true)
            element<String>("vcard", isOptional = true)
            element<Float>("latitude", isOptional = true)
            element<Float>("longitude", isOptional = true)
            element<Float>("horizontal_accuracy", isOptional = true)
            element<Int>("live_period", isOptional = true)
            element<Int>("heading", isOptional = true)
            element<Int>("proximity_alert_radius", isOptional = true)
            element<String>("message_text", isOptional = true)
            element<String>("parse_mode", isOptional = true)
            element<List<MessageEntity>>("entities", isOptional = true)
            element<Boolean>("disable_web_page_preview", isOptional = true)
            element<String>("title", isOptional = true)
            element<String>("address", isOptional = true)
            element<String>("foursquare_id", isOptional = true)
            element<String>("foursquare_type", isOptional = true)
            element<String>("google_place_id", isOptional = true)
            element<String>("google_place_type", isOptional = true)
        }

    override fun deserialize(decoder: Decoder): InputMessageContent =
        decoder.decodeStructure(descriptor) {
            var phoneNumber: String? = null
            var firstName: String? = null
            var lastName: String? = null
            var vcard: String? = null
            var latitude: Float? = null
            var longitude: Float? = null
            var horizontalAccuracy: Float? = null
            var livePeriod: Int? = null
            var heading: Int? = null
            var messageText: String? = null
            var parseMode: String? = null
            var entities = emptyList<MessageEntity>()
            var proximityAlertRadius: Int? = null
            var disableWebPagePreview: Boolean? = null
            var title: String? = null
            var address: String? = null
            var foursquareId: String? = null
            var foursquareType: String? = null
            var googlePlaceId: String? = null
            var googlePlaceType: String? = null
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> phoneNumber = decodeStringElement(descriptor, 0)
                    1 -> firstName = decodeStringElement(descriptor, 1)
                    2 -> lastName = decodeStringElement(descriptor, 2)
                    3 -> vcard = decodeStringElement(descriptor, 3)
                    4 -> latitude = decodeFloatElement(descriptor, 4)
                    5 -> longitude = decodeFloatElement(descriptor, 5)
                    6 -> horizontalAccuracy = decodeFloatElement(descriptor, 6)
                    7 -> livePeriod = decodeIntElement(descriptor, 7)
                    8 -> heading = decodeIntElement(descriptor, 8)
                    9 -> messageText = decodeStringElement(descriptor, 9)
                    10 -> parseMode = decodeStringElement(descriptor, 10)
                    11 ->
                        entities = decodeSerializableElement(descriptor, 11, ListSerializer(MessageEntity.serializer()))
                    12 -> proximityAlertRadius = decodeIntElement(descriptor, 11)
                    13 -> disableWebPagePreview = decodeBooleanElement(descriptor, 12)
                    14 -> title = decodeStringElement(descriptor, 13)
                    15 -> address = decodeStringElement(descriptor, 14)
                    16 -> foursquareId = decodeStringElement(descriptor, 15)
                    17 -> foursquareType = decodeStringElement(descriptor, 16)
                    18 -> googlePlaceId = decodeStringElement(descriptor, 17)
                    19 -> googlePlaceType = decodeStringElement(descriptor, 18)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            when {
                phoneNumber != null && firstName != null ->
                    InputContactMessageContent(phoneNumber, firstName, lastName, vcard)
                latitude != null && longitude != null && title == null && address == null ->
                    InputLocationMessageContent(
                        latitude,
                        longitude,
                        horizontalAccuracy,
                        livePeriod,
                        heading,
                        proximityAlertRadius
                    )
                messageText != null ->
                    InputTextMessageContent(messageText, parseMode, entities, disableWebPagePreview)
                latitude != null && longitude != null && title != null && address != null ->
                    InputVenueMessageContent(
                        latitude,
                        longitude,
                        title,
                        address,
                        foursquareId,
                        foursquareType,
                        googlePlaceId,
                        googlePlaceType
                    )
                else -> error("No suitable child found for InputMessageContent")
            }
        }

    override fun serialize(encoder: Encoder, value: InputMessageContent) {
        when (value) {
            is InputTextMessageContent ->
                encoder.encodeSerializableValue(InputTextMessageContent.serializer(), value)
            is InputLocationMessageContent ->
                encoder.encodeSerializableValue(InputLocationMessageContent.serializer(), value)
            is InputVenueMessageContent ->
                encoder.encodeSerializableValue(InputVenueMessageContent.serializer(), value)
            is InputContactMessageContent ->
                encoder.encodeSerializableValue(InputContactMessageContent.serializer(), value)
        }
    }

}
