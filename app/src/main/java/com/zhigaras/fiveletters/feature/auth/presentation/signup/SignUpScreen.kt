package com.zhigaras.fiveletters.feature.auth.presentation.signup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.core.presentation.compose.CircleProgressBar
import com.zhigaras.fiveletters.core.presentation.compose.ErrorEffect
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldType
import com.zhigaras.fiveletters.feature.auth.domain.model.SignInResult
import com.zhigaras.fiveletters.feature.auth.presentation.EmailInput
import com.zhigaras.fiveletters.feature.auth.presentation.PasswordInput
import com.zhigaras.fiveletters.feature.auth.presentation.SharedAuthScreen
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
    
    SharedAuthScreen {
        EmailInput(
            modifier = it,
            state = state.email,
            onTextChange = { viewModel.onFieldChanged(InputFieldType.EMAIL, it) },
            onClear = { viewModel.clearEmail() }
        )
        PasswordInput(
            modifier = it,
            state = state.password,
            hint = stringResource(R.string.password),
            onTextChange = { viewModel.onFieldChanged(InputFieldType.PASSWORD, it) }
        )
        PasswordInput(
            modifier = it,
            state = state.passwordRepeat,
            hint = stringResource(R.string.repeat_password),
            isLastInColumn = true,
            onTextChange = { viewModel.onFieldChanged(InputFieldType.REPEAT_PASSWORD, it) },
            onDone = { viewModel.signUpWithEmailAndPassword() }
        )
        CommonButton(
            modifier = it,
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