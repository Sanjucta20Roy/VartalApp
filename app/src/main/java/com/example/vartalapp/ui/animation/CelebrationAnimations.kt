package com.example.vartalapp.ui.animation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun StreakFireAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "StreakFire")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Scale"
    )

    Canvas(modifier = modifier.size(24.dp)) {
        scale(scale) {
            drawCircle(
                color = Color(0xFFFF9800),
                radius = size.minDimension / 2f
            )
            drawCircle(
                color = Color(0xFFFFE082),
                radius = size.minDimension / 3f
            )
        }
    }
}

data class ConfettiParticle(
    val x: Float,
    val y: Float,
    val radius: Float,
    val color: Color,
    val speedY: Float
)

@Composable
fun ConfettiCelebration(modifier: Modifier = Modifier) {
    val particles = remember {
        List(40) {
            ConfettiParticle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = Random.nextFloat() * 8f + 4f,
                color = listOf(Color(0xFF4CAF50), Color(0xFFFFB300), Color(0xFF2196F3), Color(0xFFE91E63)).random(),
                speedY = Random.nextFloat() * 0.02f + 0.01f
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "Confetti")
    val animProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ConfettiAnim"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        particles.forEach { particle ->
            val currentY = ((particle.y + animProgress * particle.speedY * 50) % 1f) * height
            val currentX = particle.x * width
            drawCircle(
                color = particle.color,
                radius = particle.radius,
                center = Offset(currentX, currentY)
            )
        }
    }
}
