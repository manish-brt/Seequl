package com.app.seequl.data.repository

import com.app.seequl.utils.LogUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseRepo(
    private val ioDispatcher: CoroutineDispatcher

) {
    companion object {
        private const val TAG = "BaseRepo"
    }

    protected suspend fun <T> execute(
        call: suspend () -> Response<T>
    ): T? {
        var body: T? = null

        withContext(ioDispatcher) {
            var err: String? = null
            var response: Response<T>? = null
            try {
                response = call()
                when (response.code()) {
                    200, 201, 204 -> {
                        body = response.body()
                    }

                    401 -> {
                        err = response.message()
                    }

                    else -> {
                        err = response.message()
                    }
                }
            } catch (e: Exception) {
                LogUtil.e(TAG, "execute:: ", e)
                err = e.localizedMessage ?: "!! Something went wrong !!"
            }

            err
        }

        return body
    }
}