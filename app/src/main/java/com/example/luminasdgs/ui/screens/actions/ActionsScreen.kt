package com.example.luminasdgs.ui.screens.actions

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.luminasdgs.ui.components.CompletionDialog
import com.example.luminasdgs.ui.components.CompletionReward

@Composable
fun ActionsScreen() {
    val categories = listOf("Semua", "Sampah", "Energi", "Air", "Transportasi")
    val actions = listOf(
        ActionTask(
            id = 1,
            title = "Pakai Transportasi Umum",
            description = "Tinggalkan mobil hari ini dan pilih bus atau kereta untuk mengurangi emisi.",
            xp = 50,
            hk = 10,
            icon = Icons.Filled.DirectionsBus,
            accent = Color(0xFF2E7D32)
        ),
        ActionTask(
            id = 2,
            title = "Kompos Sisa Makanan",
            description = "Ubah sampah organik menjadi tanah subur untuk membantu tanaman tumbuh.",
            xp = 30,
            hk = 5,
            icon = Icons.Filled.Recycling,
            accent = Color(0xFF2E7D32)
        ),
        ActionTask(
            id = 3,
            title = "Ganti ke Lampu LED",
            description = "Ganti satu lampu biasa dengan LED yang lebih hemat energi.",
            xp = 25,
            hk = 8,
            icon = Icons.Filled.Lightbulb,
            accent = Color(0xFFF9A825)
        ),
        ActionTask(
            id = 4,
            title = "Bawa Botol Minum Sendiri",
            description = "Kurangi botol plastik sekali pakai dengan membawa botol isi ulang.",
            xp = 15,
            hk = 2,
            icon = Icons.Filled.WaterDrop,
            accent = Color(0xFF1565C0)
        )
    )
    val completedIds = remember { mutableStateListOf<Int>() }
    var selectedAction by remember { mutableStateOf<ActionTask?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 20.dp, bottom = 140.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ActionsTopBar()
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Aksi Nyata",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Langkah kecil untukmu, dampak besar untuk bumi.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    categories.forEachIndexed { index, label ->
                        CategoryPill(
                            label = label,
                            selected = index == 0
                        )
                    }
                }
            }

            items(actions) { action ->
                ActionCard(
                    action = action,
                    isCompleted = completedIds.contains(action.id),
                    onComplete = {
                        if (!completedIds.contains(action.id)) completedIds.add(action.id)
                        selectedAction = action
                    }
                )
            }

            item {
                WeeklyImpactCard()
            }
        }

        selectedAction?.let { action ->
            CompletionDialog(
                title = "Aksi Nyata Selesai",
                message = action.title,
                rewards = listOf(
                    CompletionReward("XP", "+${action.xp} XP"),
                    CompletionReward("Hero Koin", "+${action.hk} HK"),
                    CompletionReward("Dampak", "Item kebiasaan hijau")
                ),
                primaryButtonText = "Lanjut",
                secondaryButtonText = "Tutup",
                onPrimaryClick = { selectedAction = null },
                onSecondaryClick = { selectedAction = null },
                onDismiss = { selectedAction = null },
                accentColor = action.accent
            )
        }

    }
}

@Composable
private fun ActionsTopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFB2DFDB)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Eco,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Hero Quest",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Card(
            shape = RoundedCornerShape(999.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Text(
                text = "1,250 XP \u2022 450 HK",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun CategoryPill(label: String, selected: Boolean) {
    val background = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val content = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(background)
            .padding(horizontal = 18.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            letterSpacing = 1.sp,
            fontWeight = FontWeight.Bold,
            color = content
        )
    }
}

@Composable
private fun ActionCard(
    action: ActionTask,
    isCompleted: Boolean,
    onComplete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(action.accent.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = null,
                        tint = action.accent
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "+${action.xp} XP",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "+${action.hk} HK",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

            Text(
                text = action.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = action.description,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Button(
                onClick = onComplete,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(999.dp),
                enabled = !isCompleted
            ) {
                Icon(imageVector = Icons.Filled.Recycling, contentDescription = null)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = if (isCompleted) "Selesai" else "Tandai Selesai")
            }
        }
    }
}

@Composable
private fun WeeklyImpactCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF0B3D20), Color(0xFF2E7D32))
                )
            )
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Dampak Mingguanmu",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Kamu sudah menghemat 12kg CO2 minggu ini. Lanjutkan, Hero!",
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

private data class ActionTask(
    val id: Int,
    val title: String,
    val description: String,
    val xp: Int,
    val hk: Int,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val accent: Color
)
