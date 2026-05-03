package com.example.luminasdgs.data.dummy

import com.example.luminasdgs.data.model.Quest

object QuestDummyData {
    val quests = listOf(
        Quest(
            id = 1,
            title = "Bawa Botol Minum Sendiri",
            description = "Kurangi penggunaan plastik sekali pakai.",
            sdgTag = "SDG 12",
            xpReward = 10,
            coinReward = 5,
            waterReward = 1,
            fertilizerReward = 0
        ),
        Quest(
            id = 2,
            title = "Matikan Lampu yang Tidak Digunakan",
            description = "Hemat energi di rumah atau sekolah.",
            sdgTag = "SDG 7",
            xpReward = 10,
            coinReward = 5,
            waterReward = 0,
            fertilizerReward = 1
        ),
        Quest(
            id = 3,
            title = "Pilah Sampah Organik dan Anorganik",
            description = "Mulai kebiasaan pilah sampah harian.",
            sdgTag = "SDG 11",
            xpReward = 15,
            coinReward = 8,
            waterReward = 1,
            fertilizerReward = 0
        ),
        Quest(
            id = 4,
            title = "Kurangi Plastik Sekali Pakai",
            description = "Bawa tas belanja sendiri.",
            sdgTag = "SDG 12",
            xpReward = 15,
            coinReward = 8,
            waterReward = 0,
            fertilizerReward = 1
        ),
        Quest(
            id = 5,
            title = "Gunakan Transportasi Ramah Lingkungan",
            description = "Berjalan kaki, bersepeda, atau naik transportasi umum.",
            sdgTag = "SDG 13",
            xpReward = 20,
            coinReward = 10,
            waterReward = 1,
            fertilizerReward = 0
        ),
        Quest(
            id = 6,
            title = "Hemat Air Saat Mandi",
            description = "Gunakan air secukupnya.",
            sdgTag = "SDG 6",
            xpReward = 10,
            coinReward = 5,
            waterReward = 1,
            fertilizerReward = 0
        ),
        Quest(
            id = 7,
            title = "Membantu Teman Belajar",
            description = "Bagikan ilmu dan waktu untuk sesama.",
            sdgTag = "SDG 4",
            xpReward = 15,
            coinReward = 7,
            waterReward = 0,
            fertilizerReward = 1
        ),
        Quest(
            id = 8,
            title = "Menanam atau Merawat Tanaman",
            description = "Rawat tanaman di rumah atau sekolah.",
            sdgTag = "SDG 15",
            xpReward = 20,
            coinReward = 10,
            waterReward = 0,
            fertilizerReward = 1
        )
    )
}
