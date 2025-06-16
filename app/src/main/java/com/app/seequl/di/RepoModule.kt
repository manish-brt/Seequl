package com.app.seequl.di

import com.app.seequl.data.repository.MovieRepo
import com.app.seequl.data.repository.impl.MovieRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun provideMovieRepo(movieRepoImpl: MovieRepoImpl): MovieRepo

}