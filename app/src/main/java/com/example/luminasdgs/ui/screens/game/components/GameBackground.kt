package com.example.luminasdgs.ui.screens.game.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

private const val GAME_BG_IMAGE = "https://lh3.googleusercontent.com/aida-public/AB6AXuD-R1V14CYopYJGm82mOdC74yf2bWt5e7G6tFlArs6UWD-yCliWU_i8ZYfOpjrmcZMc1COlMbwi2Q1Xvr_iOwBS5Oh9jtIx9XbNbGRVVUuKH-E3SEDOoaGlkKjdDy5CE6j4lu04ktTyHsH7bpa7DpTQvBDIbJANIw0A6bSvWVHXM4saUjbi82y0RymKpqfWeeV0fKOXw9OWz3i1ZUqxBxvTs7igUjGVPSYlF91kuiMqv3H8M9GHbIyGfyXDy9lTo8PYdeJbiir2AA"

@Composable
fun GameBackground(modifier: Modifier = Modifier, alpha: Float = 0.18f) {
    Box(modifier = modifier) {
        AsyncImage(
            model = GAME_BG_IMAGE,
            contentDescription = null,
            modifier = Modifier.fillMaxSize().alpha(alpha),
            contentScale = ContentScale.Crop
        )
    }
}
