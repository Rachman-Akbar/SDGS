package com.example.luminasdgs.viewmodel

import androidx.lifecycle.ViewModel
import com.example.luminasdgs.data.model.UserProfile

class ProfileViewModel : ViewModel() {
    val profile = UserProfile(
        name = "Pejuang SDGs",
        level = 5,
        xp = 320,
        coins = 120,
        completedQuest = 12,
        gamesPlayed = 8,
        treeLevel = 3
    )

    val badges = listOf(
        "Eco Starter",
        "Waste Hero",
        "SDG Learner",
        "Tree Keeper",
        "River Protector"
    )
}
