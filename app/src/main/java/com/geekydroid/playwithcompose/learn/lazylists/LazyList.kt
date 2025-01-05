package com.geekydroid.playwithcompose.learn.lazylists

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp

@Preview(showSystemUi = true)
@Composable
private fun LazyListRoughNote(
    modifier: Modifier = Modifier
) {
    val items = (0..<2).map { it }
    val listState = rememberLazyListState()
    var firstFullyVisibleIndex by remember { mutableStateOf(-1) }
    var scrolling by remember { mutableStateOf("") }


    LaunchedEffect(listState.isScrollInProgress) {
       if (!listState.isScrollInProgress) {
           firstFullyVisibleIndex = getFirstFullyVisibleItemIndex(listState)?:-1
       }

    }
    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {
            if (!listState.canScrollForward) {
                scrolling = "Scrolling Last item"
            } else {
                scrolling = "Scrolling"
            }
        } else {
            scrolling = "Not Scrolling"
        }
    }

    Box {
        Column(modifier = Modifier
            .background(Color.Gray)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = scrolling)
            LazyRow(
                modifier = Modifier
                    .padding(16.dp)
                    .nestedScroll(NoOpNestedScrollConnection()),
                state = listState
            ) {
                items(items.size) { index ->
                    val color by animateColorAsState(if (index == firstFullyVisibleIndex) Color.Red else Color.Yellow)
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(300.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(color.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(modifier = Modifier.wrapContentSize(), text = "page ${index}")
                    }
                }
            }
        }

    }
}

private fun getFirstFullyVisibleItemIndex(lazyListState: LazyListState) : Int? {
    return lazyListState.layoutInfo.visibleItemsInfo.firstOrNull { item ->
        item.offset in (lazyListState.layoutInfo.viewportStartOffset + lazyListState.layoutInfo.beforeContentPadding)..(lazyListState.layoutInfo.viewportEndOffset+lazyListState.layoutInfo.afterContentPadding)
    }?.index
}

private fun checkLastItemIsFullyVisible(lastItemIndex:Int,lazyListState: LazyListState) : Boolean {
    return lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.let { itemInfo ->
        itemInfo.index == lastItemIndex && itemInfo.offset in (lazyListState.layoutInfo.viewportStartOffset .. lazyListState.layoutInfo.viewportEndOffset)
    }?:false
}

class NoOpNestedScrollConnection : NestedScrollConnection {
//    override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource) = Offset(available.x, 0f)
//    override suspend fun onPostFling(consumed: Velocity, available: Velocity) = Velocity(available.x, 0f)
override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource) = Offset.Zero
    override suspend fun onPostFling(consumed: Velocity, available: Velocity) = Velocity.Zero
}