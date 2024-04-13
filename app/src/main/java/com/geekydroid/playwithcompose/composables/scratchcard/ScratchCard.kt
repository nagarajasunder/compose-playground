package com.geekydroid.playwithcompose.composables.scratchcard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.geekydroid.playwithcompose.R

@Composable
fun ScratchCanvas(
    onDragComplete: () -> Unit
) {
    val path by remember {
        mutableStateOf(Path())
    }
    var currentOffset by remember {
        mutableStateOf<Offset?>(null)
    }
    val baseImage = ImageBitmap.imageResource(id = R.drawable.base_image)
    val overlayImage = ImageBitmap.imageResource(id = R.drawable.overlay_image)
    Canvas(
        modifier = Modifier
            .size(300.dp)
            .clipToBounds()
            .clip(RoundedCornerShape(8.dp))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        path.moveTo(it.x, it.y)
                        currentOffset = it
                    },
                    onDragEnd = onDragComplete
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
        clipPath(
            path = path,
            clipOp = ClipOp.Intersect
        ) {
            inset(horizontal = 20f, vertical = 20f) {
                drawImage(
                    image = baseImage,
                    dstSize = IntSize(size.width.toInt(), size.height.toInt())
                )
            }
        }
    }
}

@Composable
fun ScratchCard(
    cardSize: Dp = 300.dp
) {
    var showOffer by remember {
        mutableStateOf(false)
    }
    if (showOffer) {
        Card(
            modifier = Modifier.size(cardSize),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color.White
        ) {
            Image(
                painter = painterResource(id = R.drawable.base_image), contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        }
    } else {
        ScratchCanvas {
            showOffer = true
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DrawCanvasPreview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ScratchCard()
    }
}