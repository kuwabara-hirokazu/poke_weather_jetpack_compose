package com.example.sampleweather.model

import com.example.sampleweather.R

data class WeeklyForecast(
    val date: String,
    val weather: WeatherItem,
    val minimumTemperature: Int,
    val maximumTemperature: Int,
    val chanceOfRain: Int
)

enum class WeatherItem(val weather: Int, val icon: Int, val image: Int) {
    SUNNY(R.string.sunny, R.drawable.sunny, R.drawable.img_sunny),
    RAINY(R.string.rainy, R.drawable.rain, R.drawable.img_rainy),
    SNOW(R.string.snow, R.drawable.snow, R.drawable.img_snow),
    THUNDER(R.string.thunder, R.drawable.thunder, R.drawable.img_thunder)
}