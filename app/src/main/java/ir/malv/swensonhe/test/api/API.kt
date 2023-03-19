package ir.malv.swensonhe.test.api

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

interface WeatherApiService {
    @GET("forecast.json")
    suspend fun getWeatherData(
        @Query("key") apiKey: String,
        @Query("q") cityName: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String
    ): WeatherApiResponse

    @GET("/v1/search.json")
    suspend fun searchCities(@Query("key") apiKey: String, @Query("q") query: String): List<City>
}

@Singleton
class WeatherApi @Inject constructor(
    retrofit: Retrofit
) {
    private val service = retrofit.create(WeatherApiService::class.java)

    private val apiKey = "cea28fc931474e94904232834231803" // TODO(mahdi): Too exposed
    private val days = 3
    private val aqi = "no"
    private val alerts = "no"

    suspend fun getWeatherData(cityName: String): WeatherApiResponse {
        return service.getWeatherData(apiKey, cityName, days, aqi, alerts)
    }

    suspend fun getCityResult(text: String): List<City> {
        return service.searchCities(apiKey, text)
    }
}
