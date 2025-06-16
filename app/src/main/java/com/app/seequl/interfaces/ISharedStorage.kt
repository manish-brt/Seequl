package com.app.seequl.interfaces

interface ISharedStorage {

    fun save(key: String, value: Any)

    fun saveSet(key: String, value: Set<String>)

    fun getSet(key: String): Set<String>

    fun getString(key: String, defaultValue: String = ""): String

    fun getInt(key: String, defaultValue: Int = 0): Int

    fun getFloat(key: String, defaultValue: Float = 0F): Float

    fun getLong(key: String, defaultValue: Long = 0L): Long

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean

    fun clear()
}