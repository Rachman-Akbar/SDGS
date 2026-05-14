package com.example.luminasdgs.ui.screens.tree

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Park
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.luminasdgs.ui.components.CompletionDialog
import com.example.luminasdgs.ui.components.CompletionReward
import com.example.luminasdgs.viewmodel.TreeViewModel

@Composable
fun TreeScreen(viewModel: TreeViewModel = viewModel()) {
    val progress = (viewModel.state.growthPoint / 100f).coerceIn(0f, 1f)
    val nextStage = when (viewModel.state.level) {
        1 -> "Young Sprout"
        2 -> "Leafy Sapling"
        3 -> "Mature Oak"
        4 -> "Ancient Guardian"
        else -> "World Tree"
    }
    val completedMissions = remember { mutableStateListOf<Int>() }
    var selectedMission by remember { mutableStateOf<TreeQuestMission?>(null) }

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {
        item {
            TreeTopBar()
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFE3FCEC), Color(0xFFB9F6CA))
                        )
                    )
            ) {
                AsyncImage(
                    model = "https://lh3.googleusercontent.com/aida-public/AB6AXuAZwfodiWD89jVpXfmHFt8Zh77_1lAXaNag6V7_GYck4Wdpx17ZGtLhssg8sZV9_0T56dm2KXHRsPR0kLuW8s8NnMyK6njLFRBpXAoUBv6M42FDq6M3OxUBdu8U2UkcvKEWQAKppqsbPpI1xrnceyxe7lmkxbdAGyeHOJuxsXcDrS3XARBwrMOphkpNPoACIRm_dDoZLLfreseIA1VBwGgy4xQvHgmjpK5jTIyYG0rnDcNGk0BEQuCJTFjLh1TaasjAjzuFnC5HAQ",
                    contentDescription = "Virtual tree",
                    modifier = Modifier.fillMaxWidth().height(360.dp),
                    contentScale = ContentScale.Crop
                )

                Surface(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp),
                    color = Color(0xFFB2DFDB),
                    shape = RoundedCornerShape(999.dp),
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Eco,
                            contentDescription = null,
                            tint = Color(0xFF0B5345)
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(
                            text = "LEVEL ${viewModel.state.level} TREE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0B5345)
                        )
                    }
                }

                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    color = Color.White.copy(alpha = 0.92f),
                    shape = RoundedCornerShape(20.dp),
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "NEXT STAGE: ${nextStage.uppercase()}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "${(progress * 100).toInt()}%",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .clip(RoundedCornerShape(999.dp))
                                .background(Color(0xFFE0F2F1))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(progress)
                                    .height(12.dp)
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(Color(0xFF2E7D32), Color(0xFF6EE7B7))
                                        )
                                    )
                            )
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatActionCard(
                    modifier = Modifier.weight(1f),
                    title = "Water",
                    subtitle = "Free Daily",
                    icon = Icons.Filled.Opacity,
                    tint = Color(0xFF00695C),
                    background = Color(0xFFB2F5EA),
                    onClick = { viewModel.waterTree() }
                )
                StatActionCard(
                    modifier = Modifier.weight(1f),
                    title = "Fertilize",
                    subtitle = "${viewModel.state.fertilizer} HK",
                    icon = Icons.Filled.Park,
                    tint = Color(0xFF8D6E00),
                    background = Color(0xFFFFE082),
                    onClick = { viewModel.fertilizeTree() }
                )
            }
        }

        item {
            TreeQuestMissionSection()
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Garden Badges",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "View All",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(sampleBadges()) { badge ->
                        BadgeItem(badge = badge)
                    }
                }
            }
        }
    }
}

@Composable
private fun TreeTopBar() {
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
                Text(text = "HQ", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Hero Quest",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Surface(
            shape = RoundedCornerShape(999.dp),
            color = MaterialTheme.colorScheme.surface
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
private fun StatActionCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    background: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(background),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = tint)
            }
            Text(text = title, fontWeight = FontWeight.SemiBold, color = tint)
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Button(onClick = onClick, shape = RoundedCornerShape(999.dp)) {
                Text(text = "Use")
            }
        }
    }
}

@Composable
private fun BadgeItem(badge: BadgeData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.width(96.dp)
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = badge.imageUrl,
                contentDescription = badge.title,
                modifier = Modifier.size(58.dp),
                contentScale = ContentScale.Crop
            )
            if (badge.count > 1) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "x${badge.count}",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
        Text(
            text = badge.title,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun TreeQuestMissionSection() {
    val completedMissions = remember { mutableStateListOf<Int>() }
    var selectedMission by remember { mutableStateOf<TreeQuestMission?>(null) }
    val missions = listOf(
        TreeQuestMission(
            id = 1,
            title = "Main 2x game hari ini",
            description = "Selesaikan dua mini-game untuk menumbuhkan pohon lebih cepat.",
            progress = 0.5f,
            reward = "+40 XP • +15 HK • +1 Air",
            accent = Color(0xFF2E7D32)
        ),
        TreeQuestMission(
            id = 2,
            title = "Menang 1 game tanpa timeout",
            description = "Tunjukkan fokusmu di satu game penuh untuk bonus hadiah kebun.",
            progress = 0.75f,
            reward = "+60 XP • +1 Pupuk",
            accent = Color(0xFF1565C0)
        ),
        TreeQuestMission(
            id = 3,
            title = "Buat 3 jawaban benar beruntun",
            description = "Kombo yang stabil akan langsung memberi item tanam tambahan.",
            progress = 0.33f,
            reward = "+30 XP • Bibit Langka",
            accent = Color(0xFF8D6E00)
        )
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Quest Harian Pohon",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Reset harian",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        missions.forEach { mission ->
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
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = mission.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = mission.description,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(999.dp),
                            color = mission.accent.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = mission.reward,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = mission.accent
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color(0xFFE0F2F1))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(mission.progress.coerceIn(0f, 1f))
                                .height(10.dp)
                                .clip(RoundedCornerShape(999.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(mission.accent, mission.accent.copy(alpha = 0.55f))
                                    )
                                )
                        )
                    }

                    Button(
                        onClick = {
                            if (!completedMissions.contains(mission.id)) completedMissions.add(mission.id)
                            selectedMission = mission
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(999.dp),
                        enabled = !completedMissions.contains(mission.id)
                    ) {
                        Text(text = if (completedMissions.contains(mission.id)) "Selesai" else "Klaim Quest")
                    }
                }
            }
        }

        selectedMission?.let { mission ->
            CompletionDialog(
                title = "Quest Harian Selesai",
                message = mission.title,
                rewards = listOf(
                    CompletionReward("XP", mission.reward.substringBefore("•").trim()),
                    CompletionReward("Bonus", mission.reward.substringAfter("•").trim()),
                    CompletionReward("Item", "Air / Pupuk / Bibit")
                ),
                primaryButtonText = "Ambil Reward",
                secondaryButtonText = "Tutup",
                onPrimaryClick = { selectedMission = null },
                onSecondaryClick = { selectedMission = null },
                onDismiss = { selectedMission = null },
                accentColor = mission.accent
            )
        }
    }
}

private data class TreeQuestMission(
    val id: Int,
    val title: String,
    val description: String,
    val progress: Float,
    val reward: String,
    val accent: Color
)

private data class BadgeData(
    val title: String,
    val imageUrl: String,
    val count: Int = 1
)

private fun sampleBadges(): List<BadgeData> {
    return listOf(
        BadgeData(
            title = "Hydrator",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuC5Y2ecGI4jVBFIOWLCr1gQmpGjL9rTFJCfvqBtWSFaIk-eT0r7Wj958tHXrmPVl1EMN0N8ECxaR4F-yUU1YWsgR69JjmrZAKPfTNdy-rOPufZMVPE09nBNLbwN2TkbywHKWvUrVe33uO80qnAVRsvunyXMk5xmHA4Otiy-DybT6jsfTE6wK9jSYnVeyB8OOjOzPDxiiYnjpFcGAkXgJdZjPQl726Y5AV2Hqw5xWFMCTQCI6pNMQymJKL0D6twykZMC5cSxF2chdA",
            count = 5
        ),
        BadgeData(
            title = "Sun Seeker",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB9gqiIYb49W2pR7l9Rzz3um0wjgBW8di83bOklxw8A0RHl5w2Xg4CYZmvQbAQ57u3tgYYP7rK1ZtMB7eCOE1LZ6sxNPl0X7WEr2oP6v2AfN8fQWm2GfZqG1e6NBVh2BB0Y6K5VfVfXa6T0Ob9b5XAjbqahV8M49Pm4B6J0x8Ux8zE4GhYgk8OsaDEB69X5aYw0dN_F7A1yOtX1Y9WmJQ5rQBGN_wdVYJDBYAZ1FkM9vOGuUQdU-uPMb7",
            count = 1
        ),
        BadgeData(
            title = "Nester",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAOO9z_6EUY0ZQLNrK3g90C1Cxyaf0h8Y3T6rLZqkF8lT0bKk6xbIh5BrO1wFj7tX4O72YW8bG2cElCLQykN5X_UA_9Cj9EwCb3GxwdgQ1xI3l7vHxtcBDtAzxObpy5gKKaSp6qkV7IOc5zUp_e5Okc7e-LjxZydSFrscCqOQh5AdTKAAhWk6_4V4gR9t7W5zJ3d4Z4k4C4B3rH1o0GpUPQ1s8yfpD2bODNItUb92aCAm",
            count = 1
        )
    )
}
