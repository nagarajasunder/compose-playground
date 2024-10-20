package com.geekydroid.playwithcompose.composables.shapes.ticketshape

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class CinemaTicketShape(private val cornerRadius:Float = 48f) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val curveRadius = cornerRadius

        val path = Path().apply {
            arcTo(
                rect = Rect(
                    top = -curveRadius,
                    left = -curveRadius,
                    right = curveRadius,
                    bottom = curveRadius
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )
            arcTo(
                rect = Rect(
                    top = -curveRadius,
                    left = size.width - curveRadius,
                    right = size.width+curveRadius,
                    bottom = curveRadius
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )
            arcTo(
                rect = Rect(
                    top = size.height-curveRadius,
                    left = size.width - curveRadius,
                    right = size.width+curveRadius,
                    bottom = size.height+curveRadius
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )
            arcTo(
                rect = Rect(
                    top = size.height-curveRadius,
                    left = -curveRadius,
                    right = curveRadius,
                    bottom = size.height+curveRadius,
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )
        }
        return Outline.Generic(path)
    }

}


@Preview(showBackground = true)
@Composable
private fun ShapePreview(modifier: Modifier = Modifier) {
    var height by remember { mutableStateOf(0) }
    Box(modifier = Modifier.padding(16.dp)) {
        Card(modifier = Modifier,
            shape = CinemaTicketShape(32f),
            backgroundColor = Color.Blue.copy(alpha = 0.5f)
        ) {
            Row(modifier = Modifier
                .padding(24.dp)
                .onSizeChanged { size ->
                    height = size.height
                },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "India  ->", style = MaterialTheme.typography.h5)
                Text(text = "  SriLanka", style = MaterialTheme.typography.h5)
            }

        }

    }


}
