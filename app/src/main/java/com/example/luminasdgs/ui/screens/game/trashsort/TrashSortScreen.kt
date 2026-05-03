package com.example.luminasdgs.ui.screens.game.trashsort

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.luminasdgs.data.dummy.TrashDummyData
import com.example.luminasdgs.ui.screens.game.components.ImmersiveGameHeader
import com.example.luminasdgs.ui.screens.game.components.formatTimer
import com.example.luminasdgs.ui.screens.game.components.rememberCountdownTimer
import com.example.luminasdgs.utils.Constants
import com.example.luminasdgs.viewmodel.TrashSortViewModel
import kotlinx.coroutines.delay

@Composable
fun TrashSortScreen(
    navController: NavController,
    viewModel: TrashSortViewModel = viewModel()
) {
    val totalItems = TrashDummyData.items.size
    val collected = viewModel.currentIndex.coerceAtMost(totalItems)
    val progress = (collected.toFloat() / totalItems.coerceAtLeast(1)).coerceIn(0f, 1f)
    val timerSeconds = rememberCountdownTimer(
        initialSeconds = 45,
        isRunning = !viewModel.isGameOver,
        resetKey = Unit,
        onFinished = { viewModel.onTimeout() }
    )
    var showRewardToast by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.isLastAnswerCorrect) {
        if (viewModel.isLastAnswerCorrect == true) {
            showRewardToast = true
            delay(700)
            showRewardToast = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F4F3))
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            ImmersiveGameHeader(
                title = "Sortir\nSampah",
                subtitle = "Seret sampah ke tempatnya!",
                timer = formatTimer(timerSeconds),
                score = viewModel.score.toString(),
                target = "$collected/$totalItems",
                lives = viewModel.lives,
                xpGain = "+${viewModel.score.coerceAtLeast(0)} XP",
                pointGain = "+${(viewModel.score.coerceAtLeast(0) / 2)} HK",
                progress = progress,
                onExit = { navController.popBackStack() },
                onSettings = {}
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(24.dp),
                color = Color(0xFFEFF6F0)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    if (viewModel.isGameOver) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Game Selesai", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Skor Akhir: ${viewModel.score}")
                            viewModel.feedbackMessage?.let { Text(text = it) }
                            Button(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier.padding(top = 12.dp)
                            ) {
                                Text(text = "Kembali ke Games")
                            }
                        }
                    } else {
                        val item = viewModel.currentItem
                        if (item != null) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                AnimatedVisibility(
                                    visible = showRewardToast,
                                    enter = fadeIn() + scaleIn(),
                                    exit = fadeOut() + scaleOut()
                                ) {
                                    Surface(shape = RoundedCornerShape(12.dp), color = Color(0xFFB2F5EA)) {
                                        Text(
                                            text = "Perfect Sort +10 XP",
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF00695C)
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = Color.White,
                                    shadowElevation = 6.dp
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = item.name,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = "Kategori: ${item.category}",
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                                        )
                                    }
                                }

                                Surface(
                                    modifier = Modifier.padding(top = 16.dp),
                                    shape = CircleShape,
                                    color = Color.White,
                                    shadowElevation = 8.dp
                                ) {
                                    Image(
                                        painter = painterResource(id = item.imageRes),
                                        contentDescription = item.name,
                                        modifier = Modifier
                                            .size(120.dp)
                                            .padding(18.dp)
                                    )
                                }

                                viewModel.feedbackMessage?.let {
                                    Text(
                                        text = it,
                                        modifier = Modifier.padding(top = 12.dp),
                                        fontWeight = FontWeight.Bold,
                                        color = if (it.contains("Benar")) Color(0xFF2E7D32) else Color(0xFFD32F2F)
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BinButton("ORGANIK", Constants.BIN_GREEN, Color(0xFFE0F2F1), modifier = Modifier.weight(1f), onSelect = viewModel::selectBin)
                        BinButton("ANORGANIK", Constants.BIN_YELLOW, Color(0xFFFFF9C4), modifier = Modifier.weight(1f), onSelect = viewModel::selectBin)
                        BinButton("B3", Constants.BIN_RED, Color(0xFFFFEBEE), modifier = Modifier.weight(1f), onSelect = viewModel::selectBin)
                        BinButton("KERTAS", Constants.BIN_BLUE, Color(0xFFE3F2FD), modifier = Modifier.weight(1f), onSelect = viewModel::selectBin)
                        BinButton("RESIDU", Constants.BIN_GRAY, Color(0xFFEEEEEE), modifier = Modifier.weight(1f), onSelect = viewModel::selectBin)
                    }
                }
            }
        }
    }
}

@Composable
private fun BinButton(
    label: String,
    bin: String,
    color: Color,
    modifier: Modifier,
    onSelect: (String) -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp)),
        onClick = { onSelect(bin) },
        shape = RoundedCornerShape(16.dp),
        color = color,
        shadowElevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 14.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF263238)
            )
        }
    }
}
