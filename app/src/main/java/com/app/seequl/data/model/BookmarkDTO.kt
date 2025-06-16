package com.app.seequl.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.seequl.constants.BASE_IMAGE_URL_OG
import com.app.seequl.constants.BASE_IMAGE_URL_W500
import com.app.seequl.constants.GenreConstants
import com.app.seequl.constants.MovieSectionType
import com.app.seequl.extensions.round
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "bookmarked_movies")
data class BookmarkEntity(
    @PrimaryKey
    val id: Int,

    val adult: Boolean? = false,

    val backdropPath: String? = null,

    val genreIds: List<Int>? = null,

    val originalLanguage: String? = null,

    val originalTitle: String? = null,

    val overview: String? = null,

    val popularity: Double? = null,

    val posterPath: String? = null,

    val releaseDate: String? = null,

    val title: String? = "",

    val video: Boolean? = null,

    val voteAverage: Double? = null,

    val voteCount: Int? = null,

    val runtime: Int? = null,

    val tagline: String? = null,

    val revenue: Int? = null,

    val budget: Int? = null,

    val spokenLanguages: List<SpokenLanguage>? = null,

    var sectionType: Int? = MovieSectionType.UNKNOWN.value,

    var bookmarked: Boolean = false

) {

    fun year(): String = releaseDate?.take(4).toString()

    fun rating(): String = voteAverage?.round(2).toString()

    fun popularity(): String = popularity?.round(2).toString()

    fun backDropW500() = BASE_IMAGE_URL_W500 + backdropPath

    fun backDropOG() = BASE_IMAGE_URL_OG + backdropPath

    fun posterW500() = BASE_IMAGE_URL_W500 + posterPath

    fun posterOG() = BASE_IMAGE_URL_OG + posterPath

    fun genreNames(): String = genreIds?.joinToString(" | ") { GenreConstants.getGenreNameById(it) }
        ?: "-"
}