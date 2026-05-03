package com.example.luminasdgs.data.model

data class UserProfile(
    val name: String,
    val level: Int,
    val xp: Int,
    val coins: Int,
    val completedQuest: Int,
    val gamesPlayed: Int,
    val treeLevel: Int
)
