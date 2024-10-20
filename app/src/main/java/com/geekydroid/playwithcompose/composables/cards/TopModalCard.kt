package com.geekydroid.playwithcompose.composables.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopModalCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(), backgroundColor = Color.LightGray,
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = "", onValueChange = {})
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = "", onValueChange = {})
            Button(onClick = {}) {
                Text(text = "Submit")
            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TopModalCardPreview(modifier: Modifier = Modifier) {
    var showCard by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = showCard,
            enter = slideInVertically() + expandIn(),
            exit = slideOutVertically() + shrinkOut()
        ) {
            TopModalCard(modifier)
        }
        Button(
            onClick = {showCard = !showCard}
        ) {
            Text(text = if(showCard) "Hide" else "Show")
        }
    }
}