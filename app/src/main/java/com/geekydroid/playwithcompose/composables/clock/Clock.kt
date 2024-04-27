package com.geekydroid.playwithcompose.composables.clock

import android.icu.util.Calendar
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Clock(
    time: () -> Long
) {
    val date = Date(time())
    val calendar = Calendar.getInstance()
    calendar.time = date
    val seconds = calendar[Calendar.SECOND]
    val minutes = calendar[Calendar.MINUTE]
    val hours = calendar[Calendar.HOUR]
    val secondsAngle = seconds * 6f
    val minutesAngle = (minutes + seconds / 60f) * 6f
    val hoursAngle = (hours + minutes / 60f) * 30f
    val circleGradient = Brush.radialGradient(
        listOf(Color.White.copy(alpha = 0.4f), Color.Gray.copy(alpha = 0.35f))
    )
    Canvas(modifier = Modifier.size(400.dp)) {
        val circleRadius = (size.width / 2f - size.width / 2f * 0.10f)
        val innerCircleRadius = circleRadius * 0.9f
        val circleCenter = Offset(size.width / 2f, size.height / 2f)
        drawCircle(
            radius = circleRadius,
            brush = circleGradient,
            center = circleCenter
        )
        drawCircle(
            radius = innerCircleRadius,
            brush = circleGradient,
            center = circleCenter
        )
        rotate(hoursAngle) {
            drawLine(
                start = Offset(size.width / 2f, size.height / 2f),
                end = Offset(size.width / 2f, size.height * 0.25f),
                color = Color.Black,
                strokeWidth = 16F
            )
        }
        rotate(minutesAngle) {
            drawLine(
                start = Offset(size.width / 2f, size.height / 2f),
                end = Offset(size.width / 2f, size.height * 0.20f),
                color = Color.Black,
                strokeWidth = 12f
            )
        }
        rotate(secondsAngle) {
            drawLine(
                start = Offset(size.width / 2f, size.height / 2f),
                end = Offset(size.width / 2f, size.height * 0.18f),
                color = Color.Black,
                strokeWidth = 8f
            )
        }
        drawCircle(
            color = Color.Black,
            radius = 15f
        )
        for (i in 0 until 60) {
            val lineLength = if (i % 5 == 0) circleRadius * 0.2f else circleRadius * 0.1f
            val lineThickness = if (i % 5 == 0) 8f else 4f
            val angleInDegrees = i * 360f / 60
            val angleInRad = angleInDegrees * PI / 180f + PI / 2F
            val start = Offset(
                x = (circleRadius * cos(angleInRad) + circleCenter.x).toFloat(),
                y = (circleRadius * sin(angleInRad) + circleCenter.y).toFloat()
            )
            val end = Offset(
                x = (circleRadius * cos(angleInRad) + circleCenter.x).toFloat(),
                y = (circleRadius * sin(angleInRad) + lineLength + circleCenter.y).toFloat()
            )

            rotate(
                angleInDegrees + 180f,
                pivot = start
            ) {
                drawLine(
                    start = start,
                    end = end,
                    color = Color.Black,
                    strokeWidth = lineThickness
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ClockPreview() {
    var currentTime by remember {
        mutableLongStateOf(System.currentTimeMillis())
    }
    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(200L)
            currentTime = System.currentTimeMillis()
        }
    }
    val timeText = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(currentTime)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Clock {
            currentTime
        }
        Text(text = timeText)
    }
}