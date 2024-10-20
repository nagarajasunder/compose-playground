package com.geekydroid.playwithcompose.composables.animations.cardreveal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun CardRevelAnimV1(modifier: Modifier = Modifier) {
    Column {
        for (i in 1..<4) {
            AnimateContent(index = i)
        }
    }
}

@Composable
private fun AnimateContent(index: Int) {
    var visible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        delay(index*100L)
        visible = true
    }
    AnimatedVisibility(visible = visible) {
        val offsetY by animateDpAsState(targetValue = if (visible) 0.dp else (-100).dp)
        Card(modifier = Modifier
            .fillMaxWidth()
            .offset(y = offsetY)
            .padding(4.dp), shape = RoundedCornerShape(6.dp)) {
            Text(
                text = "${index} Card",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardRevelAnimV1Preview(modifier: Modifier = Modifier) {
    var refresh by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth()) {
        if (refresh) {
            CardRevelAnimV1()
        }
        Button(onClick = {
            refresh = !refresh
        }) {
            Text(text = "Refresh")
        }
    }

}