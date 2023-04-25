package com.zhigaras.fiveletters.presentation.compose.ui.screens.play

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.CommonButton
import com.zhigaras.fiveletters.presentation.compose.ui.theme.playScreenMaxWidth

@Composable
fun KeyboardStub(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onNewGameClick: () -> Unit
) {
    Column(
        modifier = modifier.widthIn(max = playScreenMaxWidth),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = stringResource(R.string.to_menu),
            onClick = onBackClick
        )
        CommonButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            text = stringResource(R.string.new_word),
            onClick = onNewGameClick
        )
    }
}