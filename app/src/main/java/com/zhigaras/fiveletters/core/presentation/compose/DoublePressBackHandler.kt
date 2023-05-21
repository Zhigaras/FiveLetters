package com.zhigaras.fiveletters.core.presentation.compose

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.zhigaras.fiveletters.R

@Composable
fun DoublePressBackHandler(
    onFinish: () -> Unit
) {
    var backPressedTime by remember { mutableStateOf(0L) }
    val context = LocalContext.current
    
    BackHandler(enabled = true) {
        if (backPressedTime + 2000 > System.currentTimeMillis()) onFinish()
        else Toast.makeText(
            context,
            context.getString(R.string.press_back_again),
            Toast.LENGTH_SHORT
        ).show()
        backPressedTime = System.currentTimeMillis()
    }
}