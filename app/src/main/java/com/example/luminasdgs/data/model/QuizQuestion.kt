package com.example.luminasdgs.data.model

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
    val difficulty: String,
    val explanation: String
)
