package com.geekydroid.playwithcompose.composables.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CanvasPlayground(
    width:Dp = 400.dp,
    height:Dp = 400.dp
) {

    val path = Path()
    Canvas(
        modifier = Modifier
            .background(Color.Yellow)
            .width(width)
            .height(height)
    ) {
        inset(horizontal = 30f, vertical = 30f) {
            rotate(45f, pivot = Offset(size.width/2F,size.height)) {
                path.lineTo(size.width,0F)
            }
            drawPath(path = path,color = Color.Black, style = Stroke(10f))
        }
    }

}

@Composable
fun KotlinShape() {

    val path = Path()
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0XFF812F79), Color(0XFF2C1961))
    )
    Canvas(modifier = Modifier.size(300.dp)) {
        inset(horizontal = 20f, vertical = 20f) {
            path.lineTo(0F, size.height)
            path.lineTo(size.width, size.height)
            path.lineTo(size.width / 2f, size.height / 2)
            path.lineTo(size.width, 0F)
            path.lineTo(0F, 0F)
            drawPath(path, brush = gradient)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CanvasPlaygroundPreview() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        val height =400.dp
        CanvasPlayground(
            height = height,
        )
    }
}