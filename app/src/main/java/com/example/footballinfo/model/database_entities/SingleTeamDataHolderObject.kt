package com.example.footballinfo.model.database_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SingleTeamDataHolderObject(
    @PrimaryKey
    val teamId: Int
)