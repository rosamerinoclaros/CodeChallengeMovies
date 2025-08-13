package com.rosamerino.codechallenge.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<MovieApiModel>,
    @SerialName("total_pages")
    val totalPages: Int,
)
