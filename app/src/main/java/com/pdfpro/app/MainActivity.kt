package com.pdfpro.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pdfpro.app.theme.PdfPROTheme
import com.pdfpro.app.ui.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PdfPROTheme {
                MainScreen()
            }
        }
    }
}
