package com.zhigaras.fiveletters.feature.menu.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zhigaras.fiveletters.core.presentation.compose.CommonTextButton
import com.zhigaras.fiveletters.core.presentation.compose.theme.gray
import com.zhigaras.fiveletters.core.presentation.compose.theme.white

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    @StringRes message: Int,
    @StringRes approveText: Int,
    @StringRes declineText: Int,
    messageTextStyle: TextStyle = MaterialTheme.typography.titleLarge,
    onApprove: () -> Unit,
    onDecline: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = gray,
                contentColor = white
            ),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(message),
                    textAlign = TextAlign.Center,
                    style = messageTextStyle,
                    modifier = modifier.padding(16.dp)
                )
                CommonTextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    isAlternative = true,
                    text = stringResource(approveText),
                    onClick = { onApprove(); onDismiss() }
                )
                CommonTextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(declineText),
                    onClick = { onDecline(); onDismiss() }
                )
            }
        }
    }
}