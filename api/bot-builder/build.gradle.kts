plugins {
    id("com.github.lamba92.kotlingram-plugin")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":api:kotlingram-core"))
            }
        }
        all {
            languageSettings.useExperimentalAnnotation("kotlin.time.ExperimentalTime")
        }
    }
}
