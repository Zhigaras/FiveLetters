package com.zhigaras.fiveletters.feature.auth.presentation.signin

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.identity.Identity
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.core.presentation.compose.CircleProgressBar
import com.zhigaras.fiveletters.core.presentation.compose.CommonTextButton
import com.zhigaras.fiveletters.core.presentation.compose.DoublePressBackHandler
import com.zhigaras.fiveletters.core.presentation.compose.ErrorEffect
import com.zhigaras.fiveletters.core.presentation.compose.theme.screenEdgePadding
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldType
import com.zhigaras.fiveletters.feature.auth.domain.model.ProcessResult
import com.zhigaras.fiveletters.feature.auth.presentation.AuthDivider
import com.zhigaras.fiveletters.feature.auth.presentation.EmailInput
import com.zhigaras.fiveletters.feature.auth.presentation.PasswordInput
import com.zhigaras.fiveletters.feature.auth.presentation.SignInWithGoogleButton

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel,
    navigateToSignUpScreen: () -> Unit,
    navigateToMenu: () -> Unit,
    navigateToResetPassword: () -> Unit,
    onFinish: () -> Unit,
    showSnackBar: suspend (String) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.getState().collectAsStateWithLifecycle()
    val signInClient = Identity.getSignInClient(context)
    val signWithGoogleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) {
        viewModel.changeGoogleIdToCredential(it, signInClient)
    }
    
    DoublePressBackHandler(onFinish = onFinish)
    
    ErrorEffect(event = state.errorEvent, onConsumed = { viewModel.onConsumeError() }) {
        showSnackBar(state.errorEvent.message.asString(context))
    }
    if (state.processResult is ProcessResult.Success) navigateToMenu()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailInput(
            modifier = modifier.padding(top = screenEdgePadding),
            state = state.email,
            onTextChange = { viewModel.onFieldChanged(InputFieldType.EMAIL, it) },
            onClear = { viewModel.clearEmail() }
        )
        PasswordInput(
            modifier = modifier,
            state = state.password,
            hint = stringResource(R.string.password),
            isLastInColumn = true,
            onTextChange = { viewModel.onFieldChanged(InputFieldType.PASSWORD, it) },
            onDone = { viewModel.signInWithEmailAndPassword() }
        )
        Box(
            modifier = modifier,
            contentAlignment = Alignment.CenterEnd
        ) {
            TextButton(onClick = navigateToResetPassword) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    textDecoration = TextDecoration.Underline
                )
            }
        }
        CommonTextButton(
            modifier = modifier,
            text = stringResource(R.string.sign_in),
            enabled = state.isCompletelyFilled,
            onClick = { viewModel.signInWithEmailAndPassword() }
        )
        AuthDivider(modifier = modifier, textId = R.string.or)
        SignInWithGoogleButton(
            modifier = modifier,
            onClick = { viewModel.signInWithGoogle(signWithGoogleLauncher, signInClient) }
        )
        AuthDivider(modifier = modifier, textId = R.string.or)
        CommonTextButton(
            modifier = modifier,
            text = stringResource(id = R.string.log_in_as_a_guest),
            onClick = { viewModel.signInAnonymously() }
        )
        AuthDivider(modifier = modifier, textId = R.string.still_not_registered)
        CommonTextButton(
            modifier.padding(bottom = screenEdgePadding),
            text = stringResource(id = R.string.sign_up),
            onClick = navigateToSignUpScreen
        )
    }
    CircleProgressBar(
        modifier = Modifier.fillMaxSize(),
        state = state.isLoading
    )
}