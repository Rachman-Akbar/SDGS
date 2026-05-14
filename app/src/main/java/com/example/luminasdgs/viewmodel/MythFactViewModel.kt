package com.example.luminasdgs.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.luminasdgs.data.dummy.MythFactDummyData
import com.example.luminasdgs.data.model.MythFactStatement

class MythFactViewModel : ViewModel() {
    private var statements: List<MythFactStatement> = MythFactDummyData.items.shuffled()

    var currentIndex by mutableIntStateOf(0)
        private set

    var score by mutableIntStateOf(0)
        private set

    var lives by mutableIntStateOf(3)
        private set

    var correctCount by mutableIntStateOf(0)
        private set

    var wrongCount by mutableIntStateOf(0)
        private set

    var lastAnswerCorrect by mutableStateOf<Boolean?>(null)
        private set

    var isCompleted by mutableStateOf(false)
        private set

    var isGameOver by mutableStateOf(false)
        private set

    val currentStatement: MythFactStatement?
        get() = statements.getOrNull(currentIndex)

    fun answer(isFactSelected: Boolean) {
        if (isGameOver) return
        val statement = currentStatement ?: return
        val isCorrect = statement.isFact == isFactSelected

        if (isCorrect) {
            score += 10
            correctCount += 1
            lastAnswerCorrect = true
        } else {
            score = (score - 5).coerceAtLeast(0)
            wrongCount += 1
            lastAnswerCorrect = false
            lives = (lives - 1).coerceAtLeast(0)
            if (lives <= 0) {
                isGameOver = true
                return
            }
        }

        currentIndex += 1
        if (currentIndex >= statements.size) {
            isCompleted = true
            isGameOver = true
        }
    }

    fun onTimeout() {
        if (!isGameOver) {
            isGameOver = true
            lastAnswerCorrect = false
        }
    }

    fun resetGame() {
        statements = MythFactDummyData.items.shuffled()
        currentIndex = 0
        score = 0
        correctCount = 0
        wrongCount = 0
        lives = 3
        lastAnswerCorrect = null
        isCompleted = false
        isGameOver = false
    }
}
