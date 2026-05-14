package com.example.luminasdgs.ui.screens.profile.achievement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.luminasdgs.R
import com.example.luminasdgs.data.dummy.SdgDummyData
import com.example.luminasdgs.navigation.Screen
import com.example.luminasdgs.ui.components.SdgImageTile

@Composable
fun MatchCardAchievementScreen(navController: NavController) {
    val sdgs = SdgDummyData.goals.map { goal ->
        SdgCardItem(
            id = goal.id,
            title = goal.title,
            drawableRes = sdgDrawables[goal.id]
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 20.dp, bottom = 120.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            MatchCardAchievementTopBar(
                title = "Match Card SDGs",
                onBack = { navController.popBackStack() }
            )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = "Pilih SDG 1-17",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        items(sdgs) { item ->
            SdgCard(
                item = item,
                onOpen = {
                    navController.navigate(
                        Screen.AchievementMatchCardDetail.createRoute(item.id)
                    )
                }
            )
        }
    }
}

@Composable
fun MatchCardAchievementDetailScreen(
    navController: NavController,
    sdgId: Int
) {
    val tabs = listOf("Pernyataan", "Masalah", "Solusi")
    var selectedTab by remember { mutableIntStateOf(0) }

    val goal = SdgDummyData.goals.firstOrNull { it.id == sdgId }
    val relatedTexts = SdgDummyData.statements.filter { it.goalId == sdgId }

    val statement = relatedTexts
        .firstOrNull { it.text.startsWith("Pernyataan:") }
        ?.text
        ?: "Pernyataan: SDG $sdgId mendukung pembangunan berkelanjutan."

    val problem = relatedTexts
        .firstOrNull { it.text.startsWith("Masalah:") }
        ?.text
        ?: "Masalah utama terkait ${goal?.title ?: "SDG $sdgId"} masih belum merata."

    val solution = relatedTexts
        .firstOrNull { it.text.startsWith("Solusi:") }
        ?.text
        ?: "Solusi yang bisa dilakukan: dukung aksi lokal dan kolaborasi untuk ${goal?.title ?: "SDG $sdgId"}."

    val detail = SdgDetail(
        title = goal?.title ?: "SDG $sdgId",
        statement = statement,
        problem = problem,
        solution = solution
    )

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 20.dp, bottom = 120.dp)
    ) {
        item {
            MatchCardAchievementTopBar(
                title = detail.title,
                onBack = { navController.popBackStack() }
            )
        }

        item {
            SdgImageTile(
                drawableRes = sdgDrawables[sdgId],
                contentDescription = "SDG $sdgId",
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = 20.dp,
                elevation = 12.dp,
                onClick = {}
            )
        }

        item {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 13.sp,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }
        }

        item {
            val text = when (selectedTab) {
                0 -> detail.statement
                1 -> detail.problem
                else -> detail.solution
            }

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = text,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
private fun MatchCardAchievementTopBar(
    title: String,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Text(
            text = "←",
            modifier = Modifier
                .clickable { onBack() }
                .padding(end = 12.dp),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun SdgCard(
    item: SdgCardItem,
    onOpen: () -> Unit
) {
    SdgImageTile(
        drawableRes = item.drawableRes,
        contentDescription = "SDG ${item.id}",
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 18.dp,
        elevation = 10.dp,
        onClick = onOpen
    )
}

private data class SdgCardItem(
    val id: Int,
    val title: String,
    val drawableRes: Int?
)

private data class SdgDetail(
    val title: String,
    val statement: String,
    val problem: String,
    val solution: String
)

private val sdgDrawables = mapOf(
    1 to R.drawable.sdgs1,
    2 to R.drawable.sdgs2,
    3 to R.drawable.sdgs3,
    4 to R.drawable.sdgs4,
    5 to R.drawable.sdgs5,
    6 to R.drawable.sdgs6,
    7 to R.drawable.sdgs7,
    8 to R.drawable.sdgs8,
    9 to R.drawable.sdgs9,
    10 to R.drawable.sdgs10,
    11 to R.drawable.sdgs11,
    12 to R.drawable.sdgs12,
    13 to R.drawable.sdgs13,
    14 to R.drawable.sdgs14,
    15 to R.drawable.sdgs15,
    16 to R.drawable.sdgs16,
    17 to R.drawable.sdgs17
)