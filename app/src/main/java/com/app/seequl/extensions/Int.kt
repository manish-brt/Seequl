package com.app.seequl.extensions

fun Int.toShortenedString(): String {
    return when {
        this >= 1_000_000 -> String.format("%.1fM", this / 1_000_000.0).removeSuffix(".0M") + "M"
        this >= 1_000 -> String.format("%.1fK", this / 1_000.0).removeSuffix(".0K") + "K"
        else -> this.toString()
    }
}