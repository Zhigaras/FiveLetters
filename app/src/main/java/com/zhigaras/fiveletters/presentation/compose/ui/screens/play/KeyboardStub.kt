package com.zhigaras.fiveletters.presentation.compose.ui.screens.play

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.CommonButton

@Composable
fun KeyboardStub(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onNewGameClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = stringResource(R.string.to_menu),
            onClick = onBackClick
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