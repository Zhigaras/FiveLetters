package com.zhigaras.fiveletters.feature.auth.presentation.signin

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.identity.Identity
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.core.presentation.compose.CircleProgressBar
import com.zhigaras.fiveletters.core.presentation.compose.ErrorEffect
import com.zhigaras.fiveletters.core.presentation.compose.theme.screenEdgeDimen
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldType
import com.zhigaras.fiveletters.feature.auth.domain.model.SignInResult
import com.zhigaras.fiveletters.feature.auth.presentation.AuthDivider
import com.zhigaras.fiveletters.feature.auth.presentation.EmailInput
import com.zhigaras.fiveletters.feature.auth.presentation.PasswordInput
import com.zhigaras.fiveletters.feature.auth.presentation.SharedAuthScreen
import com.zhigaras.fiveletters.feature.auth.presentation.SignInWithGoogleButton
import com.zhigaras.fiveletters.feature.menu.presentation.CommonButton

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    showSnackBar: suspend (String) -> Unit,
    navigateToSignUpScreen: () -> Unit,
    navigateToMenu: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.getState().collectAsStateWithLifecycle()
    var showResetPasswordDialog by rememberSaveable { mutableStateOf(false) }
    val signInClient = Identity.getSignInClient(context)
    val signWithGoogleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) {
        viewModel.changeGoogleIdToCredential(it, signInClient)
    }
    ErrorEffect(event = state.errorEvent, onConsumed = { viewModel.onConsumeError() }) {
        showSnackBar(state.errorEvent.message.asString(context))
    }
    if (state.signInResult is SignInResult.Success) navigateToMenu()
    
    if (showResetPasswordDialog)
        ResetPasswordDialog(
            emailState = state.email,
            onEmailChanged = { viewModel.onFieldChanged(InputFieldType.EMAIL, it) },
            onClear = { viewModel.clearEmail() },
            onDismiss = { showResetPasswordDialog = false },
            onConfirmClick = { viewModel.resetPassword() }
        )
    
    SharedAuthScreen {
        EmailInput(
            modifier = it.padding(top = screenEdgeDimen),
            state = state.email,
            onTextChange = { viewModel.onFieldChanged(InputFieldType.EMAIL, it) },
            onClear = { viewModel.clearEmail() }
        )
        PasswordInput(
            modifier = it,
            state = state.password,
            hint = stringResource(R.string.password),
            isLastInColumn = true,
            onTextChange = { viewModel.onFieldChanged(InputFieldType.PASSWORD, it) },
            onDone = { viewModel.signInWithEmailAndPassword() }
        )
        Box(
            modifier = it,
            contentAlignment = Alignment.CenterEnd
        ) {
            TextButton(onClick = { showResetPasswordDialog = true }) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    textDecoration = TextDecoration.Underline
                )
            }
        }
        CommonButton(
            modifier = it,
            text = stringResource(R.string.sign_in),
            enabled = state.isCompletelyFilled,
            onClick = { viewModel.signInWithEmailAndPassword() }
        )
        AuthDivider(modifier = it, textId = R.string.or)
        SignInWithGoogleButton(
            modifier = it,
            onClick = { viewModel.signInWithGoogle(signWithGoogleLauncher, signInClient) }
        )
        AuthDivider(modifier = it, textId = R.string.or)
        CommonButton(
            modifier = it,
            text = stringResource(id = R.string.log_in_as_a_guest),
            onClick = { viewModel.signInAnonymously() }
        )
        AuthDivider(modifier = it, textId = R.string.still_not_registered)
        CommonButton(
            modifier = it.padding(bottom = screenEdgeDimen),
            text = stringResource(id = R.string.sign_up),
            onClick = navigateToSignUpScreen
        )
    }
    CircleProgressBar(
        modifier = Modifier.fillMaxSize(),
        state = state.isLoading
    )
}