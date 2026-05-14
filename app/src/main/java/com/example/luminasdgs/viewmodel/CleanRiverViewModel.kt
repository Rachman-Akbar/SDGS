package com.example.luminasdgs.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CleanRiverViewModel : ViewModel() {
    data class RiverItem(val name: String, val type: String)

    private val items = listOf(
        RiverItem("Botol plastik", "trash"),
        RiverItem("Kantong plastik", "trash"),
        RiverItem("Kaleng", "trash"),
        RiverItem("Ikan nila", "fish"),
        RiverItem("Ikan mas", "fish"),
        RiverItem("Ikan kecil", "fish")
    ).shuffled()

    var currentItem by mutableStateOf<RiverItem?>(null)
        private set
    var processedCount by mutableStateOf(0)
        private set
    var score by mutableStateOf(0)
        private set
    var life by mutableStateOf(3)
        private set
    var lastActionWasCorrect by mutableStateOf<Boolean?>(null)
        private set
    var isCompleted by mutableStateOf(false)
        private set
    var isGameOver by mutableStateOf(false)
        private set

    init {
        nextItem()
    }

    fun takeItem() {
        if (isGameOver) return
        val item = currentItem ?: return
        if (item.type == "trash") {
            score += 10
            lastActionWasCorrect = true
        } else {
            score -= 10
            life -= 1
            lastActionWasCorrect = false
            if (life <= 0) {
                isGameOver = true
                currentItem = null
                return
            }
        }
        processedCount += 1
        nextItem()
    }

    fun skipItem() {
        if (isGameOver) return
        val item = currentItem ?: return
        if (item.type == "trash") {
            life -= 1
            lastActionWasCorrect = false
            if (life <= 0) {
                isGameOver = true
                currentItem = null
                return
            }
        } else {
            score += 2
            lastActionWasCorrect = true
        }
        processedCount += 1
        nextItem()
    }

    fun onTimeout() {
        if (!isGameOver) {
            isGameOver = true
            currentItem = null
            lastActionWasCorrect = false
        }
    }

    private fun nextItem() {
        if (processedCount >= items.size) {
            isCompleted = true
            isGameOver = true
            currentItem = null
            return
        }
        if (life <= 0) {
            isGameOver = true
            currentItem = null
            return
        }
        currentItem = items.getOrNull(processedCount)
    }
}
