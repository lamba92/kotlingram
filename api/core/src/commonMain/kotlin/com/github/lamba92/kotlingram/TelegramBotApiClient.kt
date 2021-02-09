package com.github.lamba92.kotlingram

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*

class TelegramBotApiClient private constructor(
    val httpClient: HttpClient,
    val apiToken: String,
    val apiProtocol: URLProtocol = URLProtocol.HTTPS,
    val apiHost: String = "api.telegram.org",
    val apiPort: Int = DEFAULT_PORT,
) {

    companion object {

        private fun getDefaultConfiguration(): HttpClientConfig<*>.() -> Unit = {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }

        operator fun invoke(
            apiToken: String,
            protocol: URLProtocol = URLProtocol.HTTPS,
            engine: HttpClientEngine? = null,
            host: String = "api.telegram.org",
            port: Int = DEFAULT_PORT,
        ): TelegramBotApiClient {
            val httpClient = if (engine == null)
                HttpClient(getDefaultConfiguration())
            else
                HttpClient(engine, getDefaultConfiguration())
            return TelegramBotApiClient(httpClient, apiToken, protocol, host, port)
        }

        operator fun <T : HttpClientEngineConfig> invoke(
            apiToken: String,
            engine: HttpClientEngineFactory<T>,
            protocol: URLProtocol = URLProtocol.HTTPS,
            host: String = "api.telegram.org",
            port: Int = DEFAULT_PORT,
            configuration: (HttpClientConfig<T>.() -> Unit)? = null,
        ): TelegramBotApiClient {
            val httpClient = HttpClient(engine) {
                getDefaultConfiguration()()
                configuration?.let { it() }
            }
            return TelegramBotApiClient(httpClient, apiToken, protocol, host, port)
        }

    }

}
