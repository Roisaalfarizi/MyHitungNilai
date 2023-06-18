package org.d3if0125.assesment3_kalkulator_nilai.ui.penemu


import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0125.assesment3_kalkulator_nilai.model.PenemuHitung
import org.d3if0125.assesment3_kalkulator_nilai.network.ApiStatus
import org.d3if0125.assesment3_kalkulator_nilai.network.ServiceAPI
import org.d3if0125.assesment3_kalkulator_nilai.network.UpdateWorker

import java.util.concurrent.TimeUnit

class PenemuViewModel : ViewModel() {
    private val data = MutableLiveData<List<PenemuHitung>>()
    private val status = MutableLiveData<ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(ServiceAPI.penemuService.getPenemu())
                status.postValue(ApiStatus.SUCCES)
            } catch (e: Exception) {
                Log.d("PenemuViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }

    fun getData(): LiveData<List<PenemuHitung>> = data

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            UpdateWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    fun getStatus(): LiveData<ApiStatus> = status
}