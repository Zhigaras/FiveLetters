package com.zhigaras.fiveletters.presentation.compose.ui.screens.welcome

import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel,
    navigateToMenu: () -> Unit
) {
    val scrollState = rememberScrollState()
    val name by viewModel.usernameFlow.collectAsState()
    val alpha = remember { List(5) { Animatable(0f) } }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    
    fun confirmName() {
        scope.launch {
            keyboardController?.hide()
            delay(300) //TODO avoiding screen shacking during transition to menu screen
            viewModel.saveUsername()
            navigateToMenu()
        }
    }
    
    fun alphaModifier(index: Int) = Modifier.alpha(alpha[index].value)
    
    LaunchedEffect(key1 = true) {
        alpha.forEach {
            launch {
                it.animateTo(1f, animationSpec = tween(2000))
            }
            delay(70)
        }
    }
    
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
            Text(
                text = stringResource(R.string.hi),
                style = MaterialTheme.typography.displayLarge,
                modifier = alphaModifier(0)
            )
            Text(
                text = stringResource(R.string.game_name),
                style = MaterialTheme.typography.displaySmall,
                modifier = alphaModifier(1)
            )
            Text(
                text = stringResource(R.string.name_request),
                style = MaterialTheme.typography.displaySmall,
                modifier = alphaModifier(2)
            )
            NameInputTextField(
                textState = name,
                onTextChange = { viewModel.onNameChanged(it) },
                onNameConfirm = { confirmName() },
                modifier = alphaModifier(3)
            )
            val buttonsTextStyle = MaterialTheme.typography.titleLarge
            val buttonsModifier = alphaModifier(4)
                .fillMaxWidth()
                .padding(16.dp)
            AnimatedContent(
                targetState = name.isBlank(),
                transitionSpec = {
                    fadeIn(animationSpec = tween(800)) togetherWith
                            fadeOut(animationSpec = tween(800))
                }
            ) {
                if (it) {
                    TextButton(onClick = { confirmName() }) {
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
                        onClick = { confirmName() },
                        textStyle = buttonsTextStyle,
                        modifier = buttonsModifier
                    )
                }
            }
        }
    }
}

@Composable
fun NameInputTextField(
    textState: String,
    onTextChange: (String) -> Unit,
    onNameConfirm: () -> Unit,
    modifier: Modifier = Modifier
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
            onDone = { onNameConfirm() }
        ),
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
                text = stringResource(R.string.welcome_hint),
                style = textFieldTextStyle
            )
        },
        modifier = modifier.padding(vertical = 32.dp, horizontal = 16.dp)
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