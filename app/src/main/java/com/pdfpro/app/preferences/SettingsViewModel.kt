package com.pdfpro.app.preferences

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    data class SettingsUiState(
        val currentLanguage: String = "en",
        val themeMode: ThemeMode = ThemeMode.SYSTEM,
        val adsRemoved: Boolean = false,
        val processedFilesCount: Int = 0,
        val totalSize: String = "0 MB",
        val cacheSize: String = "0 MB"
    )
}
