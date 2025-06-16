package com.app.seequl.data.database

import androidx.room.TypeConverter
import com.app.seequl.data.model.SpokenLanguage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DBConverters {

    @TypeConverter
    fun listToString(list: List<String>?): String {
        if (list == null) return ""
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToList(json: String?): List<String> {
        if (json.isNullOrEmpty().not()) {
            val type = object : TypeToken<List<String>?>() {}.type
            return Gson().fromJson(json, type)
        }
        return listOf()
    }

    @TypeConverter
    fun intListToString(list: List<Int>?): String {
        if (list == null) return ""
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToIntList(json: String?): List<Int> {
        if (json.isNullOrEmpty().not()) {
            val type = object : TypeToken<List<Int>?>() {}.type
            return Gson().fromJson(json, type)
        }
        return listOf()
    }

    @TypeConverter
    fun languagesToString(list: List<SpokenLanguage>?): String {
        if (list == null) return ""
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToLanguages(json: String?): List<SpokenLanguage> {
        if (json.isNullOrEmpty().not()) {
            val type = object : TypeToken<List<SpokenLanguage>?>() {}.type
            return Gson().fromJson(json, type)
        }
        return listOf()
    }

}