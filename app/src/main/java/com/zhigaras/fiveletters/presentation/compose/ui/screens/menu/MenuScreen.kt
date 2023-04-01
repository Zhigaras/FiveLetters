package com.zhigaras.fiveletters.presentation.compose.ui.screens.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.R
import com.zhigaras.fiveletters.presentation.compose.ui.theme.black
import com.zhigaras.fiveletters.presentation.compose.ui.theme.yellow

@Composable
fun MenuScreen() {
    
    // TODO: animated header
    
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(15f)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = yellow,
                    contentColor = black
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.five_letters),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 32.dp)
                )
                Text(
                    text = stringResource(R.string.welcome),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 64.dp)
                )
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(10f)
        ) {
            Button(
                onClick = { /*TODO*/ },
                shape = ShapeDefaults.Medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = yellow,
                    contentColor = black
                )
            ) {
                Text(
                    text = stringResource(R.string.start),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MenuPreview() {
    MenuScreen()
}