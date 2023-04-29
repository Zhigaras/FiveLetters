package com.zhigaras.fiveletters.presentation.compose.ui.screens.play

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.zhigaras.fiveletters.R

@Composable
fun AdBanner(
    modifier: Modifier = Modifier,
    isTest: Boolean
) {
    val actualAdUnitId =
        stringResource(id = if (isTest) R.string.ad_mob_test_banner_id else R.string.ad_mob_banner_id)
    
    AndroidView(
        modifier = modifier,
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = actualAdUnitId
                loadAd(AdRequest.Builder().build())
            }
        })
}