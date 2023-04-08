package com.zhigaras.fiveletters.presentation.compose.ui.screens.welcome

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.presentation.compose.ui.theme.gray10
import com.zhigaras.fiveletters.presentation.compose.ui.theme.orange
import com.zhigaras.fiveletters.presentation.compose.ui.theme.white
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.WelcomeViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel
) {

    val scrollState = rememberScrollState()
    val name by viewModel.usernameFlow.collectAsState()
    
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.hi), style = MaterialTheme.typography.displayLarge)
            Text(
                text = stringResource(R.string.game_name),
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = stringResource(R.string.name_request),
                style = MaterialTheme.typography.displaySmall
            )
            NameInputTextField(
                textState = name,
                onTextChange = { viewModel.onNameChanged(it) },
                onNameConfirm = { viewModel.saveUsername() }
            )
            val buttonsTextStyle = MaterialTheme.typography.titleLarge
            val buttonsModifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            AnimatedContent(
                targetState = name.isBlank(),
                transitionSpec = {
                    fadeIn(animationSpec = tween(800)) with
                            fadeOut(animationSpec = tween(800))
                }
            ) {
                if (it) {
                    TextButton(onClick = { TODO() }) {
                        Text(
                            text = stringResource(R.string.skip_name_input),
                            color = gray10,
                            style = buttonsTextStyle.copy(textDecoration = TextDecoration.Underline),
                            textAlign = TextAlign.Center,
                            modifier = buttonsModifier.padding(4.dp)
                        )
                    }
                } else {
                    ConfirmNameButton(
                        onClick = { viewModel.saveUsername() },
                        textStyle = buttonsTextStyle,
                        modifier = buttonsModifier
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameInputTextField(
    textState: String,
    onTextChange: (String) -> Unit,
    onNameConfirm: () -> Unit
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
        keyboardActions = KeyboardActions(
            onDone = { onNameConfirm() } //TODO hideKeyboard?
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            focusedBorderColor = MaterialTheme.colorScheme.secondary,
            focusedTextColor = MaterialTheme.colorScheme.secondary,
            cursorColor = MaterialTheme.colorScheme.secondary,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            containerColor = white
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.welcome_hint),
                style = textFieldTextStyle
            )
        },
        modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp)
    )
}

@Composable
fun ConfirmNameButton(
    onClick: () -> Unit,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = { onClick() },
        shape = ShapeDefaults.Medium,
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.secondary
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = orange,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.welcome_button_text),
            style = textStyle,
            modifier = Modifier.padding(4.dp)
        )
    }
}