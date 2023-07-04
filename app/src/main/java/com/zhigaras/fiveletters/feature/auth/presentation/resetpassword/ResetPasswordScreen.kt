package com.zhigaras.fiveletters.feature.auth.presentation.resetpassword

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.core.presentation.compose.ButtonWithProgressBar
import com.zhigaras.fiveletters.core.presentation.compose.CommonTextButton
import com.zhigaras.fiveletters.core.presentation.compose.ErrorEffect
import com.zhigaras.fiveletters.feature.auth.domain.model.ProcessResult
import com.zhigaras.fiveletters.feature.auth.presentation.EmailInput

@Composable
fun ResetPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: ResetPasswordViewModel,
    navigateToSignIn: (String) -> Unit,
    showSnackBar: suspend (String) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.getState().collectAsStateWithLifecycle()
    BackHandler {
        navigateToSignIn(state.email.value)
    }
    ErrorEffect(event = state.errorEvent, onConsumed = { viewModel.onConsumeError() }) {
        showSnackBar(state.errorEvent.message.asString(context))
    }
    AnimatedContent(targetState = state.processResult is ProcessResult.Success) {
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (it) {
                Text(
                    modifier = modifier,
                    text = stringResource(
                        id = R.string.successful_resetting_password,
                        state.email.value
                    ),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
                CommonTextButton(
                    modifier = modifier,
                    text = stringResource(id = R.string.back),
                    onClick = { navigateToSignIn(state.email.value) }
                )
            } else {
                Text(
                    modifier = modifier,
                    text = stringResource(R.string.enter_email_for_password_reset),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
                EmailInput(
                    modifier = modifier,
                    state = state.email,
                    imeAction = ImeAction.Done,
                    onTextChange = { viewModel.onEmailChanged(it) },
                    onClear = { viewModel.clearEmail() },
                    onDone = { viewModel.resetPassword() }
                )
                ButtonWithProgressBar(
                    modifier = modifier,
                    text = stringResource(R.string.send_reset_instruction),
                    enabled = state.isCompletelyFilled,
                    isLoading = state.isLoading,
                    onClick = { viewModel.resetPassword() }
                )
            }
        }
    }
}