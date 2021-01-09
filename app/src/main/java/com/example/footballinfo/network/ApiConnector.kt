package com.example.footballinfo.network

import com.example.footballinfo.model.network_entities.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://apiv2.apifootball.com/"

private const val API_KEY = "Put your API key here"

private val moshiObj = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface FootballApiServices {
    @GET(".")
    fun getCountries(@Query("action") actionType: NetworkActions): Deferred<List<GetCountriesRoot>>

    @GET(".")
    fun getLeague(
        @Query("action") actionType: NetworkActions,
        @Query("country_id") countryId: String
    ): Deferred<List<GetCompetitionsRoot>>

    @GET(".")
    fun getTeams(
        @Query("action") actionType: NetworkActions,
        @Query("league_id") leagueId: String
    ): Deferred<List<GetTeamRoot>>

    @GET(".")
    fun getPlayerDetail(
        @Query("action") actionType: NetworkActions,
        @Query("player_name") playerName: String
    ): Deferred<List<GetSinglePlayerDetailRoot>>

    @GET(".")
    fun getStandingsData(
        @Query("action") actionType: NetworkActions,
        @Query("league_id") leagueId: String
    ): Deferred<List<GetStandingsDataRoot>>

    @GET(".")
    fun getHeadToHeadData(
        @Query("action") actionType: NetworkActions,
        @Query("firstTeam") firstTeam: String,
        @Query("secondTeam") secondTeam: String
    ): Deferred<GetHeadToHeadDataRoot>
}


object FootallApi {
    operator fun invoke(): FootballApiServices {
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("APIkey", API_KEY)
                .build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshiObj))
            .build()
            .create(FootballApiServices::class.java)
    }
}