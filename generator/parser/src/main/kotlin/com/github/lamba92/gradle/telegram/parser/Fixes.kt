package com.github.lamba92.gradle.telegram.parser

fun generateInputMessageContentInterfaceFix() = buildString {
    appendLine("/**")
    appendLine(" * This object represents the content of a message to be sent as a result of an inline query. " +
        "Telegram clients currently support the following 4 types:")
    appendLine(" * - [InputTextMessageContent]")
    appendLine(" * - [InputLocationMessageContent]")
    appendLine(" * - [InputVenueMessageContent]")
    appendLine(" * - [InputContactMessageContent]")
    appendLine(" */ ")
    appendLine("@Serializable(with = InputMessageContentSerializer::class)")
    appendLine("sealed class InputMessageContent")
}

fun generateCallbackGameFix() = buildString {
    appendLine("/**")
    appendLine(" * A placeholder, currently holds no information. Use BotFather to set up your game.")
    appendLine(" */ ")
    appendLine("typealias CallbackGame = String")
}

fun generateReplyMarkupFix() = buildString {
    appendLine("/**")
    appendLine(" * ")
    appendLine(" */ ")
    appendLine("@Serializable(with = ReplyMarkupSerializer::class)")
    appendLine("sealed class ReplyMarkup")
}

fun generateMediaFix() = buildString {
    appendLine("/**")
    appendLine(" * This object represents the content of a media message to be sent. It should be one of:")
    appendLine(" * - [InputMediaAnimation]")
    appendLine(" * - [InputMediaDocument]")
    appendLine(" * - [InputMediaAudio]")
    appendLine(" * - [InputMediaPhoto]")
    appendLine(" * - [InputMediaVideo]")
    appendLine(" */ ")
    appendLine("@Serializable")
    appendLine("sealed class InputMedia")
}

fun generateInlineQueryResultFix() = buildString {
    appendLine("/**")
    appendLine(" * This object represents one result of an inline query. Telegram clients currently support results of the following 20 types:")
    appendLine(" * - [InlineQueryResultCachedAudio]")
    appendLine(" * - [InlineQueryResultCachedDocument]")
    appendLine(" * - [InlineQueryResultCachedGif]")
    appendLine(" * - [InlineQueryResultCachedMpeg4Gif]")
    appendLine(" * - [InlineQueryResultCachedPhoto]")
    appendLine(" * - [InlineQueryResultCachedSticker]")
    appendLine(" * - [InlineQueryResultCachedVideo]")
    appendLine(" * - [InlineQueryResultCachedVoice]")
    appendLine(" * - [InlineQueryResultArticle]")
    appendLine(" * - [InlineQueryResultAudio]")
    appendLine(" * - [InlineQueryResultContact]")
    appendLine(" * - [InlineQueryResultGame]")
    appendLine(" * - [InlineQueryResultDocument]")
    appendLine(" * - [InlineQueryResultGif]")
    appendLine(" * - [InlineQueryResultLocation]")
    appendLine(" * - [InlineQueryResultMpeg4Gif]")
    appendLine(" * - [InlineQueryResultPhoto]")
    appendLine(" * - [InlineQueryResultVenue]")
    appendLine(" * - [InlineQueryResultVideo]")
    appendLine(" * - [InlineQueryResultVoice]")
    appendLine(" */ ")
    appendLine("@Serializable")
    appendLine("sealed class InlineQueryResult")
}

fun generatePassportElementErrorFix() = buildString {
    appendLine("/**")
    appendLine(" * This object represents an error in the Telegram Passport element which was submitted that should be resolved by the user. It should be one of::")
    appendLine(" * - [PassportElementErrorDataField]")
    appendLine(" * - [PassportElementErrorFrontSide]")
    appendLine(" * - [PassportElementErrorReverseSide]")
    appendLine(" * - [PassportElementErrorSelfie]")
    appendLine(" * - [PassportElementErrorFile]")
    appendLine(" * - [PassportElementErrorFiles]")
    appendLine(" * - [PassportElementErrorTranslationFile]")
    appendLine(" * - [PassportElementErrorTranslationFiles]")
    appendLine(" * - [PassportElementErrorUnspecified]")
    appendLine(" */ ")
    appendLine("@Serializable")
    appendLine("sealed class PassportElementError")
}
