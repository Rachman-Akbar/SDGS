package com.example.luminasdgs.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.luminasdgs.data.model.TreeState

class TreeViewModel : ViewModel() {
    var state by mutableStateOf(TreeState(level = 1, growthPoint = 0, water = 3, fertilizer = 2))
        private set

    val levelName: String
        get() = when (state.level) {
            1 -> "Benih"
            2 -> "Tunas"
            3 -> "Pohon Kecil"
            4 -> "Pohon Sehat"
            else -> "Pohon Kehidupan"
        }

    fun waterTree() {
        if (state.water <= 0) return
        applyGrowth(addGrowth = 10, waterDelta = -1, fertilizerDelta = 0)
    }

    fun fertilizeTree() {
        if (state.fertilizer <= 0) return
        applyGrowth(addGrowth = 15, waterDelta = 0, fertilizerDelta = -1)
    }

    private fun applyGrowth(addGrowth: Int, waterDelta: Int, fertilizerDelta: Int) {
        var newLevel = state.level
        var newGrowth = state.growthPoint + addGrowth
        var newWater = state.water + waterDelta
        var newFertilizer = state.fertilizer + fertilizerDelta

        // Level up once growth reaches the threshold.
        if (newLevel < 5 && newGrowth >= 100) {
            newLevel += 1
            newGrowth -= 100
        }

        if (newLevel >= 5) {
            newLevel = 5
            newGrowth = newGrowth.coerceAtMost(100)
        }

        state = state.copy(
            level = newLevel,
            growthPoint = newGrowth,
            water = newWater,
            fertilizer = newFertilizer
        )
    }
}
