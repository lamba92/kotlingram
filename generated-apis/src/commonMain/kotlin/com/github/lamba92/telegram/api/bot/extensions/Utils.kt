package com.github.lamba92.telegram.api.bot.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration

/**
 * Creates and configures a polling bot using the [TelegramBotsDSL].
 */
fun CoroutineScope.buildPollingBot(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    builder: TelegramPollingBotBuilder.() -> Unit,
) =
    TelegramPollingBotBuilder().apply(builder).build(this, start)

@DslMarker
internal annotation class TelegramBotsDSL

fun buildMarkdown(action: MarkdownBuilder.() -> Unit) =
    MarkdownBuilder().apply(action).toString()

internal fun CoroutineScope.repeatEvery(
    duration: Duration,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    action: suspend CoroutineScope.() -> Unit,
) = launch(context, start) {
    while (true) {
        action()
        delay(duration)
    }
}
