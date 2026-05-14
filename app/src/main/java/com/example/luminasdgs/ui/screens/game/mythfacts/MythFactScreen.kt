package com.example.luminasdgs.ui.screens.game.mythfacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.luminasdgs.ui.components.CompletionDialog
import com.example.luminasdgs.ui.components.CompletionReward
import com.example.luminasdgs.ui.screens.game.components.CompactGameHeader
import com.example.luminasdgs.ui.screens.game.components.GameBackground
import com.example.luminasdgs.ui.screens.game.components.PreGameCountdownOverlay
import com.example.luminasdgs.viewmodel.MythFactViewModel
import kotlinx.coroutines.delay

private const val GAME_DURATION_SECONDS = 60
private const val CORRECT_TIME_BONUS_SECONDS = 4
private const val MAX_TIME_SECONDS = 120

@Composable
fun MythFactScreen(
    navController: NavController,
    viewModel: MythFactViewModel = viewModel()
) {
    var remainingSeconds by rememberSaveable { mutableIntStateOf(GAME_DURATION_SECONDS) }
    var countdownActive by rememberSaveable { mutableStateOf(true) }
    var countdownSeconds by rememberSaveable { mutableIntStateOf(3) }

    val statement = viewModel.currentStatement
    val timeProgress = (remainingSeconds.toFloat() / GAME_DURATION_SECONDS).coerceIn(0f, 1f)
    val isPlaying = !viewModel.isGameOver && remainingSeconds > 0 && viewModel.lives > 0 && !countdownActive

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

    LaunchedEffect(viewModel.correctCount, viewModel.lastAnswerCorrect) {
        if (viewModel.correctCount > 0 && viewModel.lastAnswerCorrect == true && !viewModel.isGameOver) {
            remainingSeconds = (remainingSeconds + CORRECT_TIME_BONUS_SECONDS).coerceAtMost(MAX_TIME_SECONDS)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F3F1))
    ) {
        GameBackground(modifier = Modifier.fillMaxSize())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            CompactGameHeader(
                progress = timeProgress,
                lives = viewModel.lives,
                statLabel = "Skor",
                statValue = viewModel.score.toString(),
                instruction = "Tentukan apakah pernyataan ini mitos atau fakta.",
                onExit = { navController.popBackStack() },
                onSettings = {}
            )

            if (statement != null) {
                MythFactCard(
                    statement = statement.statement,
                    modifier = Modifier.fillMaxWidth()
                )

                MythFactActions(
                    onMyth = { viewModel.answer(isFactSelected = false) },
                    onFact = { viewModel.answer(isFactSelected = true) },
                    enabled = !viewModel.isGameOver
                )
            } else {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        if (viewModel.isGameOver) {
            CompletionDialog(
                title = if (viewModel.isCompleted) "Misi Selesai" else "Waktu Habis",
                message = if (viewModel.isCompleted) {
                    "Kamu telah menilai semua pernyataan."
                } else {
                    "Waktu bermain selesai."
                },
                rewards = listOf(
                    CompletionReward("Skor", "${viewModel.score}"),
                    CompletionReward("Benar", "${viewModel.correctCount}"),
                    CompletionReward("Salah", "${viewModel.wrongCount}")
                ),
                primaryButtonText = "Kembali ke Games",
                secondaryButtonText = "Main Lagi",
                onPrimaryClick = { navController.popBackStack() },
                onSecondaryClick = {
                    viewModel.resetGame()
                    remainingSeconds = GAME_DURATION_SECONDS
                },
                onDismiss = { navController.popBackStack() },
                accentColor = Color(0xFF1B5E20)
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


@Composable
private fun MythFactCard(
    statement: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.heightIn(min = 240.dp),
        shape = RoundedCornerShape(26.dp),
        color = Color.White.copy(alpha = 0.72f),
        tonalElevation = 0.dp,
        shadowElevation = 4.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(999.dp),
                color = Color(0xFFD9F7D6).copy(alpha = 0.85f)
            ) {
                Text(
                    text = "ENVIRONMENTAL CLAIM",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )
            }

            Text(
                text = statement,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF2F2F2F),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun MythFactActions(
    onMyth: () -> Unit,
    onFact: () -> Unit,
    enabled: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        MythFactActionButton(
            label = "MYTH",
            icon = Icons.Filled.Close,
            background = Color(0xFFB71C1C),
            onClick = onMyth,
            enabled = enabled,
            modifier = Modifier.weight(1f)
        )
        MythFactActionButton(
            label = "FACT",
            icon = Icons.Filled.Check,
            background = Color(0xFF1B5E20),
            onClick = onFact,
            enabled = enabled,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun MythFactActionButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    background: Color,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(92.dp),
        shape = RoundedCornerShape(22.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            disabledContainerColor = background.copy(alpha = 0.6f)
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
