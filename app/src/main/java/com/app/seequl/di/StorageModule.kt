package com.app.seequl.di

import com.app.seequl.data.SharedStorage
import com.app.seequl.interfaces.ISharedStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Singleton
    @Binds
    abstract fun provideStorage(storage: SharedStorage): ISharedStorage

}