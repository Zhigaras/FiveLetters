package com.zhigaras.fiveletters.presentation.navigation

import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreenViewProvider

class SplashScreenAnimator {
    
    fun animate(splashScreenView: SplashScreenViewProvider) =
        ObjectAnimator.ofFloat(
            splashScreenView.view,
            View.TRANSLATION_X,
            0f,
            -splashScreenView.view.width.toFloat()
        ).run {
            duration = 500L
            doOnEnd { splashScreenView.remove() }
            start()
        }
}