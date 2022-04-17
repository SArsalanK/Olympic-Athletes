package com.assessment.olympicathletes.api

import com.assessment.olympicathletes.model.Athlete
import com.assessment.olympicathletes.model.Game
import com.assessment.olympicathletes.model.Repository
import com.assessment.olympicathletes.model.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface APIService {

    @GET(ApiSettings.GAMES)
    fun getGames(): Observable<List<Game>>

    @GET(ApiSettings.ATHLETES_BY_GAME_ID)
    fun getAthletesByGameId(
        @Path(ApiSettings.GAME_ID) gameId: String,
    ): Observable<List<Athlete>>

    @GET(ApiSettings.RESULTS_BY_ATHLETE_ID)
    fun getResultsByAthleteId(
        @Path(ApiSettings.ATHLETE_ID) athleteId: String
    ): Observable<List<Result>>

}