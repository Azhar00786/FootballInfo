package com.example.footballinfo.model.network_entities

import com.example.footballinfo.model.database_entities.CountryDataObject
import com.squareup.moshi.Json

data class GetCountriesRoot(
    @Json(name = "country_name")
    val countryName: String = "",
    @Json(name = "country_id")
    val countryId: String = "",
    @Json(name = "country_logo")
    val countryLogo: String = ""
)

fun List<GetCountriesRoot>.asCountryDatabaseModel(): List<CountryDataObject> {
    return map {
        CountryDataObject(
            countryName = it.countryName,
            countryId = it.countryId,
            countryLogo = it.countryLogo
        )
    }
}