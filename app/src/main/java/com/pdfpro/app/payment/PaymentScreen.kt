package com.pdfpro.app.payment

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PaymentScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Payment Screen - Placeholder", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}

@Composable
fun AdBanner(onRemoveAdsClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Advertisement", style = MaterialTheme.typography.titleMedium)
            Text("Support us by removing ads")
            Button(onClick = onRemoveAdsClick) {
                Text("Remove Ads")
            }
        }
    }
}

@Composable
fun InlineAdNotice(onRemoveAdsClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Ads help keep the app free")
            TextButton(onClick = onRemoveAdsClick) {
                Text("Remove")
            }
        }
    }
}
