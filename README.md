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
All methods and types available in the [Telegram Bot API documentation](https://core.telegram.org/bots/api) should be available as extension of `TelegramBotApiClient`.

For building a polling bot with you can use the builder available in `kotlingram-bot-builder`: 
```kotlin
buildPollingBot {

    options {
        botApiToken = System.getenv("jvmTestBotToken")
        botUsername = "KotlingramJvmTestBot"
    }

    di {
        bind<String>(tag = "message_response") with singleton {
            buildString {
                append(System.getProperty("java.vm.name"))
                append(", version ")
                append(System.getProperty("java.vm.version"))
            }
        }
        bind<String>(tag = "media") with singleton {
            "https://www.tc-web.it/wp-content/uploads/2019/01/java.jpg"
        }
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
```
Note that `buildPollingBot` is an extension of `CoroutineScope`! To run it you need to either already be in a scope or create one with `coroutineScope {}`.

## Install
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
- Kodein 7.3.1

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

## Limitations

### Upload of files

At the moment file upload must be handled by passing the url of the file that has already been uploaded somewhere else. See the example above in `respondPhoto()` function.

### Inline query results

When creating a subclass of `InlineQueryResult` you need to set their field `type` according to their documentation. For example when creating `InlineQueryResultPhoto` the `type` must be set to `photo`.

When [kotlinx.serialization/issues/195](https://github.com/Kotlin/kotlinx.serialization/issues/195) will be implemented this behaviour will be automatized.

### Run on NodeJS

TLDR: do not use JS!

Oh boy... JS is evil and whe all know that! There are many issues in creating a working js application for NodeJS, let's list the ones I've encountered for this project. Also note that those problems are not related to kotlingram but to Kotlin/JS + NodeJS.

#### Bundling everything in a single js file

In the js example [`build.gradle.kts`](/examples/js-bot/build.gradle.kts) file I have created some tasks to webpack the output into a single script (something like a fat jar). 

To do so, I've added webpack dependencies [using the Kotlin/JS npm extension](/examples/js-bot/build.gradle.kts#L45-L46) and then used the `com.github.node-gradle.node` to run webpack from the yarn environment created by the Kotlin/JS plugin in root `build/js`. 

I've created a custom task to generate the webpack config file and it's available in the `buildSrc` [here](buildSrc/src/main/kotlin/com/github/lamba92/kotlingram/gradle/tasks/GenerateWebpackConfig.kt). The Kotlin Team said that one day such functionality will be integrated in the official plugin tho.

Note that due to how NodeJS handles import and stuff the output of the Ktor client has to be modified a little when bundling it with webpack for NodeJS. More information [here](https://youtrack.jetbrains.com/issue/KTOR-2124).

#### It won't work anyway

Even if you managed to follow this JS madness, you may have noticed that what I've described above only works when using the legacy Kotlin/JS compiler and if you run the output you will get a serialization error: 
```text
Can't find a method to construct serializer for type TelegramResponse. Make sure this class is marked as @Serializable or provide serializer explicitly.
```
That's because the legacy compiler cannot infer form inline functions the generics! More info [here](https://github.com/Kotlin/kotlinx.serialization/issues/1339). 

This error would be solved if compiling with the new IR compiler but there are issues with Kodein when doing so. More details [here](https://github.com/Kodein-Framework/Kodein-DI/issues/355).

So to sum up, to create a working bot using Kotlin/JS with kotlingram we need to:
- wait until [Kodein-DI/issues/355](https://github.com/Kodein-Framework/Kodein-DI/issues/355) is solved.
- fix the compiled version of Ktor client JS as in [KTOR-2124](https://youtrack.jetbrains.com/issue/KTOR-2124).

