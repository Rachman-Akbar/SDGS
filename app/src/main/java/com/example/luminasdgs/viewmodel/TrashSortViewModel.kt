package com.example.luminasdgs.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.luminasdgs.data.dummy.TrashDummyData
import com.example.luminasdgs.data.model.TrashItem

private const val CORRECT_SCORE = 10
private const val WRONG_SCORE_PENALTY = 5
private const val STARTING_LIVES = 3

class TrashSortViewModel : ViewModel() {
    data class SpawnedTrash(
        val id: Int,
        val item: TrashItem,
        val x: Int,
        val y: Int
    )

    private val trashPool: List<TrashItem> = TrashDummyData.items
    private var spawnCounter = 0

    val spawned = mutableStateListOf<SpawnedTrash>()

    var score by mutableIntStateOf(0)
        private set

    var lives by mutableIntStateOf(STARTING_LIVES)
        private set

    var handledCount by mutableIntStateOf(0)
        private set

    var correctCount by mutableIntStateOf(0)
        private set

    var wrongCount by mutableIntStateOf(0)
        private set

    var feedbackMessage by mutableStateOf<String?>(null)
        private set

    var isLastAnswerCorrect by mutableStateOf<Boolean?>(null)
        private set

    var isCompleted by mutableStateOf(false)
        private set

    var isGameOver by mutableStateOf(false)
        private set

    fun addSpawnAt(x: Int, y: Int) {
        if (isGameOver || trashPool.isEmpty()) return

        val randomTrash = trashPool.random()
        spawned.add(
            SpawnedTrash(
                id = ++spawnCounter,
                item = randomTrash,
                x = x,
                y = y
            )
        )
    }

    fun handleDrop(spawnId: Int, binColor: String) {
        if (isGameOver) return

        val spawnedTrash = spawned.firstOrNull { it.id == spawnId } ?: return
        val isCorrect = spawnedTrash.item.correctBinColor == binColor

        if (isCorrect) {
            onCorrectAnswer()
        } else {
            onWrongAnswer()
        }

        spawned.remove(spawnedTrash)
        handledCount += 1
    }

    private fun onCorrectAnswer() {
        score += CORRECT_SCORE
        correctCount += 1
        feedbackMessage = "Benar! +$CORRECT_SCORE XP"
        isLastAnswerCorrect = true
    }

    private fun onWrongAnswer() {
        score = (score - WRONG_SCORE_PENALTY).coerceAtLeast(0)
        lives = (lives - 1).coerceAtLeast(0)
        wrongCount += 1
        feedbackMessage = "Salah. -$WRONG_SCORE_PENALTY XP"
        isLastAnswerCorrect = false

        if (lives == 0) {
            endGame(message = "Nyawa habis.", completed = false)
        }
    }

    fun removeSpawn(spawnId: Int) {
        spawned.removeAll { it.id == spawnId }
    }

    fun onTimeout() {
        if (isGameOver) return
        endGame(message = "Waktu habis.", completed = false)
    }

    fun finishGame() {
        if (isGameOver) return
        endGame(message = "Permainan selesai.", completed = true)
    }

    fun resetGame() {
        spawned.clear()
        spawnCounter = 0
        score = 0
        lives = STARTING_LIVES
        handledCount = 0
        correctCount = 0
        wrongCount = 0
        feedbackMessage = null
        isLastAnswerCorrect = null
        isCompleted = false
        isGameOver = false
    }

    private fun endGame(message: String, completed: Boolean) {
        feedbackMessage = message
        isCompleted = completed
        isGameOver = true
        spawned.clear()
    }
}
