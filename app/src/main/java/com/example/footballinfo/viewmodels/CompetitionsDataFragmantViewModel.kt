package com.example.footballinfo.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.footballinfo.database.DatabaseGetter
import com.example.footballinfo.model.database_entities.CompetitionsDataObject
import com.example.footballinfo.repositary.FootballInfoRepositary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class CompetitionsDataFragmantViewModel(databaseCall: DatabaseGetter) : ViewModel() {
    private var repoCall = FootballInfoRepositary.getInstance(databaseCall)

    init {
        Log.d("CompetitionsDataFragVM", "ViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("CompetitionsDataFragVM", "ViewModel destroyed")
    }

    suspend fun cacheCompetitionsData(countryId: String) = withContext(Dispatchers.IO) {
        repoCall.cacheCompetitionsData(countryId)
    }

    fun deleteCompetitionsData() {
        repoCall.deleteCacheCompetitionsData()
    }

    fun competitionsDataProcess(): LiveData<List<CompetitionsDataObject>> {
        return repoCall.getCacheCompetitionsData()
    }

    suspend fun deleteTeamsCachedData() = withContext(Dispatchers.IO) {
        repoCall.deleteCacheTeamsData()
    }

    suspend fun deleteCacheTeamStandingData() = withContext(Dispatchers.IO) {
        repoCall.deleteCacheTeamStandingsData()
    }
}

class CompetitionsDataFragmantVMFactory(private val databaseCall: DatabaseGetter) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompetitionsDataFragmantViewModel::class.java)) {
            return CompetitionsDataFragmantViewModel(databaseCall) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}