package com.example.luminasdgs.data.dummy

import com.example.luminasdgs.data.model.SdgGoal
import com.example.luminasdgs.data.model.SdgStatement

object SdgDummyData {
    val goals = listOf(
        SdgGoal(1, "Tanpa Kemiskinan"),
        SdgGoal(2, "Tanpa Kelaparan"),
        SdgGoal(3, "Kehidupan Sehat dan Sejahtera"),
        SdgGoal(4, "Pendidikan Berkualitas"),
        SdgGoal(5, "Kesetaraan Gender"),
        SdgGoal(6, "Air Bersih dan Sanitasi Layak"),
        SdgGoal(7, "Energi Bersih dan Terjangkau"),
        SdgGoal(8, "Pekerjaan Layak dan Pertumbuhan Ekonomi"),
        SdgGoal(9, "Industri, Inovasi, dan Infrastruktur"),
        SdgGoal(10, "Berkurangnya Kesenjangan"),
        SdgGoal(11, "Kota dan Permukiman Berkelanjutan"),
        SdgGoal(12, "Konsumsi dan Produksi Bertanggung Jawab"),
        SdgGoal(13, "Penanganan Perubahan Iklim"),
        SdgGoal(14, "Ekosistem Lautan"),
        SdgGoal(15, "Ekosistem Daratan"),
        SdgGoal(16, "Perdamaian, Keadilan, dan Kelembagaan Tangguh"),
        SdgGoal(17, "Kemitraan untuk Mencapai Tujuan")
    )

    val statements: List<SdgStatement> = buildList {
        var id = 1
        sdgPromptSeeds.forEach { seed ->
            add(SdgStatement(id++, "Pernyataan: ${seed.statement}", seed.goalId))
            add(SdgStatement(id++, "Masalah: ${seed.problem}", seed.goalId))
            add(SdgStatement(id++, "Solusi: ${seed.solution}", seed.goalId))
        }
    }
}

private data class SdgPromptSeed(
    val goalId: Int,
    val statement: String,
    val problem: String,
    val solution: String
)

private val sdgPromptSeeds = listOf(
    SdgPromptSeed(
        goalId = 1,
        statement = "Program bantuan tunai untuk keluarga miskin.",
        problem = "Akses pendapatan stabil masih rendah di banyak wilayah.",
        solution = "Perluas program perlindungan sosial dan pelatihan kerja."
    ),
    SdgPromptSeed(
        goalId = 2,
        statement = "Inisiatif pangan lokal untuk mengurangi kelaparan.",
        problem = "Distribusi pangan belum merata dan harga sering naik.",
        solution = "Perkuat lumbung pangan komunitas dan rantai pasok lokal."
    ),
    SdgPromptSeed(
        goalId = 3,
        statement = "Kampanye imunisasi dan layanan kesehatan dasar.",
        problem = "Fasilitas kesehatan di daerah terpencil terbatas.",
        solution = "Tambah klinik mobile dan tenaga kesehatan desa."
    ),
    SdgPromptSeed(
        goalId = 4,
        statement = "Pembangunan sekolah dan pelatihan guru.",
        problem = "Kualitas belajar tidak merata di sekolah terpencil.",
        solution = "Sediakan pelatihan rutin dan akses materi digital."
    ),
    SdgPromptSeed(
        goalId = 5,
        statement = "Kebijakan upah setara untuk perempuan.",
        problem = "Kesenjangan upah dan peluang karier masih tinggi.",
        solution = "Terapkan audit upah dan kebijakan anti diskriminasi."
    ),
    SdgPromptSeed(
        goalId = 6,
        statement = "Penyediaan air bersih dan toilet layak.",
        problem = "Sumber air bersih belum tersedia di semua desa.",
        solution = "Bangun infrastruktur air dan sanitasi berbasis komunitas."
    ),
    SdgPromptSeed(
        goalId = 7,
        statement = "Pengembangan energi surya untuk desa.",
        problem = "Akses listrik stabil masih terbatas di wilayah terpencil.",
        solution = "Perluas energi terbarukan dan mikrogrid desa."
    ),
    SdgPromptSeed(
        goalId = 8,
        statement = "Pelatihan keterampilan kerja bagi pemuda.",
        problem = "Pengangguran muda masih tinggi di beberapa daerah.",
        solution = "Perkuat pelatihan vokasi dan kemitraan industri lokal."
    ),
    SdgPromptSeed(
        goalId = 9,
        statement = "Pembangunan jaringan transportasi dan inovasi teknologi.",
        problem = "Infrastruktur dan riset belum merata antar daerah.",
        solution = "Investasi infrastruktur dan dukung pusat inovasi regional."
    ),
    SdgPromptSeed(
        goalId = 10,
        statement = "Program inklusi untuk kelompok rentan.",
        problem = "Kelompok rentan sering terpinggirkan dari layanan publik.",
        solution = "Perluas akses pendidikan, kesehatan, dan layanan keuangan."
    ),
    SdgPromptSeed(
        goalId = 11,
        statement = "Transportasi publik yang ramah lingkungan.",
        problem = "Kemacetan dan polusi udara meningkat di kota besar.",
        solution = "Kembangkan transportasi massal dan ruang hijau kota."
    ),
    SdgPromptSeed(
        goalId = 12,
        statement = "Pengurangan sampah plastik sekali pakai.",
        problem = "Sampah sulit terurai masih mendominasi TPA.",
        solution = "Dorong pemilahan sampah dan penggunaan ulang."
    ),
    SdgPromptSeed(
        goalId = 13,
        statement = "Aksi pengurangan emisi karbon.",
        problem = "Emisi dari transportasi dan industri terus naik.",
        solution = "Percepat transisi energi bersih dan efisiensi energi."
    ),
    SdgPromptSeed(
        goalId = 14,
        statement = "Perlindungan terumbu karang dan laut.",
        problem = "Pencemaran laut dan penangkapan berlebih merusak ekosistem.",
        solution = "Terapkan kawasan konservasi dan pengawasan penangkapan."
    ),
    SdgPromptSeed(
        goalId = 15,
        statement = "Rehabilitasi hutan dan konservasi satwa.",
        problem = "Deforestasi dan kebakaran hutan mengurangi keanekaragaman hayati.",
        solution = "Perkuat restorasi hutan dan patroli kawasan lindung."
    ),
    SdgPromptSeed(
        goalId = 16,
        statement = "Transparansi hukum dan akses keadilan.",
        problem = "Kepercayaan publik menurun karena proses hukum lambat.",
        solution = "Perbaiki layanan hukum dan tingkatkan transparansi proses."
    ),
    SdgPromptSeed(
        goalId = 17,
        statement = "Kerja sama global untuk pendanaan SDGs.",
        problem = "Kesenjangan pendanaan menghambat program pembangunan.",
        solution = "Perkuat kemitraan dan skema pendanaan lintas sektor."
    )
)
