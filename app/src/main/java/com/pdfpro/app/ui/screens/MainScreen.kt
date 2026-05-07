package com.pdfpro.app.ui.screens

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pdfpro.app.R
import com.pdfpro.app.preferences.ThemeSideEffect
import com.pdfpro.app.storage.StorageManager
import com.pdfpro.app.theme.Background
import com.pdfpro.app.theme.OnSurfaceVariant
import com.pdfpro.app.theme.OutlineVariant
import com.pdfpro.app.theme.Primary
import com.pdfpro.app.theme.PrimaryContainer
import com.pdfpro.app.theme.Surface
import com.pdfpro.app.ui.filemanager.FavoritesScreen
import com.pdfpro.app.ui.filemanager.FileManagerScreen
import com.pdfpro.app.ui.filemanager.RecentFilesScreen
import com.pdfpro.app.ui.filemanager.ShareScreen
import com.pdfpro.app.ui.filemanager.validateFileForProcessing
import com.pdfpro.app.ui.payment.PaymentScreen
import com.pdfpro.app.ui.settings.SettingsScreen
import com.pdfpro.app.ui.screens.tools.EditToolScreens.*
import com.pdfpro.app.ui.screens.tools.ConversionToolScreens.*
import com.pdfpro.app.ui.screens.tools.ToolsHubScreen
import com.pdfpro.app.ui.viewer.PdfViewerActivity

private data class TabItem(
    val route: String,
    val labelResId: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector
)

private val tabs = listOf(
    TabItem("home", R.string.tab_home, Icons.Filled.Home, Icons.Filled.Home),
    TabItem("files", R.string.tab_files, Icons.Filled.Description, Icons.Filled.Description),
    TabItem("tools", R.string.tab_tools, Icons.Outlined.Build, Icons.Outlined.Build),
    TabItem("favorites", R.string.tab_favorites, Icons.Filled.Favorite, Icons.Filled.Favorite),
    TabItem("settings", R.string.tab_settings, Icons.Filled.Settings, Icons.Filled.Settings)
)

/* Routes that should hide the bottom bar */
private val detailRoutes = setOf(
    "tool_merge", "tool_split", "tool_compress", "tool_reorder",
    "tool_delete_pages", "tool_rotate", "tool_add_pages", "tool_fill_forms", "tool_sign",
    "tool_img_to_pdf", "tool_pdf_to_img", "tool_pdf_to_txt",
    "tool_pdf_to_word", "tool_word_to_pdf", "tool_excel_to_pdf", "tool_pptx_to_pdf",
    "tool_extract_text", "tool_extract_images", "tool_extract_tables",
    "tool_protect", "tool_unlock", "tool_watermark", "tool_ocr",
    "payment", "recent", "share"
)

@Composable
fun MainScreen() {
    ThemeSideEffect()

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val context = LocalContext.current

    var viewerPath by remember { mutableStateOf<String?>(null) }

    viewerPath?.let { path ->
        val intent = Intent(context, PdfViewerActivity::class.java).apply {
            putExtra(PdfViewerActivity.EXTRA_FILE_PATH, path)
        }
        context.startActivity(intent)
        viewerPath = null
    }

    val hideBottomBar = currentRoute in detailRoutes

    Scaffold(
        containerColor = Background,
        bottomBar = {
            if (!hideBottomBar) {
                NavigationBar(
                    containerColor = Surface,
                    tonalElevation = 8.dp,
                    shadowElevation = 8.dp
                ) {
                    tabs.forEach { tab ->
                        val selected = currentRoute == tab.route
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (selected) tab.selectedIcon else tab.icon,
                                    contentDescription = stringResource(tab.labelResId)
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(tab.labelResId),
                                    style = androidx.compose.material3.MaterialTheme.typography.labelSmall
                                )
                            },
                            selected = selected,
                            onClick = {
                                if (currentRoute != tab.route) {
                                    navController.navigate(tab.route) {
                                        popUpTo("home") { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Primary,
                                selectedTextColor = Primary,
                                unselectedIconColor = OnSurfaceVariant,
                                unselectedTextColor = OnSurfaceVariant,
                                indicatorColor = PrimaryContainer
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            /* ===== TAB SCREENS ===== */
            composable("home") {
                AnimatedVisibility(visible = true, enter = fadeIn() + slideInVertically()) {
                    HomeScreen(navController)
                }
            }
            composable("files") {
                AnimatedVisibility(visible = true, enter = fadeIn() + slideInVertically()) {
                    FileManagerScreen(onNavigateToViewer = { path ->
                        if (validateFileForProcessing(context, path) == null) viewerPath = path
                    })
                }
            }
            composable("tools") {
                AnimatedVisibility(visible = true, enter = fadeIn() + slideInVertically()) {
                    ToolsHubScreen(onToolClick = { toolId ->
                        navController.navigate("tool_$toolId")
                    })
                }
            }
            composable("favorites") {
                AnimatedVisibility(visible = true, enter = fadeIn() + slideInVertically()) {
                    FavoritesScreen(onNavigateToViewer = { path -> viewerPath = path })
                }
            }
            composable("settings") {
                AnimatedVisibility(visible = true, enter = fadeIn() + slideInVertically()) {
                    SettingsScreen(onNavigateToPayment = {
                        navController.navigate("payment")
                    })
                }
            }

            /* ===== SUB-SCREENS ===== */
            composable("recent") {
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    RecentFilesScreen(onNavigateToViewer = { path -> viewerPath = path })
                }
            }
            composable("share") {
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    ShareScreen()
                }
            }
            composable("payment") {
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    PaymentScreen(onBack = { navController.popBackStack() })
                }
            }

            /* ===== EDIT TOOLS ===== */
            composable("tool_merge") {
                AnimatedVisibility(visible = true, enter = fadeIn()) { MergePdfScreen { navController.popBackStack() } }
            }
            composable("tool_split") {
                AnimatedVisibility(visible = true, enter = fadeIn()) { SplitPdfScreen { navController.popBackStack() } }
            }
            composable("tool_compress") {
                AnimatedVisibility(visible = true, enter = fadeIn()) { CompressPdfScreen { navController.popBackStack() } }
            }
            composable("tool_reorder") {
                AnimatedVisibility(visible = true, enter = fadeIn()) { ReorderPagesScreen { navController.popBackStack() } }
            }
            composable("tool_delete_pages") {
                AnimatedVisibility(visible = true, enter = fadeIn()) { DeletePagesScreen { navController.popBackStack() } }
            }
            composable("tool_rotate") {
                AnimatedVisibility(visible = true, enter = fadeIn()) { RotatePagesScreen { navController.popBackStack() } }
            }
            composable("tool_add_pages") {
                AnimatedVisibility(visible = true, enter = fadeIn()) { AddPagesScreen { navController.popBackStack() } }
            }
            composable("tool_fill_forms") {
                AnimatedVisibility(visible = true, enter = fadeIn()) { FillFormsScreen { navController.popBackStack() } }
            }
            composable("tool_sign") {
                AnimatedVisibility(visible = true, enter = fadeIn()) { SignPdfScreen { navController.popBackStack() } }
            }

            /* ===== CONVERSION TOOLS ===== */
            composable("tool_img_to_pdf") {
                AnimatedVisibility(visible = true, enter = fadeIn()) { ImagesToPdfScreen { navController.popBackStack() } }
            }
            composable("tool_pdf_to_img") {
                AnimatedVisibility(visible = true, enter = fadeIn()) { PdfToImagesScreen { navController.popBackStack() } }
            }
            composable("tool_pdf_to_txt") {
                AnimatedVisibility(visible = true, enter = fadeIn()) { PdfToTextScreen { navController.popBackStack() } }
            }
            composable("tool_pdf_to_word") {
                AnimatedVisibility(visible = true, enter = fadeIn()) { PdfToWordScreen { navController.popBackStack() } }
            }
            composable("tool_word_to_pdf") {
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    WordToPdfScreen(navController) { navController.popBackStack() }
                }
            }
            composable("tool_excel_to_pdf") {
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    ExcelToPdfScreen(navController) { navController.popBackStack() }
                }
            }
            composable("tool_pptx_to_pdf") {
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    PptxToPdfScreen(navController) { navController.popBackStack() }
                }
            }

            /* ===== EXTRACTION / SECURITY / OCR ===== */
            composable("tool_extract_text") {
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    ExtractTextScreen(navController) { navController.popBackStack() }
                }
            }
            composable("tool_extract_images") {
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    ExtractImagesScreen(navController) { navController.popBackStack() }
                }
            }
            composable("tool_extract_tables") {
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    ExtractTablesScreen(navController) { navController.popBackStack() }
                }
            }
            composable("tool_protect") {
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    ProtectPdfScreen(navController) { navController.popBackStack() }
                }
            }
            composable("tool_unlock") {
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    UnlockPdfScreen(navController) { navController.popBackStack() }
                }
            }
            composable("tool_watermark") {
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    WatermarkScreen(navController) { navController.popBackStack() }
                }
            }
            composable("tool_ocr") {
                AnimatedVisibility(visible = true, enter = fadeIn()) {
                    OcrScreen(navController) { navController.popBackStack() }
                }
            }
        }
    }
}



