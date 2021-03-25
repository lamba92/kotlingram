plugins {
    id("com.github.lamba92.kotlingram-plugin")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":api:kotlingram-generated-apis"))
            }
        }
        all {
            languageSettings.useExperimentalAnnotation("kotlin.time.ExperimentalTime")
        }
    }
}
