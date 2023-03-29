package com.zhigaras.fiveletters.presentation.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.presentation.CardState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Letter(
    modifier: Modifier = Modifier,
    cardState: CardState
) {
    OutlinedCard(
        onClick = { /*TODO*/ },
        border = BorderStroke(width = 2.dp, cardState.borderColor),
        colors = CardDefaults.cardColors(containerColor = cardState.cardColor),
        modifier = modifier
            .width(70.dp)
            .padding(horizontal = 2.dp)
    ) {
        Text(
            text = cardState.char.toString().uppercase(),
            style = MaterialTheme.typography.displayLarge,
            color = cardState.charColor,
            modifier = modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun LetterField(
    modifier: Modifier = Modifier,
    letters: List<List<CardState>>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        letters.forEach { letterRow ->
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxWidth()
            ) {
                letterRow.forEach {
                    Letter(cardState = it)
                }
            }
        }
    }
}

