package com.example.luminasdgs.ui.screens.profile.achievement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.luminasdgs.navigation.Screen

@Composable
fun AchievementHubScreen(navController: NavController) {
    val entries = listOf(
        AchievementEntry(
            title = "SDG Quiz Battle",
            tag = "EDUCATION",
            reward = "Lihat Progress",
            tagColor = Color(0xFF0288D1),
            tagBackground = Color(0xFFE3F2FD),
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuA2tdHsHwtElTvGXCsqm8OS7I1qEkXXx9Ha5cJ3JRG0grBJQeBX2I1YVGIwuIDZJzh_zQuOpK8ynOBeSv2hojFPp0CuXel_7TBH8vSt1VMTpdgoat8y2h-7OMMp8K9_pXDBty0IBIzjUfq1MOgwGoE_QxcOiIafE5B03mQz_wuTPQoEzv_eEofolpMItCPCFzQ_RoUWc5a8FMPu0u6YZEIm0DMBz2A-RkdfJ8gQkOcIjp1c0RE-b_ysYcNcsatie2EulkIIBQ1BGA",
            route = Screen.AchievementQuiz.route
        ),
        AchievementEntry(
            title = "Sortir Sampah",
            tag = "WASTE MGMT",
            reward = "Lihat Progress",
            tagColor = Color(0xFFEF6C00),
            tagBackground = Color(0xFFFFF3E0),
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAZFiJX7tPbL4VylQVnRdGt24rzQQju02FSNPXxANRZB37aOItZ_JbLUfUOcyDV6PLqxt70xbHW2er0dLQnjvPGX72I_CZGDFgzEw5OC0R7C6pfjyfwJZkfD0IKzaD_Tq_pg1YV9j8IqEpZgDFQPhzn1NF4cJW7gypwgNs8fefpdOSoSEqfvNmUWKSrH-QeF9oXL-4-O-DOSbtcWMWjomc6v4ff2NlSTrrrkZAhrl-WxZGPP4b_CzceXRY--iJTrVhhflGZ4OHq6w",
            route = Screen.AchievementTrashSort.route
        ),
        AchievementEntry(
            title = "Myth or Fact",
            tag = "ECO FACTS",
            reward = "Lihat Progress",
            tagColor = Color(0xFF2E7D32),
            tagBackground = Color(0xFFE8F5E9),
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDvn4s6pTC1noMopmtFzMn15ek6cTTp5MUITbhb-6NZolZumR-iRKxyLQNZDHHWFQs4vsBEF0jW6Ln1K_Fnq9BjhvnJhgeCFceYjuMbigajCFF46HPe1lZV9aofWSiPpP2KyRc7wTC4Mfd8sTeHRU8AirfnbErfB64-xOPJ3TzjrbyZFyH623S_PLFNuE48BecD4GhZdduPGhf23Mr1aFVDoYy6U87LQeZ06UbIlmXNIknwCAG8suzfr-qj5VlBoMVabrOLb37Qvw",
            route = Screen.AchievementMythFact.route
        ),
        AchievementEntry(
            title = "SDG Match",
            tag = "PUZZLE",
            reward = "Lihat Progress",
            tagColor = Color(0xFF6A1B9A),
            tagBackground = Color(0xFFF3E5F5),
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAue-XbL4OPlbmTj4S1WUJdDqfEVsqx8ij6D4ZO8RIK1OnQ4VJCAeEfvBLOcw0CLczkP7hwbSnk_qwyo3KxFXqlX8Dr3mGUlKK7NGVEWPattCR1A8F1AQPM2v5jBVriQ0k8i9K3fclFDGT61is6ej3tJgzBGcyULn0-jLgX-yQjH-HwzO1nxQ33delQfmuZr2_6cR0rrTFnjzXmIC1pOCHT-fdF6AfKNX-FIady5wnO-25rIRbqrsEgmMcyhPCGMa46sSolTrZkSA",
            route = Screen.AchievementMatchCard.route
        )
    )

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {
        item {
            AchievementHubTopBar(onBack = { navController.popBackStack() })
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Achievement Hub",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Lihat hasil permainan dan item yang terbuka.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }

        items(entries) { entry ->
            AchievementCard(entry = entry, onOpen = { navController.navigate(entry.route) })
        }
    }
}

@Composable
private fun AchievementHubTopBar(onBack: () -> Unit) {
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
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onBack() }
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    text = "Kembali",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun AchievementCard(entry: AchievementEntry, onOpen: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE9F5EE))
            ) {
                AsyncImage(
                    model = entry.imageUrl,
                    contentDescription = entry.title,
                    modifier = Modifier.fillMaxWidth().height(160.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = entry.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = entry.tagBackground
                    ) {
                        Text(
                            text = entry.tag,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = entry.tagColor
                        )
                    }
                }
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFFFE8B2)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.WorkspacePremium,
                            contentDescription = null,
                            tint = Color(0xFF6E5100),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = entry.reward,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6E5100)
                        )
                    }
                }
            }

            Button(
                onClick = onOpen,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(999.dp)
            ) {
                Text(text = "Lihat Achievement")
                Spacer(modifier = Modifier.size(6.dp))
                Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null)
            }
        }
    }
}

private data class AchievementEntry(
    val title: String,
    val tag: String,
    val reward: String,
    val tagColor: Color,
    val tagBackground: Color,
    val imageUrl: String,
    val route: String
)
