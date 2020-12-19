package com.poussiere.kotlinnetworkboundresource.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poussiere.kotlinnetworkboundresource.model.local.WeatherForADay
import com.poussiere.kotlinnetworkboundresource.repositories.WeatherRepository
import com.poussiere.kotlinnetworkboundresource.services.remote.FetchError
import com.poussiere.kotlinnetworkboundresource.services.remote.FetchSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import timber.log.Timber

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@FlowPreview
class TodayWeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    //Viewmodel fields that will automatically update UI on change
    val displayedDate = ObservableField("")
    val displayedTemperature = ObservableField("")
    val displayedFeelsLike = ObservableField("")
    val displayedMinimum = ObservableField("")
    val displayedMaximum = ObservableField("")
    val displayedPressure = ObservableField("")
    val displayedHumidity = ObservableField("")
    val displayedWindSpeed = ObservableField("")


    //Trigger UI refresh by passing a city and store last emitted value
    @ExperimentalCoroutinesApi
    private val todayWeatherChannel = ConflatedBroadcastChannel<String>()

    //Expose the refresh data trigger
    @ExperimentalCoroutinesApi
    fun triggerCitySearch(city: String) {
        todayWeatherChannel.offer(city)
    }

    init {
        loadTodaysWeatherForACity()
    }

    /**
     * Load data for today weather in a city
     */
    @InternalCoroutinesApi
    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun loadTodaysWeatherForACity() {
        todayWeatherChannel.asFlow().flatMapLatest{ city ->
            //Todo : enable loading spinner here
            weatherRepository.getTodayWeather(city)
        }
            .onEach { fetchedWeather ->
                if (fetchedWeather is FetchSuccess) {
                    fetchedWeather.data.populateViewModel()
                }
            }
            .launchIn(viewModelScope)
    }

    /**
     * Populate viewModel fields from a WeatherForADay object
     */
    private fun WeatherForADay.populateViewModel() {
        displayedDate.set(this.date.toString("dd/MM/yyyy"))
        displayedTemperature.set(this.temp.toString())
        displayedFeelsLike.set(this.feelsLike.toString())
        displayedMinimum.set(this.tempMin.toString())
        displayedMaximum.set(this.tempMax.toString())
        displayedPressure.set(this.pressure.toString())
        displayedHumidity.set(this.humidity.toString())
        displayedWindSpeed.set(this.windSpeed.toString())
    }
}