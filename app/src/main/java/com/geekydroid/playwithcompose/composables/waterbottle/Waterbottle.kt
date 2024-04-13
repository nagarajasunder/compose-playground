package com.geekydroid.playwithcompose.composables.waterbottle

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun WaterBottle(
    height: Dp = 400.dp,
    width: Dp = 200.dp,
    maxWaterLimit:Float = 2500F,
    fillSize: Float = 0F,
) {
    val path = Path()
    val sizeAnim = animateFloatAsState(targetValue = fillSize)
    Box(
        modifier = Modifier
            .height(height)
            .width(width),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds(),
        ) {
            inset(horizontal = 20f, vertical = 20f) {
                path.moveTo(0F, size.height * 0.3f)
                //Bottom portion
                path.lineTo(0F, size.height)
                path.lineTo(size.width, size.height)
                path.lineTo(size.width, size.height - size.height * 0.7f)
                path.lineTo(0F, size.height * 0.3f)

                //Top Portion
                path.moveTo(0F, size.height * 0.3f)
                path.lineTo(size.width * 0.3f, size.height * 0.2f)
                path.lineTo(size.width - size.width * 0.35f, size.height * 0.2f)
                path.lineTo(size.width, size.height * 0.3f)


                drawPath(
                    path = path,
                    color = Color.LightGray
                )
                drawRoundRect(
                    color = Color(0XFF1c96c5),
                    cornerRadius = CornerRadius(6F, 6F),
                    size = Size(size.width * 0.35f, size.height * 0.1f),
                    topLeft = Offset(size.width * 0.3f, size.height * 0.1f)
                )
                clipPath(
                    path = path
                ) {
                    rotate(180f) {
                        drawRect(
                            color = Color(0XFF20a7db),
                            size = Size(size.width, sizeAnim.value)
                        )
                    }
                }
            }
        }
        Text(text = "${height.value - (height.value*0.1f) - fillSize}")
    }

}

@Preview(showBackground = true)
@Composable
fun WaterBottlePreview() {
    var fillSize by remember {
        mutableStateOf(0F)
    }
    val waterBottleHeight = 400.dp
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WaterBottle(
            fillSize = fillSize,
            height = waterBottleHeight
        )
        Button(onClick = {
            fillSize += (waterBottleHeight.value*0.2f)
        }) {
            Text(text = "Drink Water")
        }
        Button(onClick = { fillSize = 0F }) {
            Text(text = "Reset")
        }
    }
}