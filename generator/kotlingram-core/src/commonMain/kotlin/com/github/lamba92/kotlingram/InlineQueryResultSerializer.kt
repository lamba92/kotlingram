package com.github.lamba92.kotlingram

import com.github.lamba92.kotlingram.api.generated.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object InlineQueryResultSerializer : JsonContentPolymorphicSerializer<InlineQueryResult>(InlineQueryResult::class) {

    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out InlineQueryResult> =
        with(element) {
            when (jsonObject["type"]?.jsonPrimitive?.content) {
                "audio" -> when {
                    "audio_file_id" in jsonObject -> InlineQueryResultCachedAudio.serializer()
                    else -> InlineQueryResultAudio.serializer()
                }
                "document" -> when {
                    "document_file_id" in jsonObject -> InlineQueryResultCachedDocument.serializer()
                    else -> InlineQueryResultDocument.serializer()
                }
                "gif" -> when {
                    "gif_file_id" in jsonObject -> InlineQueryResultCachedGif.serializer()
                    else -> InlineQueryResultGif.serializer()
                }
                "mpeg4_gif" -> when {
                    "mpeg4_file_id" in jsonObject -> InlineQueryResultCachedMpeg4Gif.serializer()
                    else -> InlineQueryResultMpeg4Gif.serializer()
                }
                "photo" -> when {
                    "photo_file_id" in jsonObject -> InlineQueryResultCachedPhoto.serializer()
                    else -> InlineQueryResultPhoto.serializer()
                }
                "video" -> when {
                    "video_file_id" in jsonObject -> InlineQueryResultCachedVideo.serializer()
                    else -> InlineQueryResultVideo.serializer()
                }
                "voice" -> when {
                    "voice_file_id" in jsonObject -> InlineQueryResultCachedVoice.serializer()
                    else -> InlineQueryResultVideo.serializer()
                }
                "sticker" -> InlineQueryResultCachedSticker.serializer()
                "article" -> InlineQueryResultArticle.serializer()
                "contact" -> InlineQueryResultContact.serializer()
                "game" -> InlineQueryResultGame.serializer()
                "venue" -> InlineQueryResultVenue.serializer()
                else -> error("Cannot serialize/deserialize object as InlineQueryResult:\n\t$this")
            }
        }
}
