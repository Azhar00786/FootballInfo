package com.example.footballinfo.model.network_entities

import com.example.footballinfo.model.database_entities.CoachesItemDataObject
import com.example.footballinfo.model.database_entities.PlayersItemDataObject
import com.example.footballinfo.model.database_entities.TeamsDataObject
import com.squareup.moshi.Json

data class GetTeamRoot(
    @Json(name = "coaches")
    val coaches: List<CoachesItem>?,
    @Json(name = "players")
    val players: List<PlayersItem>?,
    @Json(name = "team_key")
    val teamKey: Int = 0,
    @Json(name = "team_name")
    val teamName: String = "",
    @Json(name = "team_badge")
    val teamBadge: String = ""
)


fun List<GetTeamRoot>.asTeamDatabaseModel(): List<TeamsDataObject> {
    return map {
        TeamsDataObject(
            teamKey = it.teamKey,
            teamName = it.teamName,
            teamBadge = it.teamBadge
        )
    }
}

fun List<CoachesItem>.asCoachInfoDatabaseModel(teamKey: Int): List<CoachesItemDataObject> {
    return map {
        CoachesItemDataObject(
            teamKey = teamKey,
            coachAge = it.coachAge,
            coachCountry = it.coachCountry,
            coachName = it.coachName
        )
    }
}

fun List<PlayersItem>.asPlayerInfoDatabaseModel(teamKey: Int): List<PlayersItemDataObject> {
    return map {
        PlayersItemDataObject(
            teamKey = teamKey,
            playerAge = it.playerAge,
            playerCountry = it.playerCountry,
            playerGoals = it.playerGoals,
            playerKey = it.playerKey,
            playerMatchPlayed = it.playerMatchPlayed,
            playerName = it.playerName,
            playerNumber = it.playerNumber,
            playerRedCards = it.playerRedCards,
            playerType = it.playerType,
            playerYellowCards = it.playerYellowCards,
            isExpandable = false
        )
    }
}