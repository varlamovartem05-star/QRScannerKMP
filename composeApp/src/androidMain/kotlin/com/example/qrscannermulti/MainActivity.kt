package com.example.qrscannermulti // проверьте свой пакет!

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Создаем лаунчер для запроса
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Если разрешили — перерисовываем экран
                setContent { App() }
            }
        }

        // 2. Проверяем, есть ли разрешение прямо сейчас
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                setContent { App() }
            }
            else -> {
                // 3. Если нет — запрашиваем!
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
}