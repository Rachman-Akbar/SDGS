package com.example.luminasdgs.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.luminasdgs.navigation.Screen

@Composable
fun OnboardingLearnScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hero Quest",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(280.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.TopStart)
                    .background(Color(0xFFB2DFDB).copy(alpha = 0.5f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .align(Alignment.BottomEnd)
                    .background(Color(0xFF81C784).copy(alpha = 0.3f), CircleShape)
            )
            AsyncImage(
                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuCkbz0q7iaCc17ouWc9idXmVpsQ2LHOZcAhWnCD4ncRraBFWsUdUIf29O1ozOsSoqiAXqeMW5omKB2x_paQWKJhF9MSN-uuXZVopmKi-N-V3z0ZQsIVrXRvupSaIGkG8cDuc904HpOlXjA7m6kdhcYLLjpG0NtU8jSDmmqaCkRzPfyNpeBaqMqczJ_drJI0cIOA_9g4S_wR4oJx-B5EgN2uMD_YFGdtqQFi-x81y0tTjyZ2Oh-rutL0sNWLcK7sjNKZ9Bttv9Zf5Q",
                contentDescription = "Hero character",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Your Mission Starts Here.",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Learn about the 17 SDGs through interactive challenges.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 6.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        PageIndicator(activeIndex = 0)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate(Screen.OnboardingRewards.route) },
            shape = RoundedCornerShape(999.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Next")
            Spacer(modifier = Modifier.size(6.dp))
            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null)
        }
        Text(
            text = "Skip",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Icon(
            imageVector = Icons.Filled.Eco,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            modifier = Modifier.size(56.dp)
        )
    }
}

@Composable
fun OnboardingRewardsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hero Quest",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
        ) {
            AsyncImage(
                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBnEVp-Q4UCGgiCkRHUhpSly9iyheJ3fhtKFA5NaZs4qBrt_DS4SnTmWg25prE2iso-bmosnZkCYB5jlLCPfWhaWbgVJW5o1tkzLdGlcVx8rHISCWq-OBJmoxbdB1mjvY3dS97-AifObmf39iOsNH7GqRVzEFJujYmG39wZOHN8OyN_ASfK7QpW2gq5ckEp2gV8t5vOPMbjWTmtjnfB5iGODQs6gypkwgN0qrEQzXTComcqU4sztZgUUxRabvKDHzeRF57Y9w4keQ",
                contentDescription = "Rewards",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White.copy(alpha = 0.9f),
                shadowElevation = 6.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFE082)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Filled.Eco, contentDescription = null, tint = Color(0xFF6E5100))
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Column {
                        Text(text = "REWARD", fontSize = 10.sp, color = Color(0xFF6E5100))
                        Text(text = "+25 HK", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White.copy(alpha = 0.9f),
                shadowElevation = 6.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Filled.Eco, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(text = "Plastic Sorting", fontWeight = FontWeight.SemiBold)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Take Action, Get Rewarded.",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Turn real-world eco habits into XP and Hero Koins.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 6.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        PageIndicator(activeIndex = 1)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate(Screen.OnboardingImpact.route) },
            shape = RoundedCornerShape(999.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Next")
            Spacer(modifier = Modifier.size(6.dp))
            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null)
        }
        Text(
            text = "Skip Intro",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
fun OnboardingImpactScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFFF2FFF4), Color(0xFFE6F7FF))
                )
            )
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hero Quest",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(280.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White.copy(alpha = 0.85f)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuAlbSGHPb3if7XbiQ-ry0UakVId9kiJac-CCcnne8IY3EziYjs6Q2jP7Bb6pjl6U34MyPaaFSdVYXauM68yQM199zS2Bzv3YqtcGHPsz5ZFdRz9OoPbpx28Nr_DrAuvtYkzK8i3CjTA7xRuVsERaMbKlvoOUQ1i4P2jA-dsdAZTjX71bw4rGjY9BBvABqrhppfuiIHQ-2CH98jNc_dYxffY4qUkSKkD-w4sVsXQkmPqWyDkrLNpM1u6tjOD01LtHDO0WUVlUwkJxg",
                contentDescription = "Impact tree",
                modifier = Modifier.size(220.dp),
                contentScale = ContentScale.Fit
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ImpactBar(height = 32.dp, fill = 0.3f, color = Color(0xFF8BC34A))
                ImpactBar(height = 48.dp, fill = 0.6f, color = Color(0xFF4DB6AC))
                ImpactBar(height = 64.dp, fill = 1f, color = Color(0xFF2E7D32))
                ImpactBar(height = 40.dp, fill = 0.75f, color = Color(0xFFFFC107))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Grow Your Virtual Forest",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Every real-life action helps your virtual tree thrive.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 6.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        PageIndicator(activeIndex = 2)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.OnboardingLearn.route) { inclusive = true }
                }
            },
            shape = RoundedCornerShape(999.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Get Started")
            Spacer(modifier = Modifier.size(6.dp))
            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null)
        }
    }
}

@Composable
private fun PageIndicator(activeIndex: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(3) { index ->
            val width = if (index == activeIndex) 28.dp else 8.dp
            val color = if (index == activeIndex) MaterialTheme.colorScheme.primary else Color(0xFFE5E2E0)
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(width)
                    .clip(RoundedCornerShape(999.dp))
                    .background(color)
            )
        }
    }
}

@Composable
private fun ImpactBar(height: androidx.compose.ui.unit.Dp, fill: Float, color: Color) {
    Box(
        modifier = Modifier
            .width(24.dp)
            .height(height)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF1F1F1))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height * fill)
                .align(Alignment.BottomCenter)
                .background(color)
        )
    }
}
