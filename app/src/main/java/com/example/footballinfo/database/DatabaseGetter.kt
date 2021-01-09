package com.example.footballinfo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.footballinfo.model.database_entities.*

@Database(
    entities = [CoachesItemDataObject::class, CompetitionsDataObject::class, CountryDataObject::class, FirstTeamResultsDataObject::class, PlayersItemDataObject::class, SecondTeamResultsDataObject::class, SinglePlayerDataObject::class, SingleTeamDataHolderObject::class, StandingsDataObject::class, TeamsDataObject::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseGetter : RoomDatabase() {
    abstract fun footballInfoDao(): FootballInfoDao

    companion object {
        @Volatile
        var INSTANCE: DatabaseGetter? = null
        fun getInstance(context: Context): DatabaseGetter {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, DatabaseGetter::class.java, "AppDB"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}