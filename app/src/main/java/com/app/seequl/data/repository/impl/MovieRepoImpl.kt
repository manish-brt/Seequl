package com.app.seequl.data.repository.impl

import com.app.seequl.constants.MovieSectionType
import com.app.seequl.data.api.MovieAPI
import com.app.seequl.data.database.dao.MovieDao
import com.app.seequl.data.model.MovieDTO
import com.app.seequl.data.model.MovieDetails
import com.app.seequl.data.model.MoviesResponse
import com.app.seequl.data.repository.BaseRepo
import com.app.seequl.data.repository.MovieRepo
import com.app.seequl.mapper.toMovieDTO
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieRepoImpl @Inject constructor(
    coroutineDispatcher: CoroutineDispatcher,
    private val movieAPI: MovieAPI,
    private val moviesDao: MovieDao,
) : BaseRepo(coroutineDispatcher), MovieRepo {


    override suspend fun list(): MoviesResponse? {
        val res = execute { movieAPI.movies() }
        return res
    }

    override suspend fun trending(): MoviesResponse? {
        val res = execute { movieAPI.trendingMovies() }

        val movies = res?.results ?: listOf()

        if (movies.isNotEmpty()) {
            movies.onEach { it.sectionType = MovieSectionType.TRENDING.value }
            moviesDao.deleteAll(MovieSectionType.TRENDING.value)
            moviesDao.insert(movies)
        }
        return res
    }

    override suspend fun nowShowing(): MoviesResponse? {
        val res = execute { movieAPI.showingMovies() }

        val movies = res?.results ?: listOf()

        if (movies.isNotEmpty()) {
            movies.onEach { it.sectionType = MovieSectionType.NOW_SHOWING.value }
            moviesDao.deleteAll(MovieSectionType.NOW_SHOWING.value)
            moviesDao.insert(movies)
        }
        return res
    }

    override suspend fun topRated(): MoviesResponse? {
        val res = execute { movieAPI.topRatedMovies() }

        val movies = res?.results ?: listOf()
        movies.onEach { it.sectionType = MovieSectionType.TOP_RATED.value }

        if (movies.isNotEmpty()) {
            moviesDao.deleteAll(MovieSectionType.TOP_RATED.value)
            moviesDao.insert(movies)
        }

        return res
    }

    override suspend fun details(movieId: Int): MovieDetails? {
        val res = execute { movieAPI.getMovieDetails(movieId) }

        if (res != null) {
            moviesDao.update(res.toMovieDTO())
        }

        return res
    }

    override suspend fun search(query: String): MoviesResponse? {
        val res = execute { movieAPI.searchMovies(query) }
        return res
    }

}