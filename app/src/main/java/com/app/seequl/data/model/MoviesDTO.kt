package com.app.seequl.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.seequl.constants.BASE_IMAGE_URL_OG
import com.app.seequl.constants.BASE_IMAGE_URL_W500
import com.app.seequl.constants.GenreConstants
import com.app.seequl.constants.MovieSectionType
import com.app.seequl.extensions.round
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import kotlin.collections.joinToString


@Serializable
data class MoviesResponse(
    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("results")
    val results: List<MovieDTO>? = null,

    @SerializedName("total_pages")
    val totalPages: Int? = null,

    @SerializedName("total_results")
    val totalResults: Int? = null
)


//@Parcelize
@Serializable
@Entity(tableName = "movie_home")
data class MovieDTO(
    @SerializedName("adult")
    val adult: Boolean = false,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,

    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("popularity")
    val popularity: Double? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("title")
    val title: String? = "",

//    @SerializedName("tag_line")
//    val tagLine: String? = "",

    @SerializedName("video")
    val video: Boolean? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null,

    @SerializedName("vote_count")
    val voteCount: Int? = null,

    @SerializedName("runtime")
    val runtime: Int? = null,

    ///
    @SerializedName("tagline")
    val tagline: String? = null,

    @SerializedName("revenue")
    val revenue: Int? = null,

    @SerializedName("budget")
    val budget: Int? = null,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>? = null,

    @ColumnInfo("section_type")
    var sectionType: Int? = MovieSectionType.UNKNOWN.value,

    @ColumnInfo("bookmarked")
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