package com.example.footballinfo.model.database_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class BothTeamResultDataObject(
    val matchHometeamId: String,
    val matchAwayteamName: String,
    @PrimaryKey
    val matchId: String,
    val leagueName: String,
    val matchHometeamHalftimeScore: String,
    val matchTime: String,
    val matchStatus: String,
    val matchAwayteamHalftimeScore: String,
    val matchDate: String,
    val matchLive: String,
    val matchHometeamScore: String,
    val matchAwayteamScore: String,
    val matchHometeamName: String,
    val countryName: String,
    val matchAwayteamId: String,
    val countryId: String,
    val leagueId: String
)