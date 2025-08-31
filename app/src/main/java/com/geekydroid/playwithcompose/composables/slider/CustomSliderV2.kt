package com.geekydroid.playwithcompose.composables.slider

import android.annotation.SuppressLint
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.DragScope
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.GestureCancellationException
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.SliderColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastMinByOrNull
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


val ThumbRadius = 20.dp

@Composable
fun CustomSliderV2(
    modifier: Modifier = Modifier,
    value:Float,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onValueChange: (Float) -> Unit
) {
    val onValueChangeState = rememberUpdatedState(onValueChange)
    BoxWithConstraints {
        val widthPx = constraints.maxWidth.toFloat()
        val maxPx:Float
        val minPx:Float
        with(LocalDensity.current) {
            maxPx = max(widthPx - ThumbRadius.toPx(),0f)
            minPx = min(ThumbRadius.toPx(),maxPx)
        }

        fun scaleToUserValue(offset:Float) =
            scale(minPx,maxPx,offset,valueRange.start,valueRange.endInclusive)

        fun scaleToOffset(userValue:Float) =
            scale(valueRange.start,valueRange.endInclusive,userValue,minPx,maxPx)

        val rawOffset = remember { mutableFloatStateOf(scaleToOffset(value)) }
        val pressOffset = remember { mutableFloatStateOf(0f) }

        val draggableState = remember(minPx,maxPx,valueRange) {
            SliderDraggableState {
                rawOffset.floatValue = (rawOffset.floatValue+it+pressOffset.floatValue)
                pressOffset.floatValue = 0f
                val offsetInTrack = rawOffset.floatValue.coerceIn(minPx,maxPx)
                onValueChangeState.value.invoke(scaleToUserValue(offsetInTrack))
            }
        }

        CorrectValueSideEffect(::scaleToOffset,valueRange,minPx..maxPx,rawOffset,value)

        val press = Modifier.sliderTapModifier(
            draggableState = draggableState,
            interactionSource = interactionSource,
            maxPx = widthPx,
            rawOffset = rawOffset,
            pressOffset = pressOffset
        )

        val drag = Modifier.draggable(
            orientation = Orientation.Horizontal,
            reverseDirection = false,
            enabled = true,
            interactionSource = interactionSource,
            startDragImmediately = draggableState.isDragging,
            state = draggableState
        )

        val coerced = value.coerceIn(valueRange.start,valueRange.endInclusive)
        val fraction = calcFraction(valueRange.start,valueRange.endInclusive,coerced)
        CustomSliderV2Impl(
            enabled = true,
            positionFraction = fraction,
            width = maxPx - minPx,
            modifier = press.then(drag),
            interactionSource = interactionSource
        )
    }
}


@Composable
private fun CustomSliderV2Impl(
    enabled: Boolean,
    positionFraction: Float,
    width: Float,
    interactionSource: MutableInteractionSource,
    modifier: Modifier
) {
    val widthDp: Dp
    with(LocalDensity.current) {
        widthDp = width.toDp()
    }
    val offset = widthDp*positionFraction
    Box(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth().height(60.dp).background(color = Color.Yellow))
        Box(modifier = modifier.size(64.dp).background(color = Color.Red).padding(start = offset))
    }
}

private fun scale(a1: Float, b1: Float, x1: Float, a2: Float, b2: Float) =
    lerp(a2, b2, calcFraction(a1, b1, x1))

private fun calcFraction(a: Float, b: Float, pos: Float) =
    (if (b - a == 0f) 0f else (pos - a) / (b - a)).coerceIn(0f, 1f)

@Composable
private fun CorrectValueSideEffect(
    scaleToOffset: (Float) -> Float,
    valueRange: ClosedFloatingPointRange<Float>,
    trackRange: ClosedFloatingPointRange<Float>,
    valueState:MutableState<Float>,
    value: Float
) {
    SideEffect {
        val error = (valueRange.endInclusive - valueRange.start)/1000
        val newOffset = scaleToOffset(value)
        if (abs(newOffset - valueState.value) > error) {
            if (valueState.value in trackRange) {
                valueState.value = newOffset
            }
        }
    }
}

private class SliderDraggableState(
    val onDelta: (Float) -> Unit
) : DraggableState {

    var isDragging by mutableStateOf(false)
        private set

    private val scrollMutex = MutatorMutex()

    private val dragScope:DragScope = object : DragScope {
        override fun dragBy(pixels: Float) {
            onDelta(pixels)
        }
    }

    override fun dispatchRawDelta(delta: Float) {
        return onDelta(delta)
    }

    override suspend fun drag(dragPriority: MutatePriority, block: suspend DragScope.() -> Unit) {
       isDragging = true
        scrollMutex.mutateWith(dragScope, dragPriority, block)

    }
}

private fun snapValueToTick(
    current:Float,
    tickFractions:List<Float>,
    minPx:Float,
    maxPx:Float
) : Float {
    return tickFractions
        .fastMinByOrNull { abs(lerp(minPx,maxPx,it) - current) }
        ?.run { lerp(minPx,maxPx,this) }
        ?: current
}


@SuppressLint("ModifierFactoryUnreferencedReceiver")
private fun Modifier.sliderTapModifier(
    draggableState: DraggableState,
    interactionSource: InteractionSource,
    maxPx: Float,
    rawOffset: State<Float>,
    pressOffset: MutableState<Float>
) = composed(
    factory = {
        val scope = rememberCoroutineScope()
        pointerInput(draggableState,interactionSource, maxPx) {
            detectTapGestures(
                onPress = { pos ->
                    val to = pos.x
                    pressOffset.value = to - rawOffset.value
                    try {
                        awaitRelease()
                    } catch (_:GestureCancellationException) {
                        pressOffset.value = 0f
                    }
                },
                onTap = {
                    scope.launch {
                        draggableState.drag(MutatePriority.UserInput) {
                            dragBy(0f)
                        }
                    }
                }
            )
        }
    }
)

@Preview(showBackground = true)
@Composable
private fun CustomSliderV2Preview() {
    var progress by remember { mutableFloatStateOf(0f) }
    CustomSliderV2(value = progress, onValueChange = {
        progress = it
    })
}