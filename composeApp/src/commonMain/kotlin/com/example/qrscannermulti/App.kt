package com.example.qrscannermulti // УБЕДИТЕСЬ, ЧТО ЭТО ВАШ ПАКЕТ

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun App() {
    MaterialTheme {
        var scannedText by remember { mutableStateOf("Наведите камеру на код") }

        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            // Тот самый текст, который пропал
            Text(text = scannedText, modifier = Modifier.padding(16.dp))

            // Вызов нашего сканера
            QrScannerView(onResult = { result ->
                scannedText = result
            })
        }
    }
}