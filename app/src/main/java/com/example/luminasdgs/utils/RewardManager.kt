package com.example.luminasdgs.utils

object RewardManager {
    fun calculateLevelFromXp(xp: Int): Int {
        return (xp / 100) + 1
    }
}
