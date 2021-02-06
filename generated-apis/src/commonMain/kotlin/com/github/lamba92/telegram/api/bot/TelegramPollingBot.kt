package com.github.lamba92.telegram.api.bot

import com.github.lamba92.telegram.api.TelegramBotApiClient
import com.github.lamba92.telegram.api.generated.getUpdates
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.seconds

fun TelegramBotApiClient.pollingUpdatesFlow(pollingInterval: Duration = 0.25.seconds) = flow {
    while (true) {
        getUpdates().result.forEach { emit(it) }
        delay(pollingInterval)
    }
}
