package com.example.luminasdgs.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.luminasdgs.data.dummy.QuestDummyData
import com.example.luminasdgs.data.model.Quest

class HomeViewModel : ViewModel() {
    val quests = mutableStateListOf<Quest>()

    var xp by mutableStateOf(0)
        private set
    var coins by mutableStateOf(0)
        private set
    var water by mutableStateOf(3)
        private set
    var fertilizer by mutableStateOf(2)
        private set

    var rewardMessage by mutableStateOf<String?>(null)
        private set

    init {
        quests.addAll(QuestDummyData.quests)
    }

    fun claimQuest(id: Int) {
        val index = quests.indexOfFirst { it.id == id }
        if (index == -1) return

        val quest = quests[index]
        if (quest.isCompleted) return

        quests[index] = quest.copy(isCompleted = true)
        // Update rewards and show feedback message for UI.
        xp += quest.xpReward
        coins += quest.coinReward
        water += quest.waterReward
        fertilizer += quest.fertilizerReward

        rewardMessage = "Reward didapat: +${quest.xpReward} XP, +${quest.coinReward} Coin, +${quest.waterReward} Air, +${quest.fertilizerReward} Pupuk"
    }

    fun clearRewardMessage() {
        rewardMessage = null
    }
}
