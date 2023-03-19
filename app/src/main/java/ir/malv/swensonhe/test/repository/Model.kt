package ir.malv.swensonhe.test.repository


data class WeatherData(
    val city: String,
    val currentTemperature: Float,
    val currentHumidity: Int,
    val currentWindSpeed: Float,
    val currentText: String,

    val day1Min: Float,
    val day1Max: Float,
    val day1IconUrl: String,

    val day2Min: Float,
    val day2Max: Float,
    val day2IconUrl: String,

    val day3Min: Float,
    val day3Max: Float,
    val day3IconUrl: String
) {
    companion object {
        /**
         * Used to initialize in ViewModels and ...
         */
        fun empty() = WeatherData(
            "", 0f, 0,
            0f, "",
            0f, 0f, "",
            0f, 0f, "",
            0f, 0f, "",
        )
    }

    fun isEmpty() = this == empty()
}

data class LocationData(
    val city: String,
    val country: String
)
