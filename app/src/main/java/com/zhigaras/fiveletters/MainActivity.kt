package com.zhigaras.fiveletters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.zhigaras.fiveletters.presentation.compose.ui.theme.FiveLettersTheme
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.AuthViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.MenuViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.PlayViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.viewmodels.WelcomeViewModel
import com.zhigaras.fiveletters.presentation.navigation.FiveLettersNavHost

class MainActivity : ComponentActivity(), ProvideViewModel {
    
    private lateinit var playViewModel: PlayViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel = provideViewModel(AuthViewModel::class.java, this)
        val welcomeViewModel = provideViewModel(WelcomeViewModel::class.java, this)
        playViewModel = provideViewModel(PlayViewModel::class.java, this)
        val menuViewModel = provideViewModel(MenuViewModel::class.java, this)
        
        setContent {
            FiveLettersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.secondary
                ) {
                    FiveLettersNavHost(
                        authViewModel = authViewModel,
                        welcomeViewModel = welcomeViewModel,
                        playViewModel = playViewModel,
                        menuViewModel = menuViewModel,
                        onFinish = { this.finish() }
                    )
                }
            }
        }
    }
    
    override fun onSaveInstanceState(outState: Bundle) {
        playViewModel.saveState()
        super.onSaveInstanceState(outState)
    }

    override fun finish() {
        playViewModel.saveState()
        super.finish()
    }
    
    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T {
        return (application as ProvideViewModel).provideViewModel(clazz, this)
    }
}