package com.example.luminasdgs.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.luminasdgs.data.dummy.QuizDummyData
import com.example.luminasdgs.data.model.QuizQuestion

class QuizViewModel : ViewModel() {
    private val allQuestions = QuizDummyData.questions

    var questions by mutableStateOf<List<QuizQuestion>>(emptyList())
        private set
    var currentIndex by mutableStateOf(0)
        private set
    var score by mutableStateOf(0)
        private set
    var correctCount by mutableStateOf(0)
        private set
    var answeredCount by mutableStateOf(0)
        private set
    var feedbackMessage by mutableStateOf<String?>(null)
        private set
    var lastExplanation by mutableStateOf<String?>(null)
        private set
    var isAnswerLocked by mutableStateOf(false)
        private set
    var isQuizStarted by mutableStateOf(false)
        private set

    val currentQuestion: QuizQuestion?
        get() = questions.getOrNull(currentIndex)

    fun startQuiz(difficulty: String) {
        // Reset quiz state and load questions by difficulty.
        questions = allQuestions.filter { it.difficulty == difficulty }.shuffled()
        if (questions.isEmpty()) {
            questions = allQuestions.shuffled()
        }
        currentIndex = 0
        score = 0
        correctCount = 0
        answeredCount = 0
        feedbackMessage = null
        lastExplanation = null
        isAnswerLocked = false
        isQuizStarted = true
    }

    fun answerQuestion(answer: String) {
        if (isAnswerLocked) return
        val question = currentQuestion ?: return
        val isCorrect = answer == question.correctAnswer

        if (isCorrect) {
            score += 10
            correctCount += 1
            feedbackMessage = "Benar!"
        } else {
            feedbackMessage = "Salah."
        }
        lastExplanation = question.explanation
        answeredCount += 1
        isAnswerLocked = true
    }

    fun advanceQuestion() {
        if (!isAnswerLocked) return
        currentIndex += 1
        feedbackMessage = null
        lastExplanation = null
        isAnswerLocked = false
    }
}
