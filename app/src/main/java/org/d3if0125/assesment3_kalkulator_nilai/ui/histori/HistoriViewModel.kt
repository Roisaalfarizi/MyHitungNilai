package org.d3if0125.assesment3_kalkulator_nilai.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if0125.assesment3_kalkulator_nilai.db.NilaiDao

class HistoriViewModel(private val db: NilaiDao) : ViewModel() {
    val data = db.getLastNilai()

    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }
}