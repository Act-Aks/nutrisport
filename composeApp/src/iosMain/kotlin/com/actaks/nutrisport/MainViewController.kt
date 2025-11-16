package com.actaks.nutrisport

import androidx.compose.ui.window.ComposeUIViewController
import com.actaks.nutrisport.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }