package com.example.luminasdgs.ui.screens.profile.achievement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.luminasdgs.data.dummy.TrashDummyData

@Composable
fun TrashSortAchievementScreen(navController: NavController) {
    val order = listOf("Plastik", "B3", "Organik", "Kertas", "Residu")
    val grouped = TrashDummyData.items.groupBy { it.category }
    val categories = order.mapNotNull { label ->
        grouped[label]?.let { items ->
            TrashCategory(label.uppercase(), binColor(label), items.map { it.name })
        }
    }

    var selectedTab by remember { mutableIntStateOf(0) }
    val active = categories[selectedTab]

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 20.dp, bottom = 120.dp)
    ) {
        item {
            AchievementTopBar(title = "Sortir Sampah", onBack = { navController.popBackStack() })
        }

        item {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = active.color
                    )
                }
            ) {
                categories.forEachIndexed { index, item ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = item.label,
                                color = if (selectedTab == index) item.color else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    )
                }
            }
        }

        items(active.items) { trash ->
            TrashItemCard(text = trash, accent = active.color)
        }
    }
}

@Composable
private fun TrashItemCard(text: String, accent: Color) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = accent)
        }
    }
}

private data class TrashCategory(
    val label: String,
    val color: Color,
    val items: List<String>
)

private fun binColor(category: String): Color = when (category) {
    "Plastik" -> Color(0xFFFBC02D)
    "B3" -> Color(0xFFE53935)
    "Organik" -> Color(0xFF43A047)
    "Kertas" -> Color(0xFF1E88E5)
    "Residu" -> Color(0xFF757575)
    else -> Color(0xFF9E9E9E)
}
