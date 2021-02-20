package com.github.lamba92.kotlingram.examples.js

import NodeJS.Process
import NodeJS.get
import com.github.lamba92.kotlingram.api.generated.InlineQueryResultPhoto
import com.github.lamba92.kotlingram.builder.buildPollingBot
import com.github.lamba92.kotlingram.builder.respond
import com.github.lamba92.kotlingram.builder.respondPhoto
import com.github.lamba92.kotlingram.builder.respondText
import io.ktor.client.engine.js.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.coroutineScope


/**
 * Fix for https://github.com/Kotlin/kotlinx-nodejs/issues/13
 */
@JsModule("process")
@JsNonModule
external val process: Process

suspend fun main(): Unit = coroutineScope {

    val customMessage = buildString {
        append("v8: ${process.versions.v8}")
        append(", node: ${process.versions.node}")
    }
    val media = "https://coralogix.com/wp-content/uploads/2018/04/Coralogix-Nodejs-integration.jpg"

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

        handlers {
            messages {
                respondPhoto(
                    photo = media,
                    caption = "Hi, i'm Kotlingram JS test bot",
                    replyToMessageId = message.messageId
                )
                respondText("You wrote to me \"${message.text}\", my message is $customMessage")
            }
            inlineQueries {

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
