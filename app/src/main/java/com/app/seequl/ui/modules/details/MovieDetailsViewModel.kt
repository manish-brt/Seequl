package com.app.seequl.ui.modules.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.app.seequl.data.database.dao.BookmarkDao
import com.app.seequl.data.database.dao.MovieDao
import com.app.seequl.data.model.GenreDTO
import com.app.seequl.data.model.MovieDTO
import com.app.seequl.data.model.MovieDetails
import com.app.seequl.data.repository.MovieRepo
import com.app.seequl.helper.AppHelper
import com.app.seequl.mapper.toBookmarkedEntity
import com.app.seequl.mapper.toMovieDetails
import com.app.seequl.ui.adapters.GenereAdapter
import com.app.seequl.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    appHelper: AppHelper,
    private val movieRepo: MovieRepo,
    private val movieDao: MovieDao,
    val genereAdapter: GenereAdapter,
    private val bookmarkDao: BookmarkDao
) : BaseViewModel(appHelper) {

    private val _details = MutableLiveData<MovieDetails?>(null)
    val details: LiveData<MovieDetails?> = _details

    val isBookmarked = MediatorLiveData<Boolean>()

    fun fetchMovieDetails(movieId: Int) {
        launch {
            showProgressBar()

            val cachedData = movieDao.movieById(movieId)

            val genres = mutableListOf<GenreDTO>()

            if (cachedData != null) {
                _details.value = cachedData.toMovieDetails()

                genres.clear()
                details.value?.genres?.let { genres.addAll(it) }
            }

            val res = movieRepo.details(movieId)

            if (res != null) {
                _details.value = res
            }

            res?.genres?.let {
                genres.clear()
                genres.addAll(it)
            }

            genereAdapter.updateList(genres)

            observeBookmark(details.value?.id!!)

            hideProgressBar()
        }
    }

    fun isBookmarked(movieId: Int): LiveData<Boolean> {
        return bookmarkDao.isBookmarked(movieId)
    }

    fun observeBookmark(movieId: Int) {
        val source = bookmarkDao.isBookmarked(movieId)
        isBookmarked.addSource(source) { isBookmarked.value = it }
    }

    fun toggleBookmark() {
        launch {
            val movie = details.value!!
            val bookmarked = bookmarkDao.isBookmarkedOnce(movie.id) ?: false
            val entity = movie.toBookmarkedEntity()

            if (bookmarked) {
                bookmarkDao.unBookmark(entity.id)
            } else {
                bookmarkDao.bookmark(entity)
            }
        }
        isBookmarked.value = !isBookmarked.value!!
    }
}