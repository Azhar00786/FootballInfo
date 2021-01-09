package com.example.footballinfo.model.database_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayersItemDataObject(
    val teamKey: Int,
    val playerKey: String,
    val playerMatchPlayed: String,
    val playerAge: String,
    val playerRedCards: String,
    val playerNumber: String,
    val playerCountry: String,
    val playerGoals: String,
    val playerName: String,
    val playerYellowCards: String,
    val playerType: String,
    var isExpandable: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var playerId: Int = 0
}