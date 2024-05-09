package com.geekydroid.playwithcompose.composables.piechart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

data class PieItem(
    val name: String,
    val value: Float,
    val color: Color
)

data class PieData(
    val name: String,
    val startAngle: Float,
    val animatedValue: Animatable<Float, AnimationVector1D>,
    val targetValue: Float,
    val color: Color
)

@Composable
fun Piechart(
    modifier: Modifier = Modifier,
    pieItems: List<PieItem>
) {

    BoxWithConstraints {
        val maxWidth = with(LocalDensity.current) { maxWidth.toPx() }
        val maxHeight = with(LocalDensity.current) { maxHeight.toPx() }
        val circleRadius = if (maxWidth < maxHeight) maxWidth else maxHeight
        val innerCircleRadius = circleRadius * 0.2f
        val pieData = transformPieItems(pieItems)
        LaunchedEffect(key1 = Unit) {
            pieData.forEach { pieData ->
                launch {
                    pieData.animatedValue.animateTo(pieData.targetValue, tween(1000))
                }
            }
        }
        Canvas(
            modifier = modifier
                .fillMaxSize()
        ) {
            pieData.forEach { pieItem ->
                drawArc(
                    startAngle = pieItem.startAngle,
                    sweepAngle = pieItem.animatedValue.value,
                    color = pieItem.color,
                    useCenter = true
                )
            }

            drawCircle(
                color = Color.White.copy(alpha = 0.3f),
                radius = innerCircleRadius,
            )
            drawCircle(
                color = Color.White,
                radius = innerCircleRadius*0.8f,
            )
        }
    }
}

fun getPieDataSum(pieItems: List<PieItem>): Float {
    return pieItems.sumOf { it.value.toDouble() }.toFloat()
}

fun transformPieItems(pieItems: List<PieItem>): List<PieData> {
    val pieData = mutableListOf<PieData>()
    val sum = getPieDataSum(pieItems)
    var startAngle = -90f
    pieItems.forEach { pieItem ->
        val sweepAngle = (360f * pieItem.value) / sum
        pieData.add(
            PieData(
                name = pieItem.name,
                startAngle = startAngle,
                animatedValue = Animatable(0f),
                targetValue = sweepAngle,
                color = pieItem.color
            )
        )
        startAngle += sweepAngle
    }
    return pieData
}


@Preview(showSystemUi = true)
@Composable
fun PiechartPreview() {
    val pieItems = listOf(
        PieItem("Kotlin", 70f, Color.Red),
        PieItem("Java", 40f, Color.Blue),
        PieItem("Python", 80f, Color.Yellow),
        PieItem("C++", 20f, Color.Black),
        PieItem("Rust", 15f, Color.Cyan),
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Piechart(
            modifier = Modifier.size(300.dp),
            pieItems = pieItems
        )
    }
}