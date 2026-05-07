package com.pdfpro.app.ui.screens.tools

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfpro.app.engine.PdfEngine
import kotlinx.coroutines.launch

// Processing State sealed class
sealed class ProcessingState {
    object Idle : ProcessingState()
    data class Working(val progress: Float, val message: String) : ProcessingState()
    data class Success(val outputFiles: List<String>, val message: String) : ProcessingState()
    data class Error(val message: String) : ProcessingState()
}

// Shared UI Components
@Composable
fun SectionHeader(title: String) {
    Text(title, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(vertical = 16.dp))
}

@Composable
fun SingleFilePicker(onFileSelected: (String) -> Unit, mimeType: String = "application/pdf") {
    Button(onClick = { /* TODO: Implement file picker */ }, modifier = Modifier.fillMaxWidth()) {
        Text("Pick File")
    }
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
    Button(onClick = onClick, enabled = enabled, modifier = Modifier.fillMaxWidth()) {
        Text(text)
    }
}

@Composable
fun ProcessingOverlay(state: ProcessingState) {
    if (state is ProcessingState.Working) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            CircularProgressIndicator()
            Text(state.message, modifier = Modifier.padding(top = 16.dp))
        }
    }
}

@Composable
fun ResultScreen(state: ProcessingState.Success, onOpen: () -> Unit, onDone: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDone,
        title = { Text("Success") },
        text = { Text(state.message) },
        confirmButton = {
            Button(onClick = onOpen) { Text("Open") }
        },
        dismissButton = {
            TextButton(onClick = onDone) { Text("Done") }
        }
    )
}

@Composable
fun ErrorScreen(state: ProcessingState.Error, onRetry: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Error") },
        text = { Text(state.message) },
        confirmButton = {
            Button(onClick = onRetry) { Text("Retry") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Dismiss") }
        }
    )
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(value: String, onValueChange: (String) -> Unit, label: String) {
    androidx.compose.material3.OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun WatermarkConfigPanel(
    config: com.pdfpro.app.model.WatermarkConfig,
    onConfigChange: (com.pdfpro.app.model.WatermarkConfig) -> Unit,
    onPickImage: () -> Unit
) {
    Column {
        // TODO: Implement watermark configuration UI
    }
}

fun openFile(context: android.content.Context, path: String?) {
    path?.let {
        // TODO: Implement file opening
    }
}

// Edit Tool ViewModels and Screens
class MergePdfViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
    var files = mutableStateListOf<String>()

    fun execute(ctx: android.content.Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "Merging...")
        try {
            val out = PdfEngine.mergePdfs(ctx, files) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(listOf(out.absolutePath), "Merged successfully")
        } catch (e: Exception) {
            state = ProcessingState.Error(e.message ?: "Failed")
        }
    }
    fun reset() { state = ProcessingState.Idle; files.clear() }
}

@Composable
fun MergePdfScreen(onBack: () -> Unit) {
    val vm: MergePdfViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SectionHeader("Merge PDFs")
        // TODO: Implement merge UI
    }
}

class SplitPdfViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
    var filePath by mutableStateOf<String?>(null)
    var ranges by mutableStateOf("")

    fun execute(ctx: android.content.Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "Splitting...")
        try {
            val out = PdfEngine.splitPdf(ctx, filePath!!, ranges) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(out.map { it.absolutePath }, "Split successfully")
        } catch (e: Exception) {
            state = ProcessingState.Error(e.message ?: "Failed")
        }
    }
    fun reset() { state = ProcessingState.Idle; filePath = null; ranges = "" }
}

@Composable
fun SplitPdfScreen(onBack: () -> Unit) {
    val vm: SplitPdfViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SectionHeader("Split PDF")
        // TODO: Implement split UI
    }
}

class CompressPdfViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
    var filePath by mutableStateOf<String?>(null)
    var quality by mutableStateOf("medium")

    fun execute(ctx: android.content.Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "Compressing...")
        try {
            val out = PdfEngine.compressPdf(ctx, filePath!!, quality) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(listOf(out.absolutePath), "Compressed successfully")
        } catch (e: Exception) {
            state = ProcessingState.Error(e.message ?: "Failed")
        }
    }
    fun reset() { state = ProcessingState.Idle; filePath = null; quality = "medium" }
}

@Composable
fun CompressPdfScreen(onBack: () -> Unit) {
    val vm: CompressPdfViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SectionHeader("Compress PDF")
        // TODO: Implement compress UI
    }
}

class ReorderPagesViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
    var filePath by mutableStateOf<String?>(null)

    fun execute(ctx: android.content.Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "Reordering...")
        // TODO: Implement
    }
    fun reset() { state = ProcessingState.Idle; filePath = null }
}

@Composable
fun ReorderPagesScreen(onBack: () -> Unit) {
    val vm: ReorderPagesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SectionHeader("Reorder Pages")
    }
}

class DeletePagesViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
    var filePath by mutableStateOf<String?>(null)
    var pagesToDelete by mutableStateOf("")

    fun execute(ctx: android.content.Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "Deleting pages...")
        // TODO: Implement
    }
    fun reset() { state = ProcessingState.Idle; filePath = null; pagesToDelete = "" }
}

@Composable
fun DeletePagesScreen(onBack: () -> Unit) {
    val vm: DeletePagesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SectionHeader("Delete Pages")
    }
}

class RotatePagesViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
    var filePath by mutableStateOf<String?>(null)

    fun execute(ctx: android.content.Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "Rotating...")
        // TODO: Implement
    }
    fun reset() { state = ProcessingState.Idle; filePath = null }
}

@Composable
fun RotatePagesScreen(onBack: () -> Unit) {
    val vm: RotatePagesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SectionHeader("Rotate Pages")
    }
}

class AddPagesViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
    var filePath by mutableStateOf<String?>(null)

    fun execute(ctx: android.content.Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "Adding pages...")
        // TODO: Implement
    }
    fun reset() { state = ProcessingState.Idle; filePath = null }
}

@Composable
fun AddPagesScreen(onBack: () -> Unit) {
    val vm: AddPagesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SectionHeader("Add Pages")
    }
}

class FillFormsViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
    var filePath by mutableStateOf<String?>(null)

    fun execute(ctx: android.content.Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "Filling forms...")
        // TODO: Implement
    }
    fun reset() { state = ProcessingState.Idle; filePath = null }
}

@Composable
fun FillFormsScreen(onBack: () -> Unit) {
    val vm: FillFormsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SectionHeader("Fill Forms")
    }
}

class SignPdfViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
    var filePath by mutableStateOf<String?>(null)

    fun execute(ctx: android.content.Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "Signing...")
        // TODO: Implement
    }
    fun reset() { state = ProcessingState.Idle; filePath = null }
}

@Composable
fun SignPdfScreen(onBack: () -> Unit) {
    val vm: SignPdfViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SectionHeader("Sign PDF")
    }
}

// Conversion tool screens that are referenced but not in ConversionToolScreens.kt
class ImagesToPdfViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
    var imagePaths = mutableStateListOf<String>()

    fun execute(ctx: android.content.Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "Converting...")
        try {
            val out = com.pdfpro.app.engine.ConversionEngine.imagesToPdf(ctx, imagePaths) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(listOf(out.absolutePath), "Converted successfully")
        } catch (e: Exception) {
            state = ProcessingState.Error(e.message ?: "Failed")
        }
    }
    fun reset() { state = ProcessingState.Idle; imagePaths.clear() }
}

@Composable
fun ImagesToPdfScreen(onBack: () -> Unit) {
    val vm: ImagesToPdfViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SectionHeader("Images to PDF")
    }
}

class PdfToImagesViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
    var filePath by mutableStateOf<String?>(null)

    fun execute(ctx: android.content.Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "Converting...")
        try {
            val out = com.pdfpro.app.engine.ConversionEngine.pdfToImages(ctx, filePath!!) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(out.map { it.absolutePath }, "Converted successfully")
        } catch (e: Exception) {
            state = ProcessingState.Error(e.message ?: "Failed")
        }
    }
    fun reset() { state = ProcessingState.Idle; filePath = null }
}

@Composable
fun PdfToImagesScreen(onBack: () -> Unit) {
    val vm: PdfToImagesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SectionHeader("PDF to Images")
    }
}

class PdfToTextViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
    var filePath by mutableStateOf<String?>(null)

    fun execute(ctx: android.content.Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "Converting...")
        try {
            val out = com.pdfpro.app.engine.ConversionEngine.pdfToWord(ctx, filePath!!) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(listOf(out.absolutePath), "Converted successfully")
        } catch (e: Exception) {
            state = ProcessingState.Error(e.message ?: "Failed")
        }
    }
    fun reset() { state = ProcessingState.Idle; filePath = null }
}

@Composable
fun PdfToTextScreen(onBack: () -> Unit) {
    val vm: PdfToTextViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SectionHeader("PDF to Text")
    }
}

class PdfToWordViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
    var filePath by mutableStateOf<String?>(null)

    fun execute(ctx: android.content.Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "Converting...")
        try {
            val out = com.pdfpro.app.engine.ConversionEngine.pdfToWord(ctx, filePath!!) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(listOf(out.absolutePath), "Converted successfully")
        } catch (e: Exception) {
            state = ProcessingState.Error(e.message ?: "Failed")
        }
    }
    fun reset() { state = ProcessingState.Idle; filePath = null }
}

@Composable
fun PdfToWordScreen(onBack: () -> Unit) {
    val vm: PdfToWordViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SectionHeader("PDF to Word")
    }
}
