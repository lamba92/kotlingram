import com.github.lamba92.kotlingram.gradle.div

plugins {
    kotlin("multiplatform")
}

kotlin {
    mingwX64 {
        binaries.executable {
            entryPoint = "com.github.lamba92.kotlingram.examples.mingwx64.main"
            linkerOpts("-L${projectDir / "libs"}")
        }
    }
    sourceSets {
        named("mingwX64Main") {
            dependencies {
                val ktorVersion: String by project
                implementation(project(":api:bot-builder"))
                implementation("io.ktor:ktor-client-curl:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
            }
        }
        all {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
        }
    }
}
