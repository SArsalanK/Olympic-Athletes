package com.assessment.olympicathletes.model

import java.io.Serializable

data class Athlete(
    val athlete_id: String,
    val name: String,
    val surname: String,
    val dateOfBirth: String,
    val bio: String,
    val weight: Int,
    val height: Int,
    val photo_id: Int,
    var global_score: Int,
    var results: MutableList<Result>? = mutableListOf()
) : Serializable