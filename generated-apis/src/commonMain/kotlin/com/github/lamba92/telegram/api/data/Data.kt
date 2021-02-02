package com.github.lamba92.telegram.api.data

import com.github.lamba92.telegram.api.generated.ResponseParameters
import com.github.lamba92.telegram.api.generated.User
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TelegramResponse<T>(
    val result: T,
    val ok: Boolean,
    val description: String? = null,
    @SerialName("error_code") val errorCode: Int? = null,
    val parameters: ResponseParameters? = null,
)

class TelegramBotApiClient private constructor(val httpClient: HttpClient) {

    companion object {

        private fun getDefaultConfiguration(): HttpClientConfig<*>.() -> Unit = {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }

        fun TelegramBotApiClient(engine: HttpClientEngine? = null): TelegramBotApiClient {
            val httpClient = if (engine == null)
                HttpClient(getDefaultConfiguration())
            else
                HttpClient(engine, getDefaultConfiguration())
            return TelegramBotApiClient(httpClient)
        }

        fun <T : HttpClientEngineConfig> TelegramBotApiClient(
            engine: HttpClientEngineFactory<T>,
            configuration: (HttpClientConfig<T>.() -> Unit)? = null
        ): TelegramBotApiClient {
            val httpClient = HttpClient(engine) {
                getDefaultConfiguration()()
                configuration?.let { it() }
            }
            return TelegramBotApiClient(httpClient)
        }

    }

}

suspend fun TelegramBotApiClient.getMe(): TelegramResponse<User> =
    httpClient.get<TelegramResponse<User>> {
        url("https://api.bla bla bla")


    }
