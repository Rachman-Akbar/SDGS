package com.example.luminasdgs.data.model

import androidx.annotation.DrawableRes

data class TrashItem(
    val name: String,
    val correctBinColor: String,
    val category: String,
    @DrawableRes val imageRes: Int
)
