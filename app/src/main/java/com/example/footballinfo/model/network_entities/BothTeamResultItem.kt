package com.example.footballinfo.model.network_entities

import com.squareup.moshi.Json

data class BothTeamResultItem(
    @Json(name = "match_hometeam_id")
    val matchHometeamId: String = "",
    @Json(name = "match_awayteam_name")
    val matchAwayteamName: String = "",
    @Json(name = "match_id")
    val matchId: String = "",
    @Json(name = "league_name")
    val leagueName: String = "",
    @Json(name = "match_hometeam_halftime_score")
    val matchHometeamHalftimeScore: String = "",
    @Json(name = "match_time")
    val matchTime: String = "",
    @Json(name = "match_status")
    val matchStatus: String = "",
    @Json(name = "match_awayteam_halftime_score")
    val matchAwayteamHalftimeScore: String = "",
    @Json(name = "match_date")
    val matchDate: String = "",
    @Json(name = "match_live")
    val matchLive: String = "",
    @Json(name = "match_hometeam_score")
    val matchHometeamScore: String = "",
    @Json(name = "match_awayteam_score")
    val matchAwayteamScore: String = "",
    @Json(name = "match_hometeam_name")
    val matchHometeamName: String = "",
    @Json(name = "country_name")
    val countryName: String = "",
    @Json(name = "match_awayteam_id")
    val matchAwayteamId: String = "",
    @Json(name = "country_id")
    val countryId: String = "",
    @Json(name = "league_id")
    val leagueId: String = ""
)