package com.app.seequl.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.seequl.R
import com.app.seequl.helper.AppHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel @Inject constructor(
    val appHelper: AppHelper
) : ViewModel(){

    private val _showToastMessage: MutableLiveData<Int> = MutableLiveData(Int.MIN_VALUE)
    val showToastObserver: LiveData<Int> = _showToastMessage

    private val _showProgressBar: MutableLiveData<Boolean> = MutableLiveData(false)
    val showProgressBar: LiveData<Boolean> = _showProgressBar

    protected inline fun runOnNetwork(
        dispatcher: CoroutineContext = EmptyCoroutineContext,
        errorId: Int? = null,
        noNetMsg: Boolean = true,
        crossinline block: suspend () -> Unit
    ): Job? {
        var job: Job? = null
        if (errorId == null) {
            if (appHelper.isNetworkConnected()) {
                job = viewModelScope.launch(dispatcher) {
                    block()
                }
            } else {
                if (noNetMsg) {
                    showToast(R.string.no_internet)
                }
            }
        } else {
            showToast(errorId)
        }
        return job
    }

    protected inline fun launch(crossinline block: suspend () -> Unit) =
        viewModelScope.launch { block() }

    protected inline fun launch(
        dispatcher: CoroutineContext,
        crossinline block: suspend () -> Unit
    ) {
        viewModelScope.launch(dispatcher) { block() }
    }

    protected fun showToast(resId: Int) {
        _showToastMessage.value = resId
    }

    protected fun showProgressBar() {
        _showProgressBar.value = true
    }

    protected fun hideProgressBar() {
        _showProgressBar.value = false
    }

}