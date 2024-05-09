package com.geekydroid.playwithcompose.composables.barchart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.geekydroid.playwithcompose.composables.barchart.BarChartUtils.findBarRange
import com.geekydroid.playwithcompose.composables.barchart.BarChartUtils.getSizePercentage
import kotlinx.coroutines.launch


data class BarItem(
    val name: String,
    val value: Float
)

data class BarData(
    val name: String,
    val value: Float,
    val animatable: Animatable<Float, AnimationVector1D>,
    val targetHeight: Float
)

data class BarRange(
    val minValue: Float,
    val maxValue: Float,
    val rangeInterval:Float,
    val intervals:List<Float>
    )

@Composable
fun BarChart(
    barData: List<BarItem>
) {
    val totalLines = 6
    BoxWithConstraints {
        val maxWidth = with(LocalDensity.current) { maxWidth.toPx() }
        val maxHeight = with(LocalDensity.current) { maxHeight.toPx() }
        val barMaxHeight = maxHeight * 0.9f
        val barMaxWidth = maxWidth*0.9f
        val horizontalWidthPerLine = barMaxWidth/totalLines
        val verticalHeightPerLine = barMaxHeight / totalLines
        val barRange = findBarRange(barData)
        val barDataList = barData.map { barItem ->
            val percentage = getSizePercentage(barRange, barItem)
            BarData(
                name = barItem.name,
                value = barItem.value,
                animatable = Animatable(initialValue = barMaxHeight),
                targetHeight = -(barMaxHeight * percentage)
            )
        }
        LaunchedEffect(key1 = Unit) {
            barDataList.forEach {
                launch {
                    it.animatable.animateTo(it.targetHeight, animationSpec = tween(500))
                }
            }
        }
        val lineColor = Color.Gray.copy(alpha = 0.5f)
        val textMeasurer = rememberTextMeasurer()
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            var lineOffsetY = verticalHeightPerLine
            repeat(totalLines) {
                drawLine(
                    start = Offset(horizontalWidthPerLine, lineOffsetY),
                    end = Offset(size.width, lineOffsetY),
                    color = lineColor,
                    strokeWidth = 2f
                )
                lineOffsetY += verticalHeightPerLine
            }
            var barOffsetX = horizontalWidthPerLine
            val barGradient = Brush.linearGradient(
                colors = listOf(Color(0XFF314755), Color(0XFF26a0da)),
                tileMode = TileMode.Repeated
            )
            barDataList.forEach { barItem ->
                drawRoundRect(
                    topLeft = Offset(barOffsetX, barMaxHeight),
                    size = Size(horizontalWidthPerLine * 0.6f, barItem.animatable.value),
                    brush = barGradient,
                    cornerRadius = CornerRadius(16f),

                )
                barOffsetX += horizontalWidthPerLine
            }
            //Drawing X-axis labels
            var textOffsetX = horizontalWidthPerLine
            barDataList.forEach { barData ->
                val measuredText = textMeasurer.measure(
                    text = barData.name,
                    maxLines = 1,
                    constraints = Constraints.fixedWidth(
                        width = (horizontalWidthPerLine * 0.6f).toInt()
                    ),
                    overflow = TextOverflow.Ellipsis
                )
                drawText(
                    textMeasurer,
                    barData.name,
                    topLeft = Offset(textOffsetX, barMaxHeight),
                    size = measuredText.size.toSize(),
                    style = TextStyle(textAlign = TextAlign.Center)
                )
                textOffsetX += horizontalWidthPerLine
            }
            //Drawing Y-axis labels
            var textOffsetY = verticalHeightPerLine*0.8f
            barRange.intervals.reversed().forEach { interval ->
                val measuredText = textMeasurer.measure(
                    text = "${interval.toInt()}",
                    maxLines = 1,
                    constraints = Constraints.fixed(
                        width = horizontalWidthPerLine.toInt(),
                        height = (verticalHeightPerLine*0.5f).toInt()
                    ),
                    overflow = TextOverflow.Ellipsis
                )
                drawText(
                    textMeasurer,
                    "${interval.toInt()}",
                    topLeft = Offset(horizontalWidthPerLine*0.2f, textOffsetY),
                    size = measuredText.size.toSize(),
                    style = TextStyle(textAlign = TextAlign.Left)
                )
                textOffsetY += verticalHeightPerLine
            }
        }
    }
}




@Preview(showSystemUi = true)
@Composable
fun BarChartPreview() {
    val barItems = listOf(
        BarItem(name = "Apr", value = 7048f),
        BarItem(name = "Mar", value = 6000f),
        BarItem(name = "Feb", value = 6618f),
        BarItem(name = "Jan", value = 9418.82f),
        BarItem(name = "Dec", value = 7096f),
        BarItem(name = "Nov", value = 11854f),
    )
    Column {
        Card(
            Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(8.dp)
        ) {
            BarChart(barItems)
        }
    }
}