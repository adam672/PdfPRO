package com.pdfpro.app.storage

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

object ShareManager {
    fun shareFile(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = context.contentResolver.getType(uri) ?: "*/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, context.getString(com.pdfpro.app.R.string.share_title)))
    }

    fun shareMultiple(context: Context, files: List<File>) {
        val uris = files.map {
            FileProvider.getUriForFile(context, "${context.packageName}.provider", it)
        }
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            type = "*/*"
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(uris))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, context.getString(com.pdfpro.app.R.string.share_title)))
    }
}
