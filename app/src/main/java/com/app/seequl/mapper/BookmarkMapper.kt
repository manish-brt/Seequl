package com.app.seequl.mapper

import com.app.seequl.constants.GenreConstants
import com.app.seequl.data.model.BookmarkEntity
import com.app.seequl.data.model.GenreDTO
import com.app.seequl.data.model.MovieDTO
import com.app.seequl.data.model.MovieDetails

fun MovieDTO.toBookmarkedEntity(): BookmarkEntity {

    return BookmarkEntity(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath,
        overview = this.overview,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        genreIds = this.genreIds,
        sectionType = this.sectionType,
        backdropPath = this.backdropPath,
        popularity = this.popularity,
        adult = this.adult,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        video = this.video,
        voteCount = this.voteCount,
        budget = this.budget,
        revenue = this.revenue,
        runtime = this.runtime,
        tagline = this.tagline,
        spokenLanguages = this.spokenLanguages,
    )
}

fun MovieDetails.toBookmarkedEntity(): BookmarkEntity {
    return BookmarkEntity(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath,
        overview = this.overview,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        genreIds = this.genres?.map { it.id },
        backdropPath = this.backdropPath,
        popularity = this.popularity,
        adult = this.adult,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        video = this.video,
        voteCount = this.voteCount,
        budget = this.budget,
        revenue = this.revenue,
        runtime = this.runtime,
        tagline = this.tagline,
        spokenLanguages = this.spokenLanguages
    )
}

fun BookmarkEntity.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath,
        overview = this.overview,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        genres = this.genreIds?.map {
            GenreDTO(
                id = it,
                name = GenreConstants.getGenreNameById(it)
            )
        },
        backdropPath = this.backdropPath,
        popularity = this.popularity,
        adult = this.adult,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        video = this.video,
        voteCount = this.voteCount,
        budget = this.budget,
        revenue = this.revenue,
        runtime = this.runtime,
        tagline = this.tagline,
        spokenLanguages = this.spokenLanguages,
    )
}