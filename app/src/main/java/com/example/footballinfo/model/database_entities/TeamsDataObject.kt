package com.example.footballinfo.model.database_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeamsDataObject(
    @PrimaryKey
    val teamKey: Int,
    val teamName: String,
    val teamBadge: String
)