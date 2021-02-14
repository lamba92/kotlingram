package com.github.lamba92.kotlingram.builder

@TelegramBotsDSL
class MessageHandlersBuilder {

    private var inlineQueriesHandler: InlineQueryHandler = {}
    private var sendMessageHandler: SendMessageHandler = {}

    @TelegramBotsDSL
    fun inlineQueries(handler: InlineQueryHandler) {
        inlineQueriesHandler = handler
    }

    @TelegramBotsDSL
    fun messages(handler: SendMessageHandler) {
        sendMessageHandler = handler
    }

    internal fun build() =
        MessageHandlersContainer(inlineQueriesHandler, sendMessageHandler)

}
