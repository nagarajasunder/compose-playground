package com.geekydroid.playwithcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.tappableElement
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.geekydroid.playwithcompose.ui.theme.PlaywithcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            PlaywithcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Content("Android")
                }
            }
        }
    }
}

@Composable
fun Content(name: String) {
    Scaffold(bottomBar = {
        var selected by remember { mutableIntStateOf(0) }
        Row(modifier = Modifier.fillMaxWidth()) {
            BottomNavigation(modifier = Modifier.consumeWindowInsets(WindowInsets.systemBars)) {
                (0..3).forEach {  index ->
                    BottomNavigationItem(
                        selected = selected == index,
                        onClick = {
                            selected = index
                        },
                        icon = {
                            Icon(imageVector = Icons.Default.Android, contentDescription = null)
                        }
                    )
                }
            }
        }
    }) { paddingValues ->
        LazyColumn(modifier = Modifier.consumeWindowInsets(paddingValues)) {
            item {
                val brush = Brush.linearGradient(colors = listOf(Color.Yellow,Color.Blue), tileMode = TileMode.Decal)
                Row(modifier = Modifier.fillMaxWidth().background(brush = brush).padding(start = 16.dp, end = 16.dp, bottom = 16.dp).windowInsetsPadding(WindowInsets.statusBars)) {
                    Icon(imageVector = Icons.Default.MusicNote, contentDescription = null)
                    Text(text = "Hello $name!", style = MaterialTheme.typography.h6)
                }
            }
            items(50) { index ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 2.dp)) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Index $index"
                    )
                }
            }
        }
    }
}