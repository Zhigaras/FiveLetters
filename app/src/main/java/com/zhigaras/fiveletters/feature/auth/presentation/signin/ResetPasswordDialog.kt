package com.zhigaras.fiveletters.feature.auth.presentation.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.core.presentation.compose.theme.gray100
import com.zhigaras.fiveletters.feature.auth.domain.model.InputFieldState
import com.zhigaras.fiveletters.feature.auth.presentation.EmailInput
import com.zhigaras.fiveletters.feature.menu.presentation.CommonButton

@Composable
fun ResetPasswordDialog(
    modifier: Modifier = Modifier,
    emailState: InputFieldState,
    onEmailChanged: (String) -> Unit,
    onClear: () -> Unit,
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Box(
            modifier = Modifier
                .clip(ShapeDefaults.ExtraLarge)
                .heightIn(max = 600.dp)
                .background(gray100)
                .fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.enter_email_for_password_reset),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
                EmailInput(
                    modifier = Modifier.fillMaxWidth(),
                    state = emailState,
                    onTextChange = onEmailChanged,
                    onClear = onClear
                )
                CommonButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.send_reset_instruction),
                    onClick = onConfirmClick
                )
            }
        }
    }
}