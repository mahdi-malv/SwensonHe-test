package ir.malv.swensonhe.test.api

import ir.malv.swensonhe.test.repository.LocationData
import ir.malv.swensonhe.test.repository.WeatherData

fun WeatherApiResponse.toWeatherData(cityName: String): WeatherData {
    val forecastDay1 = forecast?.forecastDays?.get(0)?.day
    val forecastDay2 = forecast?.forecastDays?.get(1)?.day
    val forecastDay3 = forecast?.forecastDays?.get(2)?.day
    return WeatherData(
        city = cityName,
        currentTemperature = this.currentWeather?.temp ?: 0f,
        currentHumidity = this.currentWeather?.humidity ?: 0,
        currentWindSpeed = this.currentWeather?.windSpeed ?: 0f,
        currentText = forecastDay1?.condition?.text ?: "",

        day1Min = forecastDay1?.minTempF ?: 0f,
        day1Max = forecastDay1?.maxTempF ?: 0f,
        day1IconUrl = forecastDay1?.condition?.actualIconUrl() ?: "",

        day2Min = forecastDay2?.minTempF ?: 0f,
        day2Max = forecastDay2?.maxTempF ?: 0f,
        day2IconUrl = forecastDay2?.condition?.actualIconUrl() ?: "",

        day3Min = forecastDay3?.minTempF ?: 0f,
        day3Max = forecastDay3?.maxTempF ?: 0f,
        day3IconUrl = forecastDay3?.condition?.actualIconUrl() ?: "",
    )
}

fun City.toLocation(): LocationData {
    return LocationData(
        city = name,
        country = country
    )
}
