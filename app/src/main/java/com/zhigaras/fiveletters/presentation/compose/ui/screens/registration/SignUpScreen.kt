package com.zhigaras.fiveletters.presentation.compose.ui.screens.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.domain.auth.InputFieldType
import com.zhigaras.fiveletters.presentation.compose.ui.screens.auth.AuthDivider
import com.zhigaras.fiveletters.presentation.compose.ui.screens.auth.InputTextField
import com.zhigaras.fiveletters.presentation.compose.ui.screens.auth.PasswordInput
import com.zhigaras.fiveletters.presentation.compose.ui.screens.auth.RegisterWithGoogleButton
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.CommonButton
import com.zhigaras.fiveletters.presentation.compose.ui.theme.playScreenMaxWidth

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel
) {
    val state by viewModel.getState().collectAsStateWithLifecycle()
    
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
            InputTextField(
                modifier = maxWidthModifier,
                textState = state.email,
                hint = stringResource(R.string.email_hint),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                onTextChange = { viewModel.onFieldChanged(InputFieldType.EMAIL, it) }
            )
            PasswordInput(
                modifier = maxWidthModifier,
                textState = state.password,
                hint = stringResource(R.string.password),
                onTextChange = { viewModel.onFieldChanged(InputFieldType.PASSWORD, it) }
            )
            PasswordInput(
                modifier = maxWidthModifier,
                textState = state.passwordRepeat,
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
            AuthDivider()
            RegisterWithGoogleButton(
                modifier = maxWidthModifier,
                onClick = { viewModel.signUpWithGoogle() }
            )
        }
    }
}