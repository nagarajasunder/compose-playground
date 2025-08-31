package com.geekydroid.playwithcompose.composables.slider

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
fun CustomSlider(modifier: Modifier = Modifier,progress:Float,onValueChange: (Float) -> Unit) {
    var offsetX by remember { mutableIntStateOf(0) }
    val thumbWidth = 20.dp
    val thumbHeight = 40.dp
    val density = LocalDensity.current
    val thumbWidthPx = with(density) {thumbWidth.toPx()}
    val progressBorderColor = Color(0xff222222)
    val progressContainerColor = Color(0xffb4e0fc)
    val thumbColor = Color(0xff047acd)
    val valueChangeState = rememberUpdatedState(onValueChange)
    BoxWithConstraints {

        val strokeWidth = 6f
        val maxWidthPx = with(density) {maxWidth.toPx()}
        LaunchedEffect(Unit) {
            offsetX = getSlideOffset(maxWidthPx,thumbWidthPx,progress).roundToInt()
        }
        Box(
            modifier = modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { offset ->
                        val updatedXPos = offset.x
                        if (updatedXPos in thumbWidthPx..maxWidthPx) {
                            offsetX = offset.x.coerceAtMost(maxWidthPx-thumbWidthPx).toInt()
                            onValueChange(getSliderProgress(maxWidthPx, updatedXPos))
                        } else {
                            if (updatedXPos > maxWidthPx) {
                                onValueChange(1f)
                            } else {
                                offsetX = 0
                                onValueChange(0f)
                            }
                        }
                    })
                }
                .pointerInput(Unit) {
                    detectDragGestures(onDragStart = { offset ->
                        val updatedXPos = offset.x
                        if (updatedXPos in thumbWidthPx..maxWidthPx) {
                            offsetX = offset.x.coerceAtMost(maxWidthPx-thumbWidthPx).toInt()
                            onValueChange(getSliderProgress(maxWidthPx, updatedXPos))
                        } else {
                            if (updatedXPos > maxWidthPx) {
                                onValueChange(1f)
                            } else {
                                offsetX = 0
                                onValueChange(0f)
                            }
                        }
                    }) { change, dragAmount ->
                        change.consume()
                        val updatedXPos = thumbWidthPx + offsetX + dragAmount.x
                        if (updatedXPos in thumbWidthPx..maxWidthPx) {
                            offsetX += dragAmount.x.roundToInt()
                            onValueChange(getSliderProgress(maxWidthPx, updatedXPos))
                        } else {
                            if (updatedXPos > maxWidthPx) {
                                onValueChange(1f)
                            } else {
                                offsetX = 0
                                onValueChange(0f)
                            }
                        }
                    }
                }
                .drawWithCache {
                    onDrawWithContent {
                        drawSliderLines()
                        if (offsetX > 0) {
                            drawSliderProgressBorder(
                                offsetX.toFloat(),
                                strokeWidth,
                                progressBorderColor
                            )
                            drawSliderProgressContainer(
                                offsetX = offsetX.toFloat(),
                                containerColor = progressContainerColor
                            )
                        }
                        if (offsetX != 1) {
                            drawOuterContainer(borderColor = Color(0xffdedede),strokeWidth = 4f, offsetX = offsetX.toFloat())
                        }
                        drawContent()
                    }
                },
            contentAlignment = Alignment.TopStart
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(thumbHeight.times(2f)),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = DecimalFormat.getInstance().format(progress)
                )
            }
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(x = offsetX, 0)
                    }
                    .shadow(elevation = 8.dp, shape = CircleShape, spotColor = Color.Red)
                    .size(height = (thumbHeight.times(2f)), width = thumbWidth)
                    .clip(CircleShape)
                    .border(width = 2.dp, color = Color.White, shape = CircleShape)
                    .background(color = thumbColor)
            )
        }
    }
}

private fun getSliderProgress(maxWidth:Float,currentPos:Float) : Float {
    return currentPos/maxWidth
}

private fun getSlideOffset(maxWidth: Float,thumbSize:Float,progress: Float) : Float {
    return ((maxWidth-thumbSize)*progress)
}

private fun DrawScope.drawSliderProgressBorder(offsetX:Float,strokeWidth: Float,progressBorderColor:Color) {
    val path = Path()
    path.moveTo(offsetX,size.height*0.3f)
    path.lineTo(0f,size.height*0.3f)
    path.lineTo(0f,size.height*0.7f)
    path.lineTo(offsetX,size.height*0.7f)
    drawPath(path = path, color = progressBorderColor, style = Stroke(width = strokeWidth, join = StrokeJoin.Round))
}

private fun DrawScope.drawOuterContainer(borderColor: Color,strokeWidth: Float,offsetX: Float) {
    val strokePath = Path()
    strokePath.moveTo(offsetX,size.height*0.3f)
    strokePath.lineTo(size.width,size.height*0.3f)
    strokePath.lineTo(size.width,size.height*0.7f)
    strokePath.lineTo(offsetX,size.height*0.7f)
    drawPath(strokePath, color = borderColor, style = Stroke(width = strokeWidth))
}

private fun DrawScope.drawSliderLines(
    color: Color = Color(0xfff7f7f7),
    strokeWidth: Float = 2f,
    spaceWidth: Dp = 15.dp
) {
    val totalLines = ((size.width).dp/spaceWidth).roundToInt()
    val linePath = Path()
    var startX = spaceWidth.toPx()
    for (i in 0 until totalLines) {
        linePath.moveTo(startX,size.height*0.3f)
        linePath.lineTo(startX,size.height*0.7f)
        startX+=spaceWidth.toPx()
    }
    drawPath(path = linePath,color = color, style = Stroke(width = strokeWidth))
}

private fun DrawScope.drawSliderProgressContainer(offsetX: Float, containerColor: Color) {
    val path = Path()
    path.moveTo(offsetX,size.height*0.31f)
    path.lineTo(3.5f,size.height*0.31f)
    path.lineTo(3.5f,size.height*0.69f)
    path.lineTo(offsetX,size.height*0.69f)
    drawPath(path = path, color = containerColor)
}


@Preview(showBackground = true)
@Composable
private fun CustomSliderPreview() {
    var progress by remember { mutableFloatStateOf(0f) }
    Column(modifier = Modifier.padding(24.dp)) {
        CustomSlider(progress = progress) { newProgress ->
            progress = newProgress
        }
        Slider(value = progress, onValueChange = {
            progress = it
        })
        Button(onClick = {
            progress = 0f
        }) {
            Text("Reset")
        }
    }
}