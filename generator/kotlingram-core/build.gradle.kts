plugins {
    id("com.github.lamba92.kotlingram-plugin")
}

kotlin {
    sourceSets {
        commonMain {
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
