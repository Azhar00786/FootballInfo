package com.example.footballinfo.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.footballinfo.database.DatabaseGetter
import com.example.footballinfo.model.database_entities.CoachesItemDataObject
import com.example.footballinfo.model.database_entities.PlayersItemDataObject
import com.example.footballinfo.model.database_entities.SingleTeamDataHolderObject
import com.example.footballinfo.repositary.FootballInfoRepositary
import java.lang.IllegalArgumentException

class SingleTeamDetailFragmentViewModel(databaseCall: DatabaseGetter) : ViewModel() {
    private var repoCall = FootballInfoRepositary.getInstance(databaseCall)
    private lateinit var teamKey: SingleTeamDataHolderObject

    init {
        Log.d("SingleTeamDetailFragVM", "ViewModel created")
    }

    fun getSingleTeamData(): LiveData<List<PlayersItemDataObject>> {
        teamKey = repoCall.getLocalStoredTeamKey()
        return repoCall.getPlayersCacheData(teamKey.teamId)
    }

    fun getCoachData(): LiveData<CoachesItemDataObject> {
        return repoCall.getCoachCacheData(repoCall.getLocalStoredTeamKey().teamId)
    }

    fun getSelectedTeamName(): String {
        return repoCall.getTeamName(repoCall.getLocalStoredTeamKey().teamId)
    }


    class SingleTeamDetailFragmentVMFactory(private val databaseCall: DatabaseGetter) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SingleTeamDetailFragmentViewModel::class.java)) {
                return SingleTeamDetailFragmentViewModel(databaseCall) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}