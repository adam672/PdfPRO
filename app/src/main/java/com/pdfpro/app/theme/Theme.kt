package com.pdfpro.app.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.pdfpro.app.preferences.ThemeManager

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Surface,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = Surface,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Outline,
    outlineVariant = OutlineVariant,
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryLight,
    onPrimary = OnBackground,
    primaryContainer = PrimaryDark,
    onPrimaryContainer = PrimaryContainer,
    secondary = SecondaryLight,
    onSecondary = OnBackground,
    secondaryContainer = SecondaryDark,
    onSecondaryContainer = SecondaryContainer,
    background = Color(0xFF1A1D2E),
    onBackground = Color(0xFFE8EAF0),
    surface = Color(0xFF222538),
    onSurface = Color(0xFFE8EAF0),
    surfaceVariant = Color(0xFF2A2E42),
    onSurfaceVariant = Color(0xFFA0A6B8),
    outline = Color(0xFF4A5068),
    outlineVariant = Color(0xFF3A3F55),
)

@Composable
fun PdfPROTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    /* Override with saved preference */
    val context = LocalContext.current
    val forcedDark = when (ThemeManager.getThemeMode(context)) {
        com.pdfpro.app.preferences.ThemeMode.DARK -> true
        com.pdfpro.app.preferences.ThemeMode.LIGHT -> false
        else -> darkTheme
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val ctx = LocalContext.current
            if (forcedDark) dynamicDarkColorScheme(ctx) else dynamicLightColorScheme(ctx)
        }
        forcedDark -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

import androidx.compose.ui.graphics.Color



