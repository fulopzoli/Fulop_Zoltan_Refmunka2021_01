package com.example.fulop_zoltan_simplexion.ViewModel


import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fulop_zoltan_simplexion.DataModels.Json.WeatherData
import com.example.fulop_zoltan_simplexion.R
import com.example.fulop_zoltan_simplexion.Repository.RetrofitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException


class RetrofitViewModel @ViewModelInject constructor(private val repository: RetrofitRepository) :
    ViewModel() {

    private lateinit var Key: String
    private lateinit var Location: String
    private lateinit var Language: String

    fun searchWeather(key: String, location: String, language: String) {
        Key = key
        Location = location
        Language = language

    }

    var getData = liveData(Dispatchers.IO) {
        try {
            val result = repository.getAllWeatherData(Key, Location, Language)
            if (result.isSuccessful) {
                emit(result.body()!!)
            } else {
                emit(result)
            }

        } catch (e: IOException) {
            emit(e.message);
        }

    }


}


