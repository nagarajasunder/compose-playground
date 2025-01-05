package com.geekydroid.playwithcompose.composables.animations.text

import android.service.quicksettings.Tile
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.geekydroid.playwithcompose.composables.shapes.zigzagshape.ZigZagCurveShape
import com.geekydroid.playwithcompose.composables.shapes.zigzagshape.ZigZagPathPosition
import com.geekydroid.playwithcompose.composables.shapes.zigzagshape.ZigZagShape

const val passage = "In a quiet town, whispers of adventure lingered in the air. Children chased dreams, while the sun painted the sky with hues of orange and pink. Each day held a promise, a chance to discover magic. In a quiet town, whispers of adventure lingered in the air. Children chased dreams, while the sun painted the sky with hues of orange and pink. Each day held a promise, a chance to discover magic."

@Preview(showBackground = true)
@Composable
private fun BrushTextAnim(modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    val brushSize = with(density){400.dp.toPx()}
    var size by remember { mutableStateOf(IntSize.Zero) }
    val width = with(density) {size.width.dp.toPx()}
    val height = with(density){size.height.dp.toPx()}
    val targetSize = (brushSize*2f)
    val infiniteAnim = rememberInfiniteTransition(label = "infinite_anim")
    val floatAnim by infiniteAnim.animateFloat(
        initialValue = 0f,
        targetValue = targetSize,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val brush = Brush.linearGradient(
        colors = listOf(
            Color.LightGray,
            Color.White,
            Color.LightGray,

        ),
        start = Offset(floatAnim+brushSize,floatAnim+brushSize),
        end = Offset(floatAnim,floatAnim),
        tileMode = TileMode.Mirror
    )
    Box(modifier = Modifier
        .padding(16.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(brush)
        .fillMaxWidth()
        .aspectRatio(1f)
        .onSizeChanged {
            size = it
        },
    )
}