package com.zhigaras.fiveletters.feature.menu.presentation

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.core.presentation.compose.DoublePressBackHandler
import com.zhigaras.fiveletters.core.presentation.compose.OrientationSwapper
import com.zhigaras.fiveletters.core.presentation.compose.SingleClickButton
import com.zhigaras.fiveletters.core.presentation.compose.theme.black
import com.zhigaras.fiveletters.core.presentation.compose.theme.yellow
import com.zhigaras.fiveletters.feature.menu.domain.model.UserStat
import com.zhigaras.fiveletters.feature.menu.presentation.rules.RulesDialog
import com.zhigaras.fiveletters.feature.menu.presentation.rules.RulesViewModel
import com.zhigaras.fiveletters.feature.play.domain.model.ProgressState

@Composable
fun MenuScreen(
    viewModel: MenuViewModel,
    rulesViewModel: RulesViewModel,
    isExpanded: Boolean,
    progressState: ProgressState,
    newGame: () -> Unit,
    continueGame: () -> Unit,
    onFinish: () -> Unit
) {
    
    val userStat by viewModel.userStatFlow().collectAsStateWithLifecycle(UserStat.Empty())
    var showAlertDialog by rememberSaveable { mutableStateOf(false) }
    var showRulesDialog by rememberSaveable { mutableStateOf(false) }
    
    DoublePressBackHandler(onFinish = onFinish)
    
    if (showAlertDialog) {
        ConfirmationDialog(
            message = R.string.new_game_confirmation,
            approveText = R.string.continue_whatever,
            declineText = R.string.new_game,
            onApprove = continueGame,
            onDecline = newGame,
            onDismiss = { showAlertDialog = false }
        )
    }
    if (showRulesDialog) {
        RulesDialog(viewModel = rulesViewModel, onDismiss = { showRulesDialog = false })
    }
    
    OrientationSwapper(modifier = Modifier.fillMaxSize(), isExpanded = isExpanded, content = listOf(
        {
            UserStatistics(
                modifier = it,
                userStat = userStat,
                isExpanded = isExpanded,
                getUser = { viewModel.getUser() } //TODO remove after debugging
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
    userStat: UserStat,
    isExpanded: Boolean,
    getUser: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = yellow,
                contentColor = black
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                if (!isExpanded)
                    Text(
                        text = stringResource(R.string.five_letters),
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                            .clickable { getUser() }
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
                        listOf(R.string.progress, R.string.average_attempts).zip(
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
    onNewGameClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        SingleClickButton(
            text = stringResource(R.string.start),
            textStyle = MaterialTheme.typography.titleMedium,
            clickDisabledPeriod = 800L,
            onClick = {
                if (progressState == ProgressState.PROGRESS) showAlertDialog()
                else onNewGameClick()
            }
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(120.dp),
            contentAlignment = Alignment.Center
        ) {
//                RulesButton(
//                    onClick = showRulesDialog
//                )
            PulsatingRulesButton(
                onClick = {
                    showRulesDialog()
                }
            )
        }
    }
}

@Composable
fun RulesButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = { onClick() }) {
        Icon(
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Default.Info,
            contentDescription = stringResource(id = R.string.show_rules),
            tint = yellow
        )
    }
}

@Composable
fun PulsatingRulesButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val transition = rememberInfiniteTransition()
    val scale by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(2000)
        )
    )
    val alpha by transition.animateFloat(
        initialValue = 0.7f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            tween(2000)
        )
    )
    Surface(
        modifier = modifier
            .fillMaxSize()
            .scale(scale)
            .alpha(alpha),
        shape = CircleShape,
        color = yellow
    ) {}
    RulesButton(onClick = { onClick() })
}