package com.zhigaras.fiveletters.presentation.compose.ui.screens.auth

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.AuthViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onFinish: () -> Unit
) {
    val authState by viewModel.authStateFlow.collectAsStateWithLifecycle()
    var authPage by rememberSaveable { mutableStateOf(AuthPage.START) }
    
    BackHandler(enabled = true) {
        if (authPage == AuthPage.START) onFinish()
        else authPage = AuthPage.START
    }
    
    Box(
        modifier = Modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(targetState = authPage) {
            when (it) {
                AuthPage.START -> AuthStartPage(
                    onSignInClick = { authPage = AuthPage.SIGN_IN },
                    onSignUpClick = { authPage = AuthPage.SIGN_UP }
                )
                
                else -> AuthSignInUpPage(
                    authPage = it,
                    authState = authState,
                    onEmailChanged = { viewModel.onEmailChanged(it) },
                    onPasswordChanged = { viewModel.onPasswordChanged(it) },
                    onButtonClick = {}
                )
            }
        }
    }
}