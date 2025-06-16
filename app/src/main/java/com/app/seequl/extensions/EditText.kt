package com.app.seequl.extensions

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

private val mainHandler = Handler(Looper.getMainLooper())

fun EditText.setDebouncedTextListener(
    debounceTime: Long = 500,
    onDebouncedTextChange: (String) -> Unit
) {
    var workRunnable: Runnable? = null

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            workRunnable?.let { mainHandler.removeCallbacks(it) }
            workRunnable = Runnable {
                onDebouncedTextChange(s.toString())
            }
            mainHandler.postDelayed(workRunnable, debounceTime)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    })
}