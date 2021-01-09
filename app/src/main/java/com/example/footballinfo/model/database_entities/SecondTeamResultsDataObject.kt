package com.example.footballinfo.model.database_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SecondTeamResultsDataObject(
    val matchAwayteamName: String,
    @PrimaryKey
    val matchId: String,
    val leagueName: String,
    val matchTime: String,
    val matchDate: String,
    val matchHometeamScore: String,
    val matchAwayteamScore: String,
    val matchHometeamName: String,
    val countryName: String,
    val countryId: String,
    val leagueId: String
)