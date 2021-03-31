package com.razzaghi.clockifyapi.di

import android.content.Context
import android.content.SharedPreferences
import com.razzaghi.clockifyapi.api.ClockifyApi
import com.razzaghi.clockifyapi.other.Constants.BASE_URL
import com.razzaghi.clockifyapi.other.Constants.KEY_API
import com.razzaghi.clockifyapi.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.razzaghi.clockifyapi.other.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    val interceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)
    }.build()


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideClockifyApi(retrofit: Retrofit): ClockifyApi =
        retrofit.create(ClockifyApi::class.java)


    @Singleton
    @Provides
    fun provideSharedPrefernces(@ApplicationContext app: Context) =
        app.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideApiKey(sharedPref: SharedPreferences) = sharedPref.getString(KEY_API, "") ?: ""


    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) =
        sharedPref.getBoolean(KEY_FIRST_TIME_TOGGLE, true)


}