package com.zhigaras.fiveletters.feature.auth.presentation.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.core.presentation.compose.CircleProgressBar
import com.zhigaras.fiveletters.core.presentation.compose.ErrorEffect
import com.zhigaras.fiveletters.core.presentation.compose.theme.playScreenMaxWidth
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldType
import com.zhigaras.fiveletters.feature.auth.domain.model.SignInResult
import com.zhigaras.fiveletters.feature.auth.presentation.EmailInput
import com.zhigaras.fiveletters.feature.auth.presentation.PasswordInput
import com.zhigaras.fiveletters.feature.menu.presentation.CommonButton

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    navigateToMenu: () -> Unit,
    showSnackBar: suspend (String) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.getState().collectAsStateWithLifecycle()
    
    ErrorEffect(event = state.errorEvent, onConsumed = { viewModel.onConsumeError() }) {
        showSnackBar(it.asString(context))
    }
    
    if (state.signInResult is SignInResult.Success) navigateToMenu()
    
    Box(
        modifier = Modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(max = playScreenMaxWidth)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val maxWidthModifier = Modifier.fillMaxWidth()
            EmailInput(
                modifier = maxWidthModifier,
                state = state.email,
                hint = stringResource(R.string.email_hint),
                onTextChange = { viewModel.onFieldChanged(InputFieldType.EMAIL, it) },
                onClear = { viewModel.clearEmail() }
            )
            PasswordInput(
                modifier = maxWidthModifier,
                state = state.password,
                hint = stringResource(R.string.password),
                onTextChange = { viewModel.onFieldChanged(InputFieldType.PASSWORD, it) }
            )
            PasswordInput(
                modifier = maxWidthModifier,
                state = state.passwordRepeat,
                hint = stringResource(R.string.repeat_password),
                isLastInColumn = true,
                onTextChange = { viewModel.onFieldChanged(InputFieldType.REPEAT_PASSWORD, it) },
                onDone = { viewModel.signUpWithEmailAndPassword() }
            )
            CommonButton(
                modifier = maxWidthModifier,
                text = stringResource(id = R.string.sign_up),
                textStyle = MaterialTheme.typography.titleLarge,
                enabled = state.isCompletelyFilled,
                onClick = { viewModel.signUpWithEmailAndPassword() }
            )
        }
    }
    // TODO remove if not needed
    CircleProgressBar(
        modifier = Modifier.fillMaxSize(),
        state = state.isLoading
    )
}