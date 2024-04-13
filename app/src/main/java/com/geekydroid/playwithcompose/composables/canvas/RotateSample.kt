package com.geekydroid.playwithcompose.composables.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RotateCanvas(
    shapeSize: Dp = 300.dp,
    rotateAngle:Float = 0F,
    horizontal:Float = 0F,
    vertical:Float = 0F
) {
    val path = Path()
    Canvas(modifier = Modifier.size(shapeSize).clipToBounds()) {
        inset(
            horizontal = 20f,
            vertical = 20F
        ) {
            path.moveTo(size.width / 2f, 0F)
            path.lineTo(size.width / 2F, size.height)
            path.moveTo(0F, size.height / 2F)
            path.lineTo(size.width, size.height / 2F)
            drawPath(path = path, color = Color.Yellow, style = Stroke(width = 10f))
            withTransform({
                translate(
                    left = horizontal,
                    top = vertical
                )
                rotate(rotateAngle)
            }) {
                drawRect(
                    color = Color.Red,
                    topLeft = Offset(size.height / 2F - 50F, size.width / 2F - 50F),
                    size = Size(100F, 100F),
                    style = Stroke(width = 10f)
                )
            }
        }
    }
}

@Composable
fun RotateSample() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val size = 300.dp
        var rotateAngle by remember {
            mutableStateOf(0F)
        }
        var horizontalOffset by remember {
            mutableStateOf(0F)
        }
        var verticalOffset by remember {
            mutableStateOf(0F)
        }
        RotateCanvas(
            shapeSize = size,
            rotateAngle = rotateAngle*360F,
            horizontal = horizontalOffset*150F,
            vertical = verticalOffset*150F
        )
        Text(text = "Rotate ${rotateAngle*360F}")
        Slider(
            value = rotateAngle,
            onValueChange = {
                rotateAngle = it
            }
        )
        Text(text = "Horizontal offset ${horizontalOffset*(150.dp.value)}")
        Slider(
            value = horizontalOffset,
            onValueChange = {
                horizontalOffset = it
            }
        )
        Text(text = "Vertical offset ${verticalOffset*(150.dp.value)}")
        Slider(
            value = verticalOffset,
            onValueChange = {
                verticalOffset = it
            }
        )
    }
}


@Preview(showSystemUi = true, device = Devices.NEXUS_5)
@Composable
fun RotateSamplePreview() {
    RotateSample()
}