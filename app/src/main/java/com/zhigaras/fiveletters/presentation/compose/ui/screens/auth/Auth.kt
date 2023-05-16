package com.zhigaras.fiveletters.presentation.compose.ui.screens.auth

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.CommonButton
import com.zhigaras.fiveletters.presentation.compose.ui.theme.playScreenMaxWidth
import com.zhigaras.fiveletters.presentation.compose.ui.theme.white
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.AuthViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onFinish: () -> Unit
) {
    val emailState by viewModel.emailFlow.collectAsStateWithLifecycle()
    val passwordState by viewModel.passwordFlow.collectAsStateWithLifecycle()
    var authPage by rememberSaveable { mutableStateOf(AuthPage.START) }
    
    BackHandler(enabled = true) {
        if (authPage == AuthPage.START) onFinish()
        else authPage = AuthPage.START
    }
    
    Box(contentAlignment = Alignment.Center) {
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
                    onButtonClick = {}
                )
                
                AuthPage.SIGN_UP -> AuthSignUp()
            }
        }
    }
}

@Composable
fun AuthStart(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxHeight()
            .widthIn(max = playScreenMaxWidth),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        CommonButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.sign_in),
            textStyle = MaterialTheme.typography.titleLarge,
            onClick = { onSignInClick() }
        )
        CommonButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
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
    onButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxHeight()
            .widthIn(max = playScreenMaxWidth),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            textState = emailState,
            hint = stringResource(R.string.email_hint),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            onTextChange = { onEmailChanged(it) }
        )
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            textState = passwordState,
            hint = stringResource(R.string.password),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            onTextChange = { onPasswordChanged(it) }
        )
        CommonButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            text = stringResource(id = R.string.sign_in),
            textStyle = MaterialTheme.typography.titleLarge,
            onClick = { onButtonClick() }
        )
    }
}

@Composable
fun AuthSignUp() {
    Column() {
    
    }
}

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    textState: String,
    hint: String,
    keyboardOptions: KeyboardOptions,
    onTextChange: (String) -> Unit,
//    onNameConfirm: () -> Unit
) {
    val textFieldTextStyle = MaterialTheme.typography.titleLarge
    OutlinedTextField(
        modifier = modifier.padding(bottom = 16.dp),
        value = textState,
        onValueChange = { onTextChange(it) },
        textStyle = textFieldTextStyle,
        singleLine = true,
        shape = ShapeDefaults.Medium,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(onDone = { /*todo*/ }),
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
        }
    )
}
