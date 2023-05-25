package com.zhigaras.fiveletters.core.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zhigaras.fiveletters.app.ProvideViewModel
import com.zhigaras.fiveletters.core.navigation.FiveLettersNavHost
import com.zhigaras.fiveletters.core.presentation.compose.theme.FiveLettersTheme
import com.zhigaras.fiveletters.feature.auth.presentation.resetpassword.ResetPasswordViewModel
import com.zhigaras.fiveletters.feature.auth.presentation.signin.SignInViewModel
import com.zhigaras.fiveletters.feature.auth.presentation.signup.SignUpViewModel
import com.zhigaras.fiveletters.feature.menu.presentation.MenuViewModel
import com.zhigaras.fiveletters.feature.menu.presentation.rules.RulesViewModel
import com.zhigaras.fiveletters.feature.play.presentation.PlayViewModel

class MainActivity : ComponentActivity(), ProvideViewModel {
    
    private lateinit var playViewModel: PlayViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        installSplashScreen()
        
        val auth = Firebase.auth //TODO maybe remain that way
        val needToAuth = auth.currentUser == null
        Log.d("AAA", auth.currentUser?.email.toString()) //TODO remove
        val menuViewModel = provideViewModel(MenuViewModel::class.java, this)
        playViewModel = provideViewModel(PlayViewModel::class.java, this)
        val signInViewModel = provideViewModel(SignInViewModel::class.java, this)
        val signUpViewModel = provideViewModel(SignUpViewModel::class.java, this)
        val resetPasswordViewModel = provideViewModel(ResetPasswordViewModel::class.java, this)
        val rulesViewModel = provideViewModel(RulesViewModel::class.java, this)
        
        setContent {
            val snackBarHostState = remember { SnackbarHostState() }
            FiveLettersTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.secondary,
                    snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
                ) { innerPaddings ->
                    FiveLettersNavHost(
                        modifier = Modifier.padding(innerPaddings),
                        needToAuth = needToAuth,
                        playViewModel = playViewModel,
                        menuViewModel = menuViewModel,
                        signInViewModel = signInViewModel,
                        signUpViewModel = signUpViewModel,
                        resetPasswordViewModel = resetPasswordViewModel,
                        rulesViewModel = rulesViewModel,
                        showSnackBar = {
                            snackBarHostState.showSnackbar(
                                message = it,
                                withDismissAction = true,
                                duration = SnackbarDuration.Long
                            )
                        },
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