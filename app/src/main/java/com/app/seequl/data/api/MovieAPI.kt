package com.app.seequl.data.api

import com.app.seequl.BuildConfig
import com.app.seequl.constants.API_MOVIE
import com.app.seequl.constants.API_MOVIE_DETAIL
import com.app.seequl.constants.API_MOVIE_NOW_SHOWING
import com.app.seequl.constants.API_MOVIE_SEARCH
import com.app.seequl.constants.API_MOVIE_TOP_RATED
import com.app.seequl.constants.API_MOVIE_TRENDING
import com.app.seequl.data.model.MovieDTO
import com.app.seequl.data.model.MovieDetails
import com.app.seequl.data.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    @GET(API_MOVIE)
    suspend fun movies(
        @Query("api_key") apiKey: String? = BuildConfig.API_KEY,
//        @Query("include_adult") includeAdult: Boolean? = false,
    ): Response<MoviesResponse>

    @GET(API_MOVIE_TRENDING)
    suspend fun trendingMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
//        @Query("include_adult") includeAdult: Boolean = false,
    ): Response<MoviesResponse>

    @GET(API_MOVIE_NOW_SHOWING)
    suspend fun showingMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
//        @Query("include_adult") includeAdult: Boolean = false,
    ): Response<MoviesResponse>

    @GET(API_MOVIE_TOP_RATED)
    suspend fun topRatedMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
//        @Query("include_adult") includeAdult: Boolean = false,
    ): Response<MoviesResponse>

    @GET(API_MOVIE_DETAIL)
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): Response<MovieDetails>

    @GET(API_MOVIE_SEARCH)
    suspend fun searchMovies(@Query("query") query: String): Response<MoviesResponse>

}