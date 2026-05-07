/* The following ViewModels were referenced in MainScreen but cut from Part 2.
   Add them to ConversionToolScreens.kt: */

class WordToPdfViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
        private set
    var filePath by mutableStateOf<String?>(null)

    fun execute(ctx: Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "")
        try {
            val out = ConversionEngine.wordToPdf(ctx, filePath!!) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(listOf(out.absolutePath), ctx.getString(R.string.proc_word_to_pdf_done))
        } catch (e: Exception) { state = ProcessingState.Error(e.localizedMessage ?: "Failed") }
    }
    fun reset() { state = ProcessingState.Idle; filePath = null }
}

@Composable
fun WordToPdfScreen(navController: NavController, onBack: () -> Unit) {
    val vm: WordToPdfViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val ctx = LocalContext.current
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())) {
            SectionHeader(stringResource(R.string.tool_word_to_pdf))
            if (vm.filePath == null) SingleFilePicker(onFileSelected = { vm.filePath = it }, mimeType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
            else {
                Text(File(vm.filePath!!).name, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(24.dp))
                ActionButton(stringResource(R.string.proc_convert_btn), { vm.execute(ctx) }, enabled = vm.state is ProcessingState.Idle)
            }
        }
        ProcessingOverlay(vm.state)
        when (vm.state) {
            is ProcessingState.Success -> ResultScreen(vm.state as ProcessingState.Success, { openFile(ctx, (vm.state as ProcessingState.Success).outputFiles.firstOrNull()) }, { vm.reset() })
            is ProcessingState.Error -> ErrorScreen(vm.state as ProcessingState.Error, { vm.execute(ctx) }, { vm.reset() })
            else -> {}
        }
    }
}

class ExcelToPdfViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
        private set
    var filePath by mutableStateOf<String?>(null)

    fun execute(ctx: Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "")
        try {
            val out = ConversionEngine.excelToPdf(ctx, filePath!!) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(listOf(out.absolutePath), ctx.getString(R.string.proc_excel_to_pdf_done))
        } catch (e: Exception) { state = ProcessingState.Error(e.localizedMessage ?: "Failed") }
    }
    fun reset() { state = ProcessingState.Idle; filePath = null }
}

@Composable
fun ExcelToPdfScreen(navController: NavController, onBack: () -> Unit) {
    val vm: ExcelToPdfViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val ctx = LocalContext.current
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())) {
            SectionHeader(stringResource(R.string.tool_excel_to_pdf))
            if (vm.filePath == null) SingleFilePicker(onFileSelected = { vm.filePath = it }, mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
            else {
                Text(File(vm.filePath!!).name, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(24.dp))
                ActionButton(stringResource(R.string.proc_convert_btn), { vm.execute(ctx) }, enabled = vm.state is ProcessingState.Idle)
            }
        }
        ProcessingOverlay(vm.state)
        when (vm.state) {
            is ProcessingState.Success -> ResultScreen(vm.state as ProcessingState.Success, { openFile(ctx, (vm.state as ProcessingState.Success).outputFiles.firstOrNull()) }, { vm.reset() })
            is ProcessingState.Error -> ErrorScreen(vm.state as ProcessingState.Error, { vm.execute(ctx) }, { vm.reset() })
            else -> {}
        }
    }
}

class PptxToPdfViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle)
        private set
    var filePath by mutableStateOf<String?>(null)

    fun execute(ctx: Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "")
        try {
            val out = ConversionEngine.pptxToPdf(ctx, filePath!!) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(listOf(out.absolutePath), ctx.getString(R.string.proc_pptx_to_pdf_done))
        } catch (e: Exception) { state = ProcessingState.Error(e.localizedMessage ?: "Failed") }
    }
    fun reset() { state = ProcessingState.Idle; filePath = null }
}

@Composable
fun PptxToPdfScreen(navController: NavController, onBack: () -> Unit) {
    val vm: PptxToPdfViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val ctx = LocalContext.current
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())) {
            SectionHeader(stringResource(R.string.tool_pptx_to_pdf))
            if (vm.filePath == null) SingleFilePicker(onFileSelected = { vm.filePath = it }, mimeType = "application/vnd.openxmlformats-officedocument.presentationml.presentation")
            else {
                Text(File(vm.filePath!!).name, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(24.dp))
                ActionButton(stringResource(R.string.proc_convert_btn), { vm.execute(ctx) }, enabled = vm.state is ProcessingState.Idle)
            }
        }
        ProcessingOverlay(vm.state)
        when (vm.state) {
            is ProcessingState.Success -> ResultScreen(vm.state as ProcessingState.Success, { openFile(ctx, (vm.state as ProcessingState.Success).outputFiles.firstOrNull()) }, { vm.reset() })
            is ProcessingState.Error -> ErrorScreen(vm.state as ProcessingState.Error, { vm.execute(ctx) }, { vm.reset() })
            else -> {}
        }
    }
}

/* ===== EXTRACTION, SECURITY, OCR TOOL SCREENS ===== */

class ExtractTextViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle); private set
    var filePath by mutableStateOf<String?>(null)
    fun execute(ctx: Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "")
        try {
            val out = PdfEngine.pdfToText(ctx, filePath!!) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(listOf(out.absolutePath), ctx.getString(R.string.proc_extract_text_done))
        } catch (e: Exception) { state = ProcessingState.Error(e.localizedMessage ?: "Failed") }
    }
    fun reset() { state = ProcessingState.Idle; filePath = null }
}

@Composable
fun ExtractTextScreen(navController: NavController, onBack: () -> Unit) {
    val vm: ExtractTextViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val ctx = LocalContext.current
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())) {
            SectionHeader(stringResource(R.string.tool_extract_text))
            if (vm.filePath == null) SingleFilePicker(onFileSelected = { vm.filePath = it })
            else { Text(File(vm.filePath!!).name); Spacer(Modifier.height(24.dp)); ActionButton(stringResource(R.string.proc_convert_btn), { vm.execute(ctx) }, enabled = vm.state is ProcessingState.Idle) }
        }
        ProcessingOverlay(vm.state)
        when (vm.state) {
            is ProcessingState.Success -> ResultScreen(vm.state as ProcessingState.Success, { openFile(ctx, (vm.state as ProcessingState.Success).outputFiles.firstOrNull()) }, { vm.reset() })
            is ProcessingState.Error -> ErrorScreen(vm.state as ProcessingState.Error, { vm.execute(ctx) }, { vm.reset() })
            else -> {}
        }
    }
}

class ExtractImagesViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle); private set
    var filePath by mutableStateOf<String?>(null)
    fun execute(ctx: Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "")
        try {
            val files = PdfEngine.extractImages(ctx, filePath!!) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(files.map { it.absolutePath }, ctx.getString(R.string.proc_extract_images_done, files.size))
        } catch (e: Exception) { state = ProcessingState.Error(e.localizedMessage ?: "Failed") }
    }
    fun reset() { state = ProcessingState.Idle; filePath = null }
}

@Composable
fun ExtractImagesScreen(navController: NavController, onBack: () -> Unit) {
    val vm: ExtractImagesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val ctx = LocalContext.current
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())) {
            SectionHeader(stringResource(R.string.tool_extract_images))
            if (vm.filePath == null) SingleFilePicker(onFileSelected = { vm.filePath = it })
            else { Text(File(vm.filePath!!).name); Spacer(Modifier.height(24.dp)); ActionButton(stringResource(R.string.proc_convert_btn), { vm.execute(ctx) }, enabled = vm.state is ProcessingState.Idle) }
        }
        ProcessingOverlay(vm.state)
        when (vm.state) {
            is ProcessingState.Success -> ResultScreen(vm.state as ProcessingState.Success, { openFile(ctx, (vm.state as ProcessingState.Success).outputFiles.firstOrNull()) }, { vm.reset() })
            is ProcessingState.Error -> ErrorScreen(vm.state as ProcessingState.Error, { vm.execute(ctx) }, { vm.reset() })
            else -> {}
        }
    }
}

class ExtractTablesViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle); private set
    var filePath by mutableStateOf<String?>(null)
    fun execute(ctx: Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "")
        try {
            val out = PdfEngine.extractTables(ctx, filePath!!) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(listOf(out.absolutePath), ctx.getString(R.string.proc_extract_tables_done))
        } catch (e: Exception) { state = ProcessingState.Error(e.localizedMessage ?: "Failed") }
    }
    fun reset() { state = ProcessingState.Idle; filePath = null }
}

@Composable
fun ExtractTablesScreen(navController: NavController, onBack: () -> Unit) {
    val vm: ExtractTablesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val ctx = LocalContext.current
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())) {
            SectionHeader(stringResource(R.string.tool_extract_tables))
            if (vm.filePath == null) SingleFilePicker(onFileSelected = { vm.filePath = it })
            else { Text(File(vm.filePath!!).name); Spacer(Modifier.height(24.dp)); ActionButton(stringResource(R.string.proc_convert_btn), { vm.execute(ctx) }, enabled = vm.state is ProcessingState.Idle) }
        }
        ProcessingOverlay(vm.state)
        when (vm.state) {
            is ProcessingState.Success -> ResultScreen(vm.state as ProcessingState.Success, { openFile(ctx, (vm.state as ProcessingState.Success).outputFiles.firstOrNull()) }, { vm.reset() })
            is ProcessingState.Error -> ErrorScreen(vm.state as ProcessingState.Error, { vm.execute(ctx) }, { vm.reset() })
            else -> {}
        }
    }
}

class ProtectPdfViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle); private set
    var filePath by mutableStateOf<String?>(null); var password by mutableStateOf("")
    fun execute(ctx: Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "")
        try {
            val out = PdfEngine.protectPdf(ctx, filePath!!, password) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(listOf(out.absolutePath), ctx.getString(R.string.proc_protect_done))
        } catch (e: Exception) { state = ProcessingState.Error(e.localizedMessage ?: "Failed") }
    }
    fun reset() { state = ProcessingState.Idle; filePath = null; password = "" }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProtectPdfScreen(navController: NavController, onBack: () -> Unit) {
    val vm: ProtectPdfViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val ctx = LocalContext.current
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())) {
            SectionHeader(stringResource(R.string.tool_protect))
            if (vm.filePath == null) SingleFilePicker(onFileSelected = { vm.filePath = it })
            else {
                Text(File(vm.filePath!!).name); Spacer(Modifier.height(12.dp))
                PasswordInput(value = vm.password, onValueChange = { vm.password = it }, label = stringResource(R.string.password_label))
                Spacer(Modifier.height(24.dp))
                ActionButton(stringResource(R.string.proc_protect_btn), { vm.execute(ctx) }, enabled = vm.password.length >= 4 && vm.state is ProcessingState.Idle)
            }
        }
        ProcessingOverlay(vm.state)
        when (vm.state) {
            is ProcessingState.Success -> ResultScreen(vm.state as ProcessingState.Success, { openFile(ctx, (vm.state as ProcessingState.Success).outputFiles.firstOrNull()) }, { vm.reset() })
            is ProcessingState.Error -> ErrorScreen(vm.state as ProcessingState.Error, { vm.execute(ctx) }, { vm.reset() })
            else -> {}
        }
    }
}

class UnlockPdfViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle); private set
    var filePath by mutableStateOf<String?>(null); var password by mutableStateOf("")
    fun execute(ctx: Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "")
        try {
            val out = PdfEngine.unlockPdf(ctx, filePath!!, password) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(listOf(out.absolutePath), ctx.getString(R.string.proc_unlock_done))
        } catch (e: Exception) { state = ProcessingState.Error(e.localizedMessage ?: "Failed") }
    }
    fun reset() { state = ProcessingState.Idle; filePath = null; password = "" }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnlockPdfScreen(navController: NavController, onBack: () -> Unit) {
    val vm: UnlockPdfViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val ctx = LocalContext.current
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())) {
            SectionHeader(stringResource(R.string.tool_unlock))
            if (vm.filePath == null) SingleFilePicker(onFileSelected = { vm.filePath = it })
            else {
                Text(File(vm.filePath!!).name); Spacer(Modifier.height(12.dp))
                PasswordInput(value = vm.password, onValueChange = { vm.password = it }, label = stringResource(R.string.password_label))
                Spacer(Modifier.height(24.dp))
                ActionButton(stringResource(R.string.proc_unlock_btn), { vm.execute(ctx) }, enabled = vm.password.isNotBlank() && vm.state is ProcessingState.Idle)
            }
        }
        ProcessingOverlay(vm.state)
        when (vm.state) {
            is ProcessingState.Success -> ResultScreen(vm.state as ProcessingState.Success, { openFile(ctx, (vm.state as ProcessingState.Success).outputFiles.firstOrNull()) }, { vm.reset() })
            is ProcessingState.Error -> ErrorScreen(vm.state as ProcessingState.Error, { vm.execute(ctx) }, { vm.reset() })
            else -> {}
        }
    }
}

class WatermarkViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle); private set
    var filePath by mutableStateOf<String?>(null)
    var config by mutableStateOf(com.pdfpro.app.model.WatermarkConfig())
    fun execute(ctx: Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "")
        try {
            val out = PdfEngine.addWatermark(ctx, filePath!!, config) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(listOf(out.absolutePath), ctx.getString(R.string.proc_watermark_done))
        } catch (e: Exception) { state = ProcessingState.Error(e.localizedMessage ?: "Failed") }
    }
    fun reset() { state = ProcessingState.Idle; filePath = null; config = com.pdfpro.app.model.WatermarkConfig() }
}

@Composable
fun WatermarkScreen(navController: NavController, onBack: () -> Unit) {
    val vm: WatermarkViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val ctx = LocalContext.current
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { vm.config = vm.config.copy(type = com.pdfpro.app.model.WatermarkType.IMAGE, imagePath = it.toString()) }
    }
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())) {
            SectionHeader(stringResource(R.string.tool_watermark))
            if (vm.filePath == null) SingleFilePicker(onFileSelected = { vm.filePath = it })
            else {
                Text(File(vm.filePath!!).name); Spacer(Modifier.height(12.dp))
                WatermarkConfigPanel(config = vm.config, onConfigChange = { vm.config = it }, onPickImage = { imagePicker.launch("image/*") })
                Spacer(Modifier.height(24.dp))
                ActionButton(stringResource(R.string.proc_watermark_btn), { vm.execute(ctx) }, enabled = vm.state is ProcessingState.Idle)
            }
        }
        ProcessingOverlay(vm.state)
        when (vm.state) {
            is ProcessingState.Success -> ResultScreen(vm.state as ProcessingState.Success, { openFile(ctx, (vm.state as ProcessingState.Success).outputFiles.firstOrNull()) }, { vm.reset() })
            is ProcessingState.Error -> ErrorScreen(vm.state as ProcessingState.Error, { vm.execute(ctx) }, { vm.reset() })
            else -> {}
        }
    }
}

class OcrViewModel : ViewModel() {
    var state by mutableStateOf<ProcessingState>(ProcessingState.Idle); private set
    var filePath by mutableStateOf<String?>(null); var isPdf by mutableStateOf(true)
    fun execute(ctx: Context) = viewModelScope.launch {
        state = ProcessingState.Working(0f, "")
        try {
            val out = com.pdfpro.app.engine.OcrEngine.recognizeAndSaveTxt(ctx, filePath!!, isPdf) { p, m -> state = ProcessingState.Working(p, m) }
            state = ProcessingState.Success(listOf(out.absolutePath), ctx.getString(R.string.proc_ocr_done))
        } catch (e: Exception) { state = ProcessingState.Error(e.localizedMessage ?: "Failed") }
    }
    fun reset() { state = ProcessingState.Idle; filePath = null; isPdf = true }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OcrScreen(navController: NavController, onBack: () -> Unit) {
    val vm: OcrViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val ctx = LocalContext.current
    var showModeExpanded by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())) {
            SectionHeader(stringResource(R.string.tool_ocr))
            ExposedDropdownMenuBox(expanded = showModeExpanded, onExpandedChange = { showModeExpanded = it }) {
                OutlinedTextField(value = if (vm.isPdf) stringResource(R.string.ocr_pdf) else stringResource(R.string.ocr_image), onValueChange = {}, readOnly = true, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(showModeExpanded) }, modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                ExposedDropdownMenu(expanded = showModeExpanded, onDismissRequest = { showModeExpanded = false }) {
                    DropdownMenuItem({ Text(stringResource(R.string.ocr_pdf)) }, onClick = { vm.isPdf = true; showModeExpanded = false })
                    DropdownMenuItem({ Text(stringResource(R.string.ocr_image)) }, onClick = { vm.isPdf = false; showModeExpanded = false })
                }
            }
            Spacer(Modifier.height(12.dp))
            SingleFilePicker(onFileSelected = { vm.filePath = it }, mimeType = if (vm.isPdf) "application/pdf" else "image/*")
            if (vm.filePath != null) {
                Spacer(Modifier.height(24.dp))
                ActionButton(stringResource(R.string.proc_ocr_btn), { vm.execute(ctx) }, enabled = vm.state is ProcessingState.Idle)
            }
        }
        ProcessingOverlay(vm.state)
        when (vm.state) {
            is ProcessingState.Success -> ResultScreen(vm.state as ProcessingState.Success, { openFile(ctx, (vm.state as ProcessingState.Success).outputFiles.firstOrNull()) }, { vm.reset() })
            is ProcessingState.Error -> ErrorScreen(vm.state as ProcessingState.Error, { vm.execute(ctx) }, { vm.reset() })
            else -> {}
        }
    }
}



