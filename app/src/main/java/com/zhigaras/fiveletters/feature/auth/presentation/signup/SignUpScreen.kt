package com.zhigaras.fiveletters.feature.auth.presentation.signup

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.zhigaras.fiveletters.core.presentation.compose.CommonTextButton
import com.zhigaras.fiveletters.core.presentation.compose.ErrorEffect
import com.zhigaras.fiveletters.core.presentation.compose.theme.screenEdgePadding
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldType
import com.zhigaras.fiveletters.feature.auth.domain.model.ProcessResult
import com.zhigaras.fiveletters.feature.auth.presentation.EmailInput
import com.zhigaras.fiveletters.feature.auth.presentation.PasswordInput

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel,
    navigateToMenu: () -> Unit,
    navigateToSignIn: () -> Unit,
    showSnackBar: suspend (String) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.getState().collectAsStateWithLifecycle()
    
    BackHandler {
        navigateToSignIn()
    }
    
    ErrorEffect(event = state.errorEvent, onConsumed = { viewModel.onConsumeError() }) {
        showSnackBar(it.asString(context))
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
            modifier = modifier,
            state = state.email,
            onTextChange = { viewModel.onFieldChanged(InputFieldType.EMAIL, it) },
            onClear = { viewModel.clearEmail() }
        )
        PasswordInput(
            modifier = modifier,
            state = state.password,
            hint = stringResource(R.string.password),
            onTextChange = { viewModel.onFieldChanged(InputFieldType.PASSWORD, it) }
        )
        PasswordInput(
            modifier = modifier,
            state = state.passwordRepeat,
            hint = stringResource(R.string.repeat_password),
            isLastInColumn = true,
            onTextChange = { viewModel.onFieldChanged(InputFieldType.REPEAT_PASSWORD, it) },
            onDone = { viewModel.signUpWithEmailAndPassword() }
        )
        CommonTextButton(
            modifier = modifier.padding(bottom = screenEdgePadding),
            text = stringResource(id = R.string.sign_up),
            enabled = state.isCompletelyFilled,
            onClick = { viewModel.signUpWithEmailAndPassword() }
        )
    }

// TODO remove if not needed
    CircleProgressBar(
        modifier = Modifier.fillMaxSize(),
        state = state.isLoading
    )
}