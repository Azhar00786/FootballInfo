package com.example.footballinfo.model.network_entities

import com.example.footballinfo.model.database_entities.CompetitionsDataObject
import com.squareup.moshi.Json

data class GetCompetitionsRoot(
    @Json(name = "league_name")
    val leagueName: String = "",
    @Json(name = "league_logo")
    val leagueLogo: String = "",
    @Json(name = "country_name")
    val countryName: String = "",
    @Json(name = "league_season")
    val leagueSeason: String = "",
    @Json(name = "country_id")
    val countryId: String = "",
    @Json(name = "league_id")
    val leagueId: String = "",
    @Json(name = "country_logo")
    val countryLogo: String = ""
)


fun List<GetCompetitionsRoot>.asCompetitionDatabaseModel(): List<CompetitionsDataObject> {
    return map {
        CompetitionsDataObject(
            leagueName = it.leagueName,
            leagueLogo = it.leagueLogo,
            countryName = it.countryName,
            leagueSeason = it.leagueSeason,
            countryId = it.countryId,
            countryLogo = it.countryLogo,
            leagueId = it.leagueId
        )
    }
}