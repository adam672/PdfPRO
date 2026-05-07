package com.pdfpro.app.ui.viewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PdfViewerActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_FILE_PATH = "file_path"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filePath = intent.getStringExtra(EXTRA_FILE_PATH)
        // TODO: Implement PDF viewer with zoom and swipe
    }
}
