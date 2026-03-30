import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    // Создаем таргеты для iOS
    iosArm64()
    iosSimulatorArm64()

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            freeCompilerArgs += listOf("-Xoverride-kanary-ios-min-target=12.0")
        }
    }

    // Включаем автоматическое создание иерархии папок (включая iosMain)
    applyDefaultHierarchyTemplate()

    sourceSets {
        // Общий код для всех платформ
        commonMain.dependencies {
            // Чтобы убрать ворчалки Gradle, пропишем библиотеки напрямую
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
        }

        // Код только для Android
        androidMain.dependencies {
            val cameraVersion = "1.3.0"
            implementation("androidx.camera:camera-core:$cameraVersion")
            implementation("androidx.camera:camera-camera2:$cameraVersion")
            implementation("androidx.camera:camera-lifecycle:$cameraVersion")
            implementation("androidx.camera:camera-view:$cameraVersion")
            implementation("com.google.mlkit:barcode-scanning:17.2.0")
            implementation("androidx.compose.ui:ui:1.6.0")
            implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.0")
        }

        // Код только для iOS (теперь ошибки не будет!)
        iosMain.dependencies {
            // Здесь пусто, но блок должен существовать
        }
    }
}

android {
    namespace = "com.example.qrscannermulti"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.qrscannermulti"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}

