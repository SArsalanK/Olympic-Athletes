package com.assessment.olympicathletes.model

import java.io.Serializable

data class Result(
    val city: String,
    val year: Int,
    val gold: Int,
    val silver: Int,
    val bronze: Int,
): Serializable