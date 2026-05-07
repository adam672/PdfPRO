package com.pdfpro.app.language

import android.content.Context
import java.util.Locale

object LanguageManager {
    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

    fun getCurrentLanguage(context: Context): String {
        return context.resources.configuration.locales[0].language
    }
}
