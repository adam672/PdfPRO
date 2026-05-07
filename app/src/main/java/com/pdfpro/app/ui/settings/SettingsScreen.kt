package com.pdfpro.app.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(onNavigateToPayment: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)

        ListItem(
            headlineContent = { Text("Language") },
            supportingContent = { Text("Current: English") }
        )

        ListItem(
            headlineContent = { Text("Theme") },
            supportingContent = { Text("System Default") }
        )

        Button(onClick = onNavigateToPayment, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text("Remove Ads")
        }
    }
}
