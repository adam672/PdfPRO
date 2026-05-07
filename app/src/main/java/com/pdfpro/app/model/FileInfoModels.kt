package com.pdfpro.app.model

data class FileInfo(
    val path: String,
    val name: String,
    val size: Long,
    val lastModified: Long,
    val isDirectory: Boolean = false
)
