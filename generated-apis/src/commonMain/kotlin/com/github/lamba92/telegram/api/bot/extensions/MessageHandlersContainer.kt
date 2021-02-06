package com.github.lamba92.telegram.api.bot.extensions

data class MessageHandlersContainer(
    val inlineQueriesHandler: InlineQueryHandler,
    val sendMessageHandler: SendMessageHandler,
)
