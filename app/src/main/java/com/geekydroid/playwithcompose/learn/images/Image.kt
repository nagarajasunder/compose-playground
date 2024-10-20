package com.geekydroid.playwithcompose.learn.images

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geekydroid.playwithcompose.R
import com.geekydroid.playwithcompose.composables.shapes.zigzagshape.ZigZagCurveShape

@Preview(showBackground = true)
@Composable
fun LoadImage(modifier: Modifier = Modifier) {
    val infiniteColorAnim = rememberInfiniteTransition("infinite_color_anim")
    val animateFloat by infiniteColorAnim.animateFloat(
        initialValue = 40f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000),
            repeatMode = RepeatMode.Reverse
        )
    )
    val density = LocalDensity.current
    Column {
        Box(
            modifier = Modifier
                .background(color = Color.Yellow)
                .padding(16.dp)
                .size(300.dp)
                .clip(ZigZagCurveShape(noOfCrosses = 27)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.blur(
                    radius = with(density){animateFloat.toDp()},
                    edgeTreatment = BlurredEdgeTreatment(ZigZagCurveShape(27))
                ),
                painter = painterResource(R.drawable.dog),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
    }
}