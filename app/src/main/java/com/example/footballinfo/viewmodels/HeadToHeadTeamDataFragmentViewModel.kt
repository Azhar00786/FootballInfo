package com.example.footballinfo.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.footballinfo.database.DatabaseGetter
import com.example.footballinfo.model.database_entities.FirstTeamResultsDataObject
import com.example.footballinfo.model.database_entities.SecondTeamResultsDataObject
import com.example.footballinfo.repositary.FootballInfoRepositary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class HeadToHeadTeamDataFragmentViewModel(databaseCall: DatabaseGetter) : ViewModel() {
    private var repoCall = FootballInfoRepositary.getInstance(databaseCall)

    init {
        Log.d("Head2HeadTeamDataFragVM", "ViewModel created")
    }

    fun getSelectedTeamName(): String {
        return repoCall.getSelectedTeamName(repoCall.getLocalStoredTeamKey().teamId.toString())
    }

    fun allTeamsName(): MutableList<String> {
        return repoCall.getAllTeamNames()
    }

    suspend fun cacheHeadToHeadData(teamOne: String, teamTwo: String) =
        withContext(Dispatchers.IO) {
            repoCall.cacheHeadToHeadDetails(teamOne, teamTwo)
        }

    suspend fun deleteHeadToHeadData() = withContext(Dispatchers.IO) {
        repoCall.deleteCacheHeadToHeadData()
    }

    fun getCountOfFirstTeamTableRecords(): Int {
        return repoCall.getCountOfFirstTeamInHToH()
    }

    fun getCountOfSecondTeamTableRecords(): Int {
        return repoCall.getCountOfSecondTeamInHToH()
    }

    fun getFirstTeamData(): LiveData<List<FirstTeamResultsDataObject>> {
        return repoCall.getHtoHFirstTeamData()
    }

    fun getSecondTeamData(): LiveData<List<SecondTeamResultsDataObject>> {
        return repoCall.getHtoHSecondTeamData()
    }
}

class HeadToHeadTeamDataFragmentVMFactory(private val databaseCall: DatabaseGetter) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HeadToHeadTeamDataFragmentViewModel::class.java)) {
            return HeadToHeadTeamDataFragmentViewModel(databaseCall) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}