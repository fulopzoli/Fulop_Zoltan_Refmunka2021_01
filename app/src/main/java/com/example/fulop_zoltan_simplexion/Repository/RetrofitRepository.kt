package com.example.fulop_zoltan_simplexion.Repository

import com.example.fulop_zoltan_simplexion.Api.WeatherApi
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private  val weatherApi: WeatherApi ){

suspend fun getAllWeatherData(key: String,location: String,lang: String)=weatherApi.allData(key, location, lang)


}