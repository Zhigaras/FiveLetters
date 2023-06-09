package com.zhigaras.fiveletters.feature.auth.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zhigaras.fiveletters.core.presentation.compose.theme.playScreenMaxWidth
import com.zhigaras.fiveletters.core.presentation.compose.theme.screenEdgePadding
import com.zhigaras.fiveletters.feature.auth.domain.model.AuthScreenPage
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldType
import com.zhigaras.fiveletters.feature.auth.presentation.resetpassword.ResetPasswordScreen
import com.zhigaras.fiveletters.feature.auth.presentation.resetpassword.ResetPasswordViewModel
import com.zhigaras.fiveletters.feature.auth.presentation.signin.SignInScreen
import com.zhigaras.fiveletters.feature.auth.presentation.signin.SignInViewModel
import com.zhigaras.fiveletters.feature.auth.presentation.signup.SignUpScreen
import com.zhigaras.fiveletters.feature.auth.presentation.signup.SignUpViewModel

@Composable
fun SharedAuthScreen(
    signInViewModel: SignInViewModel,
    signUpViewModel: SignUpViewModel,
    resetPasswordViewModel: ResetPasswordViewModel,
    navigateToMenu: () -> Unit,
    onFinish: () -> Unit,
    showSnackBar: suspend (String) -> Unit
) {
    var authScreenPage by rememberSaveable { mutableStateOf(AuthScreenPage.SIGN_IN) }
    
    Box(
        modifier = Modifier.padding(horizontal = screenEdgePadding),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = authScreenPage,
        ) {
            val maxWidthModifier = Modifier.width(playScreenMaxWidth)
            
            when (it) {
                AuthScreenPage.SIGN_IN -> SignInScreen(
                    modifier = maxWidthModifier,
                    viewModel = signInViewModel,
                    navigateToSignUpScreen = { email ->
                        authScreenPage = AuthScreenPage.SIGN_UP
                        signUpViewModel.onFieldChanged(InputFieldType.EMAIL, email)
                    },
                    navigateToMenu = navigateToMenu,
                    navigateToResetPassword = { email ->
                        authScreenPage = AuthScreenPage.RESET_PASSWORD
                        resetPasswordViewModel.onEmailChanged(email)
                    },
                    onFinish = onFinish,
                    showSnackBar = showSnackBar,
                )
                
                AuthScreenPage.SIGN_UP -> SignUpScreen(
                    modifier = maxWidthModifier,
                    viewModel = signUpViewModel,
                    navigateToMenu = navigateToMenu,
                    navigateToSignIn = { email ->
                        authScreenPage = AuthScreenPage.SIGN_IN
                        signInViewModel.onFieldChanged(InputFieldType.EMAIL, email)
                    },
                    showSnackBar = showSnackBar
                )
                
                AuthScreenPage.RESET_PASSWORD -> ResetPasswordScreen(
                    modifier = maxWidthModifier,
                    viewModel = resetPasswordViewModel,
                    navigateToSignIn = { email ->
                        authScreenPage = AuthScreenPage.SIGN_IN
                        signInViewModel.onFieldChanged(InputFieldType.EMAIL, email)
                    },
                    showSnackBar = showSnackBar
                )
            }
        }
    }
}