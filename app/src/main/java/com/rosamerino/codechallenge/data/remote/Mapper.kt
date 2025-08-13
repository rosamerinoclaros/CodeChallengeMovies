package com.rosamerino.codechallenge.data.remote

import com.rosamerino.codechallenge.data.model.Movie
import com.rosamerino.codechallenge.data.model.MovieApiModel
import com.rosamerino.codechallenge.data.model.MovieDetail
import com.rosamerino.codechallenge.data.model.MovieDetailApiModel

fun MovieApiModel.toMovie(): Movie =
    Movie(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterPath,
        releaseDate = releaseDate,
    )

fun MovieDetailApiModel.toMovieDetail(): MovieDetail =
    MovieDetail(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterPath,
        releaseDate = releaseDate,
        genres = genres.map { it.name },
        runtime = runtime,
        status = status,
        tagline = tagline,
    )
