package com.pdfpro.app.ui.screens.tools

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ToolsHubScreen(onToolClick: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Tools", style = MaterialTheme.typography.headlineMedium)

        // Edit Tools
        Text("Edit PDF", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
        val editTools = listOf("merge", "split", "compress", "reorder", "delete_pages", "rotate", "add_pages", "fill_forms", "sign")
        editTools.forEach { tool ->
            Button(onClick = { onToolClick(tool) }, modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                Text(tool.replace("_", " ").capitalize())
            }
        }

        // Convert Tools
        Text("Convert", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
        val convertTools = listOf("img_to_pdf", "pdf_to_img", "pdf_to_txt", "pdf_to_word", "word_to_pdf", "excel_to_pdf", "pptx_to_pdf")
        convertTools.forEach { tool ->
            Button(onClick = { onToolClick(tool) }, modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                Text(tool.replace("_", " ").capitalize())
            }
        }
    }
}
