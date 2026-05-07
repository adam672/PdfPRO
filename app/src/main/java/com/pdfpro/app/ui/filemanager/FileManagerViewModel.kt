package com.pdfpro.app.ui.filemanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfpro.app.model.FileInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FileManagerViewModel : ViewModel() {
    private val _files = MutableStateFlow<List<FileInfo>>(emptyList())
    val files: StateFlow<List<FileInfo>> = _files

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun loadFiles() {
        viewModelScope.launch {
            // TODO: Implement file loading
        }
    }

    fun search(query: String) {
        _searchQuery.value = query
        // TODO: Implement search
    }
}
