package com.geekydroid.playwithcompose.composables.zoomimage

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geekydroid.playwithcompose.R

@Preview(showSystemUi = true)
@Composable
fun ZoomImage(modifier: Modifier = Modifier) {
    BoxWithConstraints {
        var scale by remember { mutableFloatStateOf(1f) }
        var offset by remember { mutableStateOf(Offset.Zero) }
        var rotation by remember { mutableFloatStateOf(0f) }
        val transformableState = rememberTransformableState { zoomChange, panChange, rotationChange ->
            scale = (scale*(zoomChange)).coerceIn(0.8f,5f)
            val extraWidth = (scale-1)*constraints.maxWidth
            val extraHeight = (scale-1)*constraints.maxHeight
            val maxX = extraWidth/2
            val maxY = extraHeight/2
            offset = if (scale > 1f) {
                Offset(
                    (panChange.x+offset.x).coerceIn(-maxX,maxX),
                    (panChange.y+offset.y).coerceIn(-maxY,maxY)
                )
            } else {
                Offset.Zero
            }
            rotation += rotationChange
        }
        Image(
            modifier = modifier
                .border(width = 1.dp, color = Color.Black)
                .transformable(transformableState)
                .graphicsLayer {
                    this.scaleX = scale
                    this.scaleY = scale
                    this.translationX = offset.x
                    this.translationY = offset.y
                    this.rotationZ = rotation
                },
            painter = painterResource(R.drawable.dog),
            contentDescription = null
        )
        Text(
            text = buildAnnotatedString {
                append("Offset(${offset.x},${offset.y})\n")
                append("Scale ${scale}")
            }
        )
    }
}