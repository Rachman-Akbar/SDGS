package com.example.luminasdgs.data.dummy

import com.example.luminasdgs.R
import com.example.luminasdgs.data.model.TrashItem
import com.example.luminasdgs.utils.Constants

object TrashDummyData {
    val items = listOf(
        TrashItem("Botol plastik", Constants.BIN_YELLOW, "Plastik", R.drawable.ic_launcher_foreground),
        TrashItem("Kantong plastik", Constants.BIN_YELLOW, "Plastik", R.drawable.ic_launcher_foreground),
        TrashItem("Gelas plastik", Constants.BIN_YELLOW, "Plastik", R.drawable.ic_launcher_foreground),
        TrashItem("Baterai", Constants.BIN_RED, "B3", R.drawable.ic_launcher_foreground),
        TrashItem("Lampu rusak", Constants.BIN_RED, "B3", R.drawable.ic_launcher_foreground),
        TrashItem("Kaleng cat", Constants.BIN_RED, "B3", R.drawable.ic_launcher_foreground),
        TrashItem("Kulit pisang", Constants.BIN_GREEN, "Organik", R.drawable.ic_launcher_foreground),
        TrashItem("Sisa sayur", Constants.BIN_GREEN, "Organik", R.drawable.ic_launcher_foreground),
        TrashItem("Daun kering", Constants.BIN_GREEN, "Organik", R.drawable.ic_launcher_foreground),
        TrashItem("Koran bekas", Constants.BIN_BLUE, "Kertas", R.drawable.ic_launcher_foreground),
        TrashItem("Kardus", Constants.BIN_BLUE, "Kertas", R.drawable.ic_launcher_foreground),
        TrashItem("Buku rusak", Constants.BIN_BLUE, "Kertas", R.drawable.ic_launcher_foreground),
        TrashItem("Popok sekali pakai", Constants.BIN_GRAY, "Residu", R.drawable.ic_launcher_foreground),
        TrashItem("Puntung rokok", Constants.BIN_GRAY, "Residu", R.drawable.ic_launcher_foreground),
        TrashItem("Masker sekali pakai", Constants.BIN_GRAY, "Residu", R.drawable.ic_launcher_foreground)
    )
}
