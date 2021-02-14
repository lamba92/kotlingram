package com.github.lamba92.kotlingram.builder

import com.github.lamba92.kotlingram.TelegramBotApiClient
import com.github.lamba92.kotlingram.api.generated.*
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.http.*
import kotlinx.coroutines.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import kotlin.time.Duration
import kotlin.time.seconds

@TelegramBotsDSL
class TelegramPollingBotBuilder {

    @TelegramBotsDSL
    class BotOptions {

        var pollingInterval: Duration = 0.25.seconds
        var botApiToken: String? = null
        var botUsername: String? = null
        var apiProtocol: URLProtocol = URLProtocol.HTTPS
        var apiHost: String = "api.telegram.org"
        var apiPort: Int = DEFAULT_PORT

        internal var engine: HttpClientEngineFactory<*>? = null
        internal var engineConfiguration: (HttpClientConfig<out HttpClientEngineConfig>.() -> Unit) = {}

        fun <T : HttpClientEngineConfig> engine(
            engine: HttpClientEngineFactory<T>,
            configuration: (HttpClientConfig<T>.() -> Unit) = { },
        ) {
            this.engine = engine
            @Suppress("UNCHECKED_CAST") // it should not even be casted...
            engineConfiguration = configuration as HttpClientConfig<out HttpClientEngineConfig>.() -> Unit
        }
    }

    private var diBuilder: DI.MainBuilder.() -> Unit = {}
    private var handlersContainerBuilder: MessageHandlersBuilder.() -> Unit = {}
    private var allowKodeinOverride: Boolean = false
    private var botOptionsAction: BotOptions.() -> Unit = {}

    fun options(action: BotOptions.() -> Unit) {
        botOptionsAction = action
    }

    /**
     * Allows to declare the message handlers.
     */
    @TelegramBotsDSL
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
        val options = BotOptions().apply(botOptionsAction)
        require(options.botApiToken != null) { "Bot API token not set" }
        require(options.botUsername != null) { "Bot Username not set" }
        val client = if (options.engine != null)
            TelegramBotApiClient(
                options.botApiToken!!,
                options.engine!!,
                options.apiProtocol,
                options.apiHost,
                options.apiPort,
                options.engineConfiguration
            )
        else
            TelegramBotApiClient(
                options.botApiToken!!,
                protocol = options.apiProtocol,
                host = options.apiHost,
                port = options.apiPort
            )

        val handlers = MessageHandlersBuilder().apply(handlersContainerBuilder).build()
        val di = DI(allowKodeinOverride) {
            diBuilder()
            bind<TelegramBotApiClient>() with singleton { client }
        }
        var lastUpdateId = 0
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }
        return coroutineScope.repeatEvery(
            options.pollingInterval,
            CoroutineName(options.botUsername!!) + exceptionHandler,
            start
        ) {
            val updates = if (lastUpdateId != 0)
                client.getUpdates(lastUpdateId)
            else
                client.getUpdates()
            updates.result.lastOrNull()?.updateId?.let { lastUpdateId = it + 1 }
            updates.result.forEach { update ->
                update.inlineQuery?.let { inlineQuery ->
                    launch(exceptionHandler) {
                        handlers.inlineQueriesHandler(InlineQueryContext(inlineQuery, di, coroutineContext))
                    }
                }
                update.message?.let { message ->
                    launch(exceptionHandler) {
                        handlers.sendMessageHandler(MessageContext(message, di, coroutineContext))
                    }
                }
            }
        }
    }

}

