package com.pdfpro.app.ui.filemanager

import android.content.Context
import android.widget.Toast

object ErrorHandling {
    fun showError(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun handleFileError(context: Context, error: FileError) {
        val message = when (error) {
            FileError.NOT_FOUND -> "File not found"
            FileError.PERMISSION_DENIED -> "Permission denied"
            FileError.CORRUPTED -> "File is corrupted"
            FileError.UNSUPPORTED -> "Unsupported file format"
            FileError.STORAGE_FULL -> "Storage is full"
            FileError.GENERIC -> "An error occurred"
        }
        showError(context, message)
    }

    enum class FileError {
        NOT_FOUND, PERMISSION_DENIED, CORRUPTED, UNSUPPORTED, STORAGE_FULL, GENERIC
    }
}
