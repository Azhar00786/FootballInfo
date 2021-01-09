package com.example.footballinfo.model.database_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryDataObject(
    val countryName: String,
    @PrimaryKey
    val countryId: String,
    val countryLogo: String
)