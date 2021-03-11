package com.example.fulop_zoltan_simplexion.Modules

import com.example.fulop_zoltan_simplexion.Api.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit=Retrofit.Builder().baseUrl("das")
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideWheatherApi(retrofit: Retrofit):WeatherApi=retrofit.create(WeatherApi::class.java)



}