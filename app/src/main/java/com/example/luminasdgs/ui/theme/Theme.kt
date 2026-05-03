package com.example.luminasdgs.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,
    secondary = BluePrimary,
    onSecondary = Color.White,
    tertiary = YellowAccent,
    onTertiary = TextDark,
    error = RedAccent,
    onError = Color.White,
    background = BackgroundSoft,
    onBackground = TextDark,
    surface = Color.White,
    onSurface = TextDark
)

@Composable
fun LuminaSdgsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}
