package com.example.footballinfo.model.network_entities

import com.squareup.moshi.Json

data class GetSinglePlayerDetailRoot(
    @Json(name = "player_key")
    val playerKey: Long = 0,
    @Json(name = "player_match_played")
    val playerMatchPlayed: String = "",
    @Json(name = "player_age")
    val playerAge: String = "",
    @Json(name = "player_red_cards")
    val playerRedCards: String = "",
    @Json(name = "player_number")
    val playerNumber: String = "",
    @Json(name = "player_country")
    val playerCountry: String = "",
    @Json(name = "player_goals")
    val playerGoals: String = "",
    @Json(name = "player_name")
    val playerName: String = "",
    @Json(name = "player_yellow_cards")
    val playerYellowCards: String = "",
    @Json(name = "player_type")
    val playerType: String = "",
    @Json(name = "team_key")
    val teamKey: String = "",
    @Json(name = "team_name")
    val teamName: String = ""
)