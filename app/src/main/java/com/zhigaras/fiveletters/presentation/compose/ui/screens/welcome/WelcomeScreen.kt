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
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.presentation.compose.ui.theme.*
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.WelcomeViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel
) {
    val buttonState = remember { mutableStateOf(false) }
    val imeState by rememberImeState()
    val scrollState = rememberScrollState()
    val name by viewModel.usernameFlow.collectAsState()
    
    LaunchedEffect(key1 = imeState) {
        if (imeState) scrollState.animateScrollTo(scrollState.maxValue, tween(500))
    }
    LaunchedEffect(key1 = name.isEmpty()) {
        buttonState.value = name.isNotEmpty()
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
            Text(text = stringResource(R.string.hi), style = MaterialTheme.typography.displayLarge)
            Text(
                text = stringResource(R.string.game_name),
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = stringResource(R.string.name_request),
                style = MaterialTheme.typography.displaySmall
            )
            val textFieldTextStyle = MaterialTheme.typography.headlineMedium
            OutlinedTextField(
                value = name,
                onValueChange = { viewModel.onNameChanged(it) },
                textStyle = textFieldTextStyle,
                singleLine = true,
                shape = ShapeDefaults.Medium,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { viewModel.saveUsername() } //TODO hideKeyboard?
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
            AnimatedVisibility(
                visible = buttonState.value,
                enter = slideInVertically(animationSpec = tween(500)),
                exit = slideOutVertically(animationSpec = tween(500))
            ) {
                OutlinedButton(
                    onClick = { viewModel.saveUsername() },
                    shape = ShapeDefaults.Medium,
                    border = BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = orange,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    TypewriterText(
                        text = stringResource(R.string.welcome_button_text),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TypewriterText(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier
) {
    val index = remember { mutableStateOf(0) }
    val textToShow = remember { mutableStateOf("") }
    
    LaunchedEffect(key1 = true) {
        while (index.value < text.length) {
            index.value++
            delay(40)
            textToShow.value = text.substring(0, index.value)
        }
    }
    Text(text = textToShow.value, style = style, modifier = modifier)
}