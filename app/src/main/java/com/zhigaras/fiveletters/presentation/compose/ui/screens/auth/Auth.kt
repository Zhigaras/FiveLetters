package com.zhigaras.fiveletters.presentation.compose.ui.screens.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.CommonButton
import com.zhigaras.fiveletters.presentation.compose.ui.theme.white
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.AuthViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel
) {
    val emailState by viewModel.emailFlow.collectAsStateWithLifecycle()
    val passwordState by viewModel.passwordFlow.collectAsStateWithLifecycle()
    var authPage by remember { mutableStateOf(AuthPage.START) }
    AnimatedContent(targetState = authPage) {
        when (it) {
            AuthPage.START -> AuthStart(
                onSignInClick = { authPage = AuthPage.SIGN_IN },
                onSignUpClick = { authPage = AuthPage.SIGN_UP }
            )
            
            AuthPage.SIGN_IN -> AuthSignIn(
                emailState = emailState,
                passwordState = passwordState,
                onEmailChanged = { viewModel.onNameChanged(it) },
                onPasswordChanged = { viewModel.onPasswordChanged(it) },
                onSignIn = {}
            )
            
            AuthPage.SIGN_UP -> AuthSignUp()
        }
    }
}

@Composable
fun AuthStart(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        CommonButton(
            text = stringResource(id = R.string.sign_in),
            textStyle = MaterialTheme.typography.titleLarge,
            onClick = { onSignInClick() }
        )
        CommonButton(
            text = stringResource(id = R.string.sign_up),
            textStyle = MaterialTheme.typography.titleLarge,
            onClick = { onSignUpClick() }
        )
    }
}

@Composable
fun AuthSignIn(
    emailState: String,
    passwordState: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignIn: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputTextField(
            textState = emailState,
            hint = stringResource(R.string.email_hint),
            onTextChange = { onEmailChanged(it) }
        )
        InputTextField(
            textState = passwordState,
            hint = stringResource(R.string.password),
            onTextChange = { onPasswordChanged(it) }
        )
        CommonButton(
            text = stringResource(id = R.string.sign_in),
            textStyle = MaterialTheme.typography.titleLarge,
            onClick = { onSignIn() }
        )
    }
}

@Composable
fun AuthSignUp() {

}

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    textState: String,
    hint: String,
    onTextChange: (String) -> Unit,
//    onNameConfirm: () -> Unit
) {
    val textFieldTextStyle = MaterialTheme.typography.headlineMedium
    OutlinedTextField(
        value = textState,
        onValueChange = { onTextChange(it) },
        textStyle = textFieldTextStyle,
        singleLine = true,
        shape = ShapeDefaults.Medium,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Done
        ),
//        keyboardActions = KeyboardActions(
//            onDone = { onNameConfirm() }
//        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            focusedContainerColor = white,
            unfocusedContainerColor = white,
            disabledContainerColor = white,
            cursorColor = MaterialTheme.colorScheme.secondary,
            focusedBorderColor = MaterialTheme.colorScheme.secondary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        ),
        placeholder = {
            Text(
                text = hint,
                style = textFieldTextStyle
            )
        },
        modifier = modifier.padding(vertical = 32.dp, horizontal = 16.dp)
    )
}
