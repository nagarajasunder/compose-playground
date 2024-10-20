package com.geekydroid.playwithcompose.composables.shapes.zigzagshape

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.geekydroid.playwithcompose.composables.shapes.zigzagshape.ZigZapShapeUtils.drawZigZagCurve
import kotlin.math.max
import kotlin.math.min

class ZigZagCurveShape(
    private val noOfCrosses: Int = 36
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val crossSize = size.width/noOfCrosses
        val path = Path().apply {
            drawZigZagCurve(
                path = this,
                crossSize = crossSize,
                noOfCrosses = noOfCrosses,
                size = size
            )
            drawZigZagCurve(
                path = this,
                crossSize = crossSize,
                noOfCrosses = noOfCrosses,
                size = size,
                crossPosition = CrossPosition.BOTTOM
            )
        }

        return Outline.Generic(path)
    }
}


@Preview(showBackground = true)
@Composable
private fun ShapePreview(modifier: Modifier = Modifier) {
    Card(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
        .height(300.dp)
        .clip(ZigZagCurveShape(noOfCrosses = 24)),
        backgroundColor = Color.Red.copy(alpha = 0.5f)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)) {
            Text(text = "Hello World")
        }


    }



}