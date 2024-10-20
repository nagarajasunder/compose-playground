package com.geekydroid.playwithcompose.composables.shapes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun MyCardPreview(modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    var height by remember { mutableStateOf(0) }
    Box(
        modifier = modifier
            .size(300.dp)
            .padding(16.dp)
            .onSizeChanged { size ->
                height = size.height
            }
            .clip(
                CustomShapePlayground(
                    cornerRadius = with(density) { 16.dp.toPx() },
                    viewDividingPosition = (height*0.25f)
                )
            )
            .background(
                color = Color.Green.copy(alpha = 0.5f)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text("Hello World")
    }
}


class CustomShapePlayground(val cornerRadius:Float,val viewDividingPosition:Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val curveRadius = 45.dp
        val curveHeight = curveRadius.value*2f

        val path = Path().apply {
            moveTo(0f,0f)
            //Top Left Arc
            arcTo(
                rect = Rect(
                    top = 0f,
                    left = 0f,
                    right = cornerRadius*2f,
                    bottom = cornerRadius*2
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(size.width,0f)
            //Top Right Arc
            arcTo(
                rect = Rect(
                    top = 0f,
                    left = size.width - (cornerRadius*2),
                    right = size.width,
                    bottom = cornerRadius*2
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            arcTo(
                rect = Rect(
                    top = size.height - viewDividingPosition,
                    left = size.width - cornerRadius,
                    right = size.width + curveHeight,
                    bottom = (size.height - viewDividingPosition) + curveHeight
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )
            lineTo(size.width,size.height)
            //Bottom Right Arc
            arcTo(
                rect = Rect(
                    top = size.height - (cornerRadius*2),
                    left = size.width - (cornerRadius*2),
                    right = size.width,
                    bottom = size.height
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(0f,size.height)
            //Bottom Left Arc
            arcTo(
                rect = Rect(
                    top = size.height - (cornerRadius*2),
                    left = 0f,
                    right = cornerRadius*2f,
                    bottom = size.height
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            arcTo(
                rect = Rect(
                    top = size.height - viewDividingPosition,
                    left = -cornerRadius,
                    right = cornerRadius,
                    bottom = (size.height - viewDividingPosition) + curveHeight
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )

        }

        return Outline.Generic(path)
    }

}

