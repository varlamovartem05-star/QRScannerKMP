package com.example.qrscannermulti

import androidx.compose.runtime.Composable

@Composable
expect fun QrScannerView(onResult: (String) -> Unit)