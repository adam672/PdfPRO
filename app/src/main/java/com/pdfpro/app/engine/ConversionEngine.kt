package com.pdfpro.app.engine

import android.content.Context
import java.io.File

object ConversionEngine {
    fun imagesToPdf(context: Context, imagePaths: List<String>, onProgress: (Float, String) -> Unit): File {
        TODO("Implement images to PDF")
    }

    fun pdfToImages(context: Context, filePath: String, onProgress: (Float, String) -> Unit): List<File> {
        TODO("Implement PDF to images")
    }

    fun pdfToWord(context: Context, filePath: String, onProgress: (Float, String) -> Unit): File {
        TODO("Implement PDF to Word")
    }

    fun wordToPdf(context: Context, filePath: String, onProgress: (Float, String) -> Unit): File {
        TODO("Implement Word to PDF")
    }

    fun excelToPdf(context: Context, filePath: String, onProgress: (Float, String) -> Unit): File {
        TODO("Implement Excel to PDF")
    }

    fun pptxToPdf(context: Context, filePath: String, onProgress: (Float, String) -> Unit): File {
        TODO("Implement PPTX to PDF")
    }
}
