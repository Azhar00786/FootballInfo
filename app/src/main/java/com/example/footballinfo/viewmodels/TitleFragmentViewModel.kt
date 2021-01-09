package com.example.footballinfo.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.footballinfo.database.DatabaseGetter
import com.example.footballinfo.repositary.FootballInfoRepositary
import java.lang.IllegalArgumentException

class TitleFragmentViewModel(databaseCall: DatabaseGetter) : ViewModel() {
    private val repoCall = FootballInfoRepositary.getInstance(databaseCall)

    init {
        Log.d("TitleFragmentVM", "TitleFragmentViewModel running")
    }

    fun deleteCachedData() {
        repoCall.deleteCacheCountryData()
    }
}

class TitleFragmentVMFactory(private val databaseCall: DatabaseGetter) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TitleFragmentViewModel::class.java)) {
            return TitleFragmentViewModel(databaseCall) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}