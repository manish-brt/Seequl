package com.app.seequl.ui.modules.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.seequl.data.database.dao.BookmarkDao
import com.app.seequl.data.database.dao.MovieDao
import com.app.seequl.data.model.BookmarkEntity
import com.app.seequl.helper.AppHelper
import com.app.seequl.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    appHelper: AppHelper,
    private val movieDao: MovieDao,
    private val bookmarkDao: BookmarkDao
) : BaseViewModel(appHelper) {

    val bookmarkedMovies: LiveData<List<BookmarkEntity>> = bookmarkDao.getAllBookmarks()

    private val _noData: MutableLiveData<Boolean> = MutableLiveData(false)
    val noData: LiveData<Boolean> = _noData

    fun noDataAvailable(bool: Boolean) {
        _noData.value = bool
    }

    fun deleteBookmark(dto: BookmarkEntity) {
        launch {
            bookmarkDao.delete(dto.id)
        }
    }
}