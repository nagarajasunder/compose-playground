package com.geekydroid.playwithcompose.composables.barchart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import kotlinx.coroutines.launch

@Composable
fun BarChartV2(
    barData: List<BarItem>
) {
    BoxWithConstraints {
        val barWidth = with(LocalDensity.current) { 50.dp.toPx() }
        val maxWidth = with(LocalDensity.current) { maxWidth.toPx() }
        val maxHeight = with(LocalDensity.current) { maxHeight.toPx() }
        val totalLines = (maxWidth/barWidth).toInt()
        val totalHorizontalLines = 6
        val barMaxHeight = maxHeight * 0.9f
        val barMaxWidth = maxWidth*0.9f
        val horizontalWidthPerLine = barMaxWidth/totalLines
        val verticalHeightPerLine = barMaxHeight / totalHorizontalLines
        val barRange = BarChartUtils.findBarRange(barData, maxHorizontalLines = totalHorizontalLines)
        val barDataList by remember {
            val barDataList = barData.map { barItem ->
                val percentage = BarChartUtils.getSizePercentage(barRange, barItem)
                BarData(
                    name = barItem.name,
                    value = barItem.value,
                    animatable = Animatable(initialValue = barMaxHeight),
                    targetHeight = -(barMaxHeight * percentage)
                )
            }
            mutableStateOf(barDataList)
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
            repeat(totalHorizontalLines) {
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
fun BarChartV2Preview() {
    val barItems = listOf(
        BarItem(name = "Apr", value = 7048f),
        BarItem(name = "Mar", value = 6000f),
        BarItem(name = "Feb", value = 6618f),
        BarItem(name = "Jan", value = 9418.82f),
        BarItem(name = "Dec", value = 7096f),
        BarItem(name = "Nov", value = 11854f),
        BarItem(name = "Feb", value = 6618f),
        BarItem(name = "Apr", value = 7048f),
        BarItem(name = "Mar", value = 6000f),
        BarItem(name = "Feb", value = 11328f),
        BarItem(name = "Jan", value = 10842.82f),
        BarItem(name = "Dec", value = 19756f),
        BarItem(name = "Nov", value = 18234f),
        BarItem(name = "Feb", value = 14567f),
    )
    var chartVisibility by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = chartVisibility) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(8.dp)
            ) {
                BarChartV2(barItems)
            }
        }
        Button(onClick = { chartVisibility = !chartVisibility }) {
            Text(text = if(chartVisibility) "Hide" else "Show")
        }
    }
}