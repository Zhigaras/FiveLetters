package com.zhigaras.fiveletters.feature.menu.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.core.presentation.compose.theme.gray100
import com.zhigaras.fiveletters.core.presentation.compose.theme.yellow10
import com.zhigaras.fiveletters.feature.play.domain.model.LetterState
import com.zhigaras.fiveletters.feature.play.domain.model.RowState
import com.zhigaras.fiveletters.feature.play.presentation.LetterRow

@Composable
fun RulesDialog(
    modifier: Modifier = Modifier,
    rulesRowsList: List<RowState>,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        RulesDialogContent(
            modifier = modifier,
            rulesRowsList = rulesRowsList,
            onDismiss = onDismiss
        )
    }
}

@Composable
fun RulesDialogContent(
    modifier: Modifier = Modifier,
    rulesRowsList: List<RowState>,
    onDismiss: () -> Unit
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
            RulesContent(rulesRowsList = rulesRowsList)
        }
        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp),
            onClick = { onDismiss() }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.close),
                tint = yellow10
            )
        }
    }
}

@Composable
fun RulesContent(
    rulesRowsList: List<RowState>
) {
    Text(text = stringResource(id = R.string.rules1))
    RulesSpacer()
    LetterRow(newRow = rulesRowsList[0])
    val wrongLetters = rulesRowsList[0].select(LetterState.Wrong::class.java)
    val rightLetters = rulesRowsList[0].select(LetterState.Right::class.java)
    Text(
        text = stringResource(id = R.string.rules2, wrongLetters, rightLetters)
    )
    LetterRow(newRow = rulesRowsList[1])
    val exactLetters = rulesRowsList[1].select(LetterState.Exact::class.java)
    Text(
        text = stringResource(id = R.string.rules3, exactLetters)
    )
    LetterRow(newRow = rulesRowsList[3])
    Text(
        text = stringResource(id = R.string.rules4)
    )
    LetterRow(newRow = rulesRowsList[2])
    Text(
        text = stringResource(id = R.string.rules5)
    )
    RulesSpacer()
    Text(text = stringResource(id = R.string.rules6))
}

@Composable
fun RulesSpacer(
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(yellow10)
            .clip(shape = ShapeDefaults.ExtraLarge)
    )
}