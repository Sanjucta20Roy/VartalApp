package com.example.coldtext.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class MascotState(val emoji: String, val text: String, val color: Color) {
    GREETING("🦊", "Guru, ready to learn?", Color(0xFFE65100)),
    HAPPY("😊", "Awesome job! Sakkath!", Color(0xFF2E7D32)),
    THINKING("🤔", "Hmm, try unscrambling the words...", Color(0xFF00796B)),
    CELEBRATING("🎉", "Super! You mastered this phrase!", Color(0xFF6A1B9A))
}

@Composable
fun AnimatedMascotView(
    state: MascotState = MascotState.GREETING,
    modifier: Modifier = Modifier
) {
    // Continuous subtle bounce animation for mascot avatar
    val infiniteTransition = rememberInfiniteTransition(label = "MascotBounce")
    val bounceScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Bounce"
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Mascot Animated Avatar Circle
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .scale(bounceScale)
                    .clip(CircleShape)
                    .background(state.color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.emoji,
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Speech Bubble Text
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Vartal Companion",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = state.color
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "“${state.text}”",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

/**
 * ARCHITECTURE FOR UNITY 3D ANIMATION EMBEDDING:
 *
 * To integrate Unity 3D interactive avatar animations into VartalApp:
 * 1. Export Unity project as an Android Library (`.aar` / Unity sub-module).
 * 2. In `app/build.gradle.kts`, add: `implementation(project(":unityLibrary"))`
 * 3. Embed Unity surface inside Jetpack Compose:
 *
 * ```kotlin
 * @Composable
 * fun UnityMascotView(modifier: Modifier = Modifier) {
 *     AndroidView(
 *         factory = { context ->
 *             // UnityPlayerView container
 *             com.unity3d.player.UnityPlayer(context).run {
 *                 // Pass character state animation message to Unity
 *                 com.unity3d.player.UnityPlayer.UnitySendMessage("MascotAvatar", "SetState", "HAPPY")
 *                 this
 *             }
 *         },
 *         modifier = modifier
 *     )
 * }
 * ```
 */
