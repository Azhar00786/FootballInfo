package com.example.footballinfo.model.database_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoachesItemDataObject(
    val teamKey: Int,
    val coachAge: String,
    val coachName: String,
    val coachCountry: String
) {
    @PrimaryKey(autoGenerate = true)
    var coachId: Int = 0
}