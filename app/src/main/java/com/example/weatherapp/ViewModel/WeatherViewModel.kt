package com.example.weatherapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.API.NetworkResponse
import com.example.weatherapp.API.RetrofitInstance
import com.example.weatherapp.Environment.Constants
import com.example.weatherapp.Model.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {

    private val weatherAPI = RetrofitInstance.weatherAPI
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun fetchWeatherDataBy(cityName: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherAPI.getWeather(apiKey = Constants.apiKey, city = cityName)
                if(response.isSuccessful) {
                    Log.i("Respones", response.body().toString())
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }

                } else {
                    _weatherResult.value = NetworkResponse.Error("Unable to fetch data")
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("Unable to fetch data")
            }
        }
    }
}