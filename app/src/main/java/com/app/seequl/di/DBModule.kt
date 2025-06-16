package com.app.seequl.di

import android.content.Context
import com.app.seequl.data.database.AppDatabase
import com.app.seequl.data.database.dao.BookmarkDao
import com.app.seequl.data.database.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context)


    @Singleton
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao = appDatabase.movieDao()

    @Singleton
    @Provides
    fun provideBookmarkDao(appDatabase: AppDatabase): BookmarkDao = appDatabase.bookmarkDao()

}