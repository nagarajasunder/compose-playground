package com.geekydroid.playwithcompose.composables.waterbottle

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun WaterBottleV2(
    fillSize: Float,
    bottleHeight: Dp = 400.dp
) {

    val waterBottlePath = Path()
    val fillSizeAnim by animateFloatAsState(targetValue = fillSize, animationSpec = tween(250, easing = LinearEasing))
    Canvas(
        modifier = Modifier
            .width(200.dp)
            .height(bottleHeight)
    ) {
        inset(horizontal = 20f, vertical = 20f) {
            waterBottlePath.moveTo(0F, size.height * 0.4f)
            waterBottlePath.lineTo(0F, size.height - 20F)
            waterBottlePath.quadraticBezierTo(
                x1 = 0F,
                y1 = size.height,
                x2 = 20F,
                y2 = size.height
            )
            waterBottlePath.lineTo(size.width - 20f, size.height)
            waterBottlePath.quadraticBezierTo(
                x1 = size.width,
                y1 = size.height,
                x2 = size.width,
                y2 = size.height - 20f
            )
            waterBottlePath.lineTo(size.width, (size.height * 0.4f))
            waterBottlePath.quadraticBezierTo(
                x1 = size.width,
                y1 = size.height * 0.3f,
                x2 = size.width - 100f,
                y2 = size.height * 0.25f
            )
            waterBottlePath.moveTo(0F, size.height * 0.4f)
            waterBottlePath.quadraticBezierTo(
                x1 = 0F,
                y1 = size.height * 0.3f,
                x2 = 100f,
                y2 = size.height * 0.25f
            )
            waterBottlePath.lineTo(size.width - 100f, size.height * 0.25f)
            drawRoundRect(
                color = Color(0XFFa0d9ef),
                size = Size(size.width / 2F, size.height * 0.1f),
                topLeft = Offset(size.width * 0.25f, size.height * 0.15f),
                cornerRadius = CornerRadius(12f, 12f)
            )
            drawPath(waterBottlePath, color = Color.LightGray)
            clipPath(
                path = waterBottlePath,
                clipOp = ClipOp.Intersect
            ) {
                rotate(180f) {
                    drawRect(
                        color = Color(0XFF20a7db),
                        size = Size(size.width, fillSizeAnim)
                    )
                }
            }
        }
    }

}



@Preview(showBackground = true)
@Composable
fun WaterBottleV2Preview() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        val bottleHeight =400.dp
        var fillSize by remember {
            mutableStateOf(0F)
        }
        WaterBottleV2(
            bottleHeight = bottleHeight,
            fillSize = fillSize
        )
        Button(onClick = {
            fillSize+=(bottleHeight.value*0.2f)
        }) {
            Text(text = "Fill Water")
        }
        Button(onClick = {
            fillSize=0f
        }) {
            Text(text = "Reset")
        }
    }
}