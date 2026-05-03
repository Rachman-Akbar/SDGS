package com.example.luminasdgs.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WaterDrop

@Composable
fun TopStatusBar(
    xp: Int,
    coins: Int,
    water: Int,
    fertilizer: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RewardCard(label = "XP", value = xp.toString(), icon = Icons.Filled.Star)
        RewardCard(label = "Coin", value = coins.toString(), icon = Icons.Filled.MonetizationOn)
        RewardCard(label = "Air", value = water.toString(), icon = Icons.Filled.WaterDrop)
        RewardCard(label = "Pupuk", value = fertilizer.toString(), icon = Icons.Filled.LocalFlorist)
    }
}
