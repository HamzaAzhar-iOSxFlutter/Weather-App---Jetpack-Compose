package com.example.weatherapp.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.API.RetrofitInstance
import com.example.weatherapp.Environment.Constants
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {

    private val weatherAPI = RetrofitInstance.weatherAPI

    fun fetchWeatherDataBy(cityName: String) {
        viewModelScope.launch {
            val response = weatherAPI.getWeather(apiKey = Constants.apiKey, city = cityName)
            if(response.isSuccessful) {
                Log.i("Respones", response.body().toString())

            } else {
                Log.i("Respones", response.message())
            }
        }
    }
}