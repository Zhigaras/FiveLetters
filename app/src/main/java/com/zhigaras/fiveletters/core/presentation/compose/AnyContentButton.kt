package com.zhigaras.fiveletters.core.presentation.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.core.presentation.compose.theme.black
import com.zhigaras.fiveletters.core.presentation.compose.theme.gray
import com.zhigaras.fiveletters.core.presentation.compose.theme.white
import com.zhigaras.fiveletters.core.presentation.compose.theme.yellow

@Composable
fun AnyContentButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isAlternative: Boolean = false,
    onClick: () -> Unit,
    content: @Composable (Color) -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = ShapeDefaults.Medium,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isAlternative) Color.Transparent else yellow,
            disabledContainerColor = gray
        ),
        border = if (isAlternative) BorderStroke(width = 2.dp, color = yellow) else null
    ) {
        content(if (isAlternative) white else black)
    }
}