package com.example.luminasdgs.ui.screens.game

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.luminasdgs.navigation.Screen
import com.example.luminasdgs.ui.screens.game.components.GameBackground

@Composable
fun GameScreen(navController: NavController) {
    val games = listOf(
        GameCardItem(
            title = "SDG Quiz Battle",
            tag = "EDUCATION",
            reward = "+50 HK",
            tagColor = Color(0xFF0288D1),
            tagBackground = Color(0xFFE3F2FD),
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuA2tdHsHwtElTvGXCsqm8OS7I1qEkXXx9Ha5cJ3JRG0grBJQeBX2I1YVGIwuIDZJzh_zQuOpK8ynOBeSv2hojFPp0CuXel_7TBH8vSt1VMTpdgoat8y2h-7OMMp8K9_pXDBty0IBIzjUfq1MOgwGoE_QxcOiIafE5B03mQz_wuTPQoEzv_eEofolpMItCPCFzQ_RoUWc5a8FMPu0u6YZEIm0DMBz2A-RkdfJ8gQkOcIjp1c0RE-b_ysYcNcsatie2EulkIIBQ1BGA",
            onClick = { navController.navigate(Screen.Quiz.route) }
        ),
        GameCardItem(
            title = "Sortir Sampah",
            tag = "WASTE MGMT",
            reward = "+30 HK",
            tagColor = Color(0xFFEF6C00),
            tagBackground = Color(0xFFFFF3E0),
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAZFiJX7tPbL4VylQVnRdGt24rzQQju02FSNPXxANRZB37aOItZ_JbLUfUOcyDV6PLqxt70xbHW2er0dLQnjvPGX72I_CZGDFgzEw5OC0R7C6pfjyfwJZkfD0IKzaD_Tq_pg1YV9j8IqEpZgDFQPhzn1NF4cJW7gypwgNs8fefpdOSoSEqfvNmUWKSrH-QeF9oXL-4-O-DOSbtcWMWjomc6v4ff2NlSTrrrkZAhrl-WxZGPP4b_CzceXRY--iJTrVhhflGZ4OHq6w",
            onClick = { navController.navigate(Screen.TrashSort.route) }
        ),
        // GameCardItem(
        //     title = "Clean The River",
        //     tag = "WATER CARE",
        //     reward = "+75 HK",
        //     tagColor = Color(0xFF1565C0),
        //     tagBackground = Color(0xFFE3F2FD),
        //     imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAbIaI9dqIVYkptACU9eFq7BrYMGBcpzHVGkVhGRnGyTmj5zVcV-MYGywQF39o12SCP6loj7eVQwzqkukoZg7oVASH8IpfC72rzRqjrQrBGXsPJimG6V2kVB5GqqWb9Ce1pwt49BfLf9RPmAaR1E1MrLLhrqjAnJjmszBGSdiErWtVkl-l6Ln-ZmDWzjEBuh0z4W3pCHE6_hbzWbMCdVyoxs1KQW2-n-w7MX32RpYjcF23g9OMs-t2UefQ5ssogaLZN56deAuikvQ",
        //     onClick = { navController.navigate(Screen.CleanRiver.route) }
        // ),
        GameCardItem(
            title = "Myth or Fact",
            tag = "ECO FACTS",
            reward = "+35 HK",
            tagColor = Color(0xFF2E7D32),
            tagBackground = Color(0xFFE8F5E9),
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDvn4s6pTC1noMopmtFzMn15ek6cTTp5MUITbhb-6NZolZumR-iRKxyLQNZDHHWFQs4vsBEF0jW6Ln1K_Fnq9BjhvnJhgeCFceYjuMbigajCFF46HPe1lZV9aofWSiPpP2KyRc7wTC4Mfd8sTeHRU8AirfnbErfB64-xOPJ3TzjrbyZFyH623S_PLFNuE48BecD4GhZdduPGhf23Mr1aFVDoYy6U87LQeZ06UbIlmXNIknwCAG8suzfr-qj5VlBoMVabrOLb37Qvw",
            onClick = { navController.navigate(Screen.MythFact.route) }
        ),
        GameCardItem(
            title = "SDG Match",
            tag = "PUZZLE",
            reward = "+40 HK",
            tagColor = Color(0xFF6A1B9A),
            tagBackground = Color(0xFFF3E5F5),
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAue-XbL4OPlbmTj4S1WUJdDqfEVsqx8ij6D4ZO8RIK1OnQ4VJCAeEfvBLOcw0CLczkP7hwbSnk_qwyo3KxFXqlX8Dr3mGUlKK7NGVEWPattCR1A8F1AQPM2v5jBVriQ0k8i9K3fclFDGT61is6ej3tJgzBGcyULn0-jLgX-yQjH-HwzO1nxQ33delQfmuZr2_6cR0rrTFnjzXmIC1pOCHT-fdF6AfKNX-FIady5wnO-25rIRbqrsEgmMcyhPCGMa46sSolTrZkSA",
            onClick = { navController.navigate(Screen.MatchCard.route) }
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        GameBackground(modifier = Modifier.fillMaxSize(), alpha = 0.06f)
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            item {
                GamesTopBar()
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Arcade Hub",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Play for the planet and earn Hero Koins!",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            items(games) { game ->
                GameCard(game = game)
            }
        }
    }
}

@Composable
private fun GamesTopBar() {
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
private fun GameCard(game: GameCardItem) {
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
                    model = game.imageUrl,
                    contentDescription = game.title,
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
                        text = game.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = game.tagBackground
                    ) {
                        Text(
                            text = game.tag,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = game.tagColor
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
                            text = game.reward,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6E5100)
                        )
                    }
                }
            }

            Button(
                onClick = game.onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(999.dp)
            ) {
                Text(text = "Play Now")
                Spacer(modifier = Modifier.size(6.dp))
                Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null)
            }
        }
    }
}

private data class GameCardItem(
    val title: String,
    val tag: String,
    val reward: String,
    val tagColor: Color,
    val tagBackground: Color,
    val imageUrl: String,
    val onClick: () -> Unit
)
