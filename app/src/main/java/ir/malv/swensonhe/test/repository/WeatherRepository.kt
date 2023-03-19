package ir.malv.swensonhe.test.repository

import ir.malv.swensonhe.test.api.WeatherApi
import ir.malv.swensonhe.test.api.toLocation
import ir.malv.swensonhe.test.api.toWeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Common level interface representing clean arch concept
 */
interface WeatherRepository {
    suspend fun fetch(city: String): WeatherData
    suspend fun searchCity(text: String): List<LocationData>
}

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun fetch(city: String): WeatherData = withContext(Dispatchers.IO) {
        return@withContext weatherApi.getWeatherData(city).toWeatherData(city)
    }

    override suspend fun searchCity(text: String): List<LocationData> =
        withContext(Dispatchers.IO) {
            return@withContext weatherApi.getCityResult(text).map { it.toLocation() }
        }
}
