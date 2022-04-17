package com.assessment.olympicathletes.model

import java.io.Serializable

data class Game(
    val game_id: String,
    val city: String,
    val year: String,
    var athletes: MutableList<Athlete>?
) : Serializable
