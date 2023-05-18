package com.zhigaras.fiveletters.presentation.compose.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.presentation.compose.ui.theme.black
import com.zhigaras.fiveletters.presentation.compose.ui.theme.white
import com.zhigaras.fiveletters.presentation.compose.ui.theme.yellow

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    textState: String,
    hint: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions,
    onTextChange: (String) -> Unit,
    onDone: () -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val textFieldTextStyle = MaterialTheme.typography.titleLarge
    OutlinedTextField(
        modifier = modifier,
        value = textState,
        onValueChange = { onTextChange(it) },
        textStyle = textFieldTextStyle,
        singleLine = true,
        visualTransformation = visualTransformation,
        shape = ShapeDefaults.Medium,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(onDone = { onDone() }),
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
        trailingIcon = trailingIcon
    )
}

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    textState: String,
    hint: String,
    isLastInColumn: Boolean = false,
    onTextChange: (String) -> Unit,
    onDone: () -> Unit = {}
) {
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    val transformation =
        if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    val showHideIcon =
        painterResource(id = if (isPasswordVisible) R.drawable.hide_password else R.drawable.show_password)
    
    InputTextField(
        modifier = modifier,
        textState = textState,
        hint = hint,
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
        }
    )
}

@Composable
fun AuthDivider(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AuthDividerSpacer(modifier.weight(1f))
        Text(
            text = stringResource(R.string.or),
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
fun RegisterWithGoogleButton(
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
            style = MaterialTheme.typography.titleLarge.copy(color = black),
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}