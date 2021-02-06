import com.github.lamba92.telegram.api.bot.extensions.buildPollingBot
import com.github.lamba92.telegram.api.bot.extensions.respond
import com.github.lamba92.telegram.api.bot.extensions.respondText
import com.github.lamba92.telegram.api.generated.InlineQueryResultArticle
import com.github.lamba92.telegram.api.generated.InputTextMessageContent
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val botSession = buildPollingBot {
        options {
            botApiToken = System.getenv("DRAGALIA_BOT_TOKEN")
            botUsername = "DragaliaBot"
        }
        handlers {
            messages {
                respondText(message.text + "borra", replyToMessageId = message.messageId)
            }
            inlineQueries {
                respond(listOf(InlineQueryResultArticle("ciao mamma",
                    "hola",
                    InputTextMessageContent("ciao mamma guarda come mi diverto"))))
            }
        }
    }
}
