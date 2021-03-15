package com.example.fulop_zoltan_simplexion.Modules

import com.example.fulop_zoltan_simplexion.Api.WeatherApi
import com.example.fulop_zoltan_simplexion.Const.Consts
import com.example.fulop_zoltan_simplexion.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit=Retrofit.Builder().baseUrl(Consts.BASEURL)
        .addConverterFactory(GsonConverterFactory.create()).client(provideOkHttp()).build()

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient =OkHttpClient.Builder()
        .connectTimeout(1,TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideWheatherApi(retrofit: Retrofit):WeatherApi=retrofit.create(WeatherApi::class.java)



}