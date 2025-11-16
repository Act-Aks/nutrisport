package com.actaks.nutrisport.auth

import ContentWithMessageBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.actaks.nutrisport.auth.components.GoogleButton
import com.actaks.nutrisport.shared.Alpha
import com.actaks.nutrisport.shared.BebasNeueFontFamily
import com.actaks.nutrisport.shared.FontSize
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

@Composable
fun AuthScreen(
    navigateToHome: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val viewModel = koinViewModel<AuthViewModel>()
    val messageBarState = rememberMessageBarState()
    var loadingState by remember { mutableStateOf(false) }

    Scaffold { padding ->
        ContentWithMessageBar(
            messageBarState = messageBarState,
            errorMaxLines = 2,
            modifier = Modifier
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding(),
                ),
            contentBackgroundColor = MaterialTheme.colorScheme.background,
            errorContainerColor = MaterialTheme.colorScheme.errorContainer,
            errorContentColor = MaterialTheme.colorScheme.onErrorContainer,
            successContainerColor = MaterialTheme.colorScheme.primaryContainer,
            successContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "NUTRISPORT",
                        textAlign = TextAlign.Center,
                        fontFamily = BebasNeueFontFamily(),
                        fontSize = FontSize.EXTRA_LARGE,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Sign in to continue",
                        textAlign = TextAlign.Center,
                        fontSize = FontSize.EXTRA_REGULAR,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(Alpha.HALF)
                    )
                }
                GoogleButtonUiContainerFirebase(
                    linkAccount = false,
                    onResult = { result ->
                        result
                            .onSuccess { user ->
                                viewModel.createCustomer(
                                    user = user,
                                    onSuccess = {
                                        scope.launch {
                                            messageBarState.addSuccess("Authentication successful")
                                            delay(2000)
                                            navigateToHome()
                                        }
                                    },
                                    onError = { error ->
                                        messageBarState.addError(error)
                                    }
                                )
                                loadingState = false
                            }
                            .onFailure { error ->
                                if (error.message?.contains("A network error") == true) {
                                    messageBarState.addError("No internet connection")
                                } else if (error.message?.contains("Idtoken is null") == true) {
                                    messageBarState.addError("Sign in canceled")
                                } else {
                                    messageBarState.addError(
                                        error.message ?: "Something went wrong"
                                    )
                                }
                                loadingState = false
                            }
                    }
                ) {
                    GoogleButton(
                        loading = loadingState,
                        onClick = {
                            loadingState = true
                            this@GoogleButtonUiContainerFirebase.onClick()
                        },
                    )
                }

            }
        }
    }
}