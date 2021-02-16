plugins {
    id("com.github.lamba92.kotlingram-api-generator")
    id("com.github.lamba92.kotlingram-plugin")
}

tasks.generateTelegramApi {
    outputDirectory = file("$buildDir/generated/commonMain/kotlin")
    packageName = "com.github.lamba92.kotlingram.api.generated"
    telegramClientPackage = "com.github.lamba92.kotlingram"
}

kotlin {
    targets.all {
        compilations.all {
            compileKotlinTaskProvider {
                dependsOn(tasks.generateTelegramApi)
            }
        }
    }
    sourceSets {
        commonMain {
            kotlin.srcDir("$buildDir/generated/commonMain/kotlin")
            dependencies {
                val kotlinxSerializationVersion: String by project
                val ktorVersion: String by project
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
                api("io.ktor:ktor-client-serialization:$ktorVersion")
            }
        }
        jsMain {
            dependencies {
                val bufferutilVersion: String by project
                val utf8ValidateVersion: String by project
                api(npm("bufferutil", bufferutilVersion))
                api(npm("utf-8-validate", utf8ValidateVersion))
            }
        }
    }
}


