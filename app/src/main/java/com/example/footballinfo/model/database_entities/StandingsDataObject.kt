package com.example.footballinfo.model.database_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StandingsDataObject(
    val overallLeagueD: String,
    val overallLeaguePayed: String,
    val overallLeagueL: String,
    @PrimaryKey
    val teamId: String,
    val teamBadge: String,
    val overallLeagueGA: String,
    val overallLeaguePosition: String,
    val overallLeagueGF: String,
    val overallLeagueW: String,
    val teamName: String,
    val overallLeaguePTS: String,
    var expandable: Boolean
)