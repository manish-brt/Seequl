package com.app.seequl.ui.modules.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.seequl.data.database.dao.MovieDao
import com.app.seequl.data.model.MovieDTO
import com.app.seequl.data.repository.MovieRepo
import com.app.seequl.helper.AppHelper
import com.app.seequl.ui.adapters.NowShowingAdapter
import com.app.seequl.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    appHelper: AppHelper,
    val nowShowingAdapter: NowShowingAdapter,
    val topRatedAdapter: NowShowingAdapter,
    private val movieRepo: MovieRepo,
    private val movieDao: MovieDao
) : BaseViewModel(appHelper) {

    private val _trendingMovies: MutableLiveData<List<MovieDTO>> = MutableLiveData(listOf())
    val trendingMovies: LiveData<List<MovieDTO>> = _trendingMovies

    private val _trendingDataLoaded = MutableLiveData<Boolean>(false)
    val trendingDataLoaded: LiveData<Boolean> = _trendingDataLoaded

    private val _nowShowingMovies: MutableLiveData<List<MovieDTO>> = MutableLiveData(listOf())
    val nowShowingMovies: LiveData<List<MovieDTO>> = _nowShowingMovies

    private val _nowShowingDataLoaded = MutableLiveData<Boolean>(false)
    val nowShowingDataLoaded: LiveData<Boolean> = _nowShowingDataLoaded

    private val _topRatedMovies: MutableLiveData<List<MovieDTO>> = MutableLiveData(listOf())
    val topRatedMovies: LiveData<List<MovieDTO>> = _topRatedMovies

    private val _topRatedDataLoaded = MutableLiveData<Boolean>(false)
    val topRatedDataLoaded: LiveData<Boolean> = _topRatedDataLoaded

    private val _allDataLoaded = MutableLiveData<Boolean>()
    val allDataLoaded: LiveData<Boolean> = _allDataLoaded

    private val _openDetails = MutableLiveData<MovieDTO>()
    val openDetails: LiveData<MovieDTO> = _openDetails

    init {
        nowShowingAdapter.addListener {
            _openDetails.value = it
        }

        topRatedAdapter.addListener {
            _openDetails.value = it
        }
    }

    fun resetDetailsData() {
        _openDetails.value = null
    }

    fun checkAllDone() {
        _allDataLoaded.value =
            trendingMovies.value!!.isNotEmpty() && nowShowingMovies.value!!.isNotEmpty() && topRatedMovies.value!!.isNotEmpty()
    }

    fun fetchTrendingMovies() {
        launch {

            val cachedList = movieDao.trendingMovies()
            if (cachedList.isNotEmpty()) {
                _trendingMovies.value = cachedList
            }

            val res = movieRepo.trending()

            if (res?.results?.isNotEmpty() != null && cachedList != res.results) {
                _trendingMovies.value = res.results
            }

            _trendingDataLoaded.value = true
        }
    }

    fun fetchNowShowingMovies() {
        launch {

            val cachedList = movieDao.nowShowingMovies()
            if (cachedList.isNotEmpty()) {
                _nowShowingMovies.value = cachedList
            }

            val res = movieRepo.nowShowing()

            if (res?.results?.isNotEmpty() != null && cachedList != res.results) {
                _nowShowingMovies.value = res.results
            }

            _nowShowingDataLoaded.value = true
        }
    }

    fun fetchTopRatedMovies() {
        launch {

            val cachedList = movieDao.topRatedMovies()
            if (cachedList.isNotEmpty()) {
                _topRatedMovies.value = cachedList
            }

            val res = movieRepo.topRated()

            if (res?.results?.isNotEmpty() != null && cachedList != res.results) {
                _topRatedMovies.value = res.results
            }

            _topRatedDataLoaded.value = true
        }
    }
}