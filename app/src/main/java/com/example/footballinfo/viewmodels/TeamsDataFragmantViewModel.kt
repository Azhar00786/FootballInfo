package com.example.footballinfo.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.footballinfo.database.DatabaseGetter
import com.example.footballinfo.model.database_entities.TeamsDataObject
import com.example.footballinfo.repositary.FootballInfoRepositary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class TeamsDataFragmantViewModel(databaseCall: DatabaseGetter) : ViewModel() {
    private var repoCall = FootballInfoRepositary.getInstance(databaseCall)

    init {
        Log.d("TeamsDataFragmantVM", "ViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("TeamsDataFragmantVM", "ViewModel destroyed")
    }

    suspend fun cacheTeamsData(leagueId: String) = withContext(Dispatchers.IO) {
        repoCall.cacheTeamData(leagueId)
    }

    fun teamsDataProcess(): LiveData<List<TeamsDataObject>> {
        return repoCall.getCacheTeamsData()
    }

    fun cacheTeamId(teamId: Int) {
        val rowCount = repoCall.getCountOfRowsSingleTeamDataHolderObjects()
        if (rowCount == 0) {
            repoCall.cacheSingleTeamDataHolderObjectData(teamId)
        } else {
            repoCall.deleteSingleTeamResultsDataObject()
            repoCall.cacheSingleTeamDataHolderObjectData(teamId)
        }
    }

    suspend fun cacheStandingsData(leagueId: String) = withContext(Dispatchers.IO) {
        repoCall.cacheTeamStandingsData(leagueId)
    }

    class TeamsDataFragmantVMFactory(private val databaseCall: DatabaseGetter) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TeamsDataFragmantViewModel::class.java)) {
                return TeamsDataFragmantViewModel(databaseCall) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}