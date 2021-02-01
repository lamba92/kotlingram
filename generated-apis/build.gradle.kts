plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.github.lamba92.telegram-api-generator")
}

telegramApiParser {
    outputDirectory = file("$buildDir/generated/commonMain/kotlin")
}

kotlin {

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

    js(BOTH) {
        nodejs()
    }

    mingwX64()

    ios()
    watchos()
    tvos()

    linuxX64()

    targets.all {
        compilations.all {
            compileKotlinTaskProvider {
                dependsOn(tasks.generateTelegramApi)
            }
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                val kotlinxSerializationVersion: String by project
                val ktorVersion: String by project
                api("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxSerializationVersion")
                api("io.ktor:ktor-client-core:$ktorVersion")
                api("io.ktor:ktor-client-serialization:$ktorVersion")
            }
            kotlin.srcDir(telegramApiParser.outputDirectory)
        }
        all {
            languageSettings.enableLanguageFeature("InlineClasses")
        }
    }

}
