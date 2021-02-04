# Telegram bot API for Kotlin ![CI Publication](https://github.com/lamba92/telegram-bot-kotlin-api/workflows/CI%20Publication/badge.svg) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.lamba92/telegram-bot-kotlin-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.lamba92/telegram-bot-kotlin-api) [ ![Download](https://api.bintray.com/packages/lamba92/com.github.lamba92/telegram-bot-kotlin-api/images/download.svg) ](https://bintray.com/lamba92/com.github.lamba92/telegram-bot-kotlin-api/_latestVersion)

Looking for a Kotlin first Telegram APIs? You are in the right place! This project offers Kotlin/Multiplatform APIs for
Telegram bots based on Kotlinx.serialization and Ktor client and coroutines.

The APIs are generated by parsing the official Telegram documentation. For sure there are errors since no testing is in
place, but message retrieval works!

## Usage

Add a Ktor Client engine for your [platform of choice](https://ktor.io/docs/http-client-engines.html) in your Gradle
build, then simply:

```kotlin
val client = TelegramBotApiClient("botToken")
val user: TelegramResponse<User> = client.getMe()
val user: TelegramResponse<List<Update>> = client.getUpdates()
```

The above are suspending calls!

To install add in your Gradle build:

```kotlin
repositories {
    maven("https://dl.bintray.com/lamba92/com.github.lamba92")
}
// ...

dependenceis {
    implementation("com.github.lamba92:telegram-bot-kotlin-api:{latest_version}")
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
                implementation("com.github.lamba92:telegram-bot-kotlin-api:{latest_version}")
            }
        }
    }
}
```

Platforms available are:

- `mingwx64`: Windows x64
- `macosx64`: macOs x64
- `linuxx64`: Linux x64
- `jvm`: JVM target 1.8
- `js`: NodeJS with both compilation backends

Unfortunately I had troubles publishing on Maven Central. I will look on the issue soon. The packages are published on
GitHub Package Registry as well [here](https://github.com/lamba92?tab=packages&repo_name=telegram-bot-kotlin-api) but a
login is required with a GitHub personal access token (PAT). 
