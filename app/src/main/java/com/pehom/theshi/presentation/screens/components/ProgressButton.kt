package com.pehom.theshi.presentation.screens.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red




@Composable
fun ProgressButton(
    modifier: Modifier,
    progress: Float,
    content: @Composable () -> Unit
){
    /*val stroke = with(LocalDensity.current) {
        Stroke(width = ProgressIndicatorDefaults.StrokeWidth.toPx(), cap = StrokeCap.Butt)
    }

    Canvas(modifier = modifier){
        val diameterOffset = stroke.width / 2
        drawCircle(
            radius = size.minDimension / 2.0f-diameterOffset,
            color= LightGray,style = stroke)
    }*/

    val infiniteTransition = rememberInfiniteTransition()

    val progressAnimationValue by infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = progress,
        animationSpec = infiniteRepeatable(animation = tween(2000))
    )
    CircularProgressIndicator(
        progress = progress,
        color = Red,
        modifier = Modifier.then(modifier))
}