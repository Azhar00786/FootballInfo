package com.example.footballinfo.model.network_entities

import com.squareup.moshi.Json

data class CoachesItem(
    /*@Json(name = "team_key")
    val teamKey:Int = 0,*/
    @Json(name = "coach_age")
    val coachAge: String = "",
    @Json(name = "coach_name")
    val coachName: String = "",
    @Json(name = "coach_country")
    val coachCountry: String = ""
)