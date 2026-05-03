package com.example.luminasdgs.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.luminasdgs.data.dummy.TrashDummyData
import com.example.luminasdgs.data.model.TrashItem

class TrashSortViewModel : ViewModel() {
    private val items = TrashDummyData.items.shuffled()

    var currentIndex by mutableStateOf(0)
        private set
    var score by mutableStateOf(0)
        private set
    var lives by mutableStateOf(3)
        private set
    var feedbackMessage by mutableStateOf<String?>(null)
        private set
    var isLastAnswerCorrect by mutableStateOf<Boolean?>(null)
        private set
    var isGameOver by mutableStateOf(false)
        private set

    val currentItem: TrashItem?
        get() = items.getOrNull(currentIndex)

    fun selectBin(color: String) {
        if (isGameOver) return
        val item = currentItem ?: return
        if (item.correctBinColor == color) {
            score += 10
            feedbackMessage = "Benar!"
            isLastAnswerCorrect = true
        } else {
            score -= 5
            feedbackMessage = "Salah."
            lives -= 1
            isLastAnswerCorrect = false
        }
        currentIndex += 1
        if (currentIndex >= items.size || lives <= 0) {
            isGameOver = true
        }
    }

    fun onTimeout() {
        if (!isGameOver) {
            feedbackMessage = "Waktu habis."
            isLastAnswerCorrect = false
            isGameOver = true
        }
    }
}
