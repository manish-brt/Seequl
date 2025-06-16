package com.app.seequl.di.network

import com.app.seequl.constants.ACCESS_TOKEN
import com.app.seequl.constants.BASE_URL
import com.app.seequl.constants.CALL_TIMEOUT
import com.app.seequl.constants.CONNECTION_TIMEOUT
import com.app.seequl.constants.HEADER_AUTHORIZATION
import com.app.seequl.constants.READ_TIMEOUT
import com.app.seequl.constants.SERVER_SEEQUL
import com.app.seequl.data.SharedStorage
import com.app.seequl.data.api.MovieAPI
import com.app.seequl.interfaces.ISharedStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @Named(SERVER_SEEQUL)
    fun provideSequelRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideOkHttpClient(
        storage: ISharedStorage,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .headers(buildHeaders(storage, original.headers))
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
        .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
//        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
//        else HttpLoggingInterceptor.Level.NONE
    }

    private fun buildHeaders(storage: ISharedStorage, headers: Headers) = Headers.Builder().apply {

        add(HEADER_AUTHORIZATION, "Bearer $ACCESS_TOKEN")

        for (header in headers) {
            set(header.first, header.second)
        }

    }.build()


    @Provides
    @Singleton
    fun provideMovieApi(@Named(SERVER_SEEQUL) retrofit: Retrofit): MovieAPI =
        retrofit.create(MovieAPI::class.java)
}


