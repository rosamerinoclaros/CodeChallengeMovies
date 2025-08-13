package com.rosamerino.codechallenge.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val releaseDate: String,
    val genres: List<String>,
    val runtime: Int?,
    val status: String,
    val tagline: String?,
)
