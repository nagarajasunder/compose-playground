package com.geekydroid.playwithcompose.composables.stepperview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StepperView(modifier: Modifier = Modifier) {
    StepperViewPreview()
}

@Composable
fun StepperViewItem(
    modifier: Modifier = Modifier,
    index:Int,
    lastItem:Boolean,
    content: StepperContent
) {
    var height by remember { mutableStateOf(0) }
    val heightDp = with(LocalDensity.current) {
        height.toDp()
    }
    Row(modifier = modifier
        .fillMaxWidth()
        .onSizeChanged { size ->
            height = size.height
        }) {
        Column(modifier = Modifier.width(80.dp)) {
            Text(text = content.leftContent, fontSize = 18.sp)
        }
        Box(
            modifier = Modifier
                .height(if (lastItem) 16.dp else heightDp)
                .padding(start = 12.dp, end = 12.dp, top = if (index == 0) 8.dp else 0.dp)
                .background(
                    color = Color.Gray.copy(alpha = 0.5f),
                    shape = if (index == 0) RoundedCornerShape(
                        topStart = 50f,
                        topEnd = 50f
                    ) else if (lastItem) RoundedCornerShape(bottomStartPercent = 50, bottomEndPercent = 50)
                    else RectangleShape
                )
        ) {
            Box(
                modifier = Modifier
                    .padding(top = if (index == 0) 0.dp else 8.dp)
                    .size(8.dp)
                    .background(color = Color.Black, shape = CircleShape)
            )
        }
        Column {
            Text(text = content.rightContent, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun StepperViewPreview(modifier: Modifier = Modifier) {
    val data = getData()
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(data.size) { offset ->
            val item = data[offset]
            StepperViewItem(index = offset, content = item, lastItem = offset == data.size-1)
        }
    }
}

data class StepperContent(
    val leftContent:String,
    val rightContent:String
)

private fun getData() : List<StepperContent> {
    val list = mutableListOf<StepperContent>()
    for (i in 0 ..< 25) {
        list.add(StepperContent(leftContent = "${i}_left", rightContent = "${i}_right"))
    }
    return list
}