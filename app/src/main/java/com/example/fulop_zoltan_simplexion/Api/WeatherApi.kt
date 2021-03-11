package com.example.fulop_zoltan_simplexion.Api

import com.example.fulop_zoltan_simplexion.DaraModels.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("")
    suspend fun alldata (
        @Query("key") key:String,
        @Query("q") location:String,
        @Query("lang") lang:String
    ):WeatherData
}