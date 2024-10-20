package com.geekydroid.playwithcompose.composables.shapes.zigzagshape

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.geekydroid.playwithcompose.R
import com.geekydroid.playwithcompose.composables.shapes.zigzagshape.ZigZapShapeUtils.drawZigZagDown
import com.geekydroid.playwithcompose.composables.shapes.zigzagshape.ZigZapShapeUtils.drawZigZagUp

enum class CrossPosition {
    TOP,
    BOTTOM
}

enum class ZigZagPathPosition {
    UP,
    DOWN
}



class ZigZagShape(
    private val noOfCrosses: Int = 36,
    private val zigZagPathPosition: ZigZagPathPosition = ZigZagPathPosition.UP
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val crossSize = size.width/noOfCrosses
        val path = Path().apply {
            when(zigZagPathPosition) {
                ZigZagPathPosition.UP -> {
                    drawZigZagUp(
                        path = this,
                        size = size,
                        crossSize = crossSize,
                        noOfCrosses = noOfCrosses,
                        crossPosition = CrossPosition.TOP
                    )
                }
                ZigZagPathPosition.DOWN -> {
                    drawZigZagDown(
                        path = this,
                        crossSize = crossSize,
                        noOfCrosses = noOfCrosses,
                        size = size,
                        crossPosition = CrossPosition.TOP
                    )
                }
            }
            lineTo(size.width,0f)
            lineTo(size.width,size.height)
            when(zigZagPathPosition) {
                ZigZagPathPosition.UP -> {
                    drawZigZagUp(
                        path = this,
                        size = size,
                        crossSize = crossSize,
                        noOfCrosses = noOfCrosses,
                        crossPosition = CrossPosition.BOTTOM
                    )
                }
                ZigZagPathPosition.DOWN -> {
                    drawZigZagDown(
                        path = this,
                        crossSize = crossSize,
                        noOfCrosses = noOfCrosses,
                        size = size,
                        crossPosition = CrossPosition.BOTTOM
                    )
                }
            }

            lineTo(0f,size.height)
        }

        return Outline.Generic(path)
    }
}



@Preview(showSystemUi = true)
@Composable
private fun ShapeLazyColumnPreview(modifier: Modifier = Modifier) {
    val colors = listOf(Color.Red, Color.Blue, Color.Cyan, Color.Green, Color.Magenta)
    val lazyListState = rememberLazyListState()
    val density = LocalDensity.current
    val scrollOffset = with(density) {(300.dp * 10).toPx()}
    val scrollDuration = with(density) {300.dp.toPx()*20}
    LaunchedEffect(Unit) {
        while (true) {
            lazyListState.animateScrollBy(scrollOffset, animationSpec = tween(scrollDuration.toInt(), easing = LinearEasing))
            lazyListState.animateScrollBy(
                -scrollOffset,
                animationSpec = tween(scrollDuration.toInt(), easing = LinearEasing)
            )

        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        state = lazyListState,
        userScrollEnabled = false,
        horizontalAlignment = Alignment.CenterHorizontally) {
        items(10) { index ->
            Box(
                modifier = Modifier
                    .clip(ZigZagShape(zigZagPathPosition = ZigZagPathPosition.UP, noOfCrosses = 20))
                    .size(300.dp)
                    .background(color = (colors[index % colors.size]).copy(alpha = 0.5f))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShapePreview(modifier: Modifier = Modifier) {
    Box(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
        .height(300.dp)
        .clip(ZigZagCurveShape(noOfCrosses = 35))
        .background(color = Color.Blue.copy(alpha = 0.5f))) {

    }


}