package com.geekydroid.playwithcompose.composables.canvas

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.geekydroid.playwithcompose.R

@Preview(showBackground = true)
@Composable
fun GameHealthIndicator(modifier: Modifier = Modifier) {
   Column(modifier = Modifier
       .fillMaxWidth()
       .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
       var healthPercentage by remember { mutableStateOf(0f) }
       val healthPercentageAnim by animateFloatAsState(healthPercentage)
       val imageBitmap = ImageBitmap.imageResource(R.drawable.flight)
       HealthBar(modifier = Modifier
           .width(240.dp)
           .drawWithContent {
               rotate(-45f) {
                   drawImage(
                       imageBitmap,
                       dstSize = IntSize(
                           240.dp
                               .toPx()
                               .toInt(),
                           240.dp
                               .toPx()
                               .toInt()
                       ),
                       blendMode = BlendMode.DstIn
                   )
               }
               drawContent()
           }, healthPercentage = healthPercentageAnim)
       Button(onClick = {
           healthPercentage = (healthPercentage + 0.1f).coerceAtMost(1f)
       }) {
           Text("Fill")
       }
       Button(onClick = {
           healthPercentage = 0f
       }) {
           Text("Rest")
       }
   }
}


@Composable
private fun HealthBar(modifier: Modifier = Modifier,healthPercentage:Float) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height((240 * healthPercentage).dp)
            .background(color = Color.Blue)
    )
}