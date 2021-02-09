package com.github.lamba92.kotlingram.builder

data class MessageHandlersContainer(
    val inlineQueriesHandler: InlineQueryHandler,
    val sendMessageHandler: SendMessageHandler,
)
