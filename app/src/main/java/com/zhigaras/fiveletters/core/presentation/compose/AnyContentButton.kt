package com.zhigaras.fiveletters.core.presentation.compose

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zhigaras.fiveletters.core.presentation.compose.theme.gray
import com.zhigaras.fiveletters.core.presentation.compose.theme.yellow

@Composable
fun AnyContentButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = ShapeDefaults.Medium,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = yellow,
            disabledContainerColor = gray
        )
    ) {
        content()
    }
}