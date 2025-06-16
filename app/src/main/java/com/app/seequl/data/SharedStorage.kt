package com.app.seequl.data

import android.content.Context
import android.content.SharedPreferences
import com.app.seequl.R
import com.app.seequl.interfaces.ISharedStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit

class SharedStorage @Inject constructor(
    @ApplicationContext context: Context
) : ISharedStorage {

    private var mSharedPreference: SharedPreferences =
        context.getSharedPreferences(
            context.getString(R.string.preference_name),
            Context.MODE_PRIVATE
        )

    override fun save(key: String, value: Any) {
        mSharedPreference.edit {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
            }
        }
    }

    override fun saveSet(key: String, value: Set<String>) {
        mSharedPreference.edit().putStringSet(key, value).apply()
    }

    override fun getSet(key: String): Set<String> =
        mSharedPreference.getStringSet(key, setOf()) ?: setOf()

    override fun getString(key: String, defaultValue: String): String =
        mSharedPreference.getString(key, defaultValue) ?: defaultValue

    override fun getInt(key: String, defaultValue: Int): Int =
        mSharedPreference.getInt(key, defaultValue)

    override fun getFloat(key: String, defaultValue: Float): Float =
        mSharedPreference.getFloat(key, defaultValue)

    override fun getLong(key: String, defaultValue: Long): Long =
        mSharedPreference.getLong(key, defaultValue)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        mSharedPreference.getBoolean(key, defaultValue)

    override fun clear() {
        mSharedPreference.edit().clear().apply()
    }

}