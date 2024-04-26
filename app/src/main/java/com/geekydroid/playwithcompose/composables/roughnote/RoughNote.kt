package com.geekydroid.playwithcompose.composables.roughnote

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

private const val TAG = "RoughNote"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoughNote() {
    BoxWithConstraints {


        LaunchedEffect(key1 = Unit) {
            Log.d(TAG, "RoughNote: ${maxWidth}")
        }


//        Box(
//            modifier = Modifier
//                .width(maxWidth)
//                .offset(x = 0.dp)
//                .background(Color.Red)
//        ) {
//            Text(text = "Hello world")
//        }
//
//        Box(
//            modifier = Modifier
//                .width(maxWidth)
//                .offset(x = maxWidth/2f)
//                .background(Color.Yellow)
//        ) {
//            Text(text = "Hello world")
//        }

        val width = 96.dp
        val squareSize = 48.dp

        val swipeableState = rememberSwipeableState(0)
        val sizePx = with(LocalDensity.current) { (maxWidth - squareSize).toPx() }
        val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
        ) {
            Box(
                Modifier
                    .swipeable(
                        state = swipeableState,
                        anchors = anchors,
                        thresholds = { _, _ -> FractionalThreshold(0.3f) },
                        orientation = Orientation.Horizontal
                    )
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                    .size(squareSize)
                    .background(Color.DarkGray)
            )
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun RoughNotePreview() {
    RoughNote()
}