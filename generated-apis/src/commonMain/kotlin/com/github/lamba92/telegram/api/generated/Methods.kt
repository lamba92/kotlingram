package com.github.lamba92.telegram.api.generated

import com.github.lamba92.telegram.api.PassportElementError
import com.github.lamba92.telegram.api.TelegramBotApiClient
import com.github.lamba92.telegram.api.TelegramResponse
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Request body for [getUpdates]
 * @param offset Identifier of the first update to be returned. Must be greater by one than the highest among the identifiers of previously received updates. By default, updates starting with the earliest unconfirmed update are returned. An update is considered confirmed as soon as getUpdates is called with an offset higher than its update_id. The negative offset can be specified to retrieve updates starting from -offset update from the end of the updates queue. All previous updates will forgotten.
 * @param limit Limits the number of updates to be retrieved. Values between 1-100 are accepted. Defaults to 100.
 * @param timeout Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling. Should be positive, short polling should be used for testing purposes only.
 * @param allowedUpdates A JSON-serialized list of the update types you want your bot to receive. For example, specify [“message”, “edited_channel_post”, “callback_query”] to only receive updates of these types. See Update for a complete list of available update types. Specify an empty list to receive all updates regardless of type (default). If not specified, the previous setting will be used. Please note that this parameter doesn't affect updates created before the call to the getUpdates, so unwanted updates may be received for a short period of time.
 */
@Serializable
data class GetUpdatesRequest(
	val offset: Int? = null,
	val limit: Int? = null,
	val timeout: Int? = null,
	@SerialName("allowed_updates") val allowedUpdates: List<String> = emptyList()
)

/**
 * Use this method to receive incoming updates using long polling (wiki). An Array of Update objects is returned.
 * Notes 1. This method will not work if an outgoing webhook is set up. 2. In order to avoid getting duplicate updates, recalculate offset after each server response.
 */
suspend fun TelegramBotApiClient.getUpdates(requestBody: GetUpdatesRequest) =
    httpClient.post<TelegramResponse<List<Update>>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "getUpdates")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to receive incoming updates using long polling (wiki). An Array of Update objects is returned.
 * Notes 1. This method will not work if an outgoing webhook is set up. 2. In order to avoid getting duplicate updates, recalculate offset after each server response.
 * @param offset Identifier of the first update to be returned. Must be greater by one than the highest among the identifiers of previously received updates. By default, updates starting with the earliest unconfirmed update are returned. An update is considered confirmed as soon as getUpdates is called with an offset higher than its update_id. The negative offset can be specified to retrieve updates starting from -offset update from the end of the updates queue. All previous updates will forgotten.
 * @param limit Limits the number of updates to be retrieved. Values between 1-100 are accepted. Defaults to 100.
 * @param timeout Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling. Should be positive, short polling should be used for testing purposes only.
 * @param allowedUpdates A JSON-serialized list of the update types you want your bot to receive. For example, specify [“message”, “edited_channel_post”, “callback_query”] to only receive updates of these types. See Update for a complete list of available update types. Specify an empty list to receive all updates regardless of type (default). If not specified, the previous setting will be used. Please note that this parameter doesn't affect updates created before the call to the getUpdates, so unwanted updates may be received for a short period of time.
 */
suspend fun TelegramBotApiClient.getUpdates(
    offset: Int? = null,
    limit: Int? = null,
    timeout: Int? = null,
    allowedUpdates: List<String> = emptyList(),
) =
    getUpdates(
        GetUpdatesRequest(
            offset,
            limit,
            timeout,
            allowedUpdates
        )
    )

/**
 * Request body for [setWebhook]
 * @param url HTTPS url to send updates to. Use an empty string to remove webhook integration
 * @param certificate Upload your public key certificate so that the root certificate in use can be checked. See our self-signed guide for details.
 * @param ipAddress The fixed IP address which will be used to send webhook requests instead of the IP address resolved through DNS
 * @param maxConnections Maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery, 1-100. Defaults to 40. Use lower values to limit the load on your bot's server, and higher values to increase your bot's throughput.
 * @param allowedUpdates A JSON-serialized list of the update types you want your bot to receive. For example, specify [“message”, “edited_channel_post”, “callback_query”] to only receive updates of these types. See Update for a complete list of available update types. Specify an empty list to receive all updates regardless of type (default). If not specified, the previous setting will be used. Please note that this parameter doesn't affect updates created before the call to the setWebhook, so unwanted updates may be received for a short period of time.
 * @param dropPendingUpdates Pass True to drop all pending updates
 */
@Serializable
data class SetWebhookRequest(
	val url: String,
	val certificate: String? = null,
	@SerialName("ip_address") val ipAddress: String? = null,
	@SerialName("max_connections") val maxConnections: Int? = null,
	@SerialName("allowed_updates") val allowedUpdates: List<String> = emptyList(),
	@SerialName("drop_pending_updates") val dropPendingUpdates: Boolean? = null
)

/**
 * Use this method to specify a url and receive incoming updates via an outgoing webhook. Whenever there is an update for the bot, we will send an HTTPS POST request to the specified url, containing a JSON-serialized Update. In case of an unsuccessful request, we will give up after a reasonable amount of attempts. Returns True on success.
 * If you'd like to make sure that the Webhook request comes from Telegram, we recommend using a secret path in the URL, e.g. https://www.example.com/<token>. Since nobody else knows your bot's token, you can be pretty sure it's us.
 * Notes 1. You will not be able to receive updates using getUpdates for as long as an outgoing webhook is set up. 2. To use a self-signed certificate, you need to upload your public key certificate using certificate parameter. Please upload as InputFile, sending a String will not work. 3. Ports currently supported for Webhooks: 443, 80, 88, 8443. NEW! If you're having any trouble setting up webhooks, please check out this amazing guide to Webhooks.
 */
suspend fun TelegramBotApiClient.setWebhook(requestBody: SetWebhookRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "setWebhook")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to specify a url and receive incoming updates via an outgoing webhook. Whenever there is an update for the bot, we will send an HTTPS POST request to the specified url, containing a JSON-serialized Update. In case of an unsuccessful request, we will give up after a reasonable amount of attempts. Returns True on success.
 * If you'd like to make sure that the Webhook request comes from Telegram, we recommend using a secret path in the URL, e.g. https://www.example.com/<token>. Since nobody else knows your bot's token, you can be pretty sure it's us.
 * Notes 1. You will not be able to receive updates using getUpdates for as long as an outgoing webhook is set up. 2. To use a self-signed certificate, you need to upload your public key certificate using certificate parameter. Please upload as InputFile, sending a String will not work. 3. Ports currently supported for Webhooks: 443, 80, 88, 8443. NEW! If you're having any trouble setting up webhooks, please check out this amazing guide to Webhooks.
 * @param url HTTPS url to send updates to. Use an empty string to remove webhook integration
 * @param certificate Upload your public key certificate so that the root certificate in use can be checked. See our self-signed guide for details.
 * @param ipAddress The fixed IP address which will be used to send webhook requests instead of the IP address resolved through DNS
 * @param maxConnections Maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery, 1-100. Defaults to 40. Use lower values to limit the load on your bot's server, and higher values to increase your bot's throughput.
 * @param allowedUpdates A JSON-serialized list of the update types you want your bot to receive. For example, specify [“message”, “edited_channel_post”, “callback_query”] to only receive updates of these types. See Update for a complete list of available update types. Specify an empty list to receive all updates regardless of type (default). If not specified, the previous setting will be used. Please note that this parameter doesn't affect updates created before the call to the setWebhook, so unwanted updates may be received for a short period of time.
 * @param dropPendingUpdates Pass True to drop all pending updates
 */
suspend fun TelegramBotApiClient.setWebhook(
    url: String,
    certificate: String? = null,
    ipAddress: String? = null,
    maxConnections: Int? = null,
    allowedUpdates: List<String> = emptyList(),
    dropPendingUpdates: Boolean? = null,
) =
    setWebhook(
        SetWebhookRequest(
            url,
            certificate,
            ipAddress,
            maxConnections,
            allowedUpdates,
            dropPendingUpdates
        )
    )

/**
 * Request body for [deleteWebhook]
 * @param dropPendingUpdates Pass True to drop all pending updates
 */
@Serializable
data class DeleteWebhookRequest(
	@SerialName("drop_pending_updates") val dropPendingUpdates: Boolean? = null
)

/**
 * Use this method to remove webhook integration if you decide to switch back to getUpdates. Returns True on success.
 */
suspend fun TelegramBotApiClient.deleteWebhook(requestBody: DeleteWebhookRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "deleteWebhook")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to remove webhook integration if you decide to switch back to getUpdates. Returns True on success.
 * @param dropPendingUpdates Pass True to drop all pending updates
 */
suspend fun TelegramBotApiClient.deleteWebhook(
    dropPendingUpdates: Boolean? = null,
) =
    deleteWebhook(
        DeleteWebhookRequest(
            dropPendingUpdates
        )
    )

/**
 * Request body for [sendMessage]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param text Text of the message to be sent, 1-4096 characters after entities parsing
 * @param parseMode Mode for parsing entities in the message text. See formatting options for more details.
 * @param entities List of special entities that appear in message text, which can be specified instead of parse_mode
 * @param disableWebPagePreview Disables link previews for links in this message
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
@Serializable
data class SendMessageRequest(
	@SerialName("chat_id") val chatId: Int,
	val text: String,
	@SerialName("parse_mode") val parseMode: String? = null,
	val entities: List<MessageEntity> = emptyList(),
	@SerialName("disable_web_page_preview") val disableWebPagePreview: Boolean? = null,
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: ReplyMarkup? = null
)

/**
 * Use this method to send text messages. On success, the sent Message is returned.
 */
suspend fun TelegramBotApiClient.sendMessage(requestBody: SendMessageRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendMessage")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send text messages. On success, the sent Message is returned.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param text Text of the message to be sent, 1-4096 characters after entities parsing
 * @param parseMode Mode for parsing entities in the message text. See formatting options for more details.
 * @param entities List of special entities that appear in message text, which can be specified instead of parse_mode
 * @param disableWebPagePreview Disables link previews for links in this message
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun TelegramBotApiClient.sendMessage(
    chatId: Int,
    text: String,
    parseMode: String? = null,
    entities: List<MessageEntity> = emptyList(),
    disableWebPagePreview: Boolean? = null,
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: ReplyMarkup? = null,
) =
    sendMessage(
        SendMessageRequest(
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
    )

/**
 * Request body for [forwardMessage]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param fromChatId Unique identifier for the chat where the original message was sent (or channel username in the format @channelusername)
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param messageId Message identifier in the chat specified in from_chat_id
 */
@Serializable
data class ForwardMessageRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("from_chat_id") val fromChatId: Int,
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("message_id") val messageId: Int
)

/**
 * Use this method to forward messages of any kind. On success, the sent Message is returned.
 */
suspend fun TelegramBotApiClient.forwardMessage(requestBody: ForwardMessageRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "forwardMessage")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to forward messages of any kind. On success, the sent Message is returned.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param fromChatId Unique identifier for the chat where the original message was sent (or channel username in the format @channelusername)
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param messageId Message identifier in the chat specified in from_chat_id
 */
suspend fun TelegramBotApiClient.forwardMessage(
    chatId: Int,
    fromChatId: Int,
    disableNotification: Boolean? = null,
    messageId: Int,
) =
    forwardMessage(
        ForwardMessageRequest(
            chatId,
            fromChatId,
            disableNotification,
            messageId
        )
    )

/**
 * Request body for [copyMessage]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param fromChatId Unique identifier for the chat where the original message was sent (or channel username in the format @channelusername)
 * @param messageId Message identifier in the chat specified in from_chat_id
 * @param caption New caption for media, 0-1024 characters after entities parsing. If not specified, the original caption is kept
 * @param parseMode Mode for parsing entities in the new caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the new caption, which can be specified instead of parse_mode
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
@Serializable
data class CopyMessageRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("from_chat_id") val fromChatId: Int,
	@SerialName("message_id") val messageId: Int,
	val caption: String? = null,
	@SerialName("parse_mode") val parseMode: String? = null,
	@SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: ReplyMarkup? = null
)

/**
 * Use this method to copy messages of any kind. The method is analogous to the method forwardMessages, but the copied message doesn't have a link to the original message. Returns the MessageId of the sent message on success.
 */
suspend fun TelegramBotApiClient.copyMessage(requestBody: CopyMessageRequest) =
    httpClient.post<TelegramResponse<MessageId>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "copyMessage")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to copy messages of any kind. The method is analogous to the method forwardMessages, but the copied message doesn't have a link to the original message. Returns the MessageId of the sent message on success.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param fromChatId Unique identifier for the chat where the original message was sent (or channel username in the format @channelusername)
 * @param messageId Message identifier in the chat specified in from_chat_id
 * @param caption New caption for media, 0-1024 characters after entities parsing. If not specified, the original caption is kept
 * @param parseMode Mode for parsing entities in the new caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the new caption, which can be specified instead of parse_mode
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun TelegramBotApiClient.copyMessage(
    chatId: Int,
    fromChatId: Int,
    messageId: Int,
    caption: String? = null,
    parseMode: String? = null,
    captionEntities: List<MessageEntity> = emptyList(),
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: ReplyMarkup? = null,
) =
    copyMessage(
        CopyMessageRequest(
            chatId,
            fromChatId,
            messageId,
            caption,
            parseMode,
            captionEntities,
            disableNotification,
            replyToMessageId,
            allowSendingWithoutReply,
            replyMarkup
        )
    )

/**
 * Request body for [sendPhoto]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param photo Photo to send. Pass a file_id as String to send a photo that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a photo from the Internet, or upload a new photo using multipart/form-data. The photo must be at most 10 MB in size. The photo's width and height must not exceed 10000 in total. Width and height ratio must be at most 20. More info on Sending Files »
 * @param caption Photo caption (may also be used when resending photos by file_id), 0-1024 characters after entities parsing
 * @param parseMode Mode for parsing entities in the photo caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
@Serializable
data class SendPhotoRequest(
	@SerialName("chat_id") val chatId: Int,
	val photo: String,
	val caption: String? = null,
	@SerialName("parse_mode") val parseMode: String? = null,
	@SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: ReplyMarkup? = null
)

/**
 * Use this method to send photos. On success, the sent Message is returned.
 */
suspend fun TelegramBotApiClient.sendPhoto(requestBody: SendPhotoRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendPhoto")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send photos. On success, the sent Message is returned.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param photo Photo to send. Pass a file_id as String to send a photo that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a photo from the Internet, or upload a new photo using multipart/form-data. The photo must be at most 10 MB in size. The photo's width and height must not exceed 10000 in total. Width and height ratio must be at most 20. More info on Sending Files »
 * @param caption Photo caption (may also be used when resending photos by file_id), 0-1024 characters after entities parsing
 * @param parseMode Mode for parsing entities in the photo caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun TelegramBotApiClient.sendPhoto(
    chatId: Int,
    photo: String,
    caption: String? = null,
    parseMode: String? = null,
    captionEntities: List<MessageEntity> = emptyList(),
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: ReplyMarkup? = null,
) =
    sendPhoto(
        SendPhotoRequest(
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
    )

/**
 * Request body for [sendAudio]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param audio Audio file to send. Pass a file_id as String to send an audio file that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get an audio file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
 * @param caption Audio caption, 0-1024 characters after entities parsing
 * @param parseMode Mode for parsing entities in the audio caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param duration Duration of the audio in seconds
 * @param performer Performer
 * @param title Track name
 * @param thumb Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
@Serializable
data class SendAudioRequest(
	@SerialName("chat_id") val chatId: Int,
	val audio: String,
	val caption: String? = null,
	@SerialName("parse_mode") val parseMode: String? = null,
	@SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
	val duration: Int? = null,
	val performer: String? = null,
	val title: String? = null,
	val thumb: String? = null,
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: ReplyMarkup? = null
)

/**
 * Use this method to send audio files, if you want Telegram clients to display them in the music player. Your audio must be in the .MP3 or .M4A format. On success, the sent Message is returned. Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
 * For sending voice messages, use the sendVoice method instead.
 */
suspend fun TelegramBotApiClient.sendAudio(requestBody: SendAudioRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendAudio")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send audio files, if you want Telegram clients to display them in the music player. Your audio must be in the .MP3 or .M4A format. On success, the sent Message is returned. Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
 * For sending voice messages, use the sendVoice method instead.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param audio Audio file to send. Pass a file_id as String to send an audio file that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get an audio file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
 * @param caption Audio caption, 0-1024 characters after entities parsing
 * @param parseMode Mode for parsing entities in the audio caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param duration Duration of the audio in seconds
 * @param performer Performer
 * @param title Track name
 * @param thumb Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun TelegramBotApiClient.sendAudio(
    chatId: Int,
    audio: String,
    caption: String? = null,
    parseMode: String? = null,
    captionEntities: List<MessageEntity> = emptyList(),
    duration: Int? = null,
    performer: String? = null,
    title: String? = null,
    thumb: String? = null,
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: ReplyMarkup? = null,
) =
    sendAudio(
        SendAudioRequest(
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
    )

/**
 * Request body for [sendDocument]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param document File to send. Pass a file_id as String to send a file that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
 * @param thumb Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param caption Document caption (may also be used when resending documents by file_id), 0-1024 characters after entities parsing
 * @param parseMode Mode for parsing entities in the document caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param disableContentTypeDetection Disables automatic server-side content type detection for files uploaded using multipart/form-data
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
@Serializable
data class SendDocumentRequest(
	@SerialName("chat_id") val chatId: Int,
	val document: String,
	val thumb: String? = null,
	val caption: String? = null,
	@SerialName("parse_mode") val parseMode: String? = null,
	@SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
	@SerialName("disable_content_type_detection") val disableContentTypeDetection: Boolean? = null,
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: ReplyMarkup? = null
)

/**
 * Use this method to send general files. On success, the sent Message is returned. Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
 */
suspend fun TelegramBotApiClient.sendDocument(requestBody: SendDocumentRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendDocument")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send general files. On success, the sent Message is returned. Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param document File to send. Pass a file_id as String to send a file that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
 * @param thumb Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param caption Document caption (may also be used when resending documents by file_id), 0-1024 characters after entities parsing
 * @param parseMode Mode for parsing entities in the document caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param disableContentTypeDetection Disables automatic server-side content type detection for files uploaded using multipart/form-data
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun TelegramBotApiClient.sendDocument(
    chatId: Int,
    document: String,
    thumb: String? = null,
    caption: String? = null,
    parseMode: String? = null,
    captionEntities: List<MessageEntity> = emptyList(),
    disableContentTypeDetection: Boolean? = null,
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: ReplyMarkup? = null,
) =
    sendDocument(
        SendDocumentRequest(
            chatId,
            document,
            thumb,
            caption,
            parseMode,
            captionEntities,
            disableContentTypeDetection,
            disableNotification,
            replyToMessageId,
            allowSendingWithoutReply,
            replyMarkup
        )
    )

/**
 * Request body for [sendVideo]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param video Video to send. Pass a file_id as String to send a video that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a video from the Internet, or upload a new video using multipart/form-data. More info on Sending Files »
 * @param duration Duration of sent video in seconds
 * @param width Video width
 * @param height Video height
 * @param thumb Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param caption Video caption (may also be used when resending videos by file_id), 0-1024 characters after entities parsing
 * @param parseMode Mode for parsing entities in the video caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param supportsStreaming Pass True, if the uploaded video is suitable for streaming
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
@Serializable
data class SendVideoRequest(
	@SerialName("chat_id") val chatId: Int,
	val video: String,
	val duration: Int? = null,
	val width: Int? = null,
	val height: Int? = null,
	val thumb: String? = null,
	val caption: String? = null,
	@SerialName("parse_mode") val parseMode: String? = null,
	@SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
	@SerialName("supports_streaming") val supportsStreaming: Boolean? = null,
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: ReplyMarkup? = null
)

/**
 * Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document). On success, the sent Message is returned. Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.
 */
suspend fun TelegramBotApiClient.sendVideo(requestBody: SendVideoRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendVideo")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document). On success, the sent Message is returned. Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param video Video to send. Pass a file_id as String to send a video that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a video from the Internet, or upload a new video using multipart/form-data. More info on Sending Files »
 * @param duration Duration of sent video in seconds
 * @param width Video width
 * @param height Video height
 * @param thumb Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param caption Video caption (may also be used when resending videos by file_id), 0-1024 characters after entities parsing
 * @param parseMode Mode for parsing entities in the video caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param supportsStreaming Pass True, if the uploaded video is suitable for streaming
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun TelegramBotApiClient.sendVideo(
    chatId: Int,
    video: String,
    duration: Int? = null,
    width: Int? = null,
    height: Int? = null,
    thumb: String? = null,
    caption: String? = null,
    parseMode: String? = null,
    captionEntities: List<MessageEntity> = emptyList(),
    supportsStreaming: Boolean? = null,
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: ReplyMarkup? = null,
) =
    sendVideo(
        SendVideoRequest(
            chatId,
            video,
            duration,
            width,
            height,
            thumb,
            caption,
            parseMode,
            captionEntities,
            supportsStreaming,
            disableNotification,
            replyToMessageId,
            allowSendingWithoutReply,
            replyMarkup
        )
    )

/**
 * Request body for [sendAnimation]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param animation Animation to send. Pass a file_id as String to send an animation that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get an animation from the Internet, or upload a new animation using multipart/form-data. More info on Sending Files »
 * @param duration Duration of sent animation in seconds
 * @param width Animation width
 * @param height Animation height
 * @param thumb Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param caption Animation caption (may also be used when resending animation by file_id), 0-1024 characters after entities parsing
 * @param parseMode Mode for parsing entities in the animation caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
@Serializable
data class SendAnimationRequest(
	@SerialName("chat_id") val chatId: Int,
	val animation: String,
	val duration: Int? = null,
	val width: Int? = null,
	val height: Int? = null,
	val thumb: String? = null,
	val caption: String? = null,
	@SerialName("parse_mode") val parseMode: String? = null,
	@SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: ReplyMarkup? = null
)

/**
 * Use this method to send animation files (GIF or H.264/MPEG-4 AVC video without sound). On success, the sent Message is returned. Bots can currently send animation files of up to 50 MB in size, this limit may be changed in the future.
 */
suspend fun TelegramBotApiClient.sendAnimation(requestBody: SendAnimationRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendAnimation")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send animation files (GIF or H.264/MPEG-4 AVC video without sound). On success, the sent Message is returned. Bots can currently send animation files of up to 50 MB in size, this limit may be changed in the future.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param animation Animation to send. Pass a file_id as String to send an animation that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get an animation from the Internet, or upload a new animation using multipart/form-data. More info on Sending Files »
 * @param duration Duration of sent animation in seconds
 * @param width Animation width
 * @param height Animation height
 * @param thumb Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param caption Animation caption (may also be used when resending animation by file_id), 0-1024 characters after entities parsing
 * @param parseMode Mode for parsing entities in the animation caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun TelegramBotApiClient.sendAnimation(
    chatId: Int,
    animation: String,
    duration: Int? = null,
    width: Int? = null,
    height: Int? = null,
    thumb: String? = null,
    caption: String? = null,
    parseMode: String? = null,
    captionEntities: List<MessageEntity> = emptyList(),
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: ReplyMarkup? = null,
) =
    sendAnimation(
        SendAnimationRequest(
            chatId,
            animation,
            duration,
            width,
            height,
            thumb,
            caption,
            parseMode,
            captionEntities,
            disableNotification,
            replyToMessageId,
            allowSendingWithoutReply,
            replyMarkup
        )
    )

/**
 * Request body for [sendVoice]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param voice Audio file to send. Pass a file_id as String to send a file that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
 * @param caption Voice message caption, 0-1024 characters after entities parsing
 * @param parseMode Mode for parsing entities in the voice message caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param duration Duration of the voice message in seconds
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
@Serializable
data class SendVoiceRequest(
	@SerialName("chat_id") val chatId: Int,
	val voice: String,
	val caption: String? = null,
	@SerialName("parse_mode") val parseMode: String? = null,
	@SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
	val duration: Int? = null,
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: ReplyMarkup? = null
)

/**
 * Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message. For this to work, your audio must be in an .OGG file encoded with OPUS (other formats may be sent as Audio or Document). On success, the sent Message is returned. Bots can currently send voice messages of up to 50 MB in size, this limit may be changed in the future.
 */
suspend fun TelegramBotApiClient.sendVoice(requestBody: SendVoiceRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendVoice")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message. For this to work, your audio must be in an .OGG file encoded with OPUS (other formats may be sent as Audio or Document). On success, the sent Message is returned. Bots can currently send voice messages of up to 50 MB in size, this limit may be changed in the future.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param voice Audio file to send. Pass a file_id as String to send a file that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
 * @param caption Voice message caption, 0-1024 characters after entities parsing
 * @param parseMode Mode for parsing entities in the voice message caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param duration Duration of the voice message in seconds
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun TelegramBotApiClient.sendVoice(
    chatId: Int,
    voice: String,
    caption: String? = null,
    parseMode: String? = null,
    captionEntities: List<MessageEntity> = emptyList(),
    duration: Int? = null,
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: ReplyMarkup? = null,
) =
    sendVoice(
        SendVoiceRequest(
            chatId,
            voice,
            caption,
            parseMode,
            captionEntities,
            duration,
            disableNotification,
            replyToMessageId,
            allowSendingWithoutReply,
            replyMarkup
        )
    )

/**
 * Request body for [sendVideoNote]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param videoNote Video note to send. Pass a file_id as String to send a video note that exists on the Telegram servers (recommended) or upload a new video using multipart/form-data. More info on Sending Files ». Sending video notes by a URL is currently unsupported
 * @param duration Duration of sent video in seconds
 * @param length Video width and height, i.e. diameter of the video message
 * @param thumb Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
@Serializable
data class SendVideoNoteRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("video_note") val videoNote: String,
	val duration: Int? = null,
	val length: Int? = null,
	val thumb: String? = null,
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: ReplyMarkup? = null
)

/**
 * As of v.4.0, Telegram clients support rounded square mp4 videos of up to 1 minute long. Use this method to send video messages. On success, the sent Message is returned.
 */
suspend fun TelegramBotApiClient.sendVideoNote(requestBody: SendVideoNoteRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendVideoNote")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * As of v.4.0, Telegram clients support rounded square mp4 videos of up to 1 minute long. Use this method to send video messages. On success, the sent Message is returned.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param videoNote Video note to send. Pass a file_id as String to send a video note that exists on the Telegram servers (recommended) or upload a new video using multipart/form-data. More info on Sending Files ». Sending video notes by a URL is currently unsupported
 * @param duration Duration of sent video in seconds
 * @param length Video width and height, i.e. diameter of the video message
 * @param thumb Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun TelegramBotApiClient.sendVideoNote(
    chatId: Int,
    videoNote: String,
    duration: Int? = null,
    length: Int? = null,
    thumb: String? = null,
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: ReplyMarkup? = null,
) =
    sendVideoNote(
        SendVideoNoteRequest(
            chatId,
            videoNote,
            duration,
            length,
            thumb,
            disableNotification,
            replyToMessageId,
            allowSendingWithoutReply,
            replyMarkup
        )
    )

/**
 * Request body for [sendMediaGroup]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param media A JSON-serialized array describing messages to be sent, must include 2-10 items
 * @param disableNotification Sends messages silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the messages are a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 */
@Serializable
data class SendMediaGroupRequest(
	@SerialName("chat_id") val chatId: Int,
	val media: List<InputMedia> = emptyList(),
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null
)

/**
 * Use this method to send a group of photos, videos, documents or audios as an album. Documents and audio files can be only grouped in an album with messages of the same type. On success, an array of Messages that were sent is returned.
 */
suspend fun TelegramBotApiClient.sendMediaGroup(requestBody: SendMediaGroupRequest) =
    httpClient.post<TelegramResponse<List<Message>>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendMediaGroup")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send a group of photos, videos, documents or audios as an album. Documents and audio files can be only grouped in an album with messages of the same type. On success, an array of Messages that were sent is returned.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param media A JSON-serialized array describing messages to be sent, must include 2-10 items
 * @param disableNotification Sends messages silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the messages are a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 */
suspend fun TelegramBotApiClient.sendMediaGroup(
    chatId: Int,
    media: List<InputMedia> = emptyList(),
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
) =
    sendMediaGroup(
        SendMediaGroupRequest(
            chatId,
            media,
            disableNotification,
            replyToMessageId,
            allowSendingWithoutReply
        )
    )

/**
 * Request body for [sendLocation]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param latitude Latitude of the location
 * @param longitude Longitude of the location
 * @param horizontalAccuracy The radius of uncertainty for the location, measured in meters; 0-1500
 * @param livePeriod Period in seconds for which the location will be updated (see Live Locations, should be between 60 and 86400.
 * @param heading For live locations, a direction in which the user is moving, in degrees. Must be between 1 and 360 if specified.
 * @param proximityAlertRadius For live locations, a maximum distance for proximity alerts about approaching another chat member, in meters. Must be between 1 and 100000 if specified.
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
@Serializable
data class SendLocationRequest(
	@SerialName("chat_id") val chatId: Int,
	val latitude: Float,
	val longitude: Float,
	@SerialName("horizontal_accuracy") val horizontalAccuracy: Float? = null,
	@SerialName("live_period") val livePeriod: Int? = null,
	val heading: Int? = null,
	@SerialName("proximity_alert_radius") val proximityAlertRadius: Int? = null,
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: ReplyMarkup? = null
)

/**
 * Use this method to send point on the map. On success, the sent Message is returned.
 */
suspend fun TelegramBotApiClient.sendLocation(requestBody: SendLocationRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendLocation")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send point on the map. On success, the sent Message is returned.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param latitude Latitude of the location
 * @param longitude Longitude of the location
 * @param horizontalAccuracy The radius of uncertainty for the location, measured in meters; 0-1500
 * @param livePeriod Period in seconds for which the location will be updated (see Live Locations, should be between 60 and 86400.
 * @param heading For live locations, a direction in which the user is moving, in degrees. Must be between 1 and 360 if specified.
 * @param proximityAlertRadius For live locations, a maximum distance for proximity alerts about approaching another chat member, in meters. Must be between 1 and 100000 if specified.
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun TelegramBotApiClient.sendLocation(
    chatId: Int,
    latitude: Float,
    longitude: Float,
    horizontalAccuracy: Float? = null,
    livePeriod: Int? = null,
    heading: Int? = null,
    proximityAlertRadius: Int? = null,
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: ReplyMarkup? = null,
) =
    sendLocation(
        SendLocationRequest(
            chatId,
            latitude,
            longitude,
            horizontalAccuracy,
            livePeriod,
            heading,
            proximityAlertRadius,
            disableNotification,
            replyToMessageId,
            allowSendingWithoutReply,
            replyMarkup
        )
    )

/**
 * Request body for [editMessageLiveLocation]
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Required if inline_message_id is not specified. Identifier of the message to edit
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param latitude Latitude of new location
 * @param longitude Longitude of new location
 * @param horizontalAccuracy The radius of uncertainty for the location, measured in meters; 0-1500
 * @param heading Direction in which the user is moving, in degrees. Must be between 1 and 360 if specified.
 * @param proximityAlertRadius Maximum distance for proximity alerts about approaching another chat member, in meters. Must be between 1 and 100000 if specified.
 * @param replyMarkup A JSON-serialized object for a new inline keyboard.
 */
@Serializable
data class EditMessageLiveLocationRequest(
	@SerialName("chat_id") val chatId: Int? = null,
	@SerialName("message_id") val messageId: Int? = null,
	@SerialName("inline_message_id") val inlineMessageId: String? = null,
	val latitude: Float,
	val longitude: Float,
	@SerialName("horizontal_accuracy") val horizontalAccuracy: Float? = null,
	val heading: Int? = null,
	@SerialName("proximity_alert_radius") val proximityAlertRadius: Int? = null,
	@SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null
)

/**
 * Use this method to edit live location messages. A location can be edited until its live_period expires or editing is explicitly disabled by a call to stopMessageLiveLocation. On success, if the edited message is not an inline message, the edited Message is returned, otherwise True is returned.
 */
suspend fun TelegramBotApiClient.editMessageLiveLocation(requestBody: EditMessageLiveLocationRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "editMessageLiveLocation")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to edit live location messages. A location can be edited until its live_period expires or editing is explicitly disabled by a call to stopMessageLiveLocation. On success, if the edited message is not an inline message, the edited Message is returned, otherwise True is returned.
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Required if inline_message_id is not specified. Identifier of the message to edit
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param latitude Latitude of new location
 * @param longitude Longitude of new location
 * @param horizontalAccuracy The radius of uncertainty for the location, measured in meters; 0-1500
 * @param heading Direction in which the user is moving, in degrees. Must be between 1 and 360 if specified.
 * @param proximityAlertRadius Maximum distance for proximity alerts about approaching another chat member, in meters. Must be between 1 and 100000 if specified.
 * @param replyMarkup A JSON-serialized object for a new inline keyboard.
 */
suspend fun TelegramBotApiClient.editMessageLiveLocation(
    chatId: Int? = null,
    messageId: Int? = null,
    inlineMessageId: String? = null,
    latitude: Float,
    longitude: Float,
    horizontalAccuracy: Float? = null,
    heading: Int? = null,
    proximityAlertRadius: Int? = null,
    replyMarkup: InlineKeyboardMarkup? = null,
) =
    editMessageLiveLocation(
        EditMessageLiveLocationRequest(
            chatId,
            messageId,
            inlineMessageId,
            latitude,
            longitude,
            horizontalAccuracy,
            heading,
            proximityAlertRadius,
            replyMarkup
        )
    )

/**
 * Request body for [stopMessageLiveLocation]
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Required if inline_message_id is not specified. Identifier of the message with live location to stop
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param replyMarkup A JSON-serialized object for a new inline keyboard.
 */
@Serializable
data class StopMessageLiveLocationRequest(
	@SerialName("chat_id") val chatId: Int? = null,
	@SerialName("message_id") val messageId: Int? = null,
	@SerialName("inline_message_id") val inlineMessageId: String? = null,
	@SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null
)

/**
 * Use this method to stop updating a live location message before live_period expires. On success, if the message was sent by the bot, the sent Message is returned, otherwise True is returned.
 */
suspend fun TelegramBotApiClient.stopMessageLiveLocation(requestBody: StopMessageLiveLocationRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "stopMessageLiveLocation")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to stop updating a live location message before live_period expires. On success, if the message was sent by the bot, the sent Message is returned, otherwise True is returned.
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Required if inline_message_id is not specified. Identifier of the message with live location to stop
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param replyMarkup A JSON-serialized object for a new inline keyboard.
 */
suspend fun TelegramBotApiClient.stopMessageLiveLocation(
    chatId: Int? = null,
    messageId: Int? = null,
    inlineMessageId: String? = null,
    replyMarkup: InlineKeyboardMarkup? = null,
) =
    stopMessageLiveLocation(
        StopMessageLiveLocationRequest(
            chatId,
            messageId,
            inlineMessageId,
            replyMarkup
        )
    )

/**
 * Request body for [sendVenue]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param latitude Latitude of the venue
 * @param longitude Longitude of the venue
 * @param title Name of the venue
 * @param address Address of the venue
 * @param foursquareId Foursquare identifier of the venue
 * @param foursquareType Foursquare type of the venue, if known. (For example, “arts_entertainment/default”, “arts_entertainment/aquarium” or “food/icecream”.)
 * @param googlePlaceId Google Places identifier of the venue
 * @param googlePlaceType Google Places type of the venue. (See supported types.)
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
@Serializable
data class SendVenueRequest(
	@SerialName("chat_id") val chatId: Int,
	val latitude: Float,
	val longitude: Float,
	val title: String,
	val address: String,
	@SerialName("foursquare_id") val foursquareId: String? = null,
	@SerialName("foursquare_type") val foursquareType: String? = null,
	@SerialName("google_place_id") val googlePlaceId: String? = null,
	@SerialName("google_place_type") val googlePlaceType: String? = null,
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: ReplyMarkup? = null
)

/**
 * Use this method to send information about a venue. On success, the sent Message is returned.
 */
suspend fun TelegramBotApiClient.sendVenue(requestBody: SendVenueRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendVenue")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send information about a venue. On success, the sent Message is returned.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param latitude Latitude of the venue
 * @param longitude Longitude of the venue
 * @param title Name of the venue
 * @param address Address of the venue
 * @param foursquareId Foursquare identifier of the venue
 * @param foursquareType Foursquare type of the venue, if known. (For example, “arts_entertainment/default”, “arts_entertainment/aquarium” or “food/icecream”.)
 * @param googlePlaceId Google Places identifier of the venue
 * @param googlePlaceType Google Places type of the venue. (See supported types.)
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun TelegramBotApiClient.sendVenue(
    chatId: Int,
    latitude: Float,
    longitude: Float,
    title: String,
    address: String,
    foursquareId: String? = null,
    foursquareType: String? = null,
    googlePlaceId: String? = null,
    googlePlaceType: String? = null,
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: ReplyMarkup? = null,
) =
    sendVenue(
        SendVenueRequest(
            chatId,
            latitude,
            longitude,
            title,
            address,
            foursquareId,
            foursquareType,
            googlePlaceId,
            googlePlaceType,
            disableNotification,
            replyToMessageId,
            allowSendingWithoutReply,
            replyMarkup
        )
    )

/**
 * Request body for [sendContact]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param phoneNumber Contact's phone number
 * @param firstName Contact's first name
 * @param lastName Contact's last name
 * @param vcard Additional data about the contact in the form of a vCard, 0-2048 bytes
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove keyboard or to force a reply from the user.
 */
@Serializable
data class SendContactRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("phone_number") val phoneNumber: String,
	@SerialName("first_name") val firstName: String,
	@SerialName("last_name") val lastName: String? = null,
	val vcard: String? = null,
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: ReplyMarkup? = null
)

/**
 * Use this method to send phone contacts. On success, the sent Message is returned.
 */
suspend fun TelegramBotApiClient.sendContact(requestBody: SendContactRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendContact")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send phone contacts. On success, the sent Message is returned.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param phoneNumber Contact's phone number
 * @param firstName Contact's first name
 * @param lastName Contact's last name
 * @param vcard Additional data about the contact in the form of a vCard, 0-2048 bytes
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove keyboard or to force a reply from the user.
 */
suspend fun TelegramBotApiClient.sendContact(
    chatId: Int,
    phoneNumber: String,
    firstName: String,
    lastName: String? = null,
    vcard: String? = null,
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: ReplyMarkup? = null,
) =
    sendContact(
        SendContactRequest(
            chatId,
            phoneNumber,
            firstName,
            lastName,
            vcard,
            disableNotification,
            replyToMessageId,
            allowSendingWithoutReply,
            replyMarkup
        )
    )

/**
 * Request body for [sendPoll]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param question Poll question, 1-300 characters
 * @param options A JSON-serialized list of answer options, 2-10 strings 1-100 characters each
 * @param isAnonymous True, if the poll needs to be anonymous, defaults to True
 * @param type Poll type, “quiz” or “regular”, defaults to “regular”
 * @param allowsMultipleAnswers True, if the poll allows multiple answers, ignored for polls in quiz mode, defaults to False
 * @param correctOptionId 0-based identifier of the correct answer option, required for polls in quiz mode
 * @param explanation Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style poll, 0-200 characters with at most 2 line feeds after entities parsing
 * @param explanationParseMode Mode for parsing entities in the explanation. See formatting options for more details.
 * @param explanationEntities List of special entities that appear in the poll explanation, which can be specified instead of parse_mode
 * @param openPeriod Amount of time in seconds the poll will be active after creation, 5-600. Can't be used together with close_date.
 * @param closeDate Point in time (Unix timestamp) when the poll will be automatically closed. Must be at least 5 and no more than 600 seconds in the future. Can't be used together with open_period.
 * @param isClosed Pass True, if the poll needs to be immediately closed. This can be useful for poll preview.
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
@Serializable
data class SendPollRequest(
	@SerialName("chat_id") val chatId: Int,
	val question: String,
	val options: List<String> = emptyList(),
	@SerialName("is_anonymous") val isAnonymous: Boolean? = null,
	val type: String? = null,
	@SerialName("allows_multiple_answers") val allowsMultipleAnswers: Boolean? = null,
	@SerialName("correct_option_id") val correctOptionId: Int? = null,
	val explanation: String? = null,
	@SerialName("explanation_parse_mode") val explanationParseMode: String? = null,
	@SerialName("explanation_entities") val explanationEntities: List<MessageEntity> = emptyList(),
	@SerialName("open_period") val openPeriod: Int? = null,
	@SerialName("close_date") val closeDate: Int? = null,
	@SerialName("is_closed") val isClosed: Boolean? = null,
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: ReplyMarkup? = null
)

/**
 * Use this method to send a native poll. On success, the sent Message is returned.
 */
suspend fun TelegramBotApiClient.sendPoll(requestBody: SendPollRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendPoll")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send a native poll. On success, the sent Message is returned.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param question Poll question, 1-300 characters
 * @param options A JSON-serialized list of answer options, 2-10 strings 1-100 characters each
 * @param isAnonymous True, if the poll needs to be anonymous, defaults to True
 * @param type Poll type, “quiz” or “regular”, defaults to “regular”
 * @param allowsMultipleAnswers True, if the poll allows multiple answers, ignored for polls in quiz mode, defaults to False
 * @param correctOptionId 0-based identifier of the correct answer option, required for polls in quiz mode
 * @param explanation Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style poll, 0-200 characters with at most 2 line feeds after entities parsing
 * @param explanationParseMode Mode for parsing entities in the explanation. See formatting options for more details.
 * @param explanationEntities List of special entities that appear in the poll explanation, which can be specified instead of parse_mode
 * @param openPeriod Amount of time in seconds the poll will be active after creation, 5-600. Can't be used together with close_date.
 * @param closeDate Point in time (Unix timestamp) when the poll will be automatically closed. Must be at least 5 and no more than 600 seconds in the future. Can't be used together with open_period.
 * @param isClosed Pass True, if the poll needs to be immediately closed. This can be useful for poll preview.
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun TelegramBotApiClient.sendPoll(
    chatId: Int,
    question: String,
    options: List<String> = emptyList(),
    isAnonymous: Boolean? = null,
    type: String? = null,
    allowsMultipleAnswers: Boolean? = null,
    correctOptionId: Int? = null,
    explanation: String? = null,
    explanationParseMode: String? = null,
    explanationEntities: List<MessageEntity> = emptyList(),
    openPeriod: Int? = null,
    closeDate: Int? = null,
    isClosed: Boolean? = null,
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: ReplyMarkup? = null,
) =
    sendPoll(
        SendPollRequest(
            chatId,
            question,
            options,
            isAnonymous,
            type,
            allowsMultipleAnswers,
            correctOptionId,
            explanation,
            explanationParseMode,
            explanationEntities,
            openPeriod,
            closeDate,
            isClosed,
            disableNotification,
            replyToMessageId,
            allowSendingWithoutReply,
            replyMarkup
        )
    )

/**
 * Request body for [sendDice]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param emoji Emoji on which the dice throw animation is based. Currently, must be one of “”, “”, “”, “”, or “”. Dice can have values 1-6 for “” and “”, values 1-5 for “” and “”, and values 1-64 for “”. Defaults to “”
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
@Serializable
data class SendDiceRequest(
	@SerialName("chat_id") val chatId: Int,
	val emoji: String? = null,
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: ReplyMarkup? = null
)

/**
 * Use this method to send an animated emoji that will display a random value. On success, the sent Message is returned.
 */
suspend fun TelegramBotApiClient.sendDice(requestBody: SendDiceRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendDice")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send an animated emoji that will display a random value. On success, the sent Message is returned.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param emoji Emoji on which the dice throw animation is based. Currently, must be one of “”, “”, “”, “”, or “”. Dice can have values 1-6 for “” and “”, values 1-5 for “” and “”, and values 1-64 for “”. Defaults to “”
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun TelegramBotApiClient.sendDice(
    chatId: Int,
    emoji: String? = null,
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: ReplyMarkup? = null,
) =
    sendDice(
        SendDiceRequest(
            chatId,
            emoji,
            disableNotification,
            replyToMessageId,
            allowSendingWithoutReply,
            replyMarkup
        )
    )

/**
 * Request body for [sendChatAction]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param action Type of action to broadcast. Choose one, depending on what the user is about to receive: typing for text messages, upload_photo for photos, record_video or upload_video for videos, record_voice or upload_voice for voice notes, upload_document for general files, find_location for location data, record_video_note or upload_video_note for video notes.
 */
@Serializable
data class SendChatActionRequest(
	@SerialName("chat_id") val chatId: Int,
	val action: String
)

/**
 * Use this method when you need to tell the user that something is happening on the bot's side. The status is set for 5 seconds or less (when a message arrives from your bot, Telegram clients clear its typing status). Returns True on success.
 * Example: The ImageBot needs some time to process a request and upload the image. Instead of sending a text message along the lines of “Retrieving image, please wait…”, the bot may use sendChatAction with action = upload_photo. The user will see a “sending photo” status for the bot.
 * We only recommend using this method when a response from the bot will take a noticeable amount of time to arrive.
 */
suspend fun TelegramBotApiClient.sendChatAction(requestBody: SendChatActionRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendChatAction")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method when you need to tell the user that something is happening on the bot's side. The status is set for 5 seconds or less (when a message arrives from your bot, Telegram clients clear its typing status). Returns True on success.
 * Example: The ImageBot needs some time to process a request and upload the image. Instead of sending a text message along the lines of “Retrieving image, please wait…”, the bot may use sendChatAction with action = upload_photo. The user will see a “sending photo” status for the bot.
 * We only recommend using this method when a response from the bot will take a noticeable amount of time to arrive.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param action Type of action to broadcast. Choose one, depending on what the user is about to receive: typing for text messages, upload_photo for photos, record_video or upload_video for videos, record_voice or upload_voice for voice notes, upload_document for general files, find_location for location data, record_video_note or upload_video_note for video notes.
 */
suspend fun TelegramBotApiClient.sendChatAction(
    chatId: Int,
    action: String,
) =
    sendChatAction(
        SendChatActionRequest(
            chatId,
            action
        )
    )

/**
 * Request body for [getUserProfilePhotos]
 * @param userId Unique identifier of the target user
 * @param offset Sequential number of the first photo to be returned. By default, all photos are returned.
 * @param limit Limits the number of photos to be retrieved. Values between 1-100 are accepted. Defaults to 100.
 */
@Serializable
data class GetUserProfilePhotosRequest(
	@SerialName("user_id") val userId: Int,
	val offset: Int? = null,
	val limit: Int? = null
)

/**
 * Use this method to get a list of profile pictures for a user. Returns a UserProfilePhotos object.
 */
suspend fun TelegramBotApiClient.getUserProfilePhotos(requestBody: GetUserProfilePhotosRequest) =
    httpClient.post<TelegramResponse<UserProfilePhotos>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "getUserProfilePhotos")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to get a list of profile pictures for a user. Returns a UserProfilePhotos object.
 * @param userId Unique identifier of the target user
 * @param offset Sequential number of the first photo to be returned. By default, all photos are returned.
 * @param limit Limits the number of photos to be retrieved. Values between 1-100 are accepted. Defaults to 100.
 */
suspend fun TelegramBotApiClient.getUserProfilePhotos(
    userId: Int,
    offset: Int? = null,
    limit: Int? = null,
) =
    getUserProfilePhotos(
        GetUserProfilePhotosRequest(
            userId,
            offset,
            limit
        )
    )

/**
 * Request body for [getFile]
 * @param fileId File identifier to get info about
 */
@Serializable
data class GetFileRequest(
	@SerialName("file_id") val fileId: String
)

/**
 * Use this method to get basic info about a file and prepare it for downloading. For the moment, bots can download files of up to 20MB in size. On success, a File object is returned. The file can then be downloaded via the link https://api.telegram.org/file/bot<token>/<file_path>, where <file_path> is taken from the response. It is guaranteed that the link will be valid for at least 1 hour. When the link expires, a new one can be requested by calling getFile again.
 * Note: This function may not preserve the original file name and MIME type. You should save the file's MIME type and name (if available) when the File object is received.
 */
suspend fun TelegramBotApiClient.getFile(requestBody: GetFileRequest) =
    httpClient.post<TelegramResponse<File>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "getFile")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to get basic info about a file and prepare it for downloading. For the moment, bots can download files of up to 20MB in size. On success, a File object is returned. The file can then be downloaded via the link https://api.telegram.org/file/bot<token>/<file_path>, where <file_path> is taken from the response. It is guaranteed that the link will be valid for at least 1 hour. When the link expires, a new one can be requested by calling getFile again.
 * Note: This function may not preserve the original file name and MIME type. You should save the file's MIME type and name (if available) when the File object is received.
 * @param fileId File identifier to get info about
 */
suspend fun TelegramBotApiClient.getFile(
    fileId: String,
) =
    getFile(
        GetFileRequest(
            fileId
        )
    )

/**
 * Request body for [kickChatMember]
 * @param chatId Unique identifier for the target group or username of the target supergroup or channel (in the format @channelusername)
 * @param userId Unique identifier of the target user
 * @param untilDate Date when the user will be unbanned, unix time. If user is banned for more than 366 days or less than 30 seconds from the current time they are considered to be banned forever. Applied for supergroups and channels only.
 */
@Serializable
data class KickChatMemberRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("user_id") val userId: Int,
	@SerialName("until_date") val untilDate: Int? = null
)

/**
 * Use this method to kick a user from a group, a supergroup or a channel. In the case of supergroups and channels, the user will not be able to return to the chat on their own using invite links, etc., unless unbanned first. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns True on success.
 */
suspend fun TelegramBotApiClient.kickChatMember(requestBody: KickChatMemberRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "kickChatMember")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to kick a user from a group, a supergroup or a channel. In the case of supergroups and channels, the user will not be able to return to the chat on their own using invite links, etc., unless unbanned first. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns True on success.
 * @param chatId Unique identifier for the target group or username of the target supergroup or channel (in the format @channelusername)
 * @param userId Unique identifier of the target user
 * @param untilDate Date when the user will be unbanned, unix time. If user is banned for more than 366 days or less than 30 seconds from the current time they are considered to be banned forever. Applied for supergroups and channels only.
 */
suspend fun TelegramBotApiClient.kickChatMember(
    chatId: Int,
    userId: Int,
    untilDate: Int? = null,
) =
    kickChatMember(
        KickChatMemberRequest(
            chatId,
            userId,
            untilDate
        )
    )

/**
 * Request body for [unbanChatMember]
 * @param chatId Unique identifier for the target group or username of the target supergroup or channel (in the format @username)
 * @param userId Unique identifier of the target user
 * @param onlyIfBanned Do nothing if the user is not banned
 */
@Serializable
data class UnbanChatMemberRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("user_id") val userId: Int,
	@SerialName("only_if_banned") val onlyIfBanned: Boolean? = null
)

/**
 * Use this method to unban a previously kicked user in a supergroup or channel. The user will not return to the group or channel automatically, but will be able to join via link, etc. The bot must be an administrator for this to work. By default, this method guarantees that after the call the user is not a member of the chat, but will be able to join it. So if the user is a member of the chat they will also be removed from the chat. If you don't want this, use the parameter only_if_banned. Returns True on success.
 */
suspend fun TelegramBotApiClient.unbanChatMember(requestBody: UnbanChatMemberRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "unbanChatMember")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to unban a previously kicked user in a supergroup or channel. The user will not return to the group or channel automatically, but will be able to join via link, etc. The bot must be an administrator for this to work. By default, this method guarantees that after the call the user is not a member of the chat, but will be able to join it. So if the user is a member of the chat they will also be removed from the chat. If you don't want this, use the parameter only_if_banned. Returns True on success.
 * @param chatId Unique identifier for the target group or username of the target supergroup or channel (in the format @username)
 * @param userId Unique identifier of the target user
 * @param onlyIfBanned Do nothing if the user is not banned
 */
suspend fun TelegramBotApiClient.unbanChatMember(
    chatId: Int,
    userId: Int,
    onlyIfBanned: Boolean? = null,
) =
    unbanChatMember(
        UnbanChatMemberRequest(
            chatId,
            userId,
            onlyIfBanned
        )
    )

/**
 * Request body for [restrictChatMember]
 * @param chatId Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 * @param userId Unique identifier of the target user
 * @param permissions A JSON-serialized object for new user permissions
 * @param untilDate Date when restrictions will be lifted for the user, unix time. If user is restricted for more than 366 days or less than 30 seconds from the current time, they are considered to be restricted forever
 */
@Serializable
data class RestrictChatMemberRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("user_id") val userId: Int,
	val permissions: ChatPermissions,
	@SerialName("until_date") val untilDate: Int? = null
)

/**
 * Use this method to restrict a user in a supergroup. The bot must be an administrator in the supergroup for this to work and must have the appropriate admin rights. Pass True for all permissions to lift restrictions from a user. Returns True on success.
 */
suspend fun TelegramBotApiClient.restrictChatMember(requestBody: RestrictChatMemberRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "restrictChatMember")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to restrict a user in a supergroup. The bot must be an administrator in the supergroup for this to work and must have the appropriate admin rights. Pass True for all permissions to lift restrictions from a user. Returns True on success.
 * @param chatId Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 * @param userId Unique identifier of the target user
 * @param permissions A JSON-serialized object for new user permissions
 * @param untilDate Date when restrictions will be lifted for the user, unix time. If user is restricted for more than 366 days or less than 30 seconds from the current time, they are considered to be restricted forever
 */
suspend fun TelegramBotApiClient.restrictChatMember(
    chatId: Int,
    userId: Int,
    permissions: ChatPermissions,
    untilDate: Int? = null,
) =
    restrictChatMember(
        RestrictChatMemberRequest(
            chatId,
            userId,
            permissions,
            untilDate
        )
    )

/**
 * Request body for [promoteChatMember]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param userId Unique identifier of the target user
 * @param isAnonymous Pass True, if the administrator's presence in the chat is hidden
 * @param canChangeInfo Pass True, if the administrator can change chat title, photo and other settings
 * @param canPostMessages Pass True, if the administrator can create channel posts, channels only
 * @param canEditMessages Pass True, if the administrator can edit messages of other users and can pin messages, channels only
 * @param canDeleteMessages Pass True, if the administrator can delete messages of other users
 * @param canInviteUsers Pass True, if the administrator can invite new users to the chat
 * @param canRestrictMembers Pass True, if the administrator can restrict, ban or unban chat members
 * @param canPinMessages Pass True, if the administrator can pin messages, supergroups only
 * @param canPromoteMembers Pass True, if the administrator can add new administrators with a subset of their own privileges or demote administrators that he has promoted, directly or indirectly (promoted by administrators that were appointed by him)
 */
@Serializable
data class PromoteChatMemberRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("user_id") val userId: Int,
	@SerialName("is_anonymous") val isAnonymous: Boolean? = null,
	@SerialName("can_change_info") val canChangeInfo: Boolean? = null,
	@SerialName("can_post_messages") val canPostMessages: Boolean? = null,
	@SerialName("can_edit_messages") val canEditMessages: Boolean? = null,
	@SerialName("can_delete_messages") val canDeleteMessages: Boolean? = null,
	@SerialName("can_invite_users") val canInviteUsers: Boolean? = null,
	@SerialName("can_restrict_members") val canRestrictMembers: Boolean? = null,
	@SerialName("can_pin_messages") val canPinMessages: Boolean? = null,
	@SerialName("can_promote_members") val canPromoteMembers: Boolean? = null
)

/**
 * Use this method to promote or demote a user in a supergroup or a channel. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Pass False for all boolean parameters to demote a user. Returns True on success.
 */
suspend fun TelegramBotApiClient.promoteChatMember(requestBody: PromoteChatMemberRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "promoteChatMember")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to promote or demote a user in a supergroup or a channel. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Pass False for all boolean parameters to demote a user. Returns True on success.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param userId Unique identifier of the target user
 * @param isAnonymous Pass True, if the administrator's presence in the chat is hidden
 * @param canChangeInfo Pass True, if the administrator can change chat title, photo and other settings
 * @param canPostMessages Pass True, if the administrator can create channel posts, channels only
 * @param canEditMessages Pass True, if the administrator can edit messages of other users and can pin messages, channels only
 * @param canDeleteMessages Pass True, if the administrator can delete messages of other users
 * @param canInviteUsers Pass True, if the administrator can invite new users to the chat
 * @param canRestrictMembers Pass True, if the administrator can restrict, ban or unban chat members
 * @param canPinMessages Pass True, if the administrator can pin messages, supergroups only
 * @param canPromoteMembers Pass True, if the administrator can add new administrators with a subset of their own privileges or demote administrators that he has promoted, directly or indirectly (promoted by administrators that were appointed by him)
 */
suspend fun TelegramBotApiClient.promoteChatMember(
    chatId: Int,
    userId: Int,
    isAnonymous: Boolean? = null,
    canChangeInfo: Boolean? = null,
    canPostMessages: Boolean? = null,
    canEditMessages: Boolean? = null,
    canDeleteMessages: Boolean? = null,
    canInviteUsers: Boolean? = null,
    canRestrictMembers: Boolean? = null,
    canPinMessages: Boolean? = null,
    canPromoteMembers: Boolean? = null,
) =
    promoteChatMember(
        PromoteChatMemberRequest(
            chatId,
            userId,
            isAnonymous,
            canChangeInfo,
            canPostMessages,
            canEditMessages,
            canDeleteMessages,
            canInviteUsers,
            canRestrictMembers,
            canPinMessages,
            canPromoteMembers
        )
    )

/**
 * Request body for [setChatAdministratorCustomTitle]
 * @param chatId Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 * @param userId Unique identifier of the target user
 * @param customTitle New custom title for the administrator; 0-16 characters, emoji are not allowed
 */
@Serializable
data class SetChatAdministratorCustomTitleRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("user_id") val userId: Int,
	@SerialName("custom_title") val customTitle: String
)

/**
 * Use this method to set a custom title for an administrator in a supergroup promoted by the bot. Returns True on success.
 */
suspend fun TelegramBotApiClient.setChatAdministratorCustomTitle(requestBody: SetChatAdministratorCustomTitleRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "setChatAdministratorCustomTitle")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to set a custom title for an administrator in a supergroup promoted by the bot. Returns True on success.
 * @param chatId Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 * @param userId Unique identifier of the target user
 * @param customTitle New custom title for the administrator; 0-16 characters, emoji are not allowed
 */
suspend fun TelegramBotApiClient.setChatAdministratorCustomTitle(
    chatId: Int,
    userId: Int,
    customTitle: String,
) =
    setChatAdministratorCustomTitle(
        SetChatAdministratorCustomTitleRequest(
            chatId,
            userId,
            customTitle
        )
    )

/**
 * Request body for [setChatPermissions]
 * @param chatId Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 * @param permissions New default chat permissions
 */
@Serializable
data class SetChatPermissionsRequest(
	@SerialName("chat_id") val chatId: Int,
	val permissions: ChatPermissions
)

/**
 * Use this method to set default chat permissions for all members. The bot must be an administrator in the group or a supergroup for this to work and must have the can_restrict_members admin rights. Returns True on success.
 */
suspend fun TelegramBotApiClient.setChatPermissions(requestBody: SetChatPermissionsRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "setChatPermissions")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to set default chat permissions for all members. The bot must be an administrator in the group or a supergroup for this to work and must have the can_restrict_members admin rights. Returns True on success.
 * @param chatId Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 * @param permissions New default chat permissions
 */
suspend fun TelegramBotApiClient.setChatPermissions(
    chatId: Int,
    permissions: ChatPermissions,
) =
    setChatPermissions(
        SetChatPermissionsRequest(
            chatId,
            permissions
        )
    )

/**
 * Request body for [exportChatInviteLink]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 */
@Serializable
data class ExportChatInviteLinkRequest(
	@SerialName("chat_id") val chatId: Int
)

/**
 * Use this method to generate a new invite link for a chat; any previously generated link is revoked. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns the new invite link as String on success.
 * Note: Each administrator in a chat generates their own invite links. Bots can't use invite links generated by other administrators. If you want your bot to work with invite links, it will need to generate its own link using exportChatInviteLink — after this the link will become available to the bot via the getChat method. If your bot needs to generate a new invite link replacing its previous one, use exportChatInviteLink again.
 */
suspend fun TelegramBotApiClient.exportChatInviteLink(requestBody: ExportChatInviteLinkRequest) =
    httpClient.post<TelegramResponse<String>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "exportChatInviteLink")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to generate a new invite link for a chat; any previously generated link is revoked. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns the new invite link as String on success.
 * Note: Each administrator in a chat generates their own invite links. Bots can't use invite links generated by other administrators. If you want your bot to work with invite links, it will need to generate its own link using exportChatInviteLink — after this the link will become available to the bot via the getChat method. If your bot needs to generate a new invite link replacing its previous one, use exportChatInviteLink again.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 */
suspend fun TelegramBotApiClient.exportChatInviteLink(
    chatId: Int,
) =
    exportChatInviteLink(
        ExportChatInviteLinkRequest(
            chatId
        )
    )

/**
 * Request body for [setChatPhoto]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param photo New chat photo, uploaded using multipart/form-data
 */
@Serializable
data class SetChatPhotoRequest(
	@SerialName("chat_id") val chatId: Int,
	val photo: String
)

/**
 * Use this method to set a new profile photo for the chat. Photos can't be changed for private chats. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns True on success.
 */
suspend fun TelegramBotApiClient.setChatPhoto(requestBody: SetChatPhotoRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "setChatPhoto")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to set a new profile photo for the chat. Photos can't be changed for private chats. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns True on success.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param photo New chat photo, uploaded using multipart/form-data
 */
suspend fun TelegramBotApiClient.setChatPhoto(
    chatId: Int,
    photo: String,
) =
    setChatPhoto(
        SetChatPhotoRequest(
            chatId,
            photo
        )
    )

/**
 * Request body for [deleteChatPhoto]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 */
@Serializable
data class DeleteChatPhotoRequest(
	@SerialName("chat_id") val chatId: Int
)

/**
 * Use this method to delete a chat photo. Photos can't be changed for private chats. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns True on success.
 */
suspend fun TelegramBotApiClient.deleteChatPhoto(requestBody: DeleteChatPhotoRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "deleteChatPhoto")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to delete a chat photo. Photos can't be changed for private chats. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns True on success.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 */
suspend fun TelegramBotApiClient.deleteChatPhoto(
    chatId: Int,
) =
    deleteChatPhoto(
        DeleteChatPhotoRequest(
            chatId
        )
    )

/**
 * Request body for [setChatTitle]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param title New chat title, 1-255 characters
 */
@Serializable
data class SetChatTitleRequest(
	@SerialName("chat_id") val chatId: Int,
	val title: String
)

/**
 * Use this method to change the title of a chat. Titles can't be changed for private chats. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns True on success.
 */
suspend fun TelegramBotApiClient.setChatTitle(requestBody: SetChatTitleRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "setChatTitle")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to change the title of a chat. Titles can't be changed for private chats. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns True on success.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param title New chat title, 1-255 characters
 */
suspend fun TelegramBotApiClient.setChatTitle(
    chatId: Int,
    title: String,
) =
    setChatTitle(
        SetChatTitleRequest(
            chatId,
            title
        )
    )

/**
 * Request body for [setChatDescription]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param description New chat description, 0-255 characters
 */
@Serializable
data class SetChatDescriptionRequest(
	@SerialName("chat_id") val chatId: Int,
	val description: String? = null
)

/**
 * Use this method to change the description of a group, a supergroup or a channel. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns True on success.
 */
suspend fun TelegramBotApiClient.setChatDescription(requestBody: SetChatDescriptionRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "setChatDescription")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to change the description of a group, a supergroup or a channel. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Returns True on success.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param description New chat description, 0-255 characters
 */
suspend fun TelegramBotApiClient.setChatDescription(
    chatId: Int,
    description: String? = null,
) =
    setChatDescription(
        SetChatDescriptionRequest(
            chatId,
            description
        )
    )

/**
 * Request body for [pinChatMessage]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Identifier of a message to pin
 * @param disableNotification Pass True, if it is not necessary to send a notification to all chat members about the new pinned message. Notifications are always disabled in channels and private chats.
 */
@Serializable
data class PinChatMessageRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("message_id") val messageId: Int,
	@SerialName("disable_notification") val disableNotification: Boolean? = null
)

/**
 * Use this method to add a message to the list of pinned messages in a chat. If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must have the 'can_pin_messages' admin right in a supergroup or 'can_edit_messages' admin right in a channel. Returns True on success.
 */
suspend fun TelegramBotApiClient.pinChatMessage(requestBody: PinChatMessageRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "pinChatMessage")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to add a message to the list of pinned messages in a chat. If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must have the 'can_pin_messages' admin right in a supergroup or 'can_edit_messages' admin right in a channel. Returns True on success.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Identifier of a message to pin
 * @param disableNotification Pass True, if it is not necessary to send a notification to all chat members about the new pinned message. Notifications are always disabled in channels and private chats.
 */
suspend fun TelegramBotApiClient.pinChatMessage(
    chatId: Int,
    messageId: Int,
    disableNotification: Boolean? = null,
) =
    pinChatMessage(
        PinChatMessageRequest(
            chatId,
            messageId,
            disableNotification
        )
    )

/**
 * Request body for [unpinChatMessage]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Identifier of a message to unpin. If not specified, the most recent pinned message (by sending date) will be unpinned.
 */
@Serializable
data class UnpinChatMessageRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("message_id") val messageId: Int? = null
)

/**
 * Use this method to remove a message from the list of pinned messages in a chat. If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must have the 'can_pin_messages' admin right in a supergroup or 'can_edit_messages' admin right in a channel. Returns True on success.
 */
suspend fun TelegramBotApiClient.unpinChatMessage(requestBody: UnpinChatMessageRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "unpinChatMessage")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to remove a message from the list of pinned messages in a chat. If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must have the 'can_pin_messages' admin right in a supergroup or 'can_edit_messages' admin right in a channel. Returns True on success.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Identifier of a message to unpin. If not specified, the most recent pinned message (by sending date) will be unpinned.
 */
suspend fun TelegramBotApiClient.unpinChatMessage(
    chatId: Int,
    messageId: Int? = null,
) =
    unpinChatMessage(
        UnpinChatMessageRequest(
            chatId,
            messageId
        )
    )

/**
 * Request body for [unpinAllChatMessages]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 */
@Serializable
data class UnpinAllChatMessagesRequest(
	@SerialName("chat_id") val chatId: Int
)

/**
 * Use this method to clear the list of pinned messages in a chat. If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must have the 'can_pin_messages' admin right in a supergroup or 'can_edit_messages' admin right in a channel. Returns True on success.
 */
suspend fun TelegramBotApiClient.unpinAllChatMessages(requestBody: UnpinAllChatMessagesRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "unpinAllChatMessages")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to clear the list of pinned messages in a chat. If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must have the 'can_pin_messages' admin right in a supergroup or 'can_edit_messages' admin right in a channel. Returns True on success.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 */
suspend fun TelegramBotApiClient.unpinAllChatMessages(
    chatId: Int,
) =
    unpinAllChatMessages(
        UnpinAllChatMessagesRequest(
            chatId
        )
    )

/**
 * Request body for [leaveChat]
 * @param chatId Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 */
@Serializable
data class LeaveChatRequest(
	@SerialName("chat_id") val chatId: Int
)

/**
 * Use this method for your bot to leave a group, supergroup or channel. Returns True on success.
 */
suspend fun TelegramBotApiClient.leaveChat(requestBody: LeaveChatRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "leaveChat")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method for your bot to leave a group, supergroup or channel. Returns True on success.
 * @param chatId Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 */
suspend fun TelegramBotApiClient.leaveChat(
    chatId: Int,
) =
    leaveChat(
        LeaveChatRequest(
            chatId
        )
    )

/**
 * Request body for [getChat]
 * @param chatId Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 */
@Serializable
data class GetChatRequest(
	@SerialName("chat_id") val chatId: Int
)

/**
 * Use this method to get up to date information about the chat (current name of the user for one-on-one conversations, current username of a user, group or channel, etc.). Returns a Chat object on success.
 */
suspend fun TelegramBotApiClient.getChat(requestBody: GetChatRequest) =
    httpClient.post<TelegramResponse<Chat>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "getChat")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to get up to date information about the chat (current name of the user for one-on-one conversations, current username of a user, group or channel, etc.). Returns a Chat object on success.
 * @param chatId Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 */
suspend fun TelegramBotApiClient.getChat(
    chatId: Int,
) =
    getChat(
        GetChatRequest(
            chatId
        )
    )

/**
 * Request body for [getChatAdministrators]
 * @param chatId Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 */
@Serializable
data class GetChatAdministratorsRequest(
	@SerialName("chat_id") val chatId: Int
)

/**
 * Use this method to get a list of administrators in a chat. On success, returns an Array of ChatMember objects that contains information about all chat administrators except other bots. If the chat is a group or a supergroup and no administrators were appointed, only the creator will be returned.
 */
suspend fun TelegramBotApiClient.getChatAdministrators(requestBody: GetChatAdministratorsRequest) =
    httpClient.post<TelegramResponse<List<ChatMember>>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "getChatAdministrators")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to get a list of administrators in a chat. On success, returns an Array of ChatMember objects that contains information about all chat administrators except other bots. If the chat is a group or a supergroup and no administrators were appointed, only the creator will be returned.
 * @param chatId Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 */
suspend fun TelegramBotApiClient.getChatAdministrators(
    chatId: Int,
) =
    getChatAdministrators(
        GetChatAdministratorsRequest(
            chatId
        )
    )

/**
 * Request body for [getChatMembersCount]
 * @param chatId Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 */
@Serializable
data class GetChatMembersCountRequest(
	@SerialName("chat_id") val chatId: Int
)

/**
 * Use this method to get the number of members in a chat. Returns Int on success.
 */
suspend fun TelegramBotApiClient.getChatMembersCount(requestBody: GetChatMembersCountRequest) =
    httpClient.post<TelegramResponse<Int>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "getChatMembersCount")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to get the number of members in a chat. Returns Int on success.
 * @param chatId Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 */
suspend fun TelegramBotApiClient.getChatMembersCount(
    chatId: Int,
) =
    getChatMembersCount(
        GetChatMembersCountRequest(
            chatId
        )
    )

/**
 * Request body for [getChatMember]
 * @param chatId Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 * @param userId Unique identifier of the target user
 */
@Serializable
data class GetChatMemberRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("user_id") val userId: Int
)

/**
 * Use this method to get information about a member of a chat. Returns a ChatMember object on success.
 */
suspend fun TelegramBotApiClient.getChatMember(requestBody: GetChatMemberRequest) =
    httpClient.post<TelegramResponse<ChatMember>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "getChatMember")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to get information about a member of a chat. Returns a ChatMember object on success.
 * @param chatId Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 * @param userId Unique identifier of the target user
 */
suspend fun TelegramBotApiClient.getChatMember(
    chatId: Int,
    userId: Int,
) =
    getChatMember(
        GetChatMemberRequest(
            chatId,
            userId
        )
    )

/**
 * Request body for [setChatStickerSet]
 * @param chatId Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 * @param stickerSetName Name of the sticker set to be set as the group sticker set
 */
@Serializable
data class SetChatStickerSetRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("sticker_set_name") val stickerSetName: String
)

/**
 * Use this method to set a new group sticker set for a supergroup. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Use the field can_set_sticker_set optionally returned in getChat requests to check if the bot can use this method. Returns True on success.
 */
suspend fun TelegramBotApiClient.setChatStickerSet(requestBody: SetChatStickerSetRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "setChatStickerSet")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to set a new group sticker set for a supergroup. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Use the field can_set_sticker_set optionally returned in getChat requests to check if the bot can use this method. Returns True on success.
 * @param chatId Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 * @param stickerSetName Name of the sticker set to be set as the group sticker set
 */
suspend fun TelegramBotApiClient.setChatStickerSet(
    chatId: Int,
    stickerSetName: String,
) =
    setChatStickerSet(
        SetChatStickerSetRequest(
            chatId,
            stickerSetName
        )
    )

/**
 * Request body for [deleteChatStickerSet]
 * @param chatId Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 */
@Serializable
data class DeleteChatStickerSetRequest(
	@SerialName("chat_id") val chatId: Int
)

/**
 * Use this method to delete a group sticker set from a supergroup. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Use the field can_set_sticker_set optionally returned in getChat requests to check if the bot can use this method. Returns True on success.
 */
suspend fun TelegramBotApiClient.deleteChatStickerSet(requestBody: DeleteChatStickerSetRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "deleteChatStickerSet")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to delete a group sticker set from a supergroup. The bot must be an administrator in the chat for this to work and must have the appropriate admin rights. Use the field can_set_sticker_set optionally returned in getChat requests to check if the bot can use this method. Returns True on success.
 * @param chatId Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 */
suspend fun TelegramBotApiClient.deleteChatStickerSet(
    chatId: Int,
) =
    deleteChatStickerSet(
        DeleteChatStickerSetRequest(
            chatId
        )
    )

/**
 * Request body for [answerCallbackQuery]
 * @param callbackQueryId Unique identifier for the query to be answered
 * @param text Text of the notification. If not specified, nothing will be shown to the user, 0-200 characters
 * @param showAlert If true, an alert will be shown by the client instead of a notification at the top of the chat screen. Defaults to false.
 * @param url URL that will be opened by the user's client. If you have created a Game and accepted the conditions via @Botfather, specify the URL that opens your game — note that this will only work if the query comes from a callback_game button. Otherwise, you may use links like t.me/your_bot?start=XXXX that open your bot with a parameter.
 * @param cacheTime The maximum amount of time in seconds that the result of the callback query may be cached client-side. Telegram apps will support caching starting in version 3.14. Defaults to 0.
 */
@Serializable
data class AnswerCallbackQueryRequest(
	@SerialName("callback_query_id") val callbackQueryId: String,
	val text: String? = null,
	@SerialName("show_alert") val showAlert: Boolean? = null,
	val url: String? = null,
	@SerialName("cache_time") val cacheTime: Int? = null
)

/**
 * Use this method to send answers to callback queries sent from inline keyboards. The answer will be displayed to the user as a notification at the top of the chat screen or as an alert. On success, True is returned.
 * Alternatively, the user can be redirected to the specified Game URL. For this option to work, you must first create a game for your bot via @Botfather and accept the terms. Otherwise, you may use links like t.me/your_bot?start=XXXX that open your bot with a parameter.
 */
suspend fun TelegramBotApiClient.answerCallbackQuery(requestBody: AnswerCallbackQueryRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "answerCallbackQuery")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send answers to callback queries sent from inline keyboards. The answer will be displayed to the user as a notification at the top of the chat screen or as an alert. On success, True is returned.
 * Alternatively, the user can be redirected to the specified Game URL. For this option to work, you must first create a game for your bot via @Botfather and accept the terms. Otherwise, you may use links like t.me/your_bot?start=XXXX that open your bot with a parameter.
 * @param callbackQueryId Unique identifier for the query to be answered
 * @param text Text of the notification. If not specified, nothing will be shown to the user, 0-200 characters
 * @param showAlert If true, an alert will be shown by the client instead of a notification at the top of the chat screen. Defaults to false.
 * @param url URL that will be opened by the user's client. If you have created a Game and accepted the conditions via @Botfather, specify the URL that opens your game — note that this will only work if the query comes from a callback_game button. Otherwise, you may use links like t.me/your_bot?start=XXXX that open your bot with a parameter.
 * @param cacheTime The maximum amount of time in seconds that the result of the callback query may be cached client-side. Telegram apps will support caching starting in version 3.14. Defaults to 0.
 */
suspend fun TelegramBotApiClient.answerCallbackQuery(
    callbackQueryId: String,
    text: String? = null,
    showAlert: Boolean? = null,
    url: String? = null,
    cacheTime: Int? = null,
) =
    answerCallbackQuery(
        AnswerCallbackQueryRequest(
            callbackQueryId,
            text,
            showAlert,
            url,
            cacheTime
        )
    )

/**
 * Request body for [setMyCommands]
 * @param commands A JSON-serialized list of bot commands to be set as the list of the bot's commands. At most 100 commands can be specified.
 */
@Serializable
data class SetMyCommandsRequest(
	val commands: List<BotCommand> = emptyList()
)

/**
 * Use this method to change the list of the bot's commands. Returns True on success.
 */
suspend fun TelegramBotApiClient.setMyCommands(requestBody: SetMyCommandsRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "setMyCommands")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to change the list of the bot's commands. Returns True on success.
 * @param commands A JSON-serialized list of bot commands to be set as the list of the bot's commands. At most 100 commands can be specified.
 */
suspend fun TelegramBotApiClient.setMyCommands(
    commands: List<BotCommand> = emptyList(),
) =
    setMyCommands(
        SetMyCommandsRequest(
            commands
        )
    )

/**
 * Request body for [editMessageText]
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Required if inline_message_id is not specified. Identifier of the message to edit
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param text New text of the message, 1-4096 characters after entities parsing
 * @param parseMode Mode for parsing entities in the message text. See formatting options for more details.
 * @param entities List of special entities that appear in message text, which can be specified instead of parse_mode
 * @param disableWebPagePreview Disables link previews for links in this message
 * @param replyMarkup A JSON-serialized object for an inline keyboard.
 */
@Serializable
data class EditMessageTextRequest(
	@SerialName("chat_id") val chatId: Int? = null,
	@SerialName("message_id") val messageId: Int? = null,
	@SerialName("inline_message_id") val inlineMessageId: String? = null,
	val text: String,
	@SerialName("parse_mode") val parseMode: String? = null,
	val entities: List<MessageEntity> = emptyList(),
	@SerialName("disable_web_page_preview") val disableWebPagePreview: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null
)

/**
 * Use this method to edit text and game messages. On success, if the edited message is not an inline message, the edited Message is returned, otherwise True is returned.
 */
suspend fun TelegramBotApiClient.editMessageText(requestBody: EditMessageTextRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "editMessageText")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to edit text and game messages. On success, if the edited message is not an inline message, the edited Message is returned, otherwise True is returned.
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Required if inline_message_id is not specified. Identifier of the message to edit
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param text New text of the message, 1-4096 characters after entities parsing
 * @param parseMode Mode for parsing entities in the message text. See formatting options for more details.
 * @param entities List of special entities that appear in message text, which can be specified instead of parse_mode
 * @param disableWebPagePreview Disables link previews for links in this message
 * @param replyMarkup A JSON-serialized object for an inline keyboard.
 */
suspend fun TelegramBotApiClient.editMessageText(
    chatId: Int? = null,
    messageId: Int? = null,
    inlineMessageId: String? = null,
    text: String,
    parseMode: String? = null,
    entities: List<MessageEntity> = emptyList(),
    disableWebPagePreview: Boolean? = null,
    replyMarkup: InlineKeyboardMarkup? = null,
) =
    editMessageText(
        EditMessageTextRequest(
            chatId,
            messageId,
            inlineMessageId,
            text,
            parseMode,
            entities,
            disableWebPagePreview,
            replyMarkup
        )
    )

/**
 * Request body for [editMessageCaption]
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Required if inline_message_id is not specified. Identifier of the message to edit
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param caption New caption of the message, 0-1024 characters after entities parsing
 * @param parseMode Mode for parsing entities in the message caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param replyMarkup A JSON-serialized object for an inline keyboard.
 */
@Serializable
data class EditMessageCaptionRequest(
	@SerialName("chat_id") val chatId: Int? = null,
	@SerialName("message_id") val messageId: Int? = null,
	@SerialName("inline_message_id") val inlineMessageId: String? = null,
	val caption: String? = null,
	@SerialName("parse_mode") val parseMode: String? = null,
	@SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
	@SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null
)

/**
 * Use this method to edit captions of messages. On success, if the edited message is not an inline message, the edited Message is returned, otherwise True is returned.
 */
suspend fun TelegramBotApiClient.editMessageCaption(requestBody: EditMessageCaptionRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "editMessageCaption")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to edit captions of messages. On success, if the edited message is not an inline message, the edited Message is returned, otherwise True is returned.
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Required if inline_message_id is not specified. Identifier of the message to edit
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param caption New caption of the message, 0-1024 characters after entities parsing
 * @param parseMode Mode for parsing entities in the message caption. See formatting options for more details.
 * @param captionEntities List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param replyMarkup A JSON-serialized object for an inline keyboard.
 */
suspend fun TelegramBotApiClient.editMessageCaption(
    chatId: Int? = null,
    messageId: Int? = null,
    inlineMessageId: String? = null,
    caption: String? = null,
    parseMode: String? = null,
    captionEntities: List<MessageEntity> = emptyList(),
    replyMarkup: InlineKeyboardMarkup? = null,
) =
    editMessageCaption(
        EditMessageCaptionRequest(
            chatId,
            messageId,
            inlineMessageId,
            caption,
            parseMode,
            captionEntities,
            replyMarkup
        )
    )

/**
 * Request body for [editMessageMedia]
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Required if inline_message_id is not specified. Identifier of the message to edit
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param media A JSON-serialized object for a new media content of the message
 * @param replyMarkup A JSON-serialized object for a new inline keyboard.
 */
@Serializable
data class EditMessageMediaRequest(
	@SerialName("chat_id") val chatId: Int? = null,
	@SerialName("message_id") val messageId: Int? = null,
	@SerialName("inline_message_id") val inlineMessageId: String? = null,
	val media: InputMedia,
	@SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null
)

/**
 * Use this method to edit animation, audio, document, photo, or video messages. If a message is part of a message album, then it can be edited only to an audio for audio albums, only to a document for document albums and to a photo or a video otherwise. When an inline message is edited, a new file can't be uploaded. Use a previously uploaded file via its file_id or specify a URL. On success, if the edited message was sent by the bot, the edited Message is returned, otherwise True is returned.
 */
suspend fun TelegramBotApiClient.editMessageMedia(requestBody: EditMessageMediaRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "editMessageMedia")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to edit animation, audio, document, photo, or video messages. If a message is part of a message album, then it can be edited only to an audio for audio albums, only to a document for document albums and to a photo or a video otherwise. When an inline message is edited, a new file can't be uploaded. Use a previously uploaded file via its file_id or specify a URL. On success, if the edited message was sent by the bot, the edited Message is returned, otherwise True is returned.
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Required if inline_message_id is not specified. Identifier of the message to edit
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param media A JSON-serialized object for a new media content of the message
 * @param replyMarkup A JSON-serialized object for a new inline keyboard.
 */
suspend fun TelegramBotApiClient.editMessageMedia(
    chatId: Int? = null,
    messageId: Int? = null,
    inlineMessageId: String? = null,
    media: InputMedia,
    replyMarkup: InlineKeyboardMarkup? = null,
) =
    editMessageMedia(
        EditMessageMediaRequest(
            chatId,
            messageId,
            inlineMessageId,
            media,
            replyMarkup
        )
    )

/**
 * Request body for [editMessageReplyMarkup]
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Required if inline_message_id is not specified. Identifier of the message to edit
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param replyMarkup A JSON-serialized object for an inline keyboard.
 */
@Serializable
data class EditMessageReplyMarkupRequest(
	@SerialName("chat_id") val chatId: Int? = null,
	@SerialName("message_id") val messageId: Int? = null,
	@SerialName("inline_message_id") val inlineMessageId: String? = null,
	@SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null
)

/**
 * Use this method to edit only the reply markup of messages. On success, if the edited message is not an inline message, the edited Message is returned, otherwise True is returned.
 */
suspend fun TelegramBotApiClient.editMessageReplyMarkup(requestBody: EditMessageReplyMarkupRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "editMessageReplyMarkup")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to edit only the reply markup of messages. On success, if the edited message is not an inline message, the edited Message is returned, otherwise True is returned.
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Required if inline_message_id is not specified. Identifier of the message to edit
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param replyMarkup A JSON-serialized object for an inline keyboard.
 */
suspend fun TelegramBotApiClient.editMessageReplyMarkup(
    chatId: Int? = null,
    messageId: Int? = null,
    inlineMessageId: String? = null,
    replyMarkup: InlineKeyboardMarkup? = null,
) =
    editMessageReplyMarkup(
        EditMessageReplyMarkupRequest(
            chatId,
            messageId,
            inlineMessageId,
            replyMarkup
        )
    )

/**
 * Request body for [stopPoll]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Identifier of the original message with the poll
 * @param replyMarkup A JSON-serialized object for a new message inline keyboard.
 */
@Serializable
data class StopPollRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("message_id") val messageId: Int,
	@SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null
)

/**
 * Use this method to stop a poll which was sent by the bot. On success, the stopped Poll with the final results is returned.
 */
suspend fun TelegramBotApiClient.stopPoll(requestBody: StopPollRequest) =
    httpClient.post<TelegramResponse<Poll>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "stopPoll")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to stop a poll which was sent by the bot. On success, the stopped Poll with the final results is returned.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Identifier of the original message with the poll
 * @param replyMarkup A JSON-serialized object for a new message inline keyboard.
 */
suspend fun TelegramBotApiClient.stopPoll(
    chatId: Int,
    messageId: Int,
    replyMarkup: InlineKeyboardMarkup? = null,
) =
    stopPoll(
        StopPollRequest(
            chatId,
            messageId,
            replyMarkup
        )
    )

/**
 * Request body for [deleteMessage]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Identifier of the message to delete
 */
@Serializable
data class DeleteMessageRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("message_id") val messageId: Int
)

/**
 * Use this method to delete a message, including service messages, with the following limitations: - A message can only be deleted if it was sent less than 48 hours ago. - A dice message in a private chat can only be deleted if it was sent more than 24 hours ago. - Bots can delete outgoing messages in private chats, groups, and supergroups. - Bots can delete incoming messages in private chats. - Bots granted can_post_messages permissions can delete outgoing messages in channels. - If the bot is an administrator of a group, it can delete any message there. - If the bot has can_delete_messages permission in a supergroup or a channel, it can delete any message there. Returns True on success.
 * The following methods and objects allow your bot to handle stickers and sticker sets.
 */
suspend fun TelegramBotApiClient.deleteMessage(requestBody: DeleteMessageRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "deleteMessage")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to delete a message, including service messages, with the following limitations: - A message can only be deleted if it was sent less than 48 hours ago. - A dice message in a private chat can only be deleted if it was sent more than 24 hours ago. - Bots can delete outgoing messages in private chats, groups, and supergroups. - Bots can delete incoming messages in private chats. - Bots granted can_post_messages permissions can delete outgoing messages in channels. - If the bot is an administrator of a group, it can delete any message there. - If the bot has can_delete_messages permission in a supergroup or a channel, it can delete any message there. Returns True on success.
 * The following methods and objects allow your bot to handle stickers and sticker sets.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId Identifier of the message to delete
 */
suspend fun TelegramBotApiClient.deleteMessage(
    chatId: Int,
    messageId: Int,
) =
    deleteMessage(
        DeleteMessageRequest(
            chatId,
            messageId
        )
    )

/**
 * Request body for [sendSticker]
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param sticker Sticker to send. Pass a file_id as String to send a file that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a .WEBP file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
@Serializable
data class SendStickerRequest(
	@SerialName("chat_id") val chatId: Int,
	val sticker: String,
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: ReplyMarkup? = null
)

/**
 * Use this method to send static .WEBP or animated .TGS stickers. On success, the sent Message is returned.
 */
suspend fun TelegramBotApiClient.sendSticker(requestBody: SendStickerRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendSticker")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send static .WEBP or animated .TGS stickers. On success, the sent Message is returned.
 * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param sticker Sticker to send. Pass a file_id as String to send a file that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a .WEBP file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
suspend fun TelegramBotApiClient.sendSticker(
    chatId: Int,
    sticker: String,
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: ReplyMarkup? = null,
) =
    sendSticker(
        SendStickerRequest(
            chatId,
            sticker,
            disableNotification,
            replyToMessageId,
            allowSendingWithoutReply,
            replyMarkup
        )
    )

/**
 * Request body for [getStickerSet]
 * @param name Name of the sticker set
 */
@Serializable
data class GetStickerSetRequest(
	val name: String
)

/**
 * Use this method to get a sticker set. On success, a StickerSet object is returned.
 */
suspend fun TelegramBotApiClient.getStickerSet(requestBody: GetStickerSetRequest) =
    httpClient.post<TelegramResponse<StickerSet>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "getStickerSet")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to get a sticker set. On success, a StickerSet object is returned.
 * @param name Name of the sticker set
 */
suspend fun TelegramBotApiClient.getStickerSet(
    name: String,
) =
    getStickerSet(
        GetStickerSetRequest(
            name
        )
    )

/**
 * Request body for [uploadStickerFile]
 * @param userId User identifier of sticker file owner
 * @param pngSticker PNG image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px, and either width or height must be exactly 512px. More info on Sending Files »
 */
@Serializable
data class UploadStickerFileRequest(
	@SerialName("user_id") val userId: Int,
	@SerialName("png_sticker") val pngSticker: String
)

/**
 * Use this method to upload a .PNG file with a sticker for later use in createNewStickerSet and addStickerToSet methods (can be used multiple times). Returns the uploaded File on success.
 */
suspend fun TelegramBotApiClient.uploadStickerFile(requestBody: UploadStickerFileRequest) =
    httpClient.post<TelegramResponse<File>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "uploadStickerFile")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to upload a .PNG file with a sticker for later use in createNewStickerSet and addStickerToSet methods (can be used multiple times). Returns the uploaded File on success.
 * @param userId User identifier of sticker file owner
 * @param pngSticker PNG image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px, and either width or height must be exactly 512px. More info on Sending Files »
 */
suspend fun TelegramBotApiClient.uploadStickerFile(
    userId: Int,
    pngSticker: String,
) =
    uploadStickerFile(
        UploadStickerFileRequest(
            userId,
            pngSticker
        )
    )

/**
 * Request body for [createNewStickerSet]
 * @param userId User identifier of created sticker set owner
 * @param name Short name of sticker set, to be used in t.me/addstickers/ URLs (e.g., animals). Can contain only english letters, digits and underscores. Must begin with a letter, can't contain consecutive underscores and must end in “_by_<bot username>”. <bot_username> is case insensitive. 1-64 characters.
 * @param title Sticker set title, 1-64 characters
 * @param pngSticker PNG image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px, and either width or height must be exactly 512px. Pass a file_id as a String to send a file that already exists on the Telegram servers, pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
 * @param tgsSticker TGS animation with the sticker, uploaded using multipart/form-data. See https://core.telegram.org/animated_stickers#technical-requirements for technical requirements
 * @param emojis One or more emoji corresponding to the sticker
 * @param containsMasks Pass True, if a set of mask stickers should be created
 * @param maskPosition A JSON-serialized object for position where the mask should be placed on faces
 */
@Serializable
data class CreateNewStickerSetRequest(
	@SerialName("user_id") val userId: Int,
	val name: String,
	val title: String,
	@SerialName("png_sticker") val pngSticker: String? = null,
	@SerialName("tgs_sticker") val tgsSticker: String? = null,
	val emojis: String,
	@SerialName("contains_masks") val containsMasks: Boolean? = null,
	@SerialName("mask_position") val maskPosition: MaskPosition? = null
)

/**
 * Use this method to create a new sticker set owned by a user. The bot will be able to edit the sticker set thus created. You must use exactly one of the fields png_sticker or tgs_sticker. Returns True on success.
 */
suspend fun TelegramBotApiClient.createNewStickerSet(requestBody: CreateNewStickerSetRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "createNewStickerSet")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to create a new sticker set owned by a user. The bot will be able to edit the sticker set thus created. You must use exactly one of the fields png_sticker or tgs_sticker. Returns True on success.
 * @param userId User identifier of created sticker set owner
 * @param name Short name of sticker set, to be used in t.me/addstickers/ URLs (e.g., animals). Can contain only english letters, digits and underscores. Must begin with a letter, can't contain consecutive underscores and must end in “_by_<bot username>”. <bot_username> is case insensitive. 1-64 characters.
 * @param title Sticker set title, 1-64 characters
 * @param pngSticker PNG image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px, and either width or height must be exactly 512px. Pass a file_id as a String to send a file that already exists on the Telegram servers, pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
 * @param tgsSticker TGS animation with the sticker, uploaded using multipart/form-data. See https://core.telegram.org/animated_stickers#technical-requirements for technical requirements
 * @param emojis One or more emoji corresponding to the sticker
 * @param containsMasks Pass True, if a set of mask stickers should be created
 * @param maskPosition A JSON-serialized object for position where the mask should be placed on faces
 */
suspend fun TelegramBotApiClient.createNewStickerSet(
    userId: Int,
    name: String,
    title: String,
    pngSticker: String? = null,
    tgsSticker: String? = null,
    emojis: String,
    containsMasks: Boolean? = null,
    maskPosition: MaskPosition? = null,
) =
    createNewStickerSet(
        CreateNewStickerSetRequest(
            userId,
            name,
            title,
            pngSticker,
            tgsSticker,
            emojis,
            containsMasks,
            maskPosition
        )
    )

/**
 * Request body for [addStickerToSet]
 * @param userId User identifier of sticker set owner
 * @param name Sticker set name
 * @param pngSticker PNG image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px, and either width or height must be exactly 512px. Pass a file_id as a String to send a file that already exists on the Telegram servers, pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
 * @param tgsSticker TGS animation with the sticker, uploaded using multipart/form-data. See https://core.telegram.org/animated_stickers#technical-requirements for technical requirements
 * @param emojis One or more emoji corresponding to the sticker
 * @param maskPosition A JSON-serialized object for position where the mask should be placed on faces
 */
@Serializable
data class AddStickerToSetRequest(
	@SerialName("user_id") val userId: Int,
	val name: String,
	@SerialName("png_sticker") val pngSticker: String? = null,
	@SerialName("tgs_sticker") val tgsSticker: String? = null,
	val emojis: String,
	@SerialName("mask_position") val maskPosition: MaskPosition? = null
)

/**
 * Use this method to add a new sticker to a set created by the bot. You must use exactly one of the fields png_sticker or tgs_sticker. Animated stickers can be added to animated sticker sets and only to them. Animated sticker sets can have up to 50 stickers. Static sticker sets can have up to 120 stickers. Returns True on success.
 */
suspend fun TelegramBotApiClient.addStickerToSet(requestBody: AddStickerToSetRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "addStickerToSet")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to add a new sticker to a set created by the bot. You must use exactly one of the fields png_sticker or tgs_sticker. Animated stickers can be added to animated sticker sets and only to them. Animated sticker sets can have up to 50 stickers. Static sticker sets can have up to 120 stickers. Returns True on success.
 * @param userId User identifier of sticker set owner
 * @param name Sticker set name
 * @param pngSticker PNG image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px, and either width or height must be exactly 512px. Pass a file_id as a String to send a file that already exists on the Telegram servers, pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
 * @param tgsSticker TGS animation with the sticker, uploaded using multipart/form-data. See https://core.telegram.org/animated_stickers#technical-requirements for technical requirements
 * @param emojis One or more emoji corresponding to the sticker
 * @param maskPosition A JSON-serialized object for position where the mask should be placed on faces
 */
suspend fun TelegramBotApiClient.addStickerToSet(
    userId: Int,
    name: String,
    pngSticker: String? = null,
    tgsSticker: String? = null,
    emojis: String,
    maskPosition: MaskPosition? = null,
) =
    addStickerToSet(
        AddStickerToSetRequest(
            userId,
            name,
            pngSticker,
            tgsSticker,
            emojis,
            maskPosition
        )
    )

/**
 * Request body for [setStickerPositionInSet]
 * @param sticker File identifier of the sticker
 * @param position New sticker position in the set, zero-based
 */
@Serializable
data class SetStickerPositionInSetRequest(
	val sticker: String,
	val position: Int
)

/**
 * Use this method to move a sticker in a set created by the bot to a specific position. Returns True on success.
 */
suspend fun TelegramBotApiClient.setStickerPositionInSet(requestBody: SetStickerPositionInSetRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "setStickerPositionInSet")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to move a sticker in a set created by the bot to a specific position. Returns True on success.
 * @param sticker File identifier of the sticker
 * @param position New sticker position in the set, zero-based
 */
suspend fun TelegramBotApiClient.setStickerPositionInSet(
    sticker: String,
    position: Int,
) =
    setStickerPositionInSet(
        SetStickerPositionInSetRequest(
            sticker,
            position
        )
    )

/**
 * Request body for [deleteStickerFromSet]
 * @param sticker File identifier of the sticker
 */
@Serializable
data class DeleteStickerFromSetRequest(
	val sticker: String
)

/**
 * Use this method to delete a sticker from a set created by the bot. Returns True on success.
 */
suspend fun TelegramBotApiClient.deleteStickerFromSet(requestBody: DeleteStickerFromSetRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "deleteStickerFromSet")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to delete a sticker from a set created by the bot. Returns True on success.
 * @param sticker File identifier of the sticker
 */
suspend fun TelegramBotApiClient.deleteStickerFromSet(
    sticker: String,
) =
    deleteStickerFromSet(
        DeleteStickerFromSetRequest(
            sticker
        )
    )

/**
 * Request body for [setStickerSetThumb]
 * @param name Sticker set name
 * @param userId User identifier of the sticker set owner
 * @param thumb A PNG image with the thumbnail, must be up to 128 kilobytes in size and have width and height exactly 100px, or a TGS animation with the thumbnail up to 32 kilobytes in size; see https://core.telegram.org/animated_stickers#technical-requirements for animated sticker technical requirements. Pass a file_id as a String to send a file that already exists on the Telegram servers, pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files ». Animated sticker set thumbnail can't be uploaded via HTTP URL.
 */
@Serializable
data class SetStickerSetThumbRequest(
	val name: String,
	@SerialName("user_id") val userId: Int,
	val thumb: String? = null
)

/**
 * Use this method to set the thumbnail of a sticker set. Animated thumbnails can be set for animated sticker sets only. Returns True on success.
 * The following methods and objects allow your bot to work in inline mode. Please see our Introduction to Inline bots for more details.
 * To enable this option, send the /setinline command to @BotFather and provide the placeholder text that the user will see in the input field after typing your bot's name.
 */
suspend fun TelegramBotApiClient.setStickerSetThumb(requestBody: SetStickerSetThumbRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "setStickerSetThumb")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to set the thumbnail of a sticker set. Animated thumbnails can be set for animated sticker sets only. Returns True on success.
 * The following methods and objects allow your bot to work in inline mode. Please see our Introduction to Inline bots for more details.
 * To enable this option, send the /setinline command to @BotFather and provide the placeholder text that the user will see in the input field after typing your bot's name.
 * @param name Sticker set name
 * @param userId User identifier of the sticker set owner
 * @param thumb A PNG image with the thumbnail, must be up to 128 kilobytes in size and have width and height exactly 100px, or a TGS animation with the thumbnail up to 32 kilobytes in size; see https://core.telegram.org/animated_stickers#technical-requirements for animated sticker technical requirements. Pass a file_id as a String to send a file that already exists on the Telegram servers, pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files ». Animated sticker set thumbnail can't be uploaded via HTTP URL.
 */
suspend fun TelegramBotApiClient.setStickerSetThumb(
    name: String,
    userId: Int,
    thumb: String? = null,
) =
    setStickerSetThumb(
        SetStickerSetThumbRequest(
            name,
            userId,
            thumb
        )
    )

/**
 * Request body for [answerInlineQuery]
 * @param inlineQueryId Unique identifier for the answered query
 * @param results A JSON-serialized array of results for the inline query
 * @param cacheTime The maximum amount of time in seconds that the result of the inline query may be cached on the server. Defaults to 300.
 * @param isPersonal Pass True, if results may be cached on the server side only for the user that sent the query. By default, results may be returned to any user who sends the same query
 * @param nextOffset Pass the offset that a client should send in the next query with the same text to receive more results. Pass an empty string if there are no more results or if you don't support pagination. Offset length can't exceed 64 bytes.
 * @param switchPmText If passed, clients will display a button with specified text that switches the user to a private chat with the bot and sends the bot a start message with the parameter switch_pm_parameter
 * @param switchPmParameter Deep-linking parameter for the /start message sent to the bot when user presses the switch button. 1-64 characters, only A-Z, a-z, 0-9, _ and - are allowed. Example: An inline bot that sends YouTube videos can ask the user to connect the bot to their YouTube account to adapt search results accordingly. To do this, it displays a 'Connect your YouTube account' button above the results, or even before showing any. The user presses the button, switches to a private chat with the bot and, in doing so, passes a start parameter that instructs the bot to return an oauth link. Once done, the bot can offer a switch_inline button so that the user can easily return to the chat where they wanted to use the bot's inline capabilities.
 */
@Serializable
data class AnswerInlineQueryRequest(
	@SerialName("inline_query_id") val inlineQueryId: String,
	val results: List<InlineQueryResult> = emptyList(),
	@SerialName("cache_time") val cacheTime: Int? = null,
	@SerialName("is_personal") val isPersonal: Boolean? = null,
	@SerialName("next_offset") val nextOffset: String? = null,
	@SerialName("switch_pm_text") val switchPmText: String? = null,
	@SerialName("switch_pm_parameter") val switchPmParameter: String? = null
)

/**
 * Use this method to send answers to an inline query. On success, True is returned. No more than 50 results per query are allowed.
 */
suspend fun TelegramBotApiClient.answerInlineQuery(requestBody: AnswerInlineQueryRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "answerInlineQuery")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send answers to an inline query. On success, True is returned. No more than 50 results per query are allowed.
 * @param inlineQueryId Unique identifier for the answered query
 * @param results A JSON-serialized array of results for the inline query
 * @param cacheTime The maximum amount of time in seconds that the result of the inline query may be cached on the server. Defaults to 300.
 * @param isPersonal Pass True, if results may be cached on the server side only for the user that sent the query. By default, results may be returned to any user who sends the same query
 * @param nextOffset Pass the offset that a client should send in the next query with the same text to receive more results. Pass an empty string if there are no more results or if you don't support pagination. Offset length can't exceed 64 bytes.
 * @param switchPmText If passed, clients will display a button with specified text that switches the user to a private chat with the bot and sends the bot a start message with the parameter switch_pm_parameter
 * @param switchPmParameter Deep-linking parameter for the /start message sent to the bot when user presses the switch button. 1-64 characters, only A-Z, a-z, 0-9, _ and - are allowed. Example: An inline bot that sends YouTube videos can ask the user to connect the bot to their YouTube account to adapt search results accordingly. To do this, it displays a 'Connect your YouTube account' button above the results, or even before showing any. The user presses the button, switches to a private chat with the bot and, in doing so, passes a start parameter that instructs the bot to return an oauth link. Once done, the bot can offer a switch_inline button so that the user can easily return to the chat where they wanted to use the bot's inline capabilities.
 */
suspend fun TelegramBotApiClient.answerInlineQuery(
    inlineQueryId: String,
    results: List<InlineQueryResult> = emptyList(),
    cacheTime: Int? = null,
    isPersonal: Boolean? = null,
    nextOffset: String? = null,
    switchPmText: String? = null,
    switchPmParameter: String? = null,
) =
    answerInlineQuery(
        AnswerInlineQueryRequest(
            inlineQueryId,
            results,
            cacheTime,
            isPersonal,
            nextOffset,
            switchPmText,
            switchPmParameter
        )
    )

/**
 * Request body for [sendInvoice]
 * @param chatId Unique identifier for the target private chat
 * @param title Product name, 1-32 characters
 * @param description Product description, 1-255 characters
 * @param payload Bot-defined invoice payload, 1-128 bytes. This will not be displayed to the user, use for your internal processes.
 * @param providerToken Payments provider token, obtained via Botfather
 * @param startParameter Unique deep-linking parameter that can be used to generate this invoice when used as a start parameter
 * @param currency Three-letter ISO 4217 currency code, see more on currencies
 * @param prices Price breakdown, a JSON-serialized list of components (e.g. product price, tax, discount, delivery cost, delivery tax, bonus, etc.)
 * @param providerData A JSON-serialized data about the invoice, which will be shared with the payment provider. A detailed description of required fields should be provided by the payment provider.
 * @param photoUrl URL of the product photo for the invoice. Can be a photo of the goods or a marketing image for a service. People like it better when they see what they are paying for.
 * @param photoSize Photo size
 * @param photoWidth Photo width
 * @param photoHeight Photo height
 * @param needName Pass True, if you require the user's full name to complete the order
 * @param needPhoneNumber Pass True, if you require the user's phone number to complete the order
 * @param needEmail Pass True, if you require the user's email address to complete the order
 * @param needShippingAddress Pass True, if you require the user's shipping address to complete the order
 * @param sendPhoneNumberToProvider Pass True, if user's phone number should be sent to provider
 * @param sendEmailToProvider Pass True, if user's email address should be sent to provider
 * @param isFlexible Pass True, if the final price depends on the shipping method
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup A JSON-serialized object for an inline keyboard. If empty, one 'Pay total price' button will be shown. If not empty, the first button must be a Pay button.
 */
@Serializable
data class SendInvoiceRequest(
	@SerialName("chat_id") val chatId: Int,
	val title: String,
	val description: String,
	val payload: String,
	@SerialName("provider_token") val providerToken: String,
	@SerialName("start_parameter") val startParameter: String,
	val currency: String,
	val prices: List<LabeledPrice> = emptyList(),
	@SerialName("provider_data") val providerData: String? = null,
	@SerialName("photo_url") val photoUrl: String? = null,
	@SerialName("photo_size") val photoSize: Int? = null,
	@SerialName("photo_width") val photoWidth: Int? = null,
	@SerialName("photo_height") val photoHeight: Int? = null,
	@SerialName("need_name") val needName: Boolean? = null,
	@SerialName("need_phone_number") val needPhoneNumber: Boolean? = null,
	@SerialName("need_email") val needEmail: Boolean? = null,
	@SerialName("need_shipping_address") val needShippingAddress: Boolean? = null,
	@SerialName("send_phone_number_to_provider") val sendPhoneNumberToProvider: Boolean? = null,
	@SerialName("send_email_to_provider") val sendEmailToProvider: Boolean? = null,
	@SerialName("is_flexible") val isFlexible: Boolean? = null,
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null
)

/**
 * Use this method to send invoices. On success, the sent Message is returned.
 */
suspend fun TelegramBotApiClient.sendInvoice(requestBody: SendInvoiceRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendInvoice")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send invoices. On success, the sent Message is returned.
 * @param chatId Unique identifier for the target private chat
 * @param title Product name, 1-32 characters
 * @param description Product description, 1-255 characters
 * @param payload Bot-defined invoice payload, 1-128 bytes. This will not be displayed to the user, use for your internal processes.
 * @param providerToken Payments provider token, obtained via Botfather
 * @param startParameter Unique deep-linking parameter that can be used to generate this invoice when used as a start parameter
 * @param currency Three-letter ISO 4217 currency code, see more on currencies
 * @param prices Price breakdown, a JSON-serialized list of components (e.g. product price, tax, discount, delivery cost, delivery tax, bonus, etc.)
 * @param providerData A JSON-serialized data about the invoice, which will be shared with the payment provider. A detailed description of required fields should be provided by the payment provider.
 * @param photoUrl URL of the product photo for the invoice. Can be a photo of the goods or a marketing image for a service. People like it better when they see what they are paying for.
 * @param photoSize Photo size
 * @param photoWidth Photo width
 * @param photoHeight Photo height
 * @param needName Pass True, if you require the user's full name to complete the order
 * @param needPhoneNumber Pass True, if you require the user's phone number to complete the order
 * @param needEmail Pass True, if you require the user's email address to complete the order
 * @param needShippingAddress Pass True, if you require the user's shipping address to complete the order
 * @param sendPhoneNumberToProvider Pass True, if user's phone number should be sent to provider
 * @param sendEmailToProvider Pass True, if user's email address should be sent to provider
 * @param isFlexible Pass True, if the final price depends on the shipping method
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup A JSON-serialized object for an inline keyboard. If empty, one 'Pay total price' button will be shown. If not empty, the first button must be a Pay button.
 */
suspend fun TelegramBotApiClient.sendInvoice(
    chatId: Int,
    title: String,
    description: String,
    payload: String,
    providerToken: String,
    startParameter: String,
    currency: String,
    prices: List<LabeledPrice> = emptyList(),
    providerData: String? = null,
    photoUrl: String? = null,
    photoSize: Int? = null,
    photoWidth: Int? = null,
    photoHeight: Int? = null,
    needName: Boolean? = null,
    needPhoneNumber: Boolean? = null,
    needEmail: Boolean? = null,
    needShippingAddress: Boolean? = null,
    sendPhoneNumberToProvider: Boolean? = null,
    sendEmailToProvider: Boolean? = null,
    isFlexible: Boolean? = null,
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: InlineKeyboardMarkup? = null,
) =
    sendInvoice(
        SendInvoiceRequest(
            chatId,
            title,
            description,
            payload,
            providerToken,
            startParameter,
            currency,
            prices,
            providerData,
            photoUrl,
            photoSize,
            photoWidth,
            photoHeight,
            needName,
            needPhoneNumber,
            needEmail,
            needShippingAddress,
            sendPhoneNumberToProvider,
            sendEmailToProvider,
            isFlexible,
            disableNotification,
            replyToMessageId,
            allowSendingWithoutReply,
            replyMarkup
        )
    )

/**
 * Request body for [answerShippingQuery]
 * @param shippingQueryId Unique identifier for the query to be answered
 * @param ok Specify True if delivery to the specified address is possible and False if there are any problems (for example, if delivery to the specified address is not possible)
 * @param shippingOptions Required if ok is True. A JSON-serialized array of available shipping options.
 * @param errorMessage Required if ok is False. Error message in human readable form that explains why it is impossible to complete the order (e.g. "Sorry, delivery to your desired address is unavailable'). Telegram will display this message to the user.
 */
@Serializable
data class AnswerShippingQueryRequest(
	@SerialName("shipping_query_id") val shippingQueryId: String,
	val ok: Boolean,
	@SerialName("shipping_options") val shippingOptions: List<ShippingOption> = emptyList(),
	@SerialName("error_message") val errorMessage: String? = null
)

/**
 * If you sent an invoice requesting a shipping address and the parameter is_flexible was specified, the Bot API will send an Update with a shipping_query field to the bot. Use this method to reply to shipping queries. On success, True is returned.
 */
suspend fun TelegramBotApiClient.answerShippingQuery(requestBody: AnswerShippingQueryRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "answerShippingQuery")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * If you sent an invoice requesting a shipping address and the parameter is_flexible was specified, the Bot API will send an Update with a shipping_query field to the bot. Use this method to reply to shipping queries. On success, True is returned.
 * @param shippingQueryId Unique identifier for the query to be answered
 * @param ok Specify True if delivery to the specified address is possible and False if there are any problems (for example, if delivery to the specified address is not possible)
 * @param shippingOptions Required if ok is True. A JSON-serialized array of available shipping options.
 * @param errorMessage Required if ok is False. Error message in human readable form that explains why it is impossible to complete the order (e.g. "Sorry, delivery to your desired address is unavailable'). Telegram will display this message to the user.
 */
suspend fun TelegramBotApiClient.answerShippingQuery(
    shippingQueryId: String,
    ok: Boolean,
    shippingOptions: List<ShippingOption> = emptyList(),
    errorMessage: String? = null,
) =
    answerShippingQuery(
        AnswerShippingQueryRequest(
            shippingQueryId,
            ok,
            shippingOptions,
            errorMessage
        )
    )

/**
 * Request body for [answerPreCheckoutQuery]
 * @param preCheckoutQueryId Unique identifier for the query to be answered
 * @param ok Specify True if everything is alright (goods are available, etc.) and the bot is ready to proceed with the order. Use False if there are any problems.
 * @param errorMessage Required if ok is False. Error message in human readable form that explains the reason for failure to proceed with the checkout (e.g. "Sorry, somebody just bought the last of our amazing black T-shirts while you were busy filling out your payment details. Please choose a different color or garment!"). Telegram will display this message to the user.
 */
@Serializable
data class AnswerPreCheckoutQueryRequest(
	@SerialName("pre_checkout_query_id") val preCheckoutQueryId: String,
	val ok: Boolean,
	@SerialName("error_message") val errorMessage: String? = null
)

/**
 * Once the user has confirmed their payment and shipping details, the Bot API sends the final confirmation in the form of an Update with the field pre_checkout_query. Use this method to respond to such pre-checkout queries. On success, True is returned. Note: The Bot API must receive an answer within 10 seconds after the pre-checkout query was sent.
 */
suspend fun TelegramBotApiClient.answerPreCheckoutQuery(requestBody: AnswerPreCheckoutQueryRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "answerPreCheckoutQuery")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Once the user has confirmed their payment and shipping details, the Bot API sends the final confirmation in the form of an Update with the field pre_checkout_query. Use this method to respond to such pre-checkout queries. On success, True is returned. Note: The Bot API must receive an answer within 10 seconds after the pre-checkout query was sent.
 * @param preCheckoutQueryId Unique identifier for the query to be answered
 * @param ok Specify True if everything is alright (goods are available, etc.) and the bot is ready to proceed with the order. Use False if there are any problems.
 * @param errorMessage Required if ok is False. Error message in human readable form that explains the reason for failure to proceed with the checkout (e.g. "Sorry, somebody just bought the last of our amazing black T-shirts while you were busy filling out your payment details. Please choose a different color or garment!"). Telegram will display this message to the user.
 */
suspend fun TelegramBotApiClient.answerPreCheckoutQuery(
    preCheckoutQueryId: String,
    ok: Boolean,
    errorMessage: String? = null,
) =
    answerPreCheckoutQuery(
        AnswerPreCheckoutQueryRequest(
            preCheckoutQueryId,
            ok,
            errorMessage
        )
    )

/**
 * Request body for [setPassportDataErrors]
 * @param userId User identifier
 * @param errors A JSON-serialized array describing the errors
 */
@Serializable
data class SetPassportDataErrorsRequest(
	@SerialName("user_id") val userId: Int,
	val errors: List<PassportElementError> = emptyList()
)

/**
 * Informs a user that some of the Telegram Passport elements they provided contains errors. The user will not be able to re-submit their Passport to you until the errors are fixed (the contents of the field for which you returned the error must change). Returns True on success.
 * Use this if the data submitted by the user doesn't satisfy the standards your service requires for any reason. For example, if a birthday date seems invalid, a submitted document is blurry, a scan shows evidence of tampering, etc. Supply some details in the error message to make sure the user knows how to correct the issues.
 */
suspend fun TelegramBotApiClient.setPassportDataErrors(requestBody: SetPassportDataErrorsRequest) =
    httpClient.post<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "setPassportDataErrors")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Informs a user that some of the Telegram Passport elements they provided contains errors. The user will not be able to re-submit their Passport to you until the errors are fixed (the contents of the field for which you returned the error must change). Returns True on success.
 * Use this if the data submitted by the user doesn't satisfy the standards your service requires for any reason. For example, if a birthday date seems invalid, a submitted document is blurry, a scan shows evidence of tampering, etc. Supply some details in the error message to make sure the user knows how to correct the issues.
 * @param userId User identifier
 * @param errors A JSON-serialized array describing the errors
 */
suspend fun TelegramBotApiClient.setPassportDataErrors(
    userId: Int,
    errors: List<PassportElementError> = emptyList(),
) =
    setPassportDataErrors(
        SetPassportDataErrorsRequest(
            userId,
            errors
        )
    )

/**
 * Request body for [sendGame]
 * @param chatId Unique identifier for the target chat
 * @param gameShortName Short name of the game, serves as the unique identifier for the game. Set up your games via Botfather.
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup A JSON-serialized object for an inline keyboard. If empty, one 'Play game_title' button will be shown. If not empty, the first button must launch the game.
 */
@Serializable
data class SendGameRequest(
	@SerialName("chat_id") val chatId: Int,
	@SerialName("game_short_name") val gameShortName: String,
	@SerialName("disable_notification") val disableNotification: Boolean? = null,
	@SerialName("reply_to_message_id") val replyToMessageId: Int? = null,
	@SerialName("allow_sending_without_reply") val allowSendingWithoutReply: Boolean? = null,
	@SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null
)

/**
 * Use this method to send a game. On success, the sent Message is returned.
 */
suspend fun TelegramBotApiClient.sendGame(requestBody: SendGameRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "sendGame")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to send a game. On success, the sent Message is returned.
 * @param chatId Unique identifier for the target chat
 * @param gameShortName Short name of the game, serves as the unique identifier for the game. Set up your games via Botfather.
 * @param disableNotification Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup A JSON-serialized object for an inline keyboard. If empty, one 'Play game_title' button will be shown. If not empty, the first button must launch the game.
 */
suspend fun TelegramBotApiClient.sendGame(
    chatId: Int,
    gameShortName: String,
    disableNotification: Boolean? = null,
    replyToMessageId: Int? = null,
    allowSendingWithoutReply: Boolean? = null,
    replyMarkup: InlineKeyboardMarkup? = null,
) =
    sendGame(
        SendGameRequest(
            chatId,
            gameShortName,
            disableNotification,
            replyToMessageId,
            allowSendingWithoutReply,
            replyMarkup
        )
    )

/**
 * Request body for [setGameScore]
 * @param userId User identifier
 * @param score New score, must be non-negative
 * @param force Pass True, if the high score is allowed to decrease. This can be useful when fixing mistakes or banning cheaters
 * @param disableEditMessage Pass True, if the game message should not be automatically edited to include the current scoreboard
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat
 * @param messageId Required if inline_message_id is not specified. Identifier of the sent message
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 */
@Serializable
data class SetGameScoreRequest(
	@SerialName("user_id") val userId: Int,
	val score: Int,
	val force: Boolean? = null,
	@SerialName("disable_edit_message") val disableEditMessage: Boolean? = null,
	@SerialName("chat_id") val chatId: Int? = null,
	@SerialName("message_id") val messageId: Int? = null,
	@SerialName("inline_message_id") val inlineMessageId: String? = null
)

/**
 * Use this method to set the score of the specified user in a game. On success, if the message was sent by the bot, returns the edited Message, otherwise returns True. Returns an error, if the new score is not greater than the user's current score in the chat and force is False.
 */
suspend fun TelegramBotApiClient.setGameScore(requestBody: SetGameScoreRequest) =
    httpClient.post<TelegramResponse<Message>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "setGameScore")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to set the score of the specified user in a game. On success, if the message was sent by the bot, returns the edited Message, otherwise returns True. Returns an error, if the new score is not greater than the user's current score in the chat and force is False.
 * @param userId User identifier
 * @param score New score, must be non-negative
 * @param force Pass True, if the high score is allowed to decrease. This can be useful when fixing mistakes or banning cheaters
 * @param disableEditMessage Pass True, if the game message should not be automatically edited to include the current scoreboard
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat
 * @param messageId Required if inline_message_id is not specified. Identifier of the sent message
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 */
suspend fun TelegramBotApiClient.setGameScore(
    userId: Int,
    score: Int,
    force: Boolean? = null,
    disableEditMessage: Boolean? = null,
    chatId: Int? = null,
    messageId: Int? = null,
    inlineMessageId: String? = null,
) =
    setGameScore(
        SetGameScoreRequest(
            userId,
            score,
            force,
            disableEditMessage,
            chatId,
            messageId,
            inlineMessageId
        )
    )

/**
 * Request body for [getGameHighScores]
 * @param userId Target user id
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat
 * @param messageId Required if inline_message_id is not specified. Identifier of the sent message
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 */
@Serializable
data class GetGameHighScoresRequest(
	@SerialName("user_id") val userId: Int,
	@SerialName("chat_id") val chatId: Int? = null,
	@SerialName("message_id") val messageId: Int? = null,
	@SerialName("inline_message_id") val inlineMessageId: String? = null
)

/**
 * Use this method to get data for high score tables. Will return the score of the specified user and several of their neighbors in a game. On success, returns an Array of GameHighScore objects.
 * This method will currently return scores for the target user, plus two of their closest neighbors on each side. Will also return the top three users if the user and his neighbors are not among them. Please note that this behavior is subject to change.
 */
suspend fun TelegramBotApiClient.getGameHighScores(requestBody: GetGameHighScoresRequest) =
    httpClient.post<TelegramResponse<List<GameHighScore>>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "getGameHighScores")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

/**
 * Use this method to get data for high score tables. Will return the score of the specified user and several of their neighbors in a game. On success, returns an Array of GameHighScore objects.
 * This method will currently return scores for the target user, plus two of their closest neighbors on each side. Will also return the top three users if the user and his neighbors are not among them. Please note that this behavior is subject to change.
 * @param userId Target user id
 * @param chatId Required if inline_message_id is not specified. Unique identifier for the target chat
 * @param messageId Required if inline_message_id is not specified. Identifier of the sent message
 * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message
 */
suspend fun TelegramBotApiClient.getGameHighScores(
    userId: Int,
    chatId: Int? = null,
    messageId: Int? = null,
    inlineMessageId: String? = null,
) =
    getGameHighScores(
        GetGameHighScoresRequest(
            userId,
            chatId,
            messageId,
            inlineMessageId
        )
    )


/**
 * Use this method to get current webhook status. Requires no parameters. On success, returns a WebhookInfo object. If the bot is using getUpdates, will return an object with the url field empty.
 */
suspend fun TelegramBotApiClient.getWebhookInfo() =
    httpClient.get<TelegramResponse<WebhookInfo>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "getWebhookInfo")
        }
    }


/**
 * A simple method for testing your bot's auth token. Requires no parameters. Returns basic information about the bot in form of a User object.
 */
suspend fun TelegramBotApiClient.getMe() =
    httpClient.get<TelegramResponse<User>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "getMe")
        }
    }


/**
 * Use this method to log out from the cloud Bot API server before launching the bot locally. You must log out the bot before running it locally, otherwise there is no guarantee that the bot will receive updates. After a successful call, you can immediately log in on a local server, but will not be able to log in back to the cloud Bot API server for 10 minutes. Returns True on success. Requires no parameters.
 */
suspend fun TelegramBotApiClient.logOut() =
    httpClient.get<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "logOut")
        }
    }



/**
 * Use this method to close the bot instance before moving it from one local server to another. You need to delete the webhook before calling this method to ensure that the bot isn't launched again after server restart. The method will return error 429 in the first 10 minutes after the bot is launched. Returns True on success. Requires no parameters.
 */
suspend fun TelegramBotApiClient.close() =
    httpClient.get<TelegramResponse<Boolean>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "close")
        }
    }


/**
 * Use this method to get the current list of the bot's commands. Requires no parameters. Returns Array of BotCommand on success.
 */
suspend fun TelegramBotApiClient.getMyCommands() =
    httpClient.get<TelegramResponse<List<BotCommand>>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "getMyCommands")
        }
    }


