package com.example.footballinfo.model.network_entities

import com.example.footballinfo.model.database_entities.StandingsDataObject
import com.squareup.moshi.Json

data class GetStandingsDataRoot(
    @Json(name = "away_league_W")
    val awayLeagueW: String = "",
    @Json(name = "overall_league_D")
    val overallLeagueD: String = "",
    @Json(name = "home_league_PTS")
    val homeLeaguePTS: String = "",
    @Json(name = "overall_league_payed")
    val overallLeaguePayed: String = "",
    @Json(name = "overall_league_L")
    val overallLeagueL: String = "",
    @Json(name = "team_id")
    val teamId: String = "",
    @Json(name = "team_badge")
    val teamBadge: String = "",
    @Json(name = "home_league_GF")
    val homeLeagueGF: String = "",
    @Json(name = "home_league_position")
    val homeLeaguePosition: String = "",
    @Json(name = "away_league_L")
    val awayLeagueL: String = "",
    @Json(name = "away_league_payed")
    val awayLeaguePayed: String = "",
    @Json(name = "home_league_GA")
    val homeLeagueGA: String = "",
    @Json(name = "country_name")
    val countryName: String = "",
    @Json(name = "overall_promotion")
    val overallPromotion: String = "",
    @Json(name = "overall_league_GA")
    val overallLeagueGA: String = "",
    @Json(name = "overall_league_position")
    val overallLeaguePosition: String = "",
    @Json(name = "home_league_W")
    val homeLeagueW: String = "",
    @Json(name = "overall_league_GF")
    val overallLeagueGF: String = "",
    @Json(name = "away_league_D")
    val awayLeagueD: String = "",
    @Json(name = "overall_league_W")
    val overallLeagueW: String = "",
    @Json(name = "home_league_payed")
    val homeLeaguePayed: String = "",
    @Json(name = "home_league_L")
    val homeLeagueL: String = "",
    @Json(name = "league_round")
    val leagueRound: String = "",
    @Json(name = "away_promotion")
    val awayPromotion: String = "",
    @Json(name = "home_promotion")
    val homePromotion: String = "",
    @Json(name = "league_name")
    val leagueName: String = "",
    @Json(name = "home_league_D")
    val homeLeagueD: String = "",
    @Json(name = "team_name")
    val teamName: String = "",
    @Json(name = "overall_league_PTS")
    val overallLeaguePTS: String = "",
    @Json(name = "away_league_GF")
    val awayLeagueGF: String = "",
    @Json(name = "away_league_GA")
    val awayLeagueGA: String = "",
    @Json(name = "away_league_position")
    val awayLeaguePosition: String = "",
    @Json(name = "away_league_PTS")
    val awayLeaguePTS: String = "",
    @Json(name = "league_id")
    val leagueId: String = ""
)

fun List<GetStandingsDataRoot>.asTeamStandingDatabaseModel(): List<StandingsDataObject> {
    return map {
        StandingsDataObject(
            teamBadge = it.teamBadge,
            teamName = it.teamName,
            overallLeagueD = it.overallLeagueD,
            overallLeagueGA = it.overallLeagueGA,
            overallLeagueGF = it.overallLeagueGF,
            overallLeagueL = it.overallLeagueL,
            overallLeaguePayed = it.overallLeaguePayed,
            overallLeaguePosition = it.overallLeaguePosition,
            overallLeaguePTS = it.overallLeaguePTS,
            overallLeagueW = it.overallLeagueW,
            teamId = it.teamId,
            expandable = false
        )
    }
}
