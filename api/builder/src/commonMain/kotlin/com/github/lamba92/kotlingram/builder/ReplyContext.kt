package com.github.lamba92.kotlingram.builder

import com.github.lamba92.kotlingram.TelegramBotApiClient
import com.github.lamba92.kotlingram.api.generated.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

sealed class ReplyContext : DIAware {
    val api: TelegramBotApiClient by instance()
}

@TelegramBotsDSL
class InlineQueryContext(
    val inlineQuery: InlineQuery,
    override val di: DI,
) : ReplyContext()

@TelegramBotsDSL
class MessageContext(
    val message: Message,
    override val di: DI,
) : ReplyContext()

/**
 * Request body for [sendMessage]
 * @param chatId Unique identifier for the target chat or username of the target channel
 * (in the format @channelusername), default is the sender.
 * @param text Text of the message to be sent, 1-4096 characters after entities parsing
 * @param parseMode Mode for parsing entities in the message text. See formatting options for more details.
 * @param entities List of special entities that appear in message text, which can be specified instead
 * of parse_mode
 * @param disableWebPagePreview Disables link previews for links in this message
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to
 * message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom
 * reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun MessageContext.respondText(
    text: String,
    parseMode: String? = null,
    entities: List<MessageEntity> = emptyList(),
    disableWebPagePreview: Boolean = false,
    disableNotification: Boolean = false,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean = false,
    replyMarkup: ReplyMarkup? = null,
    chatId: Int = message.chat.id,
) {
    api.sendMessage(
        chatId,
        text,
        parseMode,
        entities,
        disableWebPagePreview,
        disableNotification,
        replyToMessageId,
        allowSendingWithoutReply,
        replyMarkup
    )
}

/**
 * Use this method to send photos. On success, the sent Message is returned.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format
 * @channelusername), default is the sender.
 * @param photo Photo to send. Pass a file_id as String to send a photo that exists on the Telegram servers
 * (recommended), pass an HTTP URL as a String for Telegram to get a photo from the Internet, or upload a new
 * photo using multipart/form-data. The photo must be at most 10 MB in size. The photo's width and height must
 * not exceed 10000 in total. Width and height ratio must be at most 20. More info on Sending Files »
 * @param caption Photo caption (may also be used when resending photos by file_id), 0-1024 characters after
 * entities parsing
 * @param parseMode Mode for parsing entities in the photo caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead of
 * parse_mode
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to
 * message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply
 * keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun MessageContext.respondPhoto(
    photo: String,
    caption: String? = null,
    parseMode: String? = null,
    captionEntities: List<MessageEntity> = emptyList(),
    disableNotification: Boolean = false,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean = false,
    replyMarkup: ReplyMarkup? = null,
    chatId: Int = message.chat.id,
) {
    api.sendPhoto(
        chatId,
        photo,
        caption,
        parseMode,
        captionEntities,
        disableNotification,
        replyToMessageId,
        allowSendingWithoutReply,
        replyMarkup
    )
}

/**
 * Use this method to send audio files, if you want Telegram clients to display them in the music player. Your
 * audio must be in the .MP3 or .M4A format. On success, the sent Message is returned. Bots can currently send
 * audio files of up to 50 MB in size, this limit may be changed in the future.
 * For sending voice messages, use the sendVoice method instead.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format
 * @channelusername), default is the sender.
 * @param audio Audio file to send. Pass a file_id as String to send an audio file that exists on the Telegram
 * servers (recommended), pass an HTTP URL as a String for Telegram to get an audio file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
 * @param caption Audio caption, 0-1024 characters after entities parsing
 * @param parseMode Mode for parsing entities in the audio caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead
 * of parse_mode
 * @param duration Duration of the audio in seconds
 * @param performer Performer
 * @param title Track name
 * @param thumb Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported
 * server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to
 * message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply
 * keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun MessageContext.respondAudio(
    audio: String,
    caption: String? = null,
    parseMode: String? = null,
    captionEntities: List<MessageEntity> = emptyList(),
    duration: Int? = null,
    performer: String? = null,
    title: String? = null,
    thumb: String? = null,
    disableNotification: Boolean = false,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean = false,
    replyMarkup: ReplyMarkup? = null,
    chatId: Int = message.chat.id,
) {
    api.sendAudio(
        chatId,
        audio,
        caption,
        parseMode,
        captionEntities,
        duration,
        performer,
        title,
        thumb,
        disableNotification,
        replyToMessageId,
        allowSendingWithoutReply,
        replyMarkup
    )
}

suspend fun InlineQueryContext.respond(
    results: List<InlineQueryResult> = emptyList(),
    cacheTime: Int? = null,
    isPersonal: Boolean = false,
    nextOffset: String? = null,
    switchPmText: String? = null,
    switchPmParameter: String? = null,
    inlineQueryId: String = inlineQuery.id,
) {
    api.answerInlineQuery(inlineQueryId, results, cacheTime, isPersonal, nextOffset, switchPmText, switchPmParameter)
}
