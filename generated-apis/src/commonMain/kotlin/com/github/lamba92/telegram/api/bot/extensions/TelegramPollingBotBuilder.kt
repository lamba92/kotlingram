package com.github.lamba92.telegram.api.bot.extensions

import com.github.lamba92.telegram.api.TelegramBotApiClient
import com.github.lamba92.telegram.api.generated.getUpdates
import io.ktor.http.*
import kotlinx.coroutines.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import kotlin.time.Duration
import kotlin.time.seconds

@TelegramBotsDSL
class TelegramPollingBotBuilder {

    private var diBuilder: DI.MainBuilder.() -> Unit = {}
    private var handlersContainerBuilder: MessageHandlersBuilder.() -> Unit = {}
    private var allowKodeinOverride: Boolean = false
    private var botOptionsAction: PollingBotOptions.() -> Unit = {}

    fun options(action: PollingBotOptions.() -> Unit) {
        botOptionsAction = action
    }

    /**
     * Allows to declare the message handlers.
     */
    fun handlers(handler: MessageHandlersBuilder.() -> Unit) {
        handlersContainerBuilder = handler
    }

    /**
     * Allows to configure the Kodein container.
     * Creates a [DI] instance that will be lazily created upon first access.
     *
     * @param allowSilentOverride Whether the configuration block is allowed to non-explicit overrides.
     * @param init The block of configuration.
     * @return A lazy property that will yield, when accessed, the new Kodein object, freshly created, and ready for hard work!
     */
    fun di(allowSilentOverride: Boolean = false, init: DI.MainBuilder.() -> Unit) {
        allowKodeinOverride = allowSilentOverride
        diBuilder = init
    }

    /**
     * Builds the bot!
     */
    fun build(
        coroutineScope: CoroutineScope,
        start: CoroutineStart = CoroutineStart.DEFAULT,
    ): Job {
        val (
            pollingInterval, botApiToken, botUsername,
            apiProtocol, apiHost, apiPort,
        ) = PollingBotOptions().apply(botOptionsAction)
        require(botApiToken != null) { "Bot API token not set" }
        require(botUsername != null) { "Bot Username not set" }
        val client = TelegramBotApiClient(
            botApiToken,
            protocol = apiProtocol,
            host = apiHost,
            port = apiPort
        )
        val handlers = MessageHandlersBuilder().apply(handlersContainerBuilder).build()
        val di = DI(allowKodeinOverride) {
            diBuilder()
            bind<TelegramBotApiClient>() with singleton { client }
        }
        var lastUpdateId = 0
        return coroutineScope.repeatEvery(
            pollingInterval,
            CoroutineName(botUsername),
            start
        ) {
            val updates = if (lastUpdateId != 0)
                client.getUpdates(lastUpdateId)
            else
                client.getUpdates()
            updates.result.lastOrNull()?.updateId?.let { lastUpdateId = it + 1 }
            updates.result.forEach { update ->
                if (update.inlineQuery != null)
                    launch {
                        runCatching { handlers.inlineQueriesHandler(InlineQueryContext(update.inlineQuery, di)) }
                            .exceptionOrNull()?.printStackTrace()
                    }
                if (update.message != null)
                    launch {
                        runCatching { handlers.sendMessageHandler(MessageContext(update.message, di)) }
                            .exceptionOrNull()?.printStackTrace()
                    }
            }

        }
    }

}

@TelegramBotsDSL
data class PollingBotOptions(
    var pollingInterval: Duration = 0.25.seconds,
    var botApiToken: String? = null,
    var botUsername: String? = null,
    var apiProtocol: URLProtocol = URLProtocol.HTTPS,
    var apiHost: String = "api.telegram.org",
    var apiPort: Int = DEFAULT_PORT,
)
