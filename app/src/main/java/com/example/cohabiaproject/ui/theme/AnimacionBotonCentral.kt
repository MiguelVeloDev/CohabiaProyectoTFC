package com.example.cohabiaproject.ui.theme


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cohabiaproject.R

@Composable
fun AnimacionBotonCentral(isPlaying: Boolean,
                          modifier: Modifier = Modifier,
                          onAnimationFinished: () -> Unit){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animacion_boton_central))
    val animationState = animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        restartOnPlay = isPlaying,
        iterations = 1
    )
    LaunchedEffect(animationState.isPlaying) {
        if (!animationState.isPlaying && isPlaying) {
            onAnimationFinished()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { animationState.progress},
            modifier = Modifier.size(136.dp)
        )
    }
}
