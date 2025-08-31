package com.geekydroid.playwithcompose.composables.mediaPlayer

import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.AspectRatioListener
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun VideoComponent(modifier: Modifier = Modifier,url:String) {
    val context = LocalContext.current
    val mediaItem = remember {  MediaItem.Builder().setUri(url).setMediaId("sample")
        .setMediaMetadata(MediaMetadata.Builder().setDisplayTitle("Sample video").build())
        .build() }
   val exoplayer = remember { ExoPlayer.Builder(context).build().apply {
       setMediaItem(mediaItem)
       prepare()
   }
   }
    exoplayer.repeatMode = Player.REPEAT_MODE_ALL
    DisposableEffect(Unit) {
        onDispose {
            exoplayer.release()
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()) {
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    useController = false
                    //this.resizeMode = aspectRatio
                    player = exoplayer
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT

                }
            },
            update = {
                exoplayer.playWhenReady = true
            }
        )
    }
}

