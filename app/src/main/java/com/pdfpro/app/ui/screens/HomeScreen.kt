package com.pdfpro.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdfpro.app.R
import com.pdfpro.app.theme.OnBackground
import com.pdfpro.app.theme.OnSurfaceVariant
import com.pdfpro.app.theme.Primary
import com.pdfpro.app.theme.Surface
import com.pdfpro.app.ui.payment.AdBanner
import com.pdfpro.app.ui.payment.InlineAdNotice

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = OnBackground
        )
        Text(
            text = stringResource(R.string.home_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = OnSurfaceVariant
        )
        Spacer(Modifier.height(8.dp))

        InlineAdNotice(onRemoveAdsClick = { navController.navigate("payment") })
        Spacer(Modifier.height(16.dp))

        val items = listOf(
            Triple("recent", R.string.fm_recent, R.string.recent_desc) to Icons.Default.AccessTime,
            Triple("files", R.string.tab_files, R.string.home_files_desc) to Icons.Default.Description,
            Triple("favorites", R.string.tab_favorites, R.string.home_fav_desc) to Icons.Default.Star,
            Triple("payment", R.string.settings_ads, R.string.home_ads_desc) to Icons.Default.Shield,
        )

        items.forEach { (data, icon) ->
            val (route, titleRes, descRes) = data
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate(route) },
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Icon(
                        icon, null,
                        tint = Primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(titleRes),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = stringResource(descRes),
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSurfaceVariant
                        )
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
        }

        Spacer(Modifier.weight(1f))
        AdBanner(onRemoveAdsClick = { navController.navigate("payment") })
    }
}



