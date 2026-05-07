package com.pdfpro.app.storage

import android.content.Context
import java.io.File

object FileActionsHandler {
    fun renameFile(file: File, newName: String): Boolean {
        val newFile = File(file.parent, newName)
        return file.renameTo(newFile)
    }

    fun duplicateFile(file: File): File? {
        val newName = "${file.nameWithoutExtension}_copy.${file.extension}"
        val newFile = File(file.parent, newName)
        return try {
            file.copyTo(newFile)
            newFile
        } catch (e: Exception) {
            null
        }
    }

    fun moveFile(file: File, destinationDir: File): Boolean {
        val newFile = File(destinationDir, file.name)
        return file.renameTo(newFile)
    }

    fun deleteFile(file: File): Boolean {
        return file.delete()
    }
}
