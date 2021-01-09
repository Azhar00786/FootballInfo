package com.example.footballinfo.model.database_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SinglePlayerDataObject(
    @PrimaryKey
    val playerKey: Long,
    val playerMatchPlayed: String,
    val playerAge: String,
    val playerRedCards: String,
    val playerNumber: String,
    val playerCountry: String,
    val playerGoals: String,
    val playerName: String,
    val playerYellowCards: String,
    val playerType: String,
    val teamKey: String,
    val teamName: String
)