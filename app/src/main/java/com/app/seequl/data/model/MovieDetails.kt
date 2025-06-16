package com.app.seequl.data.model


import com.app.seequl.constants.BASE_IMAGE_URL_OG
import com.app.seequl.constants.BASE_IMAGE_URL_W500
import com.app.seequl.extensions.round
import com.app.seequl.extensions.toShortenedString
import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("adult")
    val adult: Boolean? = false,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any? = null,

    @SerializedName("budget")
    val budget: Int? = null,

    @SerializedName("genres")
    val genres: List<GenreDTO>? = null,

    @SerializedName("homepage")
    val homepage: String? = null,

    @SerializedName("id")
    val id: Int,

    @SerializedName("imdb_id")
    val imdbId: String? = null,

    @SerializedName("origin_country")
    val originCountry: List<String?>? = null,

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

    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany?>? = null,

    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry?>? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("revenue")
    val revenue: Int? = null,

    @SerializedName("runtime")
    val runtime: Int? = 0,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("tagline")
    val tagline: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("video")
    val video: Boolean? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null,

    @SerializedName("vote_count")
    val voteCount: Int? = null,
    ) {

    fun rating(): String = voteAverage?.round(2).toString()

    fun year(): String = releaseDate?.take(4).toString()

    fun spokenLangs(): String = spokenLanguages?.joinToString(" | ") { it?.englishName ?: " - " } ?: " -"

    fun production(): String = productionCompanies?.joinToString("\n") {
        ("* " + it?.name)
    } ?: " -"

    fun budgetFormat(): String = budget?.toShortenedString() ?: " - "

    fun revenueFormat(): String = revenue?.toShortenedString() ?: " - "

    fun backDropW500() = BASE_IMAGE_URL_W500 + backdropPath

    fun backDropOG() = BASE_IMAGE_URL_OG + backdropPath

    fun posterW500() = BASE_IMAGE_URL_W500 + posterPath

    fun posterOG() = BASE_IMAGE_URL_OG + posterPath
}

data class GenreDTO(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String? = null
)

data class ProductionCompany(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("logo_path")
    val logoPath: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("origin_country")
    val originCountry: String? = null
)

data class ProductionCountry(

    @SerializedName("iso_3166_1")
    val iso31661: String? = null,

    @SerializedName("name")
    val name: String? = null
)

data class SpokenLanguage(

    @SerializedName("english_name")
    val englishName: String? = null,

    @SerializedName("iso_639_1")
    val iso6391: String? = null,

    @SerializedName("name")
    val name: String? = null
)
