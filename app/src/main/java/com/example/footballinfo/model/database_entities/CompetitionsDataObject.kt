package com.example.footballinfo.model.database_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompetitionsDataObject(
    val leagueName: String,
    val leagueLogo: String,
    val countryName: String,
    val leagueSeason: String,
    val countryId: String,
    @PrimaryKey
    val leagueId: String,
    val countryLogo: String
)