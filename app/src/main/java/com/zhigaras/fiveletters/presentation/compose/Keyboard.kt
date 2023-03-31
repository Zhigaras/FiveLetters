package com.zhigaras.fiveletters.presentation.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.data.Alphabet
import com.zhigaras.fiveletters.presentation.Letter
import com.zhigaras.fiveletters.presentation.LetterType
import com.zhigaras.fiveletters.presentation.compose.ui.theme.black
import com.zhigaras.fiveletters.presentation.compose.ui.theme.keyboardButtonCornerRadius
import com.zhigaras.fiveletters.presentation.compose.ui.theme.keyboardButtonInnerPadding
import com.zhigaras.fiveletters.presentation.compose.ui.theme.yellow

@Composable
fun Key(
    modifier: Modifier = Modifier,
    letter: Letter
) {
    OutlinedCard(
        border = BorderStroke(width = letter.type.borderWidth, color = letter.borderColor),
        shape = RoundedCornerShape(letter.type.cornersRadius),
        colors = CardDefaults.cardColors(
            containerColor = letter.cardColor,
            contentColor = letter.charColor
        ),
        modifier = modifier
    ) {
        Text(
            text = letter.char.toString().uppercase(),
            fontSize = letter.type.charSize,
            modifier = Modifier
                .padding(vertical = letter.type.charPadding)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun Keyboard(
    modifier: Modifier = Modifier
) {
    val alphabet = Alphabet.alphabetRu.map { row ->
        row.map { Letter.Default(type = LetterType.Key(), char = it) }
    }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        alphabet.forEachIndexed { index, row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = modifier.fillMaxWidth().height(IntrinsicSize.Max)
            ) {
                if (index == alphabet.lastIndex)
                    ConfirmWordButton(modifier = Modifier.weight(17f))
                row.forEach {
                    Key(modifier = Modifier.weight(10f), letter = it)
                }
                if (index == alphabet.lastIndex)
                    BackspaceButton(modifier = Modifier.weight(17f), letter = row.first())
            }
        }
    }
}

@Composable
fun ConfirmWordButton(
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        border = BorderStroke(width = 1.dp, yellow),
        shape = RoundedCornerShape(keyboardButtonCornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = yellow,
            contentColor = black
        ),
        modifier = modifier
            .fillMaxHeight()
            .clickable { TODO() }
    ) {
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = stringResource(R.string.confirm_word),
            modifier = Modifier.fillMaxSize().padding(keyboardButtonInnerPadding)
        )
    }
}

@Composable
fun BackspaceButton(
    modifier: Modifier = Modifier,
    letter: Letter
) {
    OutlinedCard(
        border = BorderStroke(width = 1.dp, color = letter.borderColor),
        shape = RoundedCornerShape(letter.type.cornersRadius),
        colors = CardDefaults.cardColors(
            containerColor = letter.cardColor,
            contentColor = letter.charColor
        ),
        modifier = modifier
            .fillMaxHeight()
            .clickable { TODO() }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(R.string.clear_letter),
            modifier = Modifier.fillMaxSize().padding(keyboardButtonInnerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KeyboardPreview() {
    Keyboard()
}