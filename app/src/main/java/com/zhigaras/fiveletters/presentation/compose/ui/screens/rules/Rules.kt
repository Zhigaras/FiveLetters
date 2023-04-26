package com.zhigaras.fiveletters.presentation.compose.ui.screens.rules

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zhigaras.fiveletters.R

@Composable
fun Rules(
    modifier: Modifier = Modifier
) {
    Text(text = stringResource(id = R.string.rules1))
    Row(modifier = modifier.fillMaxWidth()) {
    
    }
}
