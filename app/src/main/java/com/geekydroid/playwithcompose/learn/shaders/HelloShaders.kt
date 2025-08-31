package com.geekydroid.playwithcompose.learn.shaders

import android.annotation.SuppressLint
import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.Language

@Language("AGSL")
val shader = """
    uniform float2 resolution;
    uniform float2 pointer;
    
    float4 main(float2 fragCoord) {
            
        float2 uv = fragCoord.xy/resolution.xy;  
         
        if(abs(uv.y - pointer.x) > 0.05 && abs(uv.x-pointer.y) > 0.05) {
            return float4(1.0,0.0,0.0,1.0);
        }
        else {
            return float4(0.0,1.0,0.0,1.0);
        }
    }
""".trimIndent()

@Preview
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun HelloShader() {
    var pointerOffset by remember { mutableStateOf(Offset(0.05f,0.05f)) }
    LaunchedEffect(Unit) {
        var offset = pointerOffset.x
        var increasing = true
        var i = 0
        while (true) {  // Infinite loop
            delay(25)
            if (increasing) {
                offset += 0.1f
                if (offset >= 1.0) {
                    offset = 1.0f // Stop at 1.0 and start decreasing
                    increasing = false
                }
            } else {
                offset -= 0.1f
                if (offset <= 0.01) {
                    offset = 0.01f // Stop at 0.01 and start increasing
                    increasing = true
                }
            }
            pointerOffset = Offset(offset, offset)
            i++
        }
    }
    Box(
        modifier = Modifier
            .size(500.dp)
            .drawWithCache {
                val shader = RuntimeShader(shader)
                val shaderBrush = ShaderBrush(shader)
                onDrawBehind {
                    shader.setFloatUniform("resolution", size.width, size.height)
                    shader.setFloatUniform("pointer", pointerOffset.x, pointerOffset.y)
                    drawRect(shaderBrush)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.background(color = Color.Black),
            style = LocalTextStyle.current.copy(fontSize = MaterialTheme.typography.titleMedium.fontSize, color = Color.White),
            text = "Hello Shaders (${pointerOffset.x},${pointerOffset.y})",
        )
    }
}

@Language("AGSL")
val MOUSE_POINT_SHADER = """
    uniform vec2 u_resolution; // Screen size
    uniform vec2 u_mouse;      // Mouse position

    float4 main(float2 fragCoord) {
        vec2 uv = fragCoord.xy / u_resolution.xy; // Normalize coordinates (0 to 1)
        
        // Convert mouse position to the same coordinate system
        vec2 center = u_mouse / u_resolution.xy * 2.0 - 1.0;
        
        // Transform UV coordinates relative to the mouse pointer
        uv = uv * 2.0 - 1.0;  // Convert to range (-1 to 1)
        uv -= center;         // Shift to make the mouse the center
        
        float angle = atan(uv.y, uv.x); // Get the angle in polar coordinates
        float radius = length(uv);      // Get the radial distance
        
        float rays = step(0.5, fract(angle * 10.0 / 6.283185));  // hard-edged rays
        
        float gradient = smoothstep(0.0, 1.0, radius); // Radial gradient fade
        
        // Darker Peach Color: Adjusted base color
         vec3 darkPeach = vec3(1.0, 0, 0.0);  // Darker peach (brownish-pink)
        vec3 brightPeach = vec3(1.0, 1.0, 1.0); // Brighter peach highlight
        
        vec3 color = mix(darkPeach, brightPeach, rays * gradient); 
        
        return vec4(color, 1.0);
    }
""".trimIndent()


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun MousePointShader(modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    val center = with(density) {50.dp.toPx()}
    var pointerOffset by remember { mutableStateOf(Offset(with(density){150.dp.toPx()},center)) }
    Box(
        modifier = Modifier
            .width(300.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .drawWithCache {
                onDrawBehind {
                    val shader = RuntimeShader(MOUSE_POINT_SHADER)
                    shader.setFloatUniform("u_resolution", size.width, size.height)
                    shader.setFloatUniform("u_mouse", pointerOffset.x, pointerOffset.y)
                    drawRect(ShaderBrush(shader))
                }
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val position = event.changes.first().position
                        //pointerOffset = Offset(position.x, position.y)
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Promo offer ${pointerOffset.x},${pointerOffset.y}",
            style = LocalTextStyle.current.copy(
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = Color.White
            ),
        )
    }
}