package com.geekydroid.playwithcompose.learn.graphics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
private fun GraphicsLearn(modifier: Modifier = Modifier) {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)) {
        val linePath = Path()
        linePath.moveTo(size.width/2f,0f)
        linePath.lineTo(size.width/2f,size.height)
        linePath.moveTo(0f,size.height/2f)
        linePath.lineTo(size.width,size.height/2f)
        val clipPath = Path()
        clipPath.addArc(
            oval = Rect(
                top = size.height*0.4f,
                left = size.width*0.3f,
                right = size.width*0.5f,
                bottom = size.height*0.6f
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 360f
        )
        drawPath(path = clipPath, color = Color.Blue)
        clipPath(clipPath) {
            val innerPath = Path()
            innerPath.addArc(
                oval = Rect(
                    top = size.height*0.4f,
                    left = size.width*0.4f,
                    right = size.width*0.6f,
                    bottom = size.height*0.6f
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 360f
            )
            drawPath(innerPath, color = Color.Yellow)
        }
        drawPath(path = linePath,color = Color.Black, style = Stroke())
    }

}