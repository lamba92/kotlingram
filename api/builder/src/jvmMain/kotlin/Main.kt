import com.github.lamba92.kotlingram.builder.buildPollingBot
import com.github.lamba92.kotlingram.builder.respond
import com.github.lamba92.kotlingram.builder.respondText
import com.github.lamba92.kotlingram.api.generated.InlineQueryResultArticle
import com.github.lamba92.kotlingram.api.generated.InputTextMessageContent
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.runBlocking

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
        handlers {
            messages {
                respondText(message.text + "borra", replyToMessageId = message.messageId)
            }
            inlineQueries {
                respond(
                    listOf(
                        InlineQueryResultArticle(
                            "ciao mamma",
                            "hola",
                            InputTextMessageContent("ciao mamma guarda come mi diverto", parseMode = "markdown")
                        )
                    )
                )
            }
        }
    }
}
