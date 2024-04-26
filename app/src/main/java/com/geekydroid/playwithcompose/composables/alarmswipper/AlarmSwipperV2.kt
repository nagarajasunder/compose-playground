package com.geekydroid.playwithcompose.composables.alarmswipper

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.ui.platform.LocalContext
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
fun AlarmSwipperV2(
    swipeEnabled:Boolean = true,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit
) {
    BoxWithConstraints {

        val animatableLeft = remember {
            Animatable(0F)
        }
        val animatableRight = remember {
            Animatable(0F)
        }
        var refreshKey by remember {
            mutableStateOf(false)
        }

        val swipeBoxSize = 90.dp
        val swipeBoxPadding = 12.dp

        val swipeSize = with(LocalDensity.current) {
            maxWidth.div(2).minus(swipeBoxSize/2f).minus(swipeBoxPadding*2f).toPx()
        }

        val swipeAnimSize = with(LocalDensity.current) {
            maxWidth.div(2).minus(swipeBoxPadding).toPx()
        }

        LaunchedEffect(key1 = refreshKey) {
            animatableLeft.animateTo(swipeAnimSize, animationSpec = tween(1500))
            animatableLeft.animateTo(0F, animationSpec = tween(0))
            animatableRight.animateTo(swipeAnimSize, animationSpec = tween(1500))
            animatableRight.animateTo(0f, animationSpec = tween(0))
            refreshKey = !refreshKey
        }

        val swippeableState = rememberSwipeableState(initialValue = 0F)
        val anchorPoints: Map<Float, Float> =
            mapOf(0f to 0F, swipeSize to 1F, -swipeSize to 2F)

        LaunchedEffect(key1 = swippeableState.currentValue) {
            if (swippeableState.currentValue == 1F) {
                onSwipeRight()
                //swippeableState.snapTo(0F)
            } else if (swippeableState.currentValue == 2F) {
                onSwipeLeft()
                //swippeableState.snapTo(0F)
            }
        }

        Box(
            modifier = Modifier
                .padding(swipeBoxPadding)
                .fillMaxWidth()
                .height(100.dp)
                .clip(CircleShape)
                .background(Color.DarkGray.copy(alpha = 0.8f))
                .drawWithContent {
                    if (swippeableState.progress.to == 0f) {
                        rotate(180f) {
                            drawRoundRect(
                                topLeft = Offset(
                                    size.width / 2F - (swipeBoxSize.value / 2f),
                                    size.height * 0.1f
                                ),
                                size = Size(animatableLeft.value, size.height * 0.8f),
                                color = Color.LightGray.copy(alpha = 0.5f),
                                cornerRadius = CornerRadius(100F)
                            )
                        }
                        drawRoundRect(
                            topLeft = Offset(
                                size.width / 2F - (swipeBoxSize.value / 2f),
                                size.height * 0.1f
                            ),
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
                    .width(swipeBoxSize)
                    .height(80.dp)
                    .clip(CircleShape)
                    .swipeable(
                        state = swippeableState,
                        enabled = swipeEnabled,
                        thresholds = { _, _ -> FractionalThreshold(0.3f) },
                        anchors = anchorPoints,
                        orientation = Orientation.Horizontal,
                        resistance = ResistanceConfig(1f, 1f)
                    )
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    enter = fadeIn(),
                    exit = fadeOut(),
                    visible = swippeableState.progress.to == 0f
                ) {
                    Icon(Icons.Default.Alarm, contentDescription = null)
                }
                AnimatedVisibility(
                    enter = fadeIn(),
                    exit = fadeOut(),
                    visible = swippeableState.progress.to != 0F
                ) {
                    Icon(Icons.Default.AlarmOn, contentDescription = null)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AlarmSwipperV2Preview() {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxWidth()) {
        var swipeEnabled by remember {
            mutableStateOf(true)
        }
        AlarmSwipperV2(
            swipeEnabled = swipeEnabled,
            onSwipeLeft = {
                swipeEnabled = false
                Toast.makeText(context,"Swipe left",Toast.LENGTH_SHORT).show()
            },
            onSwipeRight = {
                swipeEnabled = false
                Toast.makeText(context,"Swipe right",Toast.LENGTH_SHORT).show()
            }
        )
    }
}