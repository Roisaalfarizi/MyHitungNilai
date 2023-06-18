package org.d3if0125.assesment3_kalkulator_nilai.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nilai")
data class NilaiEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var praktikum: Float,
    var assessment1: Float,
    var assessment2: Float,
    var assessment3: Float
)
