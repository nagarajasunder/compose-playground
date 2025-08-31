package com.geekydroid.playwithcompose

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumn {
                items(50) {
                    Content()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Content() {
    val data = produceState(initialValue = false) {
        value = getData()
    }
    Card(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.displaySmall,
            text = "Data ${data.value}")
    }
}

suspend fun getData() : Boolean {
    return withContext(Dispatchers.IO) {
        delay(2000)
        true
    }
}

val customAliases = listOf("LauncherOneAlias","LauncherTwoAlias","LauncherDefaultAlias")

private fun onLogoChange(context:Context,activityAlias:String) {
   try {
       //val packageManager = context.packageManager
//       packageManager.enableAlias(context, activityAlias)
       (context as Activity).changeEnabledComponent(activityAlias)
       for (alias in customAliases) {
           if (alias != activityAlias) {
               //packageManager.disableAlias(context, alias)
               (context as Activity).changeDisabledComponent(activityAlias)
           }
       }
   } catch (e:Exception) {
       Log.d(TAG, "onLogoChange: ${e.message}")
   }
}

private fun PackageManager.enableAlias(context: Context, aliasName: String) =
    setComponentEnabledSetting(
        ComponentName(
            context,
            "${context.packageName}.${aliasName}"
        ), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
    )

fun Activity.changeEnabledComponent(
    enabled: String,
) {
    packageManager.setComponentEnabledSetting(
        ComponentName(
            this,
            "${packageName}.${enabled}"
        ),
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP
    )
}

fun Activity.changeDisabledComponent(
    disabled: String,
) {
    packageManager.setComponentEnabledSetting(
        ComponentName(
            this,
            "${packageName}.${disabled}"
        ),
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
        PackageManager.DONT_KILL_APP
    )
}


private fun PackageManager.disableAlias(context: Context, aliasName: String) =
    setComponentEnabledSetting(
        ComponentName(
            context,
            "${context.packageName}.${aliasName}"
        ), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
    )