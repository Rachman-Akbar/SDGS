package com.example.luminasdgs.ui.screens.game.quiz

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.luminasdgs.ui.screens.game.components.ImmersiveGameHeader
import com.example.luminasdgs.ui.screens.game.components.formatTimer
import com.example.luminasdgs.ui.screens.game.components.rememberCountdownTimer
import com.example.luminasdgs.viewmodel.QuizViewModel
import kotlinx.coroutines.delay

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
    var showRewardToast by remember { mutableStateOf(false) }

    val isRunning = viewModel.isQuizStarted && question != null && !quizOverByTimeout && lives > 0
    val timerSeconds = rememberCountdownTimer(
        initialSeconds = 45,
        isRunning = isRunning,
        resetKey = viewModel.isQuizStarted,
        onFinished = { quizOverByTimeout = true }
    )

    LaunchedEffect(viewModel.answeredCount) {
        if (viewModel.answeredCount > processedAnswers) {
            if (viewModel.feedbackMessage?.contains("Salah") == true) {
                lives = (lives - 1).coerceAtLeast(0)
            } else if (viewModel.feedbackMessage?.contains("Benar") == true) {
                showRewardToast = true
                delay(800)
                showRewardToast = false
            }
            processedAnswers = viewModel.answeredCount
        }
    }

    val isGameOver = quizOverByTimeout || lives <= 0
    val progress = when {
        !viewModel.isQuizStarted -> 0f
        isGameOver -> 1f
        else -> (viewModel.answeredCount.toFloat() / totalQuestions).coerceIn(0f, 1f)
    }

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 20.dp, bottom = 40.dp)
    ) {
        item {
            ImmersiveGameHeader(
                title = "SDG Quiz\nBattle",
                subtitle = "Daily Quest: Earth Guardian",
                timer = formatTimer(timerSeconds),
                score = viewModel.score.toString(),
                target = "$currentNumber/$totalQuestions",
                lives = lives,
                xpGain = "+50 XP",
                pointGain = "+20 HK",
                progress = progress,
                onExit = { navController.popBackStack() },
                onSettings = {}
            )
        }

        item {
            AnimatedVisibility(
                visible = showRewardToast,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFB2F5EA)
                ) {
                    Text(
                        text = "Combo Bonus +10 XP",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00695C)
                    )
                }
            }
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
        } else if (isGameOver || question == null) {
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
                val isWrongFeedback = viewModel.feedbackMessage?.contains("Salah") == true
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = if (isWrongFeedback) androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFD32F2F)) else null
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .background(Color(0xFFE8F5E9), RoundedCornerShape(14.dp))
                        ) {
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuD-R1V14CYopYJGm82mOdC74yf2bWt5e7G6tFlArs6UWD-yCliWU_i8ZYfOpjrmcZMc1COlMbwi2Q1Xvr_iOwBS5Oh9jtIx9XbNbGRVVUuKH-E3SEDOoaGlkKjdDy5CE6j4lu04ktTyHsH7bpa7DpTQvBDIbJANIw0A6bSvWVHXM4saUjbi82y0RymKpqfWeeV0fKOXw9OWz3i1ZUqxBxvTs7igUjGVPSYlF91kuiMqv3H8M9GHbIyGfyXDy9lTo8PYdeJbiir2AA",
                                contentDescription = "Quiz illustration",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(text = question.question, style = MaterialTheme.typography.titleMedium)
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            question.options.forEachIndexed { index, option ->
                                OptionButton(
                                    label = ('A' + index).toString(),
                                    text = option,
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
private fun OptionButton(label: String, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
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
