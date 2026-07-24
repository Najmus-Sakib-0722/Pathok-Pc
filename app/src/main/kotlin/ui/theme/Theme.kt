package com.pathok.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalPathokColors = compositionLocalOf { SepiaColors }

@Composable
fun PathokTheme(
    colors: PathokColors = SepiaColors,
    content: @Composable () -> Unit
) {
    val materialScheme = if (colors === DarkColors || colors === NightColors || colors === ForestColors) {
        darkColorScheme(
            primary = colors.accent,
            secondary = colors.accent2,
            background = colors.bg,
            surface = colors.cardBg,
            onBackground = colors.text,
            onSurface = colors.text
        )
    } else {
        lightColorScheme(
            primary = colors.accent,
            secondary = colors.accent2,
            background = colors.bg,
            surface = colors.cardBg,
            onBackground = colors.text,
            onSurface = colors.text
        )
    }

    CompositionLocalProvider(LocalPathokColors provides colors) {
        MaterialTheme(
            colorScheme = materialScheme,
            typography = PathokTypography,
            content = content
        )
    }
}

// যেকোনো Composable-এ ব্যবহার: val colors = LocalPathokColors.current