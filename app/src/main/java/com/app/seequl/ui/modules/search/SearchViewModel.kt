package com.app.seequl.ui.modules.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.seequl.data.database.dao.BookmarkDao
import com.app.seequl.data.database.dao.MovieDao
import com.app.seequl.data.model.MovieDTO
import com.app.seequl.data.model.MovieDetails
import com.app.seequl.data.repository.MovieRepo
import com.app.seequl.helper.AppHelper
import com.app.seequl.mapper.toBookmarkedEntity
import com.app.seequl.ui.adapters.MovieSearchAdapter
import com.app.seequl.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    appHelper: AppHelper,
    private val movieRepo: MovieRepo,
    private val movieDao: MovieDao,
    private val bookmarkDao: BookmarkDao,
    val searchAdapter: MovieSearchAdapter
) : BaseViewModel(appHelper) {

    var searchStr = MutableLiveData("")
    var searchedString = MutableLiveData("")

    private val _noData: MutableLiveData<Boolean> = MutableLiveData(false)
    val noData: LiveData<Boolean> = _noData

    private val _openDetails = MutableLiveData<MovieDTO?>()
    val openDetails: LiveData<MovieDTO?> = _openDetails

    init {
        searchAdapter.addListener {
            _openDetails.value = it
        }

        searchAdapter.addBookmarkedListener { it, _ ->
            toggleBookmark(it)
        }
    }



    fun searchMovies() {
        if (!searchedString.value.isNullOrEmpty()) {
            runOnNetwork {

                val res = movieRepo.search(searchedString.value ?: "")
                val list = res?.results ?: listOf()

                val bookmarks = bookmarkDao.getAllBookmarksOnce()
                val bookmarkedIds = bookmarks.map { it.id }.toSet()

                val merged = list.map { movie ->
                    movie.copy(bookmarked = movie.id in bookmarkedIds)
                }

                searchAdapter.updateList(merged)

                _noData.value = list.isEmpty()

            }
        }
    }

    fun resetDetailsData() {
        _openDetails.value = null
    }

    fun clearSearchStrClick() {
        searchStr.value = ""
    }

    fun saveSearchedString(string: String?) {
        searchedString.value = string ?: ""
    }

    fun resetPage() {

    }

    fun toggleBookmark(movie: MovieDTO) {
        launch {
            val bookmarked = bookmarkDao.isBookmarked(movie.id).value ?: false
            val entity = movie.toBookmarkedEntity()

            if (bookmarked) {
                bookmarkDao.unBookmark(entity.id)
            } else {
                bookmarkDao.bookmark(entity)
            }
        }
    }

}