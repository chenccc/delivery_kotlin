package com.james.deliveryproject.di

import com.james.deliveryproject.api.DeliveryService
import com.james.deliveryproject.util.Util
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun logger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    @Singleton
    @Provides
    fun client(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger())
            .build()
    }

    @Singleton
    @Provides
    fun service(): DeliveryService {
        return Retrofit.Builder()
            .baseUrl(Util.BASE_URL)
            .client(client())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeliveryService::class.java)
    }
}