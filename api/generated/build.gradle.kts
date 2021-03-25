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
        }
    }
}
