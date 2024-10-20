    package com.geekydroid.playwithcompose.composables.scratchcard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.geekydroid.playwithcompose.R
import com.geekydroid.playwithcompose.composables.shapes.zigzagshape.ZigZagCurveShape
import com.geekydroid.playwithcompose.composables.shapes.zigzagshape.ZigZagShape

    @Composable
fun ScratchCanvasV2(
    onScratchComplete: () -> Unit
) {
    val path by remember {
        mutableStateOf(Path())
    }
    var currentOffset by remember {
        mutableStateOf<Offset?>(null)
    }
    var fillSize by remember {
        mutableStateOf(Size.Zero)
    }

    val sizeAnim by animateSizeAsState(targetValue = fillSize, tween(100))
    val baseImage = ImageBitmap.imageResource(id = R.drawable.base_image)
    val overlayImage = ImageBitmap.imageResource(id = R.drawable.overlay_image)
    AnimatedVisibility(visible = fillSize != Size.Zero,enter = fadeIn(), exit = fadeOut()) {
        Card(
            modifier = Modifier
                .size(300.dp)
                .clip(ZigZagShape())
        ) {
            Image(painter = painterResource(id = R.drawable.base_image), contentDescription = null)
        }
    }
    if (fillSize == Size.Zero) {
        Canvas(
            modifier = Modifier
                .size(300.dp)
                .clipToBounds()
                .clip(ZigZagCurveShape(20))
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            currentOffset = it
                        },
                        onDragEnd = {
                            //fillSize = Size(size.width.toFloat(), size.height.toFloat())
                            onScratchComplete()
                        }
                    ) { change, _ ->
                        change.consume()
                        currentOffset = change.position
                    }
                }
        ) {
            drawImage(
                image = overlayImage,
                dstSize = IntSize(size.width.toInt(), size.height.toInt())
            )
            currentOffset?.let {
                path.addOval(
                    Rect(it, 50F)
                )
            }
            path.addRect(
                Rect(Offset.Zero,sizeAnim)
            )
            clipPath(
                path = path,
                clipOp = ClipOp.Intersect
            ) {
                drawImage(
                    image = baseImage,
                    dstSize = IntSize(size.width.toInt(), size.height.toInt())
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ScratchCanvasPreview() {
    var refresh by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    ) {
        AnimatedVisibility(visible = refresh) {
            ScratchCanvasV2 {

            }
        }
        Button(onClick = {
            refresh = !refresh
        }) {
            Text("Refresh")
        }
    }
}