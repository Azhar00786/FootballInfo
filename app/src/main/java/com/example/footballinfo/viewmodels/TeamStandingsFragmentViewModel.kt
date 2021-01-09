package com.example.footballinfo.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.PrimaryKey
import com.example.footballinfo.database.DatabaseGetter
import com.example.footballinfo.model.database_entities.StandingsDataObject
import com.example.footballinfo.repositary.FootballInfoRepositary
import java.lang.IllegalArgumentException

class TeamStandingsFragmentViewModel(databaseCall: DatabaseGetter) : ViewModel() {
    private var repoCall = FootballInfoRepositary.getInstance(databaseCall)

    init {
        Log.d("TeamStandingsFragVM", "ViewModel created")
    }

    fun getTeamStandingsData(): LiveData<List<StandingsDataObject>> {
        return repoCall.getTeamStandingData()
    }

    fun getStoredTeamKey(): Int {
        return repoCall.getLocalStoredTeamKey().teamId
    }

    fun getSelectedTeamName(): String {
        return repoCall.getSelectedTeamName(repoCall.getLocalStoredTeamKey().teamId.toString())
    }

    fun getSelectedTeamPosition(): String {
        return repoCall.getSelectedTeamPosition(repoCall.getLocalStoredTeamKey().teamId.toString())
    }

    class TeamStandingsFragmentVMFactory(private val databaseCall: DatabaseGetter) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TeamStandingsFragmentViewModel::class.java)) {
                return TeamStandingsFragmentViewModel(databaseCall) as T
            }
            throw  IllegalArgumentException("Unknown ViewModel class")
        }
    }
}