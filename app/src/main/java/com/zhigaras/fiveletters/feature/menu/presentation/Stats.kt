package com.zhigaras.fiveletters.feature.menu.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.core.presentation.compose.theme.black
import com.zhigaras.fiveletters.core.presentation.compose.theme.white
import com.zhigaras.fiveletters.core.presentation.compose.theme.yellow


@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    content: Pair<Int, String>,
    progressFlag: Boolean,
    progress: Float
) {
    OutlinedCard(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = yellow,
            contentColor = black
        ),
        border = BorderStroke(2.dp, black),
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            StatCardContent(content = content, progressFlag = progressFlag, progress = progress)
        }
    }
}

@Composable
fun StatCardContent(
    modifier: Modifier = Modifier,
    content: Pair<Int, String>,
    progressFlag: Boolean,
    progress: Float
) {
    Box {
        StatHeader(str = content.first, modifier = modifier.align(Alignment.TopCenter))
        StatBody(modifier = modifier.align(Alignment.Center), str = content.second)
        if (progressFlag)
            LinearProgressIndicator(
                progress = progress,
                trackColor = white,
                color = black,
                modifier = modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
    }
}

@Composable
fun StatHeader(
    modifier: Modifier = Modifier,
    @StringRes str: Int
) {
    Text(
        text = stringResource(str),
        modifier = modifier.padding(8.dp),
        style = MaterialTheme.typography.titleSmall
    )
}

@Composable
fun StatBody(
    modifier: Modifier = Modifier,
    str: String
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = str, style = MaterialTheme.typography.displayMedium)
    }
}