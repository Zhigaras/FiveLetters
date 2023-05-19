package com.zhigaras.fiveletters.presentation.compose.ui.screens.signup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.identity.Identity
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.core.presentation.EventEffect
import com.zhigaras.fiveletters.model.auth.InputFieldType
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.CommonButton
import com.zhigaras.fiveletters.presentation.compose.ui.screens.signin.AuthDivider
import com.zhigaras.fiveletters.presentation.compose.ui.screens.signin.EmailInput
import com.zhigaras.fiveletters.presentation.compose.ui.screens.signin.PasswordInput
import com.zhigaras.fiveletters.presentation.compose.ui.screens.signin.RegisterWithGoogleButton
import com.zhigaras.fiveletters.presentation.compose.ui.theme.playScreenMaxWidth
import com.zhigaras.fiveletters.presentation.compose.ui.theme.semiTransparentBlack

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    showSnackBar: suspend (String) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.getState().collectAsStateWithLifecycle()
    val signInClient = Identity.getSignInClient(context)
    val signWithGoogleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) {
        viewModel.changeGoogleIdToCredential(it, signInClient)
    }
    EventEffect(event = state.errorEvent, onConsumed = { viewModel.onConsumeError() }) {
        showSnackBar(it.asString(context))
    }
    
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
                onTextChange = { viewModel.onFieldChanged(InputFieldType.PASSWORD, it) }
            )
            PasswordInput(
                modifier = maxWidthModifier,
                state = state.passwordRepeat,
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
                onClick = {
                    viewModel.signUpWithGoogle(signWithGoogleLauncher, signInClient)
                }
            )
        }
    }
    
    AnimatedVisibility(
        visible = state.isLoading,
        enter = fadeIn(tween(300)),
        exit = fadeOut(tween(300))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(semiTransparentBlack),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}