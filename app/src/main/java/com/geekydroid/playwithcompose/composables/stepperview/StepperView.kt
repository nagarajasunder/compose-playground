package com.geekydroid.playwithcompose.composables.stepperview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StepperView(
    steps: Int = 4
) {
    val path = Path()
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        path.moveTo(0F, size.height / 2F)
        path.lineTo(size.width, size.height / 2f)
        path.lineTo(size.width, (size.height / 2f) + 10f)
        path.lineTo(0F, (size.height / 2F) + 10f)
        var circleStartX = (size.width / steps) / 2F
        for (i in 0 until steps) {
            path.addRoundRect(
                RoundRect(
                    Rect(
                        offset = Offset(circleStartX, (size.height / 2F) - 20f),
                        size = Size(50F, 50F)
                    ),
                    cornerRadius = CornerRadius(50F, 50F)
                )

            )
            circleStartX += (size.width / steps)
        }
        drawPath(path, color = Color.LightGray)
        clipPath(path) {
            drawRect(
                color = Color.Green,
                size = Size(0F,size.height)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StepperViewPreview() {
    StepperView()
}