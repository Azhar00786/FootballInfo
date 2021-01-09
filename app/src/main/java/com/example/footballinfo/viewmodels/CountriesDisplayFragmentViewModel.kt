package com.example.footballinfo.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.footballinfo.database.DatabaseGetter
import com.example.footballinfo.model.database_entities.CountryDataObject
import com.example.footballinfo.repositary.FootballInfoRepositary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.lang.IllegalArgumentException

class CountriesDisplayFragmentViewModel(databaseCall: DatabaseGetter) : ViewModel() {
    private val repoCall = FootballInfoRepositary.getInstance(databaseCall)

    init {
        Log.d("CountriesDisplayFragVM", "ViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("CountriesDisplayFragVM", "ViewModel destroyed")
    }

    fun countryDataProcess(): LiveData<List<CountryDataObject>> {
        return repoCall.getCacheCountryData()
    }

    suspend fun cacheCountryData() = withContext(Dispatchers.IO) {
        repoCall.cacheCountryData()
    }

    fun deleteCacheCountryData() {
        repoCall.deleteCacheCountryData()
    }

    suspend fun deleteCompetitionsFragmentCacheData() = withContext(Dispatchers.IO) {
        repoCall.deleteCacheCompetitionsData()
    }
}

class CountriesDisplayFragmantVMFactory(private val databaseCall: DatabaseGetter) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountriesDisplayFragmentViewModel::class.java)) {
            return CountriesDisplayFragmentViewModel(databaseCall) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}