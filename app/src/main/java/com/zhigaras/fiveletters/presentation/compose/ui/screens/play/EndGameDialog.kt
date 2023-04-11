package com.zhigaras.fiveletters.presentation.compose.ui.screens.play

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.CommonButton
import com.zhigaras.fiveletters.presentation.compose.ui.theme.gray

@Composable
fun EndGameDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    toMenuClick: () -> Unit,
    onNewGameClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = gray,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            modifier = modifier
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                content()
                CommonButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = stringResource(R.string.to_menu),
                    onClick = toMenuClick
                )
                CommonButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(R.string.new_word),
                    onClick = onNewGameClick
                )
            }
        }
    }
}

@Composable
fun WinDialogContent(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.win_text),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.displaySmall,
        modifier = modifier
    )
}

@Composable
fun FailDialogContent(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.fail_text),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.displaySmall,
        modifier = modifier
    )
}