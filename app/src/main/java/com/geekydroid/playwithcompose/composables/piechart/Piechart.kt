package com.geekydroid.playwithcompose.composables.piechart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.random.Random

data class PieItem(
    val name: String,
    val value: Float
)

data class PieData(
    val name: String,
    val startAngle: Float,
    val animatedValue: Animatable<Float, AnimationVector1D>,
    val targetValue: Float,
    val color: Color
)

@Composable
fun PieChartContainer(
    modifier: Modifier = Modifier,
    pieData: List<PieData>
) {

    BoxWithConstraints {
        val maxWidth = with(LocalDensity.current) { maxWidth.toPx() }
        val maxHeight = with(LocalDensity.current) { maxHeight.toPx() }
        val circleRadius = if (maxWidth < maxHeight) maxWidth else maxHeight
        val innerCircleRadius = circleRadius * 0.2f
        LaunchedEffect(key1 = pieData) {
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
                radius = innerCircleRadius * 0.8f,
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
        var color: Color
        /**
         * We are doing this to ensure that no color is repeated in the chart
         */
        while (true) {
            color = Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255), Random.nextInt(100,255))
            pieData.find { it.color.value == color.value } ?: break
        }

        pieData.add(
            PieData(
                name = pieItem.name,
                startAngle = startAngle,
                animatedValue = Animatable(0f),
                targetValue = sweepAngle,
                color = color
            )
        )
        startAngle += sweepAngle
    }
    return pieData
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PieChart(pieItems: List<PieItem>) {
    val pieChartState by  remember(
        key1 = pieItems
    ) {
        mutableStateOf(transformPieItems(pieItems), policy = referentialEqualityPolicy())
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PieChartContainer(
            modifier = Modifier.size(300.dp),
            pieData = pieChartState
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            pieChartState.forEach { pieItem ->
                Card {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(pieItem.color)
                        )
                        {

                        }
                        Text(text = pieItem.name)
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PiechartPreview() {

    var index by remember {
        mutableStateOf(0)
    }
    val pieItems = listOf(
        PieItem("Kotlin", 70f),
        PieItem("Java", 40f),
        PieItem("Python", 80f),
        PieItem("C++", 20f),
        PieItem("Rust", 15f),
        PieItem("Golang", 56f),
        PieItem("Dart", 99f)
    )
    val pieItems2 = listOf(
        PieItem("Kotlin", 40f),
        PieItem("Java", 70f),
        PieItem("Python", 79f),
        PieItem("C++", 19f),
        PieItem("Rust", 51f),
        PieItem("Golang", 65f),
        PieItem("Dart", 17f)
    )
    LazyColumn {
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PieChart(if(index == 0)pieItems else pieItems2)
        Button(onClick = {
            index = if (index == 1) {0} else {1}
        }) {
           Text(text = "Update")
        }
    }
}