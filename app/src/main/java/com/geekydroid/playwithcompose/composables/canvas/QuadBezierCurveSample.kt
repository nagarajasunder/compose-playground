package com.geekydroid.playwithcompose.composables.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun QuadBezierCurveSample(
    width: Dp = 400.dp,
    height: Dp = 400.dp,
    x1:Float = 0F,
    y1:Float = 0F
) {

    val path = Path()
    Canvas(
        modifier = Modifier
            .width(width)
            .height(height)
    ) {
        inset(horizontal = 20f, vertical = 20f) {
            path.moveTo(0F,size.height/2F)
            path.quadraticBezierTo(
                x1 = x1,
                y1 = y1,
                x2 = size.width/2f,
                y2 = size.height
            )
            path.moveTo(0F,size.height/2F)
            path.lineTo(0F,size.height)
            path.lineTo(size.width/2f,size.height)
            drawPath(path,style = Stroke(4f),color = Color.Red)

        }
    }

}


@Preview(showBackground = true)
@Composable
fun QuadBezierCurveSamplePreview() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        val height =400.dp
        var x1 by remember {
            mutableStateOf(0f)
        }
        var y1 by remember {
            mutableStateOf(0f)
        }
        QuadBezierCurveSample(
            height = height,
            x1 = x1*height.value,
            y1 = y1*height.value
        )
        Text(text = "X ${x1*height.value}")
        Slider(value = x1, onValueChange = {
            x1 = it
        })
        Text(text = "Y${y1*height.value}")
        Slider(value = y1, onValueChange = {
            y1 = it
        })

    }
}