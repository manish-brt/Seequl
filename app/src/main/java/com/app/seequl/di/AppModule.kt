package com.app.seequl.di.network

import android.content.Context
import com.app.seequl.helper.AppHelper
import com.app.seequl.interfaces.ISharedStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideAppHelper(
        @ApplicationContext context: Context,
        coroutineDispatcher: CoroutineDispatcher,
        storage: ISharedStorage
    ): AppHelper = AppHelper(context, coroutineDispatcher, storage)

    @Singleton
    @Provides
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

}