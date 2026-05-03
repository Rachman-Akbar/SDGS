package com.example.luminasdgs.ui.screens.game.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun rememberCountdownTimer(
    initialSeconds: Int,
    isRunning: Boolean,
    resetKey: Any,
    onFinished: () -> Unit
): Int {
    var secondsLeft by remember(resetKey) { mutableIntStateOf(initialSeconds) }

    LaunchedEffect(isRunning, secondsLeft, resetKey) {
        if (!isRunning) return@LaunchedEffect
        if (secondsLeft <= 0) {
            onFinished()
            return@LaunchedEffect
        }
        delay(1000)
        secondsLeft -= 1
    }

    return secondsLeft
}

fun formatTimer(seconds: Int): String {
    val safeSeconds = seconds.coerceAtLeast(0)
    val mm = safeSeconds / 60
    val ss = safeSeconds % 60
    return String.format(Locale.US, "%02d:%02d", mm, ss)
}
