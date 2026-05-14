package com.example.luminasdgs.ui.screens.game.river

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.SetMeal
import androidx.compose.material.icons.filled.Waves
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.luminasdgs.ui.components.CompletionDialog
import com.example.luminasdgs.ui.components.CompletionReward
import com.example.luminasdgs.ui.screens.game.components.CompactGameHeader
import com.example.luminasdgs.ui.screens.game.components.GameBackground
import com.example.luminasdgs.ui.screens.game.components.PreGameCountdownOverlay
import com.example.luminasdgs.viewmodel.CleanRiverViewModel
import kotlinx.coroutines.delay

private const val GAME_DURATION_SECONDS = 60
private const val CORRECT_TIME_BONUS_SECONDS = 4
private const val MAX_TIME_SECONDS = 120

@Composable
fun CleanRiverScreen(
    navController: NavController,
    viewModel: CleanRiverViewModel = viewModel()
) {
    var remainingSeconds by rememberSaveable { mutableIntStateOf(GAME_DURATION_SECONDS) }
    var countdownActive by rememberSaveable { mutableStateOf(true) }
    var countdownSeconds by rememberSaveable { mutableIntStateOf(3) }
    val timeProgress = (remainingSeconds.toFloat() / GAME_DURATION_SECONDS).coerceIn(0f, 1f)
    val isPlaying = !viewModel.isGameOver && remainingSeconds > 0 && !countdownActive

    LaunchedEffect(countdownActive, countdownSeconds) {
        if (!countdownActive) return@LaunchedEffect
        if (countdownSeconds <= 0) {
            countdownActive = false
            return@LaunchedEffect
        }
        delay(1_000L)
        countdownSeconds -= 1
    }

    LaunchedEffect(isPlaying) {
        while (isPlaying && remainingSeconds > 0) {
            delay(1_000L)
            remainingSeconds = (remainingSeconds - 1).coerceAtLeast(0)
            if (remainingSeconds == 0) {
                viewModel.onTimeout()
            }
        }
    }

    LaunchedEffect(viewModel.lastActionWasCorrect) {
        if (viewModel.lastActionWasCorrect == true && !viewModel.isGameOver) {
            remainingSeconds = (remainingSeconds + CORRECT_TIME_BONUS_SECONDS).coerceAtMost(MAX_TIME_SECONDS)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GameBackground(modifier = Modifier.fillMaxSize(), alpha = 0.06f)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFE0F2F1), Color(0xFFB2DFDB))
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            CompactGameHeader(
                progress = timeProgress,
                lives = viewModel.life.coerceIn(0, 3),
                statLabel = "Skor",
                statValue = viewModel.score.toString(),
                instruction = "Ambil sampah, hindari ikan.",
                onExit = { navController.popBackStack() },
                onSettings = {}
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(24.dp),
                color = Color.Transparent
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = Icons.Filled.Waves,
                        contentDescription = null,
                        tint = Color(0xFF80DEEA),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 12.dp)
                            .size(54.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.Waves,
                        contentDescription = null,
                        tint = Color(0xFF80DEEA),
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 12.dp)
                            .size(54.dp)
                    )

                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val current = viewModel.currentItem
                        if (current == null && viewModel.isCompleted) {
                            Text(text = "Misi Sungai Selesai", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Skor: ${viewModel.score}")
                            Button(onClick = { navController.popBackStack() }) { Text(text = "Kembali") }
                        } else if (current == null) {
                            Text(text = "Game Over", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Skor: ${viewModel.score}")
                            Button(onClick = { navController.popBackStack() }) { Text(text = "Kembali") }
                        } else {
                            val itemIcon = if (current.type == "trash") Icons.Filled.Inventory else Icons.Filled.SetMeal
                            Surface(
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.9f),
                                shadowElevation = 8.dp
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(120.dp)
                                        .padding(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = itemIcon,
                                        contentDescription = current.name,
                                        tint = if (current.type == "trash") Color(0xFF5D4037) else Color(0xFF2E7D32),
                                        modifier = Modifier.size(52.dp)
                                    )
                                }
                            }
                            Text(text = current.name, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Button(onClick = { viewModel.takeItem() }) {
                                    Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = null)
                                    Text(text = " Ambil")
                                }
                                Button(onClick = { viewModel.skipItem() }) {
                                    Text(text = "Lewati")
                                }
                            }
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        shadowElevation = 10.dp
                    ) {
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DeleteSweep,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(54.dp)
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF80DEEA))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Filled.Eco, contentDescription = null, tint = Color(0xFF00695C))
                            Text(
                                text = "  Collect trash for combo",
                                color = Color(0xFF00695C),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        if (viewModel.isCompleted) {
            CompletionDialog(
                title = "Clean River Selesai",
                message = "Kamu berhasil membersihkan seluruh aliran sungai.",
                rewards = listOf(
                    CompletionReward("XP", "+50 XP"),
                    CompletionReward("Hero Koin", "+25 HK"),
                    CompletionReward("Bonus", "Air + Pupuk")
                ),
                primaryButtonText = "Kembali ke Games",
                secondaryButtonText = null,
                onPrimaryClick = { navController.popBackStack() },
                onDismiss = { navController.popBackStack() },
                accentColor = Color(0xFF00695C)
            )
        }

        if (countdownActive) {
            PreGameCountdownOverlay(
                countdown = countdownSeconds,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
