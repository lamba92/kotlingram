import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
}

kotlin {

    mingwX64()
    linuxX64()
    macosX64()

    targets.withType<KotlinNativeTarget> {
        binaries.executable {
            entryPoint = "com.github.lamba92.kotlingram.examples.native.main"
        }
    }

    sourceSets {
        val commonMain by getting  {
            dependencies {
                val ktorVersion: String by project
                implementation(project(":api:kotlingram-bot-builder"))
//                implementation("com.github.lamba92:kotlingram-bot-builder:1.2.2")
                implementation("io.ktor:ktor-client-curl:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
            }
        }
        val commonNativeMain by creating {
            dependsOn(commonMain)
        }
        val mingwX64Main by getting {
            dependsOn(commonNativeMain)
        }
        val linuxX64Main by getting {
            dependsOn(commonNativeMain)
        }
        val macosX64Main by getting {
            dependsOn(commonNativeMain)
        }
        all {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
        }
    }
}
