package com.github.lamba92.telegram.api.generated

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.github.lamba92.telegram.api.data.*


/**
 * This object represents an incoming update. At most one of the optional parameters can be present in any given update.
 * @param updateId The update's unique identifier. Update identifiers start from a certain positive number and increase sequentially. This ID becomes especially handy if you're using Webhooks, since it allows you to ignore repeated updates or to restore the correct update sequence, should they get out of order. If there are no new updates for at least a week, then identifier of the next update will be chosen randomly instead of sequentially.
 * @param message Optional. New incoming message of any kind — text, photo, sticker, etc.
 * @param editedMessage Optional. New version of a message that is known to the bot and was edited
 * @param channelPost Optional. New incoming channel post of any kind — text, photo, sticker, etc.
 * @param editedChannelPost Optional. New version of a channel post that is known to the bot and was edited
 * @param inlineQuery Optional. New incoming inline query
 * @param chosenInlineResult Optional. The result of an inline query that was chosen by a user and sent to their chat partner. Please see our documentation on the feedback collecting for details on how to enable these updates for your bot.
 * @param callbackQuery Optional. New incoming callback query
 * @param shippingQuery Optional. New incoming shipping query. Only for invoices with flexible price
 * @param preCheckoutQuery Optional. New incoming pre-checkout query. Contains full information about checkout
 * @param poll Optional. New poll state. Bots receive only updates about stopped polls and polls, which are sent by the bot
 * @param pollAnswer Optional. A user changed their answer in a non-anonymous poll. Bots receive new votes only in polls that were sent by the bot itself.
 */
@Serializable
data class Update(
    @SerialName("update_id") val updateId: Int,
    val message: Message? = null,
    @SerialName("edited_message") val editedMessage: Message? = null,
    @SerialName("channel_post") val channelPost: Message? = null,
    @SerialName("edited_channel_post") val editedChannelPost: Message? = null,
    @SerialName("inline_query") val inlineQuery: InlineQuery? = null,
    @SerialName("chosen_inline_result") val chosenInlineResult: ChosenInlineResult? = null,
    @SerialName("callback_query") val callbackQuery: CallbackQuery? = null,
    @SerialName("shipping_query") val shippingQuery: ShippingQuery? = null,
    @SerialName("pre_checkout_query") val preCheckoutQuery: PreCheckoutQuery? = null,
    val poll: Poll? = null,
    @SerialName("poll_answer") val pollAnswer: PollAnswer? = null
)

/**
 * Contains information about the current status of a webhook.
 * All types used in the Bot API responses are represented as JSON-objects.
 * It is safe to use 32-bit signed integers for storing all Integer fields unless otherwise noted.
 * Optional fields may be not returned when irrelevant.
 * @param url Webhook URL, may be empty if webhook is not set up
 * @param hasCustomCertificate True, if a custom certificate was provided for webhook certificate checks
 * @param pendingUpdateCount Number of updates awaiting delivery
 * @param ipAddress Optional. Currently used webhook IP address
 * @param lastErrorDate Optional. Unix time for the most recent error that happened when trying to deliver an update via webhook
 * @param lastErrorMessage Optional. Error message in human-readable format for the most recent error that happened when trying to deliver an update via webhook
 * @param maxConnections Optional. Maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery
 * @param allowedUpdates Optional. A list of update types the bot is subscribed to. Defaults to all update types
 */
@Serializable
data class WebhookInfo(
    val url: String,
    @SerialName("has_custom_certificate") val hasCustomCertificate: Boolean,
    @SerialName("pending_update_count") val pendingUpdateCount: Int,
    @SerialName("ip_address") val ipAddress: String? = null,
    @SerialName("last_error_date") val lastErrorDate: Int? = null,
    @SerialName("last_error_message") val lastErrorMessage: String? = null,
    @SerialName("max_connections") val maxConnections: Int? = null,
    @SerialName("allowed_updates") val allowedUpdates: List<String> = emptyList()
)

/**
 * This object represents a Telegram user or bot.
 * @param id Unique identifier for this user or bot
 * @param isBot True, if this user is a bot
 * @param firstName User's or bot's first name
 * @param lastName Optional. User's or bot's last name
 * @param username Optional. User's or bot's username
 * @param languageCode Optional. IETF language tag of the user's language
 * @param canJoinGroups Optional. True, if the bot can be invited to groups. Returned only in getMe.
 * @param canReadAllGroupMessages Optional. True, if privacy mode is disabled for the bot. Returned only in getMe.
 * @param supportsInlineQueries Optional. True, if the bot supports inline queries. Returned only in getMe.
 */
@Serializable
data class User(
    val id: Int,
    @SerialName("is_bot") val isBot: Boolean,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String? = null,
    val username: String? = null,
    @SerialName("language_code") val languageCode: String? = null,
    @SerialName("can_join_groups") val canJoinGroups: Boolean? = null,
    @SerialName("can_read_all_group_messages") val canReadAllGroupMessages: Boolean? = null,
    @SerialName("supports_inline_queries") val supportsInlineQueries: Boolean? = null
)

/**
 * This object represents a chat.
 * @param id Unique identifier for this chat. This number may be greater than 32 bits and some programming languages may have difficulty/silent defects in interpreting it. But it is smaller than 52 bits, so a signed 64 bit integer or double-precision float type are safe for storing this identifier.
 * @param type Type of chat, can be either “private”, “group”, “supergroup” or “channel”
 * @param title Optional. Title, for supergroups, channels and group chats
 * @param username Optional. Username, for private chats, supergroups and channels if available
 * @param firstName Optional. First name of the other party in a private chat
 * @param lastName Optional. Last name of the other party in a private chat
 * @param photo Optional. Chat photo. Returned only in getChat.
 * @param bio Optional. Bio of the other party in a private chat. Returned only in getChat.
 * @param description Optional. Description, for groups, supergroups and channel chats. Returned only in getChat.
 * @param inviteLink Optional. Chat invite link, for groups, supergroups and channel chats. Each administrator in a chat generates their own invite links, so the bot must first generate the link using exportChatInviteLink. Returned only in getChat.
 * @param pinnedMessage Optional. The most recent pinned message (by sending date). Returned only in getChat.
 * @param permissions Optional. Default chat member permissions, for groups and supergroups. Returned only in getChat.
 * @param slowModeDelay Optional. For supergroups, the minimum allowed delay between consecutive messages sent by each unpriviledged user. Returned only in getChat.
 * @param stickerSetName Optional. For supergroups, name of group sticker set. Returned only in getChat.
 * @param canSetStickerSet Optional. True, if the bot can change the group sticker set. Returned only in getChat.
 * @param linkedChatId Optional. Unique identifier for the linked chat, i.e. the discussion group identifier for a channel and vice versa; for supergroups and channel chats. This identifier may be greater than 32 bits and some programming languages may have difficulty/silent defects in interpreting it. But it is smaller than 52 bits, so a signed 64 bit integer or double-precision float type are safe for storing this identifier. Returned only in getChat.
 * @param location Optional. For supergroups, the location to which the supergroup is connected. Returned only in getChat.
 */
@Serializable
data class Chat(
    val id: Int,
    val type: String,
    val title: String? = null,
    val username: String? = null,
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    val photo: ChatPhoto? = null,
    val bio: String? = null,
    val description: String? = null,
    @SerialName("invite_link") val inviteLink: String? = null,
    @SerialName("pinned_message") val pinnedMessage: Message? = null,
    val permissions: ChatPermissions? = null,
    @SerialName("slow_mode_delay") val slowModeDelay: Int? = null,
    @SerialName("sticker_set_name") val stickerSetName: String? = null,
    @SerialName("can_set_sticker_set") val canSetStickerSet: Boolean? = null,
    @SerialName("linked_chat_id") val linkedChatId: Int? = null,
    val location: ChatLocation? = null
)

/**
 * This object represents a message.
 * @param messageId Unique message identifier inside this chat
 * @param from Optional. Sender, empty for messages sent to channels
 * @param senderChat Optional. Sender of the message, sent on behalf of a chat. The channel itself for channel messages. The supergroup itself for messages from anonymous group administrators. The linked channel for messages automatically forwarded to the discussion group
 * @param date Date the message was sent in Unix time
 * @param chat Conversation the message belongs to
 * @param forwardFrom Optional. For forwarded messages, sender of the original message
 * @param forwardFromChat Optional. For messages forwarded from channels or from anonymous administrators, information about the original sender chat
 * @param forwardFromMessageId Optional. For messages forwarded from channels, identifier of the original message in the channel
 * @param forwardSignature Optional. For messages forwarded from channels, signature of the post author if present
 * @param forwardSenderName Optional. Sender's name for messages forwarded from users who disallow adding a link to their account in forwarded messages
 * @param forwardDate Optional. For forwarded messages, date the original message was sent in Unix time
 * @param replyToMessage Optional. For replies, the original message. Note that the Message object in this field will not contain further reply_to_message fields even if it itself is a reply.
 * @param viaBot Optional. Bot through which the message was sent
 * @param editDate Optional. Date the message was last edited in Unix time
 * @param mediaGroupId Optional. The unique identifier of a media message group this message belongs to
 * @param authorSignature Optional. Signature of the post author for messages in channels, or the custom title of an anonymous group administrator
 * @param text Optional. For text messages, the actual UTF-8 text of the message, 0-4096 characters
 * @param entities Optional. For text messages, special entities like usernames, URLs, bot commands, etc. that appear in the text
 * @param animation Optional. Message is an animation, information about the animation. For backward compatibility, when this field is set, the document field will also be set
 * @param audio Optional. Message is an audio file, information about the file
 * @param document Optional. Message is a general file, information about the file
 * @param photo Optional. Message is a photo, available sizes of the photo
 * @param sticker Optional. Message is a sticker, information about the sticker
 * @param video Optional. Message is a video, information about the video
 * @param videoNote Optional. Message is a video note, information about the video message
 * @param voice Optional. Message is a voice message, information about the file
 * @param caption Optional. Caption for the animation, audio, document, photo, video or voice, 0-1024 characters
 * @param captionEntities Optional. For messages with a caption, special entities like usernames, URLs, bot commands, etc. that appear in the caption
 * @param contact Optional. Message is a shared contact, information about the contact
 * @param dice Optional. Message is a dice with random value
 * @param game Optional. Message is a game, information about the game. More about games »
 * @param poll Optional. Message is a native poll, information about the poll
 * @param venue Optional. Message is a venue, information about the venue. For backward compatibility, when this field is set, the location field will also be set
 * @param location Optional. Message is a shared location, information about the location
 * @param newChatMembers Optional. New members that were added to the group or supergroup and information about them (the bot itself may be one of these members)
 * @param leftChatMember Optional. A member was removed from the group, information about them (this member may be the bot itself)
 * @param newChatTitle Optional. A chat title was changed to this value
 * @param newChatPhoto Optional. A chat photo was change to this value
 * @param deleteChatPhoto Optional. Service message: the chat photo was deleted
 * @param groupChatCreated Optional. Service message: the group has been created
 * @param supergroupChatCreated Optional. Service message: the supergroup has been created. This field can't be received in a message coming through updates, because bot can't be a member of a supergroup when it is created. It can only be found in reply_to_message if someone replies to a very first message in a directly created supergroup.
 * @param channelChatCreated Optional. Service message: the channel has been created. This field can't be received in a message coming through updates, because bot can't be a member of a channel when it is created. It can only be found in reply_to_message if someone replies to a very first message in a channel.
 * @param migrateToChatId Optional. The group has been migrated to a supergroup with the specified identifier. This number may be greater than 32 bits and some programming languages may have difficulty/silent defects in interpreting it. But it is smaller than 52 bits, so a signed 64 bit integer or double-precision float type are safe for storing this identifier.
 * @param migrateFromChatId Optional. The supergroup has been migrated from a group with the specified identifier. This number may be greater than 32 bits and some programming languages may have difficulty/silent defects in interpreting it. But it is smaller than 52 bits, so a signed 64 bit integer or double-precision float type are safe for storing this identifier.
 * @param pinnedMessage Optional. Specified message was pinned. Note that the Message object in this field will not contain further reply_to_message fields even if it is itself a reply.
 * @param invoice Optional. Message is an invoice for a payment, information about the invoice. More about payments »
 * @param successfulPayment Optional. Message is a service message about a successful payment, information about the payment. More about payments »
 * @param connectedWebsite Optional. The domain name of the website on which the user has logged in. More about Telegram Login »
 * @param passportData Optional. Telegram Passport data
 * @param proximityAlertTriggered Optional. Service message. A user in the chat triggered another user's proximity alert while sharing Live Location.
 * @param replyMarkup Optional. Inline keyboard attached to the message. login_url buttons are represented as ordinary url buttons.
 */
@Serializable
data class Message(
    @SerialName("message_id") val messageId: Int,
    val from: User? = null,
    @SerialName("sender_chat") val senderChat: Chat? = null,
    val date: Int,
    val chat: Chat,
    @SerialName("forward_from") val forwardFrom: User? = null,
    @SerialName("forward_from_chat") val forwardFromChat: Chat? = null,
    @SerialName("forward_from_message_id") val forwardFromMessageId: Int? = null,
    @SerialName("forward_signature") val forwardSignature: String? = null,
    @SerialName("forward_sender_name") val forwardSenderName: String? = null,
    @SerialName("forward_date") val forwardDate: Int? = null,
    @SerialName("reply_to_message") val replyToMessage: Message? = null,
    @SerialName("via_bot") val viaBot: User? = null,
    @SerialName("edit_date") val editDate: Int? = null,
    @SerialName("media_group_id") val mediaGroupId: String? = null,
    @SerialName("author_signature") val authorSignature: String? = null,
    val text: String? = null,
    val entities: List<MessageEntity> = emptyList(),
    val animation: Animation? = null,
    val audio: Audio? = null,
    val document: Document? = null,
    val photo: List<PhotoSize> = emptyList(),
    val sticker: Sticker? = null,
    val video: Video? = null,
    @SerialName("video_note") val videoNote: VideoNote? = null,
    val voice: Voice? = null,
    val caption: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    val contact: Contact? = null,
    val dice: Dice? = null,
    val game: Game? = null,
    val poll: Poll? = null,
    val venue: Venue? = null,
    val location: Location? = null,
    @SerialName("new_chat_members") val newChatMembers: List<User> = emptyList(),
    @SerialName("left_chat_member") val leftChatMember: User? = null,
    @SerialName("new_chat_title") val newChatTitle: String? = null,
    @SerialName("new_chat_photo") val newChatPhoto: List<PhotoSize> = emptyList(),
    @SerialName("delete_chat_photo") val deleteChatPhoto: Boolean? = null,
    @SerialName("group_chat_created") val groupChatCreated: Boolean? = null,
    @SerialName("supergroup_chat_created") val supergroupChatCreated: Boolean? = null,
    @SerialName("channel_chat_created") val channelChatCreated: Boolean? = null,
    @SerialName("migrate_to_chat_id") val migrateToChatId: Int? = null,
    @SerialName("migrate_from_chat_id") val migrateFromChatId: Int? = null,
    @SerialName("pinned_message") val pinnedMessage: Message? = null,
    val invoice: Invoice? = null,
    @SerialName("successful_payment") val successfulPayment: SuccessfulPayment? = null,
    @SerialName("connected_website") val connectedWebsite: String? = null,
    @SerialName("passport_data") val passportData: PassportData? = null,
    @SerialName("proximity_alert_triggered") val proximityAlertTriggered: ProximityAlertTriggered? = null,
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null
)

/**
 * This object represents a unique message identifier.
 * @param messageId Unique message identifier
 */
@Serializable
data class MessageId(
    @SerialName("message_id") val messageId: Int
)

/**
 * This object represents one special entity in a text message. For example, hashtags, usernames, URLs, etc.
 * @param type Type of the entity. Can be “mention” (@username), “hashtag” (#hashtag), “cashtag” ($USD), “bot_command” (/start@jobs_bot), “url” (https://telegram.org), “email” (do-not-reply@telegram.org), “phone_number” (+1-212-555-0123), “bold” (bold text), “italic” (italic text), “underline” (underlined text), “strikethrough” (strikethrough text), “code” (monowidth string), “pre” (monowidth block), “text_link” (for clickable text URLs), “text_mention” (for users without usernames)
 * @param offset Offset in UTF-16 code units to the start of the entity
 * @param length Length of the entity in UTF-16 code units
 * @param url Optional. For “text_link” only, url that will be opened after user taps on the text
 * @param user Optional. For “text_mention” only, the mentioned user
 * @param language Optional. For “pre” only, the programming language of the entity text
 */
@Serializable
data class MessageEntity(
    val type: String,
    val offset: Int,
    val length: Int,
    val url: String? = null,
    val user: User? = null,
    val language: String? = null
)

/**
 * This object represents one size of a photo or a file / sticker thumbnail.
 * @param fileId Identifier for this file, which can be used to download or reuse the file
 * @param fileUniqueId Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used to download or reuse the file.
 * @param width Photo width
 * @param height Photo height
 * @param fileSize Optional. File size
 */
@Serializable
data class PhotoSize(
    @SerialName("file_id") val fileId: String,
    @SerialName("file_unique_id") val fileUniqueId: String,
    val width: Int,
    val height: Int,
    @SerialName("file_size") val fileSize: Int? = null
)

/**
 * This object represents an animation file (GIF or H.264/MPEG-4 AVC video without sound).
 * @param fileId Identifier for this file, which can be used to download or reuse the file
 * @param fileUniqueId Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used to download or reuse the file.
 * @param width Video width as defined by sender
 * @param height Video height as defined by sender
 * @param duration Duration of the video in seconds as defined by sender
 * @param thumb Optional. Animation thumbnail as defined by sender
 * @param fileName Optional. Original animation filename as defined by sender
 * @param mimeType Optional. MIME type of the file as defined by sender
 * @param fileSize Optional. File size
 */
@Serializable
data class Animation(
    @SerialName("file_id") val fileId: String,
    @SerialName("file_unique_id") val fileUniqueId: String,
    val width: Int,
    val height: Int,
    val duration: Int,
    val thumb: PhotoSize? = null,
    @SerialName("file_name") val fileName: String? = null,
    @SerialName("mime_type") val mimeType: String? = null,
    @SerialName("file_size") val fileSize: Int? = null
)

/**
 * This object represents an audio file to be treated as music by the Telegram clients.
 * @param fileId Identifier for this file, which can be used to download or reuse the file
 * @param fileUniqueId Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used to download or reuse the file.
 * @param duration Duration of the audio in seconds as defined by sender
 * @param performer Optional. Performer of the audio as defined by sender or by audio tags
 * @param title Optional. Title of the audio as defined by sender or by audio tags
 * @param fileName Optional. Original filename as defined by sender
 * @param mimeType Optional. MIME type of the file as defined by sender
 * @param fileSize Optional. File size
 * @param thumb Optional. Thumbnail of the album cover to which the music file belongs
 */
@Serializable
data class Audio(
    @SerialName("file_id") val fileId: String,
    @SerialName("file_unique_id") val fileUniqueId: String,
    val duration: Int,
    val performer: String? = null,
    val title: String? = null,
    @SerialName("file_name") val fileName: String? = null,
    @SerialName("mime_type") val mimeType: String? = null,
    @SerialName("file_size") val fileSize: Int? = null,
    val thumb: PhotoSize? = null
)

/**
 * This object represents a general file (as opposed to photos, voice messages and audio files).
 * @param fileId Identifier for this file, which can be used to download or reuse the file
 * @param fileUniqueId Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used to download or reuse the file.
 * @param thumb Optional. Document thumbnail as defined by sender
 * @param fileName Optional. Original filename as defined by sender
 * @param mimeType Optional. MIME type of the file as defined by sender
 * @param fileSize Optional. File size
 */
@Serializable
data class Document(
    @SerialName("file_id") val fileId: String,
    @SerialName("file_unique_id") val fileUniqueId: String,
    val thumb: PhotoSize? = null,
    @SerialName("file_name") val fileName: String? = null,
    @SerialName("mime_type") val mimeType: String? = null,
    @SerialName("file_size") val fileSize: Int? = null
)

/**
 * This object represents a video file.
 * @param fileId Identifier for this file, which can be used to download or reuse the file
 * @param fileUniqueId Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used to download or reuse the file.
 * @param width Video width as defined by sender
 * @param height Video height as defined by sender
 * @param duration Duration of the video in seconds as defined by sender
 * @param thumb Optional. Video thumbnail
 * @param fileName Optional. Original filename as defined by sender
 * @param mimeType Optional. Mime type of a file as defined by sender
 * @param fileSize Optional. File size
 */
@Serializable
data class Video(
    @SerialName("file_id") val fileId: String,
    @SerialName("file_unique_id") val fileUniqueId: String,
    val width: Int,
    val height: Int,
    val duration: Int,
    val thumb: PhotoSize? = null,
    @SerialName("file_name") val fileName: String? = null,
    @SerialName("mime_type") val mimeType: String? = null,
    @SerialName("file_size") val fileSize: Int? = null
)

/**
 * This object represents a video message (available in Telegram apps as of v.4.0).
 * @param fileId Identifier for this file, which can be used to download or reuse the file
 * @param fileUniqueId Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used to download or reuse the file.
 * @param length Video width and height (diameter of the video message) as defined by sender
 * @param duration Duration of the video in seconds as defined by sender
 * @param thumb Optional. Video thumbnail
 * @param fileSize Optional. File size
 */
@Serializable
data class VideoNote(
    @SerialName("file_id") val fileId: String,
    @SerialName("file_unique_id") val fileUniqueId: String,
    val length: Int,
    val duration: Int,
    val thumb: PhotoSize? = null,
    @SerialName("file_size") val fileSize: Int? = null
)

/**
 * This object represents a voice note.
 * @param fileId Identifier for this file, which can be used to download or reuse the file
 * @param fileUniqueId Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used to download or reuse the file.
 * @param duration Duration of the audio in seconds as defined by sender
 * @param mimeType Optional. MIME type of the file as defined by sender
 * @param fileSize Optional. File size
 */
@Serializable
data class Voice(
    @SerialName("file_id") val fileId: String,
    @SerialName("file_unique_id") val fileUniqueId: String,
    val duration: Int,
    @SerialName("mime_type") val mimeType: String? = null,
    @SerialName("file_size") val fileSize: Int? = null
)

/**
 * This object represents a phone contact.
 * @param phoneNumber Contact's phone number
 * @param firstName Contact's first name
 * @param lastName Optional. Contact's last name
 * @param userId Optional. Contact's user identifier in Telegram
 * @param vcard Optional. Additional data about the contact in the form of a vCard
 */
@Serializable
data class Contact(
    @SerialName("phone_number") val phoneNumber: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("user_id") val userId: Int? = null,
    val vcard: String? = null
)

/**
 * This object represents an animated emoji that displays a random value.
 * @param emoji Emoji on which the dice throw animation is based
 * @param value Value of the dice, 1-6 for “” and “” base emoji, 1-5 for “” and “” base emoji, 1-64 for “” base emoji
 */
@Serializable
data class Dice(
    val emoji: String,
    val value: Int
)

/**
 * This object contains information about one answer option in a poll.
 * @param text Option text, 1-100 characters
 * @param voterCount Number of users that voted for this option
 */
@Serializable
data class PollOption(
    val text: String,
    @SerialName("voter_count") val voterCount: Int
)

/**
 * This object represents an answer of a user in a non-anonymous poll.
 * @param pollId Unique poll identifier
 * @param user The user, who changed the answer to the poll
 * @param optionIds 0-based identifiers of answer options, chosen by the user. May be empty if the user retracted their vote.
 */
@Serializable
data class PollAnswer(
    @SerialName("poll_id") val pollId: String,
    val user: User,
    @SerialName("option_ids") val optionIds: List<Int> = emptyList()
)

/**
 * This object contains information about a poll.
 * @param id Unique poll identifier
 * @param question Poll question, 1-300 characters
 * @param options List of poll options
 * @param totalVoterCount Total number of users that voted in the poll
 * @param isClosed True, if the poll is closed
 * @param isAnonymous True, if the poll is anonymous
 * @param type Poll type, currently can be “regular” or “quiz”
 * @param allowsMultipleAnswers True, if the poll allows multiple answers
 * @param correctOptionId Optional. 0-based identifier of the correct answer option. Available only for polls in the quiz mode, which are closed, or was sent (not forwarded) by the bot or to the private chat with the bot.
 * @param explanation Optional. Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style poll, 0-200 characters
 * @param explanationEntities Optional. Special entities like usernames, URLs, bot commands, etc. that appear in the explanation
 * @param openPeriod Optional. Amount of time in seconds the poll will be active after creation
 * @param closeDate Optional. Point in time (Unix timestamp) when the poll will be automatically closed
 */
@Serializable
data class Poll(
    val id: String,
    val question: String,
    val options: List<PollOption> = emptyList(),
    @SerialName("total_voter_count") val totalVoterCount: Int,
    @SerialName("is_closed") val isClosed: Boolean,
    @SerialName("is_anonymous") val isAnonymous: Boolean,
    val type: String,
    @SerialName("allows_multiple_answers") val allowsMultipleAnswers: Boolean,
    @SerialName("correct_option_id") val correctOptionId: Int? = null,
    val explanation: String? = null,
    @SerialName("explanation_entities") val explanationEntities: List<MessageEntity> = emptyList(),
    @SerialName("open_period") val openPeriod: Int? = null,
    @SerialName("close_date") val closeDate: Int? = null
)

/**
 * This object represents a point on the map.
 * @param longitude Longitude as defined by sender
 * @param latitude Latitude as defined by sender
 * @param horizontalAccuracy Optional. The radius of uncertainty for the location, measured in meters; 0-1500
 * @param livePeriod Optional. Time relative to the message sending date, during which the location can be updated, in seconds. For active live locations only.
 * @param heading Optional. The direction in which user is moving, in degrees; 1-360. For active live locations only.
 * @param proximityAlertRadius Optional. Maximum distance for proximity alerts about approaching another chat member, in meters. For sent live locations only.
 */
@Serializable
data class Location(
    val longitude: Float,
    val latitude: Float,
    @SerialName("horizontal_accuracy") val horizontalAccuracy: Float? = null,
    @SerialName("live_period") val livePeriod: Int? = null,
    val heading: Int? = null,
    @SerialName("proximity_alert_radius") val proximityAlertRadius: Int? = null
)

/**
 * This object represents a venue.
 * @param location Venue location. Can't be a live location
 * @param title Name of the venue
 * @param address Address of the venue
 * @param foursquareId Optional. Foursquare identifier of the venue
 * @param foursquareType Optional. Foursquare type of the venue. (For example, “arts_entertainment/default”, “arts_entertainment/aquarium” or “food/icecream”.)
 * @param googlePlaceId Optional. Google Places identifier of the venue
 * @param googlePlaceType Optional. Google Places type of the venue. (See supported types.)
 */
@Serializable
data class Venue(
    val location: Location,
    val title: String,
    val address: String,
    @SerialName("foursquare_id") val foursquareId: String? = null,
    @SerialName("foursquare_type") val foursquareType: String? = null,
    @SerialName("google_place_id") val googlePlaceId: String? = null,
    @SerialName("google_place_type") val googlePlaceType: String? = null
)

/**
 * This object represents the content of a service message, sent whenever a user in the chat triggers a proximity alert set by another user.
 * @param traveler User that triggered the alert
 * @param watcher User that set the alert
 * @param distance The distance between the users
 */
@Serializable
data class ProximityAlertTriggered(
    val traveler: User,
    val watcher: User,
    val distance: Int
)

/**
 * This object represent a user's profile pictures.
 * @param totalCount Total number of profile pictures the target user has
 * @param photos Requested profile pictures (in up to 4 sizes each)
 */
@Serializable
data class UserProfilePhotos(
    @SerialName("total_count") val totalCount: Int,
    val photos: List<List<PhotoSize>> = emptyList()
)

/**
 * This object represents a file ready to be downloaded. The file can be downloaded via the link https://api.telegram.org/file/bot<token>/<file_path>. It is guaranteed that the link will be valid for at least 1 hour. When the link expires, a new one can be requested by calling getFile.
 * Maximum file size to download is 20 MB
 * @param fileId Identifier for this file, which can be used to download or reuse the file
 * @param fileUniqueId Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used to download or reuse the file.
 * @param fileSize Optional. File size, if known
 * @param filePath Optional. File path. Use https://api.telegram.org/file/bot<token>/<file_path> to get the file.
 */
@Serializable
data class File(
    @SerialName("file_id") val fileId: String,
    @SerialName("file_unique_id") val fileUniqueId: String,
    @SerialName("file_size") val fileSize: Int? = null,
    @SerialName("file_path") val filePath: String? = null
)

/**
 * This object represents a custom keyboard with reply options (see Introduction to bots for details and examples).
 * @param keyboard Array of button rows, each represented by an Array of KeyboardButton objects
 * @param resizeKeyboard Optional. Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard smaller if there are just two rows of buttons). Defaults to false, in which case the custom keyboard is always of the same height as the app's standard keyboard.
 * @param oneTimeKeyboard Optional. Requests clients to hide the keyboard as soon as it's been used. The keyboard will still be available, but clients will automatically display the usual letter-keyboard in the chat – the user can press a special button in the input field to see the custom keyboard again. Defaults to false.
 * @param selective Optional. Use this parameter if you want to show the keyboard to specific users only. Targets: 1) users that are @mentioned in the text of the Message object; 2) if the bot's message is a reply (has reply_to_message_id), sender of the original message. Example: A user requests to change the bot's language, bot replies to the request with a keyboard to select the new language. Other users in the group don't see the keyboard.
 */
@Serializable
data class ReplyKeyboardMarkup(
    val keyboard: List<List<KeyboardButton>> = emptyList(),
    @SerialName("resize_keyboard") val resizeKeyboard: Boolean? = null,
    @SerialName("one_time_keyboard") val oneTimeKeyboard: Boolean? = null,
    val selective: Boolean? = null
) : ReplyMarkup()

/**
 * This object represents one button of the reply keyboard. For simple text buttons String can be used instead of this object to specify text of the button. Optional fields request_contact, request_location, and request_poll are mutually exclusive.
 * Note: request_contact and request_location options will only work in Telegram versions released after 9 April, 2016. Older clients will display unsupported message. Note: request_poll option will only work in Telegram versions released after 23 January, 2020. Older clients will display unsupported message.
 * @param text Text of the button. If none of the optional fields are used, it will be sent as a message when the button is pressed
 * @param requestContact Optional. If True, the user's phone number will be sent as a contact when the button is pressed. Available in private chats only
 * @param requestLocation Optional. If True, the user's current location will be sent when the button is pressed. Available in private chats only
 * @param requestPoll Optional. If specified, the user will be asked to create a poll and send it to the bot when the button is pressed. Available in private chats only
 */
@Serializable
data class KeyboardButton(
    val text: String,
    @SerialName("request_contact") val requestContact: Boolean? = null,
    @SerialName("request_location") val requestLocation: Boolean? = null,
    @SerialName("request_poll") val requestPoll: KeyboardButtonPollType? = null
)

/**
 * This object represents type of a poll, which is allowed to be created and sent when the corresponding button is pressed.
 * @param type Optional. If quiz is passed, the user will be allowed to create only polls in the quiz mode. If regular is passed, only regular polls will be allowed. Otherwise, the user will be allowed to create a poll of any type.
 */
@Serializable
data class KeyboardButtonPollType(
    val type: String? = null
)

/**
 * Upon receiving a message with this object, Telegram clients will remove the current custom keyboard and display the default letter-keyboard. By default, custom keyboards are displayed until a new keyboard is sent by a bot. An exception is made for one-time keyboards that are hidden immediately after the user presses a button (see ReplyKeyboardMarkup).
 * @param removeKeyboard Requests clients to remove the custom keyboard (user will not be able to summon this keyboard; if you want to hide the keyboard from sight but keep it accessible, use one_time_keyboard in ReplyKeyboardMarkup)
 * @param selective Optional. Use this parameter if you want to remove the keyboard for specific users only. Targets: 1) users that are @mentioned in the text of the Message object; 2) if the bot's message is a reply (has reply_to_message_id), sender of the original message. Example: A user votes in a poll, bot returns confirmation message in reply to the vote and removes the keyboard for that user, while still showing the keyboard with poll options to users who haven't voted yet.
 */
@Serializable
data class ReplyKeyboardRemove(
    @SerialName("remove_keyboard") val removeKeyboard: Boolean,
    val selective: Boolean? = null
) : ReplyMarkup()

/**
 * This object represents an inline keyboard that appears right next to the message it belongs to.
 * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will display unsupported message.
 * @param inlineKeyboard Array of button rows, each represented by an Array of InlineKeyboardButton objects
 */
@Serializable
data class InlineKeyboardMarkup(
    @SerialName("inline_keyboard") val inlineKeyboard: List<List<InlineKeyboardButton>> = emptyList()
) : ReplyMarkup()

/**
 * This object represents one button of an inline keyboard. You must use exactly one of the optional fields.
 * @param text Label text on the button
 * @param url Optional. HTTP or tg:// url to be opened when button is pressed
 * @param loginUrl Optional. An HTTP URL used to automatically authorize the user. Can be used as a replacement for the Telegram Login Widget.
 * @param callbackData Optional. Data to be sent in a callback query to the bot when button is pressed, 1-64 bytes
 * @param switchInlineQuery Optional. If set, pressing the button will prompt the user to select one of their chats, open that chat and insert the bot's username and the specified inline query in the input field. Can be empty, in which case just the bot's username will be inserted. Note: This offers an easy way for users to start using your bot in inline mode when they are currently in a private chat with it. Especially useful when combined with switch_pm… actions – in this case the user will be automatically returned to the chat they switched from, skipping the chat selection screen.
 * @param switchInlineQueryCurrentChat Optional. If set, pressing the button will insert the bot's username and the specified inline query in the current chat's input field. Can be empty, in which case only the bot's username will be inserted. This offers a quick way for the user to open your bot in inline mode in the same chat – good for selecting something from multiple options.
 * @param callbackGame Optional. Description of the game that will be launched when the user presses the button. NOTE: This type of button must always be the first button in the first row.
 * @param pay Optional. Specify True, to send a Pay button. NOTE: This type of button must always be the first button in the first row.
 */
@Serializable
data class InlineKeyboardButton(
    val text: String,
    val url: String? = null,
    @SerialName("login_url") val loginUrl: LoginUrl? = null,
    @SerialName("callback_data") val callbackData: String? = null,
    @SerialName("switch_inline_query") val switchInlineQuery: String? = null,
    @SerialName("switch_inline_query_current_chat") val switchInlineQueryCurrentChat: String? = null,
    @SerialName("callback_game") val callbackGame: CallbackGame? = null,
    val pay: Boolean? = null
)

/**
 * This object represents a parameter of the inline keyboard button used to automatically authorize a user. Serves as a great replacement for the Telegram Login Widget when the user is coming from Telegram. All the user needs to do is tap/click a button and confirm that they want to log in:
 * Telegram apps support these buttons as of version 5.7.
 * Sample bot: @discussbot
 * @param url An HTTP URL to be opened with user authorization data added to the query string when the button is pressed. If the user refuses to provide authorization data, the original URL without information about the user will be opened. The data added is the same as described in Receiving authorization data. NOTE: You must always check the hash of the received data to verify the authentication and the integrity of the data as described in Checking authorization.
 * @param forwardText Optional. New text of the button in forwarded messages.
 * @param botUsername Optional. Username of a bot, which will be used for user authorization. See Setting up a bot for more details. If not specified, the current bot's username will be assumed. The url's domain must be the same as the domain linked with the bot. See Linking your domain to the bot for more details.
 * @param requestWriteAccess Optional. Pass True to request the permission for your bot to send messages to the user.
 */
@Serializable
data class LoginUrl(
    val url: String,
    @SerialName("forward_text") val forwardText: String? = null,
    @SerialName("bot_username") val botUsername: String? = null,
    @SerialName("request_write_access") val requestWriteAccess: Boolean? = null
)

/**
 * This object represents an incoming callback query from a callback button in an inline keyboard. If the button that originated the query was attached to a message sent by the bot, the field message will be present. If the button was attached to a message sent via the bot (in inline mode), the field inline_message_id will be present. Exactly one of the fields data or game_short_name will be present.
 * NOTE: After the user presses a callback button, Telegram clients will display a progress bar until you call answerCallbackQuery. It is, therefore, necessary to react by calling answerCallbackQuery even if no notification to the user is needed (e.g., without specifying any of the optional parameters).
 * @param id Unique identifier for this query
 * @param from Sender
 * @param message Optional. Message with the callback button that originated the query. Note that message content and message date will not be available if the message is too old
 * @param inlineMessageId Optional. Identifier of the message sent via the bot in inline mode, that originated the query.
 * @param chatInstance Global identifier, uniquely corresponding to the chat to which the message with the callback button was sent. Useful for high scores in games.
 * @param data Optional. Data associated with the callback button. Be aware that a bad client can send arbitrary data in this field.
 * @param gameShortName Optional. Short name of a Game to be returned, serves as the unique identifier for the game
 */
@Serializable
data class CallbackQuery(
    val id: String,
    val from: User,
    val message: Message? = null,
    @SerialName("inline_message_id") val inlineMessageId: String? = null,
    @SerialName("chat_instance") val chatInstance: String,
    val data: String? = null,
    @SerialName("game_short_name") val gameShortName: String? = null
)

/**
 * Upon receiving a message with this object, Telegram clients will display a reply interface to the user (act as if the user has selected the bot's message and tapped 'Reply'). This can be extremely useful if you want to create user-friendly step-by-step interfaces without having to sacrifice privacy mode.
 * Example: A poll bot for groups runs in privacy mode (only receives commands, replies to its messages and mentions). There could be two ways to create a new poll: Explain the user how to send a command with parameters (e.g. /newpoll question answer1 answer2). May be appealing for hardcore users but lacks modern day polish. Guide the user through a step-by-step process. 'Please send me your question', 'Cool, now let's add the first answer option', 'Great. Keep adding answer options, then send /done when you're ready'. The last option is definitely more attractive. And if you use ForceReply in your bot's questions, it will receive the user's answers even if it only receives replies, commands and mentions — without any extra work for the user.
 * @param forceReply Shows reply interface to the user, as if they manually selected the bot's message and tapped 'Reply'
 * @param selective Optional. Use this parameter if you want to force reply from specific users only. Targets: 1) users that are @mentioned in the text of the Message object; 2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
 */
@Serializable
data class ForceReply(
    @SerialName("force_reply") val forceReply: Boolean,
    val selective: Boolean? = null
) : ReplyMarkup()

/**
 * This object represents a chat photo.
 * @param smallFileId File identifier of small (160x160) chat photo. This file_id can be used only for photo download and only for as long as the photo is not changed.
 * @param smallFileUniqueId Unique file identifier of small (160x160) chat photo, which is supposed to be the same over time and for different bots. Can't be used to download or reuse the file.
 * @param bigFileId File identifier of big (640x640) chat photo. This file_id can be used only for photo download and only for as long as the photo is not changed.
 * @param bigFileUniqueId Unique file identifier of big (640x640) chat photo, which is supposed to be the same over time and for different bots. Can't be used to download or reuse the file.
 */
@Serializable
data class ChatPhoto(
    @SerialName("small_file_id") val smallFileId: String,
    @SerialName("small_file_unique_id") val smallFileUniqueId: String,
    @SerialName("big_file_id") val bigFileId: String,
    @SerialName("big_file_unique_id") val bigFileUniqueId: String
)

/**
 * This object contains information about one member of a chat.
 * @param user Information about the user
 * @param status The member's status in the chat. Can be “creator”, “administrator”, “member”, “restricted”, “left” or “kicked”
 * @param customTitle Optional. Owner and administrators only. Custom title for this user
 * @param isAnonymous Optional. Owner and administrators only. True, if the user's presence in the chat is hidden
 * @param canBeEdited Optional. Administrators only. True, if the bot is allowed to edit administrator privileges of that user
 * @param canPostMessages Optional. Administrators only. True, if the administrator can post in the channel; channels only
 * @param canEditMessages Optional. Administrators only. True, if the administrator can edit messages of other users and can pin messages; channels only
 * @param canDeleteMessages Optional. Administrators only. True, if the administrator can delete messages of other users
 * @param canRestrictMembers Optional. Administrators only. True, if the administrator can restrict, ban or unban chat members
 * @param canPromoteMembers Optional. Administrators only. True, if the administrator can add new administrators with a subset of their own privileges or demote administrators that he has promoted, directly or indirectly (promoted by administrators that were appointed by the user)
 * @param canChangeInfo Optional. Administrators and restricted only. True, if the user is allowed to change the chat title, photo and other settings
 * @param canInviteUsers Optional. Administrators and restricted only. True, if the user is allowed to invite new users to the chat
 * @param canPinMessages Optional. Administrators and restricted only. True, if the user is allowed to pin messages; groups and supergroups only
 * @param isMember Optional. Restricted only. True, if the user is a member of the chat at the moment of the request
 * @param canSendMessages Optional. Restricted only. True, if the user is allowed to send text messages, contacts, locations and venues
 * @param canSendMediaMessages Optional. Restricted only. True, if the user is allowed to send audios, documents, photos, videos, video notes and voice notes
 * @param canSendPolls Optional. Restricted only. True, if the user is allowed to send polls
 * @param canSendOtherMessages Optional. Restricted only. True, if the user is allowed to send animations, games, stickers and use inline bots
 * @param canAddWebPagePreviews Optional. Restricted only. True, if the user is allowed to add web page previews to their messages
 * @param untilDate Optional. Restricted and kicked only. Date when restrictions will be lifted for this user; unix time
 */
@Serializable
data class ChatMember(
    val user: User,
    val status: String,
    @SerialName("custom_title") val customTitle: String? = null,
    @SerialName("is_anonymous") val isAnonymous: Boolean? = null,
    @SerialName("can_be_edited") val canBeEdited: Boolean? = null,
    @SerialName("can_post_messages") val canPostMessages: Boolean? = null,
    @SerialName("can_edit_messages") val canEditMessages: Boolean? = null,
    @SerialName("can_delete_messages") val canDeleteMessages: Boolean? = null,
    @SerialName("can_restrict_members") val canRestrictMembers: Boolean? = null,
    @SerialName("can_promote_members") val canPromoteMembers: Boolean? = null,
    @SerialName("can_change_info") val canChangeInfo: Boolean? = null,
    @SerialName("can_invite_users") val canInviteUsers: Boolean? = null,
    @SerialName("can_pin_messages") val canPinMessages: Boolean? = null,
    @SerialName("is_member") val isMember: Boolean? = null,
    @SerialName("can_send_messages") val canSendMessages: Boolean? = null,
    @SerialName("can_send_media_messages") val canSendMediaMessages: Boolean? = null,
    @SerialName("can_send_polls") val canSendPolls: Boolean? = null,
    @SerialName("can_send_other_messages") val canSendOtherMessages: Boolean? = null,
    @SerialName("can_add_web_page_previews") val canAddWebPagePreviews: Boolean? = null,
    @SerialName("until_date") val untilDate: Int? = null
)

/**
 * Describes actions that a non-administrator user is allowed to take in a chat.
 * @param canSendMessages Optional. True, if the user is allowed to send text messages, contacts, locations and venues
 * @param canSendMediaMessages Optional. True, if the user is allowed to send audios, documents, photos, videos, video notes and voice notes, implies can_send_messages
 * @param canSendPolls Optional. True, if the user is allowed to send polls, implies can_send_messages
 * @param canSendOtherMessages Optional. True, if the user is allowed to send animations, games, stickers and use inline bots, implies can_send_media_messages
 * @param canAddWebPagePreviews Optional. True, if the user is allowed to add web page previews to their messages, implies can_send_media_messages
 * @param canChangeInfo Optional. True, if the user is allowed to change the chat title, photo and other settings. Ignored in public supergroups
 * @param canInviteUsers Optional. True, if the user is allowed to invite new users to the chat
 * @param canPinMessages Optional. True, if the user is allowed to pin messages. Ignored in public supergroups
 */
@Serializable
data class ChatPermissions(
    @SerialName("can_send_messages") val canSendMessages: Boolean? = null,
    @SerialName("can_send_media_messages") val canSendMediaMessages: Boolean? = null,
    @SerialName("can_send_polls") val canSendPolls: Boolean? = null,
    @SerialName("can_send_other_messages") val canSendOtherMessages: Boolean? = null,
    @SerialName("can_add_web_page_previews") val canAddWebPagePreviews: Boolean? = null,
    @SerialName("can_change_info") val canChangeInfo: Boolean? = null,
    @SerialName("can_invite_users") val canInviteUsers: Boolean? = null,
    @SerialName("can_pin_messages") val canPinMessages: Boolean? = null
)

/**
 * Represents a location to which a chat is connected.
 * @param location The location to which the supergroup is connected. Can't be a live location.
 * @param address Location address; 1-64 characters, as defined by the chat owner
 */
@Serializable
data class ChatLocation(
    val location: Location,
    val address: String
)

/**
 * This object represents a bot command.
 * @param command Text of the command, 1-32 characters. Can contain only lowercase English letters, digits and underscores.
 * @param description Description of the command, 3-256 characters.
 */
@Serializable
data class BotCommand(
    val command: String,
    val description: String
)

/**
 * Contains information about why a request was unsuccessful.
 * @param migrateToChatId Optional. The group has been migrated to a supergroup with the specified identifier. This number may be greater than 32 bits and some programming languages may have difficulty/silent defects in interpreting it. But it is smaller than 52 bits, so a signed 64 bit integer or double-precision float type are safe for storing this identifier.
 * @param retryAfter Optional. In case of exceeding flood control, the number of seconds left to wait before the request can be repeated
 */
@Serializable
data class ResponseParameters(
    @SerialName("migrate_to_chat_id") val migrateToChatId: Int? = null,
    @SerialName("retry_after") val retryAfter: Int? = null
)

/**
 * Represents a photo to be sent.
 * @param media File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to upload a new one using multipart/form-data under <file_attach_name> name. More info on Sending Files »
 * @param caption Optional. Caption of the photo to be sent, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the photo caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 */
@Serializable
@SerialName("photo")
data class InputMediaPhoto(
    val media: String,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList()
) : InputMedia()

/**
 * Represents a video to be sent.
 * @param media File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to upload a new one using multipart/form-data under <file_attach_name> name. More info on Sending Files »
 * @param thumb Optional. Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param caption Optional. Caption of the video to be sent, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the video caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param width Optional. Video width
 * @param height Optional. Video height
 * @param duration Optional. Video duration
 * @param supportsStreaming Optional. Pass True, if the uploaded video is suitable for streaming
 */
@Serializable
@SerialName("video")
data class InputMediaVideo(
    val media: String,
    val thumb: String? = null,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    val width: Int? = null,
    val height: Int? = null,
    val duration: Int? = null,
    @SerialName("supports_streaming") val supportsStreaming: Boolean? = null
) : InputMedia()

/**
 * Represents an animation file (GIF or H.264/MPEG-4 AVC video without sound) to be sent.
 * @param media File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to upload a new one using multipart/form-data under <file_attach_name> name. More info on Sending Files »
 * @param thumb Optional. Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param caption Optional. Caption of the animation to be sent, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the animation caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param width Optional. Animation width
 * @param height Optional. Animation height
 * @param duration Optional. Animation duration
 */
@Serializable
@SerialName("animation")
data class InputMediaAnimation(
    val media: String,
    val thumb: String? = null,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    val width: Int? = null,
    val height: Int? = null,
    val duration: Int? = null
)

/**
 * Represents an audio file to be treated as music to be sent.
 * @param media File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to upload a new one using multipart/form-data under <file_attach_name> name. More info on Sending Files »
 * @param thumb Optional. Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param caption Optional. Caption of the audio to be sent, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the audio caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param duration Optional. Duration of the audio in seconds
 * @param performer Optional. Performer of the audio
 * @param title Optional. Title of the audio
 */
@Serializable
@SerialName("audio")
data class InputMediaAudio(
    val media: String,
    val thumb: String? = null,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    val duration: Int? = null,
    val performer: String? = null,
    val title: String? = null
) : InputMedia()

/**
 * Represents a general file to be sent.
 * @param media File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to upload a new one using multipart/form-data under <file_attach_name> name. More info on Sending Files »
 * @param thumb Optional. Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param caption Optional. Caption of the document to be sent, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the document caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param disableContentTypeDetection Optional. Disables automatic server-side content type detection for files uploaded using multipart/form-data. Always true, if the document is sent as part of an album.
 */
@Serializable
@SerialName("document")
data class InputMediaDocument(
    val media: String,
    val thumb: String? = null,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    @SerialName("disable_content_type_detection") val disableContentTypeDetection: Boolean? = null
) : InputMedia()

/**
 * This object represents a sticker.
 * @param fileId Identifier for this file, which can be used to download or reuse the file
 * @param fileUniqueId Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used to download or reuse the file.
 * @param width Sticker width
 * @param height Sticker height
 * @param isAnimated True, if the sticker is animated
 * @param thumb Optional. Sticker thumbnail in the .WEBP or .JPG format
 * @param emoji Optional. Emoji associated with the sticker
 * @param setName Optional. Name of the sticker set to which the sticker belongs
 * @param maskPosition Optional. For mask stickers, the position where the mask should be placed
 * @param fileSize Optional. File size
 */
@Serializable
data class Sticker(
    @SerialName("file_id") val fileId: String,
    @SerialName("file_unique_id") val fileUniqueId: String,
    val width: Int,
    val height: Int,
    @SerialName("is_animated") val isAnimated: Boolean,
    val thumb: PhotoSize? = null,
    val emoji: String? = null,
    @SerialName("set_name") val setName: String? = null,
    @SerialName("mask_position") val maskPosition: MaskPosition? = null,
    @SerialName("file_size") val fileSize: Int? = null
)

/**
 * This object represents a sticker set.
 * @param name Sticker set name
 * @param title Sticker set title
 * @param isAnimated True, if the sticker set contains animated stickers
 * @param containsMasks True, if the sticker set contains masks
 * @param stickers List of all set stickers
 * @param thumb Optional. Sticker set thumbnail in the .WEBP or .TGS format
 */
@Serializable
data class StickerSet(
    val name: String,
    val title: String,
    @SerialName("is_animated") val isAnimated: Boolean,
    @SerialName("contains_masks") val containsMasks: Boolean,
    val stickers: List<Sticker> = emptyList(),
    val thumb: PhotoSize? = null
)

/**
 * This object describes the position on faces where a mask should be placed by default.
 * @param point The part of the face relative to which the mask should be placed. One of “forehead”, “eyes”, “mouth”, or “chin”.
 * @param xShift Shift by X-axis measured in widths of the mask scaled to the face size, from left to right. For example, choosing -1.0 will place mask just to the left of the default mask position.
 * @param yShift Shift by Y-axis measured in heights of the mask scaled to the face size, from top to bottom. For example, 1.0 will place the mask just below the default mask position.
 * @param scale Mask scaling coefficient. For example, 2.0 means double size.
 */
@Serializable
data class MaskPosition(
    val point: String,
    @SerialName("x_shift") val xShift: Float,
    @SerialName("y_shift") val yShift: Float,
    val scale: Float
)

/**
 * This object represents an incoming inline query. When the user sends an empty query, your bot could return some default or trending results.
 * @param id Unique identifier for this query
 * @param from Sender
 * @param location Optional. Sender location, only for bots that request user location
 * @param query Text of the query (up to 256 characters)
 * @param offset Offset of the results to be returned, can be controlled by the bot
 */
@Serializable
data class InlineQuery(
    val id: String,
    val from: User,
    val location: Location? = null,
    val query: String,
    val offset: String
)

/**
 * Represents a link to an article or web page.
 * @param id Unique identifier for this result, 1-64 Bytes
 * @param title Title of the result
 * @param inputMessageContent Content of the message to be sent
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param url Optional. URL of the result
 * @param hideUrl Optional. Pass True, if you don't want the URL to be shown in the message
 * @param description Optional. Short description of the result
 * @param thumbUrl Optional. Url of the thumbnail for the result
 * @param thumbWidth Optional. Thumbnail width
 * @param thumbHeight Optional. Thumbnail height
 */
@Serializable
@SerialName("article")
data class InlineQueryResultArticle(
    val id: String,
    val title: String,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent,
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    val url: String? = null,
    @SerialName("hide_url") val hideUrl: Boolean? = null,
    val description: String? = null,
    @SerialName("thumb_url") val thumbUrl: String? = null,
    @SerialName("thumb_width") val thumbWidth: Int? = null,
    @SerialName("thumb_height") val thumbHeight: Int? = null
) : InlineQueryResult()

/**
 * Represents a link to a photo. By default, this photo will be sent by the user with optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the photo.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param photoUrl A valid URL of the photo. Photo must be in jpeg format. Photo size must not exceed 5MB
 * @param thumbUrl URL of the thumbnail for the photo
 * @param photoWidth Optional. Width of the photo
 * @param photoHeight Optional. Height of the photo
 * @param title Optional. Title for the result
 * @param description Optional. Short description of the result
 * @param caption Optional. Caption of the photo to be sent, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the photo caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the photo
 */
@Serializable
@SerialName("photo")
data class InlineQueryResultPhoto(
    val id: String,
    @SerialName("photo_url") val photoUrl: String,
    @SerialName("thumb_url") val thumbUrl: String,
    @SerialName("photo_width") val photoWidth: Int? = null,
    @SerialName("photo_height") val photoHeight: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null
) : InlineQueryResult()

/**
 * Represents a link to an animated GIF file. By default, this animated GIF file will be sent by the user with optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the animation.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param gifUrl A valid URL for the GIF file. File size must not exceed 1MB
 * @param gifWidth Optional. Width of the GIF
 * @param gifHeight Optional. Height of the GIF
 * @param gifDuration Optional. Duration of the GIF
 * @param thumbUrl URL of the static (JPEG or GIF) or animated (MPEG4) thumbnail for the result
 * @param thumbMimeType Optional. MIME type of the thumbnail, must be one of “image/jpeg”, “image/gif”, or “video/mp4”. Defaults to “image/jpeg”
 * @param title Optional. Title for the result
 * @param caption Optional. Caption of the GIF file to be sent, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the GIF animation
 */
@Serializable
@SerialName("gif")
data class InlineQueryResultGif(
    val id: String,
    @SerialName("gif_url") val gifUrl: String,
    @SerialName("gif_width") val gifWidth: Int? = null,
    @SerialName("gif_height") val gifHeight: Int? = null,
    @SerialName("gif_duration") val gifDuration: Int? = null,
    @SerialName("thumb_url") val thumbUrl: String,
    @SerialName("thumb_mime_type") val thumbMimeType: String? = null,
    val title: String? = null,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null
) : InlineQueryResult()

/**
 * Represents a link to a video animation (H.264/MPEG-4 AVC video without sound). By default, this animated MPEG-4 file will be sent by the user with optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the animation.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param mpeg4Url A valid URL for the MP4 file. File size must not exceed 1MB
 * @param mpeg4Width Optional. Video width
 * @param mpeg4Height Optional. Video height
 * @param mpeg4Duration Optional. Video duration
 * @param thumbUrl URL of the static (JPEG or GIF) or animated (MPEG4) thumbnail for the result
 * @param thumbMimeType Optional. MIME type of the thumbnail, must be one of “image/jpeg”, “image/gif”, or “video/mp4”. Defaults to “image/jpeg”
 * @param title Optional. Title for the result
 * @param caption Optional. Caption of the MPEG-4 file to be sent, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the video animation
 */
@Serializable
@SerialName("mpeg4_gif")
data class InlineQueryResultMpeg4Gif(
    val id: String,
    @SerialName("mpeg4_url") val mpeg4Url: String,
    @SerialName("mpeg4_width") val mpeg4Width: Int? = null,
    @SerialName("mpeg4_height") val mpeg4Height: Int? = null,
    @SerialName("mpeg4_duration") val mpeg4Duration: Int? = null,
    @SerialName("thumb_url") val thumbUrl: String,
    @SerialName("thumb_mime_type") val thumbMimeType: String? = null,
    val title: String? = null,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null
) : InlineQueryResult()

/**
 * Represents a link to a page containing an embedded video player or a video file. By default, this video file will be sent by the user with an optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the video.
 * If an InlineQueryResultVideo message contains an embedded video (e.g., YouTube), you must replace its content using input_message_content.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param videoUrl A valid URL for the embedded video player or video file
 * @param mimeType Mime type of the content of video url, “text/html” or “video/mp4”
 * @param thumbUrl URL of the thumbnail (jpeg only) for the video
 * @param title Title for the result
 * @param caption Optional. Caption of the video to be sent, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the video caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param videoWidth Optional. Video width
 * @param videoHeight Optional. Video height
 * @param videoDuration Optional. Video duration in seconds
 * @param description Optional. Short description of the result
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the video. This field is required if InlineQueryResultVideo is used to send an HTML-page as a result (e.g., a YouTube video).
 */
@Serializable
@SerialName("video")
data class InlineQueryResultVideo(
    val id: String,
    @SerialName("video_url") val videoUrl: String,
    @SerialName("mime_type") val mimeType: String,
    @SerialName("thumb_url") val thumbUrl: String,
    val title: String,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    @SerialName("video_width") val videoWidth: Int? = null,
    @SerialName("video_height") val videoHeight: Int? = null,
    @SerialName("video_duration") val videoDuration: Int? = null,
    val description: String? = null,
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null
) : InlineQueryResult()

/**
 * Represents a link to an MP3 audio file. By default, this audio file will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the audio.
 * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param audioUrl A valid URL for the audio file
 * @param title Title
 * @param caption Optional. Caption, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the audio caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param performer Optional. Performer
 * @param audioDuration Optional. Audio duration in seconds
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the audio
 */
@Serializable
@SerialName("audio")
data class InlineQueryResultAudio(
    val id: String,
    @SerialName("audio_url") val audioUrl: String,
    val title: String,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    val performer: String? = null,
    @SerialName("audio_duration") val audioDuration: Int? = null,
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null
) : InlineQueryResult()

/**
 * Represents a link to a voice recording in an .OGG container encoded with OPUS. By default, this voice recording will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the the voice message.
 * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param voiceUrl A valid URL for the voice recording
 * @param title Recording title
 * @param caption Optional. Caption, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the voice message caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param voiceDuration Optional. Recording duration in seconds
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the voice recording
 */
@Serializable
@SerialName("voice")
data class InlineQueryResultVoice(
    val id: String,
    @SerialName("voice_url") val voiceUrl: String,
    val title: String,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    @SerialName("voice_duration") val voiceDuration: Int? = null,
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null
) : InlineQueryResult()

/**
 * Represents a link to a file. By default, this file will be sent by the user with an optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the file. Currently, only .PDF and .ZIP files can be sent using this method.
 * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param title Title for the result
 * @param caption Optional. Caption of the document to be sent, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the document caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param documentUrl A valid URL for the file
 * @param mimeType Mime type of the content of the file, either “application/pdf” or “application/zip”
 * @param description Optional. Short description of the result
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the file
 * @param thumbUrl Optional. URL of the thumbnail (jpeg only) for the file
 * @param thumbWidth Optional. Thumbnail width
 * @param thumbHeight Optional. Thumbnail height
 */
@Serializable
@SerialName("document")
data class InlineQueryResultDocument(
    val id: String,
    val title: String,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    @SerialName("document_url") val documentUrl: String,
    @SerialName("mime_type") val mimeType: String,
    val description: String? = null,
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null,
    @SerialName("thumb_url") val thumbUrl: String? = null,
    @SerialName("thumb_width") val thumbWidth: Int? = null,
    @SerialName("thumb_height") val thumbHeight: Int? = null
) : InlineQueryResult()

/**
 * Represents a location on a map. By default, the location will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the location.
 * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
 * @param id Unique identifier for this result, 1-64 Bytes
 * @param latitude Location latitude in degrees
 * @param longitude Location longitude in degrees
 * @param title Location title
 * @param horizontalAccuracy Optional. The radius of uncertainty for the location, measured in meters; 0-1500
 * @param livePeriod Optional. Period in seconds for which the location can be updated, should be between 60 and 86400.
 * @param heading Optional. For live locations, a direction in which the user is moving, in degrees. Must be between 1 and 360 if specified.
 * @param proximityAlertRadius Optional. For live locations, a maximum distance for proximity alerts about approaching another chat member, in meters. Must be between 1 and 100000 if specified.
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the location
 * @param thumbUrl Optional. Url of the thumbnail for the result
 * @param thumbWidth Optional. Thumbnail width
 * @param thumbHeight Optional. Thumbnail height
 */
@Serializable
@SerialName("location")
data class InlineQueryResultLocation(
    val id: String,
    val latitude: Float,
    val longitude: Float,
    val title: String,
    @SerialName("horizontal_accuracy") val horizontalAccuracy: Float? = null,
    @SerialName("live_period") val livePeriod: Int? = null,
    val heading: Int? = null,
    @SerialName("proximity_alert_radius") val proximityAlertRadius: Int? = null,
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null,
    @SerialName("thumb_url") val thumbUrl: String? = null,
    @SerialName("thumb_width") val thumbWidth: Int? = null,
    @SerialName("thumb_height") val thumbHeight: Int? = null
) : InlineQueryResult()

/**
 * Represents a venue. By default, the venue will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the venue.
 * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
 * @param id Unique identifier for this result, 1-64 Bytes
 * @param latitude Latitude of the venue location in degrees
 * @param longitude Longitude of the venue location in degrees
 * @param title Title of the venue
 * @param address Address of the venue
 * @param foursquareId Optional. Foursquare identifier of the venue if known
 * @param foursquareType Optional. Foursquare type of the venue, if known. (For example, “arts_entertainment/default”, “arts_entertainment/aquarium” or “food/icecream”.)
 * @param googlePlaceId Optional. Google Places identifier of the venue
 * @param googlePlaceType Optional. Google Places type of the venue. (See supported types.)
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the venue
 * @param thumbUrl Optional. Url of the thumbnail for the result
 * @param thumbWidth Optional. Thumbnail width
 * @param thumbHeight Optional. Thumbnail height
 */
@Serializable
@SerialName("venue")
data class InlineQueryResultVenue(
    val id: String,
    val latitude: Float,
    val longitude: Float,
    val title: String,
    val address: String,
    @SerialName("foursquare_id") val foursquareId: String? = null,
    @SerialName("foursquare_type") val foursquareType: String? = null,
    @SerialName("google_place_id") val googlePlaceId: String? = null,
    @SerialName("google_place_type") val googlePlaceType: String? = null,
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null,
    @SerialName("thumb_url") val thumbUrl: String? = null,
    @SerialName("thumb_width") val thumbWidth: Int? = null,
    @SerialName("thumb_height") val thumbHeight: Int? = null
) : InlineQueryResult()

/**
 * Represents a contact with a phone number. By default, this contact will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the contact.
 * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
 * @param id Unique identifier for this result, 1-64 Bytes
 * @param phoneNumber Contact's phone number
 * @param firstName Contact's first name
 * @param lastName Optional. Contact's last name
 * @param vcard Optional. Additional data about the contact in the form of a vCard, 0-2048 bytes
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the contact
 * @param thumbUrl Optional. Url of the thumbnail for the result
 * @param thumbWidth Optional. Thumbnail width
 * @param thumbHeight Optional. Thumbnail height
 */
@Serializable
@SerialName("contact")
data class InlineQueryResultContact(
    val id: String,
    @SerialName("phone_number") val phoneNumber: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String? = null,
    val vcard: String? = null,
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null,
    @SerialName("thumb_url") val thumbUrl: String? = null,
    @SerialName("thumb_width") val thumbWidth: Int? = null,
    @SerialName("thumb_height") val thumbHeight: Int? = null
) : InlineQueryResult()

/**
 * Represents a Game.
 * Note: This will only work in Telegram versions released after October 1, 2016. Older clients will not display any inline results if a game result is among them.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param gameShortName Short name of the game
 * @param replyMarkup Optional. Inline keyboard attached to the message
 */
@Serializable
@SerialName("game")
data class InlineQueryResultGame(
    val id: String,
    @SerialName("game_short_name") val gameShortName: String,
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null
) : InlineQueryResult()

/**
 * Represents a link to a photo stored on the Telegram servers. By default, this photo will be sent by the user with an optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the photo.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param photoFileId A valid file identifier of the photo
 * @param title Optional. Title for the result
 * @param description Optional. Short description of the result
 * @param caption Optional. Caption of the photo to be sent, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the photo caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the photo
 */
@Serializable
@SerialName("photo")
data class InlineQueryResultCachedPhoto(
    val id: String,
    @SerialName("photo_file_id") val photoFileId: String,
    val title: String? = null,
    val description: String? = null,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null
) : InlineQueryResult()

/**
 * Represents a link to an animated GIF file stored on the Telegram servers. By default, this animated GIF file will be sent by the user with an optional caption. Alternatively, you can use input_message_content to send a message with specified content instead of the animation.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param gifFileId A valid file identifier for the GIF file
 * @param title Optional. Title for the result
 * @param caption Optional. Caption of the GIF file to be sent, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the GIF animation
 */
@Serializable
@SerialName("gif")
data class InlineQueryResultCachedGif(
    val id: String,
    @SerialName("gif_file_id") val gifFileId: String,
    val title: String? = null,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null
) : InlineQueryResult()

/**
 * Represents a link to a video animation (H.264/MPEG-4 AVC video without sound) stored on the Telegram servers. By default, this animated MPEG-4 file will be sent by the user with an optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the animation.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param mpeg4FileId A valid file identifier for the MP4 file
 * @param title Optional. Title for the result
 * @param caption Optional. Caption of the MPEG-4 file to be sent, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the video animation
 */
@Serializable
@SerialName("mpeg4_gif")
data class InlineQueryResultCachedMpeg4Gif(
    val id: String,
    @SerialName("mpeg4_file_id") val mpeg4FileId: String,
    val title: String? = null,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null
) : InlineQueryResult()

/**
 * Represents a link to a sticker stored on the Telegram servers. By default, this sticker will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the sticker.
 * Note: This will only work in Telegram versions released after 9 April, 2016 for static stickers and after 06 July, 2019 for animated stickers. Older clients will ignore them.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param stickerFileId A valid file identifier of the sticker
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the sticker
 */
@Serializable
@SerialName("sticker")
data class InlineQueryResultCachedSticker(
    val id: String,
    @SerialName("sticker_file_id") val stickerFileId: String,
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null
) : InlineQueryResult()

/**
 * Represents a link to a file stored on the Telegram servers. By default, this file will be sent by the user with an optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the file.
 * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param title Title for the result
 * @param documentFileId A valid file identifier for the file
 * @param description Optional. Short description of the result
 * @param caption Optional. Caption of the document to be sent, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the document caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the file
 */
@Serializable
@SerialName("document")
data class InlineQueryResultCachedDocument(
    val id: String,
    val title: String,
    @SerialName("document_file_id") val documentFileId: String,
    val description: String? = null,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null
) : InlineQueryResult()

/**
 * Represents a link to a video file stored on the Telegram servers. By default, this video file will be sent by the user with an optional caption. Alternatively, you can use input_message_content to send a message with the specified content instead of the video.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param videoFileId A valid file identifier for the video file
 * @param title Title for the result
 * @param description Optional. Short description of the result
 * @param caption Optional. Caption of the video to be sent, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the video caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the video
 */
@Serializable
@SerialName("video")
data class InlineQueryResultCachedVideo(
    val id: String,
    @SerialName("video_file_id") val videoFileId: String,
    val title: String,
    val description: String? = null,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null
) : InlineQueryResult()

/**
 * Represents a link to a voice message stored on the Telegram servers. By default, this voice message will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the voice message.
 * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param voiceFileId A valid file identifier for the voice message
 * @param title Voice message title
 * @param caption Optional. Caption, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the voice message caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the voice message
 */
@Serializable
@SerialName("voice")
data class InlineQueryResultCachedVoice(
    val id: String,
    @SerialName("voice_file_id") val voiceFileId: String,
    val title: String,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null
) : InlineQueryResult()

/**
 * Represents a link to an MP3 audio file stored on the Telegram servers. By default, this audio file will be sent by the user. Alternatively, you can use input_message_content to send a message with the specified content instead of the audio.
 * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
 * @param id Unique identifier for this result, 1-64 bytes
 * @param audioFileId A valid file identifier for the audio file
 * @param caption Optional. Caption, 0-1024 characters after entities parsing
 * @param parseMode Optional. Mode for parsing entities in the audio caption. See formatting options for more details.
 * @param captionEntities Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param replyMarkup Optional. Inline keyboard attached to the message
 * @param inputMessageContent Optional. Content of the message to be sent instead of the audio
 */
@Serializable
@SerialName("audio")
data class InlineQueryResultCachedAudio(
    val id: String,
    @SerialName("audio_file_id") val audioFileId: String,
    val caption: String? = null,
    @SerialName("parse_mode") val parseMode: String? = null,
    @SerialName("caption_entities") val captionEntities: List<MessageEntity> = emptyList(),
    @SerialName("reply_markup") val replyMarkup: InlineKeyboardMarkup? = null,
    @SerialName("input_message_content") val inputMessageContent: InputMessageContent? = null
) : InlineQueryResult()

/**
 * Represents the content of a text message to be sent as the result of an inline query.
 * @param messageText Text of the message to be sent, 1-4096 characters
 * @param parseMode Optional. Mode for parsing entities in the message text. See formatting options for more details.
 * @param entities Optional. List of special entities that appear in message text, which can be specified instead of parse_mode
 * @param disableWebPagePreview Optional. Disables link previews for links in the sent message
 */
@Serializable
data class InputTextMessageContent(
    @SerialName("message_text") val messageText: String,
    @SerialName("parse_mode") val parseMode: String? = null,
    val entities: List<MessageEntity> = emptyList(),
    @SerialName("disable_web_page_preview") val disableWebPagePreview: Boolean? = null
) : InputMessageContent()

/**
 * Represents the content of a location message to be sent as the result of an inline query.
 * @param latitude Latitude of the location in degrees
 * @param longitude Longitude of the location in degrees
 * @param horizontalAccuracy Optional. The radius of uncertainty for the location, measured in meters; 0-1500
 * @param livePeriod Optional. Period in seconds for which the location can be updated, should be between 60 and 86400.
 * @param heading Optional. For live locations, a direction in which the user is moving, in degrees. Must be between 1 and 360 if specified.
 * @param proximityAlertRadius Optional. For live locations, a maximum distance for proximity alerts about approaching another chat member, in meters. Must be between 1 and 100000 if specified.
 */
@Serializable
data class InputLocationMessageContent(
    val latitude: Float,
    val longitude: Float,
    @SerialName("horizontal_accuracy") val horizontalAccuracy: Float? = null,
    @SerialName("live_period") val livePeriod: Int? = null,
    val heading: Int? = null,
    @SerialName("proximity_alert_radius") val proximityAlertRadius: Int? = null
) : InputMessageContent()

/**
 * Represents the content of a venue message to be sent as the result of an inline query.
 * @param latitude Latitude of the venue in degrees
 * @param longitude Longitude of the venue in degrees
 * @param title Name of the venue
 * @param address Address of the venue
 * @param foursquareId Optional. Foursquare identifier of the venue, if known
 * @param foursquareType Optional. Foursquare type of the venue, if known. (For example, “arts_entertainment/default”, “arts_entertainment/aquarium” or “food/icecream”.)
 * @param googlePlaceId Optional. Google Places identifier of the venue
 * @param googlePlaceType Optional. Google Places type of the venue. (See supported types.)
 */
@Serializable
data class InputVenueMessageContent(
    val latitude: Float,
    val longitude: Float,
    val title: String,
    val address: String,
    @SerialName("foursquare_id") val foursquareId: String? = null,
    @SerialName("foursquare_type") val foursquareType: String? = null,
    @SerialName("google_place_id") val googlePlaceId: String? = null,
    @SerialName("google_place_type") val googlePlaceType: String? = null
) : InputMessageContent()

/**
 * Represents the content of a contact message to be sent as the result of an inline query.
 * @param phoneNumber Contact's phone number
 * @param firstName Contact's first name
 * @param lastName Optional. Contact's last name
 * @param vcard Optional. Additional data about the contact in the form of a vCard, 0-2048 bytes
 */
@Serializable
data class InputContactMessageContent(
    @SerialName("phone_number") val phoneNumber: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String? = null,
    val vcard: String? = null
) : InputMessageContent()

/**
 * Represents a result of an inline query that was chosen by the user and sent to their chat partner.
 * Note: It is necessary to enable inline feedback via @Botfather in order to receive these objects in updates.
 * Your bot can accept payments from Telegram users. Please see the introduction to payments for more details on the process and how to set up payments for your bot. Please note that users will need Telegram v.4.0 or higher to use payments (released on May 18, 2017).
 * @param resultId The unique identifier for the result that was chosen
 * @param from The user that chose the result
 * @param location Optional. Sender location, only for bots that require user location
 * @param inlineMessageId Optional. Identifier of the sent inline message. Available only if there is an inline keyboard attached to the message. Will be also received in callback queries and can be used to edit the message.
 * @param query The query that was used to obtain the result
 */
@Serializable
data class ChosenInlineResult(
    @SerialName("result_id") val resultId: String,
    val from: User,
    val location: Location? = null,
    @SerialName("inline_message_id") val inlineMessageId: String? = null,
    val query: String
)

/**
 * This object represents a portion of the price for goods or services.
 * @param label Portion label
 * @param amount Price of the product in the smallest units of the currency (integer, not float/double). For example, for a price of US$ 1.45 pass amount = 145. See the exp parameter in currencies.json, it shows the number of digits past the decimal point for each currency (2 for the majority of currencies).
 */
@Serializable
data class LabeledPrice(
    val label: String,
    val amount: Int
)

/**
 * This object contains basic information about an invoice.
 * @param title Product name
 * @param description Product description
 * @param startParameter Unique bot deep-linking parameter that can be used to generate this invoice
 * @param currency Three-letter ISO 4217 currency code
 * @param totalAmount Total price in the smallest units of the currency (integer, not float/double). For example, for a price of US$ 1.45 pass amount = 145. See the exp parameter in currencies.json, it shows the number of digits past the decimal point for each currency (2 for the majority of currencies).
 */
@Serializable
data class Invoice(
    val title: String,
    val description: String,
    @SerialName("start_parameter") val startParameter: String,
    val currency: String,
    @SerialName("total_amount") val totalAmount: Int
)

/**
 * This object represents a shipping address.
 * @param countryCode ISO 3166-1 alpha-2 country code
 * @param state State, if applicable
 * @param city City
 * @param streetLine1 First line for the address
 * @param streetLine2 Second line for the address
 * @param postCode Address post code
 */
@Serializable
data class ShippingAddress(
    @SerialName("country_code") val countryCode: String,
    val state: String,
    val city: String,
    @SerialName("street_line1") val streetLine1: String,
    @SerialName("street_line2") val streetLine2: String,
    @SerialName("post_code") val postCode: String
)

/**
 * This object represents information about an order.
 * @param name Optional. User name
 * @param phoneNumber Optional. User's phone number
 * @param email Optional. User email
 * @param shippingAddress Optional. User shipping address
 */
@Serializable
data class OrderInfo(
    val name: String? = null,
    @SerialName("phone_number") val phoneNumber: String? = null,
    val email: String? = null,
    @SerialName("shipping_address") val shippingAddress: ShippingAddress? = null
)

/**
 * This object represents one shipping option.
 * @param id Shipping option identifier
 * @param title Option title
 * @param prices List of price portions
 */
@Serializable
data class ShippingOption(
    val id: String,
    val title: String,
    val prices: List<LabeledPrice> = emptyList()
)

/**
 * This object contains basic information about a successful payment.
 * @param currency Three-letter ISO 4217 currency code
 * @param totalAmount Total price in the smallest units of the currency (integer, not float/double). For example, for a price of US$ 1.45 pass amount = 145. See the exp parameter in currencies.json, it shows the number of digits past the decimal point for each currency (2 for the majority of currencies).
 * @param invoicePayload Bot specified invoice payload
 * @param shippingOptionId Optional. Identifier of the shipping option chosen by the user
 * @param orderInfo Optional. Order info provided by the user
 * @param telegramPaymentChargeId Telegram payment identifier
 * @param providerPaymentChargeId Provider payment identifier
 */
@Serializable
data class SuccessfulPayment(
    val currency: String,
    @SerialName("total_amount") val totalAmount: Int,
    @SerialName("invoice_payload") val invoicePayload: String,
    @SerialName("shipping_option_id") val shippingOptionId: String? = null,
    @SerialName("order_info") val orderInfo: OrderInfo? = null,
    @SerialName("telegram_payment_charge_id") val telegramPaymentChargeId: String,
    @SerialName("provider_payment_charge_id") val providerPaymentChargeId: String
)

/**
 * This object contains information about an incoming shipping query.
 * @param id Unique query identifier
 * @param from User who sent the query
 * @param invoicePayload Bot specified invoice payload
 * @param shippingAddress User specified shipping address
 */
@Serializable
data class ShippingQuery(
    val id: String,
    val from: User,
    @SerialName("invoice_payload") val invoicePayload: String,
    @SerialName("shipping_address") val shippingAddress: ShippingAddress
)

/**
 * This object contains information about an incoming pre-checkout query.
 * Telegram Passport is a unified authorization method for services that require personal identification. Users can upload their documents once, then instantly share their data with services that require real-world ID (finance, ICOs, etc.). Please see the manual for details.
 * @param id Unique query identifier
 * @param from User who sent the query
 * @param currency Three-letter ISO 4217 currency code
 * @param totalAmount Total price in the smallest units of the currency (integer, not float/double). For example, for a price of US$ 1.45 pass amount = 145. See the exp parameter in currencies.json, it shows the number of digits past the decimal point for each currency (2 for the majority of currencies).
 * @param invoicePayload Bot specified invoice payload
 * @param shippingOptionId Optional. Identifier of the shipping option chosen by the user
 * @param orderInfo Optional. Order info provided by the user
 */
@Serializable
data class PreCheckoutQuery(
    val id: String,
    val from: User,
    val currency: String,
    @SerialName("total_amount") val totalAmount: Int,
    @SerialName("invoice_payload") val invoicePayload: String,
    @SerialName("shipping_option_id") val shippingOptionId: String? = null,
    @SerialName("order_info") val orderInfo: OrderInfo? = null
)

/**
 * Contains information about Telegram Passport data shared with the bot by the user.
 * @param data Array with information about documents and other Telegram Passport elements that was shared with the bot
 * @param credentials Encrypted credentials required to decrypt the data
 */
@Serializable
data class PassportData(
    val data: List<EncryptedPassportElement> = emptyList(),
    val credentials: EncryptedCredentials
)

/**
 * This object represents a file uploaded to Telegram Passport. Currently all Telegram Passport files are in JPEG format when decrypted and don't exceed 10MB.
 * @param fileId Identifier for this file, which can be used to download or reuse the file
 * @param fileUniqueId Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used to download or reuse the file.
 * @param fileSize File size
 * @param fileDate Unix time when the file was uploaded
 */
@Serializable
data class PassportFile(
    @SerialName("file_id") val fileId: String,
    @SerialName("file_unique_id") val fileUniqueId: String,
    @SerialName("file_size") val fileSize: Int,
    @SerialName("file_date") val fileDate: Int
)

/**
 * Contains information about documents or other Telegram Passport elements shared with the bot by the user.
 * @param type Element type. One of “personal_details”, “passport”, “driver_license”, “identity_card”, “internal_passport”, “address”, “utility_bill”, “bank_statement”, “rental_agreement”, “passport_registration”, “temporary_registration”, “phone_number”, “email”.
 * @param data Optional. Base64-encoded encrypted Telegram Passport element data provided by the user, available for “personal_details”, “passport”, “driver_license”, “identity_card”, “internal_passport” and “address” types. Can be decrypted and verified using the accompanying EncryptedCredentials.
 * @param phoneNumber Optional. User's verified phone number, available only for “phone_number” type
 * @param email Optional. User's verified email address, available only for “email” type
 * @param files Optional. Array of encrypted files with documents provided by the user, available for “utility_bill”, “bank_statement”, “rental_agreement”, “passport_registration” and “temporary_registration” types. Files can be decrypted and verified using the accompanying EncryptedCredentials.
 * @param frontSide Optional. Encrypted file with the front side of the document, provided by the user. Available for “passport”, “driver_license”, “identity_card” and “internal_passport”. The file can be decrypted and verified using the accompanying EncryptedCredentials.
 * @param reverseSide Optional. Encrypted file with the reverse side of the document, provided by the user. Available for “driver_license” and “identity_card”. The file can be decrypted and verified using the accompanying EncryptedCredentials.
 * @param selfie Optional. Encrypted file with the selfie of the user holding a document, provided by the user; available for “passport”, “driver_license”, “identity_card” and “internal_passport”. The file can be decrypted and verified using the accompanying EncryptedCredentials.
 * @param translation Optional. Array of encrypted files with translated versions of documents provided by the user. Available if requested for “passport”, “driver_license”, “identity_card”, “internal_passport”, “utility_bill”, “bank_statement”, “rental_agreement”, “passport_registration” and “temporary_registration” types. Files can be decrypted and verified using the accompanying EncryptedCredentials.
 * @param hash Base64-encoded element hash for using in PassportElementErrorUnspecified
 */
@Serializable
data class EncryptedPassportElement(
    val type: String,
    val data: String? = null,
    @SerialName("phone_number") val phoneNumber: String? = null,
    val email: String? = null,
    val files: List<PassportFile> = emptyList(),
    @SerialName("front_side") val frontSide: PassportFile? = null,
    @SerialName("reverse_side") val reverseSide: PassportFile? = null,
    val selfie: PassportFile? = null,
    val translation: List<PassportFile> = emptyList(),
    val hash: String
)

/**
 * Contains data required for decrypting and authenticating EncryptedPassportElement. See the Telegram Passport Documentation for a complete description of the data decryption and authentication processes.
 * @param data Base64-encoded encrypted JSON-serialized data with unique user's payload, data hashes and secrets required for EncryptedPassportElement decryption and authentication
 * @param hash Base64-encoded data hash for data authentication
 * @param secret Base64-encoded secret, encrypted with the bot's public RSA key, required for data decryption
 */
@Serializable
data class EncryptedCredentials(
    val data: String,
    val hash: String,
    val secret: String
)

/**
 * This object represents a game. Use BotFather to create and edit games, their short names will act as unique identifiers.
 * @param title Title of the game
 * @param description Description of the game
 * @param photo Photo that will be displayed in the game message in chats.
 * @param text Optional. Brief description of the game or high scores included in the game message. Can be automatically edited to include current high scores for the game when the bot calls setGameScore, or manually edited using editMessageText. 0-4096 characters.
 * @param textEntities Optional. Special entities that appear in text, such as usernames, URLs, bot commands, etc.
 * @param animation Optional. Animation that will be displayed in the game message in chats. Upload via BotFather
 */
@Serializable
data class Game(
    val title: String,
    val description: String,
    val photo: List<PhotoSize> = emptyList(),
    val text: String? = null,
    @SerialName("text_entities") val textEntities: List<MessageEntity> = emptyList(),
    val animation: Animation? = null
)

/**
 * This object represents one row of the high scores table for a game.
 * And that's about all we've got for now. If you've got any questions, please check out our Bot FAQ »
 * @param position Position in high score table for the game
 * @param user User
 * @param score Score
 */
@Serializable
data class GameHighScore(
    val position: Int,
    val user: User,
    val score: Int
)


/**
 * This object represents the content of a message to be sent as a result of an inline query. Telegram clients currently support the following 4 types:
 * - [InputTextMessageContent]
 * - [InputLocationMessageContent]
 * - [InputVenueMessageContent]
 * - [InputContactMessageContent]
 */ 
@Serializable(with = InputMessageContentSerializer::class)
sealed class InputMessageContent


/**
 * A placeholder, currently holds no information. Use BotFather to set up your game.
 */ 
typealias CallbackGame = String


/**
 * 
 */ 
@Serializable(with = ReplyMarkupSerializer::class)
sealed class ReplyMarkup


/**
 * This object represents the content of a media message to be sent. It should be one of:
 * [InputMediaAnimation]
 * [InputMediaDocument]
 * [InputMediaAudio]
 * [InputMediaPhoto]
 * [InputMediaVideo]
 */ 
@Serializable
sealed class InputMedia


/**
 * This object represents one result of an inline query. Telegram clients currently support results of the following 20 types:
 * [InlineQueryResultCachedAudio]
 * [InlineQueryResultCachedDocument]
 * [InlineQueryResultCachedGif]
 * [InlineQueryResultCachedMpeg4Gif]
 * [InlineQueryResultCachedPhoto]
 * [InlineQueryResultCachedSticker]
 * [InlineQueryResultCachedVideo]
 * [InlineQueryResultCachedVoice]
 * [InlineQueryResultArticle]
 * [InlineQueryResultAudio]
 * [InlineQueryResultContact]
 * [InlineQueryResultGame]
 * [InlineQueryResultDocument]
 * [InlineQueryResultGif]
 * [InlineQueryResultLocation]
 * [InlineQueryResultMpeg4Gif]
 * [InlineQueryResultPhoto]
 * [InlineQueryResultVenue]
 * [InlineQueryResultVideo]
 * [InlineQueryResultVoice]
 */ 
@Serializable
sealed class InlineQueryResult


