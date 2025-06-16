package com.app.seequl.data.repository

import com.app.seequl.data.model.MovieDTO
import com.app.seequl.data.model.MovieDetails
import com.app.seequl.data.model.MoviesResponse

interface MovieRepo {

    suspend fun list(): MoviesResponse?

    suspend fun trending(): MoviesResponse?

    suspend fun nowShowing(): MoviesResponse?

    suspend fun topRated(): MoviesResponse?

    suspend fun details(movieId: Int): MovieDetails?

    suspend fun search(query: String): MoviesResponse?
}