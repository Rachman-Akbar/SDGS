package com.example.luminasdgs.ui.screens.game.matchcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.luminasdgs.R
import com.example.luminasdgs.ui.components.CompletionDialog
import com.example.luminasdgs.ui.components.CompletionReward
import com.example.luminasdgs.ui.components.SdgImageTile
import com.example.luminasdgs.ui.screens.game.components.CompactGameHeader
import com.example.luminasdgs.ui.screens.game.components.GameBackground
import com.example.luminasdgs.ui.screens.game.components.PreGameCountdownOverlay
import com.example.luminasdgs.viewmodel.MatchCardViewModel
import kotlinx.coroutines.delay

private const val GAME_DURATION_SECONDS = 60
private const val CORRECT_TIME_BONUS_SECONDS = 4
private const val MAX_TIME_SECONDS = 120

@Composable
fun MatchCardScreen(
    navController: NavController,
    viewModel: MatchCardViewModel = viewModel()
) {
    var remainingSeconds by rememberSaveable { mutableIntStateOf(GAME_DURATION_SECONDS) }
    var countdownActive by rememberSaveable { mutableStateOf(true) }
    var countdownSeconds by rememberSaveable { mutableIntStateOf(3) }

    val timeProgress = (remainingSeconds.toFloat() / GAME_DURATION_SECONDS).coerceIn(0f, 1f)
    val isPlaying = !viewModel.isCompleted &&
            !viewModel.isGameOver &&
            remainingSeconds > 0 &&
            !countdownActive

    val statement = viewModel.currentStatement

    val sdgDrawables = remember {
        mapOf(
            1 to R.drawable.sdgs1,
            2 to R.drawable.sdgs2,
            3 to R.drawable.sdgs3,
            4 to R.drawable.sdgs4,
            5 to R.drawable.sdgs5,
            6 to R.drawable.sdgs6,
            7 to R.drawable.sdgs7,
            8 to R.drawable.sdgs8,
            9 to R.drawable.sdgs9,
            10 to R.drawable.sdgs10,
            11 to R.drawable.sdgs11,
            12 to R.drawable.sdgs12,
            13 to R.drawable.sdgs13,
            14 to R.drawable.sdgs14,
            15 to R.drawable.sdgs15,
            16 to R.drawable.sdgs16,
            17 to R.drawable.sdgs17
        )
    }

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

    LaunchedEffect(viewModel.lastMoveMatched) {
        if (viewModel.lastMoveMatched == true && !viewModel.isGameOver) {
            remainingSeconds = (remainingSeconds + CORRECT_TIME_BONUS_SECONDS)
                .coerceAtMost(MAX_TIME_SECONDS)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        GameBackground(modifier = Modifier.fillMaxSize())

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(top = 20.dp, bottom = 60.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                CompactGameHeader(
                    progress = timeProgress,
                    lives = viewModel.lives,
                    statLabel = "Skor",
                    statValue = viewModel.score.toString(),
                    instruction = "Cocokkan pernyataan ke SDG yang tepat.",
                    onExit = { navController.popBackStack() },
                    onSettings = {}
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Text(
                        text = statement?.text ?: "Menyiapkan pernyataan...",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF263238)
                    )
                }
            }

            items(viewModel.goals) { goal ->
                val drawableRes = sdgDrawables[goal.id]

                SdgImageTile(
                    drawableRes = drawableRes,
                    contentDescription = "SDG ${goal.id}",
                    modifier = Modifier.fillMaxWidth(),
                    cornerRadius = 18.dp,
                    elevation = 10.dp,
                    onClick = { viewModel.selectGoal(goal.id) }
                )
            }

            if (viewModel.isGameOver) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFFEBEE),
                        shadowElevation = 4.dp
                    ) {
                        Text(
                            text = "Game Over. Nyawa habis atau waktu habis.",
                            modifier = Modifier.padding(16.dp),
                            color = Color(0xFFD32F2F),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        if (countdownActive) {
            PreGameCountdownOverlay(
                countdown = countdownSeconds,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    if (viewModel.isGameOver) {
        CompletionDialog(
            title = if (viewModel.isCompleted) "Misi Selesai" else "Game Over",
            message = if (viewModel.isCompleted) {
                "Semua pernyataan sudah dijawab."
            } else {
                "Waktu habis atau nyawa habis."
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
                countdownActive = true
                countdownSeconds = 3
            },
            onDismiss = { navController.popBackStack() },
            accentColor = Color(0xFF2E7D32)
        )
    }
}