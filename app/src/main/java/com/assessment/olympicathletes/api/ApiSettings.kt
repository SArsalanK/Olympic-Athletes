package com.assessment.olympicathletes.api

object ApiSettings {
    const val GAME_ID = "game_id"
    const val ATHLETE_ID = "athlete_id"

    const val GAMES = "/games"
    const val ATHLETES_BY_GAME_ID = "/games/{$GAME_ID}/athletes"
    const val RESULTS_BY_ATHLETE_ID = "/athletes/{$ATHLETE_ID}/results"
}