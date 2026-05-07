package com.pdfpro.app.model

import androidx.compose.ui.graphics.vector.ImageVector

data class ToolItem(
    val id: String,
    val titleResId: Int,
    val descriptionResId: Int,
    val icon: ImageVector,
    val category: ToolCategory
)

enum class ToolCategory {
    EDIT, CONVERT, EXTRACT, SECURITY, OCR
}

data class WatermarkConfig(
    val type: WatermarkType = WatermarkType.TEXT,
    val text: String = "",
    val imagePath: String? = null,
    val opacity: Int = 50,
    val position: WatermarkPosition = WatermarkPosition.CENTER
)

enum class WatermarkType { TEXT, IMAGE }
enum class WatermarkPosition { TOP_LEFT, TOP_CENTER, TOP_RIGHT, CENTER, BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT }
