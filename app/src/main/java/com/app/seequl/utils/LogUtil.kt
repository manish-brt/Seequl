package com.app.seequl.utils

import android.util.Log
import com.app.seequl.BuildConfig

object LogUtil {

    fun d(tag: String, m: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, m)
        }
    }

    fun e(tag: String, m: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, m)
        }
    }

    fun e(tag: String, m: String, t: Throwable?) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, m, t)
        }
    }

    fun i(tag: String, m: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, m)
        }
    }
    inline fun <reified T:Any> T.MyLog( arg:String){
        d(T::class.java.simpleName, arg)
    }
}