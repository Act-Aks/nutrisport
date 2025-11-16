package com.actaks.nutrisport

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.actaks.nutirsport.navigation.NavGraph
import com.actaks.nutrisport.shared.navigation.Screen
import com.actaks.nutrisport.data.domain.CustomerRepository
import com.actaks.nutrisport.shared.Constants
import com.actaks.nutrisport.shared.NutriSportTheme
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun App() {
    var isGoogleAuthInitialized by remember { mutableStateOf(false) }
    val customerRepository = koinInject<CustomerRepository>()
    val isUserAuthenticated = remember { customerRepository.getCurrentUserId() != null }
    val startDestination = remember { if (isUserAuthenticated) Screen.HomeGraph else Screen.Auth }

    LaunchedEffect(Unit) {
        GoogleAuthProvider.create(
            credentials = GoogleAuthCredentials(serverId = Constants.GOOGLE_WEB_CLIENT_ID)
        )
        isGoogleAuthInitialized = true
    }

    AnimatedVisibility(
        visible = isGoogleAuthInitialized,
        modifier = Modifier.fillMaxSize()
    ) {
        NavGraph(
            startDestination = startDestination
        )
    }
}

@Preview
@Composable
private fun AppPreview() {
    NutriSportTheme {
        App()
    }
}