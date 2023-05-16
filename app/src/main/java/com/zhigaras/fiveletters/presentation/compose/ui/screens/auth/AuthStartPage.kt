package com.zhigaras.fiveletters.presentation.compose.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.CommonButton
import com.zhigaras.fiveletters.presentation.compose.ui.theme.playScreenMaxWidth

@Composable
fun AuthStartPage(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .widthIn(max = playScreenMaxWidth),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom)
    ) {
        CommonButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.sign_in),
            textStyle = MaterialTheme.typography.titleLarge,
            onClick = { onSignInClick() }
        )
        CommonButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.sign_up),
            textStyle = MaterialTheme.typography.titleLarge,
            onClick = { onSignUpClick() }
        )
    }
}