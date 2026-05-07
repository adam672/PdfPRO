package com.pdfpro.app.engine

import android.content.Context
import java.io.File

object PdfEngine {
    fun mergePdfs(context: Context, files: List<String>, onProgress: (Float, String) -> Unit): File {
        TODO("Implement PDF merge")
    }

    fun splitPdf(context: Context, filePath: String, ranges: String, onProgress: (Float, String) -> Unit): List<File> {
        TODO("Implement PDF split")
    }

    fun compressPdf(context: Context, filePath: String, quality: String, onProgress: (Float, String) -> Unit): File {
        TODO("Implement PDF compress")
    }

    fun reorderPages(context: Context, filePath: String, newOrder: List<Int>, onProgress: (Float, String) -> Unit): File {
        TODO("Implement page reorder")
    }

    fun deletePages(context: Context, filePath: String, pagesToDelete: List<Int>, onProgress: (Float, String) -> Unit): File {
        TODO("Implement page delete")
    }

    fun rotatePages(context: Context, filePath: String, pages: List<Int>, rotation: Int, onProgress: (Float, String) -> Unit): File {
        TODO("Implement page rotate")
    }

    fun addPages(context: Context, filePath: String, source: String, onProgress: (Float, String) -> Unit): File {
        TODO("Implement add pages")
    }

    fun fillForms(context: Context, filePath: String, data: Map<String, String>, onProgress: (Float, String) -> Unit): File {
        TODO("Implement form fill")
    }

    fun signPdf(context: Context, filePath: String, signaturePath: String, page: Int, onProgress: (Float, String) -> Unit): File {
        TODO("Implement PDF sign")
    }

    fun pdfToText(context: Context, filePath: String, onProgress: (Float, String) -> Unit): File {
        TODO("Implement PDF to text")
    }

    fun extractImages(context: Context, filePath: String, onProgress: (Float, String) -> Unit): List<File> {
        TODO("Implement extract images")
    }

    fun extractTables(context: Context, filePath: String, onProgress: (Float, String) -> Unit): File {
        TODO("Implement extract tables")
    }

    fun protectPdf(context: Context, filePath: String, password: String, onProgress: (Float, String) -> Unit): File {
        TODO("Implement PDF protect")
    }

    fun unlockPdf(context: Context, filePath: String, password: String, onProgress: (Float, String) -> Unit): File {
        TODO("Implement PDF unlock")
    }

    fun addWatermark(context: Context, filePath: String, config: com.pdfpro.app.model.WatermarkConfig, onProgress: (Float, String) -> Unit): File {
        TODO("Implement watermark")
    }
}
