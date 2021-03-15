package com.example.fulop_zoltan_simplexion.Api

import com.example.fulop_zoltan_simplexion.DataModels.Json.WeatherData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current.json")
    suspend fun allData(
        @Query("key") key: String,
        @Query("q") location: String,
        @Query("lang") lang: String
    ): Response<WeatherData>
}