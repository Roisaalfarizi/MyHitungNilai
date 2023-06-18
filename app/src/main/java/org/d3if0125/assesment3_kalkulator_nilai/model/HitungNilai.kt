package org.d3if0125.assesment3_kalkulator_nilai.model

import org.d3if0125.assesment3_kalkulator_nilai.db.NilaiEntity

fun NilaiEntity.hitungnilai(): HasilNilai{
    val nilai =
        ((praktikum * 0.25) + (assessment1 * 0.2) + (assessment2 * 0.25) + (assessment3 * 0.3))
    val kategori =
        when {
            nilai >= 80 -> KategoriNilai.A
            nilai <= 59.99 -> KategoriNilai.C
            else -> KategoriNilai.B
        }
    return HasilNilai(nilai, kategori)

}