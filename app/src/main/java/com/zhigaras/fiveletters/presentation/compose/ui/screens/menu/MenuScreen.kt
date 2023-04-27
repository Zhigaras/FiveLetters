package com.zhigaras.fiveletters.presentation.compose.ui.screens.menu

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.model.ProgressState
import com.zhigaras.fiveletters.model.UserStat
import com.zhigaras.fiveletters.presentation.compose.ui.screens.OrientationSwapper
import com.zhigaras.fiveletters.presentation.compose.ui.theme.yellow
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.MenuViewModel

@Composable
fun MenuScreen(
    viewModel: MenuViewModel,
    isExpanded: Boolean,
    progressState: ProgressState,
    newGame: () -> Unit,
    continueGame: () -> Unit,
    onFinish: () -> Unit
) {
    
    // TODO: animated header
    
    val context = LocalContext.current
    val userStat by viewModel.userStatFlow().collectAsStateWithLifecycle(UserStat.Empty())
    var backPressedTime by remember { mutableStateOf(0L) }
    var showAlertDialog by rememberSaveable { mutableStateOf(false) }
    var showRulesDialog by rememberSaveable { mutableStateOf(false) }
    val rulesRowsList by viewModel.rulesRowFlow.collectAsStateWithLifecycle()
    
    BackHandler(enabled = true) {
        if (backPressedTime + 2000 > System.currentTimeMillis()) onFinish()
        else Toast.makeText(
            context,
            context.getString(R.string.press_back_again),
            Toast.LENGTH_SHORT
        ).show()
        backPressedTime = System.currentTimeMillis()
    }
    if (showAlertDialog) {
        ConfirmationDialog(
            startNewGame = newGame,
            continueGame = continueGame,
            onDismiss = { showAlertDialog = false }
        )
    }
    if (showRulesDialog) {
        RulesDialog(rulesRowsList = rulesRowsList, onDismiss = { showRulesDialog = false })
    }
    
    OrientationSwapper(modifier = Modifier.fillMaxSize(), isExpanded = isExpanded, content = listOf(
        {
            UserStatistics(
                modifier = it,
                userStat = userStat
            )
        },
        {
            PlayButtonArea(
                modifier = it,
                progressState = progressState,
                showAlertDialog = { showAlertDialog = true },
                showRulesDialog = { showRulesDialog = true },
                onNewGameClick = newGame
            )
        }
    ))
}

@Composable
fun UserStatistics(
    modifier: Modifier = Modifier,
    userStat: UserStat
) {
    Box(
        modifier = modifier
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
}

@Composable
fun PlayButtonArea(
    modifier: Modifier = Modifier,
    progressState: ProgressState,
    showAlertDialog: () -> Unit,
    showRulesDialog: () -> Unit,
    onNewGameClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CommonButton(
            text = stringResource(R.string.start),
            textStyle = MaterialTheme.typography.titleLarge,
            onClick = {
                if (progressState == ProgressState.PROGRESS) showAlertDialog()
                else onNewGameClick()
            }
        )
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { showRulesDialog() }) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.Info,
                contentDescription = stringResource(id = R.string.show_rules),
                tint = yellow
            )
        }
    }
}

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
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
            style = textStyle,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}