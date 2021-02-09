package com.github.lamba92.kotlingram.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

open class TelegramApiParserPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.create<GenerateTelegramApiTask>("generateTelegramApi") {
            group = "telegram"
        }
    }
}

