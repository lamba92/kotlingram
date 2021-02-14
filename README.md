# Kotlingram | Telegram bot API for Kotlin/Multiplatform ![CI Publication](https://github.com/lamba92/kotlingram/workflows/CI%20Publication/badge.svg) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.lamba92/kotlingram-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.lamba92/kotlingram-core)

Looking for a Kotlin first Telegram APIs? You are in the right place! This project offers Kotlin/Multiplatform APIs for
Telegram bots based on kotlinx.serialization and Ktor client and coroutines.

## Usage
For basic API usage you can use the client available in `kotlingram-core`:
```kotlin
val client = TelegramBotApiClient("botToken")
val user: TelegramResponse<User> = client.getMe()
val user: TelegramResponse<List<Update>> = client.getUpdates()
```

For building a polling bot with you can use the builder available in `kotlingram-bot-builder`: 
```kotlin
fun main(): Unit = runBlocking {
    
    buildPollingBot {
        
        options {
            botApiToken = System.getenv("DRAGALIA_BOT_TOKEN")
            botUsername = "DragaliaBot"
        }

        handlers {
            messages {
                respondText(text = message.text, replyToMessageId = message.messageId)
            }
            inlineQueries {
                respond(
                    listOf(
                        InlineQueryResultArticle(
                            id = "ciao mamma",
                            title = "Hello!",
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

```

The above are suspending calls!

To install add in your Gradle build:

```kotlin
repositories {
    maven("https://dl.bintray.com/lamba92/com.github.lamba92")
}
// ...
dependenceis {
    implementation("com.github.lamba92:kotlingram:{latest_version}")
}
```

For Kotlin/Multiplatform:

```kotlin
repositories {
    maven("https://dl.bintray.com/lamba92/com.github.lamba92")
}
// ...
kotlin {
    // ...
    sourceSets {
        commonMain {
            dependencies {
                implementation("com.github.lamba92:kotlingram-core:{latest_version}")
                implementation("com.github.lamba92:kotlingram-bot-builder:{latest_version}")
            }
        }
    }
}
```

Add a Ktor Client engine for your [platform of choice](https://ktor.io/docs/http-client-engines.html) in your Gradle
build.

Versions used:

- Kotlin 1.4.30
- Kotlinx.serialization: 1.0.1
- Ktor: 1.5.1

Platforms available are:
- `jvm`: JVM target 1.8
- `js`: NodeJS with old compilation backend

`mingwx64`, `macosx64` and `linuxx64` platform are have some building issues I will work on later. 

The packages are published on GitHub Package Registry as well [here](https://github.com/lamba92?tab=packages&repo_name=kotlingram), but a
login is required with a GitHub personal access token (PAT).

## Extensible

The implementation of each call is fairly simple, and it is possible to extend missing (or maybe wrongly generated)
calls using extension functions:

```kotlin
@Serializable
data class GetUpdatesRequest(
    val offset: Int? = null,
    val limit: Int? = null,
    val timeout: Int? = null,
    @SerialName("allowed_updates") val allowedUpdates: List<String> = emptyList()
)

suspend fun TelegramBotApiClient.getUpdates(
    requestBody: GetUpdatesRequest
): TelegramResponse<List<Update>> =
    httpClient.post<TelegramResponse<List<Update>>> {
        url {
            protocol = apiProtocol
            host = apiHost
            port = apiPort
            path("bot$apiToken", "getUpdates")
        }
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        body = requestBody
    }

suspend fun TelegramBotApiClient.getUpdates(
    offset: Int? = null,
    limit: Int? = null,
    timeout: Int? = null,
    allowedUpdates: List<String> = emptyList(),
): TelegramResponse<List<Update>> =
    getUpdates(
        GetUpdatesRequest(
            offset,
            limit,
            timeout,
            allowedUpdates
        )
    )
```

If you find any issue please report them in the issue section!
