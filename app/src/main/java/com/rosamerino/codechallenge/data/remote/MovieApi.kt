package com.rosamerino.codechallenge.data.remote

import android.util.Log
import com.rosamerino.codechallenge.data.model.MovieDetail
import com.rosamerino.codechallenge.data.model.MovieDetailApiModel
import com.rosamerino.codechallenge.data.model.MovieListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class MovieApi
    @Inject
    constructor(
        private val client: HttpClient,
    ) {
        suspend fun fetchMovies(page: Int): MovieListResponse {
            try {
                val response: MovieListResponse =
                    client
                        .get("discover/movie") {
                            parameter("include_adult", false)
                            parameter("include_video", false)
                            parameter("language", "en-US")
                            parameter("page", page)
                            parameter("sort_by", "primary_release_date.desc")
                        }.body()
                return response
            } catch (e: Exception) {
                Log.e("MovieApi", "Error while fetching movies: ${e.message}", e)
                throw e
            }
        }

        suspend fun searchMovies(
            query: String,
            page: Int,
        ): MovieListResponse {
            try {
                val response: MovieListResponse =
                    client
                        .get("search/movie") {
                            parameter("query", query)
                            parameter("include_adult", false)
                            parameter("include_video", false)
                            parameter("language", "en-US")
                            parameter("page", page)
                            parameter("sort_by", "primary_release_date.desc")
                        }.body()
                return response
            } catch (e: Exception) {
                Log.e("MovieApi", "Error while searching movies: ${e.message}", e)
                throw e
            }
        }

        suspend fun fetchMovieDetails(movieId: Int): MovieDetail {
            val response: MovieDetailApiModel = client.get("movie/$movieId").body()
            return response.toMovieDetail()
        }
    }
