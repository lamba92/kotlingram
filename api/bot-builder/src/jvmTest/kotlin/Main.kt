import com.github.lamba92.kotlingram.TelegramResponse
import com.github.lamba92.kotlingram.api.generated.*
import com.github.lamba92.kotlingram.builder.buildPollingBot
import com.github.lamba92.kotlingram.builder.respond
import com.github.lamba92.kotlingram.builder.respondText
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun main(): Unit = runBlocking {

    buildPollingBot {

        options {
            botApiToken = System.getenv("DRAGALIA_BOT_TOKEN")
            botUsername = "DragaliaBot"

            engine(CIO) {
                install(Logging) {
                    level = LogLevel.ALL
                }
            }
        }

        di {
            bind<String>(tag = "hello_world") with singleton { "Hello world!" }
        }

        handlers {
            messages {
                val myString: String by instance("hello_world")
                val me: Deferred<TelegramResponse<User>> = async { api.getMe() }

                respondText(text = message.text + myString, replyToMessageId = message.messageId)

                println(me.await().result)
            }
            inlineQueries {

                val myString: String by instance("hello_world")

                val me: TelegramResponse<List<BotCommand>> = api.getMyCommands()

                respond(
                    listOf(
                        InlineQueryResultArticle(
                            id = "ciao mamma",
                            title = myString,
                            inputMessageContent = InputTextMessageContent(
                                messageText = "ciao mamma guarda come mi diverto",
                                parseMode = "markdown"
                            ),
                            type = "article"
                        )
                    )
                )
            }
        }
    }
}
