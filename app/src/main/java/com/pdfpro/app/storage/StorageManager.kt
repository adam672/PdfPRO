package com.pdfpro.app.storage

import android.content.Context
import java.io.File

object StorageManager {
    fun getOutputDirectory(context: Context, folderName: String): File {
        val dir = File(context.getExternalFilesDir(null), folderName)
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    fun getAllFiles(context: Context): List<File> {
        val root = context.getExternalFilesDir(null) ?: return emptyList()
        return root.walkTopDown().filter { it.isFile }.toList()
    }

    fun clearCache(context: Context) {
        context.cacheDir.deleteRecursively()
    }

    fun deleteAllFiles(context: Context) {
        context.getExternalFilesDir(null)?.deleteRecursively()
    }

    fun getCacheSize(context: Context): Long {
        return context.cacheDir.walkTopDown().map { it.length() }.sum()
    }

    fun getTotalSize(context: Context): Long {
        return context.getExternalFilesDir(null)?.walkTopDown()?.map { it.length() }?.sum() ?: 0
    }
}
