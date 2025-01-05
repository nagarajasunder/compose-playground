package com.geekydroid.playwithcompose.composables.gradient

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


val text = "The morning sun cast a golden hue across the horizon import androidx.compose.ui.graphics.Color"

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BorderGradientBox(modifier: Modifier = Modifier) {

    Text(
        modifier = Modifier
            .basicMarquee()
            .padding(16.dp),
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}