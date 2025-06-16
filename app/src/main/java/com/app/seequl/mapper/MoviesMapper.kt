package com.app.seequl.mapper

import com.app.seequl.constants.GenreConstants
import com.app.seequl.data.model.GenreDTO
import com.app.seequl.data.model.MovieDTO
import com.app.seequl.data.model.MovieDetails

fun MovieDTO.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = this.id ?: 0,
        adult = this.adult,
        belongsToCollection = this,
        title = this.title ?: "No Title",
        overview = this.overview ?: "No Overview Available",
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate,
        genres = this.genreIds?.mapNotNull {
            GenreDTO(
                id = it,
                name = GenreConstants.getGenreNameById(it)
            )
        } ?: emptyList(),
        runtime = this.runtime,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        popularity = this.popularity,
        budget = this.budget,
        homepage = "",
        imdbId = "",
        originalLanguage = this.originalLanguage,
        originalTitle = "",
        revenue = this.revenue,
        status = "",
        tagline = this.tagline,
        video = false,
        productionCompanies = null,
        spokenLanguages = this.spokenLanguages,
        originCountry = null,
        productionCountries = null,
    )
}

fun MovieDetails.toMovieDTO(): MovieDTO {
    return MovieDTO(
        id = this.id,
        adult = this.adult ?: false,
        backdropPath = this.backdropPath,
        genreIds = this.genres?.map { it.id } ?: emptyList(),
        originalLanguage = this.originalLanguage ?: "",
        originalTitle = this.originalTitle ?: "",
        overview = this.overview,
        popularity = this.popularity ?: 0.0,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video ?: false,
        voteAverage = this.voteAverage ?: 0.0,
        voteCount = this.voteCount ?: 0,
        budget = this.budget ?: 0,
        revenue = this.revenue ?: 0,
        runtime = this.runtime ?: 0,
        tagline = this.tagline ?: "",
    )
}