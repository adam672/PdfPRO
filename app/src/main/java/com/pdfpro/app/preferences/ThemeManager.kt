package com.pdfpro.app.preferences

import android.content.Context
import androidx.core.content.edit

enum class ThemeMode { LIGHT, DARK, SYSTEM }

object ThemeManager {
    private const val PREFS_NAME = "pdfpro_theme_prefs"
    private const val KEY_THEME = "theme_mode"

    fun setThemeMode(context: Context, mode: ThemeMode) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit {
            putString(KEY_THEME, mode.name)
        }
    }

    fun getThemeMode(context: Context): ThemeMode {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return try {
            ThemeMode.valueOf(prefs.getString(KEY_THEME, ThemeMode.SYSTEM.name)!!)
        } catch (e: Exception) {
            ThemeMode.SYSTEM
        }
    }
}

@Composable
fun ThemeSideEffect() {
    // Side effect for theme application
}
