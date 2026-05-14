package com.example.luminasdgs.data.dummy

import com.example.luminasdgs.R
import com.example.luminasdgs.data.model.TrashItem
import com.example.luminasdgs.utils.Constants

object TrashDummyData {
    val items = listOf(
        TrashItem("Botol plastik", Constants.BIN_YELLOW, "Plastik", R.drawable.botolplastik),
        TrashItem("Kantong plastik", Constants.BIN_YELLOW, "Plastik", R.drawable.kresek),
        TrashItem("Gelas plastik", Constants.BIN_YELLOW, "Plastik", R.drawable.gelasplastik),
        TrashItem("Baterai", Constants.BIN_RED, "B3", R.drawable.baterai),
        TrashItem("Lampu rusak", Constants.BIN_RED, "B3", R.drawable.lampurusak),
        TrashItem("Kaleng cat", Constants.BIN_RED, "B3", R.drawable.kalengcat),
        TrashItem("Kulit pisang", Constants.BIN_GREEN, "Organik", R.drawable.kulitpisang),
        TrashItem("Sisa sayur", Constants.BIN_GREEN, "Organik", R.drawable.sayur),
        TrashItem("Daun kering", Constants.BIN_GREEN, "Organik", R.drawable.daunkering),
        TrashItem("Koran bekas", Constants.BIN_BLUE, "Kertas", R.drawable.koran),
        TrashItem("Kardus", Constants.BIN_BLUE, "Kertas", R.drawable.kardus),
        TrashItem("Buku rusak", Constants.BIN_BLUE, "Kertas", R.drawable.buku),
        TrashItem("Popok sekali pakai", Constants.BIN_GRAY, "Residu", R.drawable.popok),
        TrashItem("Puntung rokok", Constants.BIN_GRAY, "Residu", R.drawable.puntungrokok),
        TrashItem("Masker sekali pakai", Constants.BIN_GRAY, "Residu", R.drawable.masker)
    )
}
