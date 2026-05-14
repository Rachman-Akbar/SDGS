package com.example.luminasdgs.ui.screens.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CompactGameHeader(
    progress: Float,
    lives: Int,
    statLabel: String,
    statValue: String,
    instruction: String,
    onExit: () -> Unit,
    onSettings: () -> Unit
) {
    var showExitConfirm by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(onClick = { showExitConfirm = true }, modifier = Modifier.size(44.dp)) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = null, tint = Color(0xFF4E5C55))
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(12.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(Color(0xFFE3E0DD))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress.coerceIn(0f, 1f))
                        .height(12.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color(0xFF1B5E20))
                )
            }

            IconButton(onClick = onSettings, modifier = Modifier.size(44.dp)) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = null, tint = Color(0xFF4E5C55))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(3) { index ->
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = if (index < lives) Color(0xFFD32F2F) else Color(0xFFBDBDBD),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$statLabel:",
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF6B7470)
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    text = statValue,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF2E7D32)
                )
            }
        }

        Text(
            text = instruction,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF6B7470)
        )
    }

    if (showExitConfirm) {
        AlertDialog(
            onDismissRequest = { showExitConfirm = false },
            title = { Text(text = "Keluar dari game?") },
            text = { Text(text = "Progress kamu tidak akan tersimpan.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitConfirm = false
                        onExit()
                    }
                ) {
                    Text(text = "Keluar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitConfirm = false }) {
                    Text(text = "Batal")
                }
            }
        )
    }
}
