plugins {
    id("com.github.lamba92.kotlingram-plugin")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                val kodeinVersion: String by project
                api(project(":api:core"))
                api("org.kodein.di:kodein-di:$kodeinVersion")
            }
        }
        all {
            languageSettings.useExperimentalAnnotation("kotlin.time.ExperimentalTime")
        }
    }
}
