package com.zhigaras.fiveletters.presentation.compose.ui.screens.menu

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.model.ProgressState
import com.zhigaras.fiveletters.model.UserStat
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.MenuViewModel

@Composable
fun MenuScreen(
    viewModel: MenuViewModel,
    progressState: ProgressState,
    newGame: () -> Unit,
    continueGame: () -> Unit,
    onFinish: () -> Unit
) {
    
    // TODO: animated header
    
    val context = LocalContext.current
    val userStat by viewModel.userStatFlow().collectAsStateWithLifecycle(UserStat.Empty())
    var backPressedTime by remember { mutableStateOf(0L) }
    val showDialog = remember { mutableStateOf(false) }
    BackHandler(enabled = true) {
        if (backPressedTime + 2000 > System.currentTimeMillis()) onFinish()
        else Toast.makeText(
            context,
            context.getString(R.string.press_back_again),
            Toast.LENGTH_SHORT
        ).show()
        backPressedTime = System.currentTimeMillis()
    }
    if (showDialog.value) {
        ConfirmationDialog(
            startNewGame = newGame,
            continueGame = continueGame,
            onDismiss = { showDialog.value = false }
        )
    }
    
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(15f)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(R.string.five_letters),
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        listOf(
                            listOf(R.string.games_played, R.string.wins).zip(
                                listOf(userStat.games.toString(), userStat.wins.toString())
                            ),
                            listOf(R.string.win_rate, R.string.average_attempts).zip(
                                listOf(userStat.formattedProgress, userStat.formattedAttempts)
                            )
                        ).forEachIndexed { columnIndex, row ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                row.forEachIndexed { rowIndex, item ->
                                    StatCard(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight(),
                                        content = item,
                                        progressFlag = columnIndex == 1 && rowIndex == 0,
                                        progress = userStat.progress
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(10f)
        ) {
            CommonButton(
                text = stringResource(R.string.start),
                onClick = {
                    if (progressState == ProgressState.PROGRESS) showDialog.value = true
                    else newGame()
                }
            )
        }
    }
}

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        shape = ShapeDefaults.Medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}