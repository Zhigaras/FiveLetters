package com.zhigaras.fiveletters.feature.play.presentation

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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.core.presentation.compose.theme.gray
import com.zhigaras.fiveletters.core.presentation.compose.theme.white
import com.zhigaras.fiveletters.core.presentation.compose.theme.yellow
import com.zhigaras.fiveletters.feature.menu.presentation.CommonButton

@Composable
fun EndGameDialog(
    modifier: Modifier = Modifier,
    scale: Float = 1f,
    origin: String,
    onDismiss: () -> Unit,
    toMenuClick: () -> Unit,
    onNewGameClick: () -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = gray,
            ),
            modifier = modifier.scale(scale)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.the_word_is),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(color = white),
                    modifier = modifier.padding(top = 16.dp)
                )
                Text(
                    text = origin.uppercase(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayMedium.copy(color = yellow),
                    modifier = modifier
                )
                content(Modifier.padding(bottom = 16.dp))
                CommonButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = stringResource(R.string.to_menu),
                    textStyle = MaterialTheme.typography.titleLarge,
                    onClick = toMenuClick
                )
                CommonButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(R.string.new_word),
                    textStyle = MaterialTheme.typography.titleLarge,
                    onClick = { onNewGameClick(); onDismiss() }
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
        style = MaterialTheme.typography.titleLarge.copy(color = white),
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
        style = MaterialTheme.typography.titleLarge.copy(color = white),
        modifier = modifier
    )
}