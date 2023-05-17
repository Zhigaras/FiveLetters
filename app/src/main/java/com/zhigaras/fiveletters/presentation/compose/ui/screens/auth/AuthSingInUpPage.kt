package com.zhigaras.fiveletters.presentation.compose.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.model.AuthProcessStatus
import com.zhigaras.fiveletters.model.AuthState
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.CommonButton
import com.zhigaras.fiveletters.presentation.compose.ui.theme.black
import com.zhigaras.fiveletters.presentation.compose.ui.theme.playScreenMaxWidth
import com.zhigaras.fiveletters.presentation.compose.ui.theme.white
import com.zhigaras.fiveletters.presentation.compose.ui.theme.yellow

@Composable
fun AuthSignInUpPage(
    authPage: AuthPage,
    authState: AuthState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onButtonClick: () -> Unit
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
            textState = authState.email,
            hint = stringResource(R.string.email_hint),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            onTextChange = { onEmailChanged(it) }
        )
        InputTextField(
            modifier = maxWidthModifier,
            textState = authState.password,
            hint = stringResource(R.string.password),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            onTextChange = { onPasswordChanged(it) }
        )
        if (authPage == AuthPage.SIGN_IN)
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
            text = stringResource(
                id = if (authPage == AuthPage.SIGN_IN) R.string.sign_in else R.string.sign_up
            ),
            textStyle = MaterialTheme.typography.titleLarge,
            enabled = authState.status == AuthProcessStatus.Complete,
            onClick = { onButtonClick() }
        )
        if (authPage == AuthPage.SIGN_UP) {
            RegisterDivider()
            RegisterWithGoogleButton(modifier = maxWidthModifier)
        }
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
        modifier = modifier,
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

@Composable
fun RegisterDivider(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RegisterDividerSpacer(modifier.weight(1f))
        Text(
            text = stringResource(R.string.or),
            style = MaterialTheme.typography.titleSmall
        )
        RegisterDividerSpacer(modifier.weight(1f))
    }
}

@Composable
fun RegisterDividerSpacer(
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .height(2.dp)
            .padding(horizontal = 8.dp)
            .background(color = white)
    )
}

@Composable
fun RegisterWithGoogleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {} //TODO
) {
    Button(
        onClick = { onClick() },
        shape = ShapeDefaults.Medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = yellow
        ),
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = R.drawable.google_icon),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.continue_with_google),
            style = MaterialTheme.typography.titleLarge.copy(color = black),
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}