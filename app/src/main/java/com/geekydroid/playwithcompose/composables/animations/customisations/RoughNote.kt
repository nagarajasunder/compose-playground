package com.geekydroid.playwithcompose.composables.animations.customisations

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun RoughNote(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    var cardHeight by remember { mutableStateOf(380.dp) }
    val cardHeightAnim by animateDpAsState(
        targetValue = cardHeight,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )

    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(cardHeightAnim)
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color.Cyan),
            contentAlignment = Alignment.TopStart
        ) {
        }
        Button(onClick = {
            cardHeight = if (cardHeight == 380.dp) {
                48.dp
            } else {
                380.dp
            }
        }) {
            Text("Animate")
        }
    }


}