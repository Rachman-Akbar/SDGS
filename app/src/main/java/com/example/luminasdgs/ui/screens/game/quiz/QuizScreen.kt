package com.example.luminasdgs.ui.screens.game.quiz

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.animation.scaleOut
import com.example.luminasdgs.ui.components.CompletionDialog
import com.example.luminasdgs.ui.components.CompletionReward
import com.example.luminasdgs.ui.screens.game.components.CompactGameHeader
import com.example.luminasdgs.ui.screens.game.components.GameBackground
import com.example.luminasdgs.ui.screens.game.components.PreGameCountdownOverlay
import com.example.luminasdgs.viewmodel.QuizViewModel
import kotlinx.coroutines.delay

private const val GAME_DURATION_SECONDS = 60
private const val CORRECT_TIME_BONUS_SECONDS = 4
private const val MAX_TIME_SECONDS = 120

@Composable
fun QuizScreen(
    navController: NavController,
    viewModel: QuizViewModel = viewModel()
) {
    val question = viewModel.currentQuestion
    val totalQuestions = if (viewModel.isQuizStarted) viewModel.questions.size.coerceAtLeast(1) else 5
    val currentNumber = if (question == null) totalQuestions else (viewModel.currentIndex + 1).coerceAtMost(totalQuestions)

    var lives by rememberSaveable(viewModel.isQuizStarted) { mutableIntStateOf(3) }
    var quizOverByTimeout by rememberSaveable(viewModel.isQuizStarted) { mutableStateOf(false) }
    var processedAnswers by rememberSaveable(viewModel.isQuizStarted) { mutableIntStateOf(0) }
    var showCompletionModal by rememberSaveable(viewModel.isQuizStarted) { mutableStateOf(false) }
    var remainingSeconds by rememberSaveable(viewModel.isQuizStarted) { mutableIntStateOf(GAME_DURATION_SECONDS) }
    var countdownActive by rememberSaveable(viewModel.isQuizStarted) { mutableStateOf(true) }
    var countdownSeconds by rememberSaveable(viewModel.isQuizStarted) { mutableIntStateOf(3) }

    val isRunning = viewModel.isQuizStarted && question != null && !quizOverByTimeout && lives > 0 && !countdownActive
    val timeProgress = (remainingSeconds.toFloat() / GAME_DURATION_SECONDS).coerceIn(0f, 1f)

    LaunchedEffect(countdownActive, countdownSeconds, viewModel.isQuizStarted) {
        if (!viewModel.isQuizStarted || !countdownActive) return@LaunchedEffect
        if (countdownSeconds <= 0) {
            countdownActive = false
            return@LaunchedEffect
        }
        delay(1_000L)
        countdownSeconds -= 1
    }

    LaunchedEffect(isRunning) {
        while (isRunning && remainingSeconds > 0) {
            delay(1_000L)
            remainingSeconds = (remainingSeconds - 1).coerceAtLeast(0)
            if (remainingSeconds == 0) {
                quizOverByTimeout = true
            }
        }
    }

    LaunchedEffect(viewModel.answeredCount) {
        if (viewModel.answeredCount > processedAnswers) {
            if (viewModel.feedbackMessage?.contains("Salah") == true) {
                lives = (lives - 1).coerceAtLeast(0)
            } else if (viewModel.feedbackMessage?.contains("Benar") == true) {
                remainingSeconds = (remainingSeconds + CORRECT_TIME_BONUS_SECONDS).coerceAtMost(MAX_TIME_SECONDS)
            }
            processedAnswers = viewModel.answeredCount
        }
    }

    LaunchedEffect(viewModel.answeredCount) {
        if (viewModel.isAnswerLocked) {
            delay(1_000L)
            viewModel.advanceQuestion()
        }
    }

    val isGameOver = quizOverByTimeout || lives <= 0
    val progress = if (viewModel.isQuizStarted) timeProgress else 0f
    val quizCompleted = viewModel.isQuizStarted && question == null && !quizOverByTimeout && lives > 0 && viewModel.answeredCount >= totalQuestions

    LaunchedEffect(quizCompleted) {
        if (quizCompleted) {
            showCompletionModal = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        GameBackground(modifier = Modifier.fillMaxSize())
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(top = 20.dp, bottom = 40.dp)
        ) {
            item {
                CompactGameHeader(
                    progress = progress,
                    lives = lives,
                    statLabel = "Skor",
                    statValue = viewModel.score.toString(),
                    instruction = "Pilih jawaban yang benar sebelum waktu habis.",
                    onExit = { navController.popBackStack() },
                    onSettings = {}
                )
            }
            if (!viewModel.isQuizStarted) {
                item {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "Pilih tingkat kesulitan untuk mulai bermain",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            RowDifficultyButtons(onSelect = { viewModel.startQuiz(it) })
                        }
                    }
                }
            } else if (isGameOver) {
                item {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = if (quizOverByTimeout) "Waktu Habis" else if (lives <= 0) "Nyawa Habis" else "Quest Completed!",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(text = "Skor: ${viewModel.score}")
                            Text(text = "Jawaban benar: ${viewModel.correctCount}/${viewModel.answeredCount}")
                            Button(onClick = { viewModel.startQuiz("Mudah") }, modifier = Modifier.fillMaxWidth()) {
                                Text(text = "Main Lagi")
                            }
                            Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
                                Text(text = "Kembali ke Hub")
                            }
                        }
                    }
                }
            } else {
                item {
                    val currentQuestion = question ?: return@item
                    AnimatedContent(
                        targetState = currentQuestion,
                        transitionSpec = {
                            (fadeIn() + scaleIn(initialScale = 0.98f)) togetherWith (fadeOut() + scaleOut(targetScale = 1.02f))
                        },
                        label = "QuizQuestionTransition"
                    ) { targetQuestion ->
                        val isWrongFeedback = viewModel.feedbackMessage?.contains("Salah") == true
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 240.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.72f)),
                            border = if (isWrongFeedback) androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFD32F2F)) else null
                        ) {
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text(text = targetQuestion.question, style = MaterialTheme.typography.titleMedium)
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    targetQuestion.options.forEachIndexed { index, option ->
                                        OptionButton(
                                            label = ('A' + index).toString(),
                                            text = option,
                                            enabled = !viewModel.isAnswerLocked,
                                            onClick = { viewModel.answerQuestion(option) }
                                        )
                                    }
                                }

                                val feedback = viewModel.feedbackMessage
                                AnimatedVisibility(
                                    visible = feedback != null,
                                    enter = fadeIn() + scaleIn(initialScale = 0.9f),
                                    exit = fadeOut()
                                ) {
                                    if (feedback != null) {
                                        Surface(
                                            shape = RoundedCornerShape(14.dp),
                                            color = if (feedback.contains("Benar")) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                                        ) {
                                            Column(modifier = Modifier.padding(12.dp)) {
                                                Text(text = feedback, fontWeight = FontWeight.Bold)
                                                viewModel.lastExplanation?.let { Text(text = it, fontSize = 13.sp) }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (viewModel.isQuizStarted && countdownActive) {
            PreGameCountdownOverlay(
                countdown = countdownSeconds,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    if (showCompletionModal) {
        CompletionDialog(
            title = "Quiz Selesai",
            message = "Semua pertanyaan berhasil dijawab.",
            rewards = listOf(
                CompletionReward("XP", "+50 XP"),
                CompletionReward("Hero Koin", "+20 HK"),
                CompletionReward("Bonus", "Item pengetahuan")
            ),
            primaryButtonText = "Kembali ke Hub",
            secondaryButtonText = "Main Lagi",
            onPrimaryClick = {
                showCompletionModal = false
                navController.popBackStack()
            },
            onSecondaryClick = {
                showCompletionModal = false
                viewModel.startQuiz("Mudah")
            },
            onDismiss = { showCompletionModal = false },
            accentColor = Color(0xFF2E7D32)
        )
    }
}

@Composable
private fun RowDifficultyButtons(onSelect: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = { onSelect("Mudah") }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Mudah")
        }
        Button(onClick = { onSelect("Sedang") }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Sedang")
        }
        Button(onClick = { onSelect("Sulit") }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Sulit")
        }
    }
}

@Composable
private fun OptionButton(label: String, text: String, enabled: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(14.dp))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .background(Color(0xFFF0F0F0), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = label, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.size(12.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}
