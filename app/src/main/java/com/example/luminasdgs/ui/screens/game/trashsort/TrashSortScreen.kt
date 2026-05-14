package com.example.luminasdgs.ui.screens.game.trashsort

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.luminasdgs.R
import com.example.luminasdgs.utils.Constants
import com.example.luminasdgs.ui.screens.game.components.CompactGameHeader
import com.example.luminasdgs.ui.screens.game.components.GameBackground
import com.example.luminasdgs.ui.screens.game.components.PreGameCountdownOverlay
import com.example.luminasdgs.viewmodel.TrashSortViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

private const val GAME_DURATION_SECONDS = 120
private const val CORRECT_TIME_BONUS_SECONDS = 3
private const val MAX_TIME_SECONDS = 180
private const val MAX_ACTIVE_ITEMS = 12
private const val SPAWN_START_INTERVAL_MS = 1_000L
private const val SPAWN_MIN_INTERVAL_MS = 380L
private const val SPAWN_ACCELERATION = 0.96f

@Composable
fun TrashSortScreen(
    navController: NavController,
    viewModel: TrashSortViewModel = viewModel()
) {
    var remainingSeconds by rememberSaveable { mutableIntStateOf(GAME_DURATION_SECONDS) }
    var showSettings by rememberSaveable { mutableStateOf(false) }
    var soundEnabled by rememberSaveable { mutableStateOf(true) }
    var useDarkBoard by rememberSaveable { mutableStateOf(false) }
    var countdownActive by rememberSaveable { mutableStateOf(true) }
    var countdownSeconds by rememberSaveable { mutableIntStateOf(3) }

    val collected = viewModel.handledCount.coerceAtLeast(0)
    val score = viewModel.score.coerceAtLeast(0)
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

    LaunchedEffect(collected, viewModel.isLastAnswerCorrect) {
        if (collected > 0 && viewModel.isLastAnswerCorrect == true && !viewModel.isGameOver) {
            remainingSeconds = (remainingSeconds + CORRECT_TIME_BONUS_SECONDS).coerceAtMost(MAX_TIME_SECONDS)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (useDarkBoard) Color(0xFF10231D) else Color(0xFFF6F4F3))
    ) {
        GameBackground(
            modifier = Modifier.fillMaxSize(),
            alpha = if (useDarkBoard) 0.03f else 0.08f
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CompactGameHeader(
                progress = timeProgress,
                lives = viewModel.lives,
                statLabel = "Terkumpul",
                statValue = collected.toString(),
                instruction = "Seret sampah ke tong yang benar!",
                onExit = { navController.popBackStack() },
                onSettings = { showSettings = true }
            )

            TrashSortBoard(
                viewModel = viewModel,
                collected = collected,
                isPlaying = isPlaying,
                useDarkBoard = useDarkBoard,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }

        if (showSettings) {
            TrashSortSettingsDialog(
                soundEnabled = soundEnabled,
                onSoundEnabledChange = { soundEnabled = it },
                useDarkBoard = useDarkBoard,
                onUseDarkBoardChange = { useDarkBoard = it },
                onBackToMenu = { navController.popBackStack() },
                onDismiss = { showSettings = false }
            )
        }

        if (viewModel.isGameOver) {
            TrashSortGameOverDialog(
                score = score,
                collected = collected,
                lives = viewModel.lives,
                onBackToGames = { navController.popBackStack() }
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
private fun TrashSortBoard(
    viewModel: TrashSortViewModel,
    collected: Int,
    isPlaying: Boolean,
    useDarkBoard: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = if (useDarkBoard) Color(0xFF173229) else Color(0xFFEFF6F0)
    ) {
        val density = LocalDensity.current
        var rootOffset by remember { mutableStateOf(Offset.Zero) }
        val binRects = remember { mutableMapOf<String, Rect>() }

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .onGloballyPositioned { coords ->
                    rootOffset = coords.positionInRoot()
                }
        ) {
            val boxWidth = with(density) { maxWidth.toPx().toInt() }
            val boxHeight = with(density) { maxHeight.toPx().toInt() }
            val itemSizePx = with(density) { 84.dp.toPx().toInt() }
            val reservedBottomPx = with(density) { 140.dp.toPx().toInt() }
            val maxX = (boxWidth - itemSizePx).coerceAtLeast(0)
            val maxY = (boxHeight - itemSizePx - reservedBottomPx).coerceAtLeast(0)

            LaunchedEffect(isPlaying, boxWidth, boxHeight) {
                if (!isPlaying || boxWidth <= 0 || boxHeight <= 0) return@LaunchedEffect

                var interval = SPAWN_START_INTERVAL_MS
                while (isPlaying && !viewModel.isGameOver) {
                    if (viewModel.spawned.size < MAX_ACTIVE_ITEMS) {
                        val x = Random.nextInt(0, maxX + 1)
                        val y = Random.nextInt(0, maxY + 1)
                        viewModel.addSpawnAt(x, y)
                    }

                    delay(interval)
                    interval = (interval * SPAWN_ACCELERATION)
                        .toLong()
                        .coerceAtLeast(SPAWN_MIN_INTERVAL_MS)
                }
            }

            viewModel.spawned.forEach { spawnedTrash ->
                key(spawnedTrash.id) {
                    val sizeDp = 84.dp
                    var dragX by remember(spawnedTrash.id) { mutableIntStateOf(spawnedTrash.x) }
                    var dragY by remember(spawnedTrash.id) { mutableIntStateOf(spawnedTrash.y) }

                    Box(
                        modifier = Modifier
                            .offset { IntOffset(dragX, dragY) }
                            .size(sizeDp)
                            .pointerInput(spawnedTrash.id, isPlaying, boxWidth, boxHeight) {
                                if (!isPlaying) return@pointerInput

                                detectDragGestures(
                                    onDrag = { change, dragAmount ->
                                        change.consume()
                                        dragX = (dragX + dragAmount.x.toInt()).coerceIn(0, maxX)
                                        dragY = (dragY + dragAmount.y.toInt()).coerceIn(0, (boxHeight - itemSizePx).coerceAtLeast(0))
                                    },
                                    onDragEnd = {
                                        val center = Offset(
                                            x = rootOffset.x + dragX + itemSizePx / 2f,
                                            y = rootOffset.y + dragY + itemSizePx / 2f
                                        )
                                        val hitBin = binRects.entries
                                            .firstOrNull { (_, rect) -> rect.contains(center) }
                                            ?.key

                                        if (hitBin != null) {
                                            viewModel.handleDrop(spawnedTrash.id, hitBin)
                                        } else {
                                            dragX = spawnedTrash.x.coerceIn(0, maxX)
                                            dragY = spawnedTrash.y.coerceIn(0, maxY)
                                        }
                                    }
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = spawnedTrash.item.imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(6.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            CollectedCounter(
                collected = collected,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 4.dp)
            )

            TrashBinRow(
                binRects = binRects,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun CollectedCounter(
    collected: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 5.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = "Terkumpul",
                fontSize = 10.sp,
                color = Color(0xFF607D8B),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = collected.toString(),
                fontSize = 24.sp,
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
private fun TrashBinRow(
    binRects: MutableMap<String, Rect>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        TrashBinButton(
            label = "ORGANIK",
            binKey = Constants.BIN_GREEN,
            imageRes = R.drawable.ic_bin_organik,
            modifier = Modifier
                .weight(1f)
                .onGloballyPositioned { coords ->
                    binRects[Constants.BIN_GREEN] = coords.boundsInRoot()
                }
        )
        TrashBinButton(
            label = "ANORGANIK",
            binKey = Constants.BIN_YELLOW,
            imageRes = R.drawable.ic_bin_anorganik,
            modifier = Modifier
                .weight(1f)
                .onGloballyPositioned { coords ->
                    binRects[Constants.BIN_YELLOW] = coords.boundsInRoot()
                }
        )
        TrashBinButton(
            label = "B3",
            binKey = Constants.BIN_RED,
            imageRes = R.drawable.ic_bin_b3,
            modifier = Modifier
                .weight(1f)
                .onGloballyPositioned { coords ->
                    binRects[Constants.BIN_RED] = coords.boundsInRoot()
                }
        )
        TrashBinButton(
            label = "KERTAS",
            binKey = Constants.BIN_BLUE,
            imageRes = R.drawable.ic_bin_kertas,
            modifier = Modifier
                .weight(1f)
                .onGloballyPositioned { coords ->
                    binRects[Constants.BIN_BLUE] = coords.boundsInRoot()
                }
        )
        TrashBinButton(
            label = "RESIDU",
            binKey = Constants.BIN_GRAY,
            imageRes = R.drawable.ic_bin_residu,
            modifier = Modifier
                .weight(1f)
                .onGloballyPositioned { coords ->
                    binRects[Constants.BIN_GRAY] = coords.boundsInRoot()
                }
        )
    }
}

@Composable
private fun TrashBinButton(
    label: String,
    binKey: String,
    imageRes: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(96.dp)
            .clip(RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = label,
            modifier = Modifier.size(72.dp)
        )
    }
}

@Composable
private fun TrashSortSettingsDialog(
    soundEnabled: Boolean,
    onSoundEnabledChange: (Boolean) -> Unit,
    useDarkBoard: Boolean,
    onUseDarkBoardChange: (Boolean) -> Unit,
    onBackToMenu: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color.White,
            shadowElevation = 12.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Pengaturan",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF263238)
                )

                SettingsSwitchRow(
                    icon = "🔊",
                    title = "Audio",
                    subtitle = "Aktifkan efek suara game",
                    checked = soundEnabled,
                    onCheckedChange = onSoundEnabledChange
                )

                SettingsSwitchRow(
                    icon = "🎨",
                    title = "Theme",
                    subtitle = "Gunakan tampilan papan gelap",
                    checked = useDarkBoard,
                    onCheckedChange = onUseDarkBoardChange
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onBackToMenu,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Kembali")
                    }
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Lanjut")
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSwitchRow(
    icon: String,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFF7FAF8)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 22.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF263238)
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color(0xFF78909C)
                )
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
private fun TrashSortGameOverDialog(
    score: Int,
    collected: Int,
    lives: Int,
    onBackToGames: () -> Unit
) {
    Dialog(onDismissRequest = {}) {
        Surface(
            shape = RoundedCornerShape(30.dp),
            color = Color.White,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = if (score > 0) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                ) {
                    Text(
                        text = if (score > 0) "🌱" else "🗑️",
                        fontSize = 42.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Text(
                    text = "Game Over",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF263238)
                )

                Text(
                    text = "Waktu bermain selesai. Berikut hasil sortir sampahmu.",
                    textAlign = TextAlign.Center,
                    color = Color(0xFF607D8B),
                    lineHeight = 20.sp
                )

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    color = Color(0xFFF6F8F6)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ResultStatRow(label = "Sampah terkumpul", value = "$collected")
                        ResultStatRow(label = "Skor", value = "$score XP")
                        ResultStatRow(label = "Hero Koin", value = "${score / 2} HK")
                        ResultStatRow(label = "Sisa nyawa", value = "$lives")
                    }
                }

                Button(
                    onClick = onBackToGames,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Kembali ke Games",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun ResultStatRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color(0xFF607D8B),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = value,
            color = Color(0xFF2E7D32),
            fontWeight = FontWeight.ExtraBold
        )
    }
}
