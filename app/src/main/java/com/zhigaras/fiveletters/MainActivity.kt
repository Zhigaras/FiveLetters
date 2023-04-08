package com.zhigaras.fiveletters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.zhigaras.fiveletters.presentation.compose.ui.screens.play.PlayScreen
import com.zhigaras.fiveletters.presentation.compose.ui.theme.FiveLettersTheme
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.PlayViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.WelcomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val playViewModel =
            (application as ProvideViewModel).provideViewModel(PlayViewModel::class.java, this)
        val welcomeViewModel =
            (application as ProvideViewModel).provideViewModel(WelcomeViewModel::class.java, this)
        setContent {
            FiveLettersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.secondary
                ) {
                    FiveLettersTheme {
//                        SplashScreen()
//                        WelcomeScreen(welcomeViewModel)
//                        MenuScreen()
                        PlayScreen(playViewModel)
                    }
                }
            }
        }
    }
}