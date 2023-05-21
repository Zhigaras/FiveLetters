package com.zhigaras.fiveletters.feature.auth.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.core.presentation.compose.theme.black
import com.zhigaras.fiveletters.core.presentation.compose.theme.white
import com.zhigaras.fiveletters.core.presentation.compose.theme.yellow
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldState
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldValidity

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    textState: String,
    isError: Boolean,
    hint: String,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions,
    onTextChange: (String) -> Unit,
    onDone: () -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        modifier = modifier,
        value = textState,
        onValueChange = { onTextChange(it) },
        textStyle = textStyle,
        singleLine = true,
        isError = isError,
        visualTransformation = visualTransformation,
        shape = ShapeDefaults.Medium,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(onDone = { onDone() }),
        placeholder = {
            Text(
                text = hint,
                style = textStyle
            )
        },
        trailingIcon = trailingIcon,
        supportingText = supportingText
    )
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    state: InputFieldState,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    onTextChange: (String) -> Unit,
    onClear: () -> Unit
) {
    val errorText: @Composable (() -> Unit)? = if (state.validity is InputFieldValidity.Invalid) {
        { Text(text = stringResource(id = state.validity.error)) }
    } else null
    
    InputTextField(
        modifier = modifier,
        textStyle = textStyle,
        textState = state.value,
        hint = stringResource(R.string.email_hint),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        isError = state.isInvalid,
        onTextChange = onTextChange,
        supportingText = errorText,
        trailingIcon = {
            IconButton(
                modifier = Modifier.padding(end = 5.dp),
                onClick = onClear
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.clear)
                )
            }
        }
    )
}

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    state: InputFieldState,
    hint: String,
    isLastInColumn: Boolean = false,
    onTextChange: (String) -> Unit,
    onDone: () -> Unit = {}
) {
    var supportingText: @Composable (() -> Unit)? = null
    if (isLastInColumn) {
        supportingText = { Text(text = stringResource(id = R.string.password_support_text)) }
    }
    if (state.validity is InputFieldValidity.Invalid) {
        supportingText = { Text(text = stringResource(id = state.validity.error)) }
    }
    
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    val transformation =
        if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    val showHideIcon =
        painterResource(id = if (isPasswordVisible) R.drawable.hide_password else R.drawable.show_password)
    
    InputTextField(
        modifier = modifier,
        textState = state.value,
        hint = hint,
        isError = state.isInvalid,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = if (isLastInColumn) ImeAction.Done else ImeAction.Next
        ),
        onTextChange = onTextChange,
        visualTransformation = transformation,
        onDone = onDone,
        trailingIcon = {
            IconToggleButton(
                checked = isPasswordVisible,
                onCheckedChange = { isPasswordVisible = !isPasswordVisible }
            ) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = showHideIcon,
                    contentDescription = null
                )
            }
        },
        supportingText = supportingText
    )
}

@Composable
fun AuthDivider(
    modifier: Modifier = Modifier,
    @StringRes textId: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AuthDividerSpacer(modifier.weight(1f))
        Text(
            text = stringResource(textId),
            style = MaterialTheme.typography.titleSmall
        )
        AuthDividerSpacer(modifier.weight(1f))
    }
}

@Composable
fun AuthDividerSpacer(
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
fun SignInWithGoogleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
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
            style = MaterialTheme.typography.titleMedium.copy(color = black),
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}