package com.geekydroid.playwithcompose.composables.animations.carousel

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TextAnimationCarousel(modifier: Modifier = Modifier, list: List<String>) {

    var animatedIndex by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            animatedIndex = (animatedIndex+1)%list.size
        }
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        AnimatedContent(targetState = list[animatedIndex], transitionSpec = {
            fadeIn() + slideInVertically { fullHeight -> fullHeight } togetherWith fadeOut() + slideOutVertically { fullHeight -> -fullHeight }
        }) { content ->
            Text(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                text = content,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TextAnimationCarouselPreview(modifier: Modifier = Modifier) {
    TextAnimationCarousel(list = listOf("Hello World","Screen Time","Redbus"))
}