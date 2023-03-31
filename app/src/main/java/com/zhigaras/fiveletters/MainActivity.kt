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
import com.zhigaras.fiveletters.presentation.Letter
import com.zhigaras.fiveletters.presentation.LetterType
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.MenuScreen
import com.zhigaras.fiveletters.presentation.compose.ui.screens.play.AnimatedLetter
import com.zhigaras.fiveletters.presentation.compose.ui.screens.play.LetterField
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
//                        PlayScreen()
//                        SplashScreen()
                        MenuScreen()
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
        AnimatedLetter(startLetter = Letter.Default(LetterType.Card, ' '))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LetterFieldPreview() {
    FiveLettersTheme {
        LetterField()
    }
}