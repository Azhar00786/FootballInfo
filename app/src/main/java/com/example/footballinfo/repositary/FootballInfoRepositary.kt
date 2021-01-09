package com.example.footballinfo.repositary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.footballinfo.database.DatabaseGetter
import com.example.footballinfo.model.database_entities.*
import com.example.footballinfo.network.FootallApi
import com.example.footballinfo.network.NetworkActions
import com.example.footballinfo.model.network_entities.*
import kotlinx.coroutines.runBlocking

class FootballInfoRepositary(private val databaseCall: DatabaseGetter) {
    private val apiCaller = FootallApi.invoke()

    /*
    /   Methods for caching data coming from the API
     */


    fun cacheCountryData() = runBlocking {
        Log.d("FootballInfoRepo", "cacheCountryData running")
        databaseCall.footballInfoDao().cacheAllCountryData(
            apiCaller.getCountries(NetworkActions.get_countries).await().asCountryDatabaseModel()
        )
    }

    fun cacheCompetitionsData(countryId: String) = runBlocking {
        Log.d("FootballInfoRepo", "cacheCompetitionData running")
        databaseCall.footballInfoDao().cacheAllCompetitionData(
            apiCaller.getLeague(NetworkActions.get_leagues, countryId).await()
                .asCompetitionDatabaseModel()
        )
    }

    fun cacheTeamData(leagueId: String) = runBlocking {
        Log.d("FootballInfoRepo", "cacheTeamData running")
        val teamNetworkData: List<GetTeamRoot> =
            apiCaller.getTeams(NetworkActions.get_teams, leagueId).await()
        databaseCall.footballInfoDao().cacheAllTeamData(teamNetworkData.asTeamDatabaseModel())
        for (i in teamNetworkData) {
            databaseCall.footballInfoDao()
                .cacheCoachesData(i.coaches!!.asCoachInfoDatabaseModel(i.teamKey))
            databaseCall.footballInfoDao()
                .cachePlayerData(i.players!!.asPlayerInfoDatabaseModel(i.teamKey))
        }
    }

    fun cacheTeamStandingsData(leagueId: String) = runBlocking {
        Log.d("FootballInfoRepo", "cacheTeamStandingData running")
        databaseCall.footballInfoDao().cacheTeamStandingsData(
            apiCaller.getStandingsData(
                NetworkActions.get_standings,
                leagueId
            ).await().asTeamStandingDatabaseModel()
        )
    }

    fun cacheHeadToHeadDetails(firstTeam: String, secondTeam: String) = runBlocking {
        Log.d("FootballInfoRepo", "cacheHeadToHeadDetails running")
        val teamNetworkData: GetHeadToHeadDataRoot =
            apiCaller.getHeadToHeadData(NetworkActions.get_H2H, firstTeam, secondTeam).await()
        databaseCall.footballInfoDao()
            .cacheFirstTeamData(teamNetworkData.firstTeamLastResults!!.asFirsTeamLastResultDatabaseModel())
        databaseCall.footballInfoDao()
            .cacheSecondTeamData(teamNetworkData.secondTeamLastResults!!.asSecondTeamLastResultDatabaseModel())
    }

    fun cacheSingleTeamDataHolderObjectData(teamId: Int) = runBlocking {
        databaseCall.footballInfoDao()
            .cacheSingleTeamDataHolderObject(SingleTeamDataHolderObject(teamId))
    }

    /*
    /    Methods for accessing Local database
    */


    fun getCacheCountryData(): LiveData<List<CountryDataObject>> = runBlocking {
        Log.d("FootballInfoRepo", "getCacheCountryData running")
        val tempCountryDataLd: MutableLiveData<List<CountryDataObject>> = MutableLiveData()
        tempCountryDataLd.value = databaseCall.footballInfoDao().getCountryData()
        return@runBlocking tempCountryDataLd
    }

    fun getCacheCompetitionsData(): LiveData<List<CompetitionsDataObject>> = runBlocking {
        Log.d("FootballInfoRepo", "getCacheCompetitionsData running")
        val tempCompetitionsDataLd: MutableLiveData<List<CompetitionsDataObject>> =
            MutableLiveData()
        tempCompetitionsDataLd.value = databaseCall.footballInfoDao().getAllCompetitionsData()
        return@runBlocking tempCompetitionsDataLd
    }

    fun getCacheTeamsData(): LiveData<List<TeamsDataObject>> = runBlocking {
        Log.d("FootballInfoRepo", "getCacheTeamsData() running")
        val tempTeamsDataLd: MutableLiveData<List<TeamsDataObject>> = MutableLiveData()
        tempTeamsDataLd.value = databaseCall.footballInfoDao().getAllTeamsData()
        return@runBlocking tempTeamsDataLd
    }

    fun getCoachCacheData(teamId: Int): LiveData<CoachesItemDataObject> = runBlocking {
        Log.d("FootballInfoRepo", "getCoachCacheData() is running")
        val tempCoachData: MutableLiveData<CoachesItemDataObject> = MutableLiveData()
        tempCoachData.value = databaseCall.footballInfoDao().getAllCoachesData(teamId)
        return@runBlocking tempCoachData
    }

    fun getPlayersCacheData(teamKey: Int): LiveData<List<PlayersItemDataObject>> = runBlocking {
        Log.d("FootballInfoRepo", "getPlayersCacheData() is running")
        val tempPlayerData: MutableLiveData<List<PlayersItemDataObject>> = MutableLiveData()
        tempPlayerData.value = databaseCall.footballInfoDao().getAllPlayersData(teamKey)
        return@runBlocking tempPlayerData
    }

    fun getTeamStandingData(): LiveData<List<StandingsDataObject>> = runBlocking {
        Log.d("FootballInfoRepo", "getTeamStandingData() is running")
        val tempTeamStandingData: MutableLiveData<List<StandingsDataObject>> = MutableLiveData()
        tempTeamStandingData.value = databaseCall.footballInfoDao().getAllCacheStandingsData()
        return@runBlocking tempTeamStandingData
    }

    fun getHtoHFirstTeamData(): LiveData<List<FirstTeamResultsDataObject>> = runBlocking {
        Log.d("FootballInfoRepo", "getHtoHFirstTeamData() is running")
        val tempFirstTeamData: MutableLiveData<List<FirstTeamResultsDataObject>> = MutableLiveData()
        tempFirstTeamData.value = databaseCall.footballInfoDao().getFirstTeamData()
        return@runBlocking tempFirstTeamData
    }

    fun getHtoHSecondTeamData(): LiveData<List<SecondTeamResultsDataObject>> = runBlocking {
        Log.d("FootballInfoRepo", "getHtoHFirstTeamData() is running")
        val tempSecondTeamData: MutableLiveData<List<SecondTeamResultsDataObject>> =
            MutableLiveData()
        tempSecondTeamData.value = databaseCall.footballInfoDao().getSecondTeamData()
        return@runBlocking tempSecondTeamData
    }

    fun getCountOfRowsSingleTeamDataHolderObjects(): Int = runBlocking {
        return@runBlocking databaseCall.footballInfoDao().getCountOfSingleTeamDataHolder()
    }

    fun getLocalStoredTeamKey(): SingleTeamDataHolderObject = runBlocking {
        return@runBlocking databaseCall.footballInfoDao().getTeamKey()
    }

    fun getTeamName(teamId: Int): String = runBlocking {
        return@runBlocking databaseCall.footballInfoDao().getSelectedTeamName(teamId)
    }

    fun getSelectedTeamPosition(teamId: String): String = runBlocking {
        return@runBlocking databaseCall.footballInfoDao().getTeamPosition(teamId)
    }

    fun getSelectedTeamName(teamId: String): String = runBlocking {
        return@runBlocking databaseCall.footballInfoDao().getTeamName(teamId)
    }

    fun getAllTeamNames(): MutableList<String> = runBlocking {
        return@runBlocking databaseCall.footballInfoDao().getAllTeamName()
    }

    fun getCountOfFirstTeamInHToH(): Int = runBlocking {
        return@runBlocking databaseCall.footballInfoDao().getRecordCountOfFirstTeam()
    }


    fun getCountOfSecondTeamInHToH(): Int = runBlocking {
        return@runBlocking databaseCall.footballInfoDao().getRecordCountOfSecondTeam()
    }


    /*
    /   Methods for deleting local table data
    */


    fun deleteCacheCountryData() = runBlocking {
        databaseCall.footballInfoDao().deleteAllCachedCountryData()
        Log.d("FootballInfoRepo", "deleteCacheCountryData running")
    }

    fun deleteCacheCompetitionsData() = runBlocking {
        Log.d("FootballInfoRepo", "deleteCacheCompetitionsData running")
        databaseCall.footballInfoDao().deleteAllCachedCompetitionsData()
    }

    fun deleteCacheTeamsData() = runBlocking {
        Log.d("FootballInfoRepo", "deleteCacheTeamsData() running")
        databaseCall.footballInfoDao().deleteAllCacheTeamsData()
        databaseCall.footballInfoDao().deleteAllCoachesData()
        databaseCall.footballInfoDao().deleteAllPlayersData()
    }

    fun deleteCacheTeamStandingsData() = runBlocking {
        Log.d("FootballInfoRepo", "deleteCacheTeamStandingsData() running")
        databaseCall.footballInfoDao().deleteCacheTeamStandingData()
    }

    fun deleteSingleTeamResultsDataObject() = runBlocking {
        databaseCall.footballInfoDao().deleteSingleTeamDataHolderObject()
    }

    fun deleteCacheHeadToHeadData() = runBlocking {
        databaseCall.footballInfoDao().deleteFirstTeamData()
        databaseCall.footballInfoDao().deleteSecondTeamData()
    }

    companion object {
        @Volatile
        private var instance: FootballInfoRepositary? = null

        fun getInstance(databaseCall: DatabaseGetter) =
            instance
                ?: synchronized(this) {
                    instance
                        ?: FootballInfoRepositary(
                            databaseCall
                        )
                            .also { instance = it }
                }
    }
}