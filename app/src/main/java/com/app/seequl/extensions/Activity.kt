package com.app.seequl.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

inline fun <reified T : Any> newIntent(context: Context) = Intent(context, T::class.java)


inline fun <reified T : Any> Context.launchActivity(
    clearFlags: Boolean = false,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    if (clearFlags) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}

inline fun <reified T : Any> Activity.launchActivity(
    finish: Boolean = false,
    clearFlags: Boolean = false,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    if (clearFlags) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    if (options != null)
        startActivity(intent, options)
    else
        startActivity(intent)

    if (finish) {
        this.finish()
    }
}