package com.geekydroid.playwithcompose.composables.canvas

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.geekydroid.playwithcompose.R

@Preview(showBackground = true)
@Composable
fun GameHealthIndicator(modifier: Modifier = Modifier) {
    var healthBar by remember { mutableFloatStateOf(1f) }
    val healthBarAnim by animateFloatAsState(targetValue = healthBar)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HealthBar(
            healthPercentage = healthBarAnim
        )
        Button(onClick = {
            healthBar = (healthBar-0.1f).coerceAtLeast(0f)
        }) {
            Text("Decrease")
        }
        Button(onClick = {
            healthBar = (healthBar+0.1f).coerceAtMost(1f)
        }) {
            Text("Increase")
        }
        Button(onClick = {
            healthBar = 1f
        }) {
            Text("Reset")
        }
    }

}


@Composable
private fun HealthBar(
    modifier: Modifier = Modifier,
    healthPercentage: Float) {
    val planeSize = 240
    val image = ImageBitmap.imageResource(R.drawable.flight)
    val colorGradient = Brush.verticalGradient(
        colors = buildList {
            if(healthPercentage > 0.6f) add(Color.Green)
            if(healthPercentage > 0.6f) add(Color.Green)
            if (healthPercentage < 0.6f && healthPercentage > 0.4f) add(Color.Yellow.copy(0.5f))
            if (healthPercentage < 0.6f  && healthPercentage > 0.4f) add(Color.Yellow)
            if (healthPercentage < 0.4f) add(Color.Red)
            if (healthPercentage < 0.4f) add(Color.Red.copy(alpha = 0.7f))
        },
    )
    Box(modifier = modifier
        .size(planeSize.dp)
        .drawWithContent {
            with(drawContext.canvas.nativeCanvas) {
                val checkpoint = saveLayer(null, null)
                drawContent()
                drawImage(
                    image = image,
                    dstSize = IntSize(
                        planeSize.dp
                            .toPx()
                            .toInt(),
                        planeSize.dp
                            .toPx()
                            .toInt()
                    ),
                    blendMode = BlendMode.DstIn
                )
                restoreToCount(checkpoint)
            }
        }) {
        Box(modifier = Modifier
            .size(planeSize.dp)
            .background(color = Color.LightGray)) {
            Box(
                modifier = Modifier
                    .height((planeSize * healthPercentage).dp)
                    .fillMaxWidth()
                    .background(colorGradient)
                    .align(Alignment.BottomCenter)
            )
        }
    }


}