package com.zhigaras.fiveletters

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zhigaras.fiveletters.presentation.compose.ui.theme.FiveLettersTheme
import com.zhigaras.fiveletters.presentation.compose.ui.screens.auth.SignInViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.screens.menu.MenuViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.screens.play.PlayViewModel
import com.zhigaras.fiveletters.presentation.compose.ui.screens.registration.SignUpViewModel
import com.zhigaras.fiveletters.presentation.navigation.FiveLettersNavHost

class MainActivity : ComponentActivity(), ProvideViewModel {
    
    private lateinit var playViewModel: PlayViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = Firebase.auth //TODO
        val needToAuth = auth.currentUser == null
        val menuViewModel = provideViewModel(MenuViewModel::class.java, this)
        playViewModel = provideViewModel(PlayViewModel::class.java, this)
        val signInViewModel = provideViewModel(SignInViewModel::class.java, this)
        val signUpViewModel = provideViewModel(SignUpViewModel::class.java, this)
        
        val needToShowSplash = Build.VERSION.SDK_INT <= 31
        
        setContent {
            FiveLettersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.secondary
                ) {
                    FiveLettersNavHost(
                        needToShowSplash = needToShowSplash,
                        playViewModel = playViewModel,
                        menuViewModel = menuViewModel,
                        signInViewModel = signInViewModel,
                        signUpViewModel = signUpViewModel,
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