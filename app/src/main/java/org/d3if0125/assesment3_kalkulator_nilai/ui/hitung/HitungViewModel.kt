package org.d3if0125.assesment3_kalkulator_nilai.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if0125.assesment3_kalkulator_nilai.db.NilaiDao
import org.d3if0125.assesment3_kalkulator_nilai.db.NilaiEntity
import org.d3if0125.assesment3_kalkulator_nilai.model.HasilNilai
import org.d3if0125.assesment3_kalkulator_nilai.model.KategoriNilai
import org.d3if0125.assesment3_kalkulator_nilai.model.hitungnilai

class HitungViewModel(private val db: NilaiDao) : ViewModel() {

    private val hasilNilai = MutableLiveData<HasilNilai?>()
    private val navigasi = MutableLiveData<KategoriNilai?>()

//    val data = db.getLastNilai()

    fun hitungNilai(
        praktikum: Float,
        assessment1: Float,
        assessment2: Float,
        assessment3: Float
    ) {
        val dataNilai = NilaiEntity(
            praktikum = praktikum,
            assessment1 = assessment1,
            assessment2 = assessment2,
            assessment3 = assessment3
        )
        hasilNilai.value = dataNilai.hitungnilai()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert (dataNilai)
            }
        }
    }

    fun getHasilNilai(): LiveData<HasilNilai?> = hasilNilai

    fun mulaiNavigasi() {
        navigasi.value = hasilNilai.value?.kategori
    }

    fun selesaiNavigasi() {
        navigasi.value = null
    }

    fun getNavigasi(): LiveData<KategoriNilai?> = navigasi
}