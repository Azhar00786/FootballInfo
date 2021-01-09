package com.example.footballinfo.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.footballinfo.model.database_entities.*

@Dao
interface FootballInfoDao {

    //For countries data object

    @Insert
    suspend fun cacheAllCountryData(countryDataObj: List<CountryDataObject>)

    @Query("select * from CountryDataObject")
    fun getAllCountriesData(): LiveData<List<CountryDataObject>>

    @Query("select * from CountryDataObject")
    suspend fun getCountryData(): List<CountryDataObject>


    @Delete
    suspend fun deleteCachedCountryData(countryDataObj: CountryDataObject)

    @Query("delete from CountryDataObject")
    suspend fun deleteAllCachedCountryData()

    //For competitions data object

    @Insert
    suspend fun cacheAllCompetitionData(competitionsDataObject: List<CompetitionsDataObject>)

    @Delete
    suspend fun deleteCachedCompetitionsData(competitionsDataObject: CompetitionsDataObject)

    @Query("select * from CompetitionsDataObject")
    suspend fun getAllCompetitionsData(): List<CompetitionsDataObject>

    @Query("delete from CompetitionsDataObject")
    suspend fun deleteAllCachedCompetitionsData()

    //For Team data object

    @Insert
    suspend fun cacheAllTeamData(teamDataObject: List<TeamsDataObject>)

    @Delete
    suspend fun deleteCacheTeamData(teamDataObject: TeamsDataObject)

    @Query("select teamName from TeamsDataObject where teamKey = :teamKey")
    suspend fun getSelectedTeamName(teamKey: Int): String

    @Query("delete from TeamsDataObject")
    suspend fun deleteAllCacheTeamsData()

    @Query("select * from TeamsDataObject")
    suspend fun getAllTeamsData(): List<TeamsDataObject>

    @Query("select teamName from TeamsDataObject")
    suspend fun getAllTeamName(): MutableList<String>


    //For CoachesItemDataObject

    @Query("select * from CoachesItemDataObject where teamKey = :teamKey")
    suspend fun getAllCoachesData(teamKey: Int): CoachesItemDataObject

    @Insert
    suspend fun cacheCoachesData(coachesItemDataObject: List<CoachesItemDataObject>)

    @Query("delete from CoachesItemDataObject")
    suspend fun deleteAllCoachesData()

    //For PlayersItemDataObject

    @Insert
    suspend fun cachePlayerData(playersItemDataObject: List<PlayersItemDataObject>)

    @Query("delete from PlayersItemDataObject")
    suspend fun deleteAllPlayersData()

    @Query("select * from PlayersItemDataObject")
    suspend fun getAllTeamsStoredData(): List<PlayersItemDataObject>

    @Query("select * from PlayersItemDataObject where teamKey = :teamKey")
    suspend fun getAllPlayersData(teamKey: Int): List<PlayersItemDataObject>

    //For SinglePlayerDataObject

    @Insert
    suspend fun cacheSinglePlayerData(singlePlayerDataObject: SinglePlayerDataObject)

    //For TeamStanding Data

    @Insert
    suspend fun cacheTeamStandingsData(standingsDataObject: List<StandingsDataObject>)

    @Query("delete from StandingsDataObject")
    suspend fun deleteCacheTeamStandingData()

    @Query("select * from StandingsDataObject")
    suspend fun getAllCacheStandingsData(): List<StandingsDataObject>

    @Query("select overallLeaguePosition from StandingsDataObject where teamId = :teamKey")
    suspend fun getTeamPosition(teamKey: String): String

    @Query("select teamName from StandingsDataObject where teamId = :teamKey")
    suspend fun getTeamName(teamKey: String): String


    //For HeadToHeadTeams

    @Insert
    suspend fun cacheFirstTeamData(firstTeamResultsDataObjects: List<FirstTeamResultsDataObject>)

    @Query("select * from FirstTeamResultsDataObject")
    suspend fun getFirstTeamData(): List<FirstTeamResultsDataObject>

    @Query("delete from FirstTeamResultsDataObject")
    suspend fun deleteFirstTeamData()

    @Query("select count(*) from FirstTeamResultsDataObject")
    suspend fun getRecordCountOfFirstTeam(): Int

    @Insert
    suspend fun cacheSecondTeamData(secondTeamResultsDataObjects: List<SecondTeamResultsDataObject>)

    @Query("select * from SecondTeamResultsDataObject")
    suspend fun getSecondTeamData(): List<SecondTeamResultsDataObject>

    @Query("delete from SecondTeamResultsDataObject")
    suspend fun deleteSecondTeamData()

    @Query("select count(*) from SecondTeamResultsDataObject")
    suspend fun getRecordCountOfSecondTeam(): Int


    //For SingleTeamDataHolderObjects

    @Query("select count(*) from SingleTeamDataHolderObject")
    suspend fun getCountOfSingleTeamDataHolder(): Int

    @Insert
    suspend fun cacheSingleTeamDataHolderObject(singleTeamDataHolderObject: SingleTeamDataHolderObject)

    @Query("delete from SingleTeamDataHolderObject")
    suspend fun deleteSingleTeamDataHolderObject()

    @Query("select * from SingleTeamDataHolderObject")
    suspend fun getTeamKey(): SingleTeamDataHolderObject
}