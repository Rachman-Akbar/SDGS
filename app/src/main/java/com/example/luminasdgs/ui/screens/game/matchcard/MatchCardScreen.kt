package com.example.luminasdgs.ui.screens.game.matchcard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.luminasdgs.ui.screens.game.components.ImmersiveGameHeader
import com.example.luminasdgs.ui.screens.game.components.formatTimer
import com.example.luminasdgs.ui.screens.game.components.rememberCountdownTimer
import com.example.luminasdgs.viewmodel.MatchCardViewModel
import kotlinx.coroutines.delay

@Composable
fun MatchCardScreen(
    navController: NavController,
    viewModel: MatchCardViewModel = viewModel()
) {
    val totalPairs = (viewModel.cards.size / 2).coerceAtLeast(1)
    val progress = (viewModel.matchedPairs.toFloat() / totalPairs).coerceIn(0f, 1f)
    val timerSeconds = rememberCountdownTimer(
        initialSeconds = 42,
        isRunning = !viewModel.isCompleted && !viewModel.isGameOver,
        resetKey = Unit,
        onFinished = { viewModel.onTimeout() }
    )
    var showMatchToast by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.lastMoveMatched) {
        if (viewModel.lastMoveMatched == true) {
            showMatchToast = true
            delay(700)
            showMatchToast = false
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 20.dp, bottom = 60.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            ImmersiveGameHeader(
                title = "SDG Match\nCard",
                subtitle = "Match the icon to its Global Goal number.",
                timer = formatTimer(timerSeconds),
                score = viewModel.score.toString(),
                target = "${viewModel.matchedPairs}/$totalPairs",
                lives = viewModel.lives,
                xpGain = "+60 XP",
                pointGain = "+30 HK",
                progress = progress,
                onExit = { navController.popBackStack() },
                onSettings = {}
            )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            AnimatedVisibility(
                visible = showMatchToast,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Surface(shape = RoundedCornerShape(12.dp), color = Color(0xFFB2F5EA)) {
                    Text(
                        text = "Match! Combo +10 XP",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00695C)
                    )
                }
            }
        }

        items(viewModel.cards) { card ->
            val isActive = card.isFlipped || card.isMatched
            val cardColor = when {
                card.isMatched -> Color(0xFFC8E6C9)
                card.isFlipped -> Color(0xFFE8F5E9)
                else -> Color(0xFFEDEDED)
            }
            val borderColor = when {
                card.isMatched -> Color(0xFF66BB6A)
                card.isFlipped -> Color(0xFF2E7D32)
                else -> Color(0xFFB0BEC5)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !card.isMatched) { viewModel.flipCard(card.id) },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                border = androidx.compose.foundation.BorderStroke(2.dp, borderColor)
            ) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    if (isActive) {
                        Text(
                            text = card.text,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (card.isMatched) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onSurface
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color(0xFFE0E0E0), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "★", color = Color(0xFF90A4AE), fontSize = 22.sp)
                        }
                    }
                }
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(999.dp)
            ) {
                Icon(imageVector = Icons.Filled.Bolt, contentDescription = null)
                Text(text = " Activate Fast-Play")
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = "\"Speed builds XP. Accuracy builds Impact.\"",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color(0xFFFFE082), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "★", color = Color(0xFF6E5100))
                        }
                        Text(
                            text = "  DAILY BEST\n  00:34s",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                    }
                    Text(text = "PRIZE\n150 HK", fontWeight = FontWeight.Bold, color = Color(0xFF6E5100))
                }
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFFF1F8E9)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Icon(imageVector = Icons.Filled.Public, contentDescription = null, tint = Color(0xFF2E7D32))
                        Text(text = "GLOBAL RANK", fontSize = 11.sp)
                        Text(text = "#241", fontWeight = FontWeight.Bold, fontSize = 28.sp)
                    }
                }
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFFE0F7FA)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Icon(imageVector = Icons.Filled.Schedule, contentDescription = null, tint = Color(0xFF006A60))
                        Text(text = "MATCHES", fontSize = 11.sp)
                        Text(text = "${viewModel.matchedPairs}/$totalPairs", fontWeight = FontWeight.Bold, fontSize = 28.sp)
                    }
                }
            }
        }

        if (viewModel.isGameOver) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFFFEBEE)
                ) {
                    Text(
                        text = "Game Over. Nyawa habis atau waktu habis.",
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFFD32F2F),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Kembali ke Games")
                }
            }
        } else if (viewModel.isCompleted) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFE8F5E9)
                ) {
                    Text(
                        text = "Semua pasangan berhasil ditemukan!",
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Kembali ke Games")
                }
            }
        }
    }
}
