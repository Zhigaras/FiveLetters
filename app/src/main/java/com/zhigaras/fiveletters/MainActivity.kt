package com.zhigaras.fiveletters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zhigaras.fiveletters.presentation.CardState
import com.zhigaras.fiveletters.presentation.compose.AnimatedLetter
import com.zhigaras.fiveletters.presentation.compose.LetterField
import com.zhigaras.fiveletters.presentation.compose.ui.theme.FiveLettersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FiveLettersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FiveLettersTheme {
                        LetterField()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LetterPreview() {
    FiveLettersTheme {
        AnimatedLetter(startCardState = CardState.Default())
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LetterFieldPreview() {
    FiveLettersTheme {
        LetterField()
    }
}