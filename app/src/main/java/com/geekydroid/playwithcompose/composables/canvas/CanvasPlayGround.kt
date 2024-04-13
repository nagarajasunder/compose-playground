package com.geekydroid.playwithcompose.composables.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CanvasPlayground() {

    val path = Path()
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0XFF812F79), Color(0XFF2C1961))
    )
    Canvas(modifier = Modifier.size(300.dp)) {
        inset(horizontal = 20f, vertical = 20f) {
            path.moveTo(30F,30F)
            path.lineTo(60f, 60f)
            path.lineTo(30F,90F)
            path.lineTo(0F,60F)
            path.lineTo(30F,30F)
            drawPath(path, brush = gradient)
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
    CanvasPlayground()
}