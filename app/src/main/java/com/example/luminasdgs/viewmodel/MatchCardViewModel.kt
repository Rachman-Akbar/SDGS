package com.example.luminasdgs.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.luminasdgs.data.dummy.MatchCardDummyData
import com.example.luminasdgs.data.model.MatchCard

class MatchCardViewModel : ViewModel() {
    private val initialCards = MatchCardDummyData.cards.shuffled()

    var cards by mutableStateOf(initialCards)
        private set
    var score by mutableStateOf(0)
        private set
    var matchedPairs by mutableStateOf(0)
        private set
    var lives by mutableStateOf(3)
        private set
    var lastMoveMatched by mutableStateOf<Boolean?>(null)
        private set
    var isCompleted by mutableStateOf(false)
        private set
    var isGameOver by mutableStateOf(false)
        private set

    private var selectedIds = listOf<Int>()

    fun flipCard(cardId: Int) {
        if (isCompleted || isGameOver) return
        val card = cards.firstOrNull { it.id == cardId } ?: return
        if (card.isMatched || card.isFlipped || selectedIds.size == 2) return

        cards = cards.map {
            if (it.id == cardId) it.copy(isFlipped = true) else it
        }
        selectedIds = selectedIds + cardId

        if (selectedIds.size == 2) {
            val first = cards.first { it.id == selectedIds[0] }
            val second = cards.first { it.id == selectedIds[1] }
            if (first.pairId == second.pairId) {
                cards = cards.map {
                    if (it.id == first.id || it.id == second.id) it.copy(isMatched = true) else it
                }
                score += 10
                matchedPairs += 1
                lastMoveMatched = true
                if (matchedPairs == cards.size / 2) {
                    isCompleted = true
                }
            } else {
                cards = cards.map {
                    if (it.id == first.id || it.id == second.id) it.copy(isFlipped = false) else it
                }
                lives -= 1
                lastMoveMatched = false
                if (lives <= 0) {
                    isGameOver = true
                }
            }
            selectedIds = emptyList()
        }
    }

    fun onTimeout() {
        if (!isCompleted && !isGameOver) {
            isGameOver = true
            lastMoveMatched = false
        }
    }
}
