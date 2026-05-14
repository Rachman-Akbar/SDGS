package com.example.luminasdgs.data.dummy

import com.example.luminasdgs.data.model.MythFactStatement

object MythFactDummyData {
    private const val DEFAULT_IMAGE =
        "https://lh3.googleusercontent.com/aida-public/AB6AXuCz_4TXD7jRsuq4r5m1U4h2yq7s_GG_xrTQ0Z7k8kL-0V1c8B9it8e9rjWfVh0tL4xU1mQAEf1B7D0gK_5oKrgyHulWzTqWvVx9NfJm1k8Y9"

    val items = listOf(
        MythFactStatement(
            id = 1,
            statement = "Plastik bisa membutuhkan hingga 500 tahun untuk terurai di laut.",
            isFact = true,
            imageUrl = DEFAULT_IMAGE
        ),
        MythFactStatement(
            id = 2,
            statement = "Semua plastik dapat terurai dalam waktu satu tahun.",
            isFact = false,
            imageUrl = DEFAULT_IMAGE
        ),
        MythFactStatement(
            id = 3,
            statement = "Kaca bisa didaur ulang berulang kali tanpa kehilangan kualitas.",
            isFact = true,
            imageUrl = DEFAULT_IMAGE
        ),
        MythFactStatement(
            id = 4,
            statement = "Semua program daur ulang menerima botol plastik.",
            isFact = false,
            imageUrl = DEFAULT_IMAGE
        ),
        MythFactStatement(
            id = 5,
            statement = "Sampah elektronik termasuk jenis sampah yang paling cepat meningkat.",
            isFact = true,
            imageUrl = DEFAULT_IMAGE
        ),
        MythFactStatement(
            id = 6,
            statement = "Kulit pisang membutuhkan ratusan tahun untuk terurai.",
            isFact = false,
            imageUrl = DEFAULT_IMAGE
        ),
        MythFactStatement(
            id = 7,
            statement = "Mendaur ulang aluminium menghemat jauh lebih banyak energi dibanding membuat yang baru.",
            isFact = true,
            imageUrl = DEFAULT_IMAGE
        )
    )
}


