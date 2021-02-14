package com.github.lamba92.kotlingram.examples.jvm

import com.github.lamba92.kotlingram.api.generated.InlineQueryResultPhoto
import com.github.lamba92.kotlingram.builder.buildPollingBot
import com.github.lamba92.kotlingram.builder.respond
import com.github.lamba92.kotlingram.builder.respondPhoto
import com.github.lamba92.kotlingram.builder.respondText
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.runBlocking
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun main(): Unit = runBlocking {

    buildPollingBot {

        options {
            botApiToken = System.getenv("jvmTestBotToken")
            botUsername = "KotlingramJvmTestBot"

            engine(CIO) {
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
                    caption = "Hi, i'm Kotlingram JVM test bot",
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
