package com.app.seequl.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.seequl.R
import com.app.seequl.data.database.dao.BookmarkDao
import com.app.seequl.data.database.dao.MovieDao
import com.app.seequl.data.model.BookmarkEntity
import com.app.seequl.data.model.MovieDTO

@Database(
    entities = [MovieDTO::class, BookmarkEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(DBConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            Room.databaseBuilder(
                                context,
                                AppDatabase::class.java,
                                context.getString(R.string.database_name)
                            )
                                .allowMainThreadQueries()
                                .fallbackToDestructiveMigration(true)
                                .build()
                    }
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}