package com.example.luminasdgs.data.model

data class Quest(
    val id: Int,
    val title: String,
    val description: String,
    val sdgTag: String,
    val xpReward: Int,
    val coinReward: Int,
    val waterReward: Int,
    val fertilizerReward: Int,
    val isCompleted: Boolean = false
)
