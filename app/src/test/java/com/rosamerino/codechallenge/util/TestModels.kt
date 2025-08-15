package com.rosamerino.codechallenge.util // Oder ein passender Package-Name für Test-Utilities

import com.rosamerino.codechallenge.data.model.GenreApiModel
import com.rosamerino.codechallenge.data.model.Movie
import com.rosamerino.codechallenge.data.model.MovieApiModel
import com.rosamerino.codechallenge.data.model.MovieDetail
import com.rosamerino.codechallenge.data.model.MovieDetailApiModel
import com.rosamerino.codechallenge.data.model.MovieListResponse
import com.rosamerino.codechallenge.data.model.ProductionCompanyApiModel
import com.rosamerino.codechallenge.data.model.ProductionCountryApiModel
import com.rosamerino.codechallenge.data.model.SpokenLanguageApiModel

object TestModels {
    fun createMovie() = Movie(1, "Test Movie", "desc", "url", "2024-01-01")

    fun createMovieDetail() = MovieDetail(1, "Test", "desc", "url", "2024-01-01", listOf("Action"), 120, "Released", "Tagline")

    fun createMovieListResponse(
        page: Int = 1,
        results: List<MovieApiModel> = listOf(createMovieApiModel()),
        totalPages: Int = 10,
    ) = MovieListResponse(
        page = page,
        results = results,
        totalPages = totalPages,
    )

    fun createGenreApiModel(
        id: Int = 1,
        name: String = "Action",
    ) = GenreApiModel(
        id = id,
        name = name,
    )

    fun createMovieApiModel(
        id: Int = 1,
        title: String = "Title",
        overview: String = "This is a test movie overview.",
        posterPath: String? = "/poster123.jpg",
        releaseDate: String = "2024-01-15",
        backdropPath: String? = "/backdrop456.jpg",
        genreIds: List<Int> = listOf(28, 12), // Beispiel: Action, Adventure
        originalLanguage: String = "en",
        originalTitle: String = "Original Test Movie Title",
        popularity: Double = 7.5,
        adult: Boolean = false,
        video: Boolean = false,
        voteAverage: Double = 8.2,
        voteCount: Int = 1500,
    ) = MovieApiModel(
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )

    fun createProductionCompanyApiModel(
        id: Int = 101,
        name: String = "Test Production Company",
    ) = ProductionCompanyApiModel(
        id = id,
        name = name,
    )

    fun createProductionCountryApiModel(
        iso31661: String = "US",
        name: String = "United States of America",
    ) = ProductionCountryApiModel(
        iso31661 = iso31661,
        name = name,
    )

    fun createSpokenLanguageApiModel(
        iso6391: String = "en",
        name: String = "English",
    ) = SpokenLanguageApiModel(
        iso6391 = iso6391,
        name = name,
    )

    fun createMovieDetailApiModel(
        id: Int = 1,
        title: String = "Detailed Test Movie",
        overview: String = "This is a very detailed overview of the test movie.",
        posterPath: String? = "/detail_poster789.jpg",
        releaseDate: String = "2024-02-20",
        genres: List<GenreApiModel> =
            listOf(
                createGenreApiModel(),
                createGenreApiModel(id = 2, name = "Sci-Fi"),
            ),
        adult: Boolean = false,
        backdropPath: String? = "/detail_backdrop101.jpg",
        budget: Int = 100000000,
        homepage: String = "https://testmovie.example.com",
        imdbId: String? = "tt1234567",
        originCountry: List<String> = listOf("US"),
        originalLanguage: String = "en",
        originalTitle: String = "Original Detailed Test Movie Title",
        popularity: Double = 9.2,
        productionCompanies: List<ProductionCompanyApiModel> =
            listOf(
                createProductionCompanyApiModel(),
            ),
        productionCountries: List<ProductionCountryApiModel> =
            listOf(
                createProductionCountryApiModel(),
            ),
        revenue: Int = 500000000,
        runtime: Int? = 120,
        spokenLanguages: List<SpokenLanguageApiModel> =
            listOf(
                createSpokenLanguageApiModel(),
                createSpokenLanguageApiModel(iso6391 = "es", name = "Spanish"),
            ),
        status: String = "Released",
        tagline: String? = "An epic test adventure!",
        video: Boolean = false,
        voteAverage: Double = 8.8,
        voteCount: Int = 2500,
    ) = MovieDetailApiModel(
        adult = adult,
        backdropPath = backdropPath,
        budget = budget,
        genres = genres,
        homepage = homepage,
        id = id,
        imdbId = imdbId,
        originCountry = originCountry,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        productionCompanies = productionCompanies,
        productionCountries = productionCountries,
        releaseDate = releaseDate,
        revenue = revenue,
        runtime = runtime,
        spokenLanguages = spokenLanguages,
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )
}
