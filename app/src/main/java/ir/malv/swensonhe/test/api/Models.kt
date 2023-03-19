package ir.malv.swensonhe.test.api

import com.google.gson.annotations.SerializedName
import ir.malv.swensonhe.test.repository.LocationData
import ir.malv.swensonhe.test.repository.WeatherData


data class WeatherApiResponse(
    @SerializedName("current")
    val currentWeather: CurrentWeather? = null,
    @SerializedName("forecast")
    val forecast: Forecast? = null
)

data class CurrentWeather(
    @SerializedName("humidity")
    val humidity: Int = 0,
    @SerializedName("temp_f")
    val temp: Float,
    @SerializedName("wind_mph")
    val windSpeed: Float
)

data class Forecast(
    @SerializedName("forecastday")
    val forecastDays: List<ForecastDay>? = null
)

data class ForecastDay(
    @SerializedName("day")
    val day: Day? = null
)

data class Day(
    @SerializedName("mintemp_f")
    val minTempF: Float = 0f,

    @SerializedName("maxtemp_f")
    val maxTempF: Float = 0f,

    @SerializedName("condition")
    val condition: Condition? = null,
)

data class Condition(
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("text")
    val text: String = ""
) {
    /**
     * The API returns a "//api.com/..." format which doesn't contain https/http
     * This function adds it
     */
    fun actualIconUrl() = "https:$icon"
}

data class City(
    @SerializedName("name") val name: String,
    @SerializedName("region") val region: String,
    @SerializedName("country") val country: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("url") val url: String
)
