package ir.malv.swensonhe.test.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.malv.swensonhe.test.repository.LocationData
import ir.malv.swensonhe.test.repository.WeatherData
import ir.malv.swensonhe.test.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _citySuggestion = MutableStateFlow(emptyList<LocationData>())
    val citySuggestion = _citySuggestion.asStateFlow()

    private val _weatherData = MutableStateFlow(WeatherData.empty())
    val weatherData = _weatherData.asStateFlow()

    private val citySearchQuery = MutableStateFlow("")

    init {
        onCitySelected(LocationData("San Francisco", "USA")) // Initial
        viewModelScope.launch(Dispatchers.Main) {
            citySearchQuery
                .debounce(500)
                .collectLatest {
                    if (it.length < 3) return@collectLatest
                    try {
                        _citySuggestion.emit(
                            weatherRepository.searchCity(it).take(10)
                        ) // Take only a limited number
                    } catch (e: Exception) {
                        Log.e("ViewModel", "Failed to get city data", e)
                    }
                }
        }
    }

    /**
     * Called when textField value of search is changed
     */
    fun onTextQueryChanged(s: String) = viewModelScope.launch(Dispatchers.Main) {
        citySearchQuery.emit(s)
    }

    /**
     * When user clicks on close suggestion button
     */
    fun clearSuggestions() = viewModelScope.launch(Dispatchers.Main) {
        _citySuggestion.emit(emptyList())
    }

    /**
     * When user clicks on a city suggested by the API
     */
    fun onCitySelected(location: LocationData) = viewModelScope.launch(Dispatchers.Main) {
        try {
            _weatherData.emit(weatherRepository.fetch(location.city))
        } catch (e: Exception) {
            Log.e("ViewModel", "onCitySelected: Failed to get weather data", e)
        }
    }
}
