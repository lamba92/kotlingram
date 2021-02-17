package com.github.lamba92.kotlingram.examples.js

import NodeJS.get
import com.github.lamba92.kotlingram.api.generated.InlineQueryResultPhoto
import com.github.lamba92.kotlingram.builder.buildPollingBot
import com.github.lamba92.kotlingram.builder.respond
import com.github.lamba92.kotlingram.builder.respondPhoto
import com.github.lamba92.kotlingram.builder.respondText
import io.ktor.client.engine.js.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.coroutineScope
import org.kodein.di.instance

import process

suspend fun main(): Unit = coroutineScope {
    buildPollingBot {

        options {
            botApiToken = process.env["jsTestBotToken"]
            botUsername = "KotlingramJsTestBot"

            engine(Js) {
                install(Logging) {
                    level = LogLevel.ALL
                }
            }
        }

        di {
            import(DIModules.strings)
        }

        handlers {
            messages {
                val customMessage: String by instance("message_response")
                val media: String by instance("media")
                respondPhoto(
                    photo = media,
                    caption = "Hi, i'm Kotlingram JS test bot",
                    replyToMessageId = message.messageId
                )
                respondText("You wrote to me \"${message.text}\", my message is $customMessage")
            }
            inlineQueries {

                val media: String by instance("media")

                val responses = buildList {
                    repeat(10) { index ->
                        add(
                            InlineQueryResultPhoto(
                                id = "response#$index",
                                title = "Inline response #$index",
                                type = "photo",
                                photoUrl = media,
                                thumbUrl = media
                            )
                        )
                    }
                }
                respond(responses)
            }
        }
    }
}
