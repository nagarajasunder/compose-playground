package com.geekydroid.playwithcompose.composables.canvas

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CanvasFlower(
    width: Dp = 400.dp,
    height: Dp = 400.dp
) {

    val infiniteTransition = rememberInfiniteTransition()
    val animateAngle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 180F,
        animationSpec = infiniteRepeatable(animation = tween(5000), repeatMode = RepeatMode.Reverse)
    )
    val path = Path()
    val gradientColors = listOf(
        Color(0XFF8360c3),
        Color(0XFF2ebf91)
    )
    val brush = Brush.linearGradient(colors = gradientColors)
    Canvas(
        modifier = Modifier
            .width(width)
            .height(height)
    ) {
        inset(horizontal = 30f, vertical = 30f) {
            path.moveTo(0F,size.height/2f)
            path.quadraticBezierTo(
                x1 = size.width/2F,
                y1 = 0F,
                x2 = size.width,
                y2 = size.height/2F
            )
            path.quadraticBezierTo(
                x1 = size.width/2F,
                y1 = size.height,
                x2 = 0F,
                y2 = size.height/2F
            )
            path.moveTo(size.width/2F,0F)
            path.quadraticBezierTo(
                x1 = 0F,
                y1 = size.height/2F,
                x2 = size.width/2F,
                y2 = size.height
            )
            path.quadraticBezierTo(
                x1 = size.width,
                y1 = size.height/2F,
                x2 = size.width/2f,
                y2 = 0F
            )
            path.moveTo(size.width*0.9f,size.height*0.1f)
            path.quadraticBezierTo(
                x1 = size.width*0.1f,
                y1 = size.height*0.1F,
                x2 = size.width*0.1f,
                y2 = size.height*0.9f
            )
            path.quadraticBezierTo(
                x1 = size.width*0.9f,
                y1 = size.height*0.9f,
                x2 = size.width*0.9f,
                y2 = size.height*0.1f
            )
            path.moveTo(size.width*0.9f,size.height*0.9f)
            path.quadraticBezierTo(
                x1 = size.width*0.91f,
                y1 = size.height*0.1f,
                x2 = size.width*0.1f,
                y2 = size.height*0.1f
            )
            path.quadraticBezierTo(
                x1 = size.width*0.1F,
                y1 = size.height*0.9f,
                x2 = size.width*0.9f,
                y2 = size.height*0.9f
            )
            rotate(degrees = animateAngle) {
                drawPath(path = path,brush = brush, style = Stroke(10f, cap = StrokeCap.Round))
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun CanvasFlowerPreview() {
    CanvasFlower()
}