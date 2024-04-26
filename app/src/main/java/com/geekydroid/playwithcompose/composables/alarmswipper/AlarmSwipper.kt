package com.geekydroid.playwithcompose.composables.alarmswipper

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.ResistanceConfig
import androidx.compose.material.SwipeableState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

private const val TAG = "AlarmSwipper"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlarmSwipper() {


    val animatableLeft = remember {
        Animatable(0F)
    }
    val animatableRight = remember {
        Animatable(0F)
    }
    var refreshKey by remember {
        mutableStateOf(false)
    }

    var showAnimation by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = refreshKey) {
        animatableLeft.animateTo(530f, animationSpec = tween(1500))
        animatableLeft.animateTo(0F, animationSpec = tween(0))
        animatableRight.animateTo(530f, animationSpec = tween(1500))
        animatableRight.animateTo(0f, animationSpec = tween(0))
        refreshKey = !refreshKey
    }


    val sizePx = with(LocalDensity.current) {
        125.dp.toPx()
    }
    val swippeableState = rememberSwipeableState(initialValue = 0F)
    val anchorPoints: Map<Float, Float> =
        mapOf(0f to 0F, sizePx to 1F, -sizePx to 2F)

    LaunchedEffect(key1 = swippeableState.currentValue) {
        if (swippeableState.currentValue == 1F || swippeableState.currentValue == 2F) {
            swippeableState.snapTo(0F)
        }
    }
    LaunchedEffect(key1 = swippeableState.progress) {
        Log.d(TAG, "AlarmSwipper: ${swippeableState.progress}")
        if (swippeableState.progress.to != 0F) {
            showAnimation = false
        } else {
            showAnimation = true
        }
    }

    Box(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clip(CircleShape)
            .background(Color.DarkGray.copy(alpha = 0.8f))
            .drawWithContent {
                if (showAnimation) {
                    rotate(180f) {
                        drawRoundRect(
                            topLeft = Offset(size.width / 2F - 50f, size.height * 0.1f),
                            size = Size(animatableLeft.value, size.height * 0.8f),
                            color = Color.LightGray.copy(alpha = 0.5f),
                            cornerRadius = CornerRadius(100F)
                        )
                    }
                    drawRoundRect(
                        topLeft = Offset(size.width / 2F - 50f, size.height * 0.1f),
                        size = Size(animatableRight.value, size.height * 0.8f),
                        color = Color.LightGray.copy(alpha = 0.5f),
                        cornerRadius = CornerRadius(100F)
                    )
                }
                drawContent()
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.graphicsLayer {
                    alpha = getLeftTextAlpha(swippeableState)
                },
                text = "Snooze",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Text(
                modifier = Modifier.graphicsLayer {
                    alpha = getRightTextAlpha(swippeableState)
                },
                text = "Stop",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(swippeableState.offset.value.roundToInt(), 0)
                }
                .width(90.dp)
                .height(80.dp)
                .clip(CircleShape)
                .swipeable(
                    state = swippeableState,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    anchors = anchorPoints,
                    orientation = Orientation.Horizontal,
                    resistance = ResistanceConfig(1f, 1f)
                )
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            if (swippeableState.currentValue >= (sizePx * 0.8) || swippeableState.currentValue >= (-sizePx * 0.8f)) {
                Icon(Icons.Default.Alarm, contentDescription = null)
            } else {
                Icon(Icons.Default.AlarmOn, contentDescription = null)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
fun getLeftTextAlpha(swippeableState: SwipeableState<Float>): Float {
    return if (swippeableState.progress.to == 2F) {
        1 - swippeableState.progress.fraction
    } else {
        1F
    }
}

@OptIn(ExperimentalMaterialApi::class)
fun getRightTextAlpha(swippeableState: SwipeableState<Float>): Float {
    return if (swippeableState.progress.to == 1F) {
        1 - swippeableState.progress.fraction
    } else {
        1F
    }
}


@Preview(showBackground = true)
@Composable
fun AlarmSwipperPreview() {
    AlarmSwipper()
}