package com.example.footballinfo.model.network_entities

import com.example.footballinfo.model.database_entities.BothTeamResultDataObject
import com.example.footballinfo.model.database_entities.FirstTeamResultsDataObject
import com.example.footballinfo.model.database_entities.SecondTeamResultsDataObject
import com.squareup.moshi.Json

data class GetHeadToHeadDataRoot(
    /*@Json(name = "firstTeam_VS_secondTeam")
    val firsteamVsLastTeam: List<BothTeamResultItem>?,*/
    @Json(name = "firstTeam_lastResults")
    val firstTeamLastResults: List<FirstTeamLastResultsItem>?,
    @Json(name = "secondTeam_lastResults")
    val secondTeamLastResults: List<SecondTeamLastResultsItem>?
)

fun List<SecondTeamLastResultsItem>.asSecondTeamLastResultDatabaseModel(): List<SecondTeamResultsDataObject> {
    return map {
        SecondTeamResultsDataObject(
            leagueName = it.leagueName,
            leagueId = it.leagueId,
            countryName = it.countryName,
            countryId = it.countryId,
            matchAwayteamName = it.matchAwayteamName,
            matchAwayteamScore = it.matchAwayteamScore,
            matchDate = it.matchDate,
            matchHometeamName = it.matchHometeamName,
            matchHometeamScore = it.matchHometeamScore,
            matchId = it.matchId,
            matchTime = it.matchTime
        )
    }
}


fun List<FirstTeamLastResultsItem>.asFirsTeamLastResultDatabaseModel(): List<FirstTeamResultsDataObject> {
    return map {
        FirstTeamResultsDataObject(
            leagueName = it.leagueName,
            leagueId = it.leagueId,
            countryName = it.countryName,
            countryId = it.countryId,
            matchAwayteamName = it.matchAwayteamName,
            matchAwayteamScore = it.matchAwayteamScore,
            matchDate = it.matchDate,
            matchHometeamName = it.matchHometeamName,
            matchHometeamScore = it.matchHometeamScore,
            matchId = it.matchId,
            matchTime = it.matchTime
        )
    }
}

fun List<BothTeamResultItem>.asBothTeamResultDatabaseModel(): List<BothTeamResultDataObject> {
    return map {
        BothTeamResultDataObject(
            matchAwayteamHalftimeScore = it.matchAwayteamHalftimeScore,
            leagueName = it.leagueName,
            leagueId = it.leagueId,
            countryName = it.countryName,
            countryId = it.countryId,
            matchAwayteamId = it.matchAwayteamId,
            matchAwayteamName = it.matchAwayteamName,
            matchAwayteamScore = it.matchAwayteamScore,
            matchDate = it.matchDate,
            matchHometeamHalftimeScore = it.matchHometeamHalftimeScore,
            matchHometeamId = it.matchHometeamId,
            matchHometeamName = it.matchHometeamName,
            matchHometeamScore = it.matchHometeamScore,
            matchId = it.matchId,
            matchLive = it.matchLive,
            matchStatus = it.matchStatus,
            matchTime = it.matchTime
        )
    }
}