package com.zhigaras.fiveletters.presentation.compose.ui.screens.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.model.auth.InputFieldType
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.CommonButton
import com.zhigaras.fiveletters.presentation.compose.ui.theme.playScreenMaxWidth

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    toSignUpScreen: () -> Unit
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
            EmailInput(
                modifier = maxWidthModifier,
                state = state.email,
                hint = stringResource(R.string.email_hint),
                onTextChange = { viewModel.onFieldChanged(InputFieldType.EMAIL, it) }
            )
            PasswordInput(
                modifier = maxWidthModifier,
                state = state.password,
                hint = stringResource(R.string.password),
                isLastInColumn = true,
                onTextChange = { viewModel.onFieldChanged(InputFieldType.PASSWORD, it) },
                onDone = { viewModel.signIn() }
            )
            Box(
                modifier = maxWidthModifier,
                contentAlignment = Alignment.CenterEnd
            ) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = stringResource(R.string.forgot_password),
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
            CommonButton(
                modifier = maxWidthModifier,
                text = stringResource(R.string.sign_in),
                textStyle = MaterialTheme.typography.titleLarge,
                enabled = state.isCompletelyFilled,
                onClick = { viewModel.signIn() }
            )
            AuthDivider()
            CommonButton(
                modifier = maxWidthModifier,
                text = stringResource(id = R.string.sign_up),
                textStyle = MaterialTheme.typography.titleLarge,
                onClick = toSignUpScreen
            )
        }
    }
}