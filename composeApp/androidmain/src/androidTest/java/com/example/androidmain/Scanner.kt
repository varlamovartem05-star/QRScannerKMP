package com.example.qrscannermulti

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text

@Composable
actual fun QrScannerView(onResult: (String) -> Unit) {
    Text("Здесь будет камера Android")
}